package net.eq2online.macros.scripting;

import bip;
import net.eq2online.macros.interfaces.ICommandInfo;

public interface IDocumentationEntry extends ICommandInfo {
  boolean isHidden();
  
  String getReturnType();
  
  void drawAt(bip parambip, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\IDocumentationEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */