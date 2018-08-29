package net.darkhax.badmobs.handler;

import net.darkhax.badmobs.BadMobs;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Events in this event handler are only applied when {@link ConfigurationHandler#removeAggressively} is true.
 */
public class SpawnEventHandler {
    
    @SubscribeEvent
    public void onEntityJoinWorld (EntityJoinWorldEvent event) {

        final Entity entity = event.getEntity();

        if (entity != null && BadMobs.isBlacklisted(entity)) {

            if (entity.hasCustomName() && !ConfigurationHandler.removeNamedMobs) {
                
                return;
            }
            
            event.getWorld().removeEntity(entity);
            event.setCanceled(true);
        }
    }
}