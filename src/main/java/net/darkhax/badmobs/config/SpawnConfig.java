package net.darkhax.badmobs.config;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

public class SpawnConfig {

    private final ForgeConfigSpec.BooleanValue allowNormalSpawn;
    private final ForgeConfigSpec.BooleanValue allowSpawners;
    private final ForgeConfigSpec.BooleanValue allowSpawnEggs;
    private final ForgeConfigSpec.BooleanValue allowConversions;
    private final ForgeConfigSpec.BooleanValue removeAggresively;

    public SpawnConfig(ResourceLocation id, ForgeConfigSpec.Builder builder) {

        builder.comment("Options for the " + id.getNamespace() + " mod.");
        builder.push(id.getNamespace());

        builder.comment("Spawning options for " + id.toString());
        builder.push(id.getPath());

        builder.comment("Should the entity be allowed to spawn normally?");
        this.allowNormalSpawn = builder.define("allowNormalSpawning", true);

        builder.comment("Should spawners be able to spawn the entity?");
        this.allowSpawners = builder.define("allowSpawners", true);

        builder.comment("Should spawn eggs be able to spawn the entity?");
        this.allowSpawnEggs = builder.define("allowSpawnEggs", true);

        builder.comment("Should the entity spawn via mob conversion? i.e. villager -> zombie");
        this.allowConversions = builder.define("allowConversions", true);

        builder.comment("When enabled the entity type will be aggresively removed from worlds. This will bypass all other options.");
        this.removeAggresively = builder.define("removeAggressively", false);

        builder.pop();
        builder.pop();
    }

    public boolean canSpawn (Entity entity, SpawnReason reason) {

        if (this.removeAggresively.get()) {

            return false;
        }

        if (reason == SpawnReason.SPAWNER) {

            return this.allowSpawners.get();
        }

        if (reason == SpawnReason.SPAWN_EGG) {

            return this.allowSpawnEggs.get();
        }

        if (reason == SpawnReason.CONVERSION) {

            return this.allowConversions.get();
        }

        return this.allowNormalSpawn.get();
    }
}