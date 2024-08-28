package net.anawesomguy.bosstools_perf.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.mrscauthd.boss_tools.block.RocketLaunchPad;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nonnull;
import java.util.Random;

import static net.mrscauthd.boss_tools.block.RocketLaunchPad.STAGE;

@Mixin(RocketLaunchPad.class) @SuppressWarnings("deprecation")
public abstract class RocketLaunchPadMixin extends Block {
    @Unique
    private static final int[][] INNER = {
        new int[]{0, 1}, new int[]{1, 1},
        new int[]{1, 0}, new int[]{-1, 0},
        new int[]{-1, -1}, new int[]{0, -1},
        new int[]{1, -1}, new int[]{-1, 1}
    };
    @Unique
    private static final int[][] OUTER = {
        new int[]{2, 1}, new int[]{2, 0},
        new int[]{2, -1}, new int[]{2, -2},
        new int[]{1, -2}, new int[]{0, -2},
        new int[]{-1, -2}, new int[]{-2, -2}
    };

    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public void tick(@Nonnull BlockState state, @Nonnull ServerWorld world, @Nonnull BlockPos pos, @Nonnull Random random) {
        boolean canEdit = true;

        for (int[] val : INNER) {
            BlockPos bp = new BlockPos(pos.getX() + val[0], pos.getY(), pos.getZ() + val[1]);
            if (world.getBlockState(bp).getBlock() instanceof RocketLaunchPad && !world.getBlockState(bp).getValue(STAGE))
                continue;
            canEdit = false;
            break;
        }

        if (canEdit)
            for (int[] val : OUTER) {
                BlockPos bp = new BlockPos(pos.getX() + val[0], pos.getY(), pos.getZ() + val[1]);
                if (world.getBlockState(bp).getBlock() instanceof RocketLaunchPad && world.getBlockState(bp).getValue(STAGE)) {
                    canEdit = false;
                    break;
                }
            }

        if (canEdit) {
            world.setBlock(pos, state.setValue(STAGE, Boolean.TRUE), 2);
            world.updateNeighbourForOutputSignal(pos, this);
        } else if (state.getValue(STAGE)) {
            world.setBlock(pos, state.setValue(STAGE, Boolean.FALSE), 2);
            world.updateNeighbourForOutputSignal(pos, this);
        }

        world.getBlockTicks().scheduleTick(pos, this, 1);
    }

    private RocketLaunchPadMixin(Properties settings) {
        super(settings);
    }
}
