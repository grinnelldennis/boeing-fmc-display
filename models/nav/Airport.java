package modelsnav;

import java.util.ArrayList;
import java.util.HashMap;

import modelsinterface.Waypoint;

public class Airport {
  private String icao;
  String name;
  private ArrayList<Runway> runways;
  HashMap<String, Coordinate> fixes;
  HashMap<String, Coordinate> gates;
  HashMap<String, Procedure> sids;
  HashMap<String, Procedure> stars;

  public Airport(String icao, String name) {
    this.name = name;
    this.icao = icao;
    runways = new ArrayList<>();
    fixes = new HashMap<>();
    gates = new HashMap<>();
    sids = new HashMap<>();
    stars = new HashMap<>();
  }

  /*
         Adder-Methods
                          */
  public void addFix(String fixId, Coordinate coord) {
    if (coord==null) throw new IllegalArgumentException("Fix is null.");
    fixes.put(fixId, coord);
  }
  public void addGate(String gateId, Coordinate coord) {
    if (coord==null) throw new IllegalArgumentException("Coordinate is null.");
    gates.put(gateId, coord);
  }
  
  public void add(Runway runway) {
    if (runway==null) throw new IllegalArgumentException("Runway is null.");
    getRunways().add(runway);
  }
  public void addSid(String id, Procedure sid) {
    sids.put(id, sid);
  }
  public void addStar(String id, Procedure star) {
    stars.put(id, star);
  }

  public ArrayList<Runway> getRunways() {
    return runways;
  }

  public String getIcao() {
    return icao;
  }


  public Waypoint getFix(String fixId) {
    return new Fix(fixId, fixes.get(fixId));
  }
  
}
