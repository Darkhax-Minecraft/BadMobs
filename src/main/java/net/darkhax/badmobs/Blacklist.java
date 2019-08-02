package net.darkhax.badmobs;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public class Blacklist {

	private final Set<ResourceLocation> blacklistGlobal = new HashSet<>();
    
	public void clear() {
		
		this.blacklistGlobal.clear();
	}
	
	public void blacklistGlobal(Entity entity) {
		
		blacklistGlobal(entity.getType().getRegistryName());
	}
	
	public void blacklistGlobal(String id) {
		
		ResourceLocation idRL = ResourceLocation.tryCreate(id);
		
		if (idRL != null) {
			
			blacklistGlobal(idRL);
		}
		
		else {
			
			BadMobs.log.error("Failed to blacklist invalid ID {}. It was entered incorrectly!");
		}
	}
	
	public void blacklistGlobal(ResourceLocation id) {
		
		blacklistGlobal.add(id);
	}

    public boolean isBlacklisted (Entity entity) {

        return this.isBlacklisted(entity.getType());
    }
    
    public boolean isBlacklisted (EntityType<?> type) {

        return this.blacklistGlobal.contains(type.getRegistryName());
    }
}