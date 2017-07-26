package global;

import java.util.HashMap;

public class Navigation {
  
  public HashMap<String, String> attributes;

  public Navigation() {
    attributes = new HashMap<>();
    loadAttributes();
    System.out.println ("Navigation contains " + attributes.size() + " items.");
  }

  public void loadAttributes() {
    attributes.put("NV-DCYCLE", "AIRAC-1601");
    attributes.put("NV-ACTIVE", "JAN07FEB03/16");
    attributes.put("NV-POSNSG", "N48D36.2"); //GPS positiion, latitude
    attributes.put("NV-POSEWG", "W123D01.1"); //GPS position, longitude
    attributes.put("NV-POSNSL", "N48D36.1"); //last positiion, latitude
    attributes.put("NV-POSEWL", "W123D01.1"); //last position, longitude
  }
}