package net.eq2online.macros.core.mixin;

import agr;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = {"net.minecraft.client.gui.inventory.GuiContainerCreative$CreativeSlot"})
public interface ICreativeSlot {
  @Accessor("slot")
  agr getInnerSlot();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\ICreativeSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */