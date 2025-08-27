package net.eq2online.macros.core.mixin;

import bjm;
import bmd;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({bmd.class})
public interface IGuiKeyBindingList {
  @Accessor("listEntries")
  bjm.a[] getListEntries();
  
  @Accessor("listEntries")
  void setListEntries(bjm.a[] paramArrayOfa);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\IGuiKeyBindingList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */