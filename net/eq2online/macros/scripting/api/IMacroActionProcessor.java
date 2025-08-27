package net.eq2online.macros.scripting.api;

import net.eq2online.macros.scripting.exceptions.ScriptException;
import net.eq2online.macros.scripting.exceptions.ScriptExceptionStackOverflow;

public interface IMacroActionProcessor {
  boolean execute(IMacro paramIMacro, IMacroActionContext paramIMacroActionContext, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) throws ScriptException;
  
  void pushStack(IMacroAction paramIMacroAction, boolean paramBoolean) throws ScriptExceptionStackOverflow;
  
  void pushStack(int paramInt, IMacroAction paramIMacroAction, boolean paramBoolean) throws ScriptExceptionStackOverflow;
  
  boolean popStack();
  
  IMacroActionStackEntry getTopStackEntry();
  
  boolean getConditionalExecutionState();
  
  void breakLoop(IScriptActionProvider paramIScriptActionProvider, IMacro paramIMacro, IMacroAction paramIMacroAction);
  
  void beginUnsafeBlock(IScriptActionProvider paramIScriptActionProvider, IMacro paramIMacro, IMacroAction paramIMacroAction, int paramInt);
  
  void endUnsafeBlock(IScriptActionProvider paramIScriptActionProvider, IMacro paramIMacro, IMacroAction paramIMacroAction);
  
  boolean isUnsafe();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IMacroActionProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */