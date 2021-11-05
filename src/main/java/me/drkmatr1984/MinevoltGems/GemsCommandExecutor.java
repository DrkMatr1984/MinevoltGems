package me.drkmatr1984.MinevoltGems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.haroldstudios.hexitextlib.HexResolver;

import me.clip.placeholderapi.PlaceholderAPI;

public class GemsCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
    if (args.length == 0) {
    	if(cs instanceof Player) {
    	    cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().balance));
    	}else {
    		cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().helpMessage));
    	}    
    } else if (args.length == 1) {
    	if(args[0].equalsIgnoreCase("balance") || args[0].equalsIgnoreCase("bal")) {
        	if(cs instanceof Player) {
          	    cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().balance));
          	}else {
          		cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().helpMessage));
          	}
        }else if(args[0].equalsIgnoreCase("help")) {
        	if(cs instanceof Player) {
          	    cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().helpPlayer));
          	}else {
          		cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().helpMessage));
          	}
        }
    	else if (args[0].equalsIgnoreCase("add")) {
          if (cs.hasPermission("gems.add")) {
        	  cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().helpMessage));
          } else {
            cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().noPermission));
          } 
        } else if (args[0].equalsIgnoreCase("remove")) {
          if (cs.hasPermission("gems.remove")) {
        	  cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().helpMessage));
          } else {
        	  cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().noPermission));
          } 
        } else if (args[0].equalsIgnoreCase("set")) {
          if (cs.hasPermission("gems.set")) {
        	  cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().helpMessage));
          } else {
        	  cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().noPermission));
          } 
        } else {
          if (cs.hasPermission("gems.balance.other")) {
        	  String name = args[0];
              if (GemsAPI.isRegistered(name)) {
                Integer gems = GemsAPI.getGems(name);
                String message = MinevoltGems.getLangInstance().balanceOther;
                message = message.replace("%amount%", gems.toString());
                message = message.replace("%name%", name);
                cs.sendMessage(getFormattedMessage(cs, message));
              } else {
            	String message = MinevoltGems.getLangInstance().notRegistered;
            	message = message.replace("%name%", name);
                cs.sendMessage(getFormattedMessage(cs, message));
              }
          } else {
          	  cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().noPermission));
          } 
        }  
    } else if (args.length == 2) {
      if (args[0].equalsIgnoreCase("balance") || args[0].equalsIgnoreCase("bal")) {
  		if (cs.hasPermission("gems.balance.other")) {
        	  String name = args[1];
              if (GemsAPI.isRegistered(name)) {
                Integer gems = GemsAPI.getGems(name);
                String message = MinevoltGems.getLangInstance().balanceOther;
                message = message.replace("%amount%", gems.toString());
                message = message.replace("%name%", name);
                cs.sendMessage(getFormattedMessage(cs, message));
              } else {
            	String message = MinevoltGems.getLangInstance().notRegistered;
            	message = message.replace("%name%", name);
                cs.sendMessage(getFormattedMessage(cs, message));
              }
          } else {
          	  cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().noPermission));
          }	
  	  }else if (args[0].equalsIgnoreCase("add")) {
        if (cs.hasPermission("gems.add"))
        	cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().helpMessage)); 
      } else if (args[0].equalsIgnoreCase("remove")) {
        if (cs.hasPermission("gems.remove"))
        	cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().helpMessage)); 
      } else if (args[0].equalsIgnoreCase("set")) {
          if (cs.hasPermission("gems.set"))
        	  cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().helpMessage)); 
      } else if (cs.hasPermission("gems.add") || cs.hasPermission("gems.remove") || cs.hasPermission("gems.set")) {
    	  cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().helpMessage));
      } else {
    	  cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().noPermission));
      } 
    } else if (args.length == 3) {
    	if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("give") ) {
        if (cs.hasPermission("gems.add")) {
          String name = args[1];
          if (GemsAPI.isRegistered(name)) {
            Integer gems = Integer.valueOf(args[2]).intValue();
            GemsAPI.addGems(name, gems);
            String message = MinevoltGems.getLangInstance().gemsAdded;
            message = message.replace("%amount%", gems.toString());
            message = message.replace("%name%", name);
            cs.sendMessage(getFormattedMessage(cs, message));
            OfflinePlayer op = Bukkit.getOfflinePlayer(GemsAPI.getUUIDfromPlayerName(name));
            if(op.isOnline()) {
            	Player p = (Player) op;
            	String pMessage = MinevoltGems.getLangInstance().gemsAddedPlayer;
            	pMessage = pMessage.replace("%amount%", gems.toString());
            	pMessage = pMessage.replace("%name%", p.getName());
            	p.sendMessage(getFormattedMessage(cs, pMessage));
            }
          } else {
        	  String message = MinevoltGems.getLangInstance().notRegistered;
          	  message = message.replace("%name%", name);
              cs.sendMessage(getFormattedMessage(cs, message));
          } 
        } else {
        	cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().noPermission));
        } 
      } else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("take")) {
        if (cs.hasPermission("gems.remove")) {
          String name = args[1];
          if (GemsAPI.isRegistered(name)) {
            Integer gems = Integer.valueOf(args[2]).intValue();
            if (GemsAPI.removeGems(name, gems)) {
              String message = MinevoltGems.getLangInstance().gemsRemoved;
              message = message.replace("%amount%", gems.toString());
              message = message.replace("%name%", name);	
              cs.sendMessage(getFormattedMessage(cs, message));
              OfflinePlayer op = Bukkit.getOfflinePlayer(GemsAPI.getUUIDfromPlayerName(name));
              if(op.isOnline()) {
              	  Player p = (Player) op;
              	  String pMessage = MinevoltGems.getLangInstance().gemsRemovedPlayer;
              	  pMessage = pMessage.replace("%amount%", gems.toString());
              	  pMessage = pMessage.replace("%name%", p.getName());
              	  p.sendMessage(getFormattedMessage(cs, pMessage));
              }
            } else {
            	String message = MinevoltGems.getLangInstance().notEnoughGems;
            	message = message.replace("%amount%", gems.toString());
            	message = message.replace("%name%", name);	
                cs.sendMessage(getFormattedMessage(cs, message));
            } 
          } else {
        	  String message = MinevoltGems.getLangInstance().notRegistered;
        	  message = message.replace("%name%", name);
              cs.sendMessage(getFormattedMessage(cs, message));
          } 
        } else {
        	cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().noPermission));
        } 
      } else if (args[0].equalsIgnoreCase("set")) {
        if (cs.hasPermission("gems.set")) {
          String name = args[1];
          if (GemsAPI.isRegistered(name)) {
            Integer gems = Integer.valueOf(args[2]).intValue();
            if (GemsAPI.setGems(name, gems)) {
            	String message = MinevoltGems.getLangInstance().gemsSet;
            	message = message.replace("%amount%", gems.toString());
            	message = message.replace("%name%", name);
                cs.sendMessage(getFormattedMessage(cs, message));
                OfflinePlayer op = Bukkit.getOfflinePlayer(GemsAPI.getUUIDfromPlayerName(name));
                if(op.isOnline()) {
                	  Player p = (Player) op;
                	  String pMessage = MinevoltGems.getLangInstance().gemsSetPlayer;
                	  pMessage = pMessage.replace("%amount%", gems.toString());
                	  pMessage = pMessage.replace("%name%", p.getName());
                	  p.sendMessage(getFormattedMessage(cs, pMessage));
                }
            } else {
            	String message = MinevoltGems.getLangInstance().cantSetGems;
            	message = message.replace("%amount%", gems.toString());
            	message = message.replace("%name%", name);
                cs.sendMessage(getFormattedMessage(cs, message));
            } 
          } else {
        	  String message = MinevoltGems.getLangInstance().notRegistered;
        	  message = message.replace("%name%", name);
              cs.sendMessage(getFormattedMessage(cs, message));
          } 
        } else {
        	cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().noPermission));
        } 
      }else if(args[0].equalsIgnoreCase("pay") || args[0].equalsIgnoreCase("send")){
    	  if(cs instanceof Player) {
    		  Player p = (Player) cs;
    		  if (cs.hasPermission("gems.pay")) {
        		  String name = args[1];
                  if (GemsAPI.isRegistered(name)) {
                	if(!p.getName().equalsIgnoreCase(name)) {
                		Integer gems = Integer.valueOf(args[2]).intValue();
                        if (GemsAPI.removeGems(p, gems)) {
                        	GemsAPI.addGems(name, gems);
                        	OfflinePlayer op = Bukkit.getOfflinePlayer(GemsAPI.getUUIDfromPlayerName(name));
                            if(op.isOnline()) {
                            	Player player = (Player) op;
                            	String pMessage = MinevoltGems.getLangInstance().gemsPaidPlayer;
                            	pMessage = pMessage.replace("%amount%", gems.toString());
                            	pMessage = pMessage.replace("%name%", p.getName());
                            	player.sendMessage(getFormattedMessage(cs, pMessage));
                            }
                            String message = MinevoltGems.getLangInstance().gemsPaid;
                        	message = message.replace("%amount%", gems.toString());
                        	message = message.replace("%name%", name);	
                            cs.sendMessage(getFormattedMessage(cs, message));
                        } else {
                        	String message = MinevoltGems.getLangInstance().notEnoughGems;
                        	message = message.replace("%amount%", gems.toString());
                        	message = message.replace("%name%", p.getName());	
                            cs.sendMessage(getFormattedMessage(cs, message));
                        }
                	}else {
                		cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().cantPayYourself));
                	}
                  } else {
                	  String message = MinevoltGems.getLangInstance().notRegistered;
                	  message = message.replace("%name%", name);
                      cs.sendMessage(getFormattedMessage(cs, message));
                  }
        	  }else {
              	cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().noPermission));
              }
    	  } else {
        	  cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().helpMessage));
          }  	  
      } else {
    	  cs.sendMessage(getFormattedMessage(cs, MinevoltGems.getLangInstance().helpMessage));
      } 
    } 
    return true;
  }
  
  public static String getFormattedMessage(CommandSender cs, String message) {
	  message = message.replace("%plugin_prefix%", MinevoltGems.getConfigInstance().pr);
	  if(cs instanceof Player) {
		  Player p = (Player)cs;
		  message = message.replace("%name%", p.getName());
		  message = message.replace("%amount%", Integer.valueOf(GemsAPI.getGems(p)).toString());
		  if(MinevoltGems.getInstance().placeHolders) {
			  message = PlaceholderAPI.setPlaceholders(p, message);
		  }
		  if(MinevoltGems.getInstance().mvdwPlaceHolders) {
			  message = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, message);
		  }
	  }
	  message = message.replace("%name%", cs.getName());
	  message = message.replace("%command%", MinevoltGems.getLangInstance().command);
	  message = message.replace("%gems%", MinevoltGems.getLangInstance().currencyName);
	  if(MinevoltGems.getInstance().placeHolders) {
		  message = PlaceholderAPI.setPlaceholders(null, message);
	  }
	  if(MinevoltGems.getInstance().mvdwPlaceHolders) {
		  message = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(null, message);
	  }
	  // Do Colors
	  message = HexResolver.parseHexString(message);
      message = ChatColor.translateAlternateColorCodes('&', message);
	  return message;
  }
}
