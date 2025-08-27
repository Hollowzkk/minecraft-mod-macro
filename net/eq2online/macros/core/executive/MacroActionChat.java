/*    */ package net.eq2online.macros.core.executive;
/*    */ 
/*    */ import bib;
/*    */ import bkn;
/*    */ import blk;
/*    */ import net.eq2online.macros.scripting.ReturnValueChat;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.exceptions.ScriptExceptionVoidResult;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ 
/*    */ public class MacroActionChat
/*    */   extends MacroAction {
/*    */   private String message;
/*    */   
/*    */   public MacroActionChat(IMacroActionProcessor actionProcessor, String message) {
/* 21 */     super(actionProcessor);
/* 22 */     this.message = message;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canExecuteNow(IMacroActionContext context, IMacro macro) {
/* 28 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(IMacroActionContext context, IMacro macro, boolean stop, boolean allowLatent) {
/* 34 */     if (!this.actionProcessor.getConditionalExecutionState())
/*    */     {
/* 36 */       return true;
/*    */     }
/*    */     
/* 39 */     IScriptActionProvider provider = context.getActionProvider();
/*    */     
/* 41 */     String[] messages = this.message.split("[\\x7C\\x82]");
/*    */ 
/*    */     
/* 44 */     if (messages.length == 0 && stop) {
/*    */       
/* 46 */       processStop("");
/* 47 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 51 */     for (int messageIndex = 0; messageIndex < messages.length; messageIndex++) {
/*    */       
/* 53 */       if (stop && messageIndex == messages.length - 1) {
/*    */         
/* 55 */         processStop(messages[messageIndex]);
/*    */       
/*    */       }
/*    */       else {
/*    */         
/* 60 */         ScriptAction.onActionExecuted();
/*    */ 
/*    */         
/* 63 */         this.returnValueHandler.handleReturnValue(provider, macro, this, (IReturnValue)new ReturnValueChat(messages[messageIndex]));
/*    */       } 
/*    */     } 
/*    */     
/* 67 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleReturnValue(IScriptActionProvider provider, IMacro macro, IMacroAction instance, IReturnValue returnValue) throws ScriptExceptionVoidResult {
/* 74 */     String remoteMessage = returnValue.getRemoteMessage();
/* 75 */     if (remoteMessage != null && !remoteMessage.isEmpty())
/*    */     {
/* 77 */       provider.actionSendChatMessage(macro, this, remoteMessage);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void processStop(String buffer) {
/* 88 */     bib.z().a((blk)new bkn(buffer));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 94 */     return this.message;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\executive\MacroActionChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */