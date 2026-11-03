package miyucomics.hexcassettes.fabric;

import at.petrak.hexcasting.common.lib.hex.HexActions;

import static miyucomics.hexcassettes.Hexcassettes.id;
import static miyucomics.hexcassettes.Hexcassettes.init;
import static miyucomics.hexcassettes.Hexcassettes.LOGGER;

import miyucomics.hexcassettes.inits.HexcassettesActions;

import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registry;

public final class HexcassettesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

		LOGGER.info("Knock knock!");
        // Run our common setup.
        init();
		LOGGER.info("FABRIC!");

        HexcassettesActions.register((name, entry) -> Registry.register(
            HexActions.REGISTRY,
            id(name),
            entry
        ));
    }
}
