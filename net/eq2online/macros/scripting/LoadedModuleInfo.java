/*     */ package net.eq2online.macros.scripting;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptedIterator;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
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
/*     */ public class LoadedModuleInfo
/*     */ {
/*     */   public final File module;
/*     */   public final String name;
/*  33 */   private int customActionCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private int customVariableProviderCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private int customIteratorCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   private int customEventProviderCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private List<String> actions = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private List<String> providers = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private List<String> iterators = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private List<String> eventProviders = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoadedModuleInfo(File module) {
/*  75 */     this(module, module.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoadedModuleInfo(String name) {
/*  83 */     this(null, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LoadedModuleInfo(File module, String name) {
/*  91 */     this.module = module;
/*  92 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScriptAction addAction(IScriptAction action) {
/* 100 */     if (action != null && !this.actions.contains(action.toString())) {
/*     */       
/* 102 */       this.customActionCount++;
/* 103 */       this.actions.add(action.toString());
/*     */     } 
/*     */     
/* 106 */     return action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IVariableProvider addProvider(IVariableProvider provider) {
/* 114 */     if (provider != null && !this.providers.contains(provider.getClass().getSimpleName())) {
/*     */       
/* 116 */       this.customVariableProviderCount++;
/* 117 */       this.providers.add(provider.getClass().getSimpleName());
/*     */     } 
/*     */     
/* 120 */     return provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScriptedIterator addIterator(IScriptedIterator iterator) {
/* 128 */     if (iterator != null && !this.iterators.contains(iterator.getClass().getSimpleName())) {
/*     */       
/* 130 */       this.customIteratorCount++;
/* 131 */       this.iterators.add(iterator.getClass().getSimpleName());
/*     */     } 
/*     */     
/* 134 */     return iterator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroEventProvider addEventProvider(IMacroEventProvider provider) {
/* 142 */     if (provider != null && !this.eventProviders.contains(provider.getClass().getSimpleName())) {
/*     */       
/* 144 */       this.customEventProviderCount++;
/* 145 */       this.eventProviders.add(provider.getClass().getSimpleName());
/*     */     } 
/*     */     
/* 148 */     return provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStatus() {
/* 156 */     if (this.customActionCount + this.customVariableProviderCount > 0)
/* 157 */       Log.info("API Loaded module {0} found {1} custom action(s) {2} new variable provider(s) {3} new iterator(s), {4} event provider(s)", new Object[] { this.name, Integer.valueOf(this.customActionCount), Integer.valueOf(this.customVariableProviderCount), Integer.valueOf(this.customIteratorCount), Integer.valueOf(this.customEventProviderCount) }); 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\LoadedModuleInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */