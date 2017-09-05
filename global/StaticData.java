package global;

import java.util.HashMap;

public class StaticData {
  
  private HashMap<String, String> staticAircraft;
  private HashMap<String, String> staticNavigation;
  private HashMap<String, String> world;
  
  public StaticData() {
    this("undefined", "undefined", "undefined");
  }
  
  public StaticData(String acTitle, String nvTitle, String wrTitle) {
    staticAircraft = new HashMap<>();
    staticNavigation = new HashMap<>();
    world = new HashMap<>();
    putDefaults(acTitle, nvTitle, wrTitle);
  }
  
  public void putDefaults(String acTitle, String nvTitle, String wrTitle) {
    put("aircraft", "title", acTitle);
    put("navigation", "title", nvTitle);
    put("world", "title", wrTitle);
    put("AC", "MODEL", "777-200.2");
    put("AC", "ENGRAT", "GE90-110B1L2");
    put("NV","DCYCLE", "AIRAC-1601");
    put("NV","ACTIVE", "JAN07FEB03/16");
    put("NV","POSNSG", "N48D36.2"); //GPS positiion, latitude
    put("NV","POSEWG", "W123D01.1"); //GPS position, longitude
    put("NV","POSNSL", "N48D36.1"); //last positiion, latitude
    put("NV","POSEWL", "W123D01.1"); //last position, longitude
    put("WR", "TIMEZ", "2359z");  
  }
  
  public boolean put(String type, String key, String value) {
    if (type.equalsIgnoreCase("AC") || type.equalsIgnoreCase("Aircraft")) {
      staticAircraft.put(key, value);
      return true;
    } else if (type.equalsIgnoreCase("NV") || type.equalsIgnoreCase("Navigation")) {
      staticNavigation.put(key, value);
      return true;
    } else if (type.equalsIgnoreCase("WR") || type.equalsIgnoreCase("World")) {
      world.put(key, value);
      return true;
    }
    return false;
  }
  
  public String get(String type, String key) {
    if (type.equalsIgnoreCase("AC") || type.equalsIgnoreCase("Aircraft")) {
      return staticAircraft.get(key);
    } else if (type.equalsIgnoreCase("NV") || type.equalsIgnoreCase("Navigation")) {
      return staticNavigation.get(key);
    } else if (type.equalsIgnoreCase("WR") || type.equalsIgnoreCase("World")) {
      return world.get(key);
    }
    return "UNDEFINED";
  }
  

  
}
