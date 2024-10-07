package net.anawesomguy.bosstools_perf.mixin.client;

import net.anawesomguy.bosstools_perf.BossToolsPerformance;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.events.OverlayEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// don't really know how to optimize this further, this stuff doesn't really make too much sense to me
@SuppressWarnings("AddedMixinMembersNamePattern")
@Mixin(value = OverlayEvents.class, remap = false)
public abstract class OverlayEventsMixin {
    @Unique
    private static final ResourceLocation WARNING = new ResourceLocation("boss_tools", "textures/overlay/warning.png");
    @Unique
    private static final ResourceLocation TIMER10 = new ResourceLocation("boss_tools", "textures/timer/timer10.png");
    @Unique
    private static final ResourceLocation TIMER9 = new ResourceLocation("boss_tools", "textures/timer/timer9.png");
    @Unique
    private static final ResourceLocation TIMER8 = new ResourceLocation("boss_tools", "textures/timer/timer8.png");
    @Unique
    private static final ResourceLocation TIMER7 = new ResourceLocation("boss_tools", "textures/timer/timer7.png");
    @Unique
    private static final ResourceLocation TIMER6 = new ResourceLocation("boss_tools", "textures/timer/timer6.png");
    @Unique
    private static final ResourceLocation TIMER5 = new ResourceLocation("boss_tools", "textures/timer/timer5.png");
    @Unique
    private static final ResourceLocation TIMER4 = new ResourceLocation("boss_tools", "textures/timer/timer4.png");
    @Unique
    private static final ResourceLocation TIMER3 = new ResourceLocation("boss_tools", "textures/timer/timer3.png");
    @Unique
    private static final ResourceLocation TIMER2 = new ResourceLocation("boss_tools", "textures/timer/timer2.png");
    @Unique
    private static final ResourceLocation TIMER1 = new ResourceLocation("boss_tools", "textures/timer/timer1.png");
    @Unique
    private static final ResourceLocation OXYGEN_EMPTY = new ResourceLocation("boss_tools", "textures/overlay/oxygentankcheck_empty.png");
    @Unique
    private static final ResourceLocation OXYGEN_FULL = new ResourceLocation("boss_tools", "textures/overlay/oxygentankcheck_full.png");
    @Unique
    private static final ResourceLocation ROCKET_Y_MOON = new ResourceLocation("boss_tools", "textures/planet_bar/rocket_y_main_moon.png");
    @Unique
    private static final ResourceLocation ROCKET_Y_MARS = new ResourceLocation("boss_tools", "textures/planet_bar/rocket_y_main_mars.png");
    @Unique
    private static final ResourceLocation ROCKET_Y_MERCURY = new ResourceLocation("boss_tools", "textures/planet_bar/rocket_y_main_mercury.png");
    @Unique
    private static final ResourceLocation ROCKET_Y_VENUS = new ResourceLocation("boss_tools", "textures/planet_bar/rocket_y_main_venus.png");
    @Unique
    private static final ResourceLocation ROCKET_Y_ORBIT = new ResourceLocation("boss_tools", "textures/planet_bar/rocket_y_main_orbit.png");
    @Unique
    private static final ResourceLocation ROCKET_Y_EARTH = new ResourceLocation("boss_tools", "textures/planet_bar/rocket_y_main_earth.png");
    @Unique
    private static final ResourceLocation ROCKET_Y = new ResourceLocation("boss_tools", "textures/planet_bar/rocket_y.png");

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 0))
    private static ResourceLocation bosstools_perf$storeWarningRl(String path, String namespace) {
        return WARNING;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 1))
    private static ResourceLocation bosstools_perf$storeTimer10Rl(String path, String namespace) {
        return TIMER10;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 2))
    private static ResourceLocation bosstools_perf$storeTimer9Rl(String path, String namespace) {
        return TIMER9;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 3))
    private static ResourceLocation bosstools_perf$storeTimer8Rl(String path, String namespace) {
        return TIMER8;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 4))
    private static ResourceLocation bosstools_perf$storeTimer7Rl(String path, String namespace) {
        return TIMER7;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 5))
    private static ResourceLocation bosstools_perf$storeTimer6Rl(String path, String namespace) {
        return TIMER6;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 6))
    private static ResourceLocation bosstools_perf$storeTimer5Rl(String path, String namespace) {
        return TIMER5;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 7))
    private static ResourceLocation bosstools_perf$storeTimer4Rl(String path, String namespace) {
        return TIMER4;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 8))
    private static ResourceLocation bosstools_perf$storeTimer3Rl(String path, String namespace) {
        return TIMER3;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 9))
    private static ResourceLocation bosstools_perf$storeTimer2Rl(String path, String namespace) {
        return TIMER2;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 10))
    private static ResourceLocation bosstools_perf$storeTimer1Rl(String path, String namespace) {
        return TIMER1;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 11))
    private static ResourceLocation bosstools_perf$storeOxygenEmptyRl(String path, String namespace) {
        return OXYGEN_EMPTY;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 12))
    private static ResourceLocation bosstools_perf$storeOxygenFullRl(String path, String namespace) {
        return OXYGEN_FULL;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 13))
    private static ResourceLocation bosstools_perf$storeMoonRl(String path, String namespace) {
        return BossToolsPerformance.MOON;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 14))
    private static ResourceLocation bosstools_perf$storeRocketYMoonRl(String path, String namespace) {
        return ROCKET_Y_MOON;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 15))
    private static ResourceLocation bosstools_perf$storeMarsRl(String path, String namespace) {
        return BossToolsPerformance.MARS;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 16))
    private static ResourceLocation bosstools_perf$storeRocketYMarsRl(String path, String namespace) {
        return ROCKET_Y_MARS;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 17))
    private static ResourceLocation bosstools_perf$storeMercuryRl(String path, String namespace) {
        return BossToolsPerformance.MERCURY;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 18))
    private static ResourceLocation bosstools_perf$storeRocketYMercuryRl(String path, String namespace) {
        return ROCKET_Y_MERCURY;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 19))
    private static ResourceLocation bosstools_perf$storeVenusRl(String path, String namespace) {
        return BossToolsPerformance.VENUS;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 20))
    private static ResourceLocation bosstools_perf$storeRocketYVenusRl(String path, String namespace) {
        return ROCKET_Y_VENUS;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 21))
    private static ResourceLocation bosstools_perf$storeRocketYOrbitRl(String path, String namespace) {
        return ROCKET_Y_ORBIT;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 22))
    private static ResourceLocation bosstools_perf$storeRocketYEarthRl(String path, String namespace) {
        return ROCKET_Y_EARTH;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 23))
    private static ResourceLocation bosstools_perf$storeRocketYRl(String path, String namespace) {
        return ROCKET_Y;
    }
}
