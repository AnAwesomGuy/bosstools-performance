package net.anawesomguy.bosstools_perf.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.anawesomguy.bosstools_perf.BossToolsPerformance;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.events.forgeevents.ItemGravityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "team/creative/itemphysic/server/ItemPhysicServer")
public abstract class ItemPhysicServerMixin {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/vector/Vector3d;add(DDD)Lnet/minecraft/util/math/vector/Vector3d;"), method = "updatePre(Lnet/minecraft/entity/item/ItemEntity;)V")
    private static Vector3d bosstools_perf$gravityPerformance(Vector3d instance, double x, double y, double z, Operation<Vector3d> original, @Local(argsOnly = true) ItemEntity item) {
        if (!MinecraftForge.EVENT_BUS.post(new ItemGravityEvent(item))) {
            RegistryKey<World> worldKey = item.level.dimension();
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
}
