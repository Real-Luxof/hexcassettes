package miyucomics.hexcassettes.client;

import dev.architectury.networking.NetworkManager;

import miyucomics.hexcassettes.inits.HexcassettesNetworking;
import miyucomics.hexcassettes.inits.HexcassettesSounds;

import static miyucomics.hexcassettes.Hexcassettes.id;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class CassetteWidget extends ButtonWidget {
    public static final Identifier texture = id("textures/cassette.png");
    public static final Text freeText = Text.translatable("hexcassettes.free")
        .formatted(Formatting.DARK_GRAY)
        .formatted(Formatting.ITALIC);
    public final int index;
    public final int x; // not visible??
    public final int y; // wtfdym???

    protected CassetteWidget(int index, int x, int y) {
        super(x, y, 32, 16, Text.empty(), any -> {}, supplier -> supplier.get());
        this.index = index;
        this.x = x;
        this.y = y;
    }

    @Override
    public void renderWidget(DrawContext ctx, int mX, int mY, float delta) {
        //super.renderWidget(ctx, mX, mY, delta);
        ctx.drawTexture(texture, x, y, 0, 0, width, height, width, height);
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        ctx.drawTextWithShadow(
            textRenderer,
            isActive() ? ClientStorage.activeCassettes.get(index).display() : freeText,
            x + width,
            y + textRenderer.fontHeight / 2,
            -1
        );
    }
    
    @Override
    public void playDownSound(SoundManager soundManager) {
        soundManager.play(PositionedSoundInstance.master(
            isActive() ? HexcassettesSounds.CASSETTE_EJECT.get() : HexcassettesSounds.CASSETTE_FAIL.get(),
            1.0f,
            3.0f
        ));
    }

    @Override
    public void onPress() {
        if (!isActive()) return;
        NetworkManager.sendToServer(
            new HexcassettesNetworking.CassetteRemovalC2SPayload(
                ClientStorage.activeCassettes.get(index)
            )
        );
    }

    public boolean isActive() { return index < ClientStorage.activeCassettes.size(); }
}
