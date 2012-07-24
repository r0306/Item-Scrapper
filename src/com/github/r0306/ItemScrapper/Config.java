package com.github.r0306.ItemScrapper;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Config extends ItemScrapper
{
	
	private Material type = retrieveConfigMaterial();
	
	public Material getBlockType()
	{
		
		return type;
		
	}
	
	public boolean matches(Block block)
	{

		return block.getType() == type;
		
	}
	
	public boolean canCreate(Player player)
	{
		
		return player.hasPermission("scrap.create");
		
	}
	
	public boolean canUse(Player player)
	{
		
		return player.hasPermission("scrap.use");
		
	}
	
    private Material retrieveConfigMaterial()
    {

    	return (Material.matchMaterial(Plugin.getPlugin().getConfig().getString("Scrapper-Block")) != null ? Material.matchMaterial(Plugin.getPlugin().getConfig().getString("Scrapper-Block")) : Material.GOLD_BLOCK);

    }
	
}
