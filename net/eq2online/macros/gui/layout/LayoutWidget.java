/*     */ package net.eq2online.macros.gui.layout;
/*     */ 
/*     */ import bib;
/*     */ import bip;
/*     */ import bir;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiRendererMacros;
/*     */ import net.eq2online.macros.interfaces.ILayoutWidget;
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
/*     */ public abstract class LayoutWidget
/*     */   extends GuiRendererMacros
/*     */   implements ILayoutWidget<LayoutPanelStandard>
/*     */ {
/*     */   protected int id;
/*     */   protected String name;
/*     */   protected String text;
/*     */   protected int width;
/*     */   protected int height;
/*     */   protected int xPosition;
/*     */   protected int yPosition;
/*     */   protected int drawX;
/*     */   private int dragOffsetX;
/*     */   private int dragOffsetY;
/*     */   protected boolean centreAlign = true;
/*  72 */   public static int COLOUR_UNBOUND = -12566464;
/*  73 */   public static int COLOUR_BOUND = -256;
/*  74 */   public static int COLOUR_SPECIAL = -7864320;
/*  75 */   public static int COLOUR_BOUNDSPECIAL = -22016;
/*  76 */   public static int COLOUR_BOUNDGLOBAL = -16711936;
/*  77 */   public static int COLOUR_SELECTED = -1;
/*  78 */   public static int COLOUR_DENIED = -65536;
/*     */ 
/*     */   
/*     */   public LayoutWidget(Macros macros, bib minecraft, bip fontRenderer, String name, int width, int height, boolean centre) {
/*  82 */     super(macros, minecraft);
/*     */     
/*  84 */     this.name = name;
/*  85 */     this.text = name;
/*  86 */     this.width = width;
/*  87 */     this.height = height;
/*  88 */     this.centreAlign = centre;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setPosition(LayoutPanelStandard parent, int x, int y) {
/*  98 */     this.xPosition = x;
/*  99 */     this.yPosition = y;
/* 100 */     this.drawX = this.centreAlign ? (this.xPosition - this.width / 2) : this.xPosition;
/*     */     
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPositionSnapped(LayoutPanelStandard parent, int x, int y) {
/* 112 */     setPosition(parent, x - x % 2, y - y % 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getPosition(LayoutPanelStandard parent) {
/* 121 */     return new Point(this.xPosition, this.yPosition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getId() {
/* 130 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZIndex() {
/* 136 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth(LayoutPanelStandard parent) {
/* 145 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBound() {
/* 154 */     return this.macros.isMacroBound(this.id, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBindable() {
/* 163 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDenied() {
/* 172 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayText() {
/* 181 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDeniedText() {
/* 190 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseDragged(bib minecraft, int mouseX, int mouseY) {
/* 200 */     setPositionSnapped((LayoutPanelStandard)null, mouseX + this.dragOffsetX, mouseY + this.dragOffsetY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY) {
/* 210 */     setPositionSnapped((LayoutPanelStandard)null, mouseX + this.dragOffsetX, mouseY + this.dragOffsetY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ILayoutWidget.MousePressedResult mousePressed(bib minecraft, int mouseX, int mouseY) {
/* 220 */     if (mouseOver(null, mouseX, mouseY, false)) {
/*     */       
/* 222 */       this.dragOffsetX = this.xPosition - mouseX;
/* 223 */       this.dragOffsetY = this.yPosition - mouseY;
/* 224 */       return ILayoutWidget.MousePressedResult.HIT;
/*     */     } 
/*     */     
/* 227 */     return ILayoutWidget.MousePressedResult.MISS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClickedEdit(int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseOver(Rectangle boundingBox, int mouseX, int mouseY, boolean selected) {
/* 242 */     return (mouseX > this.drawX && mouseX < this.drawX + this.width && mouseY > this.yPosition && mouseY < this.yPosition + this.height);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\layout\LayoutWidget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */