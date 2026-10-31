package miyucomics.hexcassettes.casting.actions;

import java.util.List;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;

import miyucomics.hexcassettes.PlayerEntityMinterface;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class OpInspect implements ConstMediaAction {
    @Override public int getArgc() { return 2; }

    @Override
    public List<Iota> execute(List<? extends Iota> args, CastingEnvironment env) throws Mishap {
        ServerPlayerEntity player = OperatorUtils.getPlayer(args, 0, getArgc());
        NbtCompound key = IotaType.serialize(args.get(1));

        return List.of(new BooleanIota(
            ((PlayerEntityMinterface)player).getCassetteState().hexes.containsKey(key)
        ));
    }
}
