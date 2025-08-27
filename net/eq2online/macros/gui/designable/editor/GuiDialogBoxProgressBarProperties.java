/*    */ package net.eq2online.macros.gui.designable.editor;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.gui.GuiScreenEx;
/*    */ import net.eq2online.macros.gui.controls.GuiDropDownList;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ 
/*    */ public class GuiDialogBoxProgressBarProperties
/*    */   extends GuiDialogBoxControlProperties
/*    */ {
/*    */   public GuiDialogBoxProgressBarProperties(bib minecraft, GuiScreenEx parentScreen, DesignableGuiControl control) {
/* 13 */     super(minecraft, parentScreen, control);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 19 */     super.initDialog();
/*    */     
/* 21 */     addTextField(I18n.get("control.properties.bar.expression"), "expression", false).b(true);
/* 22 */     addTextFieldWithCheckBox(I18n.get("control.properties.bar.min"), "min", I18n.get("control.properties.bar.expression"), "calcmin", false);
/* 23 */     addTextFieldWithCheckBox(I18n.get("control.properties.bar.max"), "max", I18n.get("control.properties.bar.expression"), "calcmax", false);
/* 24 */     addColourButton(I18n.get("control.properties.forecolour"), "colour", -16711936);
/* 25 */     addColourButton(I18n.get("control.properties.backcolour"), "background", -1442840576);
/*    */     
/* 27 */     GuiDropDownList.GuiDropDownListControl alignment = addDropDown(I18n.get("control.properties.bar.style"), "style", "Style");
/*    */     
/* 29 */     alignment.addItem("horizontal", I18n.get("control.properties.bar.style.horizontal"));
/* 30 */     alignment.addItem("vertical", I18n.get("control.properties.bar.style.vertical"));
/*    */     
/* 32 */     alignment.selectItemByTag(this.control.getProperty("style", "horizontal").toLowerCase());
/*    */     
/* 34 */     this.txtName.b(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxProgressBarProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */