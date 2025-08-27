package net.eq2online.macros.scripting.api;

public interface IStringProvider {
  public static final String EMPTY = "";
  
  public static final String KEY = "string";
  
  String getString(String paramString);
  
  String getString(String paramString, int paramInt);
  
  void setString(String paramString1, String paramString2);
  
  void setString(String paramString1, int paramInt, String paramString2);
  
  void unsetString(String paramString);
  
  void unsetString(String paramString, int paramInt);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IStringProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */