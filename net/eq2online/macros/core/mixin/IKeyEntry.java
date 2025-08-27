package net.eq2online.macros.core.mixin;

import bhy;
import bmd;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({bmd.b.class})
public interface IKeyEntry {
  @Accessor("keybinding")
  bhy getBinding();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\IKeyEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */