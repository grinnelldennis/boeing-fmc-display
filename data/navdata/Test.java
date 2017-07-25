import java.io.FileNotFoundException;
import java.util.HashMap;

class Test{
  
  public static void main(String[] args) throws FileNotFoundException{
    HashMap<String, String> table = new HashMap<>();
    table.put("Wildlife", "Ideal");

    TestSubject ts = new TestSubject(table);

    System.out.println(table.size());

  } 

  
}