package net.darkhax.badmobs.handler;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.lang3.math.NumberUtils;

import net.darkhax.badmobs.BadMobs;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {

    public static Configuration config;
    public static boolean serverMode = false;
    public static boolean killMode = true;

    public ConfigurationHandler (File file) {

        config = new Configuration(file);
        this.syncConfigData();
    }

    private void syncConfigData () {

        serverMode = config.getBoolean("serverMode", Configuration.CATEGORY_GENERAL, serverMode, "If this is set to true, the mod will be put into server-side mode. This will remove the Entity Data Book from the game, but will allow clients to connect to your server, without having the mod installed.");
        killMode = config.getBoolean("killMode", Configuration.CATEGORY_GENERAL, killMode, "If this is set to true, bad mobs will be killed/deleted when they attempt to spawn. If this is set to false, the mob will not be removed from the world and only the spawning will be prevented, and when the mob is nolonger listed as bad, all previously prevented mobs of that type will spawn.");

        final String[] entries = config.getStringList("bannedMobs", Configuration.CATEGORY_GENERAL, new String[] { "example1", "example2", "example3" }, "A list of all banned mobs. If a mobs entity name is added to this list, it will not be allowed to spawn in any world. To get the name of an entity, interact with it using the data checker and it's name will be given.");

        for (final String entry : entries) {

            final String[] parts = entry.split("#");

            if (parts.length == 2 && NumberUtils.isNumber(parts[1])) {

                final int dimId = Integer.parseInt(parts[1]);

                if (!BadMobs.DIMENSIONAL_BLACKLIST.containsKey(dimId)) {

                    BadMobs.DIMENSIONAL_BLACKLIST.put(dimId, new ArrayList<String>());
                }

                BadMobs.DIMENSIONAL_BLACKLIST.get(dimId).add(parts[0]);
            }

            else {

                BadMobs.GLOBAL_BLACKLIST.add(entry);
            }
        }

        if (config.hasChanged()) {
            config.save();
        }
    }
}