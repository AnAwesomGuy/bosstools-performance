package net.anawesomguy.bosstools_performance.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.anawesomguy.bosstools_performance.BossToolsPerformance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.events.forgeevents.ItemGravityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/vector/Vector3d;add(DDD)Lnet/minecraft/util/math/vector/Vector3d;"), method = "tick")
    private Vector3d bosstools_performance$gravityPerformance(Vector3d instance, double x, double y, double z, Operation<Vector3d> original) {
        if (!MinecraftForge.EVENT_BUS.post(new ItemGravityEvent((ItemEntity)(Object)this))) {
            RegistryKey<World> worldKey = level.dimension();
            if (BossToolsPerformance.ORBIT_WORLDS.containsKey(worldKey))
                return new Vector3d(instance.x + x, (instance.y / .98 + .03) + y, instance.z + z);
            else {
                double gravity = BossToolsPerformance.SPACE_WORLDS.getDouble(worldKey) + .02;
                if (gravity == gravity) // check nan
                    return new Vector3d(instance.x + x, (instance.y / .98 + .08 - gravity) + y, instance.z + z);
            }
        }
        return original.call(instance, x, y, z);
    }

    private ItemEntityMixin(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }
}
