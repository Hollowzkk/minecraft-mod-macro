/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import net.eq2online.macros.scripting.Variable;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import net.eq2online.macros.scripting.variable.ItemID;
/*    */ 
/*    */ public class ScriptActionGetSlot
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionGetSlot(ScriptContext context) {
/* 18 */     super(context, "getslot");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 26 */     ReturnValue retVal = new ReturnValue(-1);
/*    */     
/* 28 */     if (params.length > 1) {
/*    */       
/* 30 */       String varName = provider.expand(macro, params[1], false).toLowerCase();
/* 31 */       if (!Variable.couldBeInt(varName)) return (IReturnValue)retVal;
/*    */       
/* 33 */       int slotContaining = findItem(provider, macro, provider.expand(macro, params[0], false), (params.length > 2) ? provider
/* 34 */           .expand(macro, params[2], false) : null);
/* 35 */       if (slotContaining > -1) {
/*    */         
/* 37 */         provider.setVariable(macro, varName, slotContaining);
/* 38 */         retVal.setInt(slotContaining);
/* 39 */         return (IReturnValue)retVal;
/*    */       } 
/*    */       
/* 42 */       provider.setVariable(macro, varName, -1);
/*    */     }
/* 44 */     else if (params.length > 0 && instance.hasOutVar()) {
/*    */       
/* 46 */       retVal.setInt(findItem(provider, macro, params[0], (String)null));
/*    */     } 
/*    */     
/* 49 */     return (IReturnValue)retVal;
/*    */   }
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
/*    */   public int findItem(IScriptActionProvider provider, IMacro macro, String unparsedId, String unparsedStart) {
/* 62 */     ItemID itemId = tryParseItemID(provider.expand(macro, unparsedId, false));
/*    */     
/* 64 */     int startSlot = 0;
/* 65 */     if (unparsedStart != null)
/*    */     {
/* 67 */       startSlot = Math.max(0, ScriptCore.tryParseInt(provider.expand(macro, unparsedStart, false), 0));
/*    */     }
/*    */     
/* 70 */     if (itemId.isValid())
/*    */     {
/* 72 */       return this.slotHelper.getSlotContaining(itemId, startSlot);
/*    */     }
/*    */     
/* 75 */     return -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 84 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 93 */     return "inventory";
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionGetSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */