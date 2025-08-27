/*    */ package net.eq2online.macros.interfaces;public interface ILayoutWidget<TParent extends bir> { boolean setPosition(TParent paramTParent, int paramInt1, int paramInt2); void setPositionSnapped(TParent paramTParent, int paramInt1, int paramInt2); Point getPosition(TParent paramTParent); int getId(); int getWidth(TParent paramTParent); boolean isBound();
/*    */   boolean isBindable();
/*    */   boolean isDenied();
/*    */   int getZIndex();
/*    */   String getDisplayText();
/*    */   String getDeniedText();
/*    */   void toggleReservedState();
/*    */   void draw(TParent paramTParent, Rectangle paramRectangle, int paramInt1, int paramInt2, IEditablePanel.EditMode paramEditMode, boolean paramBoolean1, boolean paramBoolean2);
/*    */   void mouseDragged(bib parambib, int paramInt1, int paramInt2);
/*    */   void mouseReleased(int paramInt1, int paramInt2);
/*    */   MousePressedResult mousePressed(bib parambib, int paramInt1, int paramInt2);
/*    */   void mouseClickedEdit(int paramInt1, int paramInt2);
/*    */   boolean mouseOver(Rectangle paramRectangle, int paramInt1, int paramInt2, boolean paramBoolean);
/* 14 */   public enum MousePressedResult { HIT,
/* 15 */     MISS,
/* 16 */     COPY,
/* 17 */     PASTE; }
/*    */    }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\ILayoutWidget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */