package net.anawesomguy.bosstools_performance;

import net.minecraftforge.common.ForgeConfigSpec;

public final class BossToolsPerformanceConfig {
    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final ForgeConfigSpec.IntValue MERCURY_STAR_COUNT, MERCURY_ORBIT_STAR_COUNT, VENUS_ORBIT_STAR_COUNT, OVERWORLD_ORBIT_STAR_COUNT, MOON_STAR_COUNT, MOON_ORBIT_STAR_COUNT, MARS_ORBIT_STAR_COUNT;
    public static final ForgeConfigSpec.BooleanValue RENDER_VENUS_RAIN, RENDER_VENUS_PARTICLES;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("Star Rendering")
               .comment(" The options below set the amount of stars to render on the skybox in the each dimension.",
                   " Set to 0 to have no stars render or -1 for the default behavior. (requires restart after changing to/from -1)");
        MERCURY_STAR_COUNT = builder.defineInRange("mercuryStarCount", 6000, -1, Integer.MAX_VALUE);
        MERCURY_ORBIT_STAR_COUNT = builder.defineInRange("mercuryOrbitStarCount", 6000, -1, Integer.MAX_VALUE);
        VENUS_ORBIT_STAR_COUNT = builder.defineInRange("venusOrbitStarCount", 6000, -1, Integer.MAX_VALUE);
        OVERWORLD_ORBIT_STAR_COUNT = builder.defineInRange("overworldOrbitStarCount", 6000, -1, Integer.MAX_VALUE);
        MOON_STAR_COUNT = builder.defineInRange("moonStarCount", 6000, -1, Integer.MAX_VALUE);
        MOON_ORBIT_STAR_COUNT = builder.defineInRange("moonOrbitStarCount", 6000, -1, Integer.MAX_VALUE);
        MARS_ORBIT_STAR_COUNT = builder.defineInRange("marsOrbitStarCount", 6000, -1, Integer.MAX_VALUE);
        builder.pop();
        RENDER_VENUS_RAIN = builder.comment(" Set to true to render the rain effect on Venus.")
                                   .define("renderVenusRain", false);
        RENDER_VENUS_PARTICLES = builder.comment(" Set to true to render the rain particles on Venus.")
                                        .define("renderVenusParticles", true);
        CONFIG_SPEC = builder.build();
    }

    private BossToolsPerformanceConfig() {
        throw new AssertionError();
    }
}
