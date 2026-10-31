package miyucomics.hexcassettes;

import dev.architectury.event.events.common.TickEvent;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;

import miyucomics.hexcassettes.inits.HexcassettesActions;
import miyucomics.hexcassettes.inits.HexcassettesCriterions;
import miyucomics.hexcassettes.inits.HexcassettesItems;
import miyucomics.hexcassettes.inits.HexcassettesNetworking;
import miyucomics.hexcassettes.inits.HexcassettesSounds;

import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Hexcassettes {
    public static final String MOD_ID = "hexcassettes";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        LOGGER.info("Who's there?");

        HexcassettesActions.init();
        HexcassettesCriterions.init();
        HexcassettesItems.init();
        HexcassettesNetworking.init();
        HexcassettesSounds.init();
        EnvExecutor.runInEnv(Env.CLIENT, () -> HexcassettesClient::init);
        
        TickEvent.SERVER_PRE.register(server -> {
            server.getPlayerManager().getPlayerList().forEach(
                sp -> ((PlayerEntityMinterface)sp).getCassetteState().tick(sp)
            );
        });
    }

    public static Identifier id(String name) {
        return new Identifier(MOD_ID, name);
    }
}
