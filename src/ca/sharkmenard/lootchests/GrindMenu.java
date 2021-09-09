package ca.sharkmenard.lootchests;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class GrindMenu {
	
	public static Inventory getGrindMenu() {
		Inventory inv = Bukkit.createInventory(null, 36, "§6Broyeur d'Armures Spéciales");
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)8);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(ChatColor.BLACK + "");
		glass.setItemMeta(glassM);
		for (int i = 27; i < 36; i++) {
			inv.setItem(i, glass);
		}
		
		ItemStack confirm = new ItemStack(Material.STAINED_CLAY, 1, (short)5);
		ItemMeta confirmM = confirm.getItemMeta();
		confirmM.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Broyer les Items");
    
		confirm.setItemMeta(confirmM);
    
		inv.setItem(31, confirm);


    
		return inv;
	}
}
