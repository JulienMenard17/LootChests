package ca.sharkmenard.lootchests;

import java.util.ArrayList;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class FragmentArmors {
	
	private static ItemStack buildArmorPiece(Material piece, String itemName, String fragmentGive, int doubleDropChance, ArrayList<Pair<Enchantment, Integer>> enchants, String enchantStr) {
		ItemStack item = new ItemStack(piece);
		ItemMeta itemM = item.getItemMeta();
    
		itemM.setDisplayName(itemName);
    
		for (int i = 0; i < enchants.size(); i++) {
			itemM.addEnchant(enchants.get(i).getLeft(), enchants.get(i).getRight().intValue(), true);
		}
    
		ArrayList<String> lore = new ArrayList<String>();
    
		lore.add("§7§l>> §7Description de l'item");
		lore.add("§2§l+ §7Pourcentage de Double Loot augmenté de §8(§a+"+ doubleDropChance + "%§8)");
		lore.add("§2§l+ §7Permet d'obtenir des fragments de type §8("+ fragmentGive + "§8)");
    	lore.add("§2§l+ §7Permet d'avoir une meilleure protection d'armure §8("+ enchantStr + "§8)");
    	
    	itemM.setLore(lore);
    
    	itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    
    	item.setItemMeta(itemM);
    	return item;
	}

  
	public static ItemStack[] normal() {
		ArrayList<Pair<Enchantment, Integer>> enchants = new ArrayList<Pair<Enchantment,Integer>>();
		enchants.add(Pair.of(Enchantment.PROTECTION_ENVIRONMENTAL, Integer.valueOf(4)));
		ItemStack helmet = buildArmorPiece(Material.IRON_HELMET,"§7Casque §9Normal", "§6Fragment Chanceux", 5, enchants, "P4");
		ItemStack body = buildArmorPiece(Material.IRON_CHESTPLATE,"§7Plastron §9Normal", "§6Fragment Chanceux", 5, enchants, "P4");
		ItemStack legs = buildArmorPiece(Material.IRON_LEGGINGS,"§7Jambières §9Normal", "§6Fragment Chanceux", 5, enchants, "P4");
		ItemStack boots = buildArmorPiece(Material.IRON_BOOTS,"§7Bottes §9Normal", "§6Fragment Chanceux", 5, enchants, "P4");
    
		return new ItemStack[] { helmet, body, legs, boots };
	}
  
	public static ItemStack[] chanceuse() {
		ArrayList<Pair<Enchantment, Integer>> enchants = new ArrayList<Pair<Enchantment,Integer>>();
		enchants.add(Pair.of(Enchantment.PROTECTION_ENVIRONMENTAL, Integer.valueOf(3)));
		ItemStack helmet = buildArmorPiece(Material.DIAMOND_HELMET,"§7Casque §6Chanceux", "§5Fragment Epic", 15, enchants, "P3");
		ItemStack body = buildArmorPiece(Material.DIAMOND_CHESTPLATE,"§7Plastron §6Chanceux", "§5Fragment Epic", 15, enchants, "P3");
		ItemStack legs = buildArmorPiece(Material.DIAMOND_LEGGINGS,"§7Jambières §6Chanceux", "§5Fragment Epic", 15, enchants, "P3");
		ItemStack boots = buildArmorPiece(Material.DIAMOND_BOOTS,"§7Bottes §6Chanceux", "§5Fragment Epic", 15, enchants, "P3");
    
		return new ItemStack[] { helmet, body, legs, boots };
	}
  
	public static ItemStack[] epic() {
		ArrayList<Pair<Enchantment, Integer>> enchants = new ArrayList<Pair<Enchantment,Integer>>();
		enchants.add(Pair.of(Enchantment.PROTECTION_ENVIRONMENTAL, Integer.valueOf(3)));
	    enchants.add(Pair.of(Enchantment.DURABILITY, Integer.valueOf(3)));
		ItemStack helmet = buildArmorPiece(Material.DIAMOND_HELMET,"§7Casque §5Epic", "§eFragment Légendaire", 25, enchants, "P3U3");
		ItemStack body = buildArmorPiece(Material.DIAMOND_CHESTPLATE,"§7Plastron §5Epic", "§eFragment Légendaire", 25, enchants, "P3U3");
		ItemStack legs = buildArmorPiece(Material.DIAMOND_LEGGINGS,"§7Jambières §5Epic", "§eFragment Légendaire", 25, enchants, "P3U3");
		ItemStack boots = buildArmorPiece(Material.DIAMOND_BOOTS,"§7Bottes §5Epic", "§eFragment Légendaire", 25, enchants, "P3U3");
    
		return new ItemStack[] { helmet, body, legs, boots };
	}
  
	public static ItemStack[] legendaire() {
	    ArrayList<Pair<Enchantment, Integer>> enchants = new ArrayList<Pair<Enchantment,Integer>>();
	    enchants.add(Pair.of(Enchantment.PROTECTION_ENVIRONMENTAL, Integer.valueOf(4)));
	    ItemStack helmet = buildArmorPiece(Material.DIAMOND_HELMET,"§7Casque §eLégendaire","§aTOUS", 50, enchants, "P4");
	    ItemStack body = buildArmorPiece(Material.DIAMOND_CHESTPLATE,"§7Plastron §eLégendaire","§aTOUS", 50, enchants, "P4");
    	ItemStack legs = buildArmorPiece(Material.DIAMOND_LEGGINGS,"§7Jambières §eLégendaire","§aTOUS", 50, enchants, "P4");
    	ItemStack boots = buildArmorPiece(Material.DIAMOND_BOOTS,"§7Bottes §eLégendaire","§aTOUS", 50, enchants, "P4");
    
    	return new ItemStack[] { helmet, body, legs, boots };
	}
  
	public static ItemStack[] arkthesium() {
	    ArrayList<Pair<Enchantment, Integer>> enchants = new ArrayList<Pair<Enchantment,Integer>>();
	    enchants.add(Pair.of(Enchantment.PROTECTION_ENVIRONMENTAL, Integer.valueOf(5)));
	    enchants.add(Pair.of(Enchantment.DURABILITY, Integer.valueOf(3)));
	    ItemStack helmet = buildArmorPiece(Material.DIAMOND_HELMET,"§7Casque §cArkthesium","§aTOUS", 100, enchants, "P5U3");
	    ItemStack body = buildArmorPiece(Material.DIAMOND_CHESTPLATE,"§7Plastron §cArkthesium","§aTOUS", 100, enchants, "P5U3");
	    ItemStack legs = buildArmorPiece(Material.DIAMOND_LEGGINGS,"§7Jambières §cArkthesium","§aTOUS", 100, enchants, "P5U3");
	    ItemStack boots = buildArmorPiece(Material.DIAMOND_BOOTS,"§7Bottes §cArkthesium","§aTOUS", 100, enchants, "P5U3");
	    
	    return new ItemStack[] { helmet, body, legs, boots };
	}


  
	public static int fragmentAmount(int armorId, int armorPieceId) {
		switch (armorId) {
		case 1:
			switch (armorPieceId) {
			case 1:
				return 5;
			case 2:
				return 8;
			case 3:
				return 7;
			case 4:
				return 4;
			} 
			return 0;
      
		case 2:
			switch (armorPieceId) {
			case 1:
				return 10;
			case 2:
				return 16;
			case 3:
				return 14;
			case 4:
				return 8;
			} 
			return 0;
      
		case 3:
			switch (armorPieceId) {
			case 1:
        	  return 20;
			case 2:
				return 32;
			case 3:
				return 28;
			case 4:
				return 16;
			} 
			return 0;
      
		case 4:
			switch (armorPieceId) {
			case 1:
				return 40;
			case 2:
				return 64;
			case 3:
				return 56;
			case 4:
				return 32;
			} 
			return 0;
      
		case 5:
			switch (armorPieceId) {
			case 1:
				return 500;
			case 2:
				return 800;
			case 3:
				return 700;
			case 4:
				return 400;
			} 
			return 0;
		} 
		
		return 0;
	}
}
