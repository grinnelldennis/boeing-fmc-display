import java.io.FileNotFoundException;

class Kernel {
  public static void main(String[] args) throws FileNotFoundException{
    Aircraft ac = new Aircraft("77w");
    Navigation nv = new Navigation();
    World wr = new World();

    DataInterface br = new DataInterface(ac, nv, wr);

    Page ident = new Page("Ident", br);
    ident.renderScreen();

    Page posInit = new Page("Pos-Init", br);
    posInit.renderScreen();
  }
}