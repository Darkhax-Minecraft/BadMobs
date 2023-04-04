package net.darkhax.badmobs.config;

import net.darkhax.badmobs.BadMobs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Configuration {

    private static final List<String> VANILLA_BLACKLIST = List.of(
            "player",
            "area_effect_cloud",
            "armor_stand",
            "arrow",
            "block_display",
            "boat",
            "chest_boat",
            "chest_minecart",
            "command_block_minecart",
            "dragon_fireball",
            "egg",
            "end_crystal",
            "ender_pearl",
            "evoker_fangs",
            "experience_bottle",
            "experience_orb",
            "eye_of_ender",
            "falling_block",
            "firework_rocket",
            "furnace_minecart",
            "glow_item_frame",
            "hopper_minecart",
            "interaction",
            "item",
            "item_display",
            "item_frame",
            "fireball",
            "leash_knot",
            "lightning_bolt",
            "llama_spit",
            "marker",
            "minecart",
            "painting",
            "potion",
            "shulker_bullet",
            "small_fireball",
            "snowball",
            "spawner_minecart",
            "spectral_arrow",
            "text_display",
            "tnt",
            "tnt_minecart",
            "trident",
            "wither_skull",
            "fishing_bobber"
    );
    private final ConfigManager manager;
    private final Map<EntityType<?>, SpawnConfig> configs = new HashMap<>();

    public Configuration() {

        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        for (final EntityType<?> type : ForgeRegistries.ENTITY_TYPES) {

            final ResourceLocation id = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(type));

            if (!"minecraft".equals(id.getNamespace()) || !VANILLA_BLACKLIST.contains(id.getPath())) {
                this.configs.put(type, new SpawnConfig(id, builder));
            }
        }

        this.manager = new ConfigManager(builder.build());
        this.manager.registerWithForge();
        this.manager.open();
    }

    public boolean allowSpawn(Entity entity, MobSpawnType reason) {

        final SpawnConfig config = this.configs.get(entity.getType());

        if (config == null) {

            if (!ForgeRegistries.ENTITY_TYPES.containsValue(entity.getType())) {

                BadMobs.LOG.error("The entity type {} of {} spawned but has not been registered. This is not allowed. SpawnReason={}", ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()), entity, reason);
            }

            return true;
        }

        else {

            return config.canSpawn(reason);
        }
    }
}