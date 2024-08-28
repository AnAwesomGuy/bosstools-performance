package net.anawesomguy.bosstools_perf.mixin.client;

import net.anawesomguy.bosstools_perf.BossToolsPerformanceConfig;
import net.minecraft.client.GameSettings;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.client.world.DimensionRenderInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "net.mrscauthd.boss_tools.skyrenderer.ClientEventBusOverworldOrbit$1")
public abstract class ClientEventBusOverworldOrbit$1Mixin extends DimensionRenderInfo {
    @Redirect(method = "renderStars", at = @At(value = "FIELD", target = "Lnet/minecraft/client/GameSettings;graphicsMode:Lnet/minecraft/client/settings/GraphicsFanciness;", ordinal = 0))
    private GraphicsFanciness bosstools_perf$redirectStarCountCheck(GameSettings instance) {
        return GraphicsFanciness.FANCY;
    }

    @ModifyConstant(method = "renderStars", constant = @Constant(intValue = 13000), remap = false)
    private int bosstools_perf$redirectStarCount(int constant) {
        return BossToolsPerformanceConfig.OVERWORLD_ORBIT_STAR_COUNT.get();
    }

    private ClientEventBusOverworldOrbit$1Mixin() {
        //noinspection DataFlowIssue
        super(0F, false, null, false, false);
    }
}
