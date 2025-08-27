package net.eq2online.macros.scripting.api;

public interface IFlagProvider {
  public static final boolean EMPTY = false;
  
  public static final String KEY = "boolean";
  
  boolean getFlag(String paramString);
  
  boolean getFlag(String paramString, int paramInt);
  
  void setFlag(String paramString, boolean paramBoolean);
  
  void setFlag(String paramString, int paramInt, boolean paramBoolean);
  
  void setFlag(String paramString);
  
  void setFlag(String paramString, int paramInt);
  
  void unsetFlag(String paramString);
  
  void unsetFlag(String paramString, int paramInt);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IFlagProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */