package miyucomics.hexcassettes.casting.actions;

import at.petrak.hexcasting.api.casting.RenderedSpell;
import at.petrak.hexcasting.api.casting.castables.SpellAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.casting.mishaps.MishapBadCaster;

import java.util.List;

import miyucomics.hexcassettes.PlayerEntityMinterface;
import miyucomics.hexcassettes.data.CassetteState;

public class OpKillAll implements SpellAction {
    @Override public int getArgc() { return 0; }

    @Override
    public Result execute(List<? extends Iota> args, CastingEnvironment env) throws Mishap {
        if (!(env.getCastingEntity() instanceof PlayerEntityMinterface sp))
            throw new MishapBadCaster();
        return new SpellAction.Result(new Spell(sp.getCassetteState()), 0, List.of(), 1);
    }

    private static record Spell(CassetteState state) implements RenderedSpell {
        @Override public void cast(CastingEnvironment env) {
            state.hexes.clear();
        }
    }
}
