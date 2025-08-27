/*     */ package net.eq2online.macros.core.executive;
/*     */ 
/*     */ import bib;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Deque;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.executive.interfaces.IMacroHost;
/*     */ import net.eq2online.macros.core.executive.interfaces.IReturnValueHandler;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionStackEntry;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptParser;
/*     */ import net.eq2online.macros.scripting.exceptions.ScriptException;
/*     */ import net.eq2online.macros.scripting.exceptions.ScriptExceptionInvalidContextSwitch;
/*     */ import net.eq2online.macros.scripting.exceptions.ScriptExceptionInvalidModeSwitch;
/*     */ import net.eq2online.macros.scripting.exceptions.ScriptExceptionStackOverflow;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
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
/*     */ public final class MacroActionProcessor
/*     */   implements IMacroActionProcessor
/*     */ {
/*  39 */   private static Pattern PATTERN_SCRIPT = Pattern.compile("\\x24\\x24\\{(.*?)\\}\\x24\\x24");
/*     */ 
/*     */   
/*     */   private final IScriptParser parser;
/*     */ 
/*     */   
/*     */   private final IMacroHost host;
/*     */ 
/*     */   
/*  48 */   private final List<IMacroAction> actions = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private final Deque<IMacroActionStackEntry> stack = new LinkedList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private int pointer = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean suspendProcessing = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IMacroAction pendingAction;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private static Object executionLock = new Object();
/*     */   
/*     */   private boolean safeMode = true;
/*     */   
/*     */   private final int maxExecutionTime;
/*     */   
/*     */   private final int originalMaxActions;
/*     */   
/*     */   private int maxActionsPerTick;
/*     */ 
/*     */   
/*     */   public static MacroActionProcessor compile(IScriptParser parser, String macro, int maxActionsPerTick, int maxExecutionTime, IMacroHost host) {
/*  88 */     macro = macro.replace("$${$${", "$${").replace("}$$}$$", "}$$");
/*  89 */     return new MacroActionProcessor(parser, macro, maxActionsPerTick, maxExecutionTime, host);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MacroActionProcessor(IScriptParser parser, String macro, int maxActionsPerTick, int maxExecutionTime, IMacroHost host) {
/*  97 */     this.parser = parser;
/*  98 */     this.maxExecutionTime = maxExecutionTime;
/*  99 */     this.originalMaxActions = maxActionsPerTick;
/* 100 */     this.maxActionsPerTick = maxActionsPerTick;
/* 101 */     this.host = host;
/*     */     
/* 103 */     Matcher scriptPatternMatcher = PATTERN_SCRIPT.matcher(macro);
/*     */     
/* 105 */     while (scriptPatternMatcher.find()) {
/*     */       
/* 107 */       int scriptStart = scriptPatternMatcher.start();
/* 108 */       String script = scriptPatternMatcher.group(1);
/* 109 */       if (scriptStart > 0) {
/*     */         
/* 111 */         String chatPart = macro.substring(0, scriptStart);
/* 112 */         this.actions.add(new MacroActionChat(this, chatPart));
/*     */       } 
/* 114 */       this.actions.addAll(this.parser.parseScript(this, script));
/* 115 */       macro = macro.substring(scriptPatternMatcher.end());
/* 116 */       scriptPatternMatcher.reset(macro);
/*     */     } 
/*     */     
/* 119 */     if (macro.length() > 0)
/*     */     {
/* 121 */       this.actions.add(new MacroActionChat(this, macro));
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReturnValueHandler(IReturnValueHandler returnValueHandler) {
/* 138 */     for (IMacroAction action : this.actions) {
/*     */       
/* 140 */       if (action instanceof MacroAction)
/*     */       {
/* 142 */         ((MacroAction)action).setReturnValueHandler(returnValueHandler);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IMacroHost getHost() {
/* 149 */     return this.host;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshPermissions() {
/* 158 */     for (IMacroAction action : this.actions)
/*     */     {
/* 160 */       action.refreshPermissions(this.parser);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void beginUnsafeBlock(IScriptActionProvider scriptActionProvider, IMacro macro, IMacroAction instance, int maxActions) {
/* 167 */     if (!this.safeMode) throw new ScriptExceptionInvalidModeSwitch();
/*     */     
/* 169 */     this.safeMode = false;
/* 170 */     this.maxActionsPerTick = maxActions;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endUnsafeBlock(IScriptActionProvider iScriptActionProvider, IMacro macro, IMacroAction instance) {
/* 176 */     if (this.safeMode) throw new ScriptExceptionInvalidModeSwitch();
/*     */     
/* 178 */     this.safeMode = true;
/* 179 */     this.maxActionsPerTick = this.originalMaxActions;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnsafe() {
/* 185 */     return !this.safeMode;
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
/*     */   public boolean execute(IMacro macro, IMacroActionContext context, boolean stop, boolean allowLatent, boolean clock) throws ScriptException {
/* 197 */     if (macro.isDead())
/*     */     {
/* 199 */       return false;
/*     */     }
/*     */     
/* 202 */     if (allowLatent) {
/*     */       
/* 204 */       int processedInstructionCount = 0;
/* 205 */       long systemTime = bib.I();
/*     */       
/* 207 */       this.suspendProcessing = false;
/*     */       
/* 209 */       while (this.pointer < this.actions.size()) {
/*     */         
/* 211 */         processedInstructionCount++;
/* 212 */         IMacroAction currentAction = this.actions.get(this.pointer);
/* 213 */         boolean actionIsAllowed = true;
/*     */         
/* 215 */         if ((clock || !currentAction.isClocked()) && (!actionIsAllowed || 
/* 216 */           !getConditionalExecutionState() || canExecuteNow(macro, context, currentAction))) {
/*     */           
/* 218 */           boolean continueExecuting = false;
/*     */           
/* 220 */           if (actionIsAllowed) {
/*     */             
/* 222 */             synchronized (executionLock) {
/*     */               
/* 224 */               this.host.trace(macro.getID(), this.pointer, getConditionalExecutionState() ? "RUN" : "   ", currentAction
/* 225 */                   .toString());
/* 226 */               continueExecuting = currentAction.execute(context, macro, (stop && this.actions.size() - this.pointer < 2), true);
/*     */             } 
/*     */             
/* 229 */             this.suspendProcessing |= (this.maxActionsPerTick > 0 && processedInstructionCount >= this.maxActionsPerTick) ? 1 : 0;
/* 230 */             this.suspendProcessing |= (this.maxExecutionTime > 0 && bib.I() - systemTime > this.maxExecutionTime) ? 1 : 0;
/*     */           } 
/*     */           
/* 233 */           if (continueExecuting) {
/*     */             
/* 235 */             advancePointer();
/* 236 */             if (this.suspendProcessing) return true;
/*     */           
/*     */           } 
/* 239 */           if (macro.isDead()) return false;
/*     */           
/*     */           continue;
/*     */         } 
/* 243 */         return true;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 248 */       if (this.stack.size() > 0)
/*     */       {
/* 250 */         String reason = "missing statement";
/* 251 */         IMacroActionStackEntry topStackEntry = getTopStackEntry();
/* 252 */         if (topStackEntry.isStackPushOperator() || topStackEntry.isConditionalOperator()) {
/*     */           
/* 254 */           IScriptAction action = topStackEntry.getAction().getAction();
/* 255 */           if (action instanceof ScriptAction)
/*     */           {
/* 257 */             reason = ((ScriptAction)action).getExpectedPopCommands();
/*     */           }
/*     */         } 
/*     */         
/* 261 */         this.host.addScriptError(context.getActionProvider(), macro, I18n.get("script.error.missingpop", new Object[] { reason }));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 266 */       if (this.pointer > 0)
/*     */       {
/* 268 */         throw new ScriptExceptionInvalidContextSwitch();
/*     */       }
/*     */       
/* 271 */       for (IMacroAction scriptAction : this.actions) {
/*     */         
/* 273 */         if (canExecuteNow(macro, context, scriptAction)) {
/*     */           
/* 275 */           scriptAction.execute(context, macro, false, false);
/* 276 */           if (macro.isDead()) return false;
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 282 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExecuteNow(IMacro macro, IMacroActionContext context, IMacroAction currentAction) {
/* 293 */     boolean canExecuteNow = (currentAction.canExecuteNow(context, macro) || macro.isSynchronous());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 300 */     this.pendingAction = canExecuteNow ? null : currentAction;
/* 301 */     return canExecuteNow;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void advancePointer() {
/* 309 */     this.pointer++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushStack(IMacroAction action, boolean conditional) throws ScriptExceptionStackOverflow {
/* 319 */     pushStack(this.pointer, action, conditional);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushStack(int ptr, IMacroAction action, boolean conditional) throws ScriptExceptionStackOverflow {
/* 329 */     if (this.stack.size() > 32)
/*     */     {
/* 331 */       throw new ScriptExceptionStackOverflow();
/*     */     }
/*     */     
/* 334 */     this.stack.addFirst(new MacroActionStackEntry(ptr, action, conditional));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean popStack() {
/* 343 */     IMacroActionStackEntry popped = null;
/*     */     
/* 345 */     if (this.stack.size() > 0) {
/*     */       
/* 347 */       popped = this.stack.removeFirst();
/* 348 */       int stackPointer = popped.getStackPointer();
/*     */       
/* 350 */       if (stackPointer > -1) {
/*     */         
/* 352 */         this.pointer = stackPointer;
/* 353 */         if (this.safeMode) this.suspendProcessing = true;
/*     */       
/*     */       } else {
/*     */         
/* 357 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 361 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroActionStackEntry getTopStackEntry() {
/* 371 */     return (this.stack.size() > 0) ? this.stack.getFirst() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getConditionalExecutionState() {
/* 381 */     if (this.stack.size() == 0) return true;
/*     */     
/* 383 */     for (IMacroActionStackEntry stackEntry : this.stack) {
/*     */       
/* 385 */       if (!stackEntry.getConditionalFlag()) return false;
/*     */     
/*     */     } 
/* 388 */     return true;
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
/*     */   public void breakLoop(IScriptActionProvider provider, IMacro macro, IMacroAction breakAction) {
/* 400 */     if (this.stack.size() == 0)
/*     */       return; 
/* 402 */     for (IMacroActionStackEntry stackEntry : this.stack) {
/*     */       
/* 404 */       if (stackEntry.getAction().canBreak(this, provider, macro, breakAction)) {
/*     */         
/* 406 */         stackEntry.setConditionalFlag(false);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onStopped(IMacro macro, IMacroActionContext context) {
/* 414 */     if (this.pendingAction != null)
/*     */     {
/*     */       
/* 417 */       this.pendingAction = null;
/*     */     }
/*     */     
/* 420 */     for (IMacroAction action : this.actions)
/*     */     {
/* 422 */       action.onStopped(this, context, macro);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\executive\MacroActionProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */