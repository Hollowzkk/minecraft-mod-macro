/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.variable.ItemID;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptActionPick
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionPick(ScriptContext context) {
/* 22 */     super(context, "pick");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 28 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 40 */     return "inventory";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 46 */     ReturnValue retVal = new ReturnValue(-1);
/*    */     
/* 48 */     for (int paramIndex = 0; paramIndex < params.length; paramIndex++) {
/*    */       
/* 50 */       String parsedParam = provider.expand(macro, params[paramIndex], false);
/* 51 */       ItemID itemId = tryParseItemID(parsedParam);
/*    */       
/* 53 */       if (itemId.isValid() && provider.actionInventoryPick(itemId.identifier, itemId.damage)) {
/*    */         
/* 55 */         retVal.setString(itemId.identifier);
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 60 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionPick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */