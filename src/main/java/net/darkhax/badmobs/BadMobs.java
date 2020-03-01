package net.darkhax.badmobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod("badmobs")
public class BadMobs {
    
    public static final Logger log = LogManager.getLogger("Bad Mobs");
    private final Configuration config = new Configuration();
    private final Blacklist blacklist = new Blacklist();
    
    public BadMobs () {
        
    	ModLoadingContext.get().registerConfig(Type.COMMON, config.getSpec());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onConfigLodaded);
        MinecraftForge.EVENT_BUS.addListener(this::checkSpawn);
        MinecraftForge.EVENT_BUS.addListener(this::specialSpawn);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.addListener(this::onItemTooltip));
    }
    
    private void loadComplete (FMLLoadCompleteEvent event) {
        
        for (final Biome biome : ForgeRegistries.BIOMES) {
            
            for (final EntityClassification type : EntityClassification.values()) {
                
                biome.getSpawns(type).removeIf(entry -> this.blacklist.isBlacklisted(entry.entityType));
            }
        }
    }
    
    private void checkSpawn(CheckSpawn event) {
    	
    	if (blacklist.isBlacklisted(event.getEntity(), event.getSpawnReason())) {
    		
    		event.getEntity().remove();
    		event.setResult(Result.DENY);
    	}
    }
    
    private void specialSpawn(SpecialSpawn event) {
    	
    	if (blacklist.isBlacklisted(event.getEntity(), event.getSpawnReason())) {
    		
    		event.getEntity().remove();
    		event.setCanceled(true);
    	}
    }
    
    private void onConfigLodaded(ModConfig.Loading loaded) {
    	
    	if ("badmobs".equalsIgnoreCase(loaded.getConfig().getModId())) {
    		
    		this.blacklist.load(this.config);
    	}
    }
    
    @OnlyIn(Dist.CLIENT)
    private void onItemTooltip (ItemTooltipEvent event) {
        
        if (event.getFlags().isAdvanced() && !event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof SpawnEggItem) {
            
            final SpawnEggItem egg = (SpawnEggItem) event.getItemStack().getItem();
            final EntityType<?> type = egg.getType(event.getItemStack().getTag());
            
            if (type != null) {
                
                event.getToolTip().add(new TranslationTextComponent("tip.badmobs.entityid", type.getRegistryName().toString()));
            }
        }
    }
}