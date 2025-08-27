/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiCheckBox
/*     */   extends GuiControlEx
/*     */ {
/*     */   public enum DisplayStyle
/*     */   {
/*  26 */     BUTTON,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  31 */     CHECKBOX,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  36 */     LAYOUT_BUTTON;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public DisplayStyle style = DisplayStyle.CHECKBOX;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checked;
/*     */ 
/*     */   
/*  49 */   public int mouseOverColour = 16777120;
/*     */   
/*  51 */   public int textColour = 14737632;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiCheckBox(bib minecraft, int id, int xPosition, int yPosition, String displayText, boolean checked) {
/*  65 */     super(minecraft, id, xPosition, yPosition, minecraft.k.a(displayText) + 20, 20, displayText);
/*  66 */     this.checked = checked;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiCheckBox(bib minecraft, int id, int xPosition, int yPosition, int width, int height, String displayText, boolean checked, DisplayStyle style) {
/*  84 */     super(minecraft, id, xPosition, yPosition, width, height, displayText);
/*     */     
/*  86 */     this.checked = checked;
/*  87 */     this.style = style;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCheckboxAt(bib minecraft, int mouseX, int mouseY, int yPos, float partialTicks) {
/*  92 */     this.i = yPos;
/*  93 */     a(minecraft, mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawControl(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/* 107 */     if (!this.m)
/*     */       return; 
/* 109 */     boolean mouseOver = (mouseX >= this.h && mouseY >= this.i && mouseX < this.h + this.f && mouseY < this.i + this.g);
/*     */     
/* 111 */     if (this.style == DisplayStyle.BUTTON) {
/*     */       
/* 113 */       a(minecraft, mouseX, mouseY, partialTicks);
/*     */     }
/* 115 */     else if (this.style == DisplayStyle.CHECKBOX) {
/*     */       
/* 117 */       minecraft.N().a(ResourceLocations.MAIN);
/* 118 */       float val = this.l ? 1.0F : 0.4F;
/* 119 */       GL.glColor4f(val, val, val, val);
/*     */       
/* 121 */       int u = this.checked ? 24 : 0;
/* 122 */       int y = this.i + (this.g - 12) / 2;
/*     */       
/* 124 */       this.renderer.drawTexturedModalRect(this.h, y, this.h + 12, y + 12, u, 104, u + 24, 128);
/* 125 */       a(minecraft, mouseX, mouseY);
/*     */       
/* 127 */       int textColour = this.l ? (mouseOver ? this.mouseOverColour : this.textColour) : -6250336;
/* 128 */       c(minecraft.k, this.j, this.h + 16, this.i + (this.g - 8) / 2, textColour);
/*     */     }
/*     */     else {
/*     */       
/* 132 */       a(this.h, this.i, this.h + this.f, this.i + this.g, this.checked ? -256 : -8355712);
/* 133 */       a(this.h + 1, this.i + 1, this.h + this.f - 1, this.i + this.g - 1, mouseOver ? -13421773 : -16777216);
/* 134 */       int colour = this.l ? (mouseOver ? this.mouseOverColour : (this.checked ? -256 : this.textColour)) : -6250336;
/* 135 */       a(minecraft.k, this.j, this.h + this.f / 2, this.i + (this.g - 8) / 2, colour);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean b(bib minecraft, int mouseX, int mouseY) {
/* 148 */     this.actionPerformed = false;
/* 149 */     if (super.b(minecraft, mouseX, mouseY)) {
/*     */       
/* 151 */       this.actionPerformed = true;
/* 152 */       this.checked = !this.checked;
/* 153 */       return true;
/*     */     } 
/*     */     
/* 156 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\GuiCheckBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */