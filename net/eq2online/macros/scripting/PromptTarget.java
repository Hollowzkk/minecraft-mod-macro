/*     */ package net.eq2online.macros.scripting;
/*     */ 
/*     */ import bib;
/*     */ import blk;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.interfaces.IMacroParamStorage;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PromptTarget
/*     */   implements IMacroParamTarget
/*     */ {
/*     */   private final bib mc;
/*     */   private final IMacro parent;
/*     */   private final String prompt;
/*     */   private final String defaultValue;
/*     */   private final blk previousScreen;
/*     */   private final MacroParams paramProvider;
/*     */   private String content;
/*     */   private boolean completed = false;
/*     */   private boolean success = false;
/*     */   
/*     */   public PromptTarget(Macros macros, bib mc, IMacro parent, String content, String prompt, String defaultValue, blk previousScreen) {
/*  35 */     this.mc = mc;
/*  36 */     this.parent = parent;
/*  37 */     this.prompt = prompt;
/*  38 */     this.defaultValue = defaultValue;
/*  39 */     this.previousScreen = previousScreen;
/*  40 */     this.paramProvider = new MacroParams(macros, mc, this);
/*  41 */     this.content = content.replace("££", "$$");
/*     */     
/*  43 */     compile();
/*     */     
/*  45 */     if (!this.paramProvider.hasRemainingParams()) {
/*     */       
/*  47 */       Log.info("Found no params in \"{0}\"", new Object[] { this.content });
/*  48 */       handleCompleted();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDefaultValue() {
/*  54 */     return this.defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getCompleted() {
/*  59 */     return this.completed;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getSuccess() {
/*  64 */     return this.success;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/*  70 */     return (this.parent != null) ? this.parent.getDisplayName() : "Macro";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/*  79 */     return this.prompt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRemainingParams() {
/*  85 */     return this.paramProvider.hasRemainingParams();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <TItem> MacroParam<TItem> getNextParam() {
/*  91 */     return this.paramProvider.getNextParam();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIteration() {
/*  97 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIterationString() {
/* 103 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void compile() {
/* 109 */     this.paramProvider.evaluateParams();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recompile() {
/* 115 */     compile();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTargetString() {
/* 121 */     return this.content;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetString(String newString) {
/* 127 */     this.content = newString;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroParamStorage getParamStore() {
/* 133 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleCompleted() {
/* 139 */     this.mc.a(this.previousScreen);
/* 140 */     this.completed = true;
/* 141 */     this.success = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleCancelled() {
/* 147 */     this.mc.a(this.previousScreen);
/* 148 */     this.completed = true;
/* 149 */     this.success = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\PromptTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */