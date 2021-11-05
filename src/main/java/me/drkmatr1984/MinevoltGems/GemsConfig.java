package me.drkmatr1984.MinevoltGems;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class GemsConfig {
  private MinevoltGems plugin = null;
  
  private File fileConfig = null;
  
  public String language = "en";
  
  public String pr = "&8[&bMine&evolt&aGems&8]&r";
  
  private File dataFolder;
  
  private FileConfiguration config;
  
  public boolean autoRegisterNew = true;
  
  public int startAmount = 0;
  
  public StorageMethod method;
  
  public String host = "127.0.0.1";
  
  public String port = "3306";
  
  public String database = "MinevoltGems";
  
  public String table = "minecraft";
  
  public String username = "root";
  
  public String password = "example";
  
  public boolean useSSL = false;
  
  public int interval = 5;
  
  public GemsConfig(Plugin plugin) {
    this.plugin = (MinevoltGems)plugin;
    this.dataFolder = new File(this.plugin.getDataFolder().toString());
    if (!this.dataFolder.exists())
        this.dataFolder.mkdir(); 
      if (this.fileConfig == null)
        this.fileConfig = new File(this.dataFolder, "config.yml"); 
      if (!this.fileConfig.exists())
        this.plugin.saveResource("config.yml", false); 
    loadConfig();
  }
  
  public void loadConfig() {
	this.config = YamlConfiguration.loadConfiguration(fileConfig);
	this.language = this.config.getString("language");
    this.pr = this.config.getString("messagePrefix");
    this.autoRegisterNew = Boolean.valueOf(this.config.getString("autoRegisterNew"));
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
