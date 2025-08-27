package net.eq2online.macros.scripting.api;

import java.util.List;

public interface IMacroEventManager {
  void registerEventProvider(IMacroEventProvider paramIMacroEventProvider);
  
  void reloadEvents();
  
  IMacroEvent registerEvent(IMacroEventProvider paramIMacroEventProvider, IMacroEventDefinition paramIMacroEventDefinition);
  
  IMacroEvent registerEvent(IMacroEvent paramIMacroEvent);
  
  IMacroEvent getEvent(int paramInt);
  
  IMacroEvent getEvent(String paramString);
  
  List<IMacroEvent> getEvents();
  
  int getEventID(String paramString);
  
  int getEventID(IMacroEvent paramIMacroEvent);
  
  void sendEvent(IMacroEvent paramIMacroEvent, String... paramVarArgs);
  
  void sendEvent(String paramString, int paramInt, String... paramVarArgs);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IMacroEventManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */