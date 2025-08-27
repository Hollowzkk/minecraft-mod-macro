package net.eq2online.macros.interfaces;

import bib;
import java.awt.Rectangle;

public interface IEditInPlace<T> extends IInteractiveListEntry<T> {
  boolean isEditingInPlace();
  
  void setEditInPlace(boolean paramBoolean);
  
  boolean editInPlaceKeyTyped(char paramChar, int paramInt);
  
  boolean editInPlaceMousePressed(boolean paramBoolean, bib parambib, int paramInt1, int paramInt2, Rectangle paramRectangle);
  
  boolean editInPlaceMousePressed(boolean paramBoolean, bib parambib, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
  
  void editInPlaceDraw(boolean paramBoolean, bib parambib, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\IEditInPlace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */