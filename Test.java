import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import modelsinterface.Waypoint;
import services.NavigationDatabase;
import modelsnav.Navaid;
import services.FlightPlan;

class Test{
  
  public static void main(String[] args) throws FileNotFoundException{
    NavigationDatabase nd = new NavigationDatabase();
    FlightPlan fp = new FlightPlan();
    System.out.println ("#Airports. "+nd.getAirports().size());
    System.out.println ("#Navaids. "+nd.getWaypoints().size());
    
    addWaypointTest(nd, fp);
  } 
    
  private static String getInput(String output) {
    Scanner scan = new Scanner(System.in);
    System.out.print (output);
    return scan.nextLine();
  }
  
  /**
   * Driver for the waypoint adding functionality. It has the ability to disambiguate user input 
   *  in event of multiple waypoints of the same name through simulated button pressing. 
   * @param nd
   * @param fp
   */
  private static void addWaypointTest(NavigationDatabase nd, FlightPlan fp){
    fp.setOrigin(nd.getAirports().get("VHHH"));
    fp.setDestination(nd.getAirports().get("EGAR"));
    System.out.println(fp.printRoute());
    
    String input = getInput("$$Navaid. ");
    while (!input.equals("quit")) {
      if (nd.getWaypoints().containsKey(input))
        if (nd.getWaypoints().get(input).size() > 1)
          fp.addWaypoint(nd.getWaypoints().get(input).get(disambiguateInput(nd.getWaypoints().get(input))));
        else
          fp.addWaypoint(nd.getWaypoints().get(input).get(0));
      else
        System.out.println("Navaid Not Found.");
      input = getInput("$$Navaid. ");
    }
  }
  
  private static int disambiguateInput(ArrayList<Waypoint> list){
    printAllNavaidOptions(list);
    return convertButtonPressToIndex(getInput("Choose Option."));
  }
  
  private static void printAllNavaidOptions(ArrayList<Waypoint> list) {
    for (Waypoint nav : list)
      System.out.println(nav.getInfo());
  }
  
  private static int convertButtonPressToIndex(String i) {
    if (i.length()<=3 && i.endsWith("L"))
      return (Integer.parseInt(i.substring(0, i.indexOf("L"))) / 2) - 1;
    return -1;
  }

  
}