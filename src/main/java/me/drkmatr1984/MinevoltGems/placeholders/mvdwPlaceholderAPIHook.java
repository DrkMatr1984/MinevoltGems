package me.drkmatr1984.MinevoltGems.placeholders;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import me.drkmatr1984.MinevoltGems.GemsAPI;
import me.drkmatr1984.MinevoltGems.MinevoltGems;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class mvdwPlaceholderAPIHook {
  public static boolean MinevoltGemsBalanceHook() {
    PlaceholderReplacer balance = new PlaceholderReplacer() {
        public String onPlaceholderReplace(PlaceholderReplaceEvent event) {
          if (event.getPlayer() != null) {
            Player p = event.getPlayer();
            return Integer.valueOf(GemsAPI.getGems(p)).toString();
          } 
          return "0";
        }
      };
    return PlaceholderAPI.registerPlaceholder((Plugin)MinevoltGems.getInstance(), "MinevoltGems_balance", balance);
  }
}
