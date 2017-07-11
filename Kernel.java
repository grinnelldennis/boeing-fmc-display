import java.io.FileNotFoundException;

class Kernel {
  public static void main(String[] args) throws FileNotFoundException{
    Aircraft ac = new Aircraft("77w");
    Navigation nv = new Navigation();
    Page ident = new Page("Ident", ac, nv);
    ident.renderScreen();
  }

}