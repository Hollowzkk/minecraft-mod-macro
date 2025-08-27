package net.eq2online.macros.gui.overlay;

public interface IOverlay {
  void onTick();
  
  boolean isAlwaysRendered();
  
  void setScreenSize(int paramInt1, int paramInt2, int paramInt3);
  
  void draw(int paramInt1, int paramInt2, long paramLong, float paramFloat, boolean paramBoolean);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\overlay\IOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */