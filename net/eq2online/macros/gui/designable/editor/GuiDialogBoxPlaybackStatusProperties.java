/*    */ package net.eq2online.macros.gui.designable.editor;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.gui.GuiScreenEx;
/*    */ import net.eq2online.macros.gui.controls.GuiDropDownList;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ 
/*    */ public class GuiDialogBoxPlaybackStatusProperties
/*    */   extends GuiDialogBoxControlProperties
/*    */ {
/*    */   public GuiDialogBoxPlaybackStatusProperties(bib minecraft, GuiScreenEx parentScreen, DesignableGuiControl control) {
/* 13 */     super(minecraft, parentScreen, control);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 19 */     super.initDialog();
/*    */     
/* 21 */     addColourButton(I18n.get("control.properties.bordercolour"), "colour", -16711936);
/* 22 */     addColourButton(I18n.get("control.properties.backcolour"), "background", -1442840576);
/*    */     
/* 24 */     GuiDropDownList.GuiDropDownListControl border = addDropDown(I18n.get("control.properties.playbackstatus.border"), "border", "-");
/*    */     
/* 26 */     border.addItem("none", I18n.get("control.properties.playbackstatus.border.none"));
/* 27 */     border.addItem("interactive", I18n.get("control.properties.playbackstatus.border.interactive"));
/* 28 */     border.addItem("always", I18n.get("control.properties.playbackstatus.border.always"));
/*    */     
/* 30 */     border.selectItemByTag(this.control.getProperty("border", "interactive").toLowerCase());
/*    */     
/* 32 */     addCheckBox(I18n.get("control.properties.playbackstatus.clip"), "clip");
/*    */     
/* 34 */     this.txtName.b(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxPlaybackStatusProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */