import java.io.FileNotFoundException;
import java.util.HashMap;

class Kernel {
  public static void main(String[] args) throws FileNotFoundException{
    Aircraft ac = new Aircraft("77w");
    Navigation nv = new Navigation();
    World wr = new World();

    DataInterface br = new DataInterface(ac, nv, wr);

    HashMap<String, Page> screens = new HashMap<>();
    screens.put("IDENT", new Page("Ident", br));
    screens.put("POS-INIT", new Page("Pos-Init", br));
    screens.put("RTE1", new Page("Route", br));

    System.out.println(screens.get("IDENT").openPage());
  }
}