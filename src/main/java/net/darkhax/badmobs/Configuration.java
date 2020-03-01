package net.darkhax.badmobs;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class Configuration {

    public static final String REMOVE_NAMED = "removeNamed";
    public static final String BANNED_MOBS = "bannedMobs";
    public static final String ALLOW_SPAWNER = "allowSpawners";
    public static final String ALLOW_SPAWN_EGG = "allowSpawnEgg";
    
    private final ForgeConfigSpec spec;
    private final ConfigValue<List<? extends String>> globalIds;
    private final BooleanValue allowSpawners;
    private final BooleanValue allowSpawnEggs;
    
    public Configuration () {

        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        // General Configs
        builder.comment("General settings for the mod.");
        builder.push("general");

        builder.comment("A list of all banned mobs. If a mobs entity id is added to this list, it will not be allowed to spawn in any world.");
        this.globalIds = builder.defineList(BANNED_MOBS, new ArrayList<String>(), val -> val instanceof String);
        
        builder.comment("Should banned mobs spawn from spawners?");
        allowSpawners = builder.define(ALLOW_SPAWNER, true);
        
        builder.comment("Should banned mobs spawn from spawn eggs?");
        allowSpawnEggs = builder.define(ALLOW_SPAWN_EGG, true);
        
        builder.pop();
        
        this.spec = builder.build();
    }
    
    public ForgeConfigSpec getSpec() {
    	
    	return this.spec;
    }

    public boolean allowSpawnEgg () {

        return this.allowSpawnEggs.get();
    }
    
    public boolean allowSpawners () {
    	
    	return this.allowSpawners.get();
    }

    public List<? extends String> getBannedMobs () {

        return this.globalIds.get();
    }
}