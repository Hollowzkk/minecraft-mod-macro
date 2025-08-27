package net.eq2online.macros.core.mixin;

import afw;
import agr;
import bmg;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({bmg.class})
public interface IGuiContainer {
  @Invoker("getSlotAtPosition")
  agr getSlot(int paramInt1, int paramInt2);
  
  @Invoker("handleMouseClick")
  void mouseClick(agr paramagr, int paramInt1, int paramInt2, afw paramafw);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\IGuiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */