/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionSetLabel
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionSetLabel(ScriptContext context) {
/* 14 */     super(context, "setlabel");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 20 */     if (params.length > 1) {
/*    */       
/* 22 */       String labelName = provider.expand(macro, params[0], false);
/* 23 */       String labelText = provider.expand(macro, params[1], false).replace('§', '&');
/* 24 */       String labelBinding = null;
/*    */       
/* 26 */       if (params.length > 2)
/*    */       {
/* 28 */         labelBinding = provider.expand(macro, params[2], false).replace('§', '&');
/*    */       }
/*    */       
/* 31 */       provider.actionSetLabel(labelName, labelText, labelBinding);
/*    */     } 
/*    */     
/* 34 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionSetLabel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */