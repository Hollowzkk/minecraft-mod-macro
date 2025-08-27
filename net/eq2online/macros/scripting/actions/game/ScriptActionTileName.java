/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import aow;
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
/*    */ public class ScriptActionTileName
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionTileName(ScriptContext context) {
/* 18 */     super(context, "tilename");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 24 */     ReturnValue retVal = new ReturnValue("");
/*    */     
/* 26 */     if (params.length > 0) {
/*    */       
/* 28 */       int id = ScriptCore.tryParseInt(provider.expand(macro, params[0], false).trim(), -1);
/* 29 */       aow block = aow.c(id);
/* 30 */       if (block != null)
/*    */       {
/* 32 */         retVal.setString(Game.getBlockName(block));
/*    */       }
/*    */     } 
/*    */     
/* 36 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionTileName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */