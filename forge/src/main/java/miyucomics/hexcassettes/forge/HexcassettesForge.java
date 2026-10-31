package miyucomics.hexcassettes.forge;

import dev.architectury.platform.forge.EventBuses;

import static miyucomics.hexcassettes.Hexcassettes.LOGGER;
import static miyucomics.hexcassettes.Hexcassettes.MOD_ID;
import static miyucomics.hexcassettes.Hexcassettes.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MOD_ID)
@EventBusSubscriber
public final class HexcassettesForge {
    public HexcassettesForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        LOGGER.info("Knock knock...");
        init();
        LOGGER.info("FORGE!");
    }
}
