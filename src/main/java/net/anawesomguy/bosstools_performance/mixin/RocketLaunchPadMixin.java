package net.anawesomguy.bosstools_performance.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.mrscauthd.boss_tools.block.RocketLaunchPad;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.mrscauthd.boss_tools.block.RocketLaunchPad.STAGE;

@Mixin(RocketLaunchPad.class) @SuppressWarnings("deprecation")
public abstract class RocketLaunchPadMixin extends Block {
    /**
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public void tick(@Nonnull BlockState state, @Nonnull ServerWorld world, @Nonnull BlockPos pos, @Nonnull Random random) {
        List<Integer[]> x = new ArrayList<>();
        x.add(new Integer[]{0, 1});
        x.add(new Integer[]{1, 1});
        x.add(new Integer[]{1, 0});
        x.add(new Integer[]{-1, 0});
        x.add(new Integer[]{-1, -1});
        x.add(new Integer[]{0, -1});
        x.add(new Integer[]{1, -1});
        x.add(new Integer[]{-1, 1});
        List<Integer[]> y = new ArrayList<>();
        y.add(new Integer[]{0, 2});
        y.add(new Integer[]{0, -2});
        y.add(new Integer[]{2, 0});
        y.add(new Integer[]{-2, 0});
        y.add(new Integer[]{-2, 1});
        y.add(new Integer[]{-2, -1});
        y.add(new Integer[]{2, 1});
        y.add(new Integer[]{1, -2});
        y.add(new Integer[]{2, -2});
        y.add(new Integer[]{-2, -2});
        y.add(new Integer[]{-1, -1});
        y.add(new Integer[]{3, 1});
        y.add(new Integer[]{1, 2});
        y.add(new Integer[]{2, 2});
        boolean canEdit = true;

        for(Integer[] value : x) {
            BlockPos bp = new BlockPos(pos.getX() + value[0], pos.getY(), pos.getZ() + value[1]);
            if (!(world.getBlockState(bp).getBlock() instanceof RocketLaunchPad) || world.getBlockState(bp).getValue(STAGE)) {
                canEdit = false;
            }
        }

        for(Integer[] val : y) {
            BlockPos bp = new BlockPos(pos.getX() + val[0], pos.getY(), pos.getZ() + val[1]);
            if (world.getBlockState(bp).getBlock() instanceof RocketLaunchPad && world.getBlockState(bp).getValue(STAGE)) {
                canEdit = false;
            }
        }

        if (canEdit) {
            world.setBlock(pos, state.setValue(STAGE, Boolean.TRUE), 2);
            world.updateNeighbourForOutputSignal(pos, this);
        } else if (state.getValue(STAGE)) {
            world.setBlock(pos, state.setValue(STAGE, Boolean.FALSE), 2);
            world.updateNeighbourForOutputSignal(pos, this);
        }

        world.getBlockTicks().scheduleTick(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), this, 1);
    }

    private RocketLaunchPadMixin(Properties settings) {
        super(settings);
    }
}
