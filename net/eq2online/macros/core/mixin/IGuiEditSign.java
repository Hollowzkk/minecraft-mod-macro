package net.eq2online.macros.core.mixin;

import awc;
import bnb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({bnb.class})
public interface IGuiEditSign {
  @Accessor("tileSign")
  awc getSign();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\IGuiEditSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */