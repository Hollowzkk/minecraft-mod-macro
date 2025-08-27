package net.eq2online.macros.scripting.api;

public interface IArrayProvider extends IVariableProvider {
  int indexOf(String paramString1, String paramString2, boolean paramBoolean);
  
  int getMaxArrayIndex(String paramString);
  
  boolean checkArrayExists(String paramString);
  
  Object getArrayVariableValue(String paramString, int paramInt);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IArrayProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */