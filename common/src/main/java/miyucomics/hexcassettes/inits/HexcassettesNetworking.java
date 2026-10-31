package miyucomics.hexcassettes.inits;

import java.util.ArrayList;
import java.util.List;

import miyucomics.hexcassettes.PlayerEntityMinterface;
import miyucomics.hexcassettes.client.ClientStorage;

import static miyucomics.hexcassettes.Hexcassettes.id;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class HexcassettesNetworking {
    public static final Identifier CASSETTE_REMOVE = id("cassette_remove");
    public static final Identifier SYNC_CASSETTES = id("sync_cassettes");

    public static void clientInit() {
        ClientPlayConnectionEvents.JOIN.register((a, b, c) -> ClientPlayNetworking.send(
            SYNC_CASSETTES,
            PacketByteBufs.empty()
        ));
        ClientPlayNetworking.registerGlobalReceiver(SYNC_CASSETTES, (client, a, packet, b) -> {
            int owned = packet.readInt();
            int count = packet.readInt();
            List<NbtCompound> cassettes = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                cassettes.add(packet.readNbt());
            }
            client.execute(() -> {
                ClientStorage.ownedCassettes = owned;
                ClientStorage.activeCassettes = cassettes;
            });
        });
    }

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(CASSETTE_REMOVE, (a, sp, b, packet, c) -> {
            ((PlayerEntityMinterface)sp).getCassetteState().hexes.remove(packet.readNbt());
        });
        ServerPlayNetworking.registerGlobalReceiver(SYNC_CASSETTES, (a, sp, b, c, d) -> {
            ((PlayerEntityMinterface)sp).getCassetteState().sync(sp);
        });
    }
}
