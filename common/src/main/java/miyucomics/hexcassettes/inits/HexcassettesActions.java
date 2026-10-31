package miyucomics.hexcassettes.inits;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.HexRegistries;

import dev.architectury.registry.registries.DeferredRegister;

import miyucomics.hexcassettes.casting.actions.*;

import static miyucomics.hexcassettes.Hexcassettes.MOD_ID;
import static miyucomics.hexcassettes.Hexcassettes.id;

public class HexcassettesActions {
    public static void init() {
		register("enqueue", "qeqwqwqwqwqeqaweqqqqqwweeweweewqdwwewewwewweweww", HexDir.EAST, new OpEnqueue());
		register("dequeue", "eqeweweweweqedwqeeeeewwqqwqwqqweawwqwqwwqwwqwqww", HexDir.WEST, new OpDequeue());
		register("killall", "eqeweweweweqedwqeeeeewwqqwqwqqw", HexDir.WEST, new OpKillAll());
		register("specs", "qeqwqwqwqwqeqaweqqqqq", HexDir.EAST, new OpSpecs());
		register("busy", "qeqwqwqwqwqeqaweqqqqqaww", HexDir.EAST, new OpBusy());
		register("inspect", "eqeweweweweqedwqeeeee", HexDir.WEST, new OpInspect());
		register("foretell", "eqeweweweweqedwqeeeeedww", HexDir.WEST, new OpForetell());
		register("self", "qeqwqwqwqwqeqaweqqqqqwweeweweew", HexDir.EAST, new OpSelf());
    }

    private static final DeferredRegister<ActionRegistryEntry> ACTIONS_REGISTRY = DeferredRegister
        .create(
            MOD_ID,
            HexRegistries.ACTION
        );
    private static void register(String name, String signature, HexDir startDir, Action action) {
        ACTIONS_REGISTRY.register(
            id(name),
            () -> new ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), action)
        );
    }
}
