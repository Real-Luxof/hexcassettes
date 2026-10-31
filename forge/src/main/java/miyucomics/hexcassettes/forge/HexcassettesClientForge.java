package miyucomics.hexcassettes.forge;

import miyucomics.hexcassettes.HexcassettesClient;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class HexcassettesClientForge {
    @SubscribeEvent
    public static void registerCassetteScreenKeybind(RegisterKeyMappingsEvent event) {
        event.register(HexcassettesClient.CASSETTE_KEYBIND);
    }
}
