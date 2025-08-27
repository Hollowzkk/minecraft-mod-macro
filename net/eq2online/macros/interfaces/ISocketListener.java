package net.eq2online.macros.interfaces;

import net.eq2online.macros.gui.controls.GuiListItemSocket;

public interface ISocketListener<T> {
  void onSocketChanged(GuiListItemSocket<T> paramGuiListItemSocket, IListEntry<T> paramIListEntry);
  
  void onSocketCleared(GuiListItemSocket<T> paramGuiListItemSocket);
  
  void onSocketClicked(GuiListItemSocket<T> paramGuiListItemSocket, boolean paramBoolean);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\ISocketListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */