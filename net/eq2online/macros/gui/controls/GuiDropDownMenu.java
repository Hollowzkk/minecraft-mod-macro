/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bib;
/*     */ import bip;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.gui.GuiRenderer;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import nf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiDropDownMenu
/*     */   extends GuiRenderer
/*     */ {
/*     */   private static final int PADDING_LEFT = 10;
/*     */   private static final int PADDING_RIGHT = 10;
/*     */   private static final int PADDING = 20;
/*     */   
/*     */   public class Item
/*     */   {
/*     */     String tag;
/*     */     String text;
/*     */     String combo;
/*     */     nf iconTexture;
/*     */     Point iconCoords;
/*     */     boolean disabled;
/*     */     
/*     */     public Item(String tag, String text) {
/*  36 */       this(tag, text, null, ResourceLocations.MAIN);
/*     */     }
/*     */ 
/*     */     
/*     */     public Item(String tag, String text, String combo) {
/*  41 */       this(tag, text, combo, (Point)null, ResourceLocations.MAIN);
/*     */     }
/*     */ 
/*     */     
/*     */     public Item(String tag, String text, int u, int v) {
/*  46 */       this(tag, text, new Point(u, v), ResourceLocations.MAIN);
/*     */     }
/*     */ 
/*     */     
/*     */     public Item(String tag, String text, String combo, int u, int v) {
/*  51 */       this(tag, text, combo, new Point(u, v), ResourceLocations.MAIN);
/*     */     }
/*     */ 
/*     */     
/*     */     public Item(String tag, String text, int u, int v, nf iconTexture) {
/*  56 */       this(tag, text, new Point(u, v), iconTexture);
/*     */     }
/*     */ 
/*     */     
/*     */     public Item(String tag, String text, String combo, int u, int v, nf iconTexture) {
/*  61 */       this(tag, text, combo, new Point(u, v), iconTexture);
/*     */     }
/*     */ 
/*     */     
/*     */     public Item(String tag, String text, Point iconCoords, nf iconTexture) {
/*  66 */       this(tag, text, (String)null, iconCoords, iconTexture);
/*     */     }
/*     */ 
/*     */     
/*     */     public Item(String tag, String text, String combo, Point iconCoords, nf iconTexture) {
/*  71 */       this.tag = tag;
/*  72 */       this.text = text;
/*  73 */       this.combo = combo;
/*  74 */       this.iconCoords = iconCoords;
/*  75 */       this.iconTexture = iconTexture;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTag() {
/*  80 */       return this.tag;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getText() {
/*  85 */       return this.text;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDisabled() {
/*  90 */       return this.disabled;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDisabled(boolean disabled) {
/*  95 */       this.disabled = disabled;
/*  96 */       GuiDropDownMenu.this.updateHeight();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getWidth(bip fontRenderer) {
/* 101 */       int width = 0;
/* 102 */       if (this.text != null)
/*     */       {
/* 104 */         width += fontRenderer.a(this.text);
/*     */       }
/* 106 */       if (this.combo != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 112 */         width += fontRenderer.a(this.combo);
/*     */       }
/* 114 */       return width;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 120 */       return this.text;
/*     */     }
/*     */   }
/*     */   
/*     */   public class Separator
/*     */     extends Item
/*     */   {
/*     */     public Separator() {
/* 128 */       super("-", "-");
/*     */     }
/*     */   }
/*     */   
/* 132 */   private final Separator separator = new Separator();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   protected List<Item> items = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private boolean hasIcons = false;
/*     */ 
/*     */   
/*     */   protected int width;
/*     */ 
/*     */   
/*     */   protected int height;
/*     */ 
/*     */   
/*     */   protected int itemHeight;
/*     */ 
/*     */   
/*     */   protected int xPos;
/*     */ 
/*     */   
/*     */   protected int yPos;
/*     */ 
/*     */   
/*     */   protected boolean dropDownVisible = false;
/*     */ 
/*     */   
/*     */   private boolean dropDown = true;
/*     */ 
/*     */   
/*     */   protected boolean autoSize = true;
/*     */ 
/*     */   
/*     */   public GuiDropDownMenu(bib minecraft) {
/* 168 */     this(minecraft, 10, 16, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDropDownMenu(bib minecraft, boolean dropDown) {
/* 178 */     this(minecraft, 10, 16, dropDown);
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
/*     */   public GuiDropDownMenu(bib minecraft, int width, int itemHeight, boolean dropDown) {
/* 190 */     super(minecraft);
/* 191 */     this.width = width;
/* 192 */     this.itemHeight = itemHeight;
/* 193 */     this.dropDown = dropDown;
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
/*     */   public GuiDropDownMenu addItem(String tag, String name) {
/* 205 */     addItem(new Item(tag, name));
/* 206 */     return this;
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
/*     */   public GuiDropDownMenu addItem(String tag, String name, String combo) {
/* 218 */     addItem(new Item(tag, name, combo));
/* 219 */     return this;
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
/*     */   public GuiDropDownMenu addItem(String tag, String name, int u, int v) {
/* 231 */     this.hasIcons = true;
/* 232 */     addItem(new Item(tag, name, u, v));
/* 233 */     return this;
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
/*     */   public GuiDropDownMenu addItem(String tag, String name, String combo, int u, int v) {
/* 245 */     this.hasIcons = true;
/* 246 */     addItem(new Item(tag, name, combo, u, v));
/* 247 */     return this;
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
/*     */   public GuiDropDownMenu addItem(String tag, String name, int u, int v, nf iconTexture) {
/* 259 */     this.hasIcons = true;
/* 260 */     addItem(new Item(tag, name, u, v, iconTexture));
/* 261 */     return this;
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
/*     */   public GuiDropDownMenu addItem(String tag, String name, String combo, int u, int v, nf iconTexture) {
/* 273 */     this.hasIcons = true;
/* 274 */     addItem(new Item(tag, name, combo, u, v, iconTexture));
/* 275 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiDropDownMenu addSeparator() {
/* 280 */     this.items.add(this.separator);
/* 281 */     updateHeight();
/* 282 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addItem(Item item) {
/* 287 */     this.items.add(item);
/* 288 */     updateHeight();
/* 289 */     updateWidth(item);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateWidth(Item item) {
/* 294 */     if (this.autoSize)
/*     */     {
/* 296 */       this.width = Math.max(this.width, item.getWidth(this.fontRenderer) + 20);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateHeight() {
/* 302 */     this.height = 8;
/* 303 */     for (Item item : this.items) {
/*     */       
/* 305 */       if (!item.disabled)
/*     */       {
/* 307 */         this.height += (item instanceof Separator) ? 4 : this.itemHeight;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(int pos) {
/* 314 */     return (pos > -1 && pos < this.items.size()) ? this.items.get(pos) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(String tag) {
/* 319 */     for (Item item : this.items) {
/*     */       
/* 321 */       if (item.tag.equals(tag))
/*     */       {
/* 323 */         return item;
/*     */       }
/*     */     } 
/*     */     
/* 327 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showDropDown() {
/* 335 */     this.dropDownVisible = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDropDownVisible() {
/* 345 */     return this.dropDownVisible;
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
/*     */   public void drawControlAt(Point position, int mouseX, int mouseY) {
/* 357 */     drawControlAt(position.x, position.y, mouseX, mouseY);
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
/*     */   public void drawControlAt(int x, int y, int mouseX, int mouseY) {
/* 369 */     this.xPos = x;
/* 370 */     this.yPos = y;
/*     */     
/* 372 */     if (!this.dropDownVisible)
/* 373 */       return;  if (!this.dropDown) y -= this.height; 
/* 374 */     mouseX -= x; mouseY -= y;
/*     */     
/* 376 */     GL.glPushMatrix();
/* 377 */     GL.glTranslatef(x, y, 0.0F);
/*     */     
/* 379 */     int fontColour = -1118482;
/* 380 */     int displayWidth = this.width + (this.hasIcons ? 16 : 0);
/*     */     
/* 382 */     a(-1, -1, displayWidth + 1, this.height + 1, fontColour);
/* 383 */     a(0, 0, displayWidth, this.height, -16777216);
/*     */     
/* 385 */     int yPos = 4;
/*     */     
/* 387 */     for (Item item : this.items) {
/*     */       
/* 389 */       if (item.disabled) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 394 */       if (item instanceof Separator) {
/*     */         
/* 396 */         a(4, yPos + 1, displayWidth - 4, yPos + 3, 1728053247);
/* 397 */         yPos += 4;
/*     */         
/*     */         continue;
/*     */       } 
/* 401 */       if (mouseX > 0 && mouseX < displayWidth && mouseY > yPos && mouseY < yPos + this.itemHeight) {
/*     */         
/* 403 */         a(2, yPos, displayWidth - 2, yPos + this.itemHeight, -1325400065);
/* 404 */         fontColour = -16777216;
/*     */       } 
/*     */       
/* 407 */       int itemXPos = 6;
/* 408 */       int itemYPos = yPos + (this.itemHeight - 8) / 2;
/*     */       
/* 410 */       if (this.hasIcons) {
/*     */         
/* 412 */         if (item.iconCoords != null) {
/*     */           
/* 414 */           float alpha = (fontColour >> 24 & 0xFF) / 255.0F;
/* 415 */           float red = (fontColour >> 16 & 0xFF) / 255.0F;
/* 416 */           float green = (fontColour >> 8 & 0xFF) / 255.0F;
/* 417 */           float blue = (fontColour & 0xFF) / 255.0F;
/* 418 */           GL.glColor4f(red, green, blue, alpha);
/*     */           
/* 420 */           this.mc.N().a(item.iconTexture);
/* 421 */           drawTexturedModalRectEx(itemXPos, itemYPos, itemXPos + 12, itemYPos + 8, item.iconCoords.x, item.iconCoords.y, item.iconCoords.x + 6, item.iconCoords.y + 4, 0);
/*     */         } 
/*     */ 
/*     */         
/* 425 */         itemXPos += 16;
/*     */       } 
/*     */       
/* 428 */       this.fontRenderer.a(item.toString(), itemXPos, itemYPos, fontColour);
/* 429 */       if (item.combo != null) {
/*     */         
/* 431 */         int comboWidth = this.fontRenderer.a(item.combo);
/* 432 */         this.fontRenderer.a(item.combo, itemXPos + this.width - comboWidth - 10, itemYPos, -8355712);
/*     */       } 
/*     */       
/* 435 */       yPos += this.itemHeight;
/* 436 */       fontColour = -1118482;
/*     */     } 
/*     */ 
/*     */     
/* 440 */     GL.glPopMatrix();
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
/*     */   public Item mousePressed(int mouseX, int mouseY) {
/* 452 */     if (!this.dropDownVisible) return null;
/*     */     
/* 454 */     this.dropDownVisible = false;
/*     */     
/* 456 */     if (!this.dropDown) mouseY += this.height; 
/* 457 */     mouseX -= this.xPos; mouseY -= this.yPos;
/*     */     
/* 459 */     int displayWidth = this.width + (this.hasIcons ? 16 : 0);
/*     */     
/* 461 */     int yPos = 4;
/*     */     
/* 463 */     for (Item item : this.items) {
/*     */       
/* 465 */       if (item.disabled) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 470 */       if (item instanceof Separator) {
/*     */         
/* 472 */         yPos += 4;
/*     */         
/*     */         continue;
/*     */       } 
/* 476 */       if (mouseX > 0 && mouseX < displayWidth && mouseY > yPos && mouseY < yPos + this.itemHeight)
/*     */       {
/* 478 */         return item;
/*     */       }
/*     */       
/* 481 */       yPos += this.itemHeight;
/*     */     } 
/*     */ 
/*     */     
/* 485 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getSize() {
/* 490 */     return new Dimension(this.width, this.height);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\GuiDropDownMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */