package net.anawesomguy.bosstools_performance;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import net.mrscauthd.boss_tools.ModInnet;

import javax.annotation.Nullable;
import java.util.List;

public final class PerformanceMethodes {
    public static final ITextComponent NO_FUEL_MESSAGE = new TranslationTextComponent(
        "tooltip.boss_tools.no_fuel",
        new TranslationTextComponent("tooltip.boss_tools.no_fuel1").withStyle(TextFormatting.RED),
        new TranslationTextComponent("tooltip.boss_tools.no_fuel2",
            new TranslationTextComponent("fluid.boss_tools.fuel").withStyle(TextFormatting.RED)),
        new TranslationTextComponent("tooltip.boss_tools.no_fuel3").withStyle(TextFormatting.GOLD)
    ), PLANET_SELECTION = new TranslationTextComponent("gui.boss_tools.planet_selection");
    public static final List<Item> ROCKETS_AND_ROVER = ImmutableList.of(
        ModInnet.TIER_1_ROCKET_ITEM.get(), ModInnet.ROVER_ITEM.get(),
        ModInnet.TIER_2_ROCKET_ITEM.get(), ModInnet.TIER_3_ROCKET_ITEM.get()
    );

    public static @Nullable <T extends Entity> T worldTeleport(T entity, RegistryKey<World> worldKey, double high) {
        MinecraftServer server = entity.getServer();
        ServerWorld level;
        if (server == null || (level = server.getLevel(worldKey)) == null)
            return null;
        return teleportTo(entity, level, entity.getX(), high, entity.getZ());
    }

    public static <T extends Entity> T worldTeleport(T entity, ServerWorld level, double high) {
        return teleportTo(entity, level, entity.getX(), high, entity.getZ());
    }

    @SuppressWarnings("unchecked")
    public static <T extends Entity> T teleportTo(T entity, ServerWorld level, double x, double y, double z) {
        float yaw = entity.yRot, pitch = entity.xRot;

        if (entity instanceof ServerPlayerEntity) {
            ChunkPos chunkPos = new ChunkPos(new BlockPos(x, y, z));
            level.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkPos, 1, entity.getId());
            entity.stopRiding();
            if (level == entity.level)
                ((ServerPlayerEntity)entity).connection.teleport(x, y, z, yaw, pitch, ImmutableSet.of());
            else
                ((ServerPlayerEntity)entity).teleportTo(level, x, y, z, yaw, pitch);
            entity.setYHeadRot(yaw);
        } else {
            float yaw1 = MathHelper.wrapDegrees(yaw);
            if (level == entity.level) {
                entity.moveTo(x, y, z, yaw1, MathHelper.clamp(MathHelper.wrapDegrees(pitch), -90F, 90F));
                entity.setYHeadRot(yaw1);
            } else {
                entity.unRide();
                T oldEntity = entity;
                entity = (T)oldEntity.getType().create(level);
                if (entity == null) return oldEntity;
                entity.restoreFrom(oldEntity);
                entity.moveTo(x, y, z, yaw1, MathHelper.clamp(MathHelper.wrapDegrees(pitch), -90F, 90F));
                entity.setYHeadRot(yaw1);
                level.addFromAnotherDimension(entity);
            }
        }

        if (!(entity instanceof LivingEntity) || !((LivingEntity)entity).isFallFlying()) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1, 0, 1));
            entity.setOnGround(true);
        }

        if (entity instanceof CreatureEntity)
            ((CreatureEntity)entity).getNavigation().stop();

        return entity;
    }

    public static boolean isBothRocketOrRover(Item item1, Item item2) {
        if (item1 == null || item2 == null)
            return false;
        Item found = null;
        for (Item item : ROCKETS_AND_ROVER)
            if (item == item1) {
                if (found == null) {
                    if (item1 == item2)
                        return true;
                    found = item1;
                } else return true;
            } else if (item == item2)
                if (found == null)
                    found = item2;
                else return true;
        return false;
    }

    public static boolean isEitherRocketOrRover(Item item1, Item item2) {
        if (item1 == null || item2 == null)
            return false;
        for (Item item : ROCKETS_AND_ROVER)
            if (item == item1 || item == item2)
                return true;
        return false;
    }

    private PerformanceMethodes() {
        throw new AssertionError();
    }
}
