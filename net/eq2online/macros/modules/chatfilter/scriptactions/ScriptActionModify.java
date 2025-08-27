/*    */ package net.eq2online.macros.modules.chatfilter.scriptactions;
/*    */ 
/*    */ import net.eq2online.macros.modules.chatfilter.ChatFilterMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.util.Util;
/*    */ 
/*    */ public class ScriptActionModify
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionModify(ScriptContext context) {
/* 16 */     super(context, "modify");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 22 */     if (macro instanceof ChatFilterMacro) {
/*    */       
/* 24 */       ChatFilterMacro chatFilter = (ChatFilterMacro)macro;
/* 25 */       chatFilter.newMessage = Util.convertAmpCodes(provider.expand(macro, rawParams, false));
/*    */     } 
/*    */     
/* 28 */     return null;
/*    */   }
/*    */   
/*    */   public void onInit() {}
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\modules\chatfilter\scriptactions\ScriptActionModify.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */