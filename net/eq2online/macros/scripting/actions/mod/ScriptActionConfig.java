/*    */ package net.eq2online.macros.scripting.actions.mod;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptActionConfig
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionConfig(ScriptContext context) {
/* 23 */     super(context, "config");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 29 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 35 */     if (params.length > 0) {
/*    */       
/* 37 */       boolean verbose = false;
/* 38 */       if (params.length > 1) {
/*    */         
/* 40 */         String param = provider.expand(macro, params[1], false);
/* 41 */         verbose = ("1".equals(param) || "true".equalsIgnoreCase(param));
/*    */       } 
/*    */       
/* 44 */       String oldConfig = provider.actionSwitchConfig(provider.expand(macro, params[0], false), verbose);
/*    */       
/* 46 */       if (macro.getState("oldConfig") == null)
/*    */       {
/* 48 */         macro.setState("oldConfig", oldConfig);
/*    */       
/*    */       }
/*    */     
/*    */     }
/* 53 */     else if (macro.getState("oldConfig") != null) {
/*    */       
/* 55 */       provider.actionSwitchConfig(macro.getState("oldConfig").toString(), false);
/*    */     } 
/*    */ 
/*    */     
/* 59 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\mod\ScriptActionConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */