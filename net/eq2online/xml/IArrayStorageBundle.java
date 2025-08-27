package net.eq2online.xml;

import java.util.Map;
import java.util.Set;

public interface IArrayStorageBundle {
  Set<String> getStorageTypes();
  
  Map<String, Map<Integer, ?>> getStorage(String paramString);
  
  void preDeserialise();
  
  void preSerialise();
  
  void postDeserialise();
  
  void postSerialise();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\xml\IArrayStorageBundle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */