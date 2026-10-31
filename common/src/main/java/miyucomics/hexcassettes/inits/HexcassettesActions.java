package miyucomics.hexcassettes.inits;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;

import miyucomics.hexcassettes.casting.actions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class HexcassettesActions {

    private static final Map<String, ActionRegistryEntry> ACTIONS_REGISTRY = new HashMap<>();

    static {
		register("enqueue", "qeqwqwqwqwqeqaweqqqqqwweeweweewqdwwewewwewweweww", HexDir.EAST, new OpEnqueue());
		register("dequeue", "eqeweweweweqedwqeeeeewwqqwqwqqweawwqwqwwqwwqwqww", HexDir.WEST, new OpDequeue());
		register("killall", "eqeweweweweqedwqeeeeewwqqwqwqqw", HexDir.WEST, new OpKillAll());
		register("specs", "qeqwqwqwqwqeqaweqqqqq", HexDir.EAST, new OpSpecs());
		register("busy", "qeqwqwqwqwqeqaweqqqqqaww", HexDir.EAST, new OpBusy());
		register("inspect", "eqeweweweweqedwqeeeee", HexDir.WEST, new OpInspect());
		register("foretell", "eqeweweweweqedwqeeeeedww", HexDir.WEST, new OpForetell());
		register("self", "qeqwqwqwqwqeqaweqqqqqwweeweweew", HexDir.EAST, new OpSelf());
    }

    public static void init() {}

    public static void register(BiConsumer<String, ActionRegistryEntry> consumer) {
        ACTIONS_REGISTRY.forEach(consumer::accept);
    }

    private static void register(String name, String signature, HexDir startDir, Action action) {
        ACTIONS_REGISTRY.put(
            name,
            new ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), action)
        );
    }
}
