package de.erdbeerbaerlp.bm_mf;

import net.minecraft.server.MinecraftServer;

import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public final class BluemapFrontiers {
    public static final String MOD_ID = "bluemapfrontiers";
    static MinecraftServer minecraftServer;
    private static Timer t;

    public static void init(MinecraftServer minecraftServer) {
        BluemapFrontiers.minecraftServer = minecraftServer;
        try {
            Config.instance().loadConfig();
        } catch (IOException e) {
            System.err.println("Error loading config file!");
            e.printStackTrace();
        }
        t = new Timer("Bluemap Frontiers",true);
        t.scheduleAtFixedRate(new FrontierTask(), 0, TimeUnit.SECONDS.toMillis(Config.instance().general.updateInterval));
    }
    public static void stop(MinecraftServer minecraftServer){
        t.cancel();
    }

}
