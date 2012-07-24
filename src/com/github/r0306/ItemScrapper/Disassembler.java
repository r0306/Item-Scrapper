package com.github.r0306.ItemScrapper;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class Disassembler implements Serializable
{

	private static final long serialVersionUID = -4274483441994919560L;
	
	protected int x;
	protected int y;
	protected int z;
	protected String world;
	
	transient Block block;
	transient Location location;
	
	public Disassembler()
	{
		
		
		
	}
	
	public Disassembler(Location loc) throws IOException
	{
		
		x = loc.getBlockX();
		y = loc.getBlockY();
		z = loc.getBlockZ();
		world = loc.getWorld().getName();
		location = loc;
		block = loc.getBlock();
		createDisassembler();
		ItemScrapper.disassemblers.add(this);
		
	}
		
	public Location getLocation()
	{
		
		Location location = new Location(Bukkit.getWorld(world), x, y, z);
		
		return location;
		
	}
	
	public Block getBlock()
	{
		
		return block;
		
	}
	
	public void createDisassembler() throws IOException
	{
		
		FileOutputStream f_out = new FileOutputStream(Plugin.getPlugin().getDataFolder() + "\\ScrapperBlocks.bin");
		
		ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
		
		obj_out.writeObject(this);
		
		ItemScrapper.disassemblers.add(this);
		
	}
	
	public void deleteDisassembler(Disassembler disassembler) throws IOException, ClassNotFoundException
	{
		
		ArrayList<Disassembler> disassemblers = getDisassemblers();
		
		if (disassemblers.contains(disassembler))
		{
		
			disassemblers.remove(disassembler);
			emptyFile();
			writeListToFile(disassemblers);
			
			if (ItemScrapper.disassemblers.contains(this))
			{
				
				ItemScrapper.disassemblers.remove(this);
		
			}
				
		}
				
	}
		
	public void emptyFile() throws IOException
	{
		
	    OutputStream os = null;
	    os = new FileOutputStream(Plugin.getPlugin().getDataFolder() + "\\ScrapperBlocks.bin");
	    
	    os.close();
		
	}
	
	public void writeListToFile(ArrayList<Disassembler> disassemblers) throws IOException
	{
		
		FileOutputStream f_out = new FileOutputStream(Plugin.getPlugin().getDataFolder() + "\\ScrapperBlocks.bin");
		
		ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
		
		for (Disassembler disassembler : disassemblers)
		{
			
			obj_out.writeObject(disassembler);
			
		}
		
	}
	
	public ArrayList<Disassembler> getDisassemblers() throws IOException, ClassNotFoundException
	{
		
		ArrayList<Disassembler> disassemblers = new ArrayList<Disassembler>();
		
		FileInputStream f_in = new FileInputStream(Plugin.getPlugin().getDataFolder() + "\\ScrapperBlocks.bin");
				
		ObjectInputStream obj_in = new ObjectInputStream(f_in);
			
		if (obj_in.readObject() != null)
		{
			
				while(true)
				{
					
					 try
					 {
						
						 Disassembler disassembler = (Disassembler) obj_in.readObject();
					     disassemblers.add(disassembler);
					  
					 } catch (EOFException ex) {
					           
						 break;
					         
					 }
			
				}
				
				f_in.close();
			
		}
		
		return disassemblers;
		
	}
	
	public void disassembleItem(Player player)
	{
	
		ItemStack item = player.getItemInHand();
		
		if (item.getType() != Material.AIR)
		{
			
			List<Recipe> recipes = Bukkit.getRecipesFor(item);
			
			for (Recipe recipe : recipes)
			{
				
				if (recipe instanceof ShapedRecipe)
				{

					ShapedRecipe srecipe = (ShapedRecipe) recipe;
					
					player.getInventory().remove(item);
					
					dropItems(srecipe.getIngredientMap().values());
					
				}
				else if (recipe instanceof ShapelessRecipe)
				{
					
					ShapelessRecipe srecipe = (ShapelessRecipe) recipe;
					
					player.getInventory().remove(item);
					
					dropItems(srecipe.getIngredientList());
					
				}
				
			}
		
		}
		
	}
	
	public void dropItems(Collection<ItemStack> items)
	{
		
		for(ItemStack item : items)
		{
			
			this.getLocation().getWorld().dropItem(this.getLocation().add(0, 1, 0), item);
			
		}
		
	}
	
}
