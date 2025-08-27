/*     */ package net.eq2online.macros.scripting.actions.lang;
/*     */ 
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScriptActionDo
/*     */   extends ScriptAction
/*     */ {
/*     */   static final class State
/*     */   {
/*     */     private final String variableName;
/*     */     private final boolean unlimited;
/*     */     private final int total;
/*     */     private int counter;
/*     */     private boolean cancelled;
/*  31 */     private int direction = 1;
/*  32 */     private int offset = 0;
/*  33 */     private int step = 1;
/*     */ 
/*     */     
/*     */     State(int loops) {
/*  37 */       this.variableName = null;
/*  38 */       this.counter = 0;
/*  39 */       this.total = loops;
/*  40 */       this.unlimited = false;
/*     */     }
/*     */ 
/*     */     
/*     */     State(String variableName, int from, int to, int step) {
/*  45 */       this.variableName = variableName;
/*  46 */       this.counter = 0;
/*  47 */       this.offset = from;
/*  48 */       this.step = Math.max(1, Math.abs(step));
/*  49 */       this.unlimited = false;
/*     */       
/*  51 */       if (from == to) {
/*     */         
/*  53 */         this.total = 0;
/*     */       }
/*  55 */       else if (from > to) {
/*     */         
/*  57 */         this.total = from - to;
/*  58 */         this.direction = -1;
/*     */       }
/*     */       else {
/*     */         
/*  62 */         this.total = to - from;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     State() {
/*  68 */       this.variableName = null;
/*  69 */       this.total = 0;
/*  70 */       this.unlimited = true;
/*     */     }
/*     */ 
/*     */     
/*     */     State cancel() {
/*  75 */       this.cancelled = true;
/*  76 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isActive() {
/*  81 */       return !this.cancelled;
/*     */     }
/*     */ 
/*     */     
/*     */     State increment() {
/*  86 */       this.counter += this.step;
/*     */       
/*  88 */       if (!this.unlimited)
/*     */       {
/*  90 */         this.cancelled |= (this.counter > this.total) ? 1 : 0;
/*     */       }
/*     */       
/*  93 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getVariableName() {
/*  98 */       return this.variableName;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getCounter() {
/* 103 */       return this.offset + this.counter * this.direction;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ScriptActionDo(ScriptContext context) {
/* 109 */     super(context, "do");
/*     */   }
/*     */ 
/*     */   
/*     */   protected ScriptActionDo(ScriptContext context, String actionName) {
/* 114 */     super(context, actionName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStackPushOperator() {
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExpectedPopCommands() {
/* 126 */     return I18n.get("script.error.stackhint", new Object[] { this, "LOOP§c, §dUNTIL§c or §dWHILE" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeStackPush(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 132 */     if (params.length > 0) {
/*     */       
/* 134 */       if (instance.getState() == null)
/*     */       {
/*     */         
/* 137 */         int loopCount = ScriptCore.tryParseInt(provider.expand(macro, params[0], false), 0);
/* 138 */         instance.setState(new State(loopCount));
/*     */       }
/*     */     
/* 141 */     } else if (instance.getState() == null) {
/*     */       
/* 143 */       instance.setState(new State());
/*     */     } 
/*     */     
/* 146 */     return ((State)instance.getState()).increment().isActive();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeStackPop(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params, IMacroAction popAction) {
/* 153 */     if (popAction.getAction() instanceof ScriptActionWhile || popAction.getAction() instanceof ScriptActionUntil) {
/*     */       
/* 155 */       State state = (State)instance.getState();
/*     */       
/* 157 */       if (state == null || state.isActive()) {
/*     */         
/* 159 */         boolean result = (provider.getExpressionEvaluator(macro, provider.expand(macro, popAction.getRawParams(), true)).evaluate() != 0);
/*     */         
/* 161 */         if (popAction.getAction() instanceof ScriptActionWhile)
/*     */         {
/* 163 */           result = !result;
/*     */         }
/*     */         
/* 166 */         if (result) {
/*     */           
/* 168 */           if (state == null) {
/*     */             
/* 170 */             state = new State(0);
/* 171 */             instance.setState(state);
/*     */           } 
/*     */           
/* 174 */           state.cancel();
/* 175 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 180 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBreak(IMacroActionProcessor processor, IScriptActionProvider provider, IMacro macro, IMacroAction instance, IMacroAction breakAction) {
/* 187 */     State state = (State)instance.getState();
/* 188 */     if (state != null) {
/*     */       
/* 190 */       state.cancel();
/* 191 */       return true;
/*     */     } 
/*     */     
/* 194 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePoppedBy(IScriptAction action) {
/* 200 */     return action instanceof ScriptActionLoop;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionDo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */