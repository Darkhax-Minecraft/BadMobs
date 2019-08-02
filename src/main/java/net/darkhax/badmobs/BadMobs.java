package net.darkhax.badmobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemSpawnEgg;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
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
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.addListener(this::onItemTooltip));
    }
    
    private void loadComplete (FMLLoadCompleteEvent event) {
        
        for (final String entry : this.config.getBannedMobs()) {
            
            final ResourceLocation id = new ResourceLocation(entry);
            
            if (ForgeRegistries.ENTITIES.containsKey(id)) {
                
                this.blacklist.blacklistGlobal(id);
            }
            
            else {
                
                log.error("Tried to blacklist mob {} but no mob exists with that ID!", id.toString());
            }
        }
        
        for (final Biome biome : ForgeRegistries.BIOMES) {
            
            for (final EnumCreatureType type : EnumCreatureType.values()) {
                
                biome.getSpawns(type).removeIf(entry -> this.blacklist.isBlacklisted(entry.entityType));
            }
        }
    }
    
    private void onConfigLodaded(ModConfig.Loading loaded) {
    	
    	if ("badmobs".equalsIgnoreCase(loaded.getConfig().getModId())) {
    		
    		log.info("Loading blacklist from config file.");
    		this.blacklist.clear();
    		
    		for (String string : this.config.getBannedMobs()) {
    			
    			this.blacklist.blacklistGlobal(string);
    		}
    	}
    }
    
    @OnlyIn(Dist.CLIENT)
    private void onItemTooltip (ItemTooltipEvent event) {
        
        if (this.config.addTooltip() && !event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof ItemSpawnEgg) {
            
            final ItemSpawnEgg egg = (ItemSpawnEgg) event.getItemStack().getItem();
            final EntityType<?> type = egg.getType(event.getItemStack().getTag());
            
            if (type != null) {
                
                event.getToolTip().add(new TextComponentTranslation("tip.badmobs.entityid", type.getRegistryName().toString()));
            }
        }
    }
}