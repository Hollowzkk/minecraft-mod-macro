/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import aow;
/*    */ import awt;
/*    */ import bsb;
/*    */ import bud;
/*    */ import et;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import net.eq2online.util.Game;
/*    */ import rk;
/*    */ 
/*    */ public class ScriptActionGetIdRel
/*    */   extends ScriptActionGetId
/*    */ {
/*    */   public ScriptActionGetIdRel(ScriptContext context) {
/* 22 */     super(context, "getidrel");
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
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 34 */     ReturnValue retVal = new ReturnValue(Game.getBlockName(null));
/*    */     
/* 36 */     if (params.length > 2) {
/*    */       
/* 38 */       bsb theWorld = this.mc.f;
/* 39 */       bud thePlayer = this.mc.h;
/* 40 */       if (theWorld != null && thePlayer != null) {
/*    */         
/* 42 */         int playerPosX = rk.c(thePlayer.p);
/* 43 */         int playerPosY = rk.c(thePlayer.q);
/* 44 */         int playerPosZ = rk.c(thePlayer.r);
/*    */         
/* 46 */         int xPos = playerPosX + ScriptCore.tryParseInt(provider.expand(macro, params[0], false), 0);
/* 47 */         int yPos = playerPosY + ScriptCore.tryParseInt(provider.expand(macro, params[1], false), 0);
/* 48 */         int zPos = playerPosZ + ScriptCore.tryParseInt(provider.expand(macro, params[2], false), 0);
/* 49 */         et blockPos = new et(xPos, yPos, zPos);
/*    */         
/* 51 */         awt blockState = theWorld.o(blockPos);
/* 52 */         aow block = blockState.u();
/* 53 */         retVal.setString(Game.getBlockName(block));
/*    */         
/* 55 */         if (params.length > 3) {
/*    */           
/* 57 */           String idVarName = provider.expand(macro, params[3], false).toLowerCase();
/* 58 */           provider.setVariable(macro, idVarName, Game.getBlockName(block));
/*    */         } 
/*    */         
/* 61 */         if (params.length > 4) {
/*    */           
/* 63 */           String dmgVarName = provider.expand(macro, params[4], false).toLowerCase();
/* 64 */           int blockDamage = block.d(blockState);
/* 65 */           provider.setVariable(macro, dmgVarName, blockDamage);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 70 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionGetIdRel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */