package net.anawesomguy.bosstools_performance.mixin.client;

import net.anawesomguy.bosstools_performance.BossToolsPerformance;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.events.OverlayEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// don't really know how to optimize this further, this stuff doesn't really make too much sense to me
@Mixin(value = OverlayEvents.class, remap = false)
public abstract class OverlayEventsMixin {
    @Unique
    private static final ResourceLocation WARNING = new ResourceLocation("boss_tools", "textures/overlay/warning.png");
    @Unique
    private static final ResourceLocation TIMER10 = new ResourceLocation("boss_tools", "textures/overlay/timer10.png");
    @Unique
    private static final ResourceLocation TIMER9 = new ResourceLocation("boss_tools", "textures/overlay/timer9.png");
    @Unique
    private static final ResourceLocation TIMER8 = new ResourceLocation("boss_tools", "textures/overlay/timer8.png");
    @Unique
    private static final ResourceLocation TIMER7 = new ResourceLocation("boss_tools", "textures/overlay/timer7.png");
    @Unique
    private static final ResourceLocation TIMER6 = new ResourceLocation("boss_tools", "textures/overlay/timer6.png");
    @Unique
    private static final ResourceLocation TIMER5 = new ResourceLocation("boss_tools", "textures/overlay/timer5.png");
    @Unique
    private static final ResourceLocation TIMER4 = new ResourceLocation("boss_tools", "textures/overlay/timer4.png");
    @Unique
    private static final ResourceLocation TIMER3 = new ResourceLocation("boss_tools", "textures/overlay/timer3.png");
    @Unique
    private static final ResourceLocation TIMER2 = new ResourceLocation("boss_tools", "textures/overlay/timer2.png");
    @Unique
    private static final ResourceLocation TIMER1 = new ResourceLocation("boss_tools", "textures/overlay/timer1.png");
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
    private static ResourceLocation bosstools_performance$storeWarningRl(String path, String namespace) {
        return WARNING;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 1))
    private static ResourceLocation bosstools_performance$storeTimer10Rl(String path, String namespace) {
        return TIMER10;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 2))
    private static ResourceLocation bosstools_performance$storeTimer9Rl(String path, String namespace) {
        return TIMER9;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 3))
    private static ResourceLocation bosstools_performance$storeTimer8Rl(String path, String namespace) {
        return TIMER8;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 4))
    private static ResourceLocation bosstools_performance$storeTimer7Rl(String path, String namespace) {
        return TIMER7;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 5))
    private static ResourceLocation bosstools_performance$storeTimer6Rl(String path, String namespace) {
        return TIMER6;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 6))
    private static ResourceLocation bosstools_performance$storeTimer5Rl(String path, String namespace) {
        return TIMER5;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 7))
    private static ResourceLocation bosstools_performance$storeTimer4Rl(String path, String namespace) {
        return TIMER4;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 8))
    private static ResourceLocation bosstools_performance$storeTimer3Rl(String path, String namespace) {
        return TIMER3;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 9))
    private static ResourceLocation bosstools_performance$storeTimer2Rl(String path, String namespace) {
        return TIMER2;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 10))
    private static ResourceLocation bosstools_performance$storeTimer1Rl(String path, String namespace) {
        return TIMER1;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 11))
    private static ResourceLocation bosstools_performance$storeOxygenEmptyRl(String path, String namespace) {
        return OXYGEN_EMPTY;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 12))
    private static ResourceLocation bosstools_performance$storeOxygenFullRl(String path, String namespace) {
        return OXYGEN_FULL;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 13))
    private static ResourceLocation bosstools_performance$storeMoonRl(String path, String namespace) {
        return BossToolsPerformance.MOON;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 14))
    private static ResourceLocation bosstools_performance$storeRocketYMoonRl(String path, String namespace) {
        return ROCKET_Y_MOON;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 15))
    private static ResourceLocation bosstools_performance$storeMarsRl(String path, String namespace) {
        return BossToolsPerformance.MARS;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 16))
    private static ResourceLocation bosstools_performance$storeRocketYMarsRl(String path, String namespace) {
        return ROCKET_Y_MARS;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 17))
    private static ResourceLocation bosstools_performance$storeMercuryRl(String path, String namespace) {
        return BossToolsPerformance.MERCURY;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 18))
    private static ResourceLocation bosstools_performance$storeRocketYMercuryRl(String path, String namespace) {
        return ROCKET_Y_MERCURY;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 19))
    private static ResourceLocation bosstools_performance$storeVenusRl(String path, String namespace) {
        return BossToolsPerformance.VENUS;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 20))
    private static ResourceLocation bosstools_performance$storeRocketYVenusRl(String path, String namespace) {
        return ROCKET_Y_VENUS;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 21))
    private static ResourceLocation bosstools_performance$storeRocketYOrbitRl(String path, String namespace) {
        return ROCKET_Y_ORBIT;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 22))
    private static ResourceLocation bosstools_performance$storeRocketYEarthRl(String path, String namespace) {
        return ROCKET_Y_EARTH;
    }

    @Redirect(method = "Overlay", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 23))
    private static ResourceLocation bosstools_performance$storeRocketYRl(String path, String namespace) {
        return ROCKET_Y;
    }
}
