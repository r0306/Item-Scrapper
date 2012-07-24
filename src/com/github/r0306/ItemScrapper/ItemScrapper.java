package com.github.r0306.ItemScrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemScrapper extends JavaPlugin
{

	public static ArrayList<Disassembler> disassemblers = new ArrayList<Disassembler>();
	
	public void onEnable()
	{
		
		loadConfig();
		
		new Plugin(this);
		
		try 
		{
			
			createDataFile();
		
		} catch (IOException e) {

			System.out.println("[Item Scrapper] Error creating scrapper data file.");
		
		}
		
		try
		{
		
			disassemblers = new Disassembler().getDisassemblers();
		
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		
		} catch (IOException e) {

			e.printStackTrace();
		
		}
		System.out.println(disassemblers.size());
		getServer().getPluginManager().registerEvents(new Listeners(), this);
		
		System.out.println("Item Scrapper version + [" + getDescription().getVersion() + "] loaded.");
		
	}
	
	public void onDisable()
	{
			
		disassemblers = null;
		
		System.out.println("Item Scrapper version + [" + getDescription().getVersion() + "] unloaded.");
		
	}
	
	public void loadConfig()
	{

		FileConfiguration cfg = this.getConfig();
		FileConfigurationOptions cfgOptions = cfg.options();
		cfg.addDefault("Scrapper-Block", "Gold_Block");
		cfgOptions.copyDefaults(true);
		cfgOptions.copyHeader(true);
		saveConfig();
		
	}
	
	public void createDataFile() throws IOException
	{
				
		File file = new File(getDataFolder() + "\\ScrapperBlocks.bin");
		
		if (!file.isFile())
		{
			
			file.createNewFile();
			
		}
			
	}
	
}
