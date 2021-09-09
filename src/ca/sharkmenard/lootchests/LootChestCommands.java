package ca.sharkmenard.lootchests;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class LootChestCommands implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean isPlayer = false;
		Player p = null;
		if (sender instanceof Player) {
			p = (Player)sender;
			isPlayer = true;
		} 
		if ((label.equalsIgnoreCase("lc") || label.equalsIgnoreCase("lootchests")) && isPlayer && !p.hasPermission("lc.admin") && !p.hasPermission("lc.*") && !p.isOp()) {
			sender.sendMessage("§cVous n'avez pas la permission d'effectuer cette commande !");
			return false;
		} 
		
		
		if (args.length == 0 || (args.length >= 1 && args[0].equalsIgnoreCase("help"))) {
			sender.sendMessage("§7LootChests Commands:");
			sender.sendMessage("§7 - /lc give");
			sender.sendMessage("§7 - /lc list");
      
			sender.sendMessage("§7 - /lc armor [TypeID]");
			sender.sendMessage("§7 - /grind");
		} 
		if (args.length == 1 && args[0].equalsIgnoreCase("give")) {
			if (!isPlayer) {
				sender.sendMessage("§cVous devez être un Joueur pour effectuer cette commande !");
				return false;
			} 
			giveLootChest(p);
		} 

    
		if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
			sender.sendMessage("§7Listes des Coffres Loot (Total: " + LootChestsManager.getLootsChests().size() + ")");
			for (int i = 0; i < LootChestsManager.getLootsChests().size(); i++) {
				Location chestLoc = LootChestsManager.getLootsChests().get(i).getLocation();
				sender.sendMessage("§7- Location: " + chestLoc.getBlockX() + ", " + chestLoc.getBlockY() + ", " + chestLoc.getBlockZ() + " | World: " + chestLoc.getWorld().getName());
			} 
		} 
		if (args.length >= 1 && (args[0].equalsIgnoreCase("armors") || args[0].equalsIgnoreCase("armor"))) {
			if (args.length == 1) {
				sender.sendMessage("§7Les Types d'armures:");
				sender.sendMessage("§7 - 1: 'Normal'");
				sender.sendMessage("§7 - 2: 'Chanceuse'");
				sender.sendMessage("§7 - 3: 'Epic'");
				sender.sendMessage("§7 - 4: 'Légendaire'");
				sender.sendMessage("§7 - 5: 'Arkthesium'");
				return true;
			}
			if (!isPlayer) {
				sender.sendMessage("§cVous devez être un Joueur pour effectuer cette commande !");
				return false;
			}
			
			int id = 0;
			
			try {
				id = Integer.parseInt(args[1]);
			} catch (Exception e) {
				p.sendMessage("§cL'id indiqué n'est pas un nombre valide");
				return false;
			}
      
			if (id < 1 || id > 5) {
				p.sendMessage("§cL'id indiqué n'est pas un nombre valide");
				return false;
			} 
      
			giveArmor(p, id);
			return true;
		} 

		if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			Main.getInstance().onReload();
		}

    
		return true;
	}
  
	public static void giveArmor(Player p, int armorId) {
		switch (armorId) {
		case 1:
			p.getInventory().addItem(FragmentArmors.normal());
        	p.sendMessage("§aVous venez de recevoir une Armure §9Normal");
        	return;
		case 2:
			p.getInventory().addItem(FragmentArmors.chanceuse());
			p.sendMessage("§aVous venez de recevoir une Armure §6Chanceuse");
			return;
		case 3:
			p.getInventory().addItem(FragmentArmors.epic());
        	p.sendMessage("§aVous venez de recevoir une Armure §5Epic");
        	return;
		case 4:
			p.getInventory().addItem(FragmentArmors.legendaire());
			p.sendMessage("§aVous venez de recevoir une Armure §eLégendaire");
			return;
		case 5:
			p.getInventory().addItem(FragmentArmors.arkthesium());
			p.sendMessage("§aVous venez de recevoir une Armure d'§cArkthesium");
			return;
		} 
	}

  
	public static void giveLootChest(Player p) {
		ItemStack chest = new ItemStack(Material.CHEST);
		ItemMeta chestM = chest.getItemMeta();
		chestM.addEnchant(Enchantment.DURABILITY, 1, true);
		chestM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§aPlacez ce coffre à un endroit pour");
		lore.add("§amettre en place un Coffre à Loot");
		chestM.setLore(lore);
		chestM.setDisplayName("§3Coffre à Loot");
		chest.setItemMeta(chestM);
    
		p.getInventory().addItem(new ItemStack[] { chest });
    
		p.sendMessage("§aUn Coffre à Loot a été ajouté à votre inventaire.");
	}
}
