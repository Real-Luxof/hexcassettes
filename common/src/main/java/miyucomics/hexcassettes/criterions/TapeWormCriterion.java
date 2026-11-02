package miyucomics.hexcassettes.criterions;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

import static miyucomics.hexcassettes.Hexcassettes.id;

import java.util.Optional;

import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class TapeWormCriterion extends AbstractCriterion<TapeWormCriterion.Condition> {
    public static Identifier ID = id("tape_worm");
    public static final Codec<Condition> CODEC = new Codec<TapeWormCriterion.Condition>() {
        @Override
        public <T> DataResult<T> encode(Condition input, DynamicOps<T> ops, T prefix) {
            return DataResult.success(ops.empty());
        }

        @Override
        public <T> DataResult<Pair<Condition, T>> decode(DynamicOps<T> ops, T input) {
            return DataResult.success(new Pair<>(new Condition(), input));
        }
    };

    public static class Condition implements AbstractCriterion.Conditions {
        @Override public Optional<LootContextPredicate> player() { return Optional.empty(); }
    }

    @Override public Codec<Condition> getConditionsCodec() { return CODEC; }
    public void trigger(ServerPlayerEntity player) { trigger(player, anything -> true); }
}
