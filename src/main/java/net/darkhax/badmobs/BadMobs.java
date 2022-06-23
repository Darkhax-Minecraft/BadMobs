package net.darkhax.badmobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.darkhax.badmobs.config.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("badmobs")
public class BadMobs {
    
    public static final Logger LOG = LogManager.getLogger("Bad Mobs");
    private Configuration config;
    
    public BadMobs() {
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }
    
    private void setup (FMLLoadCompleteEvent event) {
        
        this.config = new Configuration();
        MinecraftForge.EVENT_BUS.addListener(this::checkSpawn);
        MinecraftForge.EVENT_BUS.addListener(this::specialSpawn);
        MinecraftForge.EVENT_BUS.addListener(this::entityJoinWorld);
    }
    
    private void checkSpawn (CheckSpawn event) {
        
        if (!this.config.allowSpawn(event.getEntity(), event.getSpawnReason())) {
            
            event.getEntity().remove();
            event.setResult(Result.DENY);
        }
    }
    
    private void specialSpawn (SpecialSpawn event) {
        
        if (!this.config.allowSpawn(event.getEntity(), event.getSpawnReason())) {
            
            event.getEntity().remove();
            event.setCanceled(true);
        }
    }
    
    private void entityJoinWorld (EntityJoinWorldEvent event) {
        
        if (!this.config.allowSpawn(event.getEntity(), null)) {
            
            event.getEntity().remove();
            event.setCanceled(true);
        }
    }
}