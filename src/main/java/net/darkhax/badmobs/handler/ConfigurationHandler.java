package net.darkhax.badmobs.handler;

import java.io.File;

import org.apache.commons.lang3.math.NumberUtils;

import net.darkhax.badmobs.BadMobs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {

    public static Configuration config;
    public static boolean removeAggressively = false;
    public static boolean addIDToTooltip = true;
    public static boolean removeNamedMobs = false;

    public ConfigurationHandler (File file) {

        config = new Configuration(file);
        this.syncConfigData();
    }

    private void syncConfigData () {

        removeAggressively = config.getBoolean("removeAggressively", Configuration.CATEGORY_GENERAL, false, "When enabled this mod will target mobs that have already spawned. This option must also be enabled to prevent mob spawners from spawning as well.");
        addIDToTooltip = config.getBoolean("addTooltipInfo", Configuration.CATEGORY_GENERAL, true, "When enabled, the ID of a mob will be shown on it's corrosponding spawn egg. This is used to help you blacklist mobs.");
        removeNamedMobs = config.getBoolean("removeNamedMobs", Configuration.CATEGORY_GENERAL, false, "While false, this mod will not remove custom named mobs from the game.");
        final String[] entries = config.getStringList("bannedMobs", Configuration.CATEGORY_GENERAL, new String[] { "example1", "example2", "example3" }, "A list of all banned mobs. If a mobs entity name is added to this list, it will not be allowed to spawn in any world. To get the name of an entity, interact with it using the data checker and it's name will be given.");

        for (final String entry : entries) {

            final String[] parts = entry.split("#");

            if (parts.length == 2 && NumberUtils.isDigits(parts[1])) {

                final int dimId = Integer.parseInt(parts[1]);
                BadMobs.blacklist(dimId, parts[0]);
            }

            else {

                BadMobs.blacklist(entry);
            }
        }
        
        if (removeAggressively) {
            
            MinecraftForge.EVENT_BUS.register(new SpawnEventHandler());
        }

        if (config.hasChanged()) {
            config.save();
        }
    }
}