/*    */ package net.eq2online.macros.event.providers;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*    */ import net.eq2online.macros.scripting.api.IMacroEventVariableProvider;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OnSendChatMessageProvider
/*    */   implements IMacroEventVariableProvider
/*    */ {
/*    */   private String chat;
/*    */   private String uuid;
/*    */   private String newMessage;
/*    */   private boolean pass = true;
/*    */   
/*    */   public OnSendChatMessageProvider(IMacroEvent event) {}
/*    */   
/*    */   public void updateVariables(boolean clock) {}
/*    */   
/*    */   public Object getVariable(String variableName) {
/* 38 */     if ("CHAT".equals(variableName)) return this.chat;
/*    */     
/* 40 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<String> getVariables() {
/* 46 */     Set<String> variables = new HashSet<>();
/* 47 */     variables.add("CHAT");
/* 48 */     return variables;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onInit() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void initInstance(String[] instanceVariables) {
/* 59 */     this.chat = instanceVariables[0];
/* 60 */     this.uuid = instanceVariables[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPass(IScriptActionProvider provider, boolean pass) {
/* 65 */     this.pass = pass;
/* 66 */     ((Macros)provider.getMacroEngine()).getBuiltinEventDispatcher().addChatMessageHandler(this.uuid, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPass() {
/* 71 */     return this.pass;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isModified() {
/* 76 */     return (this.newMessage != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String newMessage() {
/* 81 */     return this.newMessage;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\event\providers\OnSendChatMessageProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */