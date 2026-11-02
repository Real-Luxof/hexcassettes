package miyucomics.hexcassettes.fabric.client;

import miyucomics.hexcassettes.HexcassettesClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public final class HexcassettesFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HexcassettesClient.init();
        KeyBindingHelper.registerKeyBinding(HexcassettesClient.CASSETTE_KEYBIND);
    }
}
