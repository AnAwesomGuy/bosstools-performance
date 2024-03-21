package net.anawesomguy.bosstools_performance.mixin.client;

import net.anawesomguy.bosstools_performance.BossToolsPerformanceConfig;
import net.minecraftforge.client.IWeatherParticleRenderHandler;
import net.minecraftforge.client.IWeatherRenderHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = "net.mrscauthd.boss_tools.skyrenderer.ClientEventBusVenus$1", remap = false)
public abstract class ClientEventBusVenus$1Mixin {
    /**
     * Allows the planet selection GUI header to be translated at {@code "gui.boss_tools.planet_selection"}.
     *
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public IWeatherParticleRenderHandler getWeatherParticleRenderHandler() {
        return BossToolsPerformanceConfig.RENDER_VENUS_PARTICLES.get() ? null : (ticks, world, mc, activeRenderInfoIn) -> {};
    }

    /**
     * Allows the planet selection GUI header to be translated at {@code "gui.boss_tools.planet_selection"}.
     *
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public IWeatherRenderHandler getWeatherRenderHandler() {
        return BossToolsPerformanceConfig.RENDER_VENUS_RAIN.get() ? null : (ticks, partialTicks, world, mc, lightmapIn, xIn, yIn, zIn) -> {};
    }
}
