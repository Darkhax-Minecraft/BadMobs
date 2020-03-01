package net.darkhax.badmobs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class Blacklist {

	private final List<EntityType<?>> blacklistGlobal = new ArrayList<>();
    
	private boolean spawnEggs;
	private boolean spawners;
	
	public void load(Configuration config) {
		
		this.spawnEggs = config.allowSpawnEgg();
		this.spawners = config.allowSpawners();
		
		this.blacklistGlobal.clear();
		
		BadMobs.log.info("Loading {} blacklist entries from config.", config.getBannedMobs().size());
		
		for (String string : config.getBannedMobs()) {
			
			this.blacklistGlobal(string);
		}
	}
	
	private void blacklistGlobal(String id) {
		
		ResourceLocation idRL = ResourceLocation.tryCreate(id);
		
		if (idRL != null) {
			
			final EntityType<?> type = ForgeRegistries.ENTITIES.getValue(idRL);
			
			if (type != null) {
				
				this.blacklistGlobal.add(type);
			}
			
			else {
				
				BadMobs.log.error("Failed to blacklist ID {}. No mob with this name exists.", id);
			}
		}
		
		else {
			
			BadMobs.log.error("Failed to blacklist invalid ID {}. It was entered incorrectly!", id);
		}
	}

    public boolean isBlacklisted (Entity entity, SpawnReason reason) {

    	if (reason == SpawnReason.COMMAND) {
    		
    		return false;
    	}
    	
    	else if ((reason == SpawnReason.SPAWN_EGG && this.spawnEggs) || (reason == SpawnReason.SPAWNER && this.spawners)) {
    		
    		return false;
    	}
    	
        return this.isBlacklisted(entity.getType());
    }
    
    public boolean isBlacklisted (Entity entity) {

        return this.isBlacklisted(entity.getType());
    }
    
    public boolean isBlacklisted (EntityType<?> type) {

        return this.blacklistGlobal.contains(type);
    }
}