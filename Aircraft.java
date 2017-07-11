import java.util.HashMap;

class Aircraft {
  String aircraft;
  HashMap<String, String> attributes;

  public Aircraft (String aircraft){
    this.aircraft = aircraft;
    attributes = new HashMap<>();
    loadAttributes();
  }

  public void loadAttributes() {
    attributes.put("AC-MODEL", "777-200.2");
    attributes.put("AC-ENGRAT", "GE90-110B1L");
    System.out.println ("Aircraft HashMap contains " + attributes.size() + " items.");
  }
}