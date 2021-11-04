package me.drkmatr1984.MinevoltGems;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class GemsConfig {
  private MinevoltGems plugin = null;
  
  private FileConfiguration config = null;
  
  public String pr = "&8[&bMine&evolt&aGems&8]&r";
  
  public String command = "gems";
  
  public String currencyName = "&egems";
  
  public int startAmount = 0;
  
  public StorageMethod method;
  
  public String host = "127.0.0.1";
  
  public String port = "3306";
  
  public String database = "MinevoltGems";
  
  public String table = "minecraft";
  
  public String username = "root";
  
  public String password = "example";
  
  public boolean useSSL = false;
  
  public int interval = 10;
  
  public GemsConfig(Plugin plugin) {
    this.plugin = (MinevoltGems)plugin;
    this.config = this.plugin.getConfig();
    this.plugin.getConfig().options().copyDefaults(true);
    this.plugin.saveConfig();
    loadConfig();
    Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getColoredMessage(this.pr + " &aConfiguration initialized"));
  }
  
  public void loadConfig() {
    this.pr = this.config.getString("messagePrefix");
    this.currencyName = this.config.getString("currencyName");
    this.command = this.config.getString("commandName");
    this.startAmount = this.config.getInt("start-amount");
    this.method = StorageMethod.valueOf(this.config.getString("Storage.StorageMethod"));
    if (this.method.equals(StorageMethod.mysql)) {
      this.host = this.config.getString("Storage.mysql.host");
      this.port = this.config.getString("Storage.mysql.port");
      this.database = this.config.getString("Storage.mysql.database");
      this.table = this.config.getString("Storage.mysql.table");
      this.username = this.config.getString("Storage.mysql.username");
      this.password = this.config.getString("Storage.mysql.password");
      this.useSSL = Boolean.valueOf(this.config.getString("Storage.mysql.useSSL").toUpperCase()).booleanValue();
    } else {
      this.interval = this.config.getInt("Storage.file.save-interval");
    } 
  }
  
  public FileConfiguration getGemsConfig() {
    return this.config;
  }
  
  public void saveConfig() {
    this.plugin.saveConfig();
  }
  
  public void reloadConfig() {
    this.plugin.reloadConfig();
  }
  
  public enum StorageMethod {
    file, mysql;
  }
}
