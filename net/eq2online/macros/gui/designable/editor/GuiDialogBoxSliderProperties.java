/*    */ package net.eq2online.macros.gui.designable.editor;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.gui.GuiScreenEx;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ 
/*    */ public class GuiDialogBoxSliderProperties
/*    */   extends GuiDialogBoxControlProperties
/*    */ {
/*    */   public GuiDialogBoxSliderProperties(bib minecraft, GuiScreenEx parentScreen, DesignableGuiControl control) {
/* 12 */     super(minecraft, parentScreen, control);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 18 */     super.initDialog();
/*    */     
/* 20 */     addTextField(I18n.get("control.properties.label.binding"), "binding", false);
/* 21 */     addTextFieldWithCheckBox(I18n.get("control.properties.bar.min"), "min", I18n.get("control.properties.bar.expression"), "calcmin", false);
/* 22 */     addTextFieldWithCheckBox(I18n.get("control.properties.bar.max"), "max", I18n.get("control.properties.bar.expression"), "calcmax", false);
/* 23 */     addColourButton(I18n.get("control.properties.forecolour"), "colour", -16711936);
/* 24 */     addColourButton(I18n.get("control.properties.backcolour"), "background", -1442840576);
/* 25 */     addKeybindButtonPair(I18n.get("control.properties.slider.hotkeys"), "hotkeydec", 0, "hotkeyinc", 0);
/*    */     
/* 27 */     this.txtName.b(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxSliderProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */