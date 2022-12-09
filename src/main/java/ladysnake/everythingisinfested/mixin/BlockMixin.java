package ladysnake.everythingisinfested.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(at = @At(value = "RETURN"), method = "onBroken")
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (!world.isClient()) {
                ServerWorld serverWorld = (ServerWorld) world;
                SilverfishEntity silverfishEntity = (SilverfishEntity) EntityType.SILVERFISH.create(serverWorld);
                silverfishEntity.refreshPositionAndAngles((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
                serverWorld.spawnEntity(silverfishEntity);
        silverfishEntity.playSpawnEffects();
        }
    }

    @Inject(at = @At(value = "RETURN"), method = "onDestroyedByExplosion")
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion, CallbackInfo ci) {
        if (!world.isClient()) {
                ServerWorld serverWorld = (ServerWorld) world;
                SilverfishEntity silverfishEntity = (SilverfishEntity) EntityType.SILVERFISH.create(serverWorld);
                silverfishEntity.refreshPositionAndAngles((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
                serverWorld.spawnEntity(silverfishEntity);
                silverfishEntity.playSpawnEffects();
        }
    }
}
