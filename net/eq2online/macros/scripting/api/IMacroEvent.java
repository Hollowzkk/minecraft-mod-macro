package net.eq2online.macros.scripting.api;

import com.mumfrey.liteloader.util.render.Icon;
import java.util.List;

public interface IMacroEvent {
  String getName();
  
  IMacroEventProvider getProvider();
  
  void setVariableProviderClass(Class<? extends IMacroEventVariableProvider> paramClass);
  
  IMacroEventVariableProvider getVariableProvider(String[] paramArrayOfString);
  
  void onDispatch();
  
  boolean isPermissible();
  
  String getPermissionGroup();
  
  String getPermissionName();
  
  Icon getIcon();
  
  void setIcon(Icon paramIcon);
  
  List<String> getHelp();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IMacroEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */