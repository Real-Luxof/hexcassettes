package miyucomics.hexcassettes.inits;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;

import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.networking.NetworkManager.Side;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;

import java.util.ArrayList;
import java.util.List;

import miyucomics.hexcassettes.PlayerEntityMinterface;
import miyucomics.hexcassettes.client.ClientStorage;

import static miyucomics.hexcassettes.Hexcassettes.id;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public class HexcassettesNetworking {
    public static record CassetteRemovalC2SPayload(Iota cassetteKey) implements CustomPayload {
        public static final Id<CassetteRemovalC2SPayload> ID = new Id<>(id("cassette_remove"));
        public static final PacketCodec<RegistryByteBuf, CassetteRemovalC2SPayload> CODEC = new PacketCodec<>() {
            @Override
            public CassetteRemovalC2SPayload decode(RegistryByteBuf buf) {
                return new CassetteRemovalC2SPayload(IotaType.TYPED_STREAM_CODEC.decode(buf));
            }

            @Override
            public void encode(RegistryByteBuf buf, CassetteRemovalC2SPayload value) {
                IotaType.TYPED_STREAM_CODEC.encode(buf, value.cassetteKey());
            }
        };

        @Override public Id<? extends CustomPayload> getId() { return ID; }

        public static void handle(CassetteRemovalC2SPayload value, PacketContext ctx) {
            ((PlayerEntityMinterface)ctx.getPlayer()).getCassetteState().hexes.remove(
                value.cassetteKey()
            );
        }
    }
    public static record SyncCassettesS2CPacket(int owned, List<Iota> cassettes) implements CustomPayload {
        public static final Id<SyncCassettesS2CPacket> ID = new Id<>(id("sync_cassettes"));
        public static final PacketCodec<RegistryByteBuf, SyncCassettesS2CPacket> CODEC = new PacketCodec<>() {
            @Override
            public SyncCassettesS2CPacket decode(RegistryByteBuf buf) {
                int owned = buf.readInt();
                int size = buf.readInt();
                List<Iota> cassettes = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    cassettes.add(IotaType.TYPED_STREAM_CODEC.decode(buf));
                }
                return new SyncCassettesS2CPacket(owned, cassettes);
            }

            @Override
            public void encode(RegistryByteBuf buf, SyncCassettesS2CPacket value) {
                buf.writeInt(value.owned);
                buf.writeInt(value.cassettes.size());
                value.cassettes.forEach(iota -> IotaType.TYPED_STREAM_CODEC.encode(buf, iota));
            }
        };

        @Override public Id<? extends CustomPayload> getId() { return ID; }

        public static void handle(SyncCassettesS2CPacket packet, PacketContext ctx) {
            MinecraftClient.getInstance().execute(() -> {
                ClientStorage.ownedCassettes = packet.owned;
                ClientStorage.activeCassettes = packet.cassettes;
            });
        }
    }

    public static void init() {
        // this is probably a gross way of doing this which i should die for
        NetworkManager.registerReceiver(
            Side.C2S,
            CassetteRemovalC2SPayload.ID,
            CassetteRemovalC2SPayload.CODEC,
            CassetteRemovalC2SPayload::handle
        );
        EnvExecutor.runInEnv(Env.SERVER, () -> () -> NetworkManager.registerS2CPayloadType(
            SyncCassettesS2CPacket.ID,
            SyncCassettesS2CPacket.CODEC
        ));
        // why does the client request a sync in Miyu's original code, anyway?
        PlayerEvent.PLAYER_JOIN.register(sp -> {
            ((PlayerEntityMinterface)sp).getCassetteState().sync(sp);
        });
    }

    public static void clientInit() {
        NetworkManager.registerReceiver(
            Side.S2C,
            SyncCassettesS2CPacket.ID,
            SyncCassettesS2CPacket.CODEC,
            SyncCassettesS2CPacket::handle
        );
    }
}
