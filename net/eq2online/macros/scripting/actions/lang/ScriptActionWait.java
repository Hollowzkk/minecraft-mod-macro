/*     */ package net.eq2online.macros.scripting.actions.lang;
/*     */ 
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScriptActionWait
/*     */   extends ScriptAction
/*     */ {
/*     */   public ScriptActionWait(ScriptContext context) {
/*  27 */     super(context, "wait");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/*  33 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExecuteNow(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  40 */     if (instance.getState() == null) {
/*     */       
/*  42 */       if (params.length > 0) {
/*     */         
/*  44 */         String timeoutParam = provider.expand(macro, params[0], false).toLowerCase();
/*  45 */         long multiplier = 1000L;
/*     */         
/*  47 */         if (timeoutParam.endsWith("t")) {
/*     */           
/*  49 */           timeoutParam = timeoutParam.substring(0, timeoutParam.length() - 1);
/*  50 */           Integer ticks = Integer.valueOf(ScriptCore.tryParseInt(timeoutParam, 0));
/*     */           
/*  52 */           if (ticks.intValue() > 0)
/*     */           {
/*  54 */             instance.setState(ticks);
/*  55 */             return false;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/*  60 */           if (timeoutParam.endsWith("ms")) {
/*     */             
/*  62 */             timeoutParam = timeoutParam.substring(0, timeoutParam.length() - 2);
/*  63 */             multiplier = 1L;
/*     */           } 
/*     */ 
/*     */           
/*  67 */           long timeout = ScriptCore.tryParseLong(timeoutParam, 0L);
/*  68 */           if (timeout > 0L)
/*     */           {
/*  70 */             instance.setState(new Long(System.currentTimeMillis() + timeout * multiplier));
/*  71 */             return false;
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/*  78 */       if (instance.getState() instanceof Long)
/*     */       {
/*  80 */         return (System.currentTimeMillis() >= ((Long)instance.getState()).longValue());
/*     */       }
/*  82 */       if (instance.getState() instanceof Integer) {
/*     */         
/*  84 */         int ticks = ((Integer)instance.getState()).intValue() - 1;
/*     */         
/*  86 */         if (ticks > 0) {
/*     */           
/*  88 */           instance.setState(Integer.valueOf(ticks));
/*  89 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 100 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionWait.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */