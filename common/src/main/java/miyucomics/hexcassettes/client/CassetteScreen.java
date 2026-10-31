package miyucomics.hexcassettes.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class CassetteScreen extends Screen {
    public CassetteScreen() {
        super(Text.of("Cassettes"));
    }

    @Override
    public void init() {
        for (int i = 0; i < ClientStorage.ownedCassettes; i++) {
            addDrawableChild(new CassetteWidget(i, 16, i * 25 + 16));
        }
        super.init();
    }

    @Override
    public void render(DrawContext ctx, int mX, int mY, float delta) {
        ctx.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
		super.render(ctx, mX, mY, delta);
    }

    @Override public boolean shouldPause() { return false; }
}
