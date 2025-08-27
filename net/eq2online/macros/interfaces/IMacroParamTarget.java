package net.eq2online.macros.interfaces;

import net.eq2online.macros.core.params.MacroParam;

public interface IMacroParamTarget {
  String getDisplayName();
  
  String getPromptMessage();
  
  boolean hasRemainingParams();
  
  <TItem> MacroParam<TItem> getNextParam();
  
  int getIteration();
  
  String getIterationString();
  
  void compile();
  
  void recompile();
  
  String getTargetString();
  
  void setTargetString(String paramString);
  
  IMacroParamStorage getParamStore();
  
  void handleCompleted();
  
  void handleCancelled();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\IMacroParamTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */