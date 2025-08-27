/*    */ package net.eq2online.macros.gui.controls;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.gui.GuiControl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiButtonTab
/*    */   extends GuiControl
/*    */ {
/* 16 */   public int tabColour = -1342177280, activeTabColour = -1607257293, enabledTextColour = -1, disabledTextColour = -256;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   public int selectedHeight = 2;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean active;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiButtonTab(int id, int xPosition, int yPosition, int controlWidth, int controlHeight, String displayText) {
/* 40 */     super(id, xPosition, yPosition, controlWidth, controlHeight, displayText);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiButtonTab(int id, int xPosition, int yPosition, String displayText) {
/* 53 */     super(id, xPosition, yPosition, displayText);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/* 59 */     if (!this.m)
/*    */       return; 
/* 61 */     a(minecraft, mouseX, mouseY);
/*    */     
/* 63 */     a(this.h, this.i, this.h + this.f, this.i + this.g + (this.active ? this.selectedHeight : 0), this.active ? this.activeTabColour : this.tabColour);
/*    */     
/* 65 */     a(minecraft.k, this.j, this.h + this.f / 2, this.i + (this.g - 8) / 2, this.l ? (this.active ? this.enabledTextColour : this.disabledTextColour) : -10461088);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\GuiButtonTab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */