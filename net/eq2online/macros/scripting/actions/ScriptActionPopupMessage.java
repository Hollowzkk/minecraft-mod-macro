/*    */ package net.eq2online.macros.scripting.actions;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import net.eq2online.util.Util;
/*    */ import rp;
/*    */ 
/*    */ public class ScriptActionPopupMessage
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionPopupMessage(ScriptContext context) {
/* 17 */     super(context, "popupmessage");
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
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 29 */     if (params.length > 0) {
/*    */       
/* 31 */       boolean animateColour = (params.length > 1 && ScriptCore.parseBoolean(provider.expand(macro, params[1], false)));
/* 32 */       String message = Util.convertAmpCodes(provider.expand(macro, params[0], false));
/* 33 */       if (animateColour)
/*    */       {
/* 35 */         message = rp.a(message);
/*    */       }
/* 37 */       this.mc.q.a(message, animateColour);
/*    */     } 
/*    */     
/* 40 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\ScriptActionPopupMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */