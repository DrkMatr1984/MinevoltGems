package me.drkmatr1984.MinevoltGems;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GemsCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
    if (args.length == 0 && cs instanceof Player) {
      Player p = (Player)cs;
      int gems = GemsAPI.getGems(p);
      p.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &7You have &f" + gems + " " + (MinevoltGems.getConfigInstance()).currencyName));
    } else if (args.length == 1) {
      if (cs.hasPermission("gems.other")) {
        if (args[0].equalsIgnoreCase("add")) {
          if (cs.hasPermission("gems.add")) {
            cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &7Use&8: &8/&e" + (MinevoltGems.getConfigInstance()).command + " &aadd&8|&cremove&8|&eset &8<&bPlayer&8> &8<&bAmount&8>"));
          } else {
            cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &cYou don't have permission!"));
          } 
        } else if (args[0].equalsIgnoreCase("remove")) {
          if (cs.hasPermission("gems.remove")) {
            cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &7Use&8: &8/&e" + (MinevoltGems.getConfigInstance()).command + " &aadd&8|&cremove&8|&eset &8<&bPlayer&8> &8<&bAmount&8>"));
          } else {
            cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &cYou don't have permission!"));
          } 
        } else if (args[0].equalsIgnoreCase("set")) {
          if (cs.hasPermission("gems.set")) {
            cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &7Use&8: &8/&e" + (MinevoltGems.getConfigInstance()).command + " &aadd&8|&cremove&8|&eset &8<&bPlayer&8> &8<&bAmount&8>"));
          } else {
            cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &cYou don't have permission!"));
          } 
        } else {
          String name = args[0];
          if (GemsAPI.isRegistered(name)) {
            int gems = GemsAPI.getGems(name);
            cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &b" + name + "&7 has &b" + gems + " &7Gems"));
          } else {
            cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &cThis Player is not Registered!"));
          } 
        } 
      } else {
        cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &cYou don't have permission!"));
      } 
    } else if (args.length == 2) {
      if (args[0].equalsIgnoreCase("add")) {
        if (cs.hasPermission("gems.add"))
          cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &7Use&8: &8/&e" + (MinevoltGems.getConfigInstance()).command + " &aadd&8|&cremove&8|&eset &8<&bPlayer&8> &8<&bAmount&8>")); 
      } else if (args[0].equalsIgnoreCase("remove")) {
        if (cs.hasPermission("gems.remove"))
          cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &7Use&8: &8/&e" + (MinevoltGems.getConfigInstance()).command + " &aadd&8|&cremove&8|&eset &8<&bPlayer&8> &8<&bAmount&8>")); 
      } else if (args[0].equalsIgnoreCase("set")) {
        if (cs.hasPermission("gems.set"))
          cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &7Use&8: &8/&e" + (MinevoltGems.getConfigInstance()).command + " &aadd&8|&cremove&8|&eset &8<&bPlayer&8> &8<&bAmount&8>")); 
      } else if (cs.hasPermission("gems.add") || cs.hasPermission("gems.remove") || cs.hasPermission("gems.set")) {
        cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &7Use&8: &8/&e" + (MinevoltGems.getConfigInstance()).command + " &aadd&8|&cremove&8|&eset &8<&bPlayer&8> &8<&bAmount&8>"));
      } else {
        cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &cYou don't have permission!"));
      } 
    } else if (args.length == 3) {
      if (args[0].equalsIgnoreCase("add")) {
        if (cs.hasPermission("gems.add")) {
          String name = args[1];
          if (GemsAPI.isRegistered(name)) {
            int gems = Integer.valueOf(args[2]).intValue();
            GemsAPI.addGems(name, gems);
            cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &f" + gems + " " + (MinevoltGems.getConfigInstance()).currencyName + " have been successfully added to " + name + "'s account!"));
          } else {
            cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &cThis Player is not Registered!"));
          } 
        } else {
          cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &cYou don't have permission!"));
        } 
      } else if (args[0].equalsIgnoreCase("remove")) {
        if (cs.hasPermission("gems.remove")) {
          String name = args[1];
          if (GemsAPI.isRegistered(name)) {
            int gems = Integer.valueOf(args[2]).intValue();
            if (GemsAPI.removeGems(name, gems)) {
              cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &f" + gems + " " + (MinevoltGems.getConfigInstance()).currencyName + " have been successfully removed from " + name + "'s account!"));
            } else {
              cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &cNot enough " + (MinevoltGems.getConfigInstance()).currencyName + "&c in " + name + "'s account!"));
            } 
          } else {
            cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &cThis Player is not Registered!"));
          } 
        } else {
          cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &cYou don't have permission!"));
        } 
      } else if (args[0].equalsIgnoreCase("set")) {
        if (cs.hasPermission("gems.set")) {
          String name = args[1];
          if (GemsAPI.isRegistered(name)) {
            int gems = Integer.valueOf(args[2]).intValue();
            if (GemsAPI.setGems(name, gems)) {
              cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &aYou have successfully set " + name + "'s " + (MinevoltGems.getConfigInstance()).currencyName + "&a to &f" + gems));
            } else {
              cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &cYou can't set " + (MinevoltGems.getConfigInstance()).currencyName + "&c to a negative amount!"));
            } 
          } else {
            cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &cThis Player is not Registered!"));
          } 
        } else {
          cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &cYou don't have permission!"));
        } 
      } else {
        cs.sendMessage(getColoredMessage((MinevoltGems.getConfigInstance()).pr + " &7Use&8: &8/&e" + (MinevoltGems.getConfigInstance()).command + " &aadd&8|&cremove&8|&eset &8<&bPlayer&8> &8<&bAmount&8>"));
      } 
    } 
    return true;
  }
  
  public static String getColoredMessage(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }
}
