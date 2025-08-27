/*    */ package net.eq2online.macros.scripting;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.scripting.api.IScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ScriptActionBase
/*    */   implements IScriptAction
/*    */ {
/* 14 */   protected final bib mc = bib.z();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final ScriptContext context;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final String actionName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ScriptActionBase(ScriptContext context, String actionName) {
/* 34 */     this.context = context;
/* 35 */     this.actionName = actionName.toLowerCase();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final ScriptContext getContext() {
/* 44 */     return this.context;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 53 */     return this.actionName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final String toString() {
/* 62 */     return getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\ScriptActionBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */