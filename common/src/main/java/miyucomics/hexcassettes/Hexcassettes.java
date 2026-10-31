package miyucomics.hexcassettes;

import miyucomics.hexcassettes.inits.HexcassettesCriterions;
import miyucomics.hexcassettes.inits.HexcassettesItems;
import miyucomics.hexcassettes.inits.HexcassettesSounds;

import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Hexcassettes {
    public static final String MOD_ID = "hexcassettes";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        HexcassettesItems.init();
        HexcassettesCriterions.init();
        HexcassettesSounds.init();
    }

    public static Identifier id(String name) {
        return new Identifier(MOD_ID, name);
    }
}
