package net.darkhax.badmobs.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemDataChecker extends Item {
    
    public ItemDataChecker() {
    
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setUnlocalizedName("badmobs.datachecker");
        this.setTextureName("badmobs:infobook");
    }
    
    @Override
    public boolean itemInteractionForEntity (ItemStack item, EntityPlayer player, EntityLivingBase entity) {
        
        if (player.worldObj.isRemote)
            return false;
        
        player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chat.badmobs.entityName") + ": " + EntityList.getEntityString(entity)));
        return true;
    }
}