/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxControlProperties;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxProgressBarProperties;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
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
/*     */ @Icon(u = 0, v = 88)
/*     */ public class DesignableGuiControlProgressBar
/*     */   extends DesignableGuiControlRanged
/*     */ {
/*     */   public enum BarStyle
/*     */   {
/*  28 */     HORIZONTAL,
/*  29 */     VERTICAL;
/*     */   }
/*     */   
/*  32 */   protected BarStyle style = BarStyle.HORIZONTAL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DesignableGuiControlProgressBar(Macros macros, bib mc, int id) {
/*  39 */     super(macros, mc, id);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBindable() {
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initProperties() {
/*  51 */     super.initProperties();
/*     */     
/*  53 */     setProperty("expression", "");
/*  54 */     setProperty("style", "horizontal");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropertyWithValidation(String property, String stringValue, int intValue, boolean boolValue) {
/*  60 */     if ("expression".equals(property)) setProperty(property, stringValue); 
/*  61 */     if ("style".equals(property) && stringValue.matches("^(horizontal|vertical)$")) setProperty(property, stringValue);
/*     */     
/*  63 */     super.setPropertyWithValidation(property, stringValue, intValue, boolValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick(int tickNumber) {
/*  69 */     IScriptActionProvider provider = ScriptContext.MAIN.getScriptActionProvider();
/*  70 */     if (provider != null) {
/*     */       
/*  72 */       calcMinMax(provider);
/*  73 */       setValueClamped(provider.getExpressionEvaluator(null, getProperty("expression", "100")).evaluate());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDialogBoxControlProperties getPropertiesDialog(GuiScreenEx parentScreen) {
/*  80 */     return (GuiDialogBoxControlProperties)new GuiDialogBoxProgressBarProperties(this.mc, parentScreen, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update() {
/*  86 */     super.update();
/*     */     
/*  88 */     this.style = getProperty("style", "horizontal").equalsIgnoreCase("vertical") ? BarStyle.VERTICAL : BarStyle.HORIZONTAL;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void draw(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY) {
/*  94 */     if (drawProgressBar(boundingBox, this.backColour, true))
/*     */     {
/*  96 */       drawRectOutline(boundingBox, this.foreColour, 1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawWidget(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY, int drawColour) {
/* 103 */     int fc = this.foreColour;
/* 104 */     this.foreColour = -8355712;
/* 105 */     drawProgressBar(boundingBox, 0, false);
/* 106 */     this.foreColour = fc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean drawProgressBar(Rectangle boundingBox, int backColour, boolean playback) {
/* 117 */     if (playback && !isVisible()) return false; 
/* 118 */     this.e = -this.zIndex;
/*     */     
/* 120 */     if (backColour != 0) {
/*     */       
/* 122 */       GL.glAlphaFunc(516, 0.0F);
/* 123 */       a(boundingBox.x, boundingBox.y, boundingBox.x + boundingBox.width, boundingBox.y + boundingBox.height, backColour);
/* 124 */       GL.glAlphaFunc(516, 0.1F);
/*     */     } 
/*     */     
/* 127 */     float fValue = (this.currentValue - this.currentMin);
/* 128 */     float fSize = (this.currentMax - this.currentMin);
/*     */     
/* 130 */     if (fSize > 0.0F)
/*     */     
/* 132 */     { switch (this.style)
/*     */       
/*     */       { case VERTICAL:
/* 135 */           drawVerticalProgressBar(boundingBox, fValue, fSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 144 */           return true; }  drawHorizontalProgressBar(boundingBox, fValue, fSize); }  return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawHorizontalProgressBar(Rectangle boundingBox, float fValue, float fSize) {
/* 154 */     int padding = (boundingBox.height > this.padding * 2 + 1) ? this.padding : 0;
/*     */     
/* 156 */     float innerWidth = Math.max(boundingBox.width - padding * 2, 1);
/* 157 */     int innerHeight = Math.max(boundingBox.height - padding * 2, 1);
/*     */     
/* 159 */     int barSize = (int)(fValue / fSize * innerWidth);
/*     */     
/* 161 */     a(boundingBox.x + padding, boundingBox.y + padding, boundingBox.x + padding + barSize, boundingBox.y + padding + innerHeight, this.foreColour);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawVerticalProgressBar(Rectangle boundingBox, float fValue, float fSize) {
/* 172 */     int padding = (boundingBox.width > this.padding * 2 + 1) ? this.padding : 0;
/*     */     
/* 174 */     int innerWidth = Math.max(boundingBox.width - padding * 2, 1);
/* 175 */     float innerHeight = Math.max(boundingBox.height - padding * 2, 1);
/*     */     
/* 177 */     int barSize = (int)(innerHeight - fValue / fSize * innerHeight);
/*     */     
/* 179 */     a(boundingBox.x + padding, boundingBox.y + padding + barSize, boundingBox.x + padding + innerWidth, boundingBox.y + padding + (int)innerHeight, this.foreColour);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\DesignableGuiControlProgressBar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */