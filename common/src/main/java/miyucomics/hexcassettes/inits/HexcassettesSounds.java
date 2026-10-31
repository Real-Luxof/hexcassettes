package miyucomics.hexcassettes.inits;

import dev.architectury.registry.registries.DeferredRegister;

import java.util.function.Supplier;

import static miyucomics.hexcassettes.Hexcassettes.MOD_ID;
import static miyucomics.hexcassettes.Hexcassettes.id;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class HexcassettesSounds {
    private static final DeferredRegister<SoundEvent> SOUND_REGISTER = DeferredRegister.create(
        MOD_ID,
        RegistryKeys.SOUND_EVENT
    );
    public static final Supplier<SoundEvent> CASSETTE_EJECT = register("cassette_eject");
    public static final Supplier<SoundEvent> CASSETTE_FAIL = register("cassette_fail");
    public static final Supplier<SoundEvent> CASSETTE_INSERT = register("cassette_insert");
    public static final Supplier<SoundEvent> CASSETTE_LOOP = register("cassette_loop");

    public static void init() {}

    public static Supplier<SoundEvent> register(String name) {
        Identifier id = id(name);
        return SOUND_REGISTER.register(id, () -> SoundEvent.of(id));
    }
}
