package miyucomics.hexcassettes.casting.actions;

import at.petrak.hexcasting.api.casting.RenderedSpell;
import at.petrak.hexcasting.api.casting.castables.SpellAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.casting.mishaps.MishapBadCaster;

import java.util.List;

import miyucomics.hexcassettes.PlayerEntityMinterface;

public class OpDequeue implements SpellAction {
    @Override public int getArgc() { return 1; }

    @Override
    public Result execute(List<? extends Iota> args, CastingEnvironment env) throws Mishap {
        if (!(env.getCastingEntity() instanceof PlayerEntityMinterface))
            throw new MishapBadCaster();
        return new SpellAction.Result(new Spell(args.get(0)), 0, List.of(), 1);
    }

    // is something wrong? you look like you've seen a ghost.
    private static record Spell(Iota key) implements RenderedSpell {
        @Override public void cast(CastingEnvironment env) {
            ((PlayerEntityMinterface)env.getCastingEntity()).getCassetteState().hexes.remove(key);
        }
    }
}
