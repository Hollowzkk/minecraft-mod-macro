/*     */ package net.eq2online.macros.scripting.actions.game;
/*     */ 
/*     */ import aow;
/*     */ import awt;
/*     */ import bsb;
/*     */ import bud;
/*     */ import et;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.ReturnValue;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import net.eq2online.util.Game;
/*     */ import rk;
/*     */ 
/*     */ public class ScriptActionGetId
/*     */   extends ScriptAction
/*     */ {
/*     */   public ScriptActionGetId(ScriptContext context) {
/*  23 */     super(context, "getid");
/*     */   }
/*     */ 
/*     */   
/*     */   protected ScriptActionGetId(ScriptContext context, String actionName) {
/*  28 */     super(context, actionName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/*  34 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  40 */     ReturnValue retVal = new ReturnValue(Game.getBlockName(null));
/*     */     
/*  42 */     if (params.length > 2) {
/*     */       
/*  44 */       bsb theWorld = this.mc.f;
/*  45 */       bud thePlayer = this.mc.h;
/*  46 */       if (theWorld != null && thePlayer != null) {
/*     */         
/*  48 */         int xPos = getPosition(provider, macro, params[0], thePlayer.p);
/*  49 */         int yPos = getPosition(provider, macro, params[1], thePlayer.q);
/*  50 */         int zPos = getPosition(provider, macro, params[2], thePlayer.r);
/*  51 */         et blockPos = new et(xPos, yPos, zPos);
/*     */         
/*  53 */         awt blockState = theWorld.o(blockPos);
/*  54 */         aow block = blockState.u();
/*  55 */         retVal.setString(Game.getBlockName(block));
/*     */         
/*  57 */         if (params.length > 3) {
/*     */           
/*  59 */           String idVarName = provider.expand(macro, params[3], false).toLowerCase();
/*  60 */           provider.setVariable(macro, idVarName, Game.getBlockName(block));
/*     */         } 
/*     */         
/*  63 */         if (params.length > 4) {
/*     */           
/*  65 */           String dmgVarName = provider.expand(macro, params[4], false).toLowerCase();
/*  66 */           int blockDamage = block.d(blockState);
/*  67 */           provider.setVariable(macro, dmgVarName, blockDamage);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  72 */     return (IReturnValue)retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPosition(IScriptActionProvider provider, IMacro macro, String param, double currentPos) {
/*  84 */     String sPos = provider.expand(macro, param, false);
/*  85 */     boolean isRelative = sPos.startsWith("~");
/*  86 */     int iCurrentPosX = isRelative ? rk.c(currentPos) : 0;
/*  87 */     int xPos = iCurrentPosX + ScriptCore.tryParseInt(isRelative ? sPos.substring(1) : sPos, 0);
/*  88 */     return xPos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPermissable() {
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionGroup() {
/* 106 */     return "inventory";
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionGetId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */