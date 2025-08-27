package net.eq2online.macros.core.mixin;

import aip;
import bmp;
import fi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({bmp.b.class})
public interface IContainerCreative {
  @Accessor("itemList")
  fi<aip> getItemsList();
  
  @Invoker("scrollTo")
  void scrollToPosition(float paramFloat);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\IContainerCreative.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */