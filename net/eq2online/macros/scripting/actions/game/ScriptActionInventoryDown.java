/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptActionInventoryDown
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionInventoryDown(ScriptContext context) {
/* 24 */     super(context, "inventorydown");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 36 */     int count = 1;
/*    */     
/* 38 */     if (params.length > 0) {
/*    */       
/* 40 */       count = ScriptCore.tryParseInt(provider.expand(macro, params[0], false), 0);
/* 41 */       count %= 9;
/* 42 */       if (count < 1) count = 1;
/*    */     
/*    */     } 
/* 45 */     for (int c = 0; c < count; c++)
/*    */     {
/* 47 */       provider.actionInventoryMove(1);
/*    */     }
/*    */     
/* 50 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 59 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 68 */     return "inventory";
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionInventoryDown.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */