package me.drkmatr1984.MinevoltGems.storage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import me.drkmatr1984.MinevoltGems.GemsCommandExecutor;
import me.drkmatr1984.MinevoltGems.MinevoltGems;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class YMLFile {
  private File usersFile;
  
  private File dataFolder;
  
  private FileConfiguration users;
  
  public HashMap<UUID, Integer> players;
  
  private Plugin plugin;
  
  public YMLFile(Plugin plugin) {
    this.plugin = plugin;
    this.dataFolder = new File(this.plugin.getDataFolder().toString() + "/data");
  }
  
  public void initLists() {
    saveDefaultUserList();
    loadPlayers();
    Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &aFile storage successfully initialized!"));
  }
  
  public void saveDefaultUserList() {
    if (!this.dataFolder.exists())
      this.dataFolder.mkdir(); 
    if (this.usersFile == null)
      this.usersFile = new File(this.dataFolder, "players.yml"); 
    if (!this.usersFile.exists())
      this.plugin.saveResource("data/players.yml", false); 
  }
  
  public void loadPlayers() {
    this.players = new HashMap<>();
    this.users = (FileConfiguration)YamlConfiguration.loadConfiguration(this.usersFile);
    if (this.users.getKeys(false) != null)
      for (String s : this.users.getKeys(false))
        this.players.put(UUID.fromString(s), Integer.valueOf(this.users.getInt(s + ".gems")));  
  }
  
  public void saveUserList() {
    if (this.players != null)
      for (UUID id : this.players.keySet())
        this.users.set(id.toString() + ".gems", this.players.get(id));  
    if (this.usersFile.exists())
      this.usersFile.delete(); 
    try {
      this.users.save(this.usersFile);
    } catch (IOException e) {
      e.printStackTrace();
    } 
    try {
      this.usersFile.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
}
