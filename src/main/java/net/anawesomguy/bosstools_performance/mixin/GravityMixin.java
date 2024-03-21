package net.anawesomguy.bosstools_performance.mixin;

import net.anawesomguy.bosstools_performance.BossToolsPerformance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.events.Gravity;
import net.mrscauthd.boss_tools.events.Methodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(value = Gravity.class, remap = false)
public abstract class GravityMixin {
    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public static void Gravity(LivingEntity entity, Gravity.GravityType type, World world) {
        if (Methodes.isOrbitWorld(world))
            Gravity.gravityMath(type, entity, .02, -2.5F);
        else {
            double gravity = BossToolsPerformance.SPACE_WORLDS.getDouble(world.dimension());
            if (gravity == gravity)
                Gravity.gravityMath(type, entity, gravity, gravity < .04 ? -2.5F : 2F);
        }
    }
}
