import java.io.FileNotFoundException;
import java.util.Scanner;

import services.NavigationDatabase;

class Test{
  
  public static void main(String[] args) throws FileNotFoundException{
    NavigationDatabase nd = new NavigationDatabase();
    System.out.println ("#Airports. "+nd.getAirports().size());
    System.out.println ("#Navaids. "+nd.getNavaids().size());
    
    
    String input = getInput("$$Navaid. ");
    while (!input.equals("quit")) {
      if (nd.getNavaids().containsKey(input))
        System.out.println(nd.getNavaids().get(input).get(0).getInfo());
      else
        System.out.println("Navaid Not Found.");
      input = getInput("$$Navaid. ");
    }
    

  } 
  
  public static String getInput(String output) {
    Scanner scan = new Scanner(System.in);
    System.out.print (output);
    return scan.nextLine();
  }

  
}