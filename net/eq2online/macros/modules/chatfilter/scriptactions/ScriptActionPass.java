/*    */ package net.eq2online.macros.modules.chatfilter.scriptactions;
/*    */ 
/*    */ import net.eq2online.macros.event.providers.OnSendChatMessageProvider;
/*    */ import net.eq2online.macros.modules.chatfilter.ChatFilterMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionPass
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionPass(ScriptContext context) {
/* 17 */     super(context, "pass");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 23 */     if (macro instanceof ChatFilterMacro) {
/*    */       
/* 25 */       ChatFilterMacro chatFilter = (ChatFilterMacro)macro;
/*    */       
/* 27 */       chatFilter.pass = true;
/* 28 */       chatFilter.kill();
/*    */     }
/* 30 */     else if (macro.isSynchronous()) {
/*    */       
/* 32 */       IVariableProvider variableProvider = macro.getContext().getVariableProvider();
/* 33 */       if (variableProvider instanceof OnSendChatMessageProvider)
/*    */       {
/* 35 */         ((OnSendChatMessageProvider)variableProvider).setPass(provider, true);
/*    */       }
/*    */     } 
/*    */     
/* 39 */     return null;
/*    */   }
/*    */   
/*    */   public void onInit() {}
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\modules\chatfilter\scriptactions\ScriptActionPass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */