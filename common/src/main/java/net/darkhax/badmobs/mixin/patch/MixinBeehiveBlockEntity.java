package net.darkhax.badmobs.mixin.patch;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BeehiveBlockEntity.class)
public class MixinBeehiveBlockEntity {

    @Inject(method = "releaseOccupant", cancellable = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
    private static void fixReleaseOccupants(Level level, BlockPos pos, BlockState state, BeehiveBlockEntity.Occupant occupant, List<Entity> storedInHives, BeehiveBlockEntity.BeeReleaseStatus releaseStatus, BlockPos storedFlowerPos, CallbackInfoReturnable<Boolean> cir, @Local Entity entity) {
        // Inject before playSound and wrap sound and game event in whether the entity was added or not
        boolean addedEntity = level.addFreshEntity(entity);
        if (addedEntity) {
            level.playSound(null, pos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, level.getBlockState(pos)));
        }
        cir.setReturnValue(addedEntity);
    }
}
