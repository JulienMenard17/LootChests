package ca.sharkmenard.lootchests;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;



public class LootChestsManager {
	private static ArrayList<LootChest> lootsChests;
	private static boolean isLoaded = false;
  
	public static void loadLootChests() {
		isLoaded = true;
		lootsChests = new ArrayList<>();
		FileConfiguration chestsConfig = Main.getInstance().getChestConfig();
		for (int i = 1; i > 0; i++) {
      
			if (!chestsConfig.contains("Chests." + String.valueOf(i))) {
				break;
			}
      
			BlockFace blockFace = BlockFace.NORTH;
			String blockFaceStr = chestsConfig.getString("Chests." + String.valueOf(i) + ".BlockFace");
			if (blockFaceStr.equalsIgnoreCase("North")) {
				blockFace = BlockFace.NORTH;
			} else if (blockFaceStr.equalsIgnoreCase("South")) {
				blockFace = BlockFace.SOUTH;
			} else if (blockFaceStr.equalsIgnoreCase("West")) {
				blockFace = BlockFace.WEST;
			} else if (blockFaceStr.equalsIgnoreCase("East")) {
				blockFace = BlockFace.EAST;
			} 
      
			lootsChests.add(new LootChest(new Location(Bukkit.getWorld(chestsConfig.getString("Chests." + String.valueOf(i) + ".World")),
					chestsConfig.getDouble("Chests." + String.valueOf(i) + ".X"), 
					chestsConfig.getDouble("Chests." + String.valueOf(i) + ".Y"), 
					chestsConfig.getDouble("Chests." + String.valueOf(i) + ".Z")), blockFace));
		} 
	}

  
	public static void saveLootChests() {
		if (!isLoaded) {
			return;
		}
		FileConfiguration chestsConfig = Main.getInstance().getChestConfig();
		chestsConfig.set("Chests", "");
		Main.getInstance().saveChestConfig();
		for (int i = 0; i < lootsChests.size(); i++) {
			String orientStr;
			chestsConfig.set("Chests." + (i + 1) + ".X", Integer.valueOf(lootsChests.get(i).getLocation().getBlockX()));
			chestsConfig.set("Chests." + (i + 1) + ".Y", Integer.valueOf(lootsChests.get(i).getLocation().getBlockY()));
			chestsConfig.set("Chests." + (i + 1) + ".Z", Integer.valueOf(lootsChests.get(i).getLocation().getBlockZ()));
			chestsConfig.set("Chests." + (i + 1) + ".World", lootsChests.get(i).getLocation().getWorld().getName());
      
			switch (lootsChests.get(i).getOrientation()) {
				case NORTH:
					orientStr = "NORTH";
					break;
				case SOUTH:
					orientStr = "SOUTH";
					break;
				case EAST:
					orientStr = "EAST";
					break;
				case WEST:
					orientStr = "WEST";
					break;
				default:
					orientStr = "NORTH";
					break;
			} 
			chestsConfig.set("Chests." + (i + 1) + ".BlockFace", orientStr);
		} 
		Main.getInstance().saveChestConfig();
		lootsChests.clear();
		isLoaded = false;
	}
  
	public static void addLootChest(Location loc, BlockFace blockFace, Player p) {
		lootsChests.add(new LootChest(loc, blockFace));
		p.sendMessage("§aVous venez de mettre en place un Coffre à Loot");
	}
  
	public static void delLootChest(Location loc) {
		for (int i = 0; i < lootsChests.size(); i++) {
      
			if (lootsChests.get(i).getLocation().equals(loc)) {
				LootChest chest = lootsChests.get(i);
				chest.getLocation().getBlock().setType(Material.AIR);
				lootsChests.remove(i);
				chest = null;
			} 
		} 
	}




  
	public static ArrayList<LootChest> getLootsChests() {
		return lootsChests;
	}

  
	public static int getPlayerGearTier(Player p) {
		int id = 0;
		int counter = 0;
		int j = 3;
		ItemStack[] normal = FragmentArmors.normal();
		ItemStack[] chanceuse = FragmentArmors.chanceuse();
		ItemStack[] epic = FragmentArmors.epic();
		ItemStack[] legendaire = FragmentArmors.legendaire();
		ItemStack[] arkthesium = FragmentArmors.arkthesium();
		ItemStack[] armor = p.getInventory().getArmorContents();
		for (int i = 0; i < armor.length; i++) {
      
			if ((id == 0 || id == 1) && compareItems(armor[j], normal[i])) {
				id = 1;
				counter++;
			} 
			if ((id == 0 || id == 2) && compareItems(armor[j], chanceuse[i])) {
				id = 2;
				counter++;
			} 
			if ((id == 0 || id == 3) && compareItems(armor[j], epic[i])) {
				id = 3;
				counter++;
			} 
			if ((id == 0 || id == 4) && compareItems(armor[j], legendaire[i])) {
				id = 4;
				counter++;
			} 
			if ((id == 0 || id == 5) && compareItems(armor[j], arkthesium[i])) {
				id = 5;
				counter++;
			} 
      
			j--;
		} 
		
		if (counter != 4) {
			return 0;
		}
		return id;
	}




  
	public static boolean compareItems(ItemStack item1, ItemStack item2) {
		if (item1 == null || item2 == null) {
			return false;
		}
    
		if (item1.getType() != item2.getType()) {
			return false;
		}
    
		if (item1.hasItemMeta() != item2.hasItemMeta()) {
			return false;
		}

    
		boolean hasItemMeta = item1.hasItemMeta();
    
		if (hasItemMeta && item1.getItemMeta().hasDisplayName() != item2.getItemMeta().hasDisplayName()) {
			return false;
		}
    
		boolean hasDisplayName = item1.getItemMeta().hasDisplayName();


    
		if (hasDisplayName && !item1.getItemMeta().getDisplayName().equalsIgnoreCase(item2.getItemMeta().getDisplayName())) {
			return false;
		}

		return true;
	}
}
