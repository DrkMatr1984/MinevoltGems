package me.drkmatr1984.MinevoltGems.placeholders;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.drkmatr1984.MinevoltGems.GemsAPI;

public class PlaceholderAPIHook extends PlaceholderExpansion {
	
	private static JavaPlugin plugin;
	
	public PlaceholderAPIHook(JavaPlugin gems) {
		plugin = gems;
	}
	
    @Override
    public boolean persist(){
        return true;
    }  

   @Override
   public boolean canRegister(){
       return true;
   }

   @Override
   public String getAuthor(){
       return plugin.getDescription().getAuthors().toString();
   }

	@Override
	public String getIdentifier(){
		return "MinevoltGems";
	}

	@Override
	public String getVersion(){
		return plugin.getDescription().getVersion();
	}

	@Override
	public String onPlaceholderRequest(Player player, String identifier){
		if(player == null){
			return "";
		}
		if(identifier.equals("balance")){
			return Integer.valueOf(GemsAPI.getGems(player)).toString();
		}
		return null;
	}
}
