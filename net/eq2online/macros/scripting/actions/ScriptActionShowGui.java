/*    */ package net.eq2online.macros.scripting.actions;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionShowGui
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionShowGui(ScriptContext context) {
/* 15 */     super(context, "showgui");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 27 */     if (params.length > 0) {
/*    */       
/* 29 */       String screenName = provider.expand(macro, params[0], false);
/* 30 */       String backScreenName = null;
/* 31 */       boolean enableTriggers = (params.length > 2 && ScriptCore.parseBoolean(provider.expand(macro, params[2], false)));
/*    */       
/* 33 */       if (params.length > 1)
/*    */       {
/* 35 */         backScreenName = provider.expand(macro, params[1], false);
/*    */       }
/*    */       
/* 38 */       provider.actionDisplayCustomScreen(screenName, backScreenName, enableTriggers);
/*    */     }
/*    */     else {
/*    */       
/* 42 */       provider.actionDisplayCustomScreen(null, null, false);
/*    */     } 
/*    */     
/* 45 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\ScriptActionShowGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */