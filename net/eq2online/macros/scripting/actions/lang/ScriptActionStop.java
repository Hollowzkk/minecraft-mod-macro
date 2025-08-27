/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionStop
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionStop(ScriptContext context) {
/* 15 */     super(context, "stop");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 21 */     if (params.length > 0) {
/*    */       
/* 23 */       String string = provider.expand(macro, params[0], false);
/* 24 */       if ("all".equalsIgnoreCase(string) || "*".equals(string))
/*    */       {
/* 26 */         provider.actionStopMacros();
/*    */       }
/*    */       else
/*    */       {
/* 30 */         int keyCode = 0;
/*    */         
/* 32 */         for (int key = 1; key < 255; key++) {
/*    */           
/* 34 */           String keyName = provider.getMacroEngine().getMacroNameForId(key);
/*    */           
/* 36 */           if (keyName != null && keyName.equalsIgnoreCase(string)) {
/*    */             
/* 38 */             keyCode = key;
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/* 43 */         if (keyCode == 0)
/*    */         {
/* 45 */           keyCode = provider.getMacroEngine().getMacroIdForName(string);
/*    */         }
/*    */         
/* 48 */         if (keyCode == 0)
/*    */         {
/* 50 */           keyCode = ScriptCore.tryParseInt(string, 0);
/*    */         }
/*    */         
/* 53 */         if (keyCode > 0)
/*    */         {
/* 55 */           provider.actionStopMacros(macro, keyCode);
/*    */         }
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 61 */       provider.actionStopMacros(macro, macro.getID());
/*    */     } 
/*    */     
/* 64 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionStop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */