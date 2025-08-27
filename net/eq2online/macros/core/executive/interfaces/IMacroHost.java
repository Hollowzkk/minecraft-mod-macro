package net.eq2online.macros.core.executive.interfaces;

import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IScriptActionProvider;

public interface IMacroHost {
  void trace(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  void addScriptError(IScriptActionProvider paramIScriptActionProvider, IMacro paramIMacro, String paramString);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\executive\interfaces\IMacroHost.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */