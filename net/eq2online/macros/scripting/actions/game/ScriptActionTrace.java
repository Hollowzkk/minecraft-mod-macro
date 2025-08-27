/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import bhc;
/*    */ import bud;
/*    */ import com.mumfrey.liteloader.util.EntityUtilities;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import net.eq2online.macros.scripting.variable.providers.VariableProviderTrace;
/*    */ import vg;
/*    */ 
/*    */ public class ScriptActionTrace
/*    */   extends ScriptAction {
/*    */   public ScriptActionTrace(ScriptContext context) {
/* 21 */     super(context, "trace");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 33 */     if (params.length > 0) {
/*    */       
/* 35 */       int traceDistance = Math.min(Math.max(ScriptCore.tryParseInt(provider.expand(macro, params[0], false), 0), 3), 256);
/* 36 */       boolean includeEntities = false;
/* 37 */       if (params.length > 1) {
/*    */         
/* 39 */         String traceEntitiesArg = provider.expand(macro, params[1], false);
/* 40 */         includeEntities = ("true".equalsIgnoreCase(traceEntitiesArg) || "1".equals(traceEntitiesArg));
/*    */       } 
/*    */       
/* 43 */       bud thePlayer = this.mc.h;
/* 44 */       if (thePlayer != null) {
/*    */         
/* 46 */         bhc ray = EntityUtilities.rayTraceFromEntity((vg)thePlayer, traceDistance, this.mc.aj(), includeEntities);
/* 47 */         VariableProviderTrace traceVars = (VariableProviderTrace)macro.getState("trace");
/* 48 */         if (traceVars == null) {
/*    */           
/* 50 */           traceVars = new VariableProviderTrace(this.mc, ray);
/* 51 */           macro.setState("trace", traceVars);
/* 52 */           macro.registerVariableProvider((IVariableProvider)traceVars);
/*    */         }
/*    */         else {
/*    */           
/* 56 */           traceVars.update(ray);
/*    */         } 
/*    */         
/* 59 */         return (IReturnValue)new ReturnValue(traceVars.getType());
/*    */       } 
/*    */     } 
/*    */     
/* 63 */     return (IReturnValue)new ReturnValue("NONE");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 72 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 81 */     return "inventory";
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionTrace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */