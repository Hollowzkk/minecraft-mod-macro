/*    */ package net.eq2online.macros.scripting.actions;
/*    */ 
/*    */ import hh;
/*    */ import ho;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionAchievementGet
/*    */   extends ScriptAction {
/* 14 */   private static final ho EMPTY_TEXT_COMPONENT = new ho("");
/*    */ 
/*    */   
/*    */   public ScriptActionAchievementGet(ScriptContext context) {
/* 18 */     super(context, "achievementget");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 24 */     return false;
/*    */   }
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
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 37 */     if (params.length > 0) {
/*    */       
/* 39 */       String icon = (params.length > 1) ? provider.expand(macro, params[1], false) : "grass";
/* 40 */       ho text = new ho(provider.expand(macro, params[0], false));
/* 41 */       provider.actionDisplayToast(IScriptActionProvider.ToastType.ADVANCEMENT, icon, (hh)text, (hh)EMPTY_TEXT_COMPONENT, 100);
/*    */     } 
/*    */     
/* 44 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\ScriptActionAchievementGet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */