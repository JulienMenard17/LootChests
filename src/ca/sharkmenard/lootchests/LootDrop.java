package ca.sharkmenard.lootchests;

import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;

public class LootDrop {
	private ArrayList<String> commands;
	private ItemStack items;
	private boolean isItems = false;
	private boolean isFragment = false;
	private boolean isFixAmount = false;
  	private int minAmount;
  	private int maxAmount;
  	private int tierRequired = 0;
  	private String lootMessage = "";
  
  	public LootDrop(int tier, String message, ArrayList<String> cmd) {
  		this.commands = new ArrayList<String>(cmd);
  		this.tierRequired = tier;
  		this.lootMessage = message;
  	}
  
  	public LootDrop(int tier, boolean isFragment, int minAmount, int maxAmount, String message, ItemStack items) {
  		this.items = items;
  		this.isItems = true;
  		if (minAmount == maxAmount)
  			this.isFixAmount = true; 
  		this.isFragment = isFragment;
  		this.minAmount = minAmount;
  		this.maxAmount = maxAmount;
  		this.tierRequired = tier;
  		this.lootMessage = message;
  	}


  
  	public boolean isItems() {
	  return isItems;
  	}


  
  	public boolean isFragment() {
	  return isFragment; 
  	}

  	public int getTierRequired() {
		return tierRequired;
	}

  	
  	public boolean isFixAmount() {
		return isFixAmount;
	}
  
  	
  	public int getMinAmount() {
		return minAmount;
	}
  	
  	public int getMaxAmount() {
		return maxAmount;
	}
  
  	public String getLootMessage() {
		return lootMessage;
	}
  
  	public ArrayList<String> getLootCmds() {
		return commands;
	}

  	public ItemStack getLootItems() {
		return items;
	}
}