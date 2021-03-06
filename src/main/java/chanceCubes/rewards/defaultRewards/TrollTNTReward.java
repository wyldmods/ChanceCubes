package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import com.google.gson.JsonObject;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class TrollTNTReward extends BaseCustomReward
{
	public TrollTNTReward()
	{
		super(CCubesCore.MODID + ":troll_tnt", -5);
	}

	@Override
	public void trigger(ServerWorld world, BlockPos pos, final PlayerEntity player, JsonObject settings)
	{
		for(int x = -1; x < 2; x++)
		{
			for(int z = -1; z < 2; z++)
			{
				RewardsUtil.placeBlock(Blocks.COBWEB.getDefaultState(), world, new BlockPos(player.getPosX() + x, player.getPosY(), player.getPosZ() + z));
			}
		}

		final TNTEntity entitytntprimed = new TNTEntity(world, player.getPosX() + 1D, player.getPosY() + 1D, player.getPosZ(), player);
		world.addEntity(entitytntprimed);
		world.playSound(player, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);

		int outOf = super.getSettingAsInt(settings, "isReal", 2, 0, 100);

		if(RewardsUtil.rand.nextInt(100) < outOf)
		{
			Scheduler.scheduleTask(new Task("TrollTNT", 77)
			{
				@Override
				public void callback()
				{
					RewardsUtil.sendMessageToPlayer(player, "BOOM");
					entitytntprimed.remove();
				}
			});
		}
	}
}