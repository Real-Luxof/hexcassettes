package miyucomics.hexcassettes.forge;

import dev.architectury.platform.forge.EventBuses;

import miyucomics.hexcassettes.Hexcassettes;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Hexcassettes.MOD_ID)
public final class HexcassettesForge {
    public HexcassettesForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(Hexcassettes.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        Hexcassettes.init();
    }
}
