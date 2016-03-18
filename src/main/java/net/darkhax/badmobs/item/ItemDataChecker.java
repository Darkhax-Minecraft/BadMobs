package net.darkhax.badmobs.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;

public class ItemDataChecker extends Item {
    
    public ItemDataChecker() {
        
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setUnlocalizedName("badmobs.datachecker");
    }
    
    @Override
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        
        if (playerIn.worldObj.isRemote)
            return false;
            
        playerIn.addChatMessage(new TextComponentString(I18n.translateToLocal("chat.badmobs.entityName") + ": " + EntityList.getEntityString(target)));
        return true;
    }
}