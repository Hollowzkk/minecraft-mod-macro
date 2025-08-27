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
/*    */ import rp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptActionStrip
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionStrip(ScriptContext context) {
/* 24 */     super(context, "strip");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 30 */     ReturnValue retVal = new ReturnValue("");
/*    */     
/* 32 */     if (params.length == 1) {
/*    */       
/* 34 */       String text = rp.a(provider.expand(macro, params[0], false));
/* 35 */       retVal.setString(text);
/*    */     }
/* 37 */     else if (params.length > 1) {
/*    */       
/* 39 */       String variableName = params[0].toLowerCase();
/*    */       
/* 41 */       if (Variable.couldBeString(variableName)) {
/*    */         
/* 43 */         String text = rp.a(provider.expand(macro, params[1], false));
/* 44 */         retVal.setString(text);
/* 45 */         provider.setVariable(macro, variableName, text);
/*    */       } 
/*    */     } 
/*    */     
/* 49 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionStrip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */