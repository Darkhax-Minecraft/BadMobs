package net.darkhax.badmobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.darkhax.badmobs.config.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod("badmobs")
public class BadMobs {
    
    public static final Logger LOG = LogManager.getLogger("Bad Mobs");
    private final Configuration config;
    
    public BadMobs() {
        
        this.config = new Configuration();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, config.getSpec());
        MinecraftForge.EVENT_BUS.addListener(this::checkSpawn);
        MinecraftForge.EVENT_BUS.addListener(this::specialSpawn);
        MinecraftForge.EVENT_BUS.addListener(this::entityJoinWorld);
    }
    
    private void checkSpawn (CheckSpawn event) {
        
        if (!this.config.allowSpawn(event.getEntity(), event.getSpawnReason(), false)) {
            
            event.getEntity().remove();
            event.setResult(Result.DENY);
        }
    }
    
    private void specialSpawn (SpecialSpawn event) {
        
        if (!this.config.allowSpawn(event.getEntity(), event.getSpawnReason(), false)) {
            
            event.getEntity().remove();
            event.setCanceled(true);
        }
    }
    
    private void entityJoinWorld (EntityJoinWorldEvent event) {
        
        if (!this.config.allowSpawn(event.getEntity(), null, true)) {
            
            event.getEntity().remove();
            event.setCanceled(true);
            LOG.info("Forcefully removed {}.", event.getEntity());
        }
    }
}