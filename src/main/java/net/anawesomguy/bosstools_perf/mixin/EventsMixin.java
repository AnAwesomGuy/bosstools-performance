package net.anawesomguy.bosstools_perf.mixin;

import net.anawesomguy.bosstools_perf.BossToolsPerformance;
import net.anawesomguy.bosstools_perf.PerformanceMethodes;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.entity.LanderEntity;
import net.mrscauthd.boss_tools.events.Events;
import net.mrscauthd.boss_tools.events.Gravity;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.events.OxygenSystem;
import net.mrscauthd.boss_tools.events.forgeevents.SetupLivingBipedAnimEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.anawesomguy.bosstools_perf.BossToolsPerformance.ORBIT_WORLDS;
import static net.anawesomguy.bosstools_perf.BossToolsPerformance.SPACE_WORLDS;
import static net.anawesomguy.bosstools_perf.BossToolsPerformance.VENUS_KEY;

@Mixin(value = Events.class, remap = false)
public abstract class EventsMixin {
    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @SubscribeEvent @Overwrite
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            World world = event.world;
            RegistryKey<World> worldKey = world.dimension();
            if (SPACE_WORLDS.containsKey(worldKey) || ORBIT_WORLDS.containsKey(worldKey)) {
                if (worldKey != VENUS_KEY)
                    world.setRainLevel(0F);
                world.setThunderLevel(0F);
            }
        }
    }

    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @SubscribeEvent @Overwrite
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            PlayerEntity player = event.player;
            World world = player.level;
            if (player.getY() < 1) {
                Entity vehicle = player.getVehicle();
                if (vehicle instanceof LanderEntity) {
                    MinecraftServer server = world.getServer();
                    ServerWorld level;
                    if (server == null || (level = server.getLevel(ORBIT_WORLDS.get(world.dimension()))) == null)
                        return;
                    PerformanceMethodes.worldTeleport(player, level, 700)
                                       .startRiding(PerformanceMethodes.worldTeleport(vehicle, level, 700));
                } else
                    PerformanceMethodes.worldTeleport(player, ORBIT_WORLDS.get(world.dimension()), 450);
            }
            Methodes.openPlanetGui(player);
            OxygenSystem.OxygenSystem(player);
            Gravity.Gravity(player, Gravity.GravityType.PLAYER, world);
            Methodes.DropRocket(player);
        }
    }

    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @OnlyIn(Dist.CLIENT) @SubscribeEvent @Overwrite
    public static void setupPlayerAngles(SetupLivingBipedAnimEvent.Post event) {
        if (event.getLivingEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)event.getLivingEntity();
            BipedModel<?> model = event.getModel();
            if (Methodes.isRocket(player.getVehicle())) {
                model.rightLeg.xRot = 0F;
                model.leftLeg.xRot = 0F;
                model.leftLeg.yRot = .05235988F; // Math.toRadians(3F)
                model.rightLeg.yRot = .05235988F; // Math.toRadians(3F)
                model.leftArm.xRot = -.07F;
                model.rightArm.xRot = -.07F;
            } else if (PerformanceMethodes.isEitherRocketOrRover(player.getMainHandItem().getItem(), player.getOffhandItem().getItem())) {
                model.rightArm.xRot = 10F;
                model.leftArm.xRot = 10F;
                model.leftArm.zRot = 0F;
                model.rightArm.zRot = 0F;
                model.rightArm.yRot = 0F;
                model.leftArm.yRot = 0F;
            }
        }
    }

    @Inject(method = "ItemRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getMainHandItem()Lnet/minecraft/item/ItemStack;"), cancellable = true, remap = true)
    private static void bosstools_perf$renderEventPerformance(CallbackInfo ci) {
        ci.cancel();
    }

    @Redirect(method = "onLivingEntityTick", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;"), slice = @Slice(to = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 2)), require = 2)
    private static ResourceLocation bosstools_perf$storeVenusRl(String namespace, String path) {
        return BossToolsPerformance.VENUS;
    }

    @Redirect(method = "onLivingEntityTick", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 2))
    private static ResourceLocation bosstools_perf$storeMercuryRl(String namespace, String path) {
        return BossToolsPerformance.MERCURY;
    }
}
