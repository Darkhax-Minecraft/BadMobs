package net.darkhax.badmobs.handler;

import net.darkhax.badmobs.BadMobs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BadMobsEventHandler {

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

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onTooltipRendered (ItemTooltipEvent event) {

        if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof ItemMonsterPlacer) {

            final ResourceLocation id = ItemMonsterPlacer.getNamedIdFrom(event.getItemStack());
            
            if (id != null) {
                
                event.getToolTip().add("Entity ID: " + id.toString());
            }
        }
    }
}
