package miyucomics.hexcassettes.client;

import java.util.List;

import net.minecraft.nbt.NbtCompound;

public class ClientStorage {
    public static int ownedCassettes = 0;
    public static List<NbtCompound> activeCassettes = List.of();
}
