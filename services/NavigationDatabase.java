package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import modelsnav.Airport;
import modelsnav.Coordinate;
import modelsnav.FlightPlanWaypoint;
import modelsnav.Procedure;
import modelsnav.Runway;
import modelsinterface.Waypoint;
//Waypoint Implementations
import modelsnav.Fix;
import modelsnav.Navaid;
import modelsnav.Heading;
import modelsnav.Track;
import modelsnav.Intercept;

/*
  NavigationDatabase.java
   Parses FSX-PMDG nav text files and create objects to represent each navigatable
   fixes as an object.
 */
public class NavigationDatabase {
  final String PATH = "C:/Users/Dennis Chan/Desktop/boeing-fmc-display/data/";
  final int NAV_LINE_LENGTH = 61;
  final int APT_LINE_LENGTH = 74;
  final int FIX_LINE_LENGTH = 50;
  final boolean DEBUG = true;

  private HashMap<String, Airport> airports;
  private HashMap<String, ArrayList<Waypoint>> waypoints;

  public NavigationDatabase() throws FileNotFoundException {
    setAirports(new HashMap<>());
    setNavaids(new HashMap<>());
    loadNavaids(new File(PATH+"wpNavAID.txt"));
    if (DEBUG) System.out.println("$$Loaded Navaids");
    loadFixes(new File(PATH+"wpNavFIX.txt"));
    if (DEBUG) System.out.println("$$Loaded Fixes");
    loadAirports(new File(PATH+"wpNavAPT.txt"));
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
   * (Airport ICAO) does not already exist in hashtable; otherwise adds the runway
   * to the hashtable.
   * @param s   text from airports database, contains one runway info for airport
   * @throws FileNotFoundException
   */
  private void addAirport(String s) throws FileNotFoundException {
    if (s.startsWith(";") || s.length() != APT_LINE_LENGTH)  return;
    checkEntryLength(s, APT_LINE_LENGTH);

    String icao = s.substring(24, 28); // 4 Characters-long
    if (!getAirports().containsKey(icao)) {
      getAirports().put(icao, new Airport(icao, s.substring(0, 24)));
      File airportFile = new File(PATH+""+icao+".txt");
      System.out.println(icao);
      if (airportFile.exists())
        loadAirport(airportFile, getAirports().get(icao));
    }
    Airport airport = getAirports().get(icao);
    airport.getRunways().add(new Runway(s));
    getAirports().put(icao, airport);
  }

  private void loadAirport(File f, Airport ap) throws FileNotFoundException {
    System.out.println("\n$$-" + ap.getIcao());
    Scanner scanf = new Scanner(f);
    String s = scanf.nextLine();
    //Get through all comments
    while (s.startsWith("//"))
      s = scanf.nextLine();
    System.out.println("$$-Skipped comments");
    while (scanf.hasNextLine()) {
      switch (s) {
      case "FIXES":
        parseFixes(scanf, ap);
        System.out.println("$$#Airport Fixes. "+ap.getFixes().size());
        break;
      case "SIDS":
        parseProcedures(scanf, ap, "SID");
        break;
      case "STARS":
        parseProcedures(scanf, ap, "STAR");
        break;
      case "APPROACHES":
        //" TRANSITION"
        System.out.println("APPROACH");
        break;
      case "GATES":
        parseGates(scanf, ap);
        System.out.println("$$-#Airport Gates. "+ap.getGates().size());
        break;
      default:
        //TODO: do soemthing
        break;
      } 
      if (scanf.hasNextLine())
        s = scanf.nextLine();
    }
    System.out.println("$$parsed ");    
  }

  /**
   * Consumes lines within a typed block, i.e. block starting from line 
   *  immediately after "SIDS" to the line "ENDSIDS," inclusive.
   * @param scanf, an object scanning a single airport file 
   * @param airport, airport object for passing for creating procedure
   * @param type, either SID or STAR
   */
  private void parseProcedures(Scanner scanf, Airport airport, String type) {
    String s = scanf.nextLine();
    Procedure procedure = null;
    String procedureId = null;
    while (!s.equals("END"+type)) {
      if (s.startsWith(type)) {
        String[] ss = s.split(" ");
        procedureId = ss[1];
        procedure = new Procedure(procedureId, type);
        procedure.setBaseProcedure(createProceduralRoute(ss, airport, 2));
        s = scanf.nextLine();
        while (!s.startsWith(type)) {
          ss = s.split(" ");
          if (s.startsWith(" RNW")) 
            procedure.addRunwayProcedure(ss[2], createProceduralRoute(ss, airport, 3));
          else if (s.startsWith("  RNW"))
            procedure.addRunwayProcedure(ss[3], createProceduralRoute(ss, airport, 4));
          else if (s.startsWith(" TRANSITION")) 
              procedure.addTransition(ss[2], createProceduralRoute(ss, airport, 3));
          else if (s.startsWith("  TRANSITION")) 
            procedure.addTransition(ss[3], createProceduralRoute(ss, airport, 4));
          s = scanf.nextLine();
        }
      }
      if (type.equals("SID"))
        airport.addSid(procedureId, procedure);
      else if (type.equals("STAR"))
        airport.addStar(procedureId, procedure);
    }
  }


  private void parseFixes(Scanner scanf, Airport airport) {
    String s = scanf.nextLine();
    while (!s.equals("ENDFIXES")) {
      if (s.startsWith("FIX")) {
        String[] ss = s.split(" ");
        System.out.println("$$  FIXID. "+ss[1]);
        if (ss.length==10)
          for (int i = 4; i < 10; i++)
            ss[i-1] = ss[i];
        airport.addFix(ss[1], 
            new Coordinate(ss[3], ss[4], ss[5], ss[6], ss[7], ss[8]));
      } else
        logError("Invalid Fix Opening", s, "parseFixes");
      s = scanf.nextLine();
    }
  }

  private void parseGates(Scanner scanf, Airport airport) {
    String s = scanf.nextLine();
    while (!s.equals("ENDGATES")) {
      if (s.startsWith("GATE")) {
        String[] ss = s.split(" ");
        airport.addGate(ss[1], 
            new Coordinate(ss[2], ss[3], ss[4], ss[5], ss[6], ss[7]));
      } else
        logError("Invalid Gate Opening", s, "parseGate");
      s = scanf.nextLine();
    }
  }


  /**
   * Takes a single line of a procedure, e.g. SID or STAR, and put new 
   *  FlightPlanWaypoints into the given procedure 
   * @param ss, a String array starting with FIRST procedural fix
   * @param ap, an airport object to get fixes from
   * @param st, a starting index for parsing ss
   * @return an ArrayList of waypoints
   */
  private ArrayList<FlightPlanWaypoint> createProceduralRoute(String[] ss, Airport ap, int st) {
    ArrayList<FlightPlanWaypoint> list = new ArrayList<>();
    FlightPlanWaypoint fpwp;
    for (int index = st; index < ss.length; index++) {
      switch (ss[index]) {
      case "FIX": //FIX {OVERFLY} (FIX) [RESTRICTIONS]
        fpwp = new FlightPlanWaypoint(ap.getFix(ss[index+1]));
        index = populateFlightPlanWaypoint(fpwp, ss, index);
        break;
      case "HDG": //HDG (DEG) [RESTRICTIONS]
        fpwp = new FlightPlanWaypoint(new Heading(ss[index+1]));
        index = populateFlightPlanWaypoint(fpwp, ss, index);
        break;
      case "TRK": //TRK (DEG) [RESTRICTIONS]
        fpwp = new FlightPlanWaypoint(new Track(ss[index+1]));
        index = populateFlightPlanWaypoint(fpwp, ss, index);
        break;
      case "TURN":
        //TURN (DIR) DIRECT
        break;
      case "LEFT": //LEFT TURN INBOUNDCOURSE (DEG) LEGTIME (#)
        break;
      case "RIGHT": //RIGHT TURN INBOUNDCOURSE (DEG) LEGTIME (#)
        break;
      case "INTERCEPT": //RADIAL (DEG) TO
        fpwp = new FlightPlanWaypoint(new Intercept(ss[index+2]));
        index = populateFlightPlanWaypoint(fpwp, ss, index);
        break; 
      }
    }
    return null;
  }

  /**
   * 
   * @param fpwp
   * @param ss
   * @param start, index of a present keyword 
   * @return index of first un-consumed string array element
   */
  private int populateFlightPlanWaypoint(FlightPlanWaypoint fpwp, String[] ss, int start){
    int index = start;
    while (isAKeyword(ss[++index])) {
      //iterates for potentially (numerous) restrictions
      switch(ss[index++]) {
      case "AT": //At or Above/Below
        if (ss[index+=2].equals("BELOW"))
          fpwp.setBelowAltitude(Integer.parseInt(ss[++index]));
        else if (ss[index].equals("ABOVE"))
          fpwp.setAboveAltitude(Integer.parseInt(ss[++index]));
        break;
      case "UNTIL":
        //TODO: Until
        break;
      case "SPEED":
        fpwp.setRestrictSpeed(Integer.parseInt(ss[index]));
        break;
      case "JUST A NUMBER":
        //TODO: Just a number
        break;
      default:
        logError("UNCAUGHT PROCEDURAL WAYPOINT PARSING", ss[index-1], "populateFlightPlanWaypoint");
        index++;
        break;
      }
    }
    return index - 1;
  }

  String[] keywords = {"FIX", "HDG", "TRK", "TURN", "INTERCEPT"};
  private boolean isAKeyword(String target) {
    for (String key: keywords) {
      if (target.equals(key))
        return true;
    }
    return false;
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

  /*
      Error & Logging Methods
   */
  private void checkEntryLength(String line, int length) {
    //TODO: Add file logging options
    if (!(line.length() == length))
      logError("INVALID LINE LENGTH", line, "loadWaypoint");
  }
  private void logError(String error, String offendingLine, String offendingLocation) {
    System.out.println("$$"+error+" at "+offendingLocation+ "\n$$  "+offendingLine);
  }

}
