/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValueArray;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ public class ScriptActionSplit
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionSplit(ScriptContext context) {
/* 19 */     super(context, "split");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 25 */     ReturnValueArray retVal = null;
/*    */     
/* 27 */     if (params.length > 1) {
/*    */       
/* 29 */       String splitter = provider.expand(macro, params[0], false);
/* 30 */       String source = provider.expand(macro, params[1], false);
/*    */       
/* 32 */       List<String> parts = Arrays.asList(source.split(Pattern.quote(splitter)));
/* 33 */       retVal = new ReturnValueArray(false);
/* 34 */       retVal.putStrings(parts);
/*    */       
/* 36 */       if (params.length > 2) {
/*    */         
/* 38 */         String arrayName = provider.expand(macro, params[2], false).toLowerCase();
/* 39 */         provider.clearArray(macro, arrayName);
/*    */         
/* 41 */         for (String part : parts)
/*    */         {
/* 43 */           provider.pushValueToArray(macro, arrayName, part);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 48 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionSplit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */