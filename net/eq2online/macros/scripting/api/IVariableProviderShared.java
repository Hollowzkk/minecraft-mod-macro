package net.eq2online.macros.scripting.api;

public interface IVariableProviderShared extends IMutableArrayProvider, IFlagProvider, ICounterProvider, IStringProvider {
  void setSharedVariable(String paramString1, String paramString2);
  
  String getSharedVariable(String paramString);
  
  int getSharedVariable(String paramString, int paramInt);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IVariableProviderShared.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */