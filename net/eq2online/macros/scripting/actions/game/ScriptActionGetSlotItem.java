/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import aip;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import net.eq2online.util.Game;
/*    */ 
/*    */ public class ScriptActionGetSlotItem
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionGetSlotItem(ScriptContext context) {
/* 18 */     super(context, "getslotitem");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 24 */     String itemID = "unknown";
/* 25 */     int stackSize = 0;
/* 26 */     int damage = 0;
/*    */     
/* 28 */     if (params.length > 0) {
/*    */       
/* 30 */       int slotId = Math.max(0, ScriptCore.tryParseInt(provider.expand(macro, params[0], false), 0));
/* 31 */       aip slotStack = this.slotHelper.getSlotStack(slotId);
/*    */       
/* 33 */       if (slotStack == null) {
/*    */         
/* 35 */         itemID = Game.getItemName(null);
/*    */       }
/*    */       else {
/*    */         
/* 39 */         itemID = Game.getItemName(slotStack.c());
/* 40 */         stackSize = slotStack.E();
/* 41 */         damage = slotStack.j();
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     ReturnValue retVal = new ReturnValue(itemID);
/*    */     
/* 47 */     if (params.length > 1) provider.setVariable(macro, provider.expand(macro, params[1], false), itemID); 
/* 48 */     if (params.length > 2) provider.setVariable(macro, provider.expand(macro, params[2], false), stackSize); 
/* 49 */     if (params.length > 3) provider.setVariable(macro, provider.expand(macro, params[3], false), damage);
/*    */     
/* 51 */     return (IReturnValue)retVal;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 60 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 69 */     return "inventory";
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionGetSlotItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */