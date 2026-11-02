package miyucomics.hexcassettes.casting.actions;

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.casting.mishaps.MishapBadCaster;

import java.util.List;

import miyucomics.hexcassettes.PlayerEntityMinterface;

public class OpBusy implements ConstMediaAction {
    @Override public int getArgc() { return 0; }

    @Override
    public List<Iota> execute(List<? extends Iota> args, CastingEnvironment env) throws Mishap {
        if (!(env.getCastingEntity() instanceof PlayerEntityMinterface sp))
            throw new MishapBadCaster();

        return sp.getCassetteState().hexes.keySet().stream().toList();
    }
}
