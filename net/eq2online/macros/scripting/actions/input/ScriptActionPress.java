/*    */ package net.eq2online.macros.scripting.actions.input;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionPress
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionPress(ScriptContext context) {
/* 15 */     super(context, "press");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 39 */     return "input";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 45 */     if (params.length > 0) {
/*    */       
/* 47 */       int keyCode = 0;
/*    */       
/* 49 */       for (int key = 1; key < 255; key++) {
/*    */         
/* 51 */         String keyName = provider.getMacroEngine().getMacroNameForId(key);
/*    */         
/* 53 */         if (keyName != null && keyName.equalsIgnoreCase(params[0])) {
/*    */           
/* 55 */           keyCode = key;
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 60 */       if (keyCode == 0)
/*    */       {
/* 62 */         keyCode = ScriptCore.tryParseInt(params[0], 0);
/*    */       }
/*    */       
/* 65 */       if (keyCode > 0) {
/*    */         
/* 67 */         boolean deep = false;
/*    */         
/* 69 */         if (params.length > 1)
/*    */         {
/* 71 */           deep = (params[1].equalsIgnoreCase("true") || params[1].equals("1"));
/*    */         }
/*    */         
/* 74 */         provider.actionPumpKeyPress(keyCode, deep);
/*    */       } 
/*    */     } 
/*    */     
/* 78 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\input\ScriptActionPress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */