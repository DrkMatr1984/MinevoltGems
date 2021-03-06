package me.drkmatr1984.MinevoltGems;

import java.lang.reflect.Field;

import me.drkmatr1984.MinevoltGems.GemsConfig.StorageMethod;
import me.drkmatr1984.MinevoltGems.placeholders.PlaceholderAPIHook;
import me.drkmatr1984.MinevoltGems.placeholders.mvdwPlaceholderAPIHook;
import me.drkmatr1984.MinevoltGems.storage.MySQL;
import me.drkmatr1984.MinevoltGems.storage.YMLFile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MinevoltGems extends JavaPlugin {
  private static Plugin gems;
  
  private static GemsConfig config;
  
  private static GemsLanguage language;
  
  private MySQL sql;
  
  private YMLFile file;
  
  public boolean placeHolders = false;
  
  public boolean mvdwPlaceHolders = false;
  
  private static CommandMap cmap;
  
  private CCommand gemsCommand;
  
  public void onEnable() {
    gems = (Plugin)this;
    BukkitRunnable fileSave = null;
    if(MinevoltGems.config == null)
    	MinevoltGems.config = new GemsConfig(gems);
    if(MinevoltGems.language == null)
    	MinevoltGems.language = new GemsLanguage(gems);
    Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getFormattedMessage(Bukkit.getConsoleSender(), (MinevoltGems.getConfigInstance()).pr + " &aConfiguration initialized"));
    if(!MinevoltGems.config.method.equals(StorageMethod.mysql)) {
    	fileSave = new BukkitRunnable() {
        public void run() {
          MinevoltGems.this.file.saveUserList();
        }
      };
    }
    switch (MinevoltGems.config.method) {
      case file:
        this.file = new YMLFile(gems);
        this.file.initLists();
        fileSave.runTaskTimerAsynchronously((Plugin)this, (MinevoltGems.config.interval * 60 * 20), (MinevoltGems.config.interval * 60 * 20));
        Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getFormattedMessage(Bukkit.getConsoleSender(), (MinevoltGems.getConfigInstance()).pr + " &aStorageMethod: &eFile"));
        Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getFormattedMessage(Bukkit.getConsoleSender(), (MinevoltGems.getConfigInstance()).pr + " &bInterval: &e" + MinevoltGems.config.interval));
        break;
      case mysql:
        this.sql = new MySQL(this);
        this.sql.connect();
        GemsAPI.createTable(this.sql);
        Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getFormattedMessage(Bukkit.getConsoleSender(), (MinevoltGems.getConfigInstance()).pr + " &aStorageMethod: &eMySQL"));
        Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getFormattedMessage(Bukkit.getConsoleSender(), (MinevoltGems.getConfigInstance()).pr + " &bMySQL Successfully Connected"));
        break;
      default:
        Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getFormattedMessage(Bukkit.getConsoleSender(), (MinevoltGems.getConfigInstance()).pr + " &cStorageMethod must be file or mysql"));
        Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getFormattedMessage(Bukkit.getConsoleSender(), (MinevoltGems.getConfigInstance()).pr + " &aDefaulting to &eFile"));
        this.file = new YMLFile(gems);
        this.file.initLists();
        fileSave.runTaskTimerAsynchronously((Plugin)this, (MinevoltGems.config.interval * 60 * 20), (MinevoltGems.config.interval * 60 * 20));
        Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getFormattedMessage(Bukkit.getConsoleSender(), (MinevoltGems.getConfigInstance()).pr + " &bInterval: &e" + MinevoltGems.config.interval));
        break;
    } 
    if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI"))
      if (mvdwPlaceholderAPIHook.MinevoltGemsBalanceHook()) {
        Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getFormattedMessage(Bukkit.getConsoleSender(), (MinevoltGems.getConfigInstance()).pr + " &awas successfully registered with &5mvdwPlaceholderAPI"));
        this.mvdwPlaceHolders = true;
      }  
    if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
	{
		if(new PlaceholderAPIHook(this).register()) {
			Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getFormattedMessage(Bukkit.getConsoleSender(), (MinevoltGems.getConfigInstance()).pr + " &awas successfully registered with &6PlaceholderAPI"));
			this.placeHolders = true;
		}
	}
    if(MinevoltGems.getConfigInstance().autoRegisterNew) {
    	Bukkit.getPluginManager().registerEvents(new GemsListener(), (Plugin)this);
    }   
    RegisterCommands();
    Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getFormattedMessage(Bukkit.getConsoleSender(), (MinevoltGems.getConfigInstance()).pr + " &aSuccessfully Loaded!"));
  }
  
  public void onDisable() {
    if (MinevoltGems.config.method.equals(GemsConfig.StorageMethod.file))
      this.file.saveUserList();  
    unRegisterCommands();
  }
  
  public static MinevoltGems getInstance() {
    return (MinevoltGems)gems;
  }
  
  public static GemsConfig getConfigInstance() {
    return config;
  }
  
  public static GemsLanguage getLangInstance() {
	return language;
  }
  
  public MySQL getMySQL() {
    return this.sql;
  }
  
  public YMLFile getYMLFile() {
    return this.file;
  }
  
  public CommandMap getCommandMap() {
    return cmap;
  }
  
  public class CCommand extends Command {
    private CommandExecutor exe = null;
    
    protected CCommand(String name) {
      super(name);
    }
    
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
      if (this.exe != null)
        this.exe.onCommand(sender, this, commandLabel, args); 
      return false;
    }
    
    public void setExecutor(CommandExecutor exe) {
      this.exe = exe;
    }
  }
  
  private void RegisterCommands() {
    String cbukkit = Bukkit.getServer().getClass().getPackage().getName() + ".CraftServer";
    try {
      Class<?> clazz = Class.forName(cbukkit);
      try {
        Field f = clazz.getDeclaredField("commandMap");
        f.setAccessible(true);
        cmap = (CommandMap)f.get(Bukkit.getServer());
        if (!language.command.equals(null)) {
          this.gemsCommand = new CCommand(language.command);
          if(!cmap.register("minevoltgems", this.gemsCommand)) {
        	  Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getFormattedMessage(Bukkit.getConsoleSender(), (MinevoltGems.getConfigInstance()).pr + " &aCommand " + language.command
        			  + " command has already been taken. Defaulting to 'minevoltgems' for gems command."));
          }else {
        	  Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getFormattedMessage(Bukkit.getConsoleSender(), (MinevoltGems.getConfigInstance()).pr + " &aCommand " + language.command + " command Registered!"));
          }
          this.gemsCommand.setExecutor(new GemsCommandExecutor());        
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } catch (ClassNotFoundException e) {
      Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getFormattedMessage(Bukkit.getConsoleSender(), (MinevoltGems.getConfigInstance()).pr + " &ccould not be loaded, is this even Spigot or CraftBukkit?"));
      setEnabled(false);
    } 
  }
  
  private void unRegisterCommands() {
    String cbukkit = Bukkit.getServer().getClass().getPackage().getName() + ".CraftServer";
    try {
      Class<?> clazz = Class.forName(cbukkit);
      try {
        Field f = clazz.getDeclaredField("commandMap");
        f.setAccessible(true);
        cmap = (CommandMap)f.get(Bukkit.getServer());
        if (!this.gemsCommand.equals(null)) {
          this.gemsCommand.unregister(cmap);
          Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getFormattedMessage(Bukkit.getConsoleSender(), (MinevoltGems.getConfigInstance()).pr + " &aCommand " + language.command + " Unregistered!"));
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } catch (ClassNotFoundException e) {
      Bukkit.getConsoleSender().sendMessage(GemsCommandExecutor.getFormattedMessage(Bukkit.getConsoleSender(), (MinevoltGems.getConfigInstance()).pr + " &ccould not be unloaded, is this even Spigot or CraftBukkit?"));
      setEnabled(false);
    } 
  }
}
