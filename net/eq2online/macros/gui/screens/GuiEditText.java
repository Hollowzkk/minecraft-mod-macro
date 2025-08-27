/*    */ package net.eq2online.macros.gui.screens;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.gui.GuiScreenEx;
/*    */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*    */ import net.eq2online.macros.gui.controls.GuiTextEditor;
/*    */ import net.eq2online.macros.input.IProhibitOverride;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class GuiEditText
/*    */   extends GuiScreenWithHeader
/*    */   implements IProhibitOverride
/*    */ {
/*    */   protected GuiScreenEx parent;
/*    */   protected GuiTextEditor textEditor;
/*    */   protected GuiCheckBox chkShowHelp;
/*    */   
/*    */   public GuiEditText(bib minecraft, GuiScreenEx parent) {
/* 27 */     super(minecraft, 0, 0);
/* 28 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onCloseClick() {
/* 34 */     close();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void close() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void b() {
/* 48 */     Keyboard.enableRepeatEvents(true);
/*    */     
/* 50 */     super.b();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void m() {
/* 59 */     Keyboard.enableRepeatEvents(false);
/*    */     
/* 61 */     super.m();
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiEditText.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */