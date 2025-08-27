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
/*    */ public class OnPickupItemProvider
/*    */   implements IMacroEventVariableProvider
/*    */ {
/*    */   private String pickupId;
/*    */   private int pickupAmount;
/*    */   private int pickupDamage;
/*    */   private String pickupItem;
/*    */   
/*    */   public OnPickupItemProvider(IMacroEvent event) {}
/*    */   
/*    */   public void updateVariables(boolean clock) {}
/*    */   
/*    */   public Object getVariable(String variableName) {
/* 28 */     if ("PICKUPID".equals(variableName)) return this.pickupId; 
/* 29 */     if ("PICKUPITEM".equals(variableName)) return this.pickupItem; 
/* 30 */     if ("PICKUPAMOUNT".equals(variableName)) return Integer.valueOf(this.pickupAmount); 
/* 31 */     if ("PICKUPDATA".equals(variableName)) return Integer.valueOf(this.pickupDamage);
/*    */     
/* 33 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<String> getVariables() {
/* 39 */     Set<String> variables = new HashSet<>();
/* 40 */     variables.add("PICKUPID");
/* 41 */     variables.add("PICKUPITEM");
/* 42 */     variables.add("PICKUPAMOUNT");
/* 43 */     variables.add("PICKUPDATA");
/* 44 */     return variables;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onInit() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initInstance(String[] instanceVariables) {
/*    */     try {
/* 57 */       this.pickupAmount = Integer.parseInt(instanceVariables[2]);
/* 58 */       this.pickupDamage = Integer.parseInt(instanceVariables[3]);
/*    */     }
/* 60 */     catch (NumberFormatException numberFormatException) {}
/*    */     
/* 62 */     this.pickupId = instanceVariables[0];
/* 63 */     this.pickupItem = instanceVariables[1];
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\event\providers\OnPickupItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */