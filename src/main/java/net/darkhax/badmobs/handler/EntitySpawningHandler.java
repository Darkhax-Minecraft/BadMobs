package net.darkhax.badmobs.handler;

import net.darkhax.badmobs.BadMobs;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntitySpawningHandler {

    @SubscribeEvent
    public void onEntityJoinWorld (EntityJoinWorldEvent event) {

        final Entity entity = event.getEntity();

        if (entity != null && BadMobs.isBlacklisted(entity)) {

            if (ConfigurationHandler.killMode) {

                event.getWorld().removeEntity(entity);
            }

            event.setCanceled(true);
        }
    }
}
