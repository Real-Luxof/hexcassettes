package miyucomics.hexcassettes;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.registry.RegistryKeys;

public final class Hexcassettes {
    public static final String MOD_ID = "hexcassettes";

    public static void init() {
        DeferredRegister.create(MOD_ID, RegistryKeys.ITEM);
        // Write common init code here.
    }
}
