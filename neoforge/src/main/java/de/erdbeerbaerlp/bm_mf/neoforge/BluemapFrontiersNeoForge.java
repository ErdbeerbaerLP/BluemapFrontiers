package de.erdbeerbaerlp.bm_mf.neoforge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import de.erdbeerbaerlp.bm_mf.BluemapFrontiers;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

@Mod(BluemapFrontiers.MOD_ID)
public final class BluemapFrontiersNeoForge {
    public BluemapFrontiersNeoForge() {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void serverStarted(final ServerStartedEvent ev) {
        BluemapFrontiers.init(ev.getServer());

    }
    @SubscribeEvent
    public void serverStopping(final ServerStoppingEvent ev) {
        BluemapFrontiers.stop(ev.getServer());

    }
}
