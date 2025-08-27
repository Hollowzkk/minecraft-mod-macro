package net.eq2online.macros.interfaces;

public interface ILayoutPanel<TWidget extends ILayoutWidget> extends IEditablePanel {
  void connect(ILayoutPanelContainer paramILayoutPanelContainer);
  
  void release();
  
  void setSizeAndPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  boolean isDragging();
  
  ILayoutWidget getWidgetAt(int paramInt1, int paramInt2);
  
  boolean keyPressed(char paramChar, int paramInt);
  
  void postRender(int paramInt1, int paramInt2);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\ILayoutPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */