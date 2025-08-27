/*    */ package net.eq2online.macros.scripting.parser;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import net.eq2online.macros.scripting.ActionParser;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*    */ 
/*    */ 
/*    */ public class ActionParserDirective
/*    */   extends ActionParserAbstract
/*    */ {
/*    */   public ActionParserDirective(ScriptContext context) {
/* 13 */     super(context);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMacroAction parse(IMacroActionProcessor actionProcessor, String scriptEntry) {
/* 19 */     Matcher directiveMatcher = ActionParser.PATTERN_DIRECTIVE.matcher(scriptEntry);
/* 20 */     if (directiveMatcher.matches())
/*    */     {
/* 22 */       return (IMacroAction)parse(actionProcessor, directiveMatcher.group(1), "", null);
/*    */     }
/*    */     
/* 25 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\parser\ActionParserDirective.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */