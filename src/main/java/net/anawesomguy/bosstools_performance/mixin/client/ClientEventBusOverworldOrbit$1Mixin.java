package net.anawesomguy.bosstools_performance.mixin.client;

import net.anawesomguy.bosstools_performance.BossToolsPerformanceConfig;
import net.minecraft.client.GameSettings;
import net.minecraft.client.settings.GraphicsFanciness;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "net.mrscauthd.boss_tools.skyrenderer.ClientEventBusOverworldOrbit$1", remap = false)
public abstract class ClientEventBusOverworldOrbit$1Mixin {
    @Redirect(method = "renderStars", at = @At(value = "FIELD", target = "Lnet/minecraft/client/GameSettings;graphicsMode:Lnet/minecraft/client/settings/GraphicsFanciness;", ordinal = 0))
    private GraphicsFanciness bosstools_performance$redirectStarCountCheck(GameSettings instance) {
        return GraphicsFanciness.FANCY;
    }

    @ModifyConstant(method = "renderStars", constant = @Constant(intValue = 13000))
    private int bosstools_performance$redirectStarCount(int constant) {
        return BossToolsPerformanceConfig.OVERWORLD_ORBIT_STAR_COUNT.get();
    }
}
