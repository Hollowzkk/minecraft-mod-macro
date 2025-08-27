package net.eq2online.macros.interfaces;

public interface ISettingsStore {
  void load();
  
  void save();
  
  void setSettingComment(String paramString1, String paramString2);
  
  void setSetting(String paramString1, String paramString2);
  
  void setSetting(String paramString, boolean paramBoolean);
  
  <T extends Enum<T>> void setSetting(String paramString, T paramT);
  
  void setSetting(String paramString, int paramInt);
  
  String getSetting(String paramString1, String paramString2);
  
  int getSetting(String paramString, int paramInt);
  
  int getSetting(String paramString, int paramInt1, int paramInt2, int paramInt3);
  
  boolean getSetting(String paramString, boolean paramBoolean);
  
  <T extends Enum> T getSetting(String paramString, T paramT);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\ISettingsStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */