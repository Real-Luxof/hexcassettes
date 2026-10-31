package miyucomics.hexcassettes.inits;

import dev.architectury.registry.registries.DeferredRegister;

import java.util.function.Supplier;

import miyucomics.hexcassettes.CassettesItem;

import static miyucomics.hexcassettes.Hexcassettes.MOD_ID;
import static miyucomics.hexcassettes.Hexcassettes.id;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;

// main looks ugly with this
public class HexcassettesItems {
    private static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(
        MOD_ID,
        RegistryKeys.ITEM
    );
    public static final Supplier<Item> CASSETTE_ITEM = ITEM_REGISTER.register(
        id("cassette"),
        CassettesItem::new
    );

    public static void init() {}
}
