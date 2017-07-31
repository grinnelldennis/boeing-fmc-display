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

  public void setIcao(String icao) {
    this.icao = icao;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRunways(ArrayList<Runway> runways) {
    this.runways = runways;
  }

  public void setFixes(HashMap<String, Coordinate> fixes) {
    this.fixes = fixes;
  }

  public void setGates(HashMap<String, Coordinate> gates) {
    this.gates = gates;
  }

  public void setSids(HashMap<String, Procedure> sids) {
    this.sids = sids;
  }

  public void setStars(HashMap<String, Procedure> stars) {
    this.stars = stars;
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

  public String getName() {
    return name;
  }

  public HashMap<String, Coordinate> getFixes() {
    return fixes;
  }

  public HashMap<String, Coordinate> getGates() {
    return gates;
  }

  public HashMap<String, Procedure> getSids() {
    return sids;
  }

  public HashMap<String, Procedure> getStars() {
    return stars;
  }
  
}
