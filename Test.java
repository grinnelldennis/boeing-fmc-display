import java.io.FileNotFoundException;

class Test{
  
  public static void main(String[] args) throws FileNotFoundException{
    NavigationDatabase nd = new NavigationDatabase();
    System.out.println ("#Airports. "+nd.airports.size());
    System.out.println ("#Navaids. "+nd.navaids.size());

  } 

  
}