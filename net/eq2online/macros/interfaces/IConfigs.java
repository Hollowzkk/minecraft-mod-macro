package net.eq2online.macros.interfaces;

public interface IConfigs {
  String[] getConfigNames();
  
  String getConfigDisplayName(String paramString);
  
  String getActiveConfigName();
  
  String getOverlayConfigName(String paramString);
  
  String getActiveConfig();
  
  String getOverlayConfig();
  
  boolean hasConfig(String paramString);
  
  void setActiveConfig(String paramString);
  
  void setOverlayConfig(String paramString);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\IConfigs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */