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
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import net.eq2online.util.Util;
/*    */ 
/*    */ public class ScriptActionToast
/*    */   extends ScriptAction
/*    */ {
/* 17 */   private static final ho EMPTY_TEXT_COMPONENT = new ho("");
/*    */ 
/*    */   
/*    */   public ScriptActionToast(ScriptContext context) {
/* 21 */     super(context, "toast");
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
/* 34 */     IScriptActionProvider.ToastType type = IScriptActionProvider.ToastType.ADVANCEMENT;
/* 35 */     String icon = "grass";
/* 36 */     ho ho1 = EMPTY_TEXT_COMPONENT;
/* 37 */     ho ho2 = EMPTY_TEXT_COMPONENT;
/* 38 */     int ticks = 100;
/*    */     
/* 40 */     if (params.length > 0) {
/*    */       
/* 42 */       String strType = provider.expand(macro, params[0], false).toLowerCase();
/* 43 */       if ("clear".equals(strType)) {
/*    */         
/* 45 */         boolean clearAll = (params.length > 1 && "all".equals(provider.expand(macro, params[1], false).toLowerCase()));
/* 46 */         provider.actionClearToasts(!clearAll);
/* 47 */         return null;
/*    */       } 
/*    */ 
/*    */       
/* 51 */       type = (IScriptActionProvider.ToastType)ScriptCore.fuzzyParseEnum((Enum)IScriptActionProvider.ToastType.ADVANCEMENT, strType, new String[] { "adv|task", "chal", "goal", "recipe", "tut", "hint", "narrat" });
/*    */     } 
/*    */     
/* 54 */     if (params.length > 1)
/*    */     {
/* 56 */       icon = provider.expand(macro, params[1], false).toLowerCase();
/*    */     }
/*    */     
/* 59 */     if (params.length > 2)
/*    */     {
/* 61 */       ho1 = new ho(Util.convertAmpCodes(provider.expand(macro, params[2], false)));
/*    */     }
/*    */     
/* 64 */     if (params.length > 3)
/*    */     {
/* 66 */       ho2 = new ho(Util.convertAmpCodes(provider.expand(macro, params[3], false)));
/*    */     }
/*    */     
/* 69 */     if (params.length > 4)
/*    */     {
/* 71 */       ticks = Math.min(Math.max(ScriptCore.tryParseInt(provider.expand(macro, params[4], false), 100), 5), 600);
/*    */     }
/*    */     
/* 74 */     provider.actionDisplayToast(type, icon, (hh)ho1, (hh)ho2, ticks);
/* 75 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\ScriptActionToast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */