package net.anawesomguy.bosstools_performance.mixin;

import com.bawnorton.mixinsquared.MixinSquaredBootstrap;
import com.bawnorton.mixinsquared.canceller.MixinCancellerRegistrar;
import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

import static net.anawesomguy.bosstools_performance.BossToolsPerformanceConfig.*;

public class BossToolsPerformanceMixinPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {
        MixinSquaredBootstrap.init();
        MixinExtrasBootstrap.init();
        MixinCancellerRegistrar.register((targets, name) -> "net.mrscauthd.boss_tools.mixin.MixinItemGravity".equals(name) || "net.mrscauthd.boss_tools.mixin.ArmorTranslucent".equals(name));
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String target, String name) {
        return (MERCURY_STAR_COUNT.get() != -1 || !"net.anawesomguy.bosstools_performance.mixin.client.stars.ClientEventBusMercury$1Mixin".equals(name)) &&
               (MERCURY_ORBIT_STAR_COUNT.get() != -1 || !"net.anawesomguy.bosstools_performance.mixin.client.stars.ClientEventBusMercuryOrbit$1Mixin".equals(name)) &&
               (VENUS_ORBIT_STAR_COUNT.get() != -1 || !"net.anawesomguy.bosstools_performance.mixin.client.stars.ClientEventBusVenusOrbit$1Mixin".equals(name)) &&
               (OVERWORLD_ORBIT_STAR_COUNT.get() != -1 || !"net.anawesomguy.bosstools_performance.mixin.client.stars.ClientEventBusOverworldOrbit$1Mixin".equals(name)) &&
               (MARS_ORBIT_STAR_COUNT.get() != -1 || !"net.anawesomguy.bosstools_performance.mixin.client.stars.ClientEventBusMarsOrbit$1Mixin".equals(name));
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
