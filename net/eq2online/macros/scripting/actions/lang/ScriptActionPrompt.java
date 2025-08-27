/*     */ package net.eq2online.macros.scripting.actions.lang;
/*     */ 
/*     */ import blk;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.gui.interfaces.IContentRenderer;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.scripting.PromptTarget;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.ReturnValue;
/*     */ import net.eq2online.macros.scripting.interfaces.IPromptOverridable;
/*     */ import net.eq2online.macros.scripting.interfaces.IScriptActionPreProcessor;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ 
/*     */ public class ScriptActionPrompt
/*     */   extends ScriptAction implements IScriptActionPreProcessor {
/*  22 */   private static final Pattern PATTERN_PROMPT = Pattern.compile("(prompt)(\\()([^;]+)(\\))", 2);
/*     */ 
/*     */   
/*     */   public ScriptActionPrompt(ScriptContext context) {
/*  26 */     super(context, "prompt");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/*  32 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExecuteNow(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  38 */     PromptTarget state = null;
/*     */     
/*  40 */     if (instance.getState() == null) {
/*     */       
/*  42 */       if (params.length < 2)
/*     */       {
/*  44 */         return true;
/*     */       }
/*     */       
/*  47 */       String promptSource = (params.length > 2) ? provider.expand(macro, params[2], false) : null;
/*  48 */       String defaultValue = (params.length > 4) ? provider.expand(macro, params[4], false) : "";
/*  49 */       state = new PromptTarget(this.macros, this.mc, macro, provider.expand(macro, params[1], false), promptSource, defaultValue, this.mc.m);
/*     */ 
/*     */       
/*  52 */       if (!state.getCompleted())
/*     */       {
/*  54 */         String arg3 = (params.length > 3) ? provider.expand(macro, params[3], false) : null;
/*  55 */         boolean allowOverride = (arg3 != null && ("1".equals(arg3) || "true".equalsIgnoreCase(arg3) || "yes".equalsIgnoreCase(arg3)));
/*     */         
/*  57 */         instance.setState(state);
/*  58 */         if (this.mc.m == null || this.mc.m instanceof IPromptOverridable || allowOverride) {
/*     */           
/*  60 */           if (this.mc.m instanceof IPromptOverridable) {
/*     */             
/*  62 */             IContentRenderer contentRenderer = ((IPromptOverridable)this.mc.m).getContentRenderer();
/*  63 */             this.mc.a((blk)new GuiMacroParam(this.macros, this.mc, (IMacroParamTarget)state, contentRenderer));
/*     */           }
/*     */           else {
/*     */             
/*  67 */             this.mc.a((blk)new GuiMacroParam(this.macros, this.mc, (IMacroParamTarget)state));
/*     */           } 
/*     */           
/*  70 */           return false;
/*     */         } 
/*     */         
/*  73 */         provider.setVariable(macro, provider.expand(macro, params[0], false), "");
/*  74 */         return true;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  79 */       state = (PromptTarget)instance.getState();
/*     */     } 
/*     */     
/*  82 */     if (state != null) return state.getCompleted(); 
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  89 */     ReturnValue retVal = new ReturnValue("");
/*  90 */     PromptTarget state = (PromptTarget)instance.getState();
/*     */     
/*  92 */     if (state != null)
/*     */     {
/*  94 */       if (state.getCompleted()) {
/*     */         
/*  96 */         String variableName = provider.expand(macro, params[0], false);
/*  97 */         String value = state.getSuccess() ? state.getTargetString() : state.getDefaultValue();
/*     */         
/*  99 */         provider.setVariable(macro, variableName, value);
/* 100 */         retVal.setString(value);
/*     */       } 
/*     */     }
/*     */     
/* 104 */     return (IReturnValue)retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String preProcess(String macro) {
/* 110 */     Matcher prompt = PATTERN_PROMPT.matcher(macro);
/*     */     
/* 112 */     while (prompt.find()) {
/*     */ 
/*     */       
/* 115 */       String parsedPrompt = prompt.group(1) + "£" + prompt.group(2) + prompt.group(3).replaceAll("\\x24\\x24", "££") + prompt.group(4);
/* 116 */       macro = macro.substring(0, prompt.start()) + parsedPrompt + macro.substring(prompt.end());
/* 117 */       prompt.reset(macro);
/*     */     } 
/*     */     
/* 120 */     return macro;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionPrompt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */