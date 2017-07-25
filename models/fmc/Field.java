import java.util.ArrayList;

class Field{
  String fieldId;
  String text;
  boolean takesInput;
  int maxSpaces;
  int row;
  int col;
  ArrayList<String> dependencies;
  public Field(String id, int spaces, int r, int c) {
    //FOR INPUT FIELDS
    this.fieldId = id; 
    this.maxSpaces = spaces; 
    this.row = r; 
    this.col = c;
    this.text = null;
    this.takesInput = true;
    dependencies = new ArrayList<>();
  }
  public Field(String id, int spaces, int r, int c, String d) {
    //FOR OUTPUT FIELDS
    this.fieldId = id; 
    this.maxSpaces = spaces; 
    this.row = r; 
    this.col = c;
    this.text = null;
    this.takesInput = false;
    dependencies = new ArrayList<>();
    loadDependencies(d);
  }
  private void loadDependencies(String s) {
    String[] sp = s.split(",");
    for (String ss : sp)
      dependencies.add(ss);
    System.out.println(fieldId+ "::#dependencies"+dependencies.size());
  }
}