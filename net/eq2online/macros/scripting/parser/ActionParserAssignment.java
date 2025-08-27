/*    */ package net.eq2online.macros.scripting.parser;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import net.eq2online.macros.scripting.ActionParser;
/*    */ import net.eq2online.macros.scripting.Variable;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*    */ 
/*    */ 
/*    */ public class ActionParserAssignment
/*    */   extends ActionParserAbstract
/*    */ {
/*    */   public ActionParserAssignment(ScriptContext context) {
/* 14 */     super(context);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMacroAction parse(IMacroActionProcessor actionProcessor, String scriptEntry) {
/* 20 */     String actionName = "ASSIGN";
/* 21 */     String varName = null;
/* 22 */     int equals = scriptEntry.indexOf('=');
/* 23 */     if (equals > -1) {
/*    */       
/* 25 */       int colon = scriptEntry.indexOf(':');
/* 26 */       if (colon > -1 && colon == equals - 1) {
/*    */         
/* 28 */         actionName = "SET";
/*    */       }
/*    */       else {
/*    */         
/* 32 */         colon = equals;
/*    */       } 
/*    */       
/* 35 */       varName = scriptEntry.substring(0, colon).trim();
/* 36 */       if (Variable.isValidVariableOrArraySpecifier(varName)) {
/*    */         
/* 38 */         String expression = scriptEntry.substring(equals + 1).trim();
/* 39 */         Matcher actionMatcher = ActionParser.PATTERN_SCRIPTACTION.matcher(expression);
/*    */         
/* 41 */         if (actionMatcher.matches())
/*    */         {
/* 43 */           return (IMacroAction)parse(actionProcessor, actionMatcher.group(1), actionMatcher.group(2), varName);
/*    */         }
/*    */         
/* 46 */         if (expression.trim().startsWith("\"") && expression.trim().endsWith("\"")) {
/*    */           
/* 48 */           StringBuilder rawParams = new StringBuilder();
/* 49 */           ScriptCore.tokenize(expression, ' ', '"', '"', '\\', rawParams);
/* 50 */           expression = (rawParams.length() > 0) ? rawParams.substring(1) : "";
/*    */         } 
/*    */         
/* 53 */         return (IMacroAction)getInstance(actionProcessor, actionName, expression, expression, new String[] { varName, expression }, null);
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 58 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\parser\ActionParserAssignment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */