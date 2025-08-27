/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxButtonProperties;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxControlProperties;
/*     */ import net.eq2online.util.Colour;
/*     */ import net.eq2online.util.Util;
/*     */ import rp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Icon(u = 0, v = 80)
/*     */ public class DesignableGuiControlButton
/*     */   extends DesignableGuiControl
/*     */ {
/*     */   private int hoverColour;
/*     */   
/*     */   protected DesignableGuiControlButton(Macros macros, bib mc, int id) {
/*  24 */     super(macros, mc, id);
/*  25 */     this.padding = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initProperties() {
/*  31 */     super.initProperties();
/*     */     
/*  33 */     setProperty("text", "Button Text");
/*  34 */     setProperty("hide", false);
/*  35 */     setProperty("sticky", false);
/*  36 */     setProperty("colour", "FF00FF00");
/*  37 */     setProperty("background", "B0000000");
/*  38 */     setProperty("hotkey", 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBindable() {
/*  44 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/*  50 */     return ((!getProperty("hide", false) || this.macros.isMacroBound(this.id, true)) && getProperty("visible", true));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean dispatchOnClick() {
/*  56 */     return !getProperty("sticky", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropertyWithValidation(String property, String stringValue, int intValue, boolean boolValue) {
/*  62 */     if ("hotkey".equals(property)) setProperty(property, intValue); 
/*  63 */     if ("text".equals(property)) setProperty(property, stringValue); 
/*  64 */     if ("hide".equals(property) || "sticky".equals(property)) setProperty(property, boolValue); 
/*  65 */     if ("colour".equals(property) || "background".equals(property))
/*     */     {
/*  67 */       setProperty(property, Colour.sanitiseColour(stringValue, "colour".equals(property) ? -16711936 : -1442840576));
/*     */     }
/*     */     
/*  70 */     super.setPropertyWithValidation(property, stringValue, intValue, boolValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getText() {
/*  75 */     return getProperty("text", "Button Text");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update() {
/*  86 */     this.foreColour = Colour.parseColour(getProperty("colour", "FF00FF00"), -16711936);
/*  87 */     this.backColour = Colour.parseColour(getProperty("background", "B0000000"), -1442840576);
/*  88 */     this.hoverColour = DesignableGuiControl.brightenColour(this.foreColour, 128);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick(int tickNumber) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void draw(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY) {
/*  99 */     int foreColour = boundingBox.contains(mouseX, mouseY) ? this.hoverColour : this.foreColour;
/*     */     
/* 101 */     if (drawButton(boundingBox, foreColour, this.backColour, true))
/*     */     {
/* 103 */       drawRectOutline(boundingBox, foreColour, 1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawWidget(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY, int drawColour) {
/* 110 */     drawButton(boundingBox, this.foreColour, 0, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean drawButton(Rectangle boundingBox, int foreColour, int backColour, boolean playback) {
/* 119 */     if (playback && !isVisible())
/*     */     {
/* 121 */       return false;
/*     */     }
/*     */     
/* 124 */     this.e = -this.zIndex;
/* 125 */     if (backColour != 0) {
/*     */       
/* 127 */       GL.glAlphaFunc(516, 0.0F);
/* 128 */       a(boundingBox.x, boundingBox.y, boundingBox.x + boundingBox.width, boundingBox.y + boundingBox.height, backColour);
/* 129 */       GL.glAlphaFunc(516, 0.1F);
/*     */     } 
/*     */     
/* 132 */     GL.glPushMatrix();
/*     */     
/* 134 */     String[] text = Util.convertAmpCodes(getText()).split("\\\\n");
/*     */     
/* 136 */     float textWidth = 0.0F;
/* 137 */     float textHeight = 8.0F;
/*     */     
/* 139 */     for (int row = 0; row < text.length; row++) {
/*     */       
/* 141 */       textWidth = Math.max(textWidth, (this.fontRenderer.a(rp.a(text[row])) + 4));
/* 142 */       if (row > 0) textHeight += 10.0F;
/*     */     
/*     */     } 
/* 145 */     if (textWidth > (boundingBox.width - this.padding * 2)) {
/*     */       
/* 147 */       float scaleFactor = boundingBox.width / textWidth;
/* 148 */       GL.glTranslatef((boundingBox.x + this.padding), boundingBox.y + (boundingBox.height - textHeight * scaleFactor) / 2.0F, 0.0F);
/* 149 */       GL.glScalef(scaleFactor, scaleFactor, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/* 153 */       GL.glTranslatef(boundingBox.x + (boundingBox.width - textWidth) / 2.0F, boundingBox.y + (boundingBox.height - textHeight) / 2.0F, 0.0F);
/*     */     } 
/*     */     
/* 156 */     int offset = 0;
/*     */     
/* 158 */     for (int i = 0; i < text.length; i++) {
/*     */       
/* 160 */       this.fontRenderer.a(text[i], 0, offset, foreColour);
/* 161 */       offset += 10;
/*     */     } 
/*     */     
/* 164 */     GL.glPopMatrix();
/*     */     
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDialogBoxControlProperties getPropertiesDialog(GuiScreenEx parentScreen) {
/* 172 */     return (GuiDialogBoxControlProperties)new GuiDialogBoxButtonProperties(this.mc, parentScreen, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\DesignableGuiControlButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */