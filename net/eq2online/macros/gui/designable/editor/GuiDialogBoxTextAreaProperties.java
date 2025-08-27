/*    */ package net.eq2online.macros.gui.designable.editor;
/*    */ 
/*    */ import bib;
/*    */ import bje;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.gui.GuiControl;
/*    */ import net.eq2online.macros.gui.GuiScreenEx;
/*    */ import net.eq2online.macros.gui.controls.GuiLabel;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ 
/*    */ public class GuiDialogBoxTextAreaProperties
/*    */   extends GuiDialogBoxControlProperties {
/*    */   public GuiDialogBoxTextAreaProperties(bib minecraft, GuiScreenEx parentScreen, DesignableGuiControl control) {
/* 14 */     super(minecraft, parentScreen, control);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 20 */     super.initDialog();
/*    */     
/* 22 */     addColourButton(I18n.get("control.properties.forecolour"), "colour", -16711936);
/*    */     
/* 24 */     bje textField = new bje(0, this.j.k, this.dialogX + 85, getControlTop(), 32, 16);
/* 25 */     addControl((GuiControl)new GuiLabel(-999, this.dialogX + 122, getControlTop() + 4, "ticks", -22016));
/* 26 */     addTextField(I18n.get("control.properties.textarea.lifespan"), "lifespan", textField, true);
/*    */     
/* 28 */     this.txtName.b(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxTextAreaProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */