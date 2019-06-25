package biomesoplenty.common.world.gen.feature;

import biomesoplenty.common.util.block.IBlockPosQuery;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class BigPumpkinFeature extends Feature<NoFeatureConfig>
{
    protected IBlockPosQuery placeOn = (world, pos) -> world.getBlockState(pos).getBlock() == Blocks.GRASS_BLOCK;
    protected IBlockPosQuery replace = (world, pos) -> world.getBlockState(pos).canBeReplacedByLeaves(world, pos);

    public BigPumpkinFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserializer)
    {
        super(deserializer);
    }

    @Override
    public boolean func_212245_a(IWorld world, ChunkGenerator<? extends GenerationSettings> p_212245_2_, Random p_212245_3_, BlockPos startPos, NoFeatureConfig p_212245_5_)
    {
        while (startPos.getY() > 1 && this.replace.matches(world, startPos)) {startPos = startPos.down();}

        if (!this.placeOn.matches(world, startPos.add(2, 0, 2)))
        {
            // Abandon if we can't place the tree on this block
            return false;
        }

        if (!this.checkSpace(world, startPos.up()))
        {
            // Abandon if there isn't enough room
            return false;
        }

        BlockPos pos = startPos.up();

        for (int x = 1; x < 4; x++)
        {
            for (int y = 0; y < 5; y++)
            {
                for (int z = 1; z < 4; z++)
                {
                    this.setBlock(world, pos.add(x,y,z), Blocks.PUMPKIN.getDefaultState());
                }
            }
        }

        for (int x = 0; x < 5; x++)
        {
            for (int y = 1; y < 4; y++)
            {
                for (int z = 1; z < 4; z++)
                {
                    this.setBlock(world, pos.add(x,y,z), Blocks.PUMPKIN.getDefaultState());
                }
            }
        }

        for (int x = 1; x < 4; x++)
        {
            for (int y = 1; y < 4; y++)
            {
                for (int z = 0; z < 5; z++)
                {
                    this.setBlock(world, pos.add(x,y,z), Blocks.PUMPKIN.getDefaultState());
                }
            }
        }

        this.setBlock(world, pos.add(2,5,2), Blocks.OAK_LOG.getDefaultState());

        this.setBlock(world, pos.add(1,5,2), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
        this.setBlock(world, pos.add(0,4,1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));

        this.setBlock(world, pos.add(2,5,3), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
        this.setBlock(world, pos.add(3,5,3), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
        this.setBlock(world, pos.add(2,4,4), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
        this.setBlock(world, pos.add(3,4,4), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));

        this.setBlock(world, pos.add(3,5,1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
        this.setBlock(world, pos.add(3,4,0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
        this.setBlock(world, pos.add(4,4,2), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
        this.setBlock(world, pos.add(4,3,0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
        this.setBlock(world, pos.add(4,2,0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));

        return true;
    }

    public boolean setBlock(IWorld world, BlockPos pos, BlockState state)
    {
        if (this.replace.matches(world, pos))
        {
            this.setBlockState(world, pos, state);
            return true;
        }
        return false;
    }

    public boolean checkSpace(IWorld world, BlockPos pos)
    {
        for (int y = 0; y <= 6; y++)
        {
            for (int x = 0; x <= 5; x++)
            {
                for (int z = 0; z <= 5; z++)
                {
                    BlockPos pos1 = pos.add(x, y, z);
                    if (pos1.getY() >= 255 || !this.replace.matches(world, pos1))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}