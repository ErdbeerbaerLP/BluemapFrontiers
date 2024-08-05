package de.erdbeerbaerlp.bm_mf.fabric;

import de.erdbeerbaerlp.bm_mf.BluemapFrontiers;
import net.fabricmc.api.ModInitializer;

import de.erdbeerbaerlp.bm_mf.fabriclike.BluemapFrontiersFabricLike;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public final class BluemapFrontiersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(BluemapFrontiersFabricLike::init);
        ServerLifecycleEvents.SERVER_STOPPING.register(BluemapFrontiers::stop);
    }
}