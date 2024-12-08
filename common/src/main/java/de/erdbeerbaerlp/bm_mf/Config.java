package de.erdbeerbaerlp.bm_mf;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlComment;
import com.moandjiezana.toml.TomlIgnore;
import com.moandjiezana.toml.TomlWriter;

import java.io.File;
import java.io.IOException;

public class Config {
    /**
     * Path to the config file
     */
    @TomlIgnore
    public static File configFile = new File("./config/Bluemap-Mapfrontiers.toml");
    @TomlIgnore
    private static Config INSTANCE;
    public static Config instance() {
        return INSTANCE;
    }
    static {

        //First instance of the Config
        INSTANCE = new Config();
    }

    @TomlComment("Configuration of the Bluemap-Mapfrontiers mod")
    public General general = new General();
    @TomlComment("Configuration of the Bluemap-Mapfrontiers mod")
    public FlatMode flatMode = new FlatMode();
    @TomlComment("Configuration of the Bluemap-Mapfrontiers mod")
    public VolumeMode volumeMode = new VolumeMode();


    public void loadConfig() throws IOException, IllegalStateException {
        if (!configFile.exists()) {
            INSTANCE = new Config();
            INSTANCE.saveConfig();
            return;
        }
        INSTANCE = new Toml().read(configFile).to(Config.class);
        INSTANCE.saveConfig(); //Re-write the config so new values get added after updates
    }

    public void saveConfig() throws IOException {
        if (!configFile.exists()) {
            if (!configFile.getParentFile().exists()) configFile.getParentFile().mkdirs();
            configFile.createNewFile();
        }
        final TomlWriter w = new TomlWriter.Builder()
                .indentValuesBy(2)
                .indentTablesBy(4)
                .padArrayDelimitersBy(2)
                .build();
        w.write(this, configFile);
    }

    public static class General {

        @TomlComment("Marker set name")
        public String markerSet = "MapFrontiers";

        @TomlComment({"Render type to use", "FLAT: Render all regions in a flat plane.", "VOLUME: Render all regions as volumes"})
        public RenderType renderType = RenderType.FLAT;

        @TomlComment("Alpha-Value of the infill. 0-1")
        public float infillAlpha = 0.24f;
        @TomlComment("Alpha-Value of the outer lines. 0-1")
        public float lineAlpha = 0.874f;

        @TomlComment("Should the regions always be visible through blocks?")
        public boolean alwaysOnTop = true;

        @TomlComment("Interval in seconds to update the marker-set")
        public long updateInterval = 5;
    }

    public static class VolumeMode{
        @TomlComment("Bottom Y level of the markers")
        public float minY = -64;
        @TomlComment("Top Y level of the markers")
        public float maxY = 320;
    }
    public static class FlatMode{
        @TomlComment("Y level of the markers")
        public float yLevel = 64;
    }
}
