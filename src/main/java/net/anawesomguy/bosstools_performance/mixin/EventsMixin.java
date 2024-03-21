package net.anawesomguy.bosstools_performance.mixin;

import net.anawesomguy.bosstools_performance.BossToolsPerformance;
import net.anawesomguy.bosstools_performance.PerformanceMethodes;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.events.Events;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.events.forgeevents.RenderHandItemEvent;
import net.mrscauthd.boss_tools.events.forgeevents.SetupLivingBipedAnimEvent;
import org.embeddedt.modernfix.forge.shadow.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.anawesomguy.bosstools_performance.BossToolsPerformance.ORBIT_WORLDS;
import static net.anawesomguy.bosstools_performance.BossToolsPerformance.SPACE_WORLDS;
import static net.anawesomguy.bosstools_performance.BossToolsPerformance.VENUS_KEY;

@Pseudo
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
            } else if (PerformanceMethodes.areBothRocketsOrRovers(player.getMainHandItem().getItem(), player.getOffhandItem().getItem())) {
                model.rightArm.xRot = 10F;
                model.leftArm.xRot = 10F;
                model.leftArm.zRot = 0F;
                model.rightArm.zRot = 0F;
                model.rightArm.yRot = 0F;
                model.leftArm.yRot = 0F;
            }
        }
    }

    @Inject(method = "ItemRender", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", ordinal = 0), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getOffhandItem()Lnet/minecraft/item/ItemStack;")), cancellable = true)
    private static void bosstools_performance$renderEventPerformance(RenderHandItemEvent.Pre event, CallbackInfo ci, @Local(ordinal = 0) Item mainHand, @Local(ordinal = 1) Item offHand) {
        for (Item item : PerformanceMethodes.ROCKETS_AND_ROVER)
            if (item == mainHand || item == offHand) {
                event.setCanceled(true);
                break;
            }
        ci.cancel();
    }

    @Redirect(method = "onLivingEntityTick", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;"), slice = @Slice(to = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 2)))
    private static ResourceLocation bosstools_performance$storeVenusRl(String namespace, String path) {
        return BossToolsPerformance.VENUS;
    }

    @Redirect(method = "onLivingEntityTick", at = @At(value = "NEW", target = "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/util/ResourceLocation;", ordinal = 2))
    private static ResourceLocation bosstools_performance$storeMercuryRl(String namespace, String path) {
        return BossToolsPerformance.MERCURY;
    }
}