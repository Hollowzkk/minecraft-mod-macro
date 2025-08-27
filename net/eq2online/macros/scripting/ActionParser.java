/*    */ package net.eq2online.macros.scripting;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ActionParser
/*    */ {
/*    */   protected final ScriptContext context;
/*    */   
/*    */   protected ActionParser(ScriptContext context) {
/* 15 */     this.context = context;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   protected static Pattern PATTERN_SCRIPTACTION = Pattern.compile("^([a-z\\_]+)£?\\((.*)\\)$", 2);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 26 */   protected static Pattern PATTERN_DIRECTIVE = Pattern.compile("^([a-z\\_]+)$", 2);
/*    */   
/*    */   public abstract IMacroAction parse(IMacroActionProcessor paramIMacroActionProcessor, String paramString);
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\ActionParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */