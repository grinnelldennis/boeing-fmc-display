import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*
  NavigationDatabase.java
   Parses FSX-PMDG nav text files and create objects to represent each navigatable
   fixes as an object.
                                                                              */
class NavigationDatabase {
  //MUTABLE
  final String PATH = "C:/Users/Dennis Chan/Desktop/boeing-fmc-display/data/";
  final int NAV_LINE_LENGTH = 61;
  final int APT_LINE_LENGTH = 74;

  HashMap<String, Airport> airports;
  HashMap<String, ArrayList<Navaid>> navaids;

  public NavigationDatabase() throws FileNotFoundException {
    airports = new HashMap<>();
    navaids = new HashMap<>();
    //loadAirports(new File(PATH+"wpNavAPT.txt"));
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
  }

  private void addToNavaids(String s) {
    if (s.startsWith(";")) return;
    //if (s.length() != NAV_LINE_LENGTH)
    //  throw new IllegalStateException("Invalid Navaid Entry");
    System.out.println (s);
    // Decompose each line in Navaid file
    String brief = s.substring(0, 24); // 24 characters-long
    String ident = s.substring(24, 29); // 5 characters-long
    String type = s.substring(29, 33); // 4 characters-long
    double lat = Double.parseDouble(s.substring(33, 43)); // 10 characters-long
    double lon =  Double.parseDouble(s.substring(43, 54)); // 11 characters-long
    double freq = Double.parseDouble(s.substring(54, 60)); // 6 characters-long
    char desig = s.charAt(60); // 1 characters-long

    Navaid nav = new Navaid(new Fix(ident, lat, lon), brief, type, freq, desig);

    if (!navaids.containsKey(ident)) {
      ArrayList<Navaid> temp = new ArrayList<>();
      navaids.put(ident, temp);
    }

    ArrayList<Navaid> ls = navaids.get(ident);
    ls.add(nav);
    navaids.put(ident, ls);
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

    System.out.println ("AIRPORT. "+s);
    String icao = s.substring(24, 28); // 4 charactesr-long
    System.out.println(icao);
    if (!airports.containsKey(icao))
      airports.put(icao, new Airport(icao, s.substring(0, 24)));

    Runway r = createRunway(s);
    Airport a = airports.get(icao);
    a.runways.add(r);
    airports.put(icao, a);
  }

  private Runway createRunway(String s) {
    String runwayId = s.substring(28, 31);
    System.out.println(runwayId);
    int length = Integer.parseInt(s.substring(33, 39));
    double latitude = Double.parseDouble(s.substring(39, 49));
    double longitude = Double.parseDouble(s.substring(49, 60));
    double ilsRadio = Double.parseDouble(s.substring(60, 66));
    int heading = Integer.parseInt(s.substring(66, 69));
    //duplicated mag heading
    int elevation = Integer.parseInt(s.substring(70, 74));
    return new Runway(runwayId, length, new Coordinate(latitude, longitude), ilsRadio, elevation, heading);
  }
}
