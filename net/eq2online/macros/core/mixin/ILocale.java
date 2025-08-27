package net.eq2online.macros.core.mixin;

import cfb;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({cfb.class})
public interface ILocale {
  @Accessor("properties")
  Map<String, String> getTranslationTable();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\ILocale.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */