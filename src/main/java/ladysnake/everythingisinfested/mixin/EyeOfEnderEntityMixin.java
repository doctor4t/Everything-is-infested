package ladysnake.everythingisinfested.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EyeOfEnderEntity.class)
public abstract class EyeOfEnderEntityMixin extends Entity {
    @Shadow private boolean dropsItem;

    @Shadow private int lifespan;

    public EyeOfEnderEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At(value = "RETURN"), method = "tick")
    public void tick(CallbackInfo ci) {
        if (!this.dropsItem && this.lifespan > 80) {
            if (!this.world.isClient()) {
                ServerWorld serverWorld = (ServerWorld) world;
                EndermiteEntity endermiteEntity = (EndermiteEntity) EntityType.ENDERMITE.create(serverWorld);
                endermiteEntity.refreshPositionAndAngles((double) this.getX() + 0.5D, (double) this.getY(), (double) this.getZ() + 0.5D, 0.0F, 0.0F);
                serverWorld.spawnEntity(endermiteEntity);
                endermiteEntity.playSpawnEffects();
            }
        }
    }
}
