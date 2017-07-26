package global;

import java.util.HashMap;

public class World {
  
  public HashMap<String, String> attributes;
  
  public World() {
    attributes = new HashMap<>();
    loadAttributes();
    System.out.println ("World contains " + attributes.size() + " items.");
  }
  
  private void loadAttributes() {
    attributes.put("WR-TIMEZ", "2359z");
  }
}