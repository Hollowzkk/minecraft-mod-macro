/*    */ package net.eq2online.macros.scripting.actions.imc;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mumfrey.liteloader.messaging.Message;
/*    */ import com.mumfrey.liteloader.messaging.MessageBus;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptActionSendMessage
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionSendMessage(ScriptContext context) {
/* 20 */     super(context, "sendmessage");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 26 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 32 */     String channel = (String)macro.getState("imc");
/* 33 */     if (channel == null) {
/*    */       
/* 35 */       provider.actionAddChatMessage(I18n.get("script.error.nochannel"));
/* 36 */       return null;
/*    */     } 
/*    */     
/* 39 */     ImmutableList.Builder<String> payload = ImmutableList.builder();
/* 40 */     for (String param : params)
/*    */     {
/* 42 */       payload.add(provider.expand(macro, param, false));
/*    */     }
/*    */     
/* 45 */     MessageBus.send(channel, Message.buildMap(new Object[] { "value", payload.build() }));
/*    */     
/* 47 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\imc\ScriptActionSendMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */