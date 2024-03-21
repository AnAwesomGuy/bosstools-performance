package net.anawesomguy.bosstools_performance;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMaps;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static net.minecraft.util.registry.Registry.DIMENSION_REGISTRY;

@Mod.EventBusSubscriber
@Mod(BossToolsPerformance.MOD_ID)
public final class BossToolsPerformance {
    public static final String MOD_ID = "bosstools-performance";

    // BossTools constants
    public static final ResourceLocation
        SPACE_STATION = new ResourceLocation("boss_tools", "space_station"),
        MERCURY_ORBIT = new ResourceLocation("boss_tools", "mercury_orbit"),
        VENUS_ORBIT = new ResourceLocation("boss_tools", "venus_orbit"),
        OVERWORLD_ORBIT = new ResourceLocation("boss_tools", "overworld_orbit"),
        MOON_ORBIT = new ResourceLocation("boss_tools", "moon_orbit"),
        MARS_ORBIT = new ResourceLocation("boss_tools", "mars_orbit"),
        MERCURY = new ResourceLocation("boss_tools", "mercury"),
        VENUS = new ResourceLocation("boss_tools", "venus"),
        MOON = new ResourceLocation("boss_tools", "moon"),
        MARS = new ResourceLocation("boss_tools", "mars"),
        OXYGEN_AFFECTED = new ResourceLocation("boss_tools", "entities/oxygen"),
        VENUS_FIRE_UNAFFECTED = new ResourceLocation("boss_tools", "entities/venus_fire"),
        VENUS_RAIN_UNAFFECTED = new ResourceLocation("boss_tools", "entities/venus_rain");
    public static final RegistryKey<World>
        MERCURY_ORBIT_KEY = RegistryKey.create(DIMENSION_REGISTRY, MERCURY_ORBIT),
        VENUS_ORBIT_KEY = RegistryKey.create(DIMENSION_REGISTRY, VENUS_ORBIT),
        OVERWORLD_ORBIT_KEY = RegistryKey.create(DIMENSION_REGISTRY, OVERWORLD_ORBIT),
        MOON_ORBIT_KEY = RegistryKey.create(DIMENSION_REGISTRY, MOON_ORBIT),
        MARS_ORBIT_KEY = RegistryKey.create(DIMENSION_REGISTRY, MARS_ORBIT),
        MERCURY_KEY = RegistryKey.create(DIMENSION_REGISTRY, MERCURY),
        VENUS_KEY = RegistryKey.create(DIMENSION_REGISTRY, VENUS),
        MOON_KEY = RegistryKey.create(DIMENSION_REGISTRY, MOON),
        MARS_KEY = RegistryKey.create(DIMENSION_REGISTRY, MARS);
    public static final ITag.INamedTag<EntityType<?>>
        OXYGEN = EntityTypeTags.createOptional(OXYGEN_AFFECTED),
        VENUS_FIRE = EntityTypeTags.createOptional(VENUS_FIRE_UNAFFECTED),
        VENUS_RAIN = EntityTypeTags.createOptional(VENUS_RAIN_UNAFFECTED);

    private static final Object2DoubleMap<RegistryKey<World>> SPACE_WORLDS_INTERNAL = new Object2DoubleOpenHashMap<>();
    private static final Map<RegistryKey<World>, RegistryKey<World>> ORBIT_WORLDS_INTERNAL = new HashMap<>();
    /**
     * A map of space world to gravity.
     * Do not modify this, it is immutable.
     * 
     * @see #registerSpaceWorld
     */
    public static final Object2DoubleMap<RegistryKey<World>> SPACE_WORLDS = Object2DoubleMaps.unmodifiable(SPACE_WORLDS_INTERNAL);
    /**
     * A map of orbit worlds to its respective planet.
     * Do not modify this.
     * 
     * @see #registerOrbitWorld
     */
    public static final Map<RegistryKey<World>, RegistryKey<World>> ORBIT_WORLDS = Collections.unmodifiableMap(ORBIT_WORLDS_INTERNAL);

    /**
     * Registers a space world ({@link net.mrscauthd.boss_tools.events.Methodes#isSpaceWorld} will return true if {@link World#dimension() world.dimension()} {@code == key}).
     * <p>
     * {@linkplain #registerOrbitWorld Orbit worlds} are also included in space worlds.
     * <p>
     * For item entities, {@code gravity} is increased by {@code .02}.
     *
     * @param key the key to add as a space world
     * @param gravity the amount of gravity for entities in the space world
     */
    public static void registerSpaceWorld(@Nonnull RegistryKey<World> key, double gravity) {
        if (Double.isNaN(gravity))
            throw new IllegalArgumentException(String.valueOf(gravity));
        SPACE_WORLDS_INTERNAL.put(Objects.requireNonNull(key), gravity);
    }

    /**
     * Registers a space world ({@link net.mrscauthd.boss_tools.events.Methodes#isOrbitWorld} will return true if {@link World#dimension() dimension()} {@code == orbitWorldKey}).
     * <p>
     * Orbit worlds are also included in {@linkplain #registerSpaceWorld space worlds} and have {@code .02} gravity for living entities and {@code .05} gravity for item entities.
     *
     * @param orbitWorldKey the key to add as an orbit world
     * @param spaceWorldKey the key to add as the space world for this orbit world
     */
    public static void registerOrbitWorld(@Nonnull RegistryKey<World> orbitWorldKey, @Nonnull RegistryKey<World> spaceWorldKey) {
        ORBIT_WORLDS_INTERNAL.put(Objects.requireNonNull(orbitWorldKey), spaceWorldKey);
    }

    static {
        SPACE_WORLDS_INTERNAL.defaultReturnValue(Double.NaN);
        SPACE_WORLDS_INTERNAL.put(MERCURY_KEY, .03);
        SPACE_WORLDS_INTERNAL.put(VENUS_KEY, .04);
        SPACE_WORLDS_INTERNAL.put(MOON_KEY, .03);
        SPACE_WORLDS_INTERNAL.put(MARS_KEY, .04);

        ORBIT_WORLDS_INTERNAL.put(MERCURY_ORBIT_KEY, MERCURY_KEY);
        ORBIT_WORLDS_INTERNAL.put(VENUS_ORBIT_KEY, VENUS_KEY);
        ORBIT_WORLDS_INTERNAL.put(OVERWORLD_ORBIT_KEY, World.OVERWORLD);
        ORBIT_WORLDS_INTERNAL.put(MOON_ORBIT_KEY, MOON_KEY);
        ORBIT_WORLDS_INTERNAL.put(MARS_ORBIT_KEY, MARS_KEY);
    }

    public BossToolsPerformance() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, BossToolsPerformanceConfig.CONFIG_SPEC);
    }
}
