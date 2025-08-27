/*     */ package net.eq2online.macros.gui.list;
/*     */ 
/*     */ import bib;
/*     */ import bip;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.compatibility.AllowedCharacters;
/*     */ import net.eq2online.macros.interfaces.IEditInPlace;
/*     */ import nf;
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
/*     */ public class EditInPlaceListEntry
/*     */   extends EditableListEntry<String>
/*     */   implements IEditInPlace<String>
/*     */ {
/*     */   protected boolean editing = false;
/*  29 */   protected String oldText = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   protected String emptyText = "";
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
/*     */   public EditInPlaceListEntry(int id, int iconID, String text, nf itemTexture, String emptyText) {
/*  47 */     super(id, iconID, text, itemTexture);
/*     */     
/*  49 */     this.emptyText = emptyText;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEditingInPlace() {
/*  55 */     return this.editing;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEditInPlace(boolean editInPlace) {
/*  61 */     this.editing = editInPlace;
/*  62 */     if (editInPlace) {
/*     */       
/*  64 */       this.oldText = this.text;
/*     */     }
/*     */     else {
/*     */       
/*  68 */       this.text = this.oldText;
/*  69 */       this.emptyText = "";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean editInPlaceKeyTyped(char keyChar, int keyCode) {
/*  76 */     if (keyCode == 28) {
/*     */       
/*  78 */       this.oldText = this.text;
/*  79 */       this.data = this.text;
/*  80 */       return false;
/*     */     } 
/*     */     
/*  83 */     if (keyCode == 1 || keyCode == 200 || keyCode == 208) {
/*     */       
/*  85 */       this.text = this.oldText;
/*  86 */       return false;
/*     */     } 
/*     */     
/*  89 */     if (keyCode == 14 && this.text.length() > 0)
/*     */     {
/*  91 */       this.text = this.text.substring(0, this.text.length() - 1);
/*     */     }
/*     */     
/*  94 */     if (AllowedCharacters.CHARACTERS.indexOf(keyChar) >= 0 && this.text.length() < 255)
/*     */     {
/*  96 */       this.text += keyChar;
/*     */     }
/*     */     
/*  99 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean editInPlaceMousePressed(boolean iconEnabled, bib minecraft, int mouseX, int mouseY, Rectangle rect) {
/* 105 */     return editInPlaceMousePressed(iconEnabled, minecraft, mouseX, mouseY, rect.x, rect.y, rect.width, rect.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean editInPlaceMousePressed(boolean iconEnabled, bib minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height) {
/* 112 */     return !this.editing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void editInPlaceDraw(boolean iconEnabled, bib minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height, int updateCounter) {
/* 119 */     a(xPosition - 1, yPosition - 1, xPosition + width + 1, yPosition + height + 1, -6250336);
/* 120 */     a(xPosition, yPosition, xPosition + width, yPosition + height, -16777216);
/*     */     
/* 122 */     if ("".equals(this.text) && !"".equals(this.emptyText))
/*     */     {
/* 124 */       c(minecraft.k, this.emptyText, xPosition + 4, yPosition + (height - 8) / 2, 5263440);
/*     */     }
/*     */     
/* 127 */     drawStringRightAligned(minecraft.k, this.text + ((updateCounter / 6 % 2 == 0) ? "_" : ""), xPosition + 4, yPosition + (height - 8) / 2, width, 14737632);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDraggable() {
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawStringRightAligned(bip fontrenderer, String s, int x, int y, int width, int colour) {
/* 143 */     Boolean cursorOffset = Boolean.valueOf(false);
/*     */     
/* 145 */     if (!s.endsWith("_")) {
/*     */       
/* 147 */       s = s + "_";
/* 148 */       cursorOffset = Boolean.valueOf(true);
/*     */     } 
/*     */     
/* 151 */     if (fontrenderer.a(s) <= width - 4) {
/*     */       
/* 153 */       if (cursorOffset.booleanValue()) s = s.substring(0, s.length() - 1); 
/* 154 */       fontrenderer.a(s, x, y, colour);
/*     */     }
/*     */     else {
/*     */       
/* 158 */       String trimmedText = s;
/* 159 */       int w = fontrenderer.a(trimmedText);
/*     */       
/* 161 */       while (w > width - 4 && trimmedText.length() > 0) {
/*     */         
/* 163 */         trimmedText = trimmedText.substring(1);
/* 164 */         w = fontrenderer.a(trimmedText);
/*     */       } 
/*     */       
/* 167 */       if (cursorOffset.booleanValue()) trimmedText = trimmedText.substring(0, trimmedText.length() - 1); 
/* 168 */       fontrenderer.a(trimmedText, x, y, colour);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\list\EditInPlaceListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */