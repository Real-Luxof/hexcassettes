package miyucomics.hexcassettes.inits;

import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager.PacketContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import miyucomics.hexcassettes.PlayerEntityMinterface;
import miyucomics.hexcassettes.client.ClientStorage;

import static miyucomics.hexcassettes.Hexcassettes.id;

import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

public class HexcassettesNetworking {
    public static class CassetteRemovalPacket {
        protected final NbtCompound cassetteKey;

        public CassetteRemovalPacket(NbtCompound cassetteKey) {
            this.cassetteKey = cassetteKey;
        }

        protected CassetteRemovalPacket(PacketByteBuf buf) {
            this.cassetteKey = buf.readNbt();
        }
        protected static void encode(CassetteRemovalPacket packet, PacketByteBuf buf) {
            buf.writeNbt(packet.cassetteKey);
        }
        protected static void handle(CassetteRemovalPacket packet, Supplier<PacketContext> ctx) {
            ((PlayerEntityMinterface)ctx.get().getPlayer()).getCassetteState().hexes.remove(
                packet.cassetteKey
            );
        }
    }
    public static class SyncCassettesS2CPacket {
        protected final int owned;
        protected final List<NbtCompound> cassettes;

        public SyncCassettesS2CPacket(int owned, List<NbtCompound> cassettes) {
            this.owned = owned;
            this.cassettes = cassettes;
        }

        protected SyncCassettesS2CPacket(PacketByteBuf buf) {
            this.owned = buf.readInt();
            int count = buf.readInt();
            this.cassettes = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                cassettes.add(buf.readNbt());
            }
        }
        protected static void encode(SyncCassettesS2CPacket packet, PacketByteBuf buf) {
            buf.writeInt(packet.owned);
            buf.writeInt(packet.cassettes.size());
            packet.cassettes.forEach(buf::writeNbt);
        }
        protected static void handle(SyncCassettesS2CPacket packet, Supplier<PacketContext> ctx) {
            MinecraftClient.getInstance().execute(() -> {
                ClientStorage.ownedCassettes = packet.owned;
                ClientStorage.activeCassettes = packet.cassettes;
            });
        }
    }

    public static final NetworkChannel CASSETTE_REMOVE = NetworkChannel.create(id("cassette_remove"));
    public static final NetworkChannel SYNC_CASSETTES = NetworkChannel.create(id("sync_cassettes"));

    public static void init() {
        CASSETTE_REMOVE.register(
            CassetteRemovalPacket.class,
            CassetteRemovalPacket::encode,
            CassetteRemovalPacket::new,
            CassetteRemovalPacket::handle
        );
        SYNC_CASSETTES.register(
            SyncCassettesS2CPacket.class,
            SyncCassettesS2CPacket::encode,
            SyncCassettesS2CPacket::new,
            SyncCassettesS2CPacket::handle
        );
        // why does the client request a sync in Miyu's original code, anyway?
        PlayerEvent.PLAYER_JOIN.register(sp -> {
            ((PlayerEntityMinterface)sp).getCassetteState().sync(sp);
        });
    }
}
