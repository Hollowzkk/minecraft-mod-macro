package net.eq2online.macros.scripting.api;

import java.util.List;

public interface IMacroEventProvider extends IMacrosAPIModule {
  IMacroEventDispatcher getDispatcher();
  
  void registerEvents(IMacroEventManager paramIMacroEventManager);
  
  List<String> getHelp(IMacroEvent paramIMacroEvent);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IMacroEventProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */