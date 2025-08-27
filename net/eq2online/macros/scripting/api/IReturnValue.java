package net.eq2online.macros.scripting.api;

public interface IReturnValue {
  boolean isVoid();
  
  boolean getBoolean();
  
  int getInteger();
  
  String getString();
  
  String getLocalMessage();
  
  String getRemoteMessage();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IReturnValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */