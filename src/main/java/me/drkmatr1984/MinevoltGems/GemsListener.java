package me.drkmatr1984.MinevoltGems;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GemsListener implements Listener {
  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    if (!GemsAPI.isRegistered(p))
      GemsAPI.register(p, (MinevoltGems.getConfigInstance()).startAmount); 
  }
}
