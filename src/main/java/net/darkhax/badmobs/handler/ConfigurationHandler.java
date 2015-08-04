package net.darkhax.badmobs.handler;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import net.darkhax.badmobs.lib.Constants;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {
    
    public static Configuration config;
    public static boolean serverMode = false;
    public static boolean killMode = true;
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
        killMode = config.getBoolean("killMode", config.CATEGORY_GENERAL, killMode, "If this is set to true, bad mobs will be killed/deleted when they attempt to spawn. If this is set to false, the mob will not be removed from the world and only the spawning will be prevented, and when the mob is nolonger listed as bad, all previously prevented mobs of that type will spawn.");
        bannedEntities = Arrays.asList(config.getStringList("bannedMobs", config.CATEGORY_GENERAL, new String[] { "example1", "example2", "example3" }, "A list of all banned mobs. If a mobs entity name is added to this list, it will not be allowed to spawn in any world. To get the name of an entity, interact with it using the data checker and it's name will be given."));
        
        if (config.hasChanged())
            config.save();
    }
}