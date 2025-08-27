/*     */ package net.eq2online.macros.scripting;
/*     */ 
/*     */ import bib;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptedIterator;
/*     */ 
/*     */ public abstract class ScriptedIterator
/*     */   implements IScriptedIterator
/*     */ {
/*  16 */   protected final bib mc = bib.z();
/*     */   
/*     */   protected final IScriptActionProvider provider;
/*     */   
/*     */   protected final IMacro macro;
/*     */   
/*  22 */   private final List<Map<String, Object>> items = new ArrayList<>();
/*     */   
/*  24 */   private int pointer = 0;
/*     */   
/*     */   private boolean cancelled;
/*     */   
/*     */   private Map<String, Object> current;
/*     */ 
/*     */   
/*     */   public ScriptedIterator(IScriptActionProvider provider, IMacro macro) {
/*  32 */     this.provider = provider;
/*  33 */     this.macro = macro;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ScriptedIterator begin() {
/*  44 */     if (this.current != null)
/*     */     {
/*  46 */       throw new IllegalStateException("Previous loop not closed calling begin() in " + getClass().getSimpleName());
/*     */     }
/*     */     
/*  49 */     this.current = new HashMap<>();
/*  50 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final ScriptedIterator add(String key, Object value) {
/*  55 */     if (this.current == null)
/*     */     {
/*  57 */       throw new IllegalStateException("Loop not opened calling add() in " + getClass().getSimpleName());
/*     */     }
/*     */     
/*  60 */     if (key != null && value != null)
/*     */     {
/*  62 */       this.current.put(key, value);
/*     */     }
/*     */     
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final ScriptedIterator end() {
/*  70 */     if (this.current != null && !this.current.isEmpty())
/*     */     {
/*  72 */       this.items.add(this.current);
/*     */     }
/*     */     
/*  75 */     this.current = null;
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/*  85 */     return (!this.cancelled && this.pointer < this.items.size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void increment() {
/*  94 */     this.pointer++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 103 */     this.pointer = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void terminate() {
/* 109 */     this.cancelled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVariables(boolean clock) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getVariables() {
/* 120 */     return ((Map)this.items.get(this.pointer)).keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/* 126 */     if (this.pointer < this.items.size()) {
/*     */       
/* 128 */       Map<String, Object> currentScope = this.items.get(this.pointer);
/* 129 */       return currentScope.get(variableName);
/*     */     } 
/*     */     
/* 132 */     return null;
/*     */   }
/*     */   
/*     */   public void onInit() {}
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\ScriptedIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */