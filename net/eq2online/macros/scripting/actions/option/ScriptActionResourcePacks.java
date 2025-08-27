/*    */ package net.eq2online.macros.scripting.actions.option;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionResourcePacks
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionResourcePacks(ScriptContext context) {
/* 14 */     super(context, "resourcepacks");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 20 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 26 */     String[] resourcePacks = new String[params.length];
/* 27 */     for (int index = 0; index < params.length; index++)
/*    */     {
/* 29 */       resourcePacks[index] = provider.expand(macro, params[index], false);
/*    */     }
/*    */     
/* 32 */     provider.actionSelectResourcePacks(resourcePacks);
/*    */     
/* 34 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionResourcePacks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */