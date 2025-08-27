package net.eq2online.macros.scripting.api;

import java.util.Map;
import net.eq2online.macros.scripting.exceptions.ScriptException;

public interface IMacro extends IVariableProvider, IVariableListener {
  IMacroStatus getStatus();
  
  IMacroTemplate getTemplate();
  
  boolean play(boolean paramBoolean1, boolean paramBoolean2) throws ScriptException;
  
  void refreshPermissions();
  
  int getID();
  
  String getDisplayName();
  
  void setSynchronous(boolean paramBoolean);
  
  boolean isSynchronous();
  
  IFlagProvider getFlagProvider();
  
  ICounterProvider getCounterProvider();
  
  IStringProvider getStringProvider();
  
  IMutableArrayProvider getArrayProvider();
  
  IMacroActionContext getContext();
  
  Map<String, Object> getStateData();
  
  <T> T getState(String paramString);
  
  <T> void setState(String paramString, T paramT);
  
  void markDirty();
  
  boolean isDirty();
  
  void kill();
  
  boolean isDead();
  
  void registerVariableProvider(IVariableProvider paramIVariableProvider);
  
  void unregisterVariableProvider(IVariableProvider paramIVariableProvider);
  
  public static interface IMacroStatus {
    long getStartTime();
    
    long getRunTime();
    
    String getFormattedStartTime();
    
    String getFormattedRunTime();
    
    IMacro getMacro();
  }
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IMacro.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */