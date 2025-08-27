/*    */ package net.eq2online.macros.gui.ext;
/*    */ 
/*    */ import bib;
/*    */ import bja;
/*    */ import bjm;
/*    */ import bme;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.core.settings.Settings;
/*    */ import net.eq2online.macros.input.InputHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BindScreenOptionEntry
/*    */   implements bjm.a
/*    */ {
/*    */   private final Settings settings;
/*    */   private final bib minecraft;
/*    */   private final int maxDescriptionWidth;
/*    */   private final String description;
/*    */   private final bja modeButton;
/*    */   
/*    */   public BindScreenOptionEntry(Macros macros, bib minecraft, bme controlsGui, int maxDescriptionWidth) {
/* 24 */     this.settings = macros.getSettings();
/* 25 */     this.minecraft = minecraft;
/* 26 */     this.maxDescriptionWidth = maxDescriptionWidth;
/*    */     
/* 28 */     this.description = I18n.get("options.option.mode.description");
/* 29 */     this.modeButton = new bja(0, 0, 0, 110, 18, "");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(int id, int xPosition, int yPosition, int width, int height, int mouseX, int mouseY, boolean mouseOver, float partialTicks) {
/* 35 */     this.minecraft.k.a(this.description, xPosition + 90 - this.maxDescriptionWidth, yPosition + height / 2 - this.minecraft.k.a / 2, 16777215);
/*    */ 
/*    */     
/* 38 */     this.modeButton.h = xPosition + 105;
/* 39 */     this.modeButton.i = yPosition;
/* 40 */     this.modeButton.j = this.settings.bindingMode.getDescription();
/* 41 */     this.modeButton.l = (this.settings.bindingMode != InputHandler.BindingComboMode.DISABLED);
/* 42 */     this.modeButton.a(this.minecraft, mouseX, mouseY, partialTicks);
/* 43 */     this.modeButton.l = true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean a(int id, int mouseX, int mouseY, int button, int relX, int relY) {
/* 49 */     if (this.modeButton.b(this.minecraft, mouseX, mouseY)) {
/*    */       
/* 51 */       this.settings.bindingMode = this.settings.bindingMode.getNextMode();
/* 52 */       this.settings.save();
/* 53 */       return true;
/*    */     } 
/*    */     
/* 56 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void b(int id, int mouseX, int mouseY, int button, int width, int height) {
/* 62 */     this.modeButton.a(mouseX, mouseY);
/*    */   }
/*    */   
/*    */   public void a(int entryID, int insideLeft, int yPos, float partialTicks) {}
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\ext\BindScreenOptionEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */