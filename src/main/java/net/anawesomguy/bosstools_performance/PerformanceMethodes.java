package net.anawesomguy.bosstools_performance;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.PortalInfo;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import net.mrscauthd.boss_tools.ModInnet;

import java.util.List;
import java.util.function.Function;

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

    public static void worldTeleport(Entity entity, RegistryKey<World> worldKey, double high) {
        MinecraftServer server = entity.getServer();
        ServerWorld level;
        if (server == null || (level = server.getLevel(worldKey)) == null) return;
        double x = entity.getX(), z = entity.getZ();
        float yaw = entity.yRot, pitch = entity.xRot;
        if (entity instanceof ServerPlayerEntity) {
            ChunkPos chunkpos = new ChunkPos(new BlockPos(x, high, z));
            level.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkpos, 1, entity.getId());
            entity.stopRiding();
            if (level == entity.level)
                ((ServerPlayerEntity)entity).connection.teleport(x, high, z, yaw, pitch, ImmutableSet.of());
            else
                ((ServerPlayerEntity)entity).teleportTo(level, x, high, z, yaw, pitch);
            entity.setYHeadRot(yaw);
        } else {
            float yaw1 = MathHelper.wrapDegrees(yaw);
            float pitch1 = MathHelper.clamp(MathHelper.wrapDegrees(pitch), -90.0F, 90.0F);
            if (level == entity.level) {
                entity.moveTo(x, high, z, yaw1, pitch1);
                entity.setYHeadRot(yaw1);
            } else {
                entity.unRide();
                Entity newEntity = entity.getType().create(level);
                if (newEntity == null) return;
                newEntity.restoreFrom(entity);
                newEntity.moveTo(x, high, z, yaw1, pitch1);
                newEntity.setYHeadRot(yaw1);
                level.addFromAnotherDimension(newEntity);
            }
        }

        if (!(entity instanceof LivingEntity) || !((LivingEntity)entity).isFallFlying()) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1D, 0D, 1D));
            entity.setOnGround(true);
        }

        if (entity instanceof CreatureEntity)
            ((CreatureEntity)entity).getNavigation().stop();
    }

    public static boolean isRocketOrRover(Item item) {
        return ROCKETS_AND_ROVER.contains(item);
    }

    public static boolean areBothRocketsOrRovers(Item item1, Item item2) {
        if (item1 == null || item2 == null)
            return false;
        Item found = null;
        for (Item item : ROCKETS_AND_ROVER) {
            if (item == item1) {
                if (found == null) {
                    if (item1 == item2)
                        return true;
                    found = item1;
                } else return true;
            } else if (item == item2) {
                if (found == null)
                    found = item2;
                else return true;
            }
        }
        return false;
    }

    private static final class Teleporter extends net.minecraft.world.Teleporter {
        private final Vector3d pos;

        Teleporter(ServerWorld world, Vector3d pos) {
            super(world);
            this.pos = pos;
        }

        @Override
        public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
            return repositionEntity.apply(false);
        }

        @Override
        public PortalInfo getPortalInfo(Entity entity, ServerWorld destWorld, Function<ServerWorld, PortalInfo> defaultPortalInfo) {
            return new PortalInfo(pos, Vector3d.ZERO, entity.yRot, entity.xRot);
        }
    }

    private PerformanceMethodes() {
        throw new AssertionError();
    }
}
