package de.erdbeerbaerlp.bm_mf.forge;


import de.erdbeerbaerlp.bm_mf.BluemapFrontiers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(BluemapFrontiers.MOD_ID)
public final class BluemapFrontiersForge {
    public BluemapFrontiersForge() {
        MinecraftForge.EVENT_BUS.register(this);
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
