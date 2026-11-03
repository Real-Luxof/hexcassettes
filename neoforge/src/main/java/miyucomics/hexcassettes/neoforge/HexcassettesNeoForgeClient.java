package miyucomics.hexcassettes.neoforge;

import miyucomics.hexcassettes.HexcassettesClient;

import static miyucomics.hexcassettes.Hexcassettes.MOD_ID;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@Mod(value = MOD_ID, dist = Dist.CLIENT)
public class HexcassettesNeoForgeClient {
    public HexcassettesNeoForgeClient(
        IEventBus modBus
    ) {
        HexcassettesClient.init();

        modBus.addListener(HexcassettesNeoForgeClient::registerBindings);
    }

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(HexcassettesClient.CASSETTE_KEYBIND);
    }
}
