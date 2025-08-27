/*    */ package net.eq2online.macros.scripting.actions.option;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import qg;
/*    */ 
/*    */ public class ScriptActionVolume
/*    */   extends ScriptActionGamma<qg>
/*    */ {
/*    */   public ScriptActionVolume(ScriptContext context) {
/* 13 */     super(context, "volume", qg.a, 0.0F, 100.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected qg getOption(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String[] params) {
/* 19 */     if (params.length > 1) {
/*    */       
/* 21 */       String categoryName = provider.expand(macro, params[1], false).toLowerCase().trim();
/* 22 */       qg category = qg.a(categoryName);
/* 23 */       if (category != null) return category;
/*    */     
/*    */     } 
/* 26 */     return super.getOption(provider, macro, instance, params);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionVolume.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */