package miyucomics.hexcassettes.casting.actions;

import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.api.casting.math.EulerPathFinder;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.casting.mishaps.MishapBadCaster;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota;
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;

import java.util.List;
import java.util.Stack;

import miyucomics.hexcassettes.PlayerEntityMinterface;
import miyucomics.hexcassettes.casting.env.CassetteCastEnv;
import miyucomics.hexcassettes.casting.mishaps.NoFreeCassettes;
import miyucomics.hexcassettes.data.CassetteState;
import miyucomics.hexcassettes.data.QueuedHex;

import net.minecraft.nbt.NbtCompound;

public class OpEnqueue implements Action {
    @Override
    public OperationResult operate(
        CastingEnvironment env,
        CastingImage img,
        SpellContinuation cont
    ) throws Mishap {
        if (!(env.getCastingEntity() instanceof PlayerEntityMinterface sp))
            throw new MishapBadCaster();

        CassetteState cassetteState = sp.getCassetteState();

        Stack<Iota> stack = new Stack<>();
        stack.addAll(img.getStack());
        int ogStackSize = stack.size();

        if (stack.size() < 2)
            throw new MishapNotEnoughArgs(2, stack.size());

        Iota keyIota = getKeyIota(stack, env.getWorld().getTime());
        NbtCompound key = IotaType.serialize(keyIota);

        Iota potentialDelay = stack.pop();
        if (
            !(potentialDelay instanceof DoubleIota delayIota) ||
            Math.abs(delayIota.getDouble() - Math.floor(delayIota.getDouble())) > DoubleIota.TOLERANCE
        )
            throw MishapInvalidIota.of(potentialDelay, ogStackSize - stack.size(), "int.positive");

        Iota potentialHex = stack.pop();
        // i'd do evaluatable but this is a port
        if (!(potentialHex instanceof ListIota listIota))
            throw MishapInvalidIota.of(potentialHex, ogStackSize - stack.size(), "list");

        if (
            !cassetteState.hexes.containsKey(key) &&
            cassetteState.hexes.keySet().size() >= cassetteState.owned
        )
            throw new NoFreeCassettes();

        int depth = 0;
        if (env instanceof CassetteCastEnv cce && cce.depth < 10)
            // this is going to loop?
            depth = cce.depth + 1;
        
        cassetteState.hexes.put(
            key,
            new QueuedHex(IotaType.serialize(listIota), (int)Math.floor(delayIota.getDouble()), depth)
        );

        if (ogStackSize - stack.size() == 3)
            stack.add(keyIota);

        return new OperationResult(
            img.copy(
                List.copyOf(stack),
                img.getParenCount(),
                img.getParenthesized(),
                img.getEscapeNext(),
                img.getOpsConsumed(),
                img.getUserData()
            ).withUsedOp(),
            List.of(),
            cont,
            HexEvalSounds.SPELL
        );
    }
    
    private Iota getKeyIota(Stack<Iota> stack, long seed) {
        Iota key = stack.pop();
        if (!(stack.peek() instanceof ListIota)) {
            if (stack.size() < 2)
                throw new MishapNotEnoughArgs(3, 2);
            return key;
        }

        stack.push(key);
        return new PatternIota(EulerPathFinder.findAltDrawing(
            HexPattern.fromAngles("qeqwqwqwqwqeqaweqqqqqwweeweweewqdwwewewwewweweww", HexDir.EAST),
            seed
        ));
    }
}
