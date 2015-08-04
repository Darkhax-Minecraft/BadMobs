package net.darkhax.badmobs.handler;

import net.minecraft.entity.EntityList;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntitySpawningHandler {
    
    @SubscribeEvent
    public void onEntityJoinWorld (EntityJoinWorldEvent event) {
    
        if (event.entity != null && !ConfigurationHandler.bannedEntities.isEmpty() && ConfigurationHandler.bannedEntities.contains(EntityList.getEntityString(event.entity))) {
            
            if (ConfigurationHandler.killMode)
                event.world.removeEntity(event.entity);
            
            event.setCanceled(true);
        }
    }
}
