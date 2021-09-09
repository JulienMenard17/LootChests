package ca.sharkmenard.lootchests;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;



public class GrindListener implements Listener {
	@EventHandler
 	public void onGrindClickInv(InventoryClickEvent e) {
		if (e.getClickedInventory() != null && e.getClickedInventory().getName().equals("§6Broyeur d'Armures Spéciales")) {
			
			if (e.getAction() == InventoryAction.PICKUP_ALL && e.getRawSlot() == 31) {
				e.setCancelled(true);
				
				ArrayList<ItemStack> noneItems = new ArrayList<ItemStack>();
				ArrayList<ItemStack> fragmentItems = new ArrayList<ItemStack>();
        
				int fragments = 0;
				for (int i = 0; i < e.getInventory().getSize() - 9; i++) {
					if (e.getInventory().getItem(i) != null && !e.getInventory().getItem(i).getType().equals(Material.AIR)) {
						if (checkItem(e.getInventory().getItem(i))) {
							int fragAmount = fragmentCalculator(e.getInventory().getItem(i));
							fragments += fragAmount;
							if (fragAmount == 0) {
								noneItems.add(e.getInventory().getItem(i));
							}
						} else {
							noneItems.add(e.getInventory().getItem(i));
						}  	
					} 
				} 
				e.getInventory().clear();
				e.getWhoClicked().closeInventory();
				for (int i = 0; i <= fragments / 64; i++) {
					int amount = 0;
					if (fragments - 64 * (i + 1) >= 64) {
						amount = 64;
					} else {
						amount = fragments - 64 * i;
						if (amount == 0) {
							break;
						}
					} 
          
					ItemStack fragment = new ItemStack(Material.EMERALD, amount);
					ItemMeta fragM = fragment.getItemMeta();
					fragM.setDisplayName("§cFragment D'Arkthesium");
					fragM.addEnchant(Enchantment.DURABILITY, 1, true);
					fragM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
					fragment.setItemMeta(fragM);
          
					fragmentItems.add(fragment);
				} 
        
				for (ItemStack item : fragmentItems) {
					HashMap<Integer, ItemStack> invFull = e.getWhoClicked().getInventory().addItem(new ItemStack[] { item });
					LootChestsListener.invFullToDrop(invFull, (Player)e.getWhoClicked());
				} 

        
				for (ItemStack item : noneItems) {
					HashMap<Integer, ItemStack> invFull = e.getWhoClicked().getInventory().addItem(new ItemStack[] { item });
					LootChestsListener.invFullToDrop(invFull, (Player)e.getWhoClicked());
				} 
			} 
      
			if (e.getRawSlot() >= 27) {
				e.setCancelled(true);
			}
		} 
	}


  
	@EventHandler
	public void onGrindCloseInv(InventoryCloseEvent e) {
		if (e.getInventory() != null && e.getInventory().getName().equals("§6Broyeur d'Armures Spéciales")) {
				ArrayList<ItemStack> noneItems = new ArrayList<ItemStack>();
				for (int i = 0; i < e.getInventory().getSize() - 9; i++) {
					if (e.getInventory().getItem(i) != null && !e.getInventory().getItem(i).getType().equals(Material.AIR)) {
						noneItems.add(e.getInventory().getItem(i));
					}
				} 
				if (e.getPlayer().isDead()) {
					HashMap<Integer, ItemStack> drops = new HashMap<Integer, ItemStack>();
					for (int i = 0; i < noneItems.size(); i++) {
						drops.put(Integer.valueOf(i), noneItems.get(i));
					}
					LootChestsListener.invFullToDrop(drops, (Player)e.getPlayer());
				} else {
        
					for (ItemStack item : noneItems) {
						HashMap<Integer, ItemStack> invFull = e.getPlayer().getInventory().addItem(new ItemStack[] { item });
						LootChestsListener.invFullToDrop(invFull, (Player)e.getPlayer());
					} 
				} 
		} 
	}


  
	public boolean checkItem(ItemStack item) {
		if (item == null || item.getType().equals(Material.AIR)) {
			return false;
		}
		ArrayList<ItemStack[]> armors = new ArrayList<ItemStack[]>();
		armors.add(FragmentArmors.normal());
		armors.add(FragmentArmors.chanceuse());
		armors.add(FragmentArmors.epic());
		armors.add(FragmentArmors.legendaire());
		armors.add(FragmentArmors.arkthesium());
    
		for (int i = 0; i < armors.size(); i++) {
			for (int j = 0; j < armors.get(i).length; j++) {
				if (LootChestsManager.compareItems(item, armors.get(i)[j])) {
					return true;
				}
			} 
		} 
    
		return false;
	}
  
	public int fragmentCalculator(ItemStack item) {
		if (item == null || item.getType().equals(Material.AIR)) {
			return 0;
		}
		int fragmentAmount = 0;
		ArrayList<ItemStack[]> armors = new ArrayList<ItemStack[]>();
		armors.add(FragmentArmors.normal());
		armors.add(FragmentArmors.chanceuse());
		armors.add(FragmentArmors.epic());
		armors.add(FragmentArmors.legendaire());
		armors.add(FragmentArmors.arkthesium());
		
		for (int i = 0; i < armors.size(); i++) {
			for (int j = 0; j < armors.get(i).length; j++) {
				if (LootChestsManager.compareItems(item, armors.get(i)[j])) {
					fragmentAmount = FragmentArmors.fragmentAmount(i + 1, j + 1);
					break;
				} 
			} 
		} 
		double dura = (item.getType().getMaxDurability() - item.getDurability());
		dura /= item.getType().getMaxDurability();
		fragmentAmount = (int)(fragmentAmount * dura);
    
		return fragmentAmount;
	}
}
