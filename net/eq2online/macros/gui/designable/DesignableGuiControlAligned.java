/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import bib;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ 
/*     */ public abstract class DesignableGuiControlAligned
/*     */   extends DesignableGuiControl {
/*     */   protected static final int TOP = 0;
/*     */   protected static final int MIDDLE = 1;
/*     */   protected static final int BOTTOM = 2;
/*  12 */   private int hAlign = 4; protected static final int LEFT = 4; protected static final int CENTRE = 8; protected static final int RIGHT = 16; protected static final int FILL = 32;
/*  13 */   private int vAlign = 1;
/*     */   
/*     */   private boolean updating;
/*     */   
/*     */   protected final String defaultAlignment;
/*     */ 
/*     */   
/*     */   public DesignableGuiControlAligned(Macros macros, bib mc, int id) {
/*  21 */     this(macros, mc, id, "top left");
/*     */   }
/*     */ 
/*     */   
/*     */   protected DesignableGuiControlAligned(Macros macros, bib mc, int id, String defaultAlignment) {
/*  26 */     super(macros, mc, id);
/*  27 */     this.defaultAlignment = defaultAlignment;
/*  28 */     initProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doInit() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initProperties() {
/*  39 */     super.initProperties();
/*  40 */     setProperty("align", this.defaultAlignment);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBindable() {
/*  46 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropertyWithValidation(String property, String stringValue, int intValue, boolean boolValue) {
/*  52 */     String lcase = stringValue.toLowerCase();
/*  53 */     if ("align".equals(property) && ("fill".equals(lcase.trim()) || lcase.matches("^(top|middle|bottom) (left|centre|right)$")))
/*     */     {
/*  55 */       setProperty(property, lcase);
/*     */     }
/*     */     
/*  58 */     super.setPropertyWithValidation(property, stringValue, intValue, boolValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update() {
/*  64 */     if (!this.updating) {
/*     */       
/*  66 */       this.updating = true;
/*  67 */       setupAlignment();
/*     */     } 
/*     */     
/*  70 */     this.updating = false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setupAlignment() {
/*  75 */     String alignment = getProperty("align", this.defaultAlignment).toLowerCase();
/*     */     
/*  77 */     if (alignment.contains("fill")) {
/*     */       
/*  79 */       this.hAlign = 32;
/*  80 */       this.vAlign = 32;
/*  81 */       setProperty("align", "fill");
/*     */       
/*     */       return;
/*     */     } 
/*  85 */     if (alignment.contains("right")) this.hAlign = 16; 
/*  86 */     if (alignment.contains("centre")) this.hAlign = 8; 
/*  87 */     if (alignment.contains("left")) this.hAlign = 4; 
/*  88 */     if (alignment.contains("bottom")) this.vAlign = 2; 
/*  89 */     if (alignment.contains("middle")) this.vAlign = 1; 
/*  90 */     if (alignment.contains("top")) this.vAlign = 0;
/*     */     
/*  92 */     alignment = ((this.vAlign == 2) ? "bottom " : ((this.vAlign == 1) ? "middle " : "top ")) + ((this.hAlign == 16) ? "right" : ((this.hAlign == 8) ? "centre" : "left"));
/*     */ 
/*     */     
/*  95 */     setProperty("align", alignment);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getXPosition(Rectangle boundingBox, float contentWidth) {
/* 100 */     if (this.hAlign == 16)
/*     */     {
/* 102 */       return (boundingBox.x + boundingBox.width - this.padding) - contentWidth;
/*     */     }
/* 104 */     if (this.hAlign == 8)
/*     */     {
/* 106 */       return (boundingBox.x + boundingBox.width / 2) - contentWidth / 2.0F;
/*     */     }
/*     */ 
/*     */     
/* 110 */     return (boundingBox.x + this.padding);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getYPosition(Rectangle boundingBox, float contentHeight) {
/* 116 */     if (this.vAlign == 2)
/*     */     {
/* 118 */       return (boundingBox.y + boundingBox.height - this.padding) - contentHeight;
/*     */     }
/* 120 */     if (this.vAlign == 1)
/*     */     {
/* 122 */       return (boundingBox.y + boundingBox.height / 2) - contentHeight / 2.0F;
/*     */     }
/*     */ 
/*     */     
/* 126 */     return (boundingBox.y + this.padding);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\DesignableGuiControlAligned.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */