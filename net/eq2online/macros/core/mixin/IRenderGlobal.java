package net.eq2online.macros.core.mixin;

import buy;
import java.util.Map;
import oh;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({buy.class})
public interface IRenderGlobal {
  @Accessor("damagedBlocks")
  Map<Integer, oh> getDamageProgressMap();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\IRenderGlobal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */