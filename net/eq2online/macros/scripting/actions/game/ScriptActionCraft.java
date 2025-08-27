/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import bud;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.crafting.AutoCraftingToken;
/*    */ import net.eq2online.macros.scripting.crafting.IAutoCraftingInitiator;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import net.eq2online.macros.scripting.variable.ItemID;
/*    */ 
/*    */ public class ScriptActionCraft
/*    */   extends ScriptActionSlotClick
/*    */   implements IAutoCraftingInitiator {
/*    */   public ScriptActionCraft(ScriptContext context) {
/* 19 */     super(context, "craft");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ScriptActionCraft(ScriptContext context, String actionName) {
/* 24 */     super(context, actionName);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 30 */     return "craft";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 36 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 42 */     doCrafting(provider, macro, params);
/* 43 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AutoCraftingToken doCrafting(IScriptActionProvider provider, IMacro macro, String[] params) {
/* 52 */     if (params.length > 0) {
/*    */       
/* 54 */       bud thePlayer = this.mc.h;
/*    */       
/* 56 */       ItemID itemId = ScriptAction.tryParseItemID(provider.expand(macro, params[0], false));
/*    */       
/* 58 */       int amount = 1;
/* 59 */       if (params.length > 1) amount = Math.min(Math.max(ScriptCore.tryParseInt(provider.expand(macro, params[1], false), 1), 1), 999);
/*    */       
/* 61 */       boolean shouldThrowResult = false;
/* 62 */       if (params.length > 2) {
/*    */         
/* 64 */         String shouldThrowArg = provider.expand(macro, params[2], false);
/* 65 */         shouldThrowResult = ("true".equalsIgnoreCase(shouldThrowArg) || "1".equals(shouldThrowArg));
/*    */       } 
/*    */       
/* 68 */       boolean verbose = false;
/* 69 */       if (params.length > 3) {
/*    */         
/* 71 */         String verboseArg = provider.expand(macro, params[3], false);
/* 72 */         verbose = ("true".equalsIgnoreCase(verboseArg) || "1".equals(verboseArg));
/*    */       } 
/*    */       
/* 75 */       return provider.actionCraft(this, thePlayer, itemId.identifier, itemId.damage, amount, shouldThrowResult, verbose);
/*    */     } 
/*    */     
/* 78 */     return new AutoCraftingToken(null, "BADPARAMS");
/*    */   }
/*    */   
/*    */   public void notifyTokenProcessed(AutoCraftingToken token, String reason) {}
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionCraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */