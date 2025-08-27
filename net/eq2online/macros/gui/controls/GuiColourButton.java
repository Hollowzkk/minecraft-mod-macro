/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bib;
/*     */ import bit;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.gui.GuiDialogBox;
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
/*     */ public class GuiColourButton
/*     */   extends GuiControlEx
/*     */ {
/*  21 */   private int colour = -16777216;
/*     */ 
/*     */   
/*     */   private GuiColourPicker picker;
/*     */   
/*     */   private boolean pickerClicked = false;
/*     */ 
/*     */   
/*     */   public GuiColourButton(bib minecraft, int id, int xPosition, int yPosition, int controlWidth, int controlHeight, String displayText, int initialColour) {
/*  30 */     super(minecraft, id, xPosition, yPosition, controlWidth, controlHeight, displayText);
/*  31 */     this.colour = initialColour;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColour() {
/*  36 */     return this.colour;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawControl(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/*  42 */     if (this.m) {
/*     */       
/*  44 */       boolean mouseOver = (mouseX >= this.h && mouseY >= this.i && mouseX < this.h + this.f && mouseY < this.i + this.g);
/*  45 */       int borderColour = mouseOver ? -1 : -6250336;
/*     */       
/*  47 */       a(this.h, this.i, this.h + this.f, this.i + this.g, borderColour);
/*     */       
/*  49 */       int v = Math.min(Math.max((int)(this.g / this.f * 1024.0F), 256), 1024);
/*     */       
/*  51 */       minecraft.N().a(ResourceLocations.COLOURPICKER_CHECKER);
/*  52 */       GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  53 */       this.renderer.drawTexturedModalRect(this.h + 1, this.i + 1, this.h + this.f - 1, this.i + this.g - 1, 0, 0, 1024, v);
/*     */       
/*  55 */       a(this.h + 1, this.i + 1, this.h + this.f - 1, this.i + this.g - 1, this.colour);
/*     */       
/*  57 */       a(minecraft, mouseX, mouseY);
/*     */       
/*  59 */       if (this.j != null && this.j.length() > 0) {
/*     */         
/*  61 */         GL.glEnableColorLogic();
/*  62 */         GL.glLogicOp(5387);
/*  63 */         a(minecraft.k, this.j, this.h + this.f / 2, this.i + (this.g - 8) / 2, -16777216);
/*     */         
/*  65 */         GL.glDisableColorLogic();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawPicker(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/*  72 */     if (this.m && this.picker != null) {
/*     */       
/*  74 */       this.picker.a(minecraft, mouseX, mouseY, partialTicks);
/*     */       
/*  76 */       if (this.picker.getDialogResult() == GuiDialogBox.DialogResult.OK) {
/*     */         
/*  78 */         closePicker(true);
/*     */       }
/*  80 */       else if (this.picker.getDialogResult() == GuiDialogBox.DialogResult.CANCEL) {
/*     */         
/*  82 */         closePicker(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closePicker(boolean getColour) {
/*  92 */     if (getColour) this.colour = this.picker.getColour(); 
/*  93 */     this.picker = null;
/*  94 */     this.pickerClicked = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY) {
/* 103 */     if (this.pickerClicked && this.picker != null) {
/*     */       
/* 105 */       this.picker.a(mouseX, mouseY);
/* 106 */       this.pickerClicked = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean b(bib minecraft, int mouseX, int mouseY) {
/* 117 */     this.actionPerformed = super.b(minecraft, mouseX, mouseY);
/*     */     
/* 119 */     if (this.picker == null) {
/*     */       
/* 121 */       if (this.actionPerformed) {
/*     */         
/* 123 */         bit sr = new bit(minecraft);
/* 124 */         int screenWidth = sr.a();
/* 125 */         int screenHeight = sr.b();
/*     */         
/* 127 */         int xPos = Math.min(this.h + this.f, screenWidth - 233);
/* 128 */         int yPos = Math.min(this.i, screenHeight - 175);
/*     */         
/* 130 */         this.picker = new GuiColourPicker(minecraft, 1, xPos, yPos, this.colour, "Choose colour");
/* 131 */         this.pickerClicked = false;
/*     */       } 
/*     */       
/* 134 */       return this.actionPerformed;
/*     */     } 
/*     */     
/* 137 */     this.pickerClicked = this.picker.b(minecraft, mouseX, mouseY);
/*     */     
/* 139 */     if (this.actionPerformed && !this.pickerClicked)
/*     */     {
/* 141 */       closePicker(true);
/*     */     }
/*     */     
/* 144 */     return this.pickerClicked;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean textBoxKeyTyped(char keyChar, int keyCode) {
/* 149 */     return (this.picker != null) ? this.picker.textBoxKeyTyped(keyChar, keyCode) : false;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\GuiColourButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */