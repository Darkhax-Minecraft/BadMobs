package net.darkhax.badmobs.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import net.darkhax.badmobs.BadMobs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

public class Configuration {
    
    private final Map<EntityType<?>, SpawnConfig> configs = new HashMap<>();
    private final ForgeConfigSpec spec;
    
    public Configuration() {
        
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        
        for (final EntityType<?> type : ForgeRegistries.ENTITIES) {
            
            this.configs.put(type, new SpawnConfig(type.getRegistryName(), builder));
        }
        
        this.spec = builder.build();
        this.save();
    }
    
    public boolean allowSpawn (Entity entity, SpawnReason reason, boolean world) {
        
        final SpawnConfig config = this.configs.get(entity.getType());
        
        if (config == null) {
            
            BadMobs.log.error("The entity type {} of {} spawned but has not been registered. This is not allowed. SpawnReason={}", entity.getType().getRegistryName(), entity, reason);
            return true;
        }
        
        else {
            
            return config.canSpawn(entity, reason, world);
        }
    }
    
    private void save () {
        
        final ModConfig modConfig = new ModConfig(Type.COMMON, this.spec, ModLoadingContext.get().getActiveContainer());
        final CommentedFileConfig configData = modConfig.getHandler().reader(FMLPaths.CONFIGDIR.relative()).apply(modConfig);
        final Method setConfigDataMethod = ObfuscationReflectionHelper.findMethod(ModConfig.class, "setConfigData", CommentedConfig.class);
        
        try {
            setConfigDataMethod.invoke(modConfig, configData);
        }
        
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            BadMobs.log.error("Forge's config code could not be accessed.", e);
            throw new IllegalStateException(e);
        }
        
        modConfig.save();
    }
}