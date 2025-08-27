package net.eq2online.macros.interfaces;

import bib;
import blk;
import net.eq2online.macros.gui.GuiDialogBox;

public interface IDialogParent {
  blk getDelegate();
  
  void displayDialog(GuiDialogBox paramGuiDialogBox);
  
  void drawScreenWithEnabledState(int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean);
  
  void initParentGui();
  
  void setResolution(bib parambib, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\IDialogParent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */