/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.core.Macro;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxControlProperties;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxLabelProperties;
/*     */ import net.eq2online.macros.scripting.VariableExpander;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.util.Colour;
/*     */ import net.eq2online.util.Util;
/*     */ import rp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Icon(u = 24, v = 80)
/*     */ public class DesignableGuiControlLabel
/*     */   extends DesignableGuiControlAligned
/*     */ {
/*     */   private String bindingName;
/*     */   private String bindingValue;
/*     */   
/*     */   public DesignableGuiControlLabel(Macros macros, bib mc, int id) {
/*  30 */     super(macros, mc, id);
/*  31 */     this.padding = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initProperties() {
/*  37 */     super.initProperties();
/*     */     
/*  39 */     setProperty("binding", "");
/*  40 */     setProperty("text", "Label text");
/*  41 */     setProperty("colour", "00FF00");
/*  42 */     setProperty("background", "AA000000");
/*  43 */     setProperty("shadow", true);
/*  44 */     setProperty("border", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick(int tickNumber) {
/*  50 */     IScriptActionProvider provider = ScriptContext.MAIN.getScriptActionProvider();
/*     */     
/*  52 */     if (provider != null) {
/*     */       
/*  54 */       this.bindingName = getProperty("binding", "");
/*  55 */       if (!this.bindingName.isEmpty()) {
/*     */         
/*  57 */         if (this.bindingName.indexOf('%') > -1) {
/*     */           
/*  59 */           this.bindingValue = (new VariableExpander(provider, null, this.bindingName, false)).toString();
/*     */         }
/*     */         else {
/*     */           
/*  63 */           this.bindingValue = VariableExpander.expand(provider, null, this.bindingName);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  69 */     this.bindingValue = "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDialogBoxControlProperties getPropertiesDialog(GuiScreenEx parentScreen) {
/*  75 */     return (GuiDialogBoxControlProperties)new GuiDialogBoxLabelProperties(this.mc, parentScreen, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getText() {
/*  80 */     if (!this.bindingName.isEmpty()) {
/*     */       
/*  82 */       String text = getProperty("text", "%%");
/*     */       
/*  84 */       if (text.indexOf("%%") > -1) {
/*     */         
/*  86 */         text = text.replaceAll("%%", Macro.escapeReplacement(this.bindingValue));
/*  87 */         return Util.convertAmpCodes(text);
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     return Util.convertAmpCodes(getProperty("text", getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropertyWithValidation(String property, String stringValue, int intValue, boolean boolValue) {
/*  97 */     if ("text".equals(property) || "binding".equals(property)) setProperty(property, stringValue); 
/*  98 */     if ("shadow".equals(property)) setProperty(property, boolValue); 
/*  99 */     if ("colour".equals(property) || "background".equals(property))
/*     */     {
/* 101 */       setProperty(property, Colour.sanitiseColour(stringValue, "colour".equals(property) ? -16711936 : -1442840576));
/*     */     }
/*     */     
/* 104 */     super.setPropertyWithValidation(property, stringValue, intValue, boolValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update() {
/* 110 */     super.update();
/*     */     
/* 112 */     this.foreColour = Colour.parseColour(getProperty("colour", "00FF00"), -16711936);
/* 113 */     this.backColour = Colour.parseColour(getProperty("background", "AA000000"), -1442840576);
/* 114 */     this.bindingName = getProperty("binding", "");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void draw(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY) {
/* 120 */     if (isVisible()) {
/*     */       
/* 122 */       GL.glAlphaFunc(516, 0.0F);
/* 123 */       drawRect(boundingBox, this.backColour);
/* 124 */       GL.glAlphaFunc(516, 0.1F);
/* 125 */       drawLabel(parent, boundingBox, mouseX, mouseY);
/* 126 */       if (getProperty("border", false))
/*     */       {
/* 128 */         drawRectOutline(boundingBox, this.foreColour, 1.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawWidget(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY, int drawColour) {
/* 136 */     drawLabel(parent, boundingBox, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawLabel(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY) {
/* 144 */     String[] text = Util.convertAmpCodes(getText()).split("\\\\n");
/* 145 */     int[] rowWidths = new int[text.length];
/*     */     
/* 147 */     float textWidth = 0.0F;
/* 148 */     float textHeight = 8.0F;
/*     */     
/* 150 */     for (int row = 0; row < text.length; row++) {
/*     */       
/* 152 */       rowWidths[row] = this.fontRenderer.a(rp.a(text[row])) + 4; textWidth = Math.max(textWidth, (this.fontRenderer.a(rp.a(text[row])) + 4));
/* 153 */       if (row > 0) textHeight += 10.0F;
/*     */     
/*     */     } 
/* 156 */     int yPos = (int)getYPosition(boundingBox, textHeight);
/* 157 */     boolean shadow = getProperty("shadow", true);
/*     */     
/* 159 */     for (int i = 0; i < text.length; i++) {
/*     */       
/* 161 */       int xPos = (int)getXPosition(boundingBox, rowWidths[i]);
/*     */       
/* 163 */       if (shadow) {
/*     */         
/* 165 */         this.fontRenderer.a(text[i] + "§r", xPos, yPos, this.foreColour);
/*     */       }
/*     */       else {
/*     */         
/* 169 */         this.fontRenderer.a(text[i] + "§r", xPos, yPos, this.foreColour);
/*     */       } 
/* 171 */       yPos += 10;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\DesignableGuiControlLabel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */