package net.darkhax.badmobs.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import net.darkhax.badmobs.BadMobs;
import net.darkhax.badmobs.tempshelf.ConfigManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

public class Configuration {
    
    private final ConfigManager manager;
    private final Map<EntityType<?>, SpawnConfig> configs = new HashMap<>();
    
    public Configuration() {
        
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        
        for (final EntityType<?> type : ForgeRegistries.ENTITIES) {
            
            this.configs.put(type, new SpawnConfig(Objects.requireNonNull(ForgeRegistries.ENTITIES.getKey(type)), builder));
        }
        
        this.manager = new ConfigManager(builder.build());
        this.manager.registerWithForge();
        this.manager.open();
    }
    
    public boolean allowSpawn (Entity entity, MobSpawnType reason) {
        
        final SpawnConfig config = this.configs.get(entity.getType());
        
        if (config == null) {
            
            if (!ForgeRegistries.ENTITIES.containsValue(entity.getType())) {
                
                BadMobs.LOG.error("The entity type {} of {} spawned but has not been registered. This is not allowed. SpawnReason={}", ForgeRegistries.ENTITIES.getKey(entity.getType()), entity, reason);
            }
            
            return true;
        }
        
        else {
            
            return config.canSpawn(reason);
        }
    }
}