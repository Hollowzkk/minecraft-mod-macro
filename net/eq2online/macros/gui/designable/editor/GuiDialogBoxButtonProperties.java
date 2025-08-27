/*    */ package net.eq2online.macros.gui.designable.editor;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.gui.GuiScreenEx;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ 
/*    */ public class GuiDialogBoxButtonProperties
/*    */   extends GuiDialogBoxControlProperties
/*    */ {
/*    */   public GuiDialogBoxButtonProperties(bib minecraft, GuiScreenEx parentScreen, DesignableGuiControl control) {
/* 12 */     super(minecraft, parentScreen, control);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 18 */     super.initDialog();
/*    */     
/* 20 */     addTextField(I18n.get("control.properties.button.text"), "text", false).b(true);
/* 21 */     addCheckBox(I18n.get("control.properties.button.hide"), "hide");
/* 22 */     addCheckBox(I18n.get("control.properties.button.sticky"), "sticky");
/* 23 */     addColourButton(I18n.get("control.properties.forecolour"), "colour", -16711936);
/* 24 */     addColourButton(I18n.get("control.properties.backcolour"), "background", -1442840576);
/* 25 */     addKeybindButton(I18n.get("control.properties.button.hotkey"), "hotkey", 0);
/*    */     
/* 27 */     this.txtName.b(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxButtonProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */