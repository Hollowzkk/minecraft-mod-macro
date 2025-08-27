package net.eq2online.macros.scripting.api;

import java.util.Set;

public interface IVariableProvider extends IMacrosAPIModule {
  void updateVariables(boolean paramBoolean);
  
  Object getVariable(String paramString);
  
  Set<String> getVariables();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IVariableProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */