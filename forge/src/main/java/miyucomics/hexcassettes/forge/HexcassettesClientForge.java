package miyucomics.hexcassettes.forge;

import miyucomics.hexcassettes.HexcassettesClient;

import static miyucomics.hexcassettes.Hexcassettes.MOD_ID;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class HexcassettesClientForge {
    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(HexcassettesClient.CASSETTE_KEYBIND);
    }
}
