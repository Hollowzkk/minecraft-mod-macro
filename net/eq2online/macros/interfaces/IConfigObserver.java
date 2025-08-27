package net.eq2online.macros.interfaces;

public interface IConfigObserver extends IObserver {
  void onConfigChanged(IConfigs paramIConfigs);
  
  void onConfigAdded(IConfigs paramIConfigs, String paramString, boolean paramBoolean);
  
  void onConfigRemoved(IConfigs paramIConfigs, String paramString);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\IConfigObserver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */