/*    */ package net.eq2online.macros.core.params;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ public abstract class MacroParamListOnly<TItem>
/*    */   extends MacroParam<TItem>
/*    */ {
/*    */   protected MacroParamListOnly(Macros macros, bib mc, MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider<TItem> provider) {
/* 14 */     super(macros, mc, type, target, params, provider);
/*    */     
/* 16 */     this.enableTextField = Boolean.valueOf(false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPromptMessage() {
/* 22 */     return "";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean apply() {
/* 28 */     boolean success = true;
/*    */     
/* 30 */     if (this.itemListBox.getSelectedItem().getId() != -1) {
/*    */       
/* 32 */       String selectedValue = this.itemListBox.getSelectedItem().getText();
/*    */       
/* 34 */       if (!checkForInvalidParameterValue(selectedValue))
/*    */       {
/* 36 */         setParameterValue(selectedValue);
/*    */       }
/*    */       else
/*    */       {
/* 40 */         setParameterValue("");
/* 41 */         success = false;
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 46 */       setParameterValue("");
/*    */     } 
/*    */     
/* 49 */     if (success)
/*    */     {
/*    */       
/* 52 */       replace();
/*    */     }
/*    */     
/* 55 */     return success;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean checkForInvalidParameterValue(String paramValue) {
/* 63 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\MacroParamListOnly.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */