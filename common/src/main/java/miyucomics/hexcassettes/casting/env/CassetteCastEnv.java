package miyucomics.hexcassettes.casting.env;

import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.casting.eval.env.PlayerBasedCastEnv;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import at.petrak.hexcasting.xplat.IXplatAbstractions;

import miyucomics.hexcassettes.inits.HexcassettesCriterions;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

public class CassetteCastEnv extends PlayerBasedCastEnv {
    public final NbtCompound key;
    public final int depth;
    public CassetteCastEnv(ServerPlayerEntity sp, Hand castingHand, NbtCompound key, int depth) {
        super(sp, castingHand);
        this.key = key;
        this.depth = depth;
        if (depth >= 9)
            HexcassettesCriterions.QUINE_CRITERION.trigger(sp);
    }
    public CassetteCastEnv(ServerPlayerEntity sp, Hand castingHand, NbtCompound key) {
        this(sp, castingHand, key, 0);
    }

    @Override public Hand getCastingHand() { return castingHand; }
    @Override public void produceParticles(ParticleSpray particles, FrozenPigment pigment) {}
    @Override public FrozenPigment getPigment() {
        return IXplatAbstractions.INSTANCE.getPigment(caster);
    }

    @Override
    public long extractMediaEnvironment(long costLeft, boolean simulate) {
        if (caster.isCreative()) return 0L;
        return extractMediaFromInventory(costLeft, true, simulate);
    }
}
