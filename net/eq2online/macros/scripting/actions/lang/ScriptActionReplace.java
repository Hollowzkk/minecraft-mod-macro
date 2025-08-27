/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.Variable;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionReplace
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionReplace(ScriptContext context) {
/* 16 */     this(context, "replace");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ScriptActionReplace(ScriptContext context, String actionName) {
/* 21 */     super(context, actionName);
/*    */   }
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*    */     ReturnValue returnValue;
/* 27 */     IReturnValue retVal = null;
/*    */     
/* 29 */     if (params.length > 1) {
/*    */       
/* 31 */       String variableName = params[0].toLowerCase();
/*    */       
/* 33 */       if (Variable.couldBeString(variableName)) {
/*    */         
/* 35 */         String sourceString = provider.expand(macro, "%" + variableName + "%", false);
/* 36 */         String searchFor = provider.expand(macro, params[1], false);
/* 37 */         String replaceWith = "";
/*    */         
/* 39 */         if (params.length > 2)
/*    */         {
/* 41 */           replaceWith = provider.expand(macro, params[2], false);
/*    */         }
/*    */         
/* 44 */         String result = doReplacement(provider, macro, instance, sourceString, searchFor, replaceWith);
/*    */         
/* 46 */         if (instance.hasOutVar()) {
/*    */           
/* 48 */           returnValue = new ReturnValue(result);
/*    */         }
/*    */         else {
/*    */           
/* 52 */           provider.setVariable(macro, variableName, result);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 57 */     return (IReturnValue)returnValue;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String doReplacement(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String sourceString, String searchFor, String replaceWith) {
/* 63 */     return sourceString.replace(searchFor, replaceWith);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionReplace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */