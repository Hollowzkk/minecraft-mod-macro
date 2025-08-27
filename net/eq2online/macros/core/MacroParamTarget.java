/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import bib;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.util.Util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MacroParamTarget
/*     */   extends MacroIncludeProcessor
/*     */   implements IMacroParamTarget
/*     */ {
/*     */   protected final bib mc;
/*     */   protected final MacroParams params;
/*     */   private int iteration;
/*     */   
/*     */   public MacroParamTarget(Macros macros, bib minecraft, ScriptContext scriptContext) {
/*  22 */     super(macros, minecraft, scriptContext);
/*     */     
/*  24 */     this.mc = minecraft;
/*  25 */     this.params = new MacroParams(macros, minecraft, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recompile() {
/*  32 */     compile();
/*  33 */     this.iteration = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void compile() {
/*  39 */     compileMacro();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void compileMacro() {
/*  47 */     String macro = getTargetString();
/*  48 */     macro = compileMacro(macro);
/*  49 */     setTargetString(macro);
/*     */ 
/*     */     
/*  52 */     this.params.evaluateParams();
/*     */ 
/*     */     
/*  55 */     this.iteration++;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String compileMacro(String macro) {
/*  60 */     macro = processIncludes(macro);
/*  61 */     macro = processEscapes(macro);
/*  62 */     macro = processPrompts(macro);
/*  63 */     return macro;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/*  72 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getIteration() {
/*  83 */     return this.iteration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getIterationString() {
/*  94 */     return this.iteration + Util.getOrdinalSuffix(this.iteration);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRemainingParams() {
/* 100 */     return this.params.hasRemainingParams();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <TItem> MacroParam<TItem> getNextParam() {
/* 106 */     return this.params.getNextParam();
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\MacroParamTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */