/*    */ package net.eq2online.macros.gui.designable;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.util.Colour;
/*    */ 
/*    */ public abstract class DesignableGuiControlRanged extends DesignableGuiControl {
/*    */   protected boolean calcMin;
/*    */   protected boolean calcMax;
/*    */   protected int currentMin;
/*    */   protected int currentMax;
/*    */   protected int currentValue;
/*    */   
/*    */   public DesignableGuiControlRanged(Macros macros, bib mc, int id) {
/* 17 */     super(macros, mc, id);
/* 18 */     this.padding = 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initProperties() {
/* 24 */     super.initProperties();
/*    */     
/* 26 */     setProperty("min", 0);
/* 27 */     setProperty("max", 100);
/* 28 */     setProperty("calcmin", false);
/* 29 */     setProperty("calcmax", false);
/* 30 */     setProperty("colour", "00FF00");
/* 31 */     setProperty("background", "AA000000");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPropertyWithValidation(String property, String stringValue, int intValue, boolean boolValue) {
/* 37 */     if ("min".equals(property) || "max".equals(property)) setProperty(property, intValue); 
/* 38 */     if ("calcmin".equals(property) || "calcmax".equals(property)) setProperty(property, boolValue); 
/* 39 */     if ("colour".equals(property) || "background".equals(property)) {
/*    */       
/* 41 */       int defaultColour = "colour".equals(property) ? -16711936 : -1442840576;
/* 42 */       setProperty(property, Colour.sanitiseColour(stringValue, defaultColour));
/*    */     } 
/*    */     
/* 45 */     super.setPropertyWithValidation(property, stringValue, intValue, boolValue);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void update() {
/* 51 */     this.calcMin = getProperty("calcmin", false);
/* 52 */     this.calcMax = getProperty("calcmax", false);
/*    */     
/* 54 */     this.foreColour = Colour.parseColour(getProperty("colour", "FF00FF00"), -16711936);
/* 55 */     this.backColour = Colour.parseColour(getProperty("background", "B0000000"), -1442840576);
/*    */     
/* 57 */     calcMinMax(ScriptContext.MAIN.getScriptActionProvider());
/* 58 */     setValueClamped(this.currentValue);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final void setValueClamped(int value) {
/* 63 */     this.currentValue = Math.min(Math.max(value, this.currentMin), this.currentMax);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void calcMinMax(IScriptActionProvider provider) {
/* 71 */     if (provider != null) {
/*    */       
/* 73 */       this
/*    */         
/* 75 */         .currentMin = this.calcMin ? provider.getExpressionEvaluator(null, getProperty("min", "0")).evaluate() : getProperty("min", 0);
/* 76 */       this
/*    */         
/* 78 */         .currentMax = this.calcMax ? provider.getExpressionEvaluator(null, getProperty("max", "100")).evaluate() : getProperty("max", 100);
/*    */     }
/*    */     else {
/*    */       
/* 82 */       this.currentMin = getProperty("min", 0);
/* 83 */       this.currentMax = getProperty("max", 100);
/*    */     } 
/*    */     
/* 86 */     if (this.currentMax <= this.currentMin) this.currentMax = this.currentMin + 1; 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\DesignableGuiControlRanged.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */