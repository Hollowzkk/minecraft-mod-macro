package net.eq2online.macros.interfaces;

public interface ILayoutPanelContainer {
  void captureWidgetAt(int paramInt1, int paramInt2);
  
  ILayoutWidget getCapturedWidget();
  
  void handleWidgetClick(ILayoutPanel<? extends ILayoutWidget> paramILayoutPanel, int paramInt, boolean paramBoolean);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\ILayoutPanelContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */