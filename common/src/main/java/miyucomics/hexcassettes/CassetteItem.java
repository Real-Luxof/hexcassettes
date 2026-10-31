package miyucomics.hexcassettes;

import miyucomics.hexcassettes.data.CassetteState;
import miyucomics.hexcassettes.inits.HexcassettesCriterions;
import miyucomics.hexcassettes.inits.HexcassettesSounds;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

public class CassetteItem extends Item {
    public CassetteItem() {
        super(
            new Item.Settings()
                .maxCount(1)
                .rarity(Rarity.UNCOMMON)
                .food(new FoodComponent.Builder().alwaysEdible().build())
        );
    }
    
    @Override public int getMaxUseTime(ItemStack stack) { return 100; }
    @Override public SoundEvent getEatSound() { return HexcassettesSounds.CASSETTE_LOOP.get(); }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.isClient) {
            world.playSound(
                user.getX(), user.getY(), user.getZ(),
                HexcassettesSounds.CASSETTE_INSERT.get(),
                SoundCategory.MASTER,
                5f,
                1f,
                false
            );
            return super.finishUsing(stack, world, user);
        }

        if (!(user instanceof ServerPlayerEntity sp)) return super.finishUsing(stack, world, user);

        int maxCassettes = HexcassettesConfiguration.INSTANCE.maxCassettes;
        CassetteState cassetteData = ((PlayerEntityMinterface)sp).getCassetteState();

        if (cassetteData.owned < maxCassettes) {
            HexcassettesCriterions.TAPE_WORM_CRITERION.trigger(sp);
            cassetteData.owned += 1;
            if (cassetteData.owned == maxCassettes)
                HexcassettesCriterions.FULL_ARSENAL_CRITERION.trigger(sp);
            cassetteData.sync(sp);
        }

        return super.finishUsing(stack, world, user);
    }
}
