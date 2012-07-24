package com.github.r0306.ItemScrapper;

public class Plugin
{

	private static ItemScrapper plugin;
	
	public Plugin(ItemScrapper plugin)
	{
		
		Plugin.plugin = plugin;
		
	}
	
	public static ItemScrapper getPlugin()
	{
		
		return plugin;
		
	}
	
}
