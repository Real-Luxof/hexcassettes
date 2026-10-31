package miyucomics.hexcassettes.inits;

import static at.petrak.hexcasting.mixin.accessor.CriteriaTriggersAccessor.hex$register;

import miyucomics.hexcassettes.criterions.FullArsenalCriterion;
import miyucomics.hexcassettes.criterions.QuineCriterion;
import miyucomics.hexcassettes.criterions.TapeWormCriterion;

public class HexcassettesCriterions {
    public static final FullArsenalCriterion FULL_ARSENAL_CRITERION = hex$register(new FullArsenalCriterion());
    public static final QuineCriterion QUINE_CRITERION = hex$register(new QuineCriterion());
    public static final TapeWormCriterion TAPE_WORM_CRITERION = hex$register(new TapeWormCriterion());

    public static void init() {}
}
