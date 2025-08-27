package net.eq2online.macros.interfaces;

import net.eq2online.macros.core.MacroParamProvider;
import net.eq2online.macros.core.MacroTriggerType;
import net.eq2online.macros.core.params.MacroParam;

public interface IMacroParamStorage {
  MacroTriggerType getMacroType();
  
  <TItem> String getStoredParam(MacroParamProvider<TItem> paramMacroParamProvider);
  
  void setStoredParam(MacroParam.Type paramType, String paramString);
  
  void setStoredParam(MacroParam.Type paramType, int paramInt, String paramString);
  
  void setStoredParam(MacroParam.Type paramType, int paramInt, String paramString1, String paramString2);
  
  void setReplaceFirstOccurrenceOnly(MacroParam.Type paramType, boolean paramBoolean);
  
  boolean shouldReplaceFirstOccurrenceOnly(MacroParam.Type paramType);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\IMacroParamStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */