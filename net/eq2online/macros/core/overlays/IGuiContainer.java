package net.eq2online.macros.core.overlays;

import afw;
import agr;
import com.mumfrey.liteloader.transformers.access.Accessor;
import com.mumfrey.liteloader.transformers.access.Invoker;
import com.mumfrey.liteloader.transformers.access.ObfTableClass;
import net.eq2online.obfuscation.ObfTbl;

@ObfTableClass(ObfTbl.class)
@Accessor({"GuiContainer"})
public interface IGuiContainer {
  @Invoker({"getSlotAtPosition"})
  agr getSlot(int paramInt1, int paramInt2);
  
  @Invoker({"handleMouseClick"})
  void mouseClick(agr paramagr, int paramInt1, int paramInt2, afw paramafw);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\overlays\IGuiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */