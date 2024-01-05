package net.darkhax.badmobs;

import net.darkhax.badmobs.config.Configuration;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("badmobs")
public class BadMobs {

    public static final Logger LOG = LogManager.getLogger("Bad Mobs");
    private Configuration config;

    public BadMobs() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onLoadComplete);
        MinecraftForge.EVENT_BUS.addListener(this::onSpawnFinalized);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
    }

    private void onLoadComplete(FMLLoadCompleteEvent event) {

        event.enqueueWork(() -> config = new Configuration());
    }

    private void onEntityJoinWorld(EntityJoinLevelEvent event) {

        if (event.getEntity() instanceof Mob mob && !mob.level().isClientSide) {

            if (config != null && !config.allowSpawn(mob, mob.getSpawnType())) {

                event.setCanceled(true);
            }
        }
    }

    private void onSpawnFinalized(MobSpawnEvent.FinalizeSpawn event) {

        if (config != null && !event.getEntity().level().isClientSide && !config.allowSpawn(event.getEntity(), event.getSpawnType())) {

            event.setSpawnCancelled(true);
        }
    }
}