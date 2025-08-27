/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import bib;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.core.params.MacroParamStandard;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderNamed;
/*     */ import net.eq2online.macros.interfaces.IMacroParamStorage;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
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
/*     */ public class MacroParams
/*     */ {
/*     */   public static final String ESCAPE = "\\x5C";
/*     */   public static final String INVISIBLE_ESCAPE = "";
/*     */   public static final String PARAM_PREFIX = "\\x24\\x24";
/*     */   public static final String PARAM_REGEX = "(?<![\\x5C])\\x24\\x24";
/*     */   private static List<MacroParamProvider<?>> providers;
/*     */   private static List<Pattern> highlighters;
/*     */   protected final Macros macros;
/*     */   protected final bib mc;
/*     */   protected MacroParamProvider<?> nextParam;
/*     */   protected IMacroParamTarget target;
/*     */   
/*     */   public MacroParams(Macros macros, bib mc, IMacroParamTarget target) {
/*  62 */     this.macros = macros;
/*  63 */     this.mc = mc;
/*  64 */     this.target = target;
/*     */     
/*  66 */     if (providers == null)
/*     */     {
/*  68 */       initProviders(macros, mc);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void initProviders(Macros macros, bib mc) {
/*     */     try {
/*  76 */       providers = MacroParamProvider.getProviders(macros, mc);
/*  77 */       initHighlighters(providers);
/*     */     }
/*  79 */     catch (Exception ex) {
/*     */       
/*  81 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initHighlighters(List<MacroParamProvider<?>> providers2) {
/*  87 */     if (highlighters != null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  92 */     highlighters = new ArrayList<>();
/*     */ 
/*     */     
/*     */     try {
/*  96 */       for (MacroParamProvider<?> provider : providers)
/*     */       {
/*  98 */         highlighters.add(provider.getPattern());
/*     */       }
/*     */       
/* 101 */       Collections.reverse(highlighters);
/*     */     }
/* 103 */     catch (Exception ex) {
/*     */       
/* 105 */       throw new RuntimeException(ex);
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
/*     */   public static String highlightParams(String macro, String prefix, String suffix) {
/* 117 */     for (Pattern highlighter : highlighters)
/*     */     {
/* 119 */       macro = highlighter.matcher(macro).replaceAll(prefix + "$0" + suffix);
/*     */     }
/*     */     
/* 122 */     return macro;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void evaluateParams() {
/* 130 */     this.nextParam = null;
/*     */     
/* 132 */     String script = this.target.getTargetString();
/* 133 */     int minPos = script.length();
/*     */     
/* 135 */     for (MacroParamProvider<?> provider : providers) {
/*     */       
/* 137 */       provider.reset();
/* 138 */       provider.find(script);
/*     */       
/* 140 */       int start = provider.getStart();
/* 141 */       if (start < minPos) {
/*     */         
/* 143 */         this.nextParam = provider;
/* 144 */         minPos = start;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <TItem> MacroParam<TItem> getNextParam() {
/* 152 */     if (hasRemainingParams())
/*     */     {
/* 154 */       return getMacroParam(null, null, (MacroParamProvider)this.nextParam, this.target, this);
/*     */     }
/*     */     
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroParam.Type getNextParameterType() {
/* 167 */     return this.nextParam.getType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getParameterValueFromStore(MacroParamProvider<?> provider) {
/* 177 */     if ((this.target.getIteration() > 1 && getNextParameterType() == MacroParam.Type.NORMAL) || this.target.getParamStore() == null)
/*     */     {
/* 179 */       return "";
/*     */     }
/*     */     
/* 182 */     return this.target.getParamStore().getStoredParam(provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRemainingParams() {
/* 192 */     return (this.nextParam != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeNamedVar(String listName) {
/* 197 */     for (MacroParamProvider<?> provider : providers) {
/*     */       
/* 199 */       if (provider instanceof MacroParamProviderNamed)
/*     */       {
/* 201 */         ((MacroParamProviderNamed)provider).removeNamedVar(listName);
/*     */       }
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
/*     */   public void replaceParam(MacroParam<?> param) {
/* 215 */     if (param.isFirstOccurrenceSupported() && this.target.getParamStore() != null)
/*     */     {
/* 217 */       this.target.getParamStore().setReplaceFirstOccurrenceOnly(param.getType(), param.shouldReplaceFirstOccurrenceOnly());
/*     */     }
/*     */ 
/*     */     
/* 221 */     boolean evalNeeded = param.replace();
/*     */     
/* 223 */     if (evalNeeded)
/*     */     {
/* 225 */       evaluateParams();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <TItem> MacroParam<TItem> getMacroParam(Macros macros, bib mc, MacroParamProvider<TItem> provider, IMacroParamTarget target, MacroParams params) {
/* 232 */     IMacroParamStorage paramStore = target.getParamStore();
/*     */ 
/*     */     
/*     */     try {
/* 236 */       MacroParam<TItem> newParam = provider.getMacroParam(target, params);
/* 237 */       return initParam(paramStore, newParam);
/*     */     }
/* 239 */     catch (Exception ex) {
/*     */       
/* 241 */       Log.printStackTrace(ex);
/*     */ 
/*     */       
/* 244 */       MacroParamStandard<TItem> newParam = new MacroParamStandard(macros, mc, provider.getType(), target, params, provider);
/* 245 */       return initParam(paramStore, (MacroParam<?>)newParam);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private <TItem> MacroParam<TItem> initParam(IMacroParamStorage paramStore, MacroParam<?> newParam) {
/* 251 */     if (newParam.isFirstOccurrenceSupported() && paramStore != null)
/*     */     {
/* 253 */       newParam.setReplaceFirstOccurrenceOnly(paramStore.shouldReplaceFirstOccurrenceOnly(newParam.getType()));
/*     */     }
/*     */     
/* 256 */     return (MacroParam)newParam;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\MacroParams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */