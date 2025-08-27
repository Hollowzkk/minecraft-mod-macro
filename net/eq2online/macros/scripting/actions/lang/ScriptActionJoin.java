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
/*    */ public class ScriptActionJoin
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionJoin(ScriptContext context) {
/* 16 */     super(context, "join");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 22 */     ReturnValue retVal = new ReturnValue("");
/*    */     
/* 24 */     if (params.length > 1) {
/*    */       
/* 26 */       String glue = provider.expand(macro, params[0], false);
/* 27 */       String arrayName = provider.expand(macro, params[1], false);
/* 28 */       StringBuilder output = new StringBuilder();
/*    */       
/* 30 */       if (Variable.getValidVariableOrArraySpecifier(arrayName) != null) {
/*    */         
/* 32 */         arrayName = Variable.getValidVariableOrArraySpecifier(arrayName);
/*    */         
/* 34 */         if (provider.getArrayExists(macro, arrayName)) {
/*    */           
/* 36 */           int ubound = provider.getArraySize(macro, arrayName);
/* 37 */           boolean first = true;
/*    */           
/* 39 */           for (int offset = 0; offset < ubound; offset++) {
/*    */             
/* 41 */             if (!first) output.append(glue);  first = false;
/* 42 */             output.append(getArrayElement(provider, macro, arrayName, offset));
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 47 */       retVal.setString(output.toString());
/*    */       
/* 49 */       if (params.length > 2) {
/*    */         
/* 51 */         String varName = provider.expand(macro, params[2], false).toLowerCase();
/* 52 */         provider.setVariable(macro, varName, output.toString());
/*    */       } 
/*    */     } 
/*    */     
/* 56 */     return (IReturnValue)retVal;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getArrayElement(IScriptActionProvider provider, IMacro macro, String arrayName, int offset) {
/* 68 */     Object element = provider.getArrayElement(macro, arrayName, offset);
/*    */     
/* 70 */     if (element == null) return "";
/*    */     
/* 72 */     if (element instanceof Integer) return String.valueOf(element); 
/* 73 */     if (element instanceof Boolean) return ((Boolean)element).booleanValue() ? "True" : "False";
/*    */     
/* 75 */     return element.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionJoin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */