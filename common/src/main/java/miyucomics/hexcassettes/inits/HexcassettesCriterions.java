package miyucomics.hexcassettes.inits;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;

import miyucomics.hexcassettes.criterions.FullArsenalCriterion;
import miyucomics.hexcassettes.criterions.QuineCriterion;
import miyucomics.hexcassettes.criterions.TapeWormCriterion;

import static miyucomics.hexcassettes.Hexcassettes.MOD_ID;

import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.registry.RegistryKeys;

public class HexcassettesCriterions {
    public static final DeferredRegister<Criterion<?>> CRITERION_REGISTER = DeferredRegister.create(
        MOD_ID,
        RegistryKeys.CRITERION
    );
    public static final RegistrySupplier<FullArsenalCriterion> FULL_ARSENAL_CRITERION = CRITERION_REGISTER.register(FullArsenalCriterion.ID, FullArsenalCriterion::new);
    public static final RegistrySupplier<QuineCriterion> QUINE_CRITERION = CRITERION_REGISTER.register(QuineCriterion.ID, QuineCriterion::new);
    public static final RegistrySupplier<TapeWormCriterion> TAPE_WORM_CRITERION = CRITERION_REGISTER.register(TapeWormCriterion.ID, TapeWormCriterion::new);

    public static void init() {
        CRITERION_REGISTER.register();
    }
}
