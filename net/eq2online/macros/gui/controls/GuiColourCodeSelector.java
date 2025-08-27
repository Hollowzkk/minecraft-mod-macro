/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bib;
/*     */ import bir;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiColourCodeSelector
/*     */   extends bir
/*     */ {
/*  13 */   private static int[] COLOURS = new int[] { -16777216, -16777025, -16728320, -16728129, -4259840, -4259649, -4210944, -4210753, -12566464, -12566273, -12517568, -12517377, -49088, -48897, -192, -1 };
/*     */ 
/*     */   
/*     */   private int width;
/*     */ 
/*     */   
/*     */   private int height;
/*     */ 
/*     */   
/*     */   private int xPosition;
/*     */ 
/*     */   
/*     */   private int yPosition;
/*     */   
/*     */   private int spacing;
/*     */   
/*  29 */   private int colour = -1;
/*     */   
/*     */   private boolean labels = true;
/*     */   
/*  33 */   private static String COLOUR_CODES = "0123456789abcdef";
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
/*     */   public GuiColourCodeSelector(int width, int height, int xPosition, int yPosition, int spacing) {
/*  45 */     this.width = width;
/*  46 */     this.height = height;
/*  47 */     this.xPosition = xPosition;
/*  48 */     this.yPosition = yPosition;
/*  49 */     this.spacing = spacing;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  54 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  59 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPosition(int xPosition, int yPosition) {
/*  64 */     this.xPosition = xPosition;
/*  65 */     this.yPosition = yPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXPosition(int xPosition) {
/*  70 */     this.xPosition = xPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setYPosition(int yPosition) {
/*  75 */     this.yPosition = yPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasColour() {
/*  80 */     return (this.colour > -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setColour(int colour) {
/*  85 */     this.colour = colour;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColour() {
/*  90 */     return this.colour;
/*     */   }
/*     */ 
/*     */   
/*     */   public char getColourCode() {
/*  95 */     return COLOUR_CODES.charAt(this.colour);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawColourCodeSelector(bib minecraft) {
/* 103 */     int cellWidth = (this.width - this.spacing * 3) / 4;
/* 104 */     int cellHeight = (this.height - this.spacing * 3) / 4;
/*     */     
/* 106 */     a(this.xPosition - this.spacing, this.yPosition - this.height - this.spacing, this.xPosition + this.width + this.spacing, this.yPosition + this.spacing, -16777216);
/*     */ 
/*     */     
/* 109 */     for (int colourIndex = 0; colourIndex < 16; colourIndex++) {
/*     */ 
/*     */       
/* 112 */       int x = this.xPosition + colourIndex % 4 * (cellWidth + this.spacing);
/* 113 */       int y = this.yPosition - this.height + colourIndex / 4 * (cellHeight + this.spacing);
/*     */       
/* 115 */       if (colourIndex == this.colour)
/*     */       {
/* 117 */         a(x - this.spacing, y - this.spacing, x + cellWidth + this.spacing, y + cellHeight + this.spacing, -1);
/*     */       }
/*     */       
/* 120 */       a(x, y, x + cellWidth, y + cellHeight, COLOURS[colourIndex]);
/*     */       
/* 122 */       if (this.labels)
/*     */       {
/* 124 */         minecraft.k.a(COLOUR_CODES.substring(colourIndex, colourIndex + 1), x + 2, y + 2, -12566464);
/*     */       }
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
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY) {
/* 139 */     int cellWidth = (this.width - this.spacing * 3) / 4;
/* 140 */     int cellHeight = (this.height - this.spacing * 3) / 4;
/*     */     
/* 142 */     for (int colourIndex = 0; colourIndex < 16; colourIndex++) {
/*     */ 
/*     */       
/* 145 */       int x = this.xPosition + colourIndex % 4 * (cellWidth + this.spacing);
/* 146 */       int y = this.yPosition - this.height + colourIndex / 4 * (cellHeight + this.spacing);
/*     */       
/* 148 */       if (mouseX >= x && mouseX < x + cellWidth && mouseY >= y && mouseY < y + cellHeight) {
/*     */         
/* 150 */         this.colour = colourIndex;
/* 151 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 155 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyTyped(char keyChar, int keyCode) {
/* 166 */     if (keyCode > 1 && keyCode < 11) this.colour = keyCode - 1; 
/* 167 */     if (keyCode == 11) this.colour = 0; 
/* 168 */     if (keyCode == 30) this.colour = 10; 
/* 169 */     if (keyCode == 48) this.colour = 11; 
/* 170 */     if (keyCode == 46) this.colour = 12; 
/* 171 */     if (keyCode == 32) this.colour = 13; 
/* 172 */     if (keyCode == 18) this.colour = 14; 
/* 173 */     if (keyCode == 33) this.colour = 15;
/*     */     
/* 175 */     if (this.colour == -1) {
/*     */       
/* 177 */       if (keyCode == 200) this.colour = 12; 
/* 178 */       if (keyCode == 208) this.colour = 0; 
/* 179 */       if (keyCode == 203) this.colour = 3; 
/* 180 */       if (keyCode == 205) this.colour = 1;
/*     */     
/*     */     } else {
/*     */       
/* 184 */       if (keyCode == 200) this.colour = (this.colour + 12) % 16; 
/* 185 */       if (keyCode == 208) this.colour = (this.colour + 4) % 16; 
/* 186 */       if (keyCode == 203) this.colour = (this.colour + 15) % 16; 
/* 187 */       if (keyCode == 205) this.colour = (this.colour + 1) % 16; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\GuiColourCodeSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */