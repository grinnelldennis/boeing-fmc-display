import java.io.FileNotFoundException;
import java.util.HashMap;

import global.Aircraft;
import global.Navigation;
import global.World;
import modelsfmc.*;
import services.NavigationDatabase;
import services.DataInterface;
import services.FlightPlan;

class Kernel {

  public static void main(String[] args) throws FileNotFoundException{
    Aircraft ac = new Aircraft("77w");
    Navigation nv = new Navigation();
    World wr = new World();
    Fields fd = new Fields();

    FlightPlan fp = new FlightPlan();
    NavigationDatabase nd = new NavigationDatabase();
    DataInterface br = new DataInterface(ac, nv, wr, nd, fp);

    HashMap<String, Page> screens = new HashMap<>();
    screens.put("IDENT", new Page("Ident", br, fd));
    screens.put("POS INIT", new Page("Pos-Init", br, fd));
    screens.put("ROUTE", new Page("Route", br, fd));
    screens.put("INDEX", new Page("Index", br, fd));
    screens.put("ACTIVATE", new Page("Prog", br, fd)); //progress page

    System.out.println("Screens contain "+fd.getIfds().size()+" fields.");

    String currentScreen = "IDENT";
    while (screens.containsKey(currentScreen)) 
      currentScreen = screens.get(currentScreen).openPage(fd);
    System.out.println(currentScreen + " not found.");
  }
}