package net.azagwen.atbyw.blocks;

import net.azagwen.atbyw.blocks.state.AtbywProperties;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SpikeTrapBlock extends Block {
    private final Block spikeBlock;
    private final float strength;
    public static final BooleanProperty ACTIVE;
    public static final BooleanProperty CAN_BREAK;

    public SpikeTrapBlock(Block spikeBlock, float strength, Settings settings) {
        super(settings);
        this.spikeBlock = spikeBlock;
        this.strength = strength;
        this.setDefaultState(this.getDefaultState().with(ACTIVE, false).with(CAN_BREAK, false));
    }

    private boolean isNeighBorBreakable(World world, BlockPos pos) {
        BlockState upState = world.getBlockState(pos.up());

        boolean negativeHardness = upState.getHardness(world, pos.up()) < 0.0F;
        boolean aboveOneHardness = upState.getHardness(world, pos.up()) < strength;
        boolean isSpikeBlock = upState.getBlock() instanceof SpikeBlock;

        return aboveOneHardness && !negativeHardness && !isSpikeBlock;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
            if (world.isReceivingRedstonePower(pos)) {
                world.getBlockTickScheduler().schedule(pos, this, 4);
            } else {
                retract(world, pos);
            }

            world.setBlockState(pos, this.getDefaultState().with(ACTIVE, world.isReceivingRedstonePower(pos)).with(CAN_BREAK, isNeighBorBreakable(world, pos)));
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.isAir(pos.up()) || world.isWater(pos.up())) {
            placeSpikes(state, world, pos);
        } else if (state.get(CAN_BREAK)) {
            world.breakBlock(pos.up(), true);
            placeSpikes(state, world, pos);
        } else {
            if (world.getBlockState(pos.up()).isSolidBlock(world, pos.up())) {
                if (state.get(ACTIVE)) {
                    world.playSound(null, pos, SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.BLOCKS, 0.5F, 1.5F);
                }
            }
        }
    }

    private void retract(World world, BlockPos pos) {
        if (world.getBlockState(pos.up()).getBlock() instanceof SpikeBlock) {
            world.removeBlock(pos.up(), false);
            world.playSound(null, pos, SoundEvents.BLOCK_CHAIN_HIT, SoundCategory.BLOCKS, 0.5F, 0.5F);
        }
    }

    private void placeSpikes(BlockState state, World world, BlockPos pos) {
        if (state.get(ACTIVE)) {
            world.setBlockState(pos.up(), spikeBlock.getDefaultState());
            world.playSound(null, pos, SoundEvents.BLOCK_CHAIN_HIT, SoundCategory.BLOCKS, 0.5F, 1);
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.getBlockState(pos.up()).getBlock() instanceof SpikeBlock) {
            world.breakBlock(pos.up(), false, player);
        }

        super.onBreak(world, pos, state, player);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();

        if (world.isReceivingRedstonePower(pos)) {
            world.getBlockTickScheduler().schedule(pos, this, 4);
        } else {
            retract(world, pos);
        }

        return this.getDefaultState().with(ACTIVE, world.isReceivingRedstonePower(pos)).with(CAN_BREAK, isNeighBorBreakable(world, pos));
    }

    protected void appendProperties(StateManager.Builder<net.minecraft.block.Block, BlockState> builder) {
        builder.add(ACTIVE, CAN_BREAK);
    }


    static {
        ACTIVE = AtbywProperties.ACTIVE;
        CAN_BREAK = AtbywProperties.CAN_BREAK;
    }
}