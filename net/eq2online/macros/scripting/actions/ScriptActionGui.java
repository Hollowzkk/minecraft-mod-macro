/*    */ package net.eq2online.macros.scripting.actions;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
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
/*    */ public class ScriptActionGui
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionGui(ScriptContext context) {
/* 23 */     super(context, "gui");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 29 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 35 */     if (params.length > 2 && "bind".equalsIgnoreCase(params[0])) {
/*    */       
/* 37 */       String slotName = provider.expand(macro, params[1], false);
/* 38 */       String layoutName = provider.expand(macro, params[2], false);
/*    */       
/* 40 */       provider.actionBindScreenToSlot(slotName, layoutName);
/*    */     }
/* 42 */     else if (params.length > 0) {
/*    */       
/* 44 */       provider.actionDisplayGuiScreen(params[0], this.context);
/*    */     }
/*    */     else {
/*    */       
/* 48 */       provider.actionDisplayGuiScreen(null, this.context);
/*    */     } 
/*    */     
/* 51 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\ScriptActionGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */