/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ 
/*    */ public class ScriptActionIsRunning
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionIsRunning(ScriptContext context) {
/* 17 */     super(context, "isrunning");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 23 */     boolean result = false;
/*    */     
/* 25 */     if (params.length > 0) {
/*    */       
/* 27 */       String value = provider.expand(macro, params[0], false).trim();
/* 28 */       int intValue = ScriptCore.tryParseInt(value, 0);
/*    */       
/* 30 */       for (IMacro.IMacroStatus status : provider.getMacroEngine().getExecutingMacroStatus()) {
/*    */         
/* 32 */         if (value.equalsIgnoreCase(status.getMacro().getDisplayName()) || (intValue > 0 && status.getMacro().getID() == intValue)) {
/*    */           
/* 34 */           result = true;
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/* 40 */     return (IReturnValue)new ReturnValue(result);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionIsRunning.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */