package net.darkhax.badmobs;

import net.darkhax.badmobs.config.Configuration;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

@Mod("badmobs")
public class BadMobs {

    private Configuration config;

    public BadMobs(IEventBus modBus) {

        modBus.addListener(EventPriority.LOWEST, this::afterEntityRegistered);
        NeoForge.EVENT_BUS.addListener(this::onSpawnFinalized);
        NeoForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
    }

    private void afterEntityRegistered(RegisterSpawnPlacementsEvent event) {
        config = new Configuration();
    }

    private void onEntityJoinWorld(EntityJoinLevelEvent event) {

        if (event.getEntity() instanceof Mob mob && !mob.level().isClientSide()) {

            if (config != null && !config.allowSpawn(mob, mob.getSpawnType())) {
                event.setCanceled(true);
                // Discard here to prevent any post configurations from running forever
                event.getEntity().discard();
            }
        }
    }

    private void onSpawnFinalized(FinalizeSpawnEvent event) {

        if (config != null && !event.getEntity().level().isClientSide() && !config.allowSpawn(event.getEntity(), event.getSpawnType())) {
            event.setSpawnCancelled(true);
        }
    }
}