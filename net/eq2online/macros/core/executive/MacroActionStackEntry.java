/*     */ package net.eq2online.macros.core.executive;
/*     */ 
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionStackEntry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroActionStackEntry
/*     */   implements IMacroActionStackEntry
/*     */ {
/*     */   private int stackPointer;
/*     */   private boolean conditionalFlag = false;
/*     */   private boolean ifFlag = false;
/*     */   private boolean elseFlag = false;
/*     */   private IMacroAction action;
/*     */   
/*     */   public MacroActionStackEntry(int pointer, IMacroAction action, boolean conditional) {
/*  53 */     this.stackPointer = pointer;
/*  54 */     this.action = action;
/*  55 */     this.conditionalFlag = conditional;
/*  56 */     this.ifFlag = conditional;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStackPushOperator() {
/*  66 */     if (this.action == null || this.action.getAction() == null)
/*     */     {
/*  68 */       return false;
/*     */     }
/*     */     
/*  71 */     return this.action.getAction().isStackPushOperator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePoppedBy(IMacroAction action) {
/*  81 */     if (this.action == null || this.action.getAction() == null)
/*     */     {
/*  83 */       return true;
/*     */     }
/*     */     
/*  86 */     return this.action.getAction().canBePoppedBy(action.getAction());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeStackPop(IMacroActionProcessor processor, IMacroActionContext context, IMacro macro, IMacroAction popAction) {
/* 100 */     this.action.executeStackPop(processor, context, macro, popAction);
/*     */     
/* 102 */     if (this.stackPointer == -1)
/*     */     {
/* 104 */       this.action.setState(null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConditionalOperator() {
/* 115 */     if (this.action == null || this.action.getAction() == null)
/*     */     {
/* 117 */       return false;
/*     */     }
/*     */     
/* 120 */     return this.action.getAction().isConditionalOperator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConditionalElseOperator(IMacroAction action) {
/* 131 */     if (this.action == null || action == null || this.action.getAction() == null || action.getAction() == null)
/*     */     {
/* 133 */       return false;
/*     */     }
/*     */     
/* 136 */     return action.getAction().isConditionalElseOperator(this.action.getAction());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesConditionalOperator(IMacroAction action) {
/* 147 */     if (this.action == null || action == null || this.action.getAction() == null || action.getAction() == null)
/*     */     {
/* 149 */       return false;
/*     */     }
/*     */     
/* 152 */     return action.getAction().matchesConditionalOperator(this.action.getAction());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getConditionalFlag() {
/* 162 */     return this.conditionalFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConditionalFlag(boolean newFlag) {
/* 172 */     this.conditionalFlag = newFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIfFlag() {
/* 182 */     return this.ifFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIfFlag(boolean newFlag) {
/* 192 */     this.ifFlag = newFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getElseFlag() {
/* 202 */     return this.elseFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElseFlag(boolean newFlag) {
/* 212 */     this.elseFlag = newFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroAction getAction() {
/* 222 */     return this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStackPointer() {
/* 232 */     return this.stackPointer;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\executive\MacroActionStackEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */