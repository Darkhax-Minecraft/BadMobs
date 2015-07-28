package net.darkhax.badmobs.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityList;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EntitySpawningHandler {
    
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        
        if (event.entity != null && !ConfigurationHandler.bannedEntities.isEmpty() && ConfigurationHandler.bannedEntities.contains(EntityList.getEntityString(event.entity)))
            event.setCanceled(true);
    }
}
