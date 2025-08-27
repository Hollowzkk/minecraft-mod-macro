/*    */ package net.eq2online.macros.gui.screens;
/*    */ 
/*    */ import bib;
/*    */ import blk;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.gui.GuiControl;
/*    */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*    */ 
/*    */ public class GuiMacroEditSimple
/*    */   extends GuiMacroEdit {
/*    */   public GuiMacroEditSimple(Macros macros, bib minecraft, blk parentScreen, int key) {
/* 13 */     super(macros, minecraft, parentScreen, key);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initControls() {
/* 19 */     addControl(
/* 20 */         (GuiControl)(this.chkControl = new GuiCheckBox(this.j, 4, this.l - 82, this.m - 50, 78, 14, I18n.get("macro.keyname.control"), this.options.control, GuiCheckBox.DisplayStyle.LAYOUT_BUTTON)));
/* 21 */     addControl(
/* 22 */         (GuiControl)(this.chkAlt = new GuiCheckBox(this.j, 5, this.l - 82, this.m - 34, 78, 14, I18n.get("macro.keyname.alt"), this.options.alt, GuiCheckBox.DisplayStyle.LAYOUT_BUTTON)));
/* 23 */     addControl(
/* 24 */         (GuiControl)(this.chkShift = new GuiCheckBox(this.j, 6, this.l - 82, this.m - 18, 78, 14, I18n.get("macro.keyname.shift"), this.options.shift, GuiCheckBox.DisplayStyle.LAYOUT_BUTTON)));
/* 25 */     addControl(
/* 26 */         (GuiControl)(this.chkGlobal = new GuiCheckBox(this.j, 8, this.l - 82, this.m - 82, 78, 14, I18n.get("macro.option.global.simple"), this.options.isGlobal, GuiCheckBox.DisplayStyle.LAYOUT_BUTTON)));
/* 27 */     addControl(
/* 28 */         (GuiControl)(this.chkInhibit = new GuiCheckBox(this.j, 7, this.l - 82, this.m - 66, 78, 14, I18n.get("macro.option.inhibit.simple"), this.options.inhibitParams, GuiCheckBox.DisplayStyle.LAYOUT_BUTTON)));
/* 29 */     addControl(
/* 30 */         (GuiControl)(this.chkAlways = new GuiCheckBox(this.j, 9, this.l - 82, this.m - 98, 78, 14, I18n.get("macro.option.always.simple"), this.options.isOverride, GuiCheckBox.DisplayStyle.LAYOUT_BUTTON)));
/*    */     
/* 32 */     initTabButtons(16);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawLabels(int left, int top, int foreColour) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean showOptions() {
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setupControlPositions(int left, int top) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawBackground(int backColour, int backColour2) {
/* 54 */     a(this.l - 84, this.m - 100, this.l - 2, this.m - 2, backColour);
/*    */     
/* 56 */     this.btnTabNormal.setYPosition(this.textBoxPosition - 34);
/* 57 */     this.btnTabKeystate.setYPosition(this.textBoxPosition - 34);
/* 58 */     this.btnTabConditional.setYPosition(this.textBoxPosition - 34);
/*    */     
/* 60 */     this.drawHeader = false;
/*    */ 
/*    */     
/* 63 */     a(2, this.textBoxPosition - 16, this.l - 86, this.m - 2, backColour);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiMacroEditSimple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */