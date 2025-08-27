/*    */ package net.eq2online.macros.core.executive;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroActionContext
/*    */   implements IMacroActionContext
/*    */ {
/*    */   private final ScriptContext context;
/*    */   private final IScriptActionProvider actionProvider;
/*    */   private final IVariableProvider variableProvider;
/*    */   
/*    */   public MacroActionContext(ScriptContext scriptContext, IScriptActionProvider scriptActionProvider, IVariableProvider contextVariableProvider) {
/* 18 */     this.context = scriptContext;
/* 19 */     this.actionProvider = scriptActionProvider;
/* 20 */     this.variableProvider = contextVariableProvider;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onTick(boolean clock) {
/* 26 */     if (this.variableProvider != null)
/*    */     {
/* 28 */       this.variableProvider.updateVariables(clock);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ScriptContext getScriptContext() {
/* 35 */     return this.context;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IScriptActionProvider getActionProvider() {
/* 41 */     return this.actionProvider;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IVariableProvider getVariableProvider() {
/* 47 */     return this.variableProvider;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\executive\MacroActionContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */