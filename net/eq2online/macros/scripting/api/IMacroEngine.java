package net.eq2online.macros.scripting.api;

import java.io.File;
import java.util.List;
import net.eq2online.macros.scripting.parser.ScriptContext;

public interface IMacroEngine {
  File getFile(String paramString);
  
  String getMacroNameForId(int paramInt);
  
  int getMacroIdForName(String paramString);
  
  String getActiveConfig();
  
  String getActiveConfigName();
  
  void playMacro(IMacroTemplate paramIMacroTemplate, boolean paramBoolean, ScriptContext paramScriptContext, IVariableProvider paramIVariableProvider);
  
  IMacroTemplate createFloatingTemplate(String paramString1, String paramString2);
  
  int getExecutingMacroCount();
  
  List<IMacro.IMacroStatus> getExecutingMacroStatus();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IMacroEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */