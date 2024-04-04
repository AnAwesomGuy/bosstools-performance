package net.anawesomguy.bosstools_performance.mixin;

import net.anawesomguy.bosstools_performance.PerformanceMethodes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.LanderEntity;
import net.mrscauthd.boss_tools.events.Config;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.events.forgeevents.LivingSetFireInHotPlanetEvent;
import net.mrscauthd.boss_tools.events.forgeevents.LivingSetVenusRainEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.anawesomguy.bosstools_performance.BossToolsPerformance.*;
import static net.minecraft.inventory.EquipmentSlotType.*;
import static net.mrscauthd.boss_tools.ModInnet.*;

@Mixin(value = Methodes.class, remap = false)
public abstract class MethodesMixin {
    @Redirect(method = "createSpaceStation", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;"))
    private static ResourceLocation bosstools_performance$spaceStation(String namespace, String path) {
        return SPACE_STATION;
    }

    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public static void RocketSounds(Entity entity, World world) {
        world.playSound(null, entity, ROCKET_SOUND.get(), SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }

    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public static void EntityOxygen(LivingEntity entity, World world) {
        if (Config.EntityOxygenSystem && !world.isClientSide
            && entity.tickCount % 15 == 0 && isSpaceWorld(world)
            && OXYGEN.contains(entity.getType())
            && !entity.hasEffect(ModInnet.OXYGEN_EFFECT.get())) {
            Methodes.OxygenDamage(entity);
        }
    }

    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public static void playerFalltoPlanet(World world, PlayerEntity player) {
        PerformanceMethodes.worldTeleport(player, ORBIT_WORLDS.get(world.dimension()), 450);
    }

    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public static void landerTeleportOrbit(PlayerEntity player, World world) {
        Entity vehicle = player.getVehicle();
        if (vehicle instanceof LanderEntity && player.getY() < 1) {
            MinecraftServer server = world.getServer();
            ServerWorld level;
            if (server == null || (level = server.getLevel(ORBIT_WORLDS.get(world.dimension()))) == null)
                return;
            PerformanceMethodes.worldTeleport(player, level, 700D)
                               .startRiding(PerformanceMethodes.worldTeleport(vehicle, level, 700D));
        }
    }

    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public static void landerTeleport(PlayerEntity player, ResourceLocation newPlanet) {
        Entity vehicle = player.getVehicle();
        if (vehicle instanceof LanderEntity && player.getY() < 1) {
            RegistryKey<World> worldKey = RegistryKey.create(Registry.DIMENSION_REGISTRY, newPlanet);
            PerformanceMethodes.worldTeleport(player, worldKey, 700D);
            PerformanceMethodes.worldTeleport(vehicle, worldKey, 700D);
        }
    }

    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public static void DropRocket(PlayerEntity player) {
        if (!player.level.isClientSide) {
            ItemStack offHandStack = player.getOffhandItem();
            if (PerformanceMethodes.isBothRocketOrRover(player.getMainHandItem().getItem(), offHandStack.getItem())) {
                ItemEntity itemEntity = player.drop(player.inventory.offhand.set(0, ItemStack.EMPTY), false);
                if (itemEntity != null) {
                    itemEntity.setNoPickUpDelay();
                    itemEntity.setOwner(player.getUUID());
                }
            }
        }
    }

    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public static boolean isSpaceWorld(World world) {
        RegistryKey<World> dimension = world.dimension();
        return SPACE_WORLDS.containsKey(dimension) || ORBIT_WORLDS.containsKey(dimension);
    }

    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public static boolean isOrbitWorld(World world) {
        return ORBIT_WORLDS.containsKey(world.dimension());
    }

    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public static void VenusRain(LivingEntity entity, ResourceLocation planet) {
        if (isWorld(entity.level, planet) &&
            ((EntityAccessor)entity).callIsInRain() &&
            !VENUS_RAIN.contains(entity.getType()) &&
            !MinecraftForge.EVENT_BUS.post(new LivingSetVenusRainEvent(entity))) {
            entity.hurt(DAMAGE_SOURCE_ACID_RAIN, 1F);
        }
    }

    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public static void VenusFire(LivingEntity entity, ResourceLocation planet1, ResourceLocation planet2) {
        ResourceLocation key = entity.level.dimension().location();
        if ((key.equals(planet1) || key.equals(planet2)) &&
            !nethriteSpaceSuitCheck(entity) &&
            !VENUS_FIRE.contains(entity.getType()) &&
            !MinecraftForge.EVENT_BUS.post(new LivingSetFireInHotPlanetEvent(entity))) {
            entity.setSecondsOnFire(10);
        }
    }

    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public static boolean isWorld(World world, ResourceLocation loc) {
        return world.dimension().location().equals(loc);
    }

    /**
     * Allows the no fuel message to be translated at {@code "tooltip.boss_tools.no_fuel"}.
     *
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public static void noFuelMessage(PlayerEntity player) {
        if (!player.level.isClientSide)
            player.displayClientMessage(PerformanceMethodes.NO_FUEL_MESSAGE, false);
    }

    /**
     * there's also a typo in the name
     *
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public static boolean nethriteSpaceSuitCheck(LivingEntity entity) {
        return entity.getItemBySlot(FEET).getItem() == SPACE_BOOTS.get() && entity.getItemBySlot(LEGS).getItem() == SPACE_PANTS.get() && entity.getItemBySlot(CHEST).getItem() == SPACE_SUIT.get() && entity.getItemBySlot(HEAD).getItem() == OXYGEN_MASK.get();
    }

    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public static boolean spaceSuitCheck(LivingEntity entity) {
        return entity.getItemBySlot(FEET).getItem() == NETHERITE_SPACE_BOOTS.get() && entity.getItemBySlot(LEGS).getItem() == NETHERITE_SPACE_PANTS.get() && entity.getItemBySlot(CHEST).getItem() == NETHERITE_SPACE_SUIT.get() && entity.getItemBySlot(HEAD).getItem() == NETHERITE_OXYGEN_MASK.get();
    }

    /**
     * (yes ik its very ugly code)
     *
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public static boolean spaceSuitCheckBoth(LivingEntity entity) {
        return (entity.getItemBySlot(FEET).getItem() == SPACE_BOOTS.get() || entity.getItemBySlot(FEET).getItem() == NETHERITE_SPACE_BOOTS.get()) && (entity.getItemBySlot(LEGS).getItem() == SPACE_PANTS.get() || entity.getItemBySlot(LEGS).getItem() == NETHERITE_SPACE_PANTS.get()) && (entity.getItemBySlot(CHEST).getItem() == SPACE_SUIT.get() || entity.getItemBySlot(CHEST).getItem() == NETHERITE_SPACE_SUIT.get()) && (entity.getItemBySlot(HEAD).getItem() == OXYGEN_MASK.get() || entity.getItemBySlot(HEAD).getItem() == NETHERITE_OXYGEN_MASK.get());
    }
}
