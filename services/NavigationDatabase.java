package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import modelsnav.Airport;
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

  private HashMap<String, Airport> airports;
  private HashMap<String, ArrayList<Navaid>> navaids;

  public NavigationDatabase() throws FileNotFoundException {
    setAirports(new HashMap<>());
    setNavaids(new HashMap<>());
    loadAirports(new File(PATH+"wpNavAPT.txt"));
    loadNavaids(new File(PATH+"wpNavAID.txt"));
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
      addToNavaids(scanf.nextLine());
    scanf.close();
  }

  private void addToNavaids(String s) {
    if (s.startsWith(";")) return;
    if (s.length() != NAV_LINE_LENGTH)
      System.out.println ("$$INVALID LINE LENGTH. "+s);
    
    Navaid nav = new Navaid(s);
    String ident = nav.getId();

    if (!getNavaids().containsKey(ident))
      getNavaids().put(ident, new ArrayList<Navaid>());

    ArrayList<Navaid> ls = getNavaids().get(ident);
    ls.add(nav);
    getNavaids().put(ident, ls);
  }


  /*
      Airport-Parsing Methods
                                */
  /**
   * Driver that iterates the entire wpNavAPT file, calls addLineToAriports with
   * individual lines within the text file
   * @param f     file containing all airports
   * @throws FileNotFoundException
   * @see addLineTiAirport
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
    if (s.length() != APT_LINE_LENGTH) // Add Logging Option
      throw new IllegalStateException("Invalid Airport Entry: " + s);

    String icao = s.substring(24, 28); // 4 Characters-long
    if (!getAirports().containsKey(icao))
      getAirports().put(icao, new Airport(icao, s.substring(0, 24)));

    Airport airport = getAirports().get(icao);
    airport.getRunways().add(new Runway(s));
    getAirports().put(icao, airport);
  }

  public HashMap<String, Airport> getAirports() {
    return airports;
  }

  public void setAirports(HashMap<String, Airport> airports) {
    this.airports = airports;
  }


  public HashMap<String, ArrayList<Navaid>> getNavaids() {
    return navaids;
  }

  public void setNavaids(HashMap<String, ArrayList<Navaid>> navaids) {
    this.navaids = navaids;
  }
  
  
}
