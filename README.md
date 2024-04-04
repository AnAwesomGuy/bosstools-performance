## **DEPENDS ON MODERNFIX!** (and Space-BossTools)

A performance mod for Space-BossTools, and "optimizes" a lot of its absolutely _terrible_ code.

On its own with Space-BossTools, ModernFix, and Embeddium, this mod can save around 20% on _both_ the server and client, especially when you're actively on a planet/in orbit.<br>
However, when put into a larger modpack (for example, FTB Integration by Parts DX), it can still save up to 9% (even more, depending on how many entities are loaded), still on both sides.

The aims of this mod are to improve performance while using Space-BossTools, while still keeping the same features and being easily configurable.

## What does it do?

This mod is just a bunch of extremely simple but invasive fixes using mixin.

For example, it optimizes this code:

```java
@SubscribeEvent
public static void onLivingEntityTick(LivingUpdateEvent event) {
  LivingEntity entity = event.getEntityLiving();
  World world = entity.level;
  Methodes.EntityOxygen(entity, world);
  Gravity.Gravity(entity, Gravity.GravityType.LIVING, world);
  Methodes.VenusRain(entity, new ResourceLocation("boss_tools", "venus"));
  Methodes.VenusFire(entity, new ResourceLocation("boss_tools", "venus"),
                     new ResourceLocation("boss_tools", "mercury"));
}

// Gravity.class
public static void Gravity(LivingEntity entity, GravityType type, World world) {
    double moon = 0.03;
    double mars = 0.04;
    double mercury = 0.03;
    double venus = 0.04;
    double orbit = 0.02;
    if (Methodes.isWorld(world, new ResourceLocation("boss_tools", "moon")))
        gravityMath(type, entity, moon, -2.5F);
    
    if (Methodes.isWorld(world, new ResourceLocation("boss_tools", "mars")))
        gravityMath(type, entity, mars, -2.0F);
    
    if (Methodes.isWorld(world, new ResourceLocation("boss_tools", "mercury")))
        gravityMath(type, entity, mercury, -2.5F);
    
    if (Methodes.isWorld(world, new ResourceLocation("boss_tools", "venus")))
        gravityMath(type, entity, venus, -2.0F);
    
    if (Methodes.isOrbitWorld(world))
        gravityMath(type, entity, orbit, -2.5F);
}

// Methodes.class
public static boolean isSpaceWorld(World world) {
    // called every living entity tick (and every time a sound is played)
    return isWorld(world, new ResourceLocation("boss_tools", "moon"))
        || isWorld(world, new ResourceLocation("boss_tools", "moon_orbit"))
        || isWorld(world, new ResourceLocation("boss_tools", "mars"))
        || isWorld(world, new ResourceLocation("boss_tools", "mars_orbit"))
        || isWorld(world, new ResourceLocation("boss_tools", "mercury"))
        || isWorld(world, new ResourceLocation("boss_tools", "mercury_orbit"))
        || isWorld(world, new ResourceLocation("boss_tools", "venus"))
        || isWorld(world, new ResourceLocation("boss_tools", "venus_orbit"))
        || isWorld(world, new ResourceLocation("boss_tools", "overworld_orbit"));
}

public static boolean isWorld(World world, ResourceLocation loc) {
    // called around 15 times a living entity tick
    RegistryKey<World> world2 = world.dimension();
    return world2 == RegistryKey.create(Registry.DIMENSION_REGISTRY, loc);
}
```

into just this:

```java
@SubscribeEvent
public static void onLivingEntityTick(LivingUpdateEvent event) {
  LivingEntity entity = event.getEntityLiving();
  World world = entity.level;
  Methodes.EntityOxygen(entity, world);
  Gravity.Gravity(entity, Gravity.GravityType.LIVING, world);
  Methodes.VenusRain(entity, VENUS);
  Methodes.VenusFire(entity, VENUS, MERCURY);
}

// Gravity.class
public static void Gravity(LivingEntity entity, GravityType type, World world) {
    if (Methodes.isOrbitWorld(world))
        gravityMath(type, entity, .02, -2.5F);
    else {
        // BossToolsPerformance.SPACE_WORLDS is a map of RegistryKey<World>
        // to double, which represents the gravity (you can add your own)
        double gravity = SPACE_WORLDS.getDouble(world.dimension());
        if (gravity == gravity) // check nan
            gravityMath(type, entity, gravity, gravity < .04 ? -2.5F : 2F);
    }
}

// Methodes.class
public static boolean isSpaceWorld(World world) {
    RegistryKey<World> dimension = world.dimension();
    return SPACE_WORLDS.containsKey(dimension) ||
           ORBIT_WORLDS.containsKey(dimension);
}

public static boolean isWorld(World world, ResourceLocation loc) {
    // many methods were also redirected to not use this
    return world.dimension().location().equals(loc);
}
```

The mod is also supposed to be very configurable, and it even has hooks for registering your own space world or orbit world with custom gravities.

It also allows for translating the no fuel message at `tooltip.boss_tools.no_fuel` and the Planet Selection gui title at `gui.boss_tools.planet_selection`.