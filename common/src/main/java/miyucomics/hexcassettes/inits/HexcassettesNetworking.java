package miyucomics.hexcassettes.inits;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;

import miyucomics.hexcassettes.PlayerEntityMinterface;
import miyucomics.hexcassettes.client.ClientStorage;

import static miyucomics.hexcassettes.Hexcassettes.id;

import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

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
    public static class SyncCassettesPacket {
        protected int owned = 0;
        protected List<NbtCompound> cassettes = List.of();

        public SyncCassettesPacket(int owned, List<NbtCompound> cassettes) {
            this.owned = owned;
            this.cassettes = cassettes;
        }

        // am i doing this weirdly?
        protected SyncCassettesPacket(PacketByteBuf buf) {
            EnvExecutor.runInEnv(Env.CLIENT, () -> () -> {
                this.owned = buf.readInt();
                int count = buf.readInt();
                this.cassettes = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    cassettes.add(buf.readNbt());
                }
            });
        }
        protected static void encode(SyncCassettesPacket packet, PacketByteBuf buf) {
            EnvExecutor.runInEnv(Env.SERVER, () -> () -> {
                buf.writeInt(packet.owned);
                buf.writeInt(packet.cassettes.size());
                packet.cassettes.forEach(buf::writeNbt);
            });
        }
        protected static void handle(SyncCassettesPacket packet, Supplier<PacketContext> ctx) {
            EnvExecutor.runInEnv(Env.CLIENT, () -> () -> {
                MinecraftClient.getInstance().execute(() -> {
                    ClientStorage.ownedCassettes = packet.owned;
                    ClientStorage.activeCassettes = packet.cassettes;
                });
            });
            EnvExecutor.runInEnv(Env.SERVER, () -> () -> {
                ServerPlayerEntity p = (ServerPlayerEntity)ctx.get().getPlayer();
                ((PlayerEntityMinterface)p).getCassetteState().sync(p);
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
            SyncCassettesPacket.class,
            SyncCassettesPacket::encode,
            SyncCassettesPacket::new,
            SyncCassettesPacket::handle
        );
    }
}
