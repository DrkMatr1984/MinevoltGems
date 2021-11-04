package me.drkmatr1984.MinevoltGems;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import org.bukkit.entity.Player;

public class GemsAPI {
  public static void createTable() {
    try {
      PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("CREATE TABLE IF NOT EXISTS " + (MinevoltGems.getConfigInstance()).table + " (playername VARCHAR(100), UUID VARCHAR(100), Gems INT(100))");
      ps.executeUpdate();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public static void register(Player p, int startAmount) {
    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql)) {
      try {
        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("INSERT INTO " + (MinevoltGems.getConfigInstance()).table + " (Playername, UUID, Gems) VALUES (?, ?, ?)");
        ps.setString(1, p.getName());
        ps.setString(2, p.getUniqueId().toString());
        ps.setInt(3, startAmount);
        ps.executeUpdate();
        ps.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } else {
      (MinevoltGems.getInstance().getYMLFile()).players.put(p.getUniqueId(), Integer.valueOf(startAmount));
    } 
  }
  
  public static void register(UUID pid, int startAmount) {
	    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql)) {
	      try {
	        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("INSERT INTO " + (MinevoltGems.getConfigInstance()).table + " (Playername, UUID, Gems) VALUES (?, ?, ?)");
	        ps.setString(1, getPlayerNamefromUUID(pid));
	        ps.setString(2, pid.toString());
	        ps.setInt(3, startAmount);
	        ps.executeUpdate();
	        ps.close();
	      } catch (Exception ex) {
	        ex.printStackTrace();
	      } 
	    } else {
	      (MinevoltGems.getInstance().getYMLFile()).players.put(pid, Integer.valueOf(startAmount));
	    } 
	  }
  
  public static boolean isRegistered(Player p) {
    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql))
      try {
        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("SELECT * FROM " + (MinevoltGems.getConfigInstance()).table + " WHERE UUID= ?");
        ps.setString(1, p.getUniqueId().toString());
        ResultSet rs = ps.executeQuery();
        boolean user = rs.next();
        rs.close();
        rs.close();
        return user;
      } catch (Exception ex) {
        ex.printStackTrace();
        return false;
      }  
    for (UUID id : (MinevoltGems.getInstance().getYMLFile()).players.keySet()) {
      if (id.toString().equals(p.getUniqueId().toString()))
        return true; 
    } 
    return false;
  }
  
  public static boolean isRegistered(String name) {
    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql))
      try {
        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("SELECT * FROM " + (MinevoltGems.getConfigInstance()).table + " WHERE Playername= ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        boolean user = rs.next();
        rs.close();
        rs.close();
        return user;
      } catch (Exception ex) {
        ex.printStackTrace();
        return false;
      }  
    for (UUID id : (MinevoltGems.getInstance().getYMLFile()).players.keySet()) {
      if (getPlayerNamefromUUID(id).equals(name))
        return true; 
    } 
    return false;
  }
  
  public static boolean isRegistered(UUID pid) {
	    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql))
	      try {
	        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("SELECT * FROM " + (MinevoltGems.getConfigInstance()).table + " WHERE UUID= ?");
	        ps.setString(1, pid.toString());
	        ResultSet rs = ps.executeQuery();
	        boolean user = rs.next();
	        rs.close();
	        rs.close();
	        return user;
	      } catch (Exception ex) {
	        ex.printStackTrace();
	        return false;
	      }  
	    for (UUID id2 : (MinevoltGems.getInstance().getYMLFile()).players.keySet()) {
	      if (id2.toString().equals(pid.toString()))
	        return true; 
	    } 
	    return false;
	  }
  
  public static int getGems(Player p) {
    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql))
      try {
        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("SELECT * FROM " + (MinevoltGems.getConfigInstance()).table + " WHERE UUID= ?");
        ps.setString(1, p.getUniqueId().toString());
        ResultSet rs = ps.executeQuery();
        rs.next();
        int gems = rs.getInt("Gems");
        rs.close();
        ps.close();
        return gems;
      } catch (Exception ex) {
        ex.printStackTrace();
        return -1;
      }  
    for (UUID id : (MinevoltGems.getInstance().getYMLFile()).players.keySet()) {
      if (id.toString().equals(p.getUniqueId().toString()))
        return ((Integer)(MinevoltGems.getInstance().getYMLFile()).players.get(id)).intValue(); 
    } 
    return -1;
  }
  
  public static int getGems(UUID pid) {
	    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql))
	      try {
	        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("SELECT * FROM " + (MinevoltGems.getConfigInstance()).table + " WHERE UUID= ?");
	        ps.setString(1, pid.toString());
	        ResultSet rs = ps.executeQuery();
	        rs.next();
	        int gems = rs.getInt("Gems");
	        rs.close();
	        ps.close();
	        return gems;
	      } catch (Exception ex) {
	        ex.printStackTrace();
	        return -1;
	      }  
	    for (UUID id : (MinevoltGems.getInstance().getYMLFile()).players.keySet()) {
	      if (id.toString().equals(pid.toString()))
	        return ((Integer)(MinevoltGems.getInstance().getYMLFile()).players.get(id)).intValue(); 
	    } 
	    return -1;
	  }
  
  public static boolean setGems(Player p, int gems) {
    if (gems < 0)
      return false; 
    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql)) {
      try {
        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("UPDATE " + (MinevoltGems.getConfigInstance()).table + " SET Gems= ? WHERE UUID= ?");
        ps.setInt(1, gems);
        ps.setString(2, p.getUniqueId().toString());
        ps.executeUpdate();
        ps.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } else {
      (MinevoltGems.getInstance().getYMLFile()).players.put(p.getUniqueId(), Integer.valueOf(gems));
    } 
    return true;
  }
  
  public static boolean setGems(UUID pid, int gems) {
	    if (gems < 0)
	      return false; 
	    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql)) {
	      try {
	        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("UPDATE " + (MinevoltGems.getConfigInstance()).table + " SET Gems= ? WHERE UUID= ?");
	        ps.setInt(1, gems);
	        ps.setString(2, pid.toString());
	        ps.executeUpdate();
	        ps.close();
	      } catch (Exception ex) {
	        ex.printStackTrace();
	      } 
	    } else {
	      (MinevoltGems.getInstance().getYMLFile()).players.put(pid, Integer.valueOf(gems));
	    } 
	    return true;
	  }
  
  public static void addGems(Player p, int gems) {
    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql)) {
      try {
        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("UPDATE " + (MinevoltGems.getConfigInstance()).table + " SET Gems= ? WHERE UUID= ?");
        ps.setInt(1, getGems(p) + gems);
        ps.setString(2, p.getUniqueId().toString());
        ps.executeUpdate();
        ps.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } else {
      (MinevoltGems.getInstance().getYMLFile()).players.put(p.getUniqueId(), Integer.valueOf(getGems(p) + gems));
    } 
  }
  
  public static void addGems(UUID pid, int gems) {
	    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql)) {
	      try {
	        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("UPDATE " + (MinevoltGems.getConfigInstance()).table + " SET Gems= ? WHERE UUID= ?");
	        ps.setInt(1, getGems(pid) + gems);
	        ps.setString(2, pid.toString());
	        ps.executeUpdate();
	        ps.close();
	      } catch (Exception ex) {
	        ex.printStackTrace();
	      } 
	    } else {
	      (MinevoltGems.getInstance().getYMLFile()).players.put(pid, Integer.valueOf(getGems(pid) + gems));
	    } 
	  }
  
  public static boolean removeGems(Player p, int gems) {
    if (getGems(p) - gems < 0)
      return false; 
    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql)) {
      try {
        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("UPDATE " + (MinevoltGems.getConfigInstance()).table + " SET Gems= ? WHERE UUID= ?");
        ps.setInt(1, getGems(p) - gems);
        ps.setString(2, p.getUniqueId().toString());
        ps.executeUpdate();
        ps.close();
      } catch (Exception ex) {
        ex.printStackTrace();
        return false;
      } 
    } else {
      (MinevoltGems.getInstance().getYMLFile()).players.put(p.getUniqueId(), Integer.valueOf(getGems(p) - gems));
    } 
    return true;
  }
  
  public static boolean removeGems(UUID pid, int gems) {
	    if (getGems(pid) - gems < 0)
	      return false; 
	    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql)) {
	      try {
	        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("UPDATE " + (MinevoltGems.getConfigInstance()).table + " SET Gems= ? WHERE UUID= ?");
	        ps.setInt(1, getGems(pid) - gems);
	        ps.setString(2, pid.toString());
	        ps.executeUpdate();
	        ps.close();
	      } catch (Exception ex) {
	        ex.printStackTrace();
	        return false;
	      } 
	    } else {
	      (MinevoltGems.getInstance().getYMLFile()).players.put(pid, Integer.valueOf(getGems(pid) - gems));
	    } 
	    return true;
	  }
  
  public static int getGems(String name) {
    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql))
      try {
        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("SELECT * FROM " + (MinevoltGems.getConfigInstance()).table + " WHERE Playername= ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int gems = rs.getInt("Gems");
        rs.close();
        ps.close();
        return gems;
      } catch (Exception ex) {
        ex.printStackTrace();
        return -1;
      }  
    for (UUID id : (MinevoltGems.getInstance().getYMLFile()).players.keySet()) {
      if (getPlayerNamefromUUID(id).equals(name))
        return ((Integer)(MinevoltGems.getInstance().getYMLFile()).players.get(id)).intValue(); 
    } 
    return -1;
  }
  
  public static boolean setGems(String name, int gems) {
    if (gems < 0)
      return false; 
    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql)) {
      try {
        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("UPDATE " + (MinevoltGems.getConfigInstance()).table + " SET Gems= ? WHERE Playername= ?");
        ps.setInt(1, gems);
        ps.setString(2, name);
        ps.executeUpdate();
        ps.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } else {
      (MinevoltGems.getInstance().getYMLFile()).players.put(getUUIDfromPlayerName(name), Integer.valueOf(gems));
    } 
    return true;
  }
  
  public static void addGems(String name, int gems) {
    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql)) {
      try {
        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("UPDATE " + (MinevoltGems.getConfigInstance()).table + " SET Gems= ? WHERE Playername= ?");
        ps.setInt(1, getGems(name) + gems);
        ps.setString(2, name);
        ps.executeUpdate();
        ps.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } else {
      (MinevoltGems.getInstance().getYMLFile()).players.put(getUUIDfromPlayerName(name), Integer.valueOf(getGems(name) + gems));
    } 
  }
  
  public static boolean removeGems(String name, int gems) {
    if (getGems(name) - gems < 0)
      return false; 
    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql)) {
      try {
        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("UPDATE " + (MinevoltGems.getConfigInstance()).table + " SET Gems= ? WHERE Playername= ?");
        ps.setInt(1, getGems(name) - gems);
        ps.setString(2, name);
        ps.executeUpdate();
        ps.close();
      } catch (Exception ex) {
        ex.printStackTrace();
        return false;
      } 
    } else {
      (MinevoltGems.getInstance().getYMLFile()).players.put(getUUIDfromPlayerName(name), Integer.valueOf(getGems(name) - gems));
    } 
    return true;
  }
  
  public static UUID getUUIDfromPlayerName(String playerName) {
    return UUIDFetcher.getUUID(playerName);
  }
  
  public static String getPlayerNamefromUUID(UUID uuid) {
    return UUIDFetcher.getName(uuid);
  }
}
