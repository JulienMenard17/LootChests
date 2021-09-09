package ca.sharkmenard.lootchests;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GrindCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission("lc.grind") || p.hasPermission("lc.*") || p.isOp())
				p.openInventory(GrindMenu.getGrindMenu());
		} else {
			sender.sendMessage("§cVous devez être un Joueur pour effectuer cette commande !");
			return false;
		}
		return true;
	}
}
