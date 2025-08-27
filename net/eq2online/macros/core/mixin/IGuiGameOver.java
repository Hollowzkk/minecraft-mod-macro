package net.eq2online.macros.core.mixin;

import bkv;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({bkv.class})
public interface IGuiGameOver {
  @Accessor("enableButtonsTimer")
  int getCooldownTimer();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\IGuiGameOver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */