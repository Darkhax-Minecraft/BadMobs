package net.darkhax.badmobs;

import net.darkhax.badmobs.config.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("badmobs")
public class BadMobs {

    public static final Logger LOG = LogManager.getLogger("Bad Mobs");
    private Configuration config;

    public BadMobs() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onLoadComplete);
        MinecraftForge.EVENT_BUS.addListener(this::onSpawnFinalized);
    }

    private void onLoadComplete(FMLLoadCompleteEvent event) {

        event.enqueueWork(() -> config = new Configuration());
    }

    private void onSpawnFinalized(MobSpawnEvent.FinalizeSpawn event) {

        if (config != null && !config.allowSpawn(event.getEntity(), event.getSpawnType())) {
            event.setSpawnCancelled(true);
        }
    }
}