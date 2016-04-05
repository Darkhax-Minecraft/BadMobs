package net.darkhax.badmobs.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntitySpawningHandler {
    
    @SubscribeEvent
    public void onEntityJoinWorld (EntityJoinWorldEvent event) {
        
        Entity entity = event.getEntity();
        if (entity != null && !ConfigurationHandler.bannedEntities.isEmpty() && ConfigurationHandler.bannedEntities.contains(EntityList.getEntityString(entity))) {
            
            if (ConfigurationHandler.killMode)
                event.getWorld().removeEntity(entity);
                
            event.setCanceled(true);
        }
    }
}
