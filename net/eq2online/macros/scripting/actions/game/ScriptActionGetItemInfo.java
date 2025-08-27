/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import ain;
/*    */ import aip;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.util.Game;
/*    */ 
/*    */ 
/*    */ public class ScriptActionGetItemInfo
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionGetItemInfo(ScriptContext context) {
/* 19 */     super(context, "getiteminfo");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 25 */     ReturnValue retVal = new ReturnValue("None");
/*    */     
/* 27 */     if (params.length > 1) {
/*    */       
/* 29 */       aip itemStack = tryParseItemID(provider.expand(macro, params[0], false)).toItemStack(1);
/*    */       
/* 31 */       ain item = itemStack.c();
/* 32 */       if (item != null) {
/*    */         
/* 34 */         String stackType = "ITEM";
/* 35 */         String idDropped = Game.getItemName(item);
/*    */         
/* 37 */         if (item instanceof ahb)
/*    */         {
/* 39 */           stackType = "TILE";
/*    */         }
/*    */         
/* 42 */         retVal.setString(itemStack.r());
/*    */         
/* 44 */         provider.setVariable(macro, params[1], itemStack.r());
/* 45 */         if (params.length > 2) provider.setVariable(macro, params[2], itemStack.d()); 
/* 46 */         if (params.length > 3) provider.setVariable(macro, params[3], stackType); 
/* 47 */         if (params.length > 4) provider.setVariable(macro, params[4], idDropped);
/*    */       
/*    */       } else {
/*    */         
/* 51 */         provider.setVariable(macro, params[1], "None");
/* 52 */         if (params.length > 2) provider.setVariable(macro, params[2], 0); 
/* 53 */         if (params.length > 3) provider.setVariable(macro, params[3], "NONE"); 
/* 54 */         if (params.length > 4) provider.setVariable(macro, params[4], "");
/*    */       
/*    */       } 
/*    */     } 
/* 58 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionGetItemInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */