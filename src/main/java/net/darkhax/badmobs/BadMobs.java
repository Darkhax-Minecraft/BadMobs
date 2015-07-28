package net.darkhax.badmobs;

import net.darkhax.badmobs.common.ProxyCommon;
import net.darkhax.badmobs.handler.ConfigurationHandler;
import net.darkhax.badmobs.handler.EntitySpawningHandler;
import net.darkhax.badmobs.item.ItemDataChecker;
import net.darkhax.badmobs.lib.Constants;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = Constants.VERSION, acceptableRemoteVersions = "*", guiFactory = Constants.FACTORY)
public class BadMobs {
    
    @SidedProxy(clientSide = Constants.CLIENT, serverSide = Constants.SERVER)
    public static ProxyCommon proxy;
    
    @Instance(Constants.MODID)
    public static BadMobs instance;
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent pre) {
    
        new ConfigurationHandler(pre.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new EntitySpawningHandler());
        
        if (!ConfigurationHandler.serverMode)
            GameRegistry.registerItem(new ItemDataChecker(), "dataChecker");
    }
}