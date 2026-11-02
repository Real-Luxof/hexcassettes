package miyucomics.hexcassettes.data;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;

import dev.architectury.networking.NetworkManager;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import miyucomics.hexcassettes.inits.HexcassettesNetworking;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.network.ServerPlayerEntity;

public class CassetteState {
    public int owned = 0;
    public HashMap<Iota, QueuedHex> hexes = new HashMap<>();
    private Set<Iota> previouslyActiveSlots = Set.of();

    public CassetteState() {}
    public CassetteState(NbtCompound serialized) {
        owned = serialized.getInt("owned");
        serialized.getList("hexes", NbtElement.COMPOUND_TYPE).forEach(ele -> {
            NbtCompound nbt = (NbtCompound)ele;
            hexes.put(
                IotaType.TYPED_CODEC.decode(NbtOps.INSTANCE, nbt.get("hex")).getOrThrow().getFirst(),
                new QueuedHex(nbt.getCompound("hex"))
            );
        });
    }

    public void sync(ServerPlayerEntity sp) {
        NetworkManager.sendToPlayer(
            sp,
            new HexcassettesNetworking.SyncCassettesS2CPacket(owned, List.copyOf(hexes.keySet()))
        );
    }

    public void tick(ServerPlayerEntity sp) {
        // if only Map.flatMap()...
        hexes.forEach(
            (key, hex) -> {
                int delay = hex.delay();
                if (delay <= 1) {
                    hexes.remove(key);
                    hex.cast(sp, key);
                } else
                    hexes.put(key, new QueuedHex(hex.hex(), hex.delay() - 1, hex.depth()));
            }
        );

        Set<Iota> activeIndices = hexes.keySet();
        if (!previouslyActiveSlots.equals(activeIndices)) {
            sync(sp);
            previouslyActiveSlots = Set.copyOf(activeIndices);
        }
    }

    public NbtCompound serialize() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("owned", owned);
        NbtList hexesNbt = new NbtList();
        hexes.forEach((key, hex) -> {
            NbtCompound entryNbt = new NbtCompound();
            entryNbt.put(
                "key",
                IotaType.TYPED_CODEC.encode(key, NbtOps.INSTANCE, new NbtCompound()).getOrThrow()
            );
            entryNbt.put("hex", hex.serialize());
            hexesNbt.add(entryNbt);
        });
        nbt.put("hexes", hexesNbt);
        return nbt;
    }
}
