/*     */ package net.eq2online.macros.core.executive;
/*     */ 
/*     */ import biq;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.executive.interfaces.IReturnValueHandler;
/*     */ import net.eq2online.macros.scripting.ReturnValueLogTo;
/*     */ import net.eq2online.macros.scripting.ReturnValueRaw;
/*     */ import net.eq2online.macros.scripting.Variable;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionStackEntry;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IReturnValueArray;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptParser;
/*     */ import net.eq2online.macros.scripting.exceptions.ScriptExceptionVoidResult;
/*     */ import net.eq2online.macros.scripting.parser.DeniedAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.util.Game;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroAction
/*     */   implements IMacroAction, IReturnValueHandler
/*     */ {
/*     */   protected IMacroActionProcessor actionProcessor;
/*     */   protected IReturnValueHandler returnValueHandler;
/*     */   private IScriptAction action;
/*     */   private String[] params;
/*     */   private String rawParams;
/*     */   private String unparsedParams;
/*     */   private Object state;
/*  61 */   private String outVar = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MacroAction(IMacroActionProcessor actionProcessor) {
/*  70 */     this.actionProcessor = actionProcessor;
/*  71 */     this.returnValueHandler = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroAction(IMacroActionProcessor actionProcessor, IScriptAction action, String rawParams, String unparsedParams, String[] params, String outVar) {
/*  83 */     this(actionProcessor);
/*     */     
/*  85 */     this.action = action;
/*  86 */     this.rawParams = rawParams;
/*  87 */     this.unparsedParams = unparsedParams;
/*  88 */     this.params = params;
/*  89 */     this.outVar = outVar;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReturnValueHandler(IReturnValueHandler returnValueHandler) {
/*  94 */     this.returnValueHandler = returnValueHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroActionProcessor getActionProcessor() {
/* 100 */     return this.actionProcessor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasOutVar() {
/* 106 */     return (this.outVar != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOutVarName() {
/* 112 */     return (this.outVar != null) ? this.outVar : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExecuteNow(IMacroActionContext context, IMacro macro) {
/* 123 */     return (this.action.isStackPopOperator() || this.action
/* 124 */       .isStackPushOperator() || this.action
/* 125 */       .isConditionalOperator() || this.action
/* 126 */       .canExecuteNow(context.getActionProvider(), macro, this, this.rawParams, this.params));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshPermissions(IScriptParser parser) {
/* 137 */     if (this.action != null && !this.action.checkExecutePermission()) {
/*     */       
/* 139 */       Log.info("Disabling disallowed script action {0}", new Object[] { this.action.toString().toUpperCase() });
/* 140 */       this.rawParams = this.action.toString();
/* 141 */       this.action = (IScriptAction)new DeniedAction(parser.getContext());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(IMacroActionContext context, IMacro macro, boolean stop, boolean allowLatent) {
/* 154 */     IScriptActionProvider provider = context.getActionProvider();
/* 155 */     if (this.action.isStackPopOperator())
/*     */     {
/* 157 */       return executeStackPop(context, macro, provider, allowLatent);
/*     */     }
/* 159 */     if (this.action.isStackPushOperator()) {
/*     */       
/* 161 */       executeStackPush(context, macro, provider, allowLatent);
/*     */     }
/* 163 */     else if (this.action.isConditionalOperator()) {
/*     */       
/* 165 */       executeConditional(context, macro, provider, allowLatent);
/*     */     }
/* 167 */     else if (this.actionProcessor.getConditionalExecutionState()) {
/*     */       
/* 169 */       executeAction(context, macro, provider, allowLatent);
/*     */     } 
/*     */     
/* 172 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean executeStackPop(IMacroActionContext context, IMacro macro, IScriptActionProvider provider, boolean allowLatent) {
/* 177 */     IMacroActionStackEntry topmost = this.actionProcessor.getTopStackEntry();
/* 178 */     if (!allowLatent || topmost == null)
/*     */     {
/* 180 */       return true;
/*     */     }
/*     */     
/* 183 */     if (topmost.isStackPushOperator()) {
/*     */       
/* 185 */       if (topmost.canBePoppedBy(this))
/*     */       {
/* 187 */         ScriptAction.onActionSkipped();
/* 188 */         topmost.executeStackPop(this.actionProcessor, context, macro, this);
/* 189 */         return this.actionProcessor.popStack();
/*     */       }
/*     */     
/* 192 */     } else if (topmost.isConditionalOperator()) {
/*     */       
/* 194 */       if (topmost.isConditionalElseOperator(this)) {
/*     */         
/* 196 */         if (!topmost.getElseFlag())
/*     */         {
/* 198 */           ScriptAction.onActionSkipped();
/* 199 */           this.action.executeConditionalElse(provider, macro, this, this.rawParams, this.params, topmost);
/*     */         }
/*     */       
/* 202 */       } else if (topmost.matchesConditionalOperator(this)) {
/*     */         
/* 204 */         ScriptAction.onActionSkipped();
/* 205 */         topmost.executeStackPop(this.actionProcessor, context, macro, this);
/* 206 */         this.actionProcessor.popStack();
/*     */       } 
/*     */     } 
/*     */     
/* 210 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeStackPush(IMacroActionContext context, IMacro macro, IScriptActionProvider provider, boolean allowLatent) {
/* 215 */     if (!allowLatent) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 221 */     ScriptAction.onActionSkipped();
/*     */     
/* 223 */     if (this.action.executeStackPush(provider, macro, this, this.rawParams, this.params) && this.actionProcessor.getConditionalExecutionState()) {
/*     */       
/* 225 */       this.actionProcessor.pushStack(this, true);
/*     */     }
/*     */     else {
/*     */       
/* 229 */       this.actionProcessor.pushStack(-1, this, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeConditional(IMacroActionContext context, IMacro macro, IScriptActionProvider provider, boolean allowLatent) {
/* 235 */     if (!allowLatent) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 241 */     ScriptAction.onActionSkipped();
/*     */     
/* 243 */     boolean conditional = this.action.executeConditional(provider, macro, this, this.rawParams, this.params);
/* 244 */     this.actionProcessor.pushStack(-1, this, conditional);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void executeAction(IMacroActionContext context, IMacro macro, IScriptActionProvider provider, boolean allowLatent) {
/* 250 */     ScriptAction.onActionExecuted();
/*     */ 
/*     */     
/* 253 */     IReturnValue returnValue = this.action.execute(provider, macro, this, this.rawParams, this.params);
/* 254 */     this.returnValueHandler.handleReturnValue(provider, macro, this, returnValue);
/*     */     
/* 256 */     if (this.outVar != null) {
/*     */       
/* 258 */       if (returnValue.isVoid())
/*     */       {
/* 260 */         throw new ScriptExceptionVoidResult(this.action.toString().toUpperCase());
/*     */       }
/*     */       
/* 263 */       String parsedOutVar = provider.expand(macro, this.outVar, false);
/* 264 */       String varName = Variable.getValidVariableOrArraySpecifier(parsedOutVar);
/* 265 */       if (Variable.couldBeArraySpecifier(parsedOutVar) && varName != null) {
/*     */         
/* 267 */         if (returnValue instanceof IReturnValueArray)
/*     */         {
/* 269 */           IReturnValueArray array = (IReturnValueArray)returnValue;
/*     */           
/* 271 */           if (!array.shouldAppend())
/*     */           {
/* 273 */             provider.clearArray(macro, varName);
/*     */           }
/*     */           
/* 276 */           for (String stringValue : array.getStrings())
/*     */           {
/* 278 */             provider.pushValueToArray(macro, varName, stringValue);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 283 */           provider.pushValueToArray(macro, varName, returnValue.getString());
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 288 */         provider.setVariable(macro, parsedOutVar, returnValue);
/*     */       } 
/*     */     } 
/*     */     
/* 292 */     provider.updateVariableProviders(false);
/*     */ 
/*     */     
/* 295 */     this.state = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleReturnValue(IScriptActionProvider provider, IMacro macro, IMacroAction instance, IReturnValue returnValue) throws ScriptExceptionVoidResult {
/* 308 */     if (returnValue == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 313 */     String remoteMessage = returnValue.getRemoteMessage();
/* 314 */     if (remoteMessage != null)
/*     */     {
/* 316 */       provider.actionSendChatMessage(macro, instance, remoteMessage);
/*     */     }
/*     */     
/* 319 */     String localMessage = returnValue.getLocalMessage();
/* 320 */     if (localMessage != null)
/*     */     {
/* 322 */       if (localMessage.equals("")) {
/*     */         
/* 324 */         biq inGameGui = Game.getIngameGui();
/*     */         
/* 326 */         if (inGameGui != null && inGameGui.d() != null)
/*     */         {
/* 328 */           inGameGui.d().a(false);
/*     */         }
/*     */       }
/* 331 */       else if (returnValue instanceof ReturnValueLogTo) {
/*     */         
/* 333 */         provider.actionAddLogMessage(((ReturnValueLogTo)returnValue).getTarget(), localMessage);
/*     */       }
/* 335 */       else if (returnValue instanceof ReturnValueRaw) {
/*     */         
/* 337 */         Game.addChatMessage(((ReturnValueRaw)returnValue).getRawMessage());
/*     */       }
/*     */       else {
/*     */         
/* 341 */         provider.actionAddChatMessage(localMessage);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStopped(IMacroActionProcessor processor, IMacroActionContext context, IMacro macro) {
/* 355 */     if (this.action != null)
/*     */     {
/* 357 */       this.action.onStopped(context.getActionProvider(), macro, this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeStackPop(IMacroActionProcessor processor, IMacroActionContext context, IMacro macro, IMacroAction popAction) {
/* 371 */     return this.action.executeStackPop(context.getActionProvider(), macro, this, this.rawParams, this.params, popAction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBreak(IMacroActionProcessor processor, IScriptActionProvider provider, IMacro macro, IMacroAction breakAction) {
/* 384 */     return this.action.canBreak(processor, provider, macro, this, breakAction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 393 */     return (this.action != null) ? this.action.toString() : "ERROR";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setState(Object state) {
/* 403 */     this.state = state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScriptAction getAction() {
/* 412 */     return this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getState() {
/* 422 */     return (T)this.state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getParams() {
/* 431 */     return this.params;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRawParams() {
/* 440 */     return this.rawParams;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUnparsedParams() {
/* 445 */     return this.unparsedParams;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClocked() {
/* 454 */     return (this.action != null) ? this.action.isClocked() : true;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\executive\MacroAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */