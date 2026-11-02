package miyucomics.hexcassettes.casting.actions;

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.NullIota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.casting.mishaps.MishapBadCaster;

import java.util.List;

import miyucomics.hexcassettes.PlayerEntityMinterface;

public class OpForetell implements ConstMediaAction {
    @Override public int getArgc() { return 1; }

    @Override
    public List<Iota> execute(List<? extends Iota> args, CastingEnvironment env) throws Mishap {
        if (!(env.getCastingEntity() instanceof PlayerEntityMinterface sp))
            throw new MishapBadCaster();

        var queued = sp.getCassetteState().hexes;
        Iota key = args.get(0);

        return List.of(queued.containsKey(key)
            ? new DoubleIota(queued.get(key).delay())
            : new NullIota());
    }
}
