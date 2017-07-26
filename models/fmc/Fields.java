package modelsfmc;

import java.util.HashMap;
public class Fields{
  private HashMap<String, String> ifds;
  
  public Fields() {
    setIfds(new HashMap<>());
  }
  
  public void create(String key) {
    put(key, null);
  }
  
  public void put(String key, String value){
    //if (!ifds.containsKey(key))
      getIfds().put(key, value);
  }
  
  public boolean containsField(String key) {
    return getIfds().containsKey(key);
  }
  
  public boolean containsKey(String key) {
    if (getIfds().containsKey(key))
      return getIfds().get(key)!=null;
    return false;
  }
  
  public String get(String key) {
    if (getIfds().containsKey(key))
      return getIfds().get(key);
    return null;
  }

  public HashMap<String, String> getIfds() {
    return ifds;
  }

  public void setIfds(HashMap<String, String> ifds) {
    this.ifds = ifds;
  }
}