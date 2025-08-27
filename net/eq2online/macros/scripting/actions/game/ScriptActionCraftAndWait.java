/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.crafting.AutoCraftingToken;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionCraftAndWait
/*    */   extends ScriptActionCraft
/*    */ {
/*    */   public ScriptActionCraftAndWait(ScriptContext context) {
/* 14 */     super(context, "craftandwait");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canExecuteNow(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 20 */     if (params.length > 0) {
/*    */       
/* 22 */       AutoCraftingToken token = null;
/*    */       
/* 24 */       if (instance.getState() == null) {
/*    */         
/* 26 */         token = doCrafting(provider, macro, params);
/* 27 */         if (token == null) return true;
/*    */         
/* 29 */         instance.setState(token);
/* 30 */         return token.isCompleted();
/*    */       } 
/*    */       
/* 33 */       token = (AutoCraftingToken)instance.getState();
/* 34 */       return (token == null || token.isCompleted());
/*    */     } 
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 43 */     return null;
/*    */   }
/*    */   
/*    */   public void notifyTokenProcessed(AutoCraftingToken token, String reason) {}
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionCraftAndWait.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */