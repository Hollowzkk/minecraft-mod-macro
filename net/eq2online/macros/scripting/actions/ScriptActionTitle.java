/*    */ package net.eq2online.macros.scripting.actions;
/*    */ 
/*    */ import com.google.common.base.Strings;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import net.eq2online.util.Util;
/*    */ 
/*    */ 
/*    */ public class ScriptActionTitle
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionTitle(ScriptContext context) {
/* 18 */     super(context, "title");
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
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 30 */     if (params.length > 1) {
/*    */       
/* 32 */       String subTitle = Util.convertAmpCodes(provider.expand(macro, params[1], false));
/* 33 */       this.mc.q.a(null, Strings.emptyToNull(subTitle), -1, -1, -1);
/*    */     } 
/*    */     
/* 36 */     int timeFadeIn = -1;
/* 37 */     int displayTime = -1;
/* 38 */     int timeFadeOut = -1;
/* 39 */     if (params.length > 2) {
/*    */       
/* 41 */       timeFadeIn = ScriptCore.tryParseInt(provider.expand(macro, params[2], false), -1);
/* 42 */       if (params.length > 3) {
/*    */         
/* 44 */         displayTime = ScriptCore.tryParseInt(provider.expand(macro, params[3], false), -1);
/* 45 */         if (params.length > 4)
/*    */         {
/* 47 */           timeFadeOut = ScriptCore.tryParseInt(provider.expand(macro, params[4], false), -1);
/*    */         }
/*    */       } 
/* 50 */       this.mc.q.a(null, null, timeFadeIn, displayTime, timeFadeOut);
/*    */     } 
/*    */     
/* 53 */     if (params.length > 0) {
/*    */       
/* 55 */       String title = Util.convertAmpCodes(provider.expand(macro, params[0], false));
/* 56 */       this.mc.q.a(title, null, -1, -1, -1);
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 61 */       this.mc.q.a("", "", -1, -1, -1);
/* 62 */       this.mc.q.a();
/*    */     } 
/*    */     
/* 65 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\ScriptActionTitle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */