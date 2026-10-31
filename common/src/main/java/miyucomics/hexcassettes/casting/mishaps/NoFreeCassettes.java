package miyucomics.hexcassettes.casting.mishaps;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.pigment.FrozenPigment;

import static miyucomics.hexcassettes.Hexcassettes.MOD_ID;

import java.util.List;

import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;

public class NoFreeCassettes extends Mishap {
    @Override
    public FrozenPigment accentColor(CastingEnvironment env, Context errorCtx) {
        return dyeColor(DyeColor.RED);
    }

    @Override
    public Text errorMessage(CastingEnvironment env, Context errorCtx) {
        return error(MOD_ID + ":no_free_cassettes");
    }

    @Override
    public void execute(CastingEnvironment env, Context errorCtx, List<Iota> stack) {}
}
