/*    */ package net.eq2online.macros.scripting.parser;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import net.eq2online.macros.scripting.ActionParser;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*    */ 
/*    */ 
/*    */ public class ActionParserAction
/*    */   extends ActionParserAbstract
/*    */ {
/*    */   public ActionParserAction(ScriptContext context) {
/* 13 */     super(context);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMacroAction parse(IMacroActionProcessor actionProcessor, String scriptEntry) {
/* 19 */     Matcher scriptActionPatternMatcher = ActionParser.PATTERN_SCRIPTACTION.matcher(scriptEntry);
/* 20 */     if (scriptActionPatternMatcher.matches()) {
/*    */       
/* 22 */       String actionName = scriptActionPatternMatcher.group(1);
/* 23 */       String params = scriptActionPatternMatcher.group(2);
/* 24 */       return (IMacroAction)parse(actionProcessor, actionName, params, null);
/*    */     } 
/*    */     
/* 27 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\parser\ActionParserAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */