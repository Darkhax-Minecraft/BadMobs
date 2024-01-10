package net.darkhax.badmobs.config;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

import net.darkhax.badmobs.BadMobs;
import net.darkhax.badmobs.Constants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class ConfigManager {
    
    private final ModContainer owner;
    private final ModConfig.Type type;
    private final String name;
    private final ForgeConfigSpec spec;
    private final ModConfig config;
    
    private boolean forgeRegistered = false;
    
    public ConfigManager(ForgeConfigSpec spec) {
        
        this(ModConfig.Type.COMMON, spec);
    }
    
    public ConfigManager(ModConfig.Type type, ForgeConfigSpec spec) {
        
        this(ModLoadingContext.get().getActiveContainer(), type, spec);
    }
    
    public ConfigManager(ModContainer owner, ModConfig.Type type, ForgeConfigSpec spec) {
        
        this(owner, type, defaultConfigName(type, owner.getModId()), spec);
    }
    
    public ConfigManager(ModContainer owner, ModConfig.Type type, String name, ForgeConfigSpec spec) {
        
        this.owner = owner;
        this.type = type;
        this.name = name;
        this.spec = spec;
        
        this.config = new ModConfig(this.type, this.spec, this.owner, this.name);
    }
    
    public ConfigManager open () {
        
        try {
            
            ObfuscationReflectionHelper.findMethod(ConfigTracker.class, "openConfig", ModConfig.class, Path.class).invoke(ConfigTracker.INSTANCE, this.config, FMLPaths.CONFIGDIR.get());
        }
        
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            
            Constants.LOG.catching(e);
        }
        
        return this;
    }
    
    public ConfigManager registerWithForge () {
        
        if (!this.forgeRegistered) {
            
            this.owner.addConfig(this.config);
            this.forgeRegistered = true;
            return this;
        }
        
        throw new IllegalStateException("The config " + this.config.getFileName() + " has already been registered with Forge.");
    }
    
    private static String defaultConfigName (Type type, String modId) {
        
        return String.format("%s-%s.toml", modId, type.extension());
    }
}