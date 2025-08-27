/*    */ package net.eq2online.macros.scripting.actions.imc;
/*    */ 
/*    */ import com.mumfrey.liteloader.messaging.Message;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ public class ScriptActionSelectChannel
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionSelectChannel(ScriptContext context) {
/* 17 */     super(context, "selectchannel");
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
/* 31 */       String channel = provider.expand(macro, params[0], false);
/* 32 */       if (Message.isValidChannel(channel)) {
/*    */         
/* 34 */         macro.setState("imc", channel);
/*    */       }
/*    */       else {
/*    */         
/* 38 */         provider.actionAddChatMessage(I18n.get("script.error.badchannel", new Object[] { channel }));
/*    */       } 
/*    */     } 
/*    */     
/* 42 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\imc\ScriptActionSelectChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */