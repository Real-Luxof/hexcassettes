package miyucomics.hexcassettes.forge;

import dev.architectury.platform.forge.EventBuses;
import miyucomics.hexcassettes.inits.HexcassettesActions;

import static miyucomics.hexcassettes.Hexcassettes.LOGGER;
import static miyucomics.hexcassettes.Hexcassettes.MOD_ID;
import static miyucomics.hexcassettes.Hexcassettes.init;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.common.lib.HexRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

@Mod(MOD_ID)
@EventBusSubscriber
public final class HexcassettesForge {
    public static DeferredRegister<ActionRegistryEntry> ACTION_REGISTRY = DeferredRegister.create(
        HexRegistries.ACTION,
        MOD_ID
    );

    static {
        HexcassettesActions.register(
            (string, entry) -> ACTION_REGISTRY.register(string, () -> entry)
        );
    }

    public HexcassettesForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        LOGGER.info("Knock knock...");
        init();
        LOGGER.info("FORGE!");

        ACTION_REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
