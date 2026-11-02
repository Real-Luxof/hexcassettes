package miyucomics.hexcassettes.neoforge;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.common.lib.HexRegistries;

import miyucomics.hexcassettes.inits.HexcassettesActions;

import static miyucomics.hexcassettes.Hexcassettes.init;
import static miyucomics.hexcassettes.Hexcassettes.MOD_ID;

import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(MOD_ID)
public final class HexcassettesNeoForge {
    public static final DeferredRegister<ActionRegistryEntry> ACTION_REGISTRY = DeferredRegister.create(
        HexRegistries.ACTION,
        MOD_ID
    );
    public HexcassettesNeoForge() {
        // Run our common setup.
        init();
        
        HexcassettesActions.register((name, entry) -> ACTION_REGISTRY.register(name, () -> entry));
    }
}
