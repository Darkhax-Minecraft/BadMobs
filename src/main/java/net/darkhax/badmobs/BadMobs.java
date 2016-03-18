package net.darkhax.badmobs;

import net.darkhax.badmobs.common.ProxyCommon;
import net.darkhax.badmobs.handler.ConfigurationHandler;
import net.darkhax.badmobs.handler.EntitySpawningHandler;
import net.darkhax.badmobs.item.ItemDataChecker;
import net.darkhax.badmobs.lib.Constants;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, acceptableRemoteVersions = "*", guiFactory = Constants.FACTORY)
public class BadMobs {
    
    @SidedProxy(clientSide = Constants.CLIENT, serverSide = Constants.SERVER)
    public static ProxyCommon proxy;
    
    @Instance(Constants.MODID)
    public static BadMobs instance;
    
    public static Item infoBook = new ItemDataChecker();
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent pre) {
        
        new ConfigurationHandler(pre.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new EntitySpawningHandler());
        
        if (!ConfigurationHandler.serverMode) {
            
            GameRegistry.registerItem(infoBook, "infobook");
            
            if (pre.getSide().equals(Side.CLIENT))
                ModelLoader.setCustomModelResourceLocation(infoBook, 0, new ModelResourceLocation("badmobs:infobook", "inventory"));
        }
    }
}