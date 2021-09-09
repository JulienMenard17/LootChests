package ca.sharkmenard.lootchests;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LootChestsDrops {
	private static HashMap<LootDrop, Double> dropTable;

	@SuppressWarnings("deprecation")
	public static void loadDropTable() {
		dropTable = new HashMap<LootDrop, Double>();
		FileConfiguration lootsConfig = Main.getInstance().getLootConfig();

		ArrayList<String> command = new ArrayList<String>();



		for (int i = 1; i > 0 && lootsConfig.contains("Loots." + i); i++) {
			String type = "";
			int minAmount = 1;
			int maxAmount = 1;
			boolean enchanted = false;
			boolean hideEnchants = false;
			double chance = 1.0D;
			int itemID = 0;
			int itemDamage = 0;
			String itemName = "";
			String message = "";
			int tier = 0;
			command.clear();
			
			if (!lootsConfig.contains("Loots." + i + ".Type")) {
				Bukkit.getServer().getConsoleSender().sendMessage(
						ChatColor.RED + "[LootChests] Erreur lors du chargement du Loot #" + i + ": Type Non Indiquer");
			} else {

				type = lootsConfig.getString("Loots." + i + ".Type");

				if (lootsConfig.contains("Loots." + i + ".Tier")) {
					tier = lootsConfig.getInt("Loots." + i + ".Tier");
				}

				if (lootsConfig.contains("Loots." + i + ".Name")) {
					itemName = lootsConfig.getString("Loots." + i + ".Name").replaceAll("&", "§");
				}

				if (lootsConfig.contains("Loots." + i + ".Message")) {
					message = lootsConfig.getString("Loots." + i + ".Message").replaceAll("&", "§");
				}

				if (lootsConfig.contains("Loots." + i + ".MaterialID")) {
					itemID = lootsConfig.getInt("Loots." + i + ".MaterialID");
				}

				if (lootsConfig.contains("Loots." + i + ".Damage")) {
					itemDamage = lootsConfig.getInt("Loots." + i + ".Damage");
				}

				if (lootsConfig.contains("Loots." + i + ".MinAmount")) {
					minAmount = lootsConfig.getInt("Loots." + i + ".MinAmount");
				}

				if (lootsConfig.contains("Loots." + i + ".MaxAmount")) {
					maxAmount = lootsConfig.getInt("Loots." + i + ".MaxAmount");
				}

				if (lootsConfig.contains("Loots." + i + ".Amount")) {
					minAmount = lootsConfig.getInt("Loots." + i + ".Amount");
					maxAmount = minAmount;
				}

				if (lootsConfig.contains("Loots." + i + ".Chance")) {
					chance = lootsConfig.getDouble("Loots." + i + ".Chance");
				}

				if (lootsConfig.contains("Loots." + i + ".Enchanted")) {
					enchanted = lootsConfig.getBoolean("Loots." + i + ".Enchanted");
				}

				if (lootsConfig.contains("Loots." + i + ".HideEnchants")) {
					hideEnchants = lootsConfig.getBoolean("Loots." + i + ".HideEnchants");
				}

				if (lootsConfig.contains("Loots." + i + ".Commands")) {
					for (String cmd : lootsConfig.getStringList("Loots." + i + ".Commands")) {
						command.add(cmd);
					}
				}

				if (type.equalsIgnoreCase("fragment")) {
					if (itemID == 0) {
						Bukkit.getServer().getConsoleSender()
								.sendMessage(ChatColor.RED + "[LootChests] Erreur lors du chargement du Loot #" + i
										+ ": MaterialID Manquant/Invalide");
					} else {

						ItemStack fragment = new ItemStack(itemID, minAmount, (byte) itemDamage);
						ItemMeta fragM = fragment.getItemMeta();
						if (enchanted) {
							fragM.addEnchant(Enchantment.DURABILITY, 1, true);
						}

						if (hideEnchants) {
							fragM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						}

						if (!itemName.isEmpty()) {
							fragM.setDisplayName(itemName);
						}
						fragment.setItemMeta(fragM);

						dropTable.put(new LootDrop(tier, true, minAmount, maxAmount, message, fragment), chance);
					}

				} else if (type.equalsIgnoreCase("item") || type.equalsIgnoreCase("items")) {
					if (itemID == 0) {
						Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[LootChests] Erreur lors du chargement du Loot #" + i + ": MaterialID Manquant/Invalide");
					} else {

						ItemStack item = new ItemStack(itemID, minAmount, (byte) itemDamage);
						ItemMeta itemM = item.getItemMeta();
						if (enchanted) {
							itemM.addEnchant(Enchantment.DURABILITY, 1, true);
						}

						if (hideEnchants) {
							itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						}

						if (!itemName.isEmpty()) {
							itemM.setDisplayName(itemName);
						}

						item.setItemMeta(itemM);

						dropTable.put(new LootDrop(tier, false, minAmount, maxAmount, message, item), chance);
					}

				} else if (type.equalsIgnoreCase("command") || type.equalsIgnoreCase("cmd")
						|| type.equalsIgnoreCase("commands")) {

					if (command.isEmpty()) {
						Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED
								+ "[LootChests] Erreur lors du chargement du Loot #" + i + ": Aucune Commande");

					} else {
						dropTable.put(new LootDrop(tier, message, command), chance);
					}
				} else {
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED
							+ "[LootChests] Erreur lors du chargement du Loot #" + i + ": Type Non Valide");
				}
			}
		}
	}

	public static void clearDropTable() {
		dropTable.clear();
	}

	public static int tierToDoubleLootRate(int tier) {
		if (tier == 0)
			return 0;
		if (tier == 1)
			return 5;
		if (tier == 2)
			return 15;
		if (tier == 3)
			return 25;
		if (tier == 4)
			return 50;
		if (tier == 5) {
			return 100;
		}
		return 0;
	}

	public static HashMap<LootDrop, Double> getDropTable() {
		return dropTable;
	}

	public static ArrayList<LootDrop> getDrops(int tier) {
		double fragmentsChance = 0.0D;
		double othersChance = 0.0D;
		double multiplier = 0.0D;
		int dropAmount = 1;
		double calculator = 0.0D;
		double random = 0.0D;
		ArrayList<LootDrop> dropsList = new ArrayList<>();

		for (LootDrop loot : dropTable.keySet()) {
			if (loot.isFragment()) {
				if (loot.getTierRequired() == tier || tier >= 4)
					fragmentsChance += dropTable.get(loot);
				continue;
			}
			if (loot.getTierRequired() == tier || loot.getTierRequired() == 0) {
				othersChance += dropTable.get(loot);
			}
		}

		multiplier = (100.0D - fragmentsChance) / othersChance;

		random = Math.random() * 100.0D;
		if (tier == 1) {
			if (random < 5.0D)
				dropAmount = 2;
		} else if (tier == 2) {
			if (random < 15.0D)
				dropAmount = 2;
		} else if (tier == 3) {
			if (random < 25.0D)
				dropAmount = 2;
		} else if (tier == 4) {
			if (random < 50.0D)
				dropAmount = 2;
		} else if (tier == 5 && random < 100.0D) {
			dropAmount = 2;
		}
		ArrayList<LootDrop> drops = new ArrayList<LootDrop>(dropTable.keySet());
		ArrayList<Double> dropsChance = new ArrayList<Double>(dropTable.values());

		for (int j = 0; j < dropAmount; j++) {
			random = Math.random() * 100.0D;
			calculator = 0.0D;
			for (int i = 0; i < dropTable.size(); i++) {
				if (!drops.get(i).isFragment() || drops.get(i).getTierRequired() == tier || tier >= 4) {

					if (drops.get(i).isFragment()) {
						calculator += dropsChance.get(i);
					} else {
						calculator += dropsChance.get(i) * multiplier;
					}
					if (calculator > random) {
						dropsList.add(drops.get(i));
						break;
					}
				}
			}
		}
		return dropsList;
	}
}
