/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import bn;
/*    */ import com.google.common.base.Strings;
/*    */ import com.google.gson.JsonSyntaxException;
/*    */ import hh;
/*    */ import hi;
/*    */ import net.eq2online.macros.core.executive.MacroAction;
/*    */ import net.eq2online.macros.scripting.ReturnValueLog;
/*    */ import net.eq2online.macros.scripting.ReturnValueRaw;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import vg;
/*    */ 
/*    */ public class ScriptActionLogRaw
/*    */   extends ScriptAction {
/*    */   public ScriptActionLogRaw(ScriptContext context) {
/* 22 */     super(context, "lograw");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 28 */     if (params.length == 0)
/*    */     {
/* 30 */       return null;
/*    */     }
/*    */ 
/*    */     
/*    */     try {
/* 35 */       String unparsedParams = ((MacroAction)instance).getUnparsedParams();
/* 36 */       if (Strings.isNullOrEmpty(unparsedParams))
/*    */       {
/* 38 */         return null;
/*    */       }
/* 40 */       String json = provider.expand(macro, unparsedParams, false);
/* 41 */       hh itextcomponent = hh.a.a(json);
/* 42 */       hh processed = hi.a((bn)this.mc.h, itextcomponent, (vg)this.mc.h);
/* 43 */       return (IReturnValue)new ReturnValueRaw(processed);
/*    */     }
/* 45 */     catch (JsonSyntaxException ex) {
/*    */       
/* 47 */       String message = ex.getMessage();
/* 48 */       int pos = message.indexOf("at ");
/* 49 */       return (IReturnValue)new ReturnValueLog("§cInvalid JSON " + ((pos > -1) ? message.substring(pos) : message));
/*    */     }
/* 51 */     catch (Exception ex) {
/*    */       
/* 53 */       return (IReturnValue)new ReturnValueLog("§c" + ex.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionLogRaw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */