/*    */ package net.eq2online.macros.modules.chatfilter.scriptactions;
/*    */ 
/*    */ import net.eq2online.console.Log;
/*    */ import net.eq2online.macros.modules.chatfilter.ChatFilterManager;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ public class ScriptActionChatFilter
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionChatFilter(ScriptContext context) {
/* 18 */     super(context, "chatfilter");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 24 */     ChatFilterManager mananger = ChatFilterManager.getInstance();
/* 25 */     if (!(macro instanceof net.eq2online.macros.modules.chatfilter.ChatFilterMacro)) {
/*    */       
/* 27 */       if (params.length > 0) {
/*    */         
/* 29 */         mananger.setEnabled((params[0].equals("1") || params[0].equalsIgnoreCase("on") || params[0].equalsIgnoreCase("true")));
/*    */       }
/*    */       else {
/*    */         
/* 33 */         mananger.setEnabled(!mananger.isEnabled());
/*    */       } 
/*    */       
/* 36 */       Log.info("Chat filter " + (mananger.isEnabled() ? "enabled" : "disabled"));
/*    */     } 
/*    */     
/* 39 */     return (IReturnValue)new ReturnValue(mananger.isEnabled());
/*    */   }
/*    */   
/*    */   public void onInit() {}
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\modules\chatfilter\scriptactions\ScriptActionChatFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */