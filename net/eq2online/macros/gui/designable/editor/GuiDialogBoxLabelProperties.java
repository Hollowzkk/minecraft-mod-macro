/*    */ package net.eq2online.macros.gui.designable.editor;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.gui.GuiScreenEx;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ import net.eq2online.macros.interfaces.IHighlighter;
/*    */ import net.eq2online.util.Util;
/*    */ 
/*    */ public class GuiDialogBoxLabelProperties
/*    */   extends GuiDialogBoxAlignableProperties
/*    */   implements IHighlighter {
/*    */   public GuiDialogBoxLabelProperties(bib minecraft, GuiScreenEx parentScreen, DesignableGuiControl control) {
/* 14 */     super(minecraft, parentScreen, control);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 20 */     super.initDialog();
/*    */     
/* 22 */     addTextField(I18n.get("control.properties.label.text"), "text", false, this).b(true);
/* 23 */     addTextField(I18n.get("control.properties.label.binding"), "binding", false);
/* 24 */     addColourButton(I18n.get("control.properties.forecolour"), "colour", -16711936);
/* 25 */     addColourButton(I18n.get("control.properties.backcolour"), "background", -1442840576);
/* 26 */     addAlignmentDropdown(I18n.get("control.properties.label.align"), false);
/* 27 */     addCheckBox(I18n.get("control.properties.text.shadow"), "shadow");
/* 28 */     addCheckBox(I18n.get("control.properties.text.border"), "border");
/*    */     
/* 30 */     this.txtName.b(false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String generateHighlightMask(String text) {
/* 36 */     return Util.stringToHighlightMask(highlight(text));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String highlight(String text) {
/* 42 */     return text.replaceAll("(?<!d)%%", "§d%§d%§f");
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxLabelProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */