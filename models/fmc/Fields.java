import java.util.HashMap;
class Fields{
  HashMap<String, String> ifds;
  public Fields() {
    ifds = new HashMap<>();
  }
  public void create(String key) {
    put(key, null);
  }
  public void put(String key, String value){
    //if (!ifds.containsKey(key))
      ifds.put(key, value);
  }
  public boolean containsField(String key) {
    return ifds.containsKey(key);
  }
  public boolean containsKey(String key) {
    if (ifds.containsKey(key))
      return ifds.get(key)!=null;
    return false;
  }
  public String get(String key) {
    if (ifds.containsKey(key))
      return ifds.get(key);
    return null;
  }
}