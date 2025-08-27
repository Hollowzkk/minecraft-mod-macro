/*    */ package net.eq2online.macros.scripting.actions.option;
/*    */ 
/*    */ import bhy;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionBind
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionBind(ScriptContext context) {
/* 16 */     super(context, "bind");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 22 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 28 */     if (params.length > 1) {
/*    */       
/* 30 */       int keyBindId = -1;
/* 31 */       int keyCode = 0;
/*    */       
/* 33 */       bhy[] keyBindings = this.mc.t.as;
/*    */       int keyBindIndex;
/* 35 */       for (keyBindIndex = 0; keyBindIndex < keyBindings.length; keyBindIndex++) {
/*    */         
/* 37 */         if (keyBindings[keyBindIndex].h().equalsIgnoreCase(params[0])) {
/*    */           
/* 39 */           keyBindId = keyBindIndex;
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 44 */       if (keyBindId == -1)
/*    */       {
/* 46 */         for (keyBindIndex = 0; keyBindIndex < keyBindings.length; keyBindIndex++) {
/*    */           
/* 48 */           if (keyBindings[keyBindIndex].h().toLowerCase().contains(params[0].toLowerCase())) {
/*    */             
/* 50 */             keyBindId = keyBindIndex;
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/*    */       }
/* 56 */       for (int key = 1; key < 255; key++) {
/*    */         
/* 58 */         String keyName = provider.getMacroEngine().getMacroNameForId(key);
/*    */         
/* 60 */         if (keyName != null && keyName.equalsIgnoreCase(params[1])) {
/*    */           
/* 62 */           keyCode = key;
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 67 */       if (keyCode == 0)
/*    */       {
/* 69 */         keyCode = ScriptCore.tryParseInt(params[1], 0);
/*    */       }
/*    */       
/* 72 */       if (keyBindId > -1 && keyCode > 0) {
/*    */         
/*    */         try {
/*    */           
/* 76 */           provider.actionBindKey(keyBindId, keyCode);
/*    */         }
/* 78 */         catch (Exception exception) {}
/*    */       }
/*    */     } 
/*    */     
/* 82 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionBind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */