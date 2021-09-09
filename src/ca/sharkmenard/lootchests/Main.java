package ca.sharkmenard.lootchests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.base.Charsets;


public class Main extends JavaPlugin {
	public static Main main;
	private File configFile;
	private File lootFile;
	private File messageFile;
	private File chestFile;
	private FileConfiguration config;
	private FileConfiguration lootConfig;
	private FileConfiguration messageConfig;
	private FileConfiguration chestConfig;
  
	public void onEnable() {
		main = this;
		createConfig();
		createLootConfig();
		createMessageConfig();
		createChestConfig();
		LootChestsDrops.loadDropTable();
		LootChestsManager.loadLootChests();
		getCommand("lootchests").setExecutor(new LootChestCommands());
		getCommand("grind").setExecutor(new GrindCommand());
		Bukkit.getPluginManager().registerEvents(new LootChestsListener(), this);
		Bukkit.getPluginManager().registerEvents(new GrindListener(), this);
	}


  
	public void onDisable() { 
		LootChestsManager.saveLootChests();
	}

  
	public void onReload() {
		createConfig();
		createMessageConfig();
		LootChestsManager.saveLootChests();
		createChestConfig();
		LootChestsManager.loadLootChests();
		LootChestsDrops.clearDropTable();
		createLootConfig();
		LootChestsDrops.loadDropTable();
	}

  
	public static Main getInstance() {
		return main; 
 	}
  
	public FileConfiguration getConfigs() {
		return config;
	}

	public FileConfiguration getLootConfig() {
		return lootConfig; 
	}

	public FileConfiguration getMessageConfig() {
		return messageConfig;
	}

	public FileConfiguration getChestConfig() {
		return chestConfig;
	}

  
	private void createConfig() {
		configFile = new File(getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			configFile.getParentFile().mkdirs();
			saveResource("config.yml", false);
		} 
    
		config = new YamlConfiguration();
		try {
			config.load(configFile);
		} catch (IOException|InvalidConfigurationException e) {
			e.printStackTrace();
		} 
	}
  
	private void createLootConfig() {
		lootFile = new File(getDataFolder(), "loots.yml");
		if (!lootFile.exists()) {
			lootFile.getParentFile().mkdirs();
			saveResource("loots.yml", false);
		} 
		
		lootConfig = new YamlConfiguration();
		try {
			lootConfig.load(new InputStreamReader(new FileInputStream(lootFile), Charsets.UTF_8));
		} catch (IOException|InvalidConfigurationException e) {
			e.printStackTrace();
		} 
	}
  
	private void createMessageConfig() {
		messageFile = new File(getDataFolder(), "messages.yml");
		if (!messageFile.exists()) {
			messageFile.getParentFile().mkdirs();
			saveResource("messages.yml", false);
		} 
		
		messageConfig = new YamlConfiguration();
		try {
			messageConfig.load(messageFile);
		} catch (IOException|InvalidConfigurationException e) {
			e.printStackTrace();
		} 
	}
  
	private void createChestConfig() {
		chestFile = new File(getDataFolder(), "chests.yml");
		if (!chestFile.exists()) {
			chestFile.getParentFile().mkdirs();
			saveResource("chests.yml", false);
		} 
		
		chestConfig = new YamlConfiguration();
		try {
			chestConfig.load(chestFile);
		} catch (IOException|InvalidConfigurationException e) {
			e.printStackTrace();
		} 
	}
  
	public void saveConfig() {
		try {
			config.save(configFile);
		} catch (IOException e) {
			getLogger().warning("Unable to save config.yml");
		} 
	}
	
	public void saveLootConfig() {
		try {
			lootConfig.save(lootFile);
		} catch (IOException e) {
			getLogger().warning("Unable to save loots.yml");
		} 
	}
	
	public void saveMessageConfig() {
		try {
			messageConfig.save(messageFile);
    	} catch (IOException e) {
    		getLogger().warning("Unable to save messages.yml");
    	} 
 	}
  
	public void saveChestConfig() {
		try {
			chestConfig.save(chestFile);
		} catch (IOException e) {
			getLogger().warning("Unable to save chests.yml");
		} 
	}
}
