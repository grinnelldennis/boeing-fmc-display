import java.util.HashMap;

class Navigation {
  
  HashMap<String, String> attributes;

  public Navigation() {
    attributes = new HashMap<>();
    loadAttributes();
  }

  public void loadAttributes() {
    attributes.put("NV-DCYCLE", "AIRAC-1601");
    attributes.put("NV-ACTIVE", "JAN07FEB03/16");
    System.out.println ("Navigation HashMap contains " + attributes.size() + " items.");
  }
}