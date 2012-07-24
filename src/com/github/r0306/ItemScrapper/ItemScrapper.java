package com.github.r0306.ItemScrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
		
			disassemblers = getDisassemblers();
					
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		
		} catch (IOException e) {

			e.printStackTrace();
		
		}

		getServer().getPluginManager().registerEvents(new Listeners(), this);
		
		System.out.println("Item Scrapper version [" + getDescription().getVersion() + "] loaded.");
		
	}
	
	public void onDisable()
	{
			
		try
		{
		
			saveDisassemblers();
		
		} catch (IOException e) {

			e.printStackTrace();

		}
		
		disassemblers = null;
		
		System.out.println("Item Scrapper version [" + getDescription().getVersion() + "] unloaded.");
		
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
	
	@SuppressWarnings("unchecked")
	public ArrayList<Disassembler> getDisassemblers() throws IOException, ClassNotFoundException
	{
				
		ArrayList<Disassembler> tempList = new ArrayList<Disassembler>();
		
		FileInputStream f_in = new FileInputStream(Plugin.getPlugin().getDataFolder() + "\\ScrapperBlocks.bin");
		
		if (f_in.available() != 0)
		{

			ObjectInputStream obj_in = new ObjectInputStream(f_in);
			
			tempList = (ArrayList<Disassembler>) obj_in.readObject();

			obj_in.close();
		
		}
		
		return tempList;
				
	}
	
	public void saveDisassemblers() throws IOException
	{
		
		FileOutputStream f_out = new FileOutputStream(Plugin.getPlugin().getDataFolder() + "\\ScrapperBlocks.bin");
		
		ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
		
		obj_out.writeObject(disassemblers);
		
		obj_out.close();
		
	}
	
}
