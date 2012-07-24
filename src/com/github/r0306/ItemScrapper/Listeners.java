package com.github.r0306.ItemScrapper;

import java.io.IOException;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Listeners implements Listener
{
	
	private HashMap<String, Integer> breaking = new HashMap<String, Integer>();
	
	@EventHandler (ignoreCancelled = true)
	public void creationListener(BlockPlaceEvent event) throws IOException
	{
		
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		Config config = new Config();
		
		if (config.matches(block))
		{
			
			if (config.canCreate(player))
			{

				new Disassembler(block.getLocation());
				player.sendMessage(ChatColor.YELLOW + "[Item Scrapper] " + ChatColor.GREEN + "Created new scrapper. Left click with item in hand to disassemble the item.");
				
			}
			
		}
		
	}
	
	@EventHandler (ignoreCancelled = true)
	public void disassembleListener(PlayerInteractEvent event)
	{
		
		Player player = event.getPlayer();
		
		if (event.getAction() == Action.LEFT_CLICK_BLOCK)
		{
			
			Block block = event.getClickedBlock();
			

			Config config = new Config();
			
			if (config.canUse(player))
			{

				if (player.getItemInHand().getType() != Material.AIR)
				{

					if (matchDisassembler(block) != null)
					{

						Disassembler disassembler = matchDisassembler(block);
						
						if (!breaking.containsKey(player.getName()))
						{
						
							scheduleDelayAssemble(player, disassembler);
							breaking.put(player.getName(), 1);
						
						}
						else
						{
							
							breaking.put(player.getName(), breaking.get(player.getName()) + 1);
							
						}
						
					}
					
				}

			}
			
		}
		
	}
	
	public Disassembler matchDisassembler(Block block)
	{
		
		for (Disassembler disassembler : ItemScrapper.disassemblers)
		{

			if (disassembler.matches(block))
			{

				return disassembler;
				
			}
			
		}
		
		return null;
		
	}
	
	public void scheduleDelayAssemble(final Player player, final Disassembler disassembler)
	{
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Plugin.getPlugin(), new Runnable()
		{
			
			@Override
			public void run()
			{
				
				if (!(breaking.get(player.getName()) > 1))
				{
					System.out.println("done");
					disassembler.disassembleItem(player);
					
				}
				
				breaking.remove(player.getName());
				
			}
			
		}, 5L);
		
	}
	
}
