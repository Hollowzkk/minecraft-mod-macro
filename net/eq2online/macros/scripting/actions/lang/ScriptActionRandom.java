/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionRandom
/*    */   extends ScriptAction
/*    */ {
/* 16 */   private static Random rand = new Random();
/*    */ 
/*    */   
/*    */   public ScriptActionRandom(ScriptContext context) {
/* 20 */     super(context, "random");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 26 */     ReturnValue retVal = new ReturnValue(0);
/*    */     
/* 28 */     if (params.length > 0) {
/*    */       
/* 30 */       int min = 0;
/* 31 */       int max = 100;
/*    */       
/* 33 */       if (params.length > 1)
/*    */       {
/* 35 */         max = ScriptCore.tryParseInt(provider.expand(macro, params[1], false), 100);
/*    */       }
/*    */       
/* 38 */       if (params.length > 2)
/*    */       {
/* 40 */         min = ScriptCore.tryParseInt(provider.expand(macro, params[2], false), 0);
/*    */       }
/*    */       
/* 43 */       if (max < min) {
/*    */         
/* 45 */         int swap = min;
/* 46 */         min = max;
/* 47 */         max = swap;
/*    */       } 
/*    */       
/* 50 */       int randomNumber = rand.nextInt(max - min + 1) + min;
/*    */       
/* 52 */       retVal.setInt(randomNumber);
/* 53 */       provider.setVariable(macro, params[0], randomNumber);
/*    */     }
/*    */     else {
/*    */       
/* 57 */       int randomNumber = rand.nextInt(101);
/* 58 */       retVal.setInt(randomNumber);
/*    */     } 
/*    */     
/* 61 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionRandom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */