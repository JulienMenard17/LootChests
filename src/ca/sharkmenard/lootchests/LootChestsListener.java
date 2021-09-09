package ca.sharkmenard.lootchests;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class LootChestsListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.getBlockPlaced() != null && e.getBlockPlaced().getType().equals(Material.CHEST) && e.getItemInHand().hasItemMeta() && 
			e.getItemInHand().getItemMeta().hasDisplayName() && e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§3Coffre à Loot")) {
			BlockFace chestFace;
      
			switch (e.getBlockPlaced().getData()) {
			case 2:
				chestFace = BlockFace.NORTH;
				break;
			case 3:
				chestFace = BlockFace.SOUTH;
				break;
			case 4:
				chestFace = BlockFace.WEST;
				break;
			case 5:
				chestFace = BlockFace.EAST;
				break;
			default:
				chestFace = BlockFace.NORTH;
				break;
			} 
			LootChestsManager.addLootChest(e.getBlock().getLocation(), chestFace, e.getPlayer());
		} 
	}
  
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getBlock() != null && e.getBlock().getType().equals(Material.CHEST)) {
			for (LootChest chest : LootChestsManager.getLootsChests()) {
				if (chest.getLocation().equals(e.getBlock().getLocation())) {
					if (e.getPlayer().hasPermission("lc.admin") || e.getPlayer().hasPermission("lc.*") || e.getPlayer().isOp()) {
						LootChestsManager.delLootChest(chest.getLocation());
						e.getPlayer().sendMessage("§aVous avez détruit un LootChest avec succès");
						break;
					}
					e.setCancelled(true);
					break;
				} 
			} 
		}
	}

  
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.hasBlock() && e.getClickedBlock() != null && e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.CHEST)) {
			for (LootChest chest : LootChestsManager.getLootsChests()) {
				if (chest.getLocation().equals(e.getClickedBlock().getLocation())) {
					e.setCancelled(true);
					if (chest.getLastOpen() + (Main.getInstance().getConfigs().getInt("RefillTime") * 1000) < System.currentTimeMillis()) {
						int tier = LootChestsManager.getPlayerGearTier(e.getPlayer());
						ArrayList<LootDrop> drops = new ArrayList<LootDrop>(LootChestsDrops.getDrops(tier));
						e.getPlayer().sendMessage("§aVous avez obtenu §8" + drops.size() + " §aloot avec §8" + LootChestsDrops.tierToDoubleLootRate(tier) + "%§a Chance de Double Loot:");
						for (LootDrop loot : drops) {
							if (loot.isItems()) {
								int amount = 0;
								if (loot.isFixAmount()) {
									amount = loot.getMinAmount();
								} else {
									amount = (int)(Math.random() * (loot.getMaxAmount() - loot.getMinAmount()) + loot.getMinAmount());
								}
								ItemStack lootItem = new ItemStack(loot.getLootItems());
								lootItem.setAmount(amount);
								HashMap<Integer, ItemStack> invFull = e.getPlayer().getInventory().addItem(new ItemStack[] { lootItem });
								invFullToDrop(invFull, e.getPlayer());
								String lootMessage = loot.getLootMessage();
								e.getPlayer().sendMessage(lootMessage.replaceAll("%amount%", String.valueOf(amount)).replaceAll("%player%", e.getPlayer().getName()));
								lootItem = null;
								continue;
							} 
							for (String cmd : loot.getLootCmds()) {
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("%player%", e.getPlayer().getName()));
							}
							String lootMessage = loot.getLootMessage();
							e.getPlayer().sendMessage(lootMessage.replaceAll("%player%", e.getPlayer().getName()));
						} 
            
						chest.updateLastOpen(); break;
					} 
					e.getPlayer().sendMessage("§cCe Coffre à Loot a déjà été récolté !");
					long timeLeft = (((Main.getInstance().getConfigs().getInt("RefillTime") * 1000L)  - (System.currentTimeMillis() - chest.getLastOpen())) / 1000L);
					e.getPlayer().sendMessage("§7Prochain Loot dans " + timeLeft + " secs");
					break;
				} 
			} 
		}
	}
	
  
	public static void invFullToDrop(HashMap<Integer, ItemStack> invFull, Player player) {
		for (int i = 0; i < invFull.values().size(); i++)
			player.getWorld().dropItem(player.getLocation(), (new ArrayList<ItemStack>(invFull.values())).get(i)); 
	}
}