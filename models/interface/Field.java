package modelsinterface;

import java.util.ArrayList;

public class Field{
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
  
  /*
   *  Getters & Setters (AUTPGEN)
   */
  public String getFieldId() {
    return fieldId;
  }
  public void setFieldId(String fieldId) {
    this.fieldId = fieldId;
  }
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
  public boolean isTakesInput() {
    return takesInput;
  }
  public void setTakesInput(boolean takesInput) {
    this.takesInput = takesInput;
  }
  public int getMaxSpaces() {
    return maxSpaces;
  }
  public void setMaxSpaces(int maxSpaces) {
    this.maxSpaces = maxSpaces;
  }
  public int getRow() {
    return row;
  }
  public void setRow(int row) {
    this.row = row;
  }
  public int getCol() {
    return col;
  }
  public void setCol(int col) {
    this.col = col;
  }
  public ArrayList<String> getDependencies() {
    return dependencies;
  }
  public void setDependencies(ArrayList<String> dependencies) {
    this.dependencies = dependencies;
  }
}
