package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import modelsinterface.Waypoint;
import modelsnav.Airport;
import modelsnav.Fix;
import modelsnav.Navaid;
import modelsnav.Runway;

/*
  NavigationDatabase.java
   Parses FSX-PMDG nav text files and create objects to represent each navigatable
   fixes as an object.
                                                                              */
public class NavigationDatabase {
  //MUTABLE
  final String PATH = "C:/Users/Dennis Chan/Desktop/boeing-fmc-display/data/";
  final int NAV_LINE_LENGTH = 61;
  final int APT_LINE_LENGTH = 74;
  final int FIX_LINE_LENGTH = 50;

  private HashMap<String, Airport> airports;
  private HashMap<String, ArrayList<Waypoint>> waypoints;

  public NavigationDatabase() throws FileNotFoundException {
    setAirports(new HashMap<>());
    setNavaids(new HashMap<>());
    loadAirports(new File(PATH+"wpNavAPT.txt"));
    loadNavaids(new File(PATH+"wpNavAID.txt"));
    loadFixes(new File(PATH+"wpNavFIX.txt"));
  }

  /**
   * Adds waypoint into waypoints object
   * @param waypoint, a valid waypoint
   */
  private void addWaypoint(Waypoint waypoint){
    String identifier = waypoint.getId();
    if (!getWaypoints().containsKey(identifier))
      getWaypoints().put(identifier, new ArrayList<Waypoint>());
    
    ArrayList<Waypoint> waypoints = getWaypoints().get(identifier);
    waypoints.add(waypoint);
    getWaypoints().put(identifier, waypoints);
  }
  
  
  /*
      Navaids-Parsing Objects
                                */
  /**
   * Driver that iterates the entire wpNavAID file, calls addLineToNavaids with
   * individual lines within the text file
   * @param f, file containing all navaids
   * @throws FileNotFoundException
   * @see addToNavaids
   */
  private void loadNavaids(File f) throws FileNotFoundException {
    Scanner scanf = new Scanner(f);
    while (scanf.hasNext())
      createNavaid(scanf.nextLine());
    scanf.close();
  }

  private void createNavaid(String s) {
    if (s.startsWith(";")) return;
    checkEntryLength(s, NAV_LINE_LENGTH);
    addWaypoint(new Navaid(s));
  }

  
  /*
      Fix-Parsing Methods
   */
  private void loadFixes(File f) throws FileNotFoundException{
    Scanner scanf = new Scanner(f);
    while (scanf.hasNextLine()) {
      createFix(scanf.nextLine());
    }
  }
  
  private void createFix(String s) {
    if (s.startsWith(";")) return;
    checkEntryLength(s, FIX_LINE_LENGTH);
    addWaypoint(new Fix(s));
  }

  
  /*
      Airport-Parsing Methods
                                */
  /**
   * Driver that iterates the entire wpNavAPT file, calls addLineToAriports with
   * individual lines within the text file
   * @param f     file containing all airports
   * @throws FileNotFoundException
   * @see addLineToAirport
   */
  private void loadAirports(File f) throws FileNotFoundException {
    Scanner scanf = new Scanner(f);
    while (scanf.hasNext())
      addAirport(scanf.nextLine());
  }

  /**
   * Parses a line of the airport text file containing a single runway information
   * and extract the runway information, creates mapping airport object the key
   * (airport icao) does not already exist in hashtable; otherwise adds the runway
   * to the hashtable.
   * @param s   text from airports database, contains one runway info for airport
   * @throws FileNotFoundException
   */
  private void addAirport(String s) throws FileNotFoundException {
    if (s.startsWith(";") || s.length() != APT_LINE_LENGTH)  return;
    checkEntryLength(s, APT_LINE_LENGTH);

    String icao = s.substring(24, 28); // 4 Characters-long
    if (!getAirports().containsKey(icao))
      getAirports().put(icao, new Airport(icao, s.substring(0, 24)));

    Airport airport = getAirports().get(icao);
    airport.getRunways().add(new Runway(s));
    getAirports().put(icao, airport);
  }

    
  /*
      Setter & Getter Methods
   */
  public HashMap<String, Airport> getAirports() {
    return airports;
  }

  public void setAirports(HashMap<String, Airport> airports) {
    this.airports = airports;
  }

  public HashMap<String, ArrayList<Waypoint>> getWaypoints() {
    return waypoints;
  }

  public void setNavaids(HashMap<String, ArrayList<Waypoint>> navaids) {
    this.waypoints = navaids;
  }
  
  private void checkEntryLength(String line, int length) {
    if (!(line.length() == length))
      System.out.println("$$INVALID FIX LINE LENGTH" + line);
  }
  
}
