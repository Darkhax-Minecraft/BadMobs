package net.darkhax.badmobs.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;

public class ItemDataChecker extends Item {

    public ItemDataChecker () {

        this.setCreativeTab(CreativeTabs.MISC);
        this.setUnlocalizedName("badmobs.datachecker");
        this.setRegistryName("infobook");
    }

    @Override
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {

        if (playerIn.getEntityWorld().isRemote) {
            return false;
        }

        playerIn.sendMessage(new TextComponentString(I18n.format("chat.badmobs.entityName") + ": " + EntityList.getEntityString(target)));
        return true;
    }
}