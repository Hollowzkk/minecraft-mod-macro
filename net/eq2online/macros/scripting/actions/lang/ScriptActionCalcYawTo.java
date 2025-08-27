/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import rk;
/*    */ 
/*    */ public class ScriptActionCalcYawTo
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionCalcYawTo(ScriptContext context) {
/* 17 */     super(context, "calcyawto");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 23 */     ReturnValue retVal = new ReturnValue(0);
/*    */     
/* 25 */     if (params.length > 1 && this.mc != null && this.mc.h != null) {
/*    */       
/* 27 */       float xPos = ScriptCore.tryParseInt(provider.expand(macro, params[0], false), 0) + 0.5F;
/* 28 */       float zPos = ScriptCore.tryParseInt(provider.expand(macro, params[1], false), 0) + 0.5F;
/*    */       
/* 30 */       double deltaX = xPos - this.mc.h.p;
/* 31 */       double deltaZ = zPos - this.mc.h.r;
/* 32 */       double distance = rk.a(deltaX * deltaX + deltaZ * deltaZ);
/* 33 */       int yaw = (int)(Math.atan2(deltaZ, deltaX) * 180.0D / Math.PI - 90.0D);
/* 34 */       while (yaw < 0)
/*    */       {
/* 36 */         yaw += 360;
/*    */       }
/*    */       
/* 39 */       retVal.setInt(yaw);
/*    */       
/* 41 */       if (params.length > 2) {
/*    */         
/* 43 */         String varName = provider.expand(macro, params[2], false);
/* 44 */         provider.setVariable(macro, varName, yaw);
/*    */       } 
/*    */       
/* 47 */       if (params.length > 3) {
/*    */         
/* 49 */         String varName = provider.expand(macro, params[3], false);
/* 50 */         provider.setVariable(macro, varName, rk.c(distance));
/*    */       } 
/*    */     } 
/*    */     
/* 54 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionCalcYawTo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */