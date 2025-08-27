/*    */ package net.eq2online.macros.event.providers;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*    */ import net.eq2online.macros.scripting.api.IMacroEventVariableProvider;
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
/*    */ public class OnFilterableChatProvider
/*    */   implements IMacroEventVariableProvider
/*    */ {
/*    */   String chat;
/*    */   
/*    */   public OnFilterableChatProvider(IMacroEvent event) {}
/*    */   
/*    */   public void updateVariables(boolean clock) {}
/*    */   
/*    */   public Object getVariable(String variableName) {
/* 30 */     if ("CHAT".equals(variableName)) return this.chat;
/*    */     
/* 32 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<String> getVariables() {
/* 38 */     Set<String> variables = new HashSet<>();
/* 39 */     variables.add("CHAT");
/* 40 */     return variables;
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
/* 51 */     this.chat = instanceVariables[0];
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\event\providers\OnFilterableChatProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */