package net.eq2online.macros.core.executive.interfaces;

import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IReturnValue;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.exceptions.ScriptExceptionVoidResult;

public interface IReturnValueHandler {
  void handleReturnValue(IScriptActionProvider paramIScriptActionProvider, IMacro paramIMacro, IMacroAction paramIMacroAction, IReturnValue paramIReturnValue) throws ScriptExceptionVoidResult;
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\executive\interfaces\IReturnValueHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */