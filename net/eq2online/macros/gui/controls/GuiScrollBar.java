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
/*     */ public class GuiScrollBar
/*     */   extends GuiControlEx
/*     */ {
/*     */   protected ScrollBarOrientation orientation;
/*     */   protected int min;
/*     */   protected int max;
/*     */   protected int value;
/*     */   
/*     */   public enum ScrollBarOrientation
/*     */   {
/*  27 */     VERTICAL,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  32 */     HORIZONTAL;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   protected int buttonPos = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   protected int scrollButtonSize = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int traySize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int dragOffset;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   protected int mouseDownState = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   protected int mouseDownTime = 0;
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
/*     */   public GuiScrollBar(bib minecraft, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, int minValue, int maxValue, ScrollBarOrientation orientation) {
/* 103 */     super(minecraft, controlId, xPos, yPos, controlWidth, controlHeight, "");
/*     */     
/* 105 */     this.orientation = orientation;
/*     */     
/* 107 */     this.value = this.min = minValue;
/* 108 */     this.max = Math.max(this.min, maxValue);
/*     */     
/* 110 */     this.traySize = getLargeDimension() - getSmallDimension() * 2 - this.scrollButtonSize;
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
/*     */   protected int getLargeDimension() {
/* 122 */     return (this.orientation == ScrollBarOrientation.VERTICAL) ? this.g : this.f;
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
/*     */   protected int getSmallDimension() {
/* 134 */     return (this.orientation == ScrollBarOrientation.VERTICAL) ? this.f : this.g;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValue() {
/* 144 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(int value) {
/* 154 */     this.value = value;
/* 155 */     updateValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMin(int value) {
/* 166 */     this.min = value;
/* 167 */     this.max = Math.max(this.min, this.max);
/* 168 */     updateValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMax(int value) {
/* 179 */     this.max = value;
/* 180 */     this.min = Math.min(this.max, this.min);
/* 181 */     updateValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSize(int controlWidth, int controlHeight) {
/* 192 */     this.f = controlWidth;
/* 193 */     this.g = controlHeight;
/* 194 */     this.traySize = getLargeDimension() - getSmallDimension() * 2 - this.scrollButtonSize;
/* 195 */     updateValue();
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
/*     */   public void setSizeAndPosition(int left, int top, int controlWidth, int controlHeight) {
/* 208 */     setPosition(left, top);
/* 209 */     setSize(controlWidth, controlHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateValue() {
/* 217 */     if (this.value < this.min) this.value = this.min; 
/* 218 */     if (this.value > this.max) this.value = this.max;
/*     */     
/* 220 */     this.buttonPos = (int)((this.value - this.min) / (this.max - this.min) * this.traySize);
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
/*     */   protected void drawControl(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/* 233 */     if (!this.m)
/*     */       return; 
/* 235 */     float opacity = this.l ? 1.0F : 0.3F;
/*     */     
/* 237 */     minecraft.N().a(ResourceLocations.MAIN);
/* 238 */     GL.glColor4f(opacity, opacity, opacity, opacity);
/* 239 */     this.renderer.setTexMapSize(128);
/*     */ 
/*     */     
/* 242 */     boolean mouseOverUpButton = (this.mouseDownState == 3 || mouseIsOverButton(mouseX, mouseY, 3));
/* 243 */     boolean mouseOverDownButton = (this.mouseDownState == 2 || mouseIsOverButton(mouseX, mouseY, 2));
/* 244 */     boolean mouseOverButton = (this.mouseDownState == 1 || mouseIsOverButton(mouseX, mouseY, 1));
/*     */ 
/*     */     
/* 247 */     int upButtonHoverState = 64 + a(mouseOverUpButton) * this.scrollButtonSize;
/* 248 */     int downButtonHoverState = 64 + a(mouseOverDownButton) * this.scrollButtonSize;
/* 249 */     int dragbuttonHoverState = 64 + a(mouseOverButton) * this.scrollButtonSize;
/*     */ 
/*     */     
/* 252 */     a(minecraft, mouseX, mouseY);
/*     */     
/* 254 */     if (this.orientation == ScrollBarOrientation.VERTICAL) {
/*     */       
/* 256 */       drawVerticalScrollBar(upButtonHoverState, downButtonHoverState, dragbuttonHoverState);
/*     */     }
/*     */     else {
/*     */       
/* 260 */       drawHorizontalScrollBar(upButtonHoverState, downButtonHoverState, dragbuttonHoverState);
/*     */     } 
/*     */     
/* 263 */     this.renderer.setTexMapSize(256);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawHorizontalScrollBar(int upButtonHoverState, int downButtonHoverState, int dragbuttonHoverState) {
/* 274 */     this.renderer.drawTexturedModalRectRot(this.h, this.i + this.g - 2, this.h + this.g, this.i + this.g, 0, upButtonHoverState, 2, upButtonHoverState + 20);
/*     */     
/* 276 */     this.renderer.drawTexturedModalRectRot(this.h, this.i, this.h + this.g, this.i + this.g - 2, 128 - this.g + 2, upButtonHoverState, 128, upButtonHoverState + 20);
/*     */     
/* 278 */     this.renderer.drawTexturedModalRectRot(this.h + this.f - this.g, this.i + this.g - 2, this.h + this.f, this.i + this.g, 0, downButtonHoverState, 2, downButtonHoverState + 20);
/*     */     
/* 280 */     this.renderer.drawTexturedModalRectRot(this.h + this.f - this.g, this.i, this.h + this.f, this.i + this.g - 2, 128 - this.g + 2, downButtonHoverState, 128, downButtonHoverState + 20);
/*     */ 
/*     */ 
/*     */     
/* 284 */     this.renderer.drawTessellatedModalRectH(this.h + this.g, this.i, this.h + this.f - this.g, this.i + this.g - 1, 64, 46, 82, 64);
/*     */ 
/*     */ 
/*     */     
/* 288 */     this.renderer.drawTexturedModalRectRot(this.h + 1, this.i + 1, this.h + this.g - 4, this.i + this.g - 2, 100, 48, 118, 64);
/* 289 */     this.renderer.drawTexturedModalRectRot(this.h + this.f - this.g + 1, this.i + 1, this.h + this.f - 3, this.i + this.g - 2, 82, 48, 100, 64);
/*     */ 
/*     */ 
/*     */     
/* 293 */     this.renderer.drawTexturedModalRectRot(this.h + this.g + this.buttonPos, this.i + this.g - 3, 0, dragbuttonHoverState, 3, this.scrollButtonSize);
/*     */     
/* 295 */     this.renderer.drawTexturedModalRectRot(this.h + this.g + this.buttonPos, this.i, 128 - this.g + 3, dragbuttonHoverState, this.g - 3, this.scrollButtonSize);
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
/*     */   protected void drawVerticalScrollBar(int upButtonHoverState, int downButtonHoverState, int dragbuttonHoverState) {
/* 307 */     this.renderer.drawTexturedModalRect(this.h, this.i, this.h + 2, this.i + this.f, 0, upButtonHoverState, 2, upButtonHoverState + 20);
/*     */     
/* 309 */     this.renderer.drawTexturedModalRect(this.h + 2, this.i, this.h + this.f, this.i + this.f, 128 - this.f + 2, upButtonHoverState, 128, upButtonHoverState + 20);
/*     */     
/* 311 */     this.renderer.drawTexturedModalRect(this.h, this.i + this.g - this.f, this.h + 2, this.i + this.g, 0, downButtonHoverState, 2, downButtonHoverState + 20);
/*     */     
/* 313 */     this.renderer.drawTexturedModalRect(this.h + 2, this.i + this.g - this.f, this.h + this.f, this.i + this.g, 128 - this.f + 2, downButtonHoverState, 128, downButtonHoverState + 20);
/*     */ 
/*     */ 
/*     */     
/* 317 */     this.renderer.drawTessellatedModalRectV(this.h + 1, this.i + this.f, this.h + this.f - 1, this.i + this.g - this.f, 64, 48, 82, 64);
/*     */ 
/*     */ 
/*     */     
/* 321 */     this.renderer.drawTexturedModalRect(this.h + 1, this.i + 1, this.h + this.f - 2, this.i + this.f - 4, 100, 48, 118, 64);
/*     */     
/* 323 */     this.renderer.drawTexturedModalRect(this.h + 1, this.i + this.g - this.f + 1, this.h + this.f - 2, this.i + this.g - 3, 82, 48, 100, 64);
/*     */ 
/*     */ 
/*     */     
/* 327 */     this.renderer.drawTexturedModalRect(this.h, this.i + this.f + this.buttonPos, 0, dragbuttonHoverState, 3, this.scrollButtonSize, 0.0078125F);
/*     */     
/* 329 */     this.renderer.drawTexturedModalRect(this.h + 3, this.i + this.f + this.buttonPos, 128 - this.f + 3, dragbuttonHoverState, this.f - 3, this.scrollButtonSize, 0.0078125F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean mouseIsOverButton(int mouseX, int mouseY, int button) {
/* 335 */     mouseX -= this.h;
/* 336 */     mouseY -= this.i;
/*     */     
/* 338 */     if (mouseX < 0 || mouseY < 0 || mouseX > this.f || mouseY > this.g) return false;
/*     */     
/* 340 */     int largeDimension = getLargeDimension();
/* 341 */     int smallDimension = getSmallDimension();
/* 342 */     int buttonWidth = smallDimension;
/* 343 */     int buttonHeight = smallDimension;
/* 344 */     int buttonX = 0;
/* 345 */     int buttonY = 0;
/*     */     
/* 347 */     if (button == 2) {
/*     */       
/* 349 */       if (this.orientation == ScrollBarOrientation.VERTICAL)
/*     */       {
/* 351 */         buttonY = largeDimension - smallDimension;
/*     */       }
/* 353 */       else if (this.orientation == ScrollBarOrientation.HORIZONTAL)
/*     */       {
/* 355 */         buttonX = largeDimension - smallDimension;
/*     */       }
/*     */     
/* 358 */     } else if (button == 1) {
/*     */       
/* 360 */       if (this.orientation == ScrollBarOrientation.VERTICAL) {
/*     */         
/* 362 */         buttonY = smallDimension + this.buttonPos;
/* 363 */         buttonHeight = this.scrollButtonSize;
/*     */       }
/* 365 */       else if (this.orientation == ScrollBarOrientation.HORIZONTAL) {
/*     */         
/* 367 */         buttonX = smallDimension + this.buttonPos;
/* 368 */         buttonWidth = this.scrollButtonSize;
/*     */       } 
/*     */     } 
/*     */     
/* 372 */     return (mouseX >= buttonX && mouseY >= buttonY && mouseX < buttonX + buttonWidth && mouseY < buttonY + buttonHeight);
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
/*     */   protected void a(bib minecraft, int mouseX, int mouseY) {
/* 385 */     if (!this.m)
/*     */       return; 
/* 387 */     if (this.mouseDownState > 0) {
/*     */       
/* 389 */       int mouseDownTicks = this.updateCounter - this.mouseDownTime;
/*     */       
/* 391 */       if (this.mouseDownState == 1) {
/*     */         
/* 393 */         int range = this.max - this.min;
/* 394 */         int mPos = (this.orientation == ScrollBarOrientation.VERTICAL) ? (mouseY - this.i) : (mouseX - this.h);
/* 395 */         this.value = (int)((mPos - this.dragOffset - getSmallDimension()) / this.traySize * range) + this.min;
/*     */       }
/* 397 */       else if (this.mouseDownState == 2 && mouseDownTicks > 6) {
/*     */         
/* 399 */         this.value++;
/*     */       }
/* 401 */       else if (this.mouseDownState == 3 && mouseDownTicks > 6) {
/*     */         
/* 403 */         this.value--;
/*     */       } 
/*     */       
/* 406 */       updateValue();
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
/*     */   public void a(int mouseX, int mouseY) {
/* 419 */     this.mouseDownState = 0;
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
/*     */   public boolean b(bib minecraft, int mouseX, int mouseY) {
/* 432 */     this.actionPerformed = false;
/* 433 */     boolean returnValue = false;
/*     */     
/* 435 */     if (super.b(minecraft, mouseX, mouseY)) {
/*     */ 
/*     */       
/* 438 */       mouseX -= this.h; mouseY -= this.i;
/*     */       
/* 440 */       if (this.orientation == ScrollBarOrientation.HORIZONTAL) {
/*     */         
/* 442 */         int mouseT = mouseY;
/* 443 */         mouseY = mouseX;
/* 444 */         mouseX = mouseT;
/*     */       } 
/*     */ 
/*     */       
/* 448 */       this.mouseDownTime = this.updateCounter;
/*     */       
/* 450 */       int largeDimension = getLargeDimension();
/* 451 */       int smallDimension = getSmallDimension();
/*     */       
/* 453 */       if (mouseY < smallDimension) {
/*     */         
/* 455 */         this.mouseDownState = 3;
/* 456 */         this.value--;
/* 457 */         this.actionPerformed = true;
/*     */ 
/*     */       
/*     */       }
/* 461 */       else if (mouseY > largeDimension - smallDimension) {
/*     */         
/* 463 */         this.mouseDownState = 2;
/* 464 */         this.value++;
/* 465 */         this.actionPerformed = true;
/*     */       }
/* 467 */       else if (mouseY > this.buttonPos + smallDimension && mouseY < this.buttonPos + smallDimension + this.scrollButtonSize) {
/*     */         
/* 469 */         this.mouseDownState = 1;
/* 470 */         this.dragOffset = mouseY - this.buttonPos - smallDimension;
/* 471 */         returnValue = true;
/*     */       }
/* 473 */       else if (mouseY < this.buttonPos + smallDimension) {
/*     */         
/* 475 */         this.value -= 5;
/* 476 */         this.actionPerformed = true;
/*     */       }
/* 478 */       else if (mouseY > this.buttonPos + smallDimension + this.scrollButtonSize) {
/*     */         
/* 480 */         this.value += 5;
/* 481 */         this.actionPerformed = true;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 486 */       updateValue();
/*     */     } 
/*     */     
/* 489 */     return (this.actionPerformed || returnValue);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\GuiScrollBar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */