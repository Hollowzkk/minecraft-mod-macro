package net.eq2online.macros.scripting.api;

public interface IMacroTemplate extends IFlagProvider, ICounterProvider, IStringProvider, IMutableArrayProvider {
  String getKeyDownMacro();
  
  String getKeyHeldMacro();
  
  String getKeyUpMacro();
  
  String getMacroCondition();
  
  int getRepeatRate();
  
  IMacro createInstance(boolean paramBoolean, IMacroActionContext paramIMacroActionContext);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IMacroTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */