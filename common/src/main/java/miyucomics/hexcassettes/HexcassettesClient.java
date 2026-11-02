package miyucomics.hexcassettes;

import dev.architectury.event.events.client.ClientTickEvent;

import miyucomics.hexcassettes.client.CassetteScreen;
import miyucomics.hexcassettes.inits.HexcassettesNetworking;

import net.minecraft.client.option.KeyBinding;

import org.lwjgl.glfw.GLFW;

public class HexcassettesClient {
    // a field for fabric and forge client inits to pick up
    public static final KeyBinding CASSETTE_KEYBIND = new KeyBinding(
        "key.hexcassettes.ponder_cassette",
        GLFW.GLFW_KEY_G,
        "key.categories.hexcassettes"
    );

    public static void init() {
        HexcassettesNetworking.clientInit();
        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            if (CASSETTE_KEYBIND.wasPressed() && minecraft.currentScreen == null)
                minecraft.setScreen(new CassetteScreen());
        });
    }
}
