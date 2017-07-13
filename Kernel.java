import java.io.FileNotFoundException;
import java.util.HashMap;

class Kernel {
  public static void main(String[] args) throws FileNotFoundException{
    Aircraft ac = new Aircraft("77w");
    Navigation nv = new Navigation();
    World wr = new World();

    FlightPlan fp = new FlightPlan();
    NavigationDatabase nd = new NavigationDatabase();
    DataInterface br = new DataInterface(ac, nv, wr, nd, fp);

    HashMap<String, Page> screens = new HashMap<>();
    screens.put("IDENT", new Page("Ident", br));
    screens.put("POS INIT", new Page("Pos-Init", br));
    screens.put("ROUTE", new Page("Route", br));
    screens.put("INDEX", new Page("Index", br));

    String currentScreen = "IDENT";
    while (screens.containsKey(currentScreen)) 
      currentScreen = screens.get(currentScreen).openPage();
    System.out.println(currentScreen + " not found.");
  }
}