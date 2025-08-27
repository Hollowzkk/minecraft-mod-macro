/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import blk;
/*    */ import net.eq2online.macros.core.mixin.IGuiGameOver;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ public class ScriptActionRespawn
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionRespawn(ScriptContext context) {
/* 17 */     super(context, "respawn");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 35 */     return "player";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 41 */     blk currentScreen = this.mc.m;
/*    */     
/* 43 */     if (currentScreen instanceof bkv) {
/*    */       
/*    */       try {
/*    */         
/* 47 */         int cooldownTimer = ((IGuiGameOver)currentScreen).getCooldownTimer();
/*    */         
/* 49 */         if (cooldownTimer >= 20)
/*    */         {
/* 51 */           provider.actionRespawnPlayer();
/*    */         }
/*    */       }
/* 54 */       catch (Exception exception) {}
/*    */     }
/*    */     
/* 57 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionRespawn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */