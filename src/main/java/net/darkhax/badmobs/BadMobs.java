package net.darkhax.badmobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import crafttweaker.CraftTweakerAPI;
import net.darkhax.badmobs.addons.crt.BadMobsTweaker;
import net.darkhax.badmobs.handler.BadMobsEventHandler;
import net.darkhax.badmobs.handler.ConfigurationHandler;
import net.darkhax.badmobs.lib.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = "@VERSION@", acceptableRemoteVersions = "*")
public class BadMobs {

    public static final List<String> GLOBAL_BLACKLIST = new ArrayList<>();
    public static final Map<Integer, List<String>> DIMENSIONAL_BLACKLIST = new HashMap<>();

    @EventHandler
    public void preInit (FMLPreInitializationEvent pre) {

        new ConfigurationHandler(pre.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new BadMobsEventHandler());
    }

    @EventHandler
    public void postInit (FMLPostInitializationEvent ev) {

        if (Loader.isModLoaded("crafttweaker")) {

            CraftTweakerAPI.registerClass(BadMobsTweaker.class);
        }
    }

    public static void blacklist (String entity) {

        GLOBAL_BLACKLIST.add(entity);
    }

    public static void remove (String entity) {

        GLOBAL_BLACKLIST.remove(entity);
    }

    public static void blacklist (int id, String entity) {

        if (!DIMENSIONAL_BLACKLIST.containsKey(id)) {

            DIMENSIONAL_BLACKLIST.put(id, new ArrayList<String>());
        }

        DIMENSIONAL_BLACKLIST.get(id).add(entity);
    }

    public static void remove (int id, String entity) {

        if (!DIMENSIONAL_BLACKLIST.containsKey(id)) {

            DIMENSIONAL_BLACKLIST.put(id, new ArrayList<String>());
        }

        DIMENSIONAL_BLACKLIST.get(id).remove(entity);
    }

    public static boolean isBlacklisted (Entity entity) {

        final String entityId = EntityList.getEntityString(entity);
        return GLOBAL_BLACKLIST.contains(entityId) ? true : DIMENSIONAL_BLACKLIST.get(entity.dimension) != null ? DIMENSIONAL_BLACKLIST.get(entity.dimension).contains(entityId) : false;
    }
}