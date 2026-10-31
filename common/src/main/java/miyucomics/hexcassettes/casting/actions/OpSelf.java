package miyucomics.hexcassettes.casting.actions;

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.iota.NullIota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;

import java.util.List;

import miyucomics.hexcassettes.casting.env.CassetteCastEnv;

public class OpSelf implements ConstMediaAction {
    @Override public int getArgc() { return 0; }

    @Override
    public List<Iota> execute(List<? extends Iota> args, CastingEnvironment env) throws Mishap {
        return List.of(env instanceof CassetteCastEnv cce
            ? IotaType.deserialize(cce.key, cce.getWorld())
            : new NullIota()
        );
    }
}
