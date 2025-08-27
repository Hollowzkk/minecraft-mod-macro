/*    */ package net.eq2online.macros.scripting.actions.option;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionCamera
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionCamera(ScriptContext context) {
/* 15 */     super(context, "camera");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 28 */     int cameraMode = (params.length > 0) ? ScriptCore.tryParseInt(provider.expand(macro, params[0], false), 0) : ((this.mc.t.aw + 1) % 3);
/*    */     
/* 30 */     provider.actionSetCameraMode(cameraMode);
/* 31 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionCamera.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */