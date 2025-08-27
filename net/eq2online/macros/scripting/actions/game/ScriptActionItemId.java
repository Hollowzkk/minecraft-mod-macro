/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import ain;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.util.Game;
/*    */ import nf;
/*    */ 
/*    */ public class ScriptActionItemId
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionItemId(ScriptContext context) {
/* 18 */     super(context, "itemid");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 24 */     ReturnValue retVal = new ReturnValue(-1);
/*    */     
/* 26 */     if (params.length > 0) {
/*    */       
/* 28 */       String identifier = provider.expand(macro, params[0], false).trim();
/* 29 */       ain item = Game.getItem(new nf(identifier));
/* 30 */       if (item != null)
/*    */       {
/* 32 */         retVal.setInt(ain.a(item));
/*    */       }
/*    */     } 
/*    */     
/* 36 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionItemId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */