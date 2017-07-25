package global;

import java.util.HashMap;

class Aircraft {
  String aircraft;
  HashMap<String, String> attributes;

  public Aircraft (String aircraft){
    this.aircraft = aircraft;
    attributes = new HashMap<>();
    loadAttributes();
    System.out.println ("Aircraft contains " + attributes.size() + " items.");
  }

  public void loadAttributes() {
    attributes.put("AC-MODEL", "777-200.2");
    attributes.put("AC-ENGRAT", "GE90-110B1L2");
  }
}