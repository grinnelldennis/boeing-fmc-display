package test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import modelsinterface.Waypoint;
import modelsnav.Navaid;
import services.NavigationDatabase;
import services.FlightPlan;

public class FlightPlanTest{
  
  NavigationDatabase nd;
  FlightPlan fp;
  Scanner scan;
  
  public FlightPlanTest() throws FileNotFoundException{
    System.out.println("$$::FPT Init");
    nd = new NavigationDatabase();
    fp = new FlightPlan();
    scan = new Scanner(System.in);
    postConstructionDebug();
    addWaypoint(nd, fp);
  } 
  
  private void postConstructionDebug() {
    System.out.println ("#Airports. "+nd.getAirports().size());
    System.out.println ("#Navaids. "+nd.getWaypoints().size());
  }
    
  private String getInput(String output) {
    System.out.print (output);
    return scan.nextLine();
  }
  
  /**
   * Driver for the waypoint adding functionality. It has the ability to disambiguate user input 
   *  in event of multiple waypoints of the same name through simulated button pressing. 
   * @param nd
   * @param fp
   */
  private void addWaypoint(NavigationDatabase nd, FlightPlan fp){
    fp.setOrigin(nd.getAirports().get("VHHH"));
    fp.setDestination(nd.getAirports().get("EGAR"));
    
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
    System.out.println(fp.printRoute());
  }
  
  private <T> int disambiguateInput(ArrayList<T> list){
    printAllNavaidOptions(list);
    return convertButtonPressToIndex(getInput("#Choose Option."));
  }
  
  private <T> void printAllNavaidOptions(ArrayList<T> list) {
    int index = 0;
    for (T item : list)
      System.out.println((index+=2) + " " + item.toString());
  }
  
  private int convertButtonPressToIndex(String i) {
    if (i.length()<=3 && i.endsWith("L"))
      return (Integer.parseInt(i.substring(0, i.indexOf("L"))) / 2) - 1;
    return -1;
  }

  
}