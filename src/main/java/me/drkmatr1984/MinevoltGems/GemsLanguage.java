package me.drkmatr1984.MinevoltGems;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class GemsLanguage
{
	private File languageFile;
	private FileConfiguration language;
	
	public String languageType = "en";
	public String command = "gems";
	public String currencyName = "&egems";
	public String balance = "%plugin_prefix% &7You have %amount% %gems%";
	public String balanceOther = "%plugin_prefix% %name% &7has &b%amount% %gems%";
	public String helpPlayer = "%plugin_prefix% &7Use&8: &8/&e%command% &bbal&8|&apay&8 &8<&bPlayer&8> &8<&bAmount&8>";
	public String helpMessage = "%plugin_prefix% &7Use&8: &8/&e%command% &bbal&8|&aadd&8|&cremove&8|&eset &8<&bPlayer&8> &8<&bAmount&8>";
	public String noPermission = "%plugin_prefix% &cYou don't have permission!";
	public String notRegistered = "%plugin_prefix% &cThe player %name% is not Registered!";
	public String gemsAdded = "%plugin_prefix% &f%amount% %gems% have been successfully added to %name%'s account!";
	public String gemsRemoved = "%plugin_prefix% &f%amount% %gems% have been successfully added to %name%'s account!";
	public String gemsAddedPlayer = "%plugin_prefix% &f%amount% %gems% have been added to your account!";
	public String gemsRemovedPlayer = "%plugin_prefix% &f%amount% %gems% have been removed from your account!";
	public String notEnoughGems = "%plugin_prefix% &cNot enough %gems% &cin %name%'s account!";
	public String gemsSet = "%plugin_prefix% &aYou have successfully set %name%'s %gems% &ato %amount% %gems%";
	public String gemsSetPlayer = "%plugin_prefix% &aYour %gems% &ahave been set to &f%amount% %gems%!";
	public String cantSetGems = "%plugin_prefix% &cYou can't set %gems% &cto a negative amount!";
	public String gemsPaid = "%plugin_prefix% &aYou have successfully sent &f%amount% %gems% &ato &f%name%'s &aaccount!";
	public String gemsPaidPlayer = "%plugin_prefix% %name% has sent you &f%amount% %gems%!";
	public String cantPayYourself = "%plugin_prefix% You cannot pay yourself!";	
	
	private MinevoltGems plugin;
	private File languageFolder;
	
	public GemsLanguage(Plugin plugin){
		this.plugin = (MinevoltGems) plugin;
		languageType = MinevoltGems.getConfigInstance().language;
		initLanguageFile();
	}
	
	public void initLanguageFile(){
		saveDefaultLanguageFile();
		loadLanguageFile();
	}
	
	public void saveDefaultLanguageFile() {
		this.languageFolder = new File(this.plugin.getDataFolder().toString() + "/languages");
		if (!this.languageFolder.exists())
		      this.languageFolder.mkdir();
		if (languageFile == null) {
			languageFile = new File(this.languageFolder, languageType + ".yml");
	    }
	    if (!languageFile.exists()) {           
	    	plugin.saveResource("languages/" + languageType + ".yml", false);
	    }   
	}
	  
	public void loadLanguageFile(){
		language = YamlConfiguration.loadConfiguration(languageFile);
		this.currencyName = this.language.getString("currencyName");
	    this.command = this.language.getString("commandName");
	    this.balance = this.language.getString("balance");
	    this.balanceOther = this.language.getString("balanceOther");
	    this.helpPlayer = this.language.getString("helpPlayer");
	    this.helpMessage = this.language.getString("helpMessage");
	    this.noPermission = this.language.getString("noPermission");
	    this.notRegistered = this.language.getString("notRegistered");
	    this.gemsAdded = this.language.getString("gemsAdded");
	    this.gemsRemoved = this.language.getString("gemsRemoved");
	    this.gemsAddedPlayer = this.language.getString("gemsAddedPlayer");
	    this.gemsRemovedPlayer = this.language.getString("gemsRemovedPlayer");
	    this.notEnoughGems = this.language.getString("notEnoughGems");
	    this.gemsSet = this.language.getString("gemsSet");
	    this.gemsSetPlayer = this.language.getString("gemsSetPlayer");
	    this.cantSetGems = this.language.getString("cantSetGems");
	    this.gemsPaid = this.language.getString("gemsPaid");
	    this.gemsPaidPlayer = this.language.getString("gemsPaidPlayer");
	    this.cantPayYourself = this.language.getString("cantPayYourself");
	}
}