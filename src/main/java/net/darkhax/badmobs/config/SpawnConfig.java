package net.darkhax.badmobs.config;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

public class SpawnConfig {
    
    private final ForgeConfigSpec.BooleanValue allowNormalSpawn;
    private final ForgeConfigSpec.BooleanValue allowSpawners;
    private final ForgeConfigSpec.BooleanValue allowSpawnEggs;
    private final ForgeConfigSpec.BooleanValue removeAggresively;
    
    public SpawnConfig(ResourceLocation id, ForgeConfigSpec.Builder spec) {
        
        spec.comment("Options for the " + id.getNamespace() + " mod.");
        spec.push(id.getNamespace());
        
        spec.comment("Spawning options for " + id.toString());
        spec.push(id.getPath());
        
        spec.comment("Should the entity be allowed to spawn normally?");
        this.allowNormalSpawn = spec.define("allowNormalSpawning", true);
        
        spec.comment("Should spawners be able to spawn the entity?");
        this.allowSpawners = spec.define("allowSpawners", true);
        
        spec.comment("Should spawn eggs be able to spawn the entity?");
        this.allowSpawnEggs = spec.define("allowSpawnEggs", true);
        
        spec.comment("When enabled the entity type will be aggresively removed from worlds. This will bypass all other options.");
        this.removeAggresively = spec.define("removeAggressively", false);
        
        spec.pop();        
        spec.pop();
    }
    
    public boolean canSpawn (Entity entity, SpawnReason reason, boolean world) {
        
        if (world || this.removeAggresively.get()) {
            
            return !this.removeAggresively.get();
        }
        
        if (reason == SpawnReason.SPAWNER) {
            
            return this.allowSpawners.get();
        }
        
        if (reason == SpawnReason.SPAWN_EGG) {
            
            return this.allowSpawnEggs.get();
        }
        
        return this.allowNormalSpawn.get();
    }
}