package miyucomics.hexcassettes.criterions;

import com.google.gson.JsonObject;

import static miyucomics.hexcassettes.Hexcassettes.id;

import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class TapeWormCriterion extends AbstractCriterion<TapeWormCriterion.Condition> {
    public static Identifier ID = id("tape_worm");

    public static class Condition extends AbstractCriterionConditions {
        public Condition() { super(ID, LootContextPredicate.EMPTY); }
    }

    @Override public Identifier getId() { return ID; }
    @Override
    protected Condition conditionsFromJson(JsonObject obj, LootContextPredicate playerPredicate,
            AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new Condition();
    }

    public void trigger(ServerPlayerEntity player) {
        trigger(player, anything -> true);
    }
}
