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
/*    */ public class OnAutoCraftingCompleteProvider
/*    */   implements IMacroEventVariableProvider
/*    */ {
/*    */   private String reason;
/*    */   
/*    */   public OnAutoCraftingCompleteProvider(IMacroEvent event) {}
/*    */   
/*    */   public void updateVariables(boolean clock) {}
/*    */   
/*    */   public Object getVariable(String variableName) {
/* 25 */     return "REASON".equals(variableName) ? this.reason : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<String> getVariables() {
/* 31 */     Set<String> variables = new HashSet<>();
/* 32 */     variables.add("REASON");
/* 33 */     return variables;
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
/* 44 */     this.reason = instanceVariables[0];
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\event\providers\OnAutoCraftingCompleteProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */