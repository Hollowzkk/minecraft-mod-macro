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
/*    */ public class ScriptActionSlot
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionSlot(ScriptContext context) {
/* 21 */     super(context, "slot");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 45 */     return "inventory";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 51 */     if (params.length > 0)
/*    */     {
/* 53 */       provider.actionInventorySlot(ScriptCore.tryParseInt(provider.expand(macro, params[0], false), 0));
/*    */     }
/*    */     
/* 56 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */