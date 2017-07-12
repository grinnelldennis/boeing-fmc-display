import java.io.FileNotFoundException;

class Kernel {
  public static void main(String[] args) throws FileNotFoundException{
    Aircraft ac = new Aircraft("77w");
    Navigation nv = new Navigation();
    World wr = new World();

    Page ident = new Page("Ident", ac, nv, wr);
    ident.renderScreen();

    Page posInit = new Page("Pos-Init", ac, nv, wr);
    posInit.renderScreen();
  }
}