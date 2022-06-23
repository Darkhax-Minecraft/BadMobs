package net.darkhax.badmobs.config;

import java.util.HashMap;
import java.util.Map;

import net.darkhax.badmobs.BadMobs;
import net.darkhax.badmobs.tempshelf.ConfigManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

public class Configuration {
    
    private final ConfigManager manager;
    private final Map<EntityType<?>, SpawnConfig> configs = new HashMap<>();
    
    public Configuration() {
        
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        
        for (final EntityType<?> type : ForgeRegistries.ENTITIES) {
            
            this.configs.put(type, new SpawnConfig(type.getRegistryName(), builder));
        }
        
        this.manager = new ConfigManager(builder.build());
        this.manager.registerWithForge();
        this.manager.open();
    }
    
    public boolean allowSpawn (Entity entity, SpawnReason reason) {
        
        final SpawnConfig config = this.configs.get(entity.getType());
        
        if (config == null) {
            
            if (!ForgeRegistries.ENTITIES.containsValue(entity.getType())) {
                
                BadMobs.LOG.error("The entity type {} of {} spawned but has not been registered. This is not allowed. SpawnReason={}", entity.getType().getRegistryName(), entity, reason);
            }
            
            return true;
        }
        
        else {
            
            return config.canSpawn(entity, reason);
        }
    }
}