/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.eq2online.macros.core.MacroExecVariableProvider;
/*    */ import net.eq2online.macros.core.MacroIncludeProcessor;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IMacroTemplate;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.IVariableProvider;
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
/*    */ public class ScriptActionExec
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionExec(ScriptContext context) {
/* 26 */     super(context, "exec");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 38 */     if (params.length > 0) {
/*    */       MacroExecVariableProvider macroExecVariableProvider;
/* 40 */       String paramCompiled = provider.expand(macro, params[0], false);
/* 41 */       String macroName = null;
/*    */       
/* 43 */       if (params.length > 1)
/*    */       {
/* 45 */         macroName = provider.expand(macro, params[1], false);
/*    */       }
/*    */       
/* 48 */       IVariableProvider contextProvider = null;
/*    */       
/* 50 */       if (params.length > 2)
/*    */       {
/* 52 */         macroExecVariableProvider = new MacroExecVariableProvider(params, 2, provider, macro);
/*    */       }
/*    */       
/* 55 */       if (MacroIncludeProcessor.PATTERN_FILE_NAME.matcher(paramCompiled).matches())
/*    */       {
/* 57 */         if (instance.getState() != null) {
/*    */           
/* 59 */           IMacroTemplate tpl = (IMacroTemplate)instance.getState();
/* 60 */           provider.getMacroEngine().playMacro(tpl, false, ScriptContext.MAIN, (IVariableProvider)macroExecVariableProvider);
/*    */         }
/*    */         else {
/*    */           
/* 64 */           File playFile = provider.getMacroEngine().getFile(paramCompiled);
/*    */           
/* 66 */           if (playFile.isFile()) {
/*    */             
/* 68 */             IMacroTemplate tpl = provider.getMacroEngine().createFloatingTemplate("$${$$<" + paramCompiled + ">}$$", macroName);
/* 69 */             instance.setState(tpl);
/*    */             
/* 71 */             provider.getMacroEngine().playMacro(tpl, false, ScriptContext.MAIN, (IVariableProvider)macroExecVariableProvider);
/*    */           } 
/*    */         } 
/*    */       }
/*    */     } 
/*    */     
/* 77 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionExec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */