package me.drkmatr1984.MinevoltGems;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import org.bukkit.entity.Player;

import me.drkmatr1984.MinevoltGems.storage.MySQL;

public class GemsAPI {
  public static void createTable(MySQL sql) {
    try {
      PreparedStatement ps = sql.getStatement("CREATE TABLE IF NOT EXISTS " + MinevoltGems.getConfigInstance().table + " (UUID VARCHAR(100), Gems INT(100))");
      ps.executeUpdate();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public static boolean register(Player p, int startAmount) {
      return GemsAPI.register(p.getUniqueId(), startAmount); 
  }
  
  public static boolean register(String playerName, int startAmount) {
	  if(getUUIDfromPlayerName(playerName)!=null) {
		  UUID pid = getUUIDfromPlayerName(playerName);
		  return GemsAPI.register(pid, startAmount);
	  }
	  return false;     
  }
  
  public static boolean register(UUID pid, int startAmount) {
      if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql)) {
	      try {
	          PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("INSERT INTO " + (MinevoltGems.getConfigInstance()).table + " (UUID, Gems) VALUES (?, ?)");
	          ps.setString(1, pid.toString());
	          ps.setInt(2, startAmount);
	          ps.executeUpdate();
	          ps.close();
	          return true;
	      } catch (Exception ex) {
	          return false;
	      } 
	    } else {
	        (MinevoltGems.getInstance().getYMLFile()).players.put(pid, Integer.valueOf(startAmount));
	        return true;
	    } 
	  }
  
  public static boolean isRegistered(Player p) {
    return GemsAPI.isRegistered(p.getUniqueId());
  }
  
  public static boolean isRegistered(String playerName) {
	  if(getUUIDfromPlayerName(playerName)!=null) {
		  UUID pid = getUUIDfromPlayerName(playerName);
		  return GemsAPI.isRegistered(pid);
	  }
      return false;
  }
  
  public static boolean isRegistered(UUID pid) {
	    if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql)) {
	    	try {
		        PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("SELECT * FROM " + (MinevoltGems.getConfigInstance()).table + " WHERE UUID= ?");
		        ps.setString(1, pid.toString());
		        ResultSet rs = ps.executeQuery();
		        boolean user = rs.next();
		        rs.close();
		        rs.close();
		        return user;
		      } catch (Exception ex) {
		        return false;
		      }
	    }else {
	    	for (UUID id : (MinevoltGems.getInstance().getYMLFile()).players.keySet()) {
		        if (id.equals(pid))
		          return true; 
		    }
	    }	     
	    return false;
	  }
  
  public static int getGems(Player p) {
      return GemsAPI.getGems(p.getUniqueId());
  }
  
  public static int getGems(String playerName) {
	  if(getUUIDfromPlayerName(playerName)!=null) {
		  UUID pid = getUUIDfromPlayerName(playerName);
	      return GemsAPI.getGems(pid); 
	  }
	  return -1;
  }
  
  public static int getGems(UUID pid) {
	  if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql)) {
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
		      return -1;
		  }
	  }else {
           for (UUID id : (MinevoltGems.getInstance().getYMLFile()).players.keySet()) {
	  	       if (id.equals(pid))
	  	           return ((Integer)(MinevoltGems.getInstance().getYMLFile()).players.get(id)).intValue(); 
	  	   }
	  }	     
	  return -1;
  }
  
  public static boolean setGems(Player p, int gems) {
      return GemsAPI.setGems(p.getUniqueId(), gems);
  }
  
  public static boolean setGems(String playerName, int gems) {
	    if (gems < 0)
	      return false;
	    if(getUUIDfromPlayerName(playerName)!=null) {
			UUID pid = getUUIDfromPlayerName(playerName);
			return GemsAPI.setGems(pid, gems);
	    }	    
	    return false;
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
	        return false;
	      } 
	    } else {
	      (MinevoltGems.getInstance().getYMLFile()).players.put(pid, Integer.valueOf(gems));
	    } 
	    return true;
	  }
  
  public static boolean addGems(Player p, int gems) {
      return GemsAPI.addGems(p.getUniqueId(), gems);
  }
  
	public static boolean addGems(String playerName, int gems) {
		if (getUUIDfromPlayerName(playerName) != null) {
			UUID pid = getUUIDfromPlayerName(playerName);
			return GemsAPI.addGems(pid, gems);
		}
		return false;
	}
  
  public static boolean addGems(UUID pid, int gems) {
	  if ((MinevoltGems.getConfigInstance()).method.equals(GemsConfig.StorageMethod.mysql)) {
	      try {
	          PreparedStatement ps = MinevoltGems.getInstance().getMySQL().getStatement("UPDATE " + (MinevoltGems.getConfigInstance()).table + " SET Gems= ? WHERE UUID= ?");
	          ps.setInt(1, getGems(pid) + gems);
	          ps.setString(2, pid.toString());
	          ps.executeUpdate();
	          ps.close();
	      } catch (Exception ex) {
	          return false;
	      } 
	  } else {
	      (MinevoltGems.getInstance().getYMLFile()).players.put(pid, Integer.valueOf(getGems(pid) + gems));
	  }
      return true;
  }
  
  public static boolean removeGems(Player p, int gems) {
      return GemsAPI.removeGems(p.getUniqueId(), gems);
  }
  
  public static boolean removeGems(String playerName, int gems) {
      if (getGems(playerName) - gems < 0)
	      return false; 
	  if(getUUIDfromPlayerName(playerName)!=null) {
	      UUID pid = getUUIDfromPlayerName(playerName);
	      return GemsAPI.removeGems(pid, gems);
      }
	  return false;
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
	          return false;
	    } 
	  } else {
	      (MinevoltGems.getInstance().getYMLFile()).players.put(pid, Integer.valueOf(getGems(pid) - gems));
	  } 
	  return true;
  }
  
  public static int getStartingAmountFromConfig() {
	  return MinevoltGems.getConfigInstance().startAmount;
  }
  
  public static UUID getUUIDfromPlayerName(String playerName) {
    return UUIDFetcher.getUUID(playerName);
  }
  
  public static String getPlayerNamefromUUID(UUID uuid) {
    return UUIDFetcher.getName(uuid);
  }
}
