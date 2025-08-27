package net.eq2online.macros.interfaces;

import java.awt.Rectangle;

public interface IInteractiveListEntry<T> extends IListEntry<T> {
  boolean mousePressed(boolean paramBoolean, int paramInt1, int paramInt2, Rectangle paramRectangle);
  
  boolean mousePressed(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
  
  void mouseReleased(int paramInt1, int paramInt2);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\IInteractiveListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */