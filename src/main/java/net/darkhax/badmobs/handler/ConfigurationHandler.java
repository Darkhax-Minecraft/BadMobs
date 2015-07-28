package net.darkhax.badmobs.handler;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import net.darkhax.badmobs.lib.Constants;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {
    
    public static Configuration config;
    public static boolean serverMode = false;
    public static List<String> bannedEntities;
    
    public ConfigurationHandler(File file) {
    
        config = new Configuration(file);
        FMLCommonHandler.instance().bus().register(this);
        syncConfigData();
    }
    
    @SubscribeEvent
    public void onConfigChange (OnConfigChangedEvent event) {
    
        if (event.modID.equals(Constants.MODID))
            syncConfigData();
    }
    
    /**
     * Allows for the configuration data to be re-synced between the file on disk, and the
     * values in memory. For internal use only!
     */
    private void syncConfigData () {
    
        serverMode = config.getBoolean("serverMode", config.CATEGORY_GENERAL, serverMode, "If this is set to true, the mod will be put into server-side mode. This will remove the Entity Data Book from the game, but will allow clients to connect to your server, without having the mod installed.");
        bannedEntities = Arrays.asList(config.getStringList("bannedMobs", config.CATEGORY_GENERAL, new String[] { "example1", "example2", "example3" }, "A list of all banned mobs. If a mobs entity name is added to this list, it will not be allowed to spawn in any world. To get the name of an entity, interact with it using the data checker and it's name will be given."));
        
        if (config.hasChanged())
            config.save();
    }
}