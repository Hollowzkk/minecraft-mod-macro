package net.eq2online.macros.scripting.api;

import net.eq2online.macros.scripting.parser.ScriptContext;

public interface IMacroActionContext {
  void onTick(boolean paramBoolean);
  
  ScriptContext getScriptContext();
  
  IScriptActionProvider getActionProvider();
  
  IVariableProvider getVariableProvider();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IMacroActionContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */