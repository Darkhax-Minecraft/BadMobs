package net.darkhax.badmobs.config;

import net.darkhax.badmobs.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntitySpawnReason;
import net.neoforged.fml.ModList;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Configuration {

    private static final List<String> VANILLA_MISC_WHITELIST = List.of(
            "villager",
            "snow_golem",
            "iron_golem"
    );

    private final Map<EntityType<?>, SpawnConfig> configs = new HashMap<>();

    public Configuration() {

        final ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        for (final EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {

            final Identifier id = Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(type));

            if (!"minecraft".equals(id.getNamespace()) || (type.getCategory() != MobCategory.MISC || VANILLA_MISC_WHITELIST.contains(id.getPath()))) {
                this.configs.put(type, new SpawnConfig(id, builder));
            }
        }

        ModList.get().getModContainerById("badmobs").ifPresent(container -> container.registerConfig(ModConfig.Type.LOCAL, builder.build()));
    }

    public boolean allowSpawn(Entity entity, EntitySpawnReason reason) {

        final SpawnConfig config = this.configs.get(entity.getType());

        if (config == null) {

            if (!BuiltInRegistries.ENTITY_TYPE.containsValue(entity.getType())) {

                Constants.LOG.error("The entity type {} of {} spawned but has not been registered. This is not allowed. SpawnReason={}", BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()), entity, reason);
            }

            return true;
        }

        else {

            return config.canSpawn(reason);
        }
    }
}