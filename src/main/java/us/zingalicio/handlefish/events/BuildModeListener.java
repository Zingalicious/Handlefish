package us.zingalicio.handlefish.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import ru.tehkode.permissions.bukkit.PermissionsEx;

import us.zingalicio.handlefish.Handlefish;

public final class BuildModeListener implements Listener
{
	Handlefish plugin;
	private final static int REACH = 6;
	public BuildModeListener(Handlefish plugin)
	{
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		if(PermissionsEx.getUser(player).getOptionBoolean("buildmode", player.getWorld().getName(), false))
		{
			if(e.getAction() == Action.RIGHT_CLICK_AIR)
			{
				Block b = player.getTargetBlock(null, REACH);
				if(b.getType() != Material.AIR)
				{
					BlockFace bF = getBlockFace(b, player);
					ItemStack item = player.getItemInHand();
					if(item.getTypeId() < 256)
					{
						b.getRelative(bF).setType(item.getType());
						b.getRelative(bF).setData(item.getData().getData());
					}
					item.setAmount(item.getAmount() - 1);
					PlayerInteractEvent newEvent = new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK, e.getItem(), b, bF);
					Bukkit.getServer().getPluginManager().callEvent(newEvent);
				}
				else if(player.isSneaking())
				{
					BlockFace bF = getBlockFace(b, player);
					ItemStack item = player.getItemInHand();
					if(item.getTypeId() < 256)
					{
						b.getRelative(bF).setType(item.getType());
						b.getRelative(bF).setData(item.getData().getData());
					}
					item.setAmount(item.getAmount() - 1);
					PlayerInteractEvent newEvent = new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK, e.getItem(), b, bF);
					Bukkit.getServer().getPluginManager().callEvent(newEvent);
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private BlockFace getBlockFace(Block block, Player player)
	{
		BlockFace blockFace = null;

		ArrayList<Block> adjacentBlocks = new ArrayList<Block>();
		adjacentBlocks.add(block.getRelative(BlockFace.UP));
		adjacentBlocks.add(block.getRelative(BlockFace.DOWN));
		adjacentBlocks.add(block.getRelative(BlockFace.NORTH));
		adjacentBlocks.add(block.getRelative(BlockFace.SOUTH));
		adjacentBlocks.add(block.getRelative(BlockFace.EAST));
		adjacentBlocks.add(block.getRelative(BlockFace.WEST));
	
	for (Block b : adjacentBlocks)
	{
		if ((b == null) || (!(player.getLineOfSight(null, (Bukkit.getViewDistance() + 1) * 16).contains(b))))
		{
			continue;
		}
	      blockFace = block.getFace(b);
	      return blockFace;
	    }
	
	    return blockFace;
	  }
}
