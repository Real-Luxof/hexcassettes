package miyucomics.hexcassettes;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dev.architectury.platform.Platform;

import java.io.IOException;
import java.nio.file.Files;

import static miyucomics.hexcassettes.Hexcassettes.LOGGER;

public class HexcassettesConfiguration {
    private static final Path CONFIG_PATH = Platform.getConfigFolder().resolve("hexcassettes.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static final HexcassettesConfiguration INSTANCE = load();

    public final int maxCassettes;
    public HexcassettesConfiguration(int maxCassettes) {
        this.maxCassettes = maxCassettes;
    }
    public HexcassettesConfiguration() { this(6); }
    
    private static HexcassettesConfiguration load() {
        try {
            if (!Files.exists(CONFIG_PATH)) {
                HexcassettesConfiguration config = new HexcassettesConfiguration();
                save(config);
                return config;
            }
            return gson.fromJson(Files.readString(CONFIG_PATH), HexcassettesConfiguration.class);
        } catch (IOException e) {
            LOGGER.error("Failed to load config!", e);
            HexcassettesConfiguration config = new HexcassettesConfiguration();
            save(config);
            return config;
        }
    }

    protected static void save(HexcassettesConfiguration config) {
        try {
            Files.writeString(CONFIG_PATH, gson.toJson(config));
        } catch (IOException e) {
            LOGGER.error("Failed to save config!", e);
        }
    }
}
