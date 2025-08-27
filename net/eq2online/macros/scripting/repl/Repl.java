/*     */ package net.eq2online.macros.scripting.repl;
/*     */ import bib;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.executive.MacroActionProcessor;
/*     */ import net.eq2online.macros.interfaces.IMacroParamStorage;
/*     */ import net.eq2online.macros.scripting.ActionParser;
/*     */ import net.eq2online.macros.scripting.ReturnValueLog;
/*     */ import net.eq2online.macros.scripting.ReturnValueLogTo;
/*     */ import net.eq2online.macros.scripting.api.IExpressionEvaluator;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IReturnValueArray;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptParser;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.api.ReturnValue;
/*     */ import net.eq2online.macros.scripting.exceptions.ScriptException;
/*     */ import net.eq2online.macros.scripting.exceptions.ScriptExceptionVoidResult;
/*     */ import net.eq2online.macros.scripting.interfaces.IScriptActionPreProcessor;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.repl.commands.IReplConsoleCommand;
/*     */ import net.eq2online.macros.scripting.repl.commands.IReplConsoleCommandBlocking;
/*     */ import net.eq2online.macros.scripting.repl.commands.ReplConsoleCommandBegin;
/*     */ import net.eq2online.macros.scripting.repl.commands.ReplConsoleCommandEdit;
/*     */ import net.eq2online.macros.scripting.repl.commands.ReplConsoleCommandKill;
/*     */ import net.eq2online.macros.scripting.repl.commands.ReplConsoleCommandLive;
/*     */ import net.eq2online.macros.scripting.repl.commands.ReplConsoleCommandRun;
/*     */ import net.eq2online.macros.scripting.repl.commands.ReplConsoleCommandSay;
/*     */ import net.eq2online.macros.scripting.repl.commands.ReplConsoleCommandVersion;
/*     */ import net.eq2online.util.Util;
/*     */ 
/*     */ public class Repl implements IReturnValueHandler, IErrorLogger, IMacroHost {
/*     */   class ScriptActionEval extends ScriptAction {
/*     */     ScriptActionEval(ScriptContext context) {
/*  41 */       super(context, "eval");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String expression, String[] params) {
/*  47 */       if (expression.length() < 1)
/*     */       {
/*  49 */         return (IReturnValue)new ReturnValueLog(I18n.get("repl.error.eval"));
/*     */       }
/*     */       
/*  52 */       if (params[1].startsWith("%")) {
/*     */         
/*  54 */         String expander = expression.replace("%", "").replaceAll("(^| )(.)", "$1%$2").replaceAll("(.)($| )", "$1%$2");
/*  55 */         return (IReturnValue)new ReturnValue("§b" + Util.convertAmpCodes(provider.expand(macro, expander, false)));
/*     */       } 
/*     */       
/*  58 */       if (expression.trim().startsWith("=")) {
/*     */         
/*  60 */         int pos = expression.indexOf('=');
/*     */         
/*  62 */         String expanded = provider.expand(macro, expression.substring(pos + 1).trim(), true);
/*  63 */         IExpressionEvaluator evaluator = provider.getExpressionEvaluator(macro, provider.expand(macro, expanded, true));
/*  64 */         return (IReturnValue)new ReturnValue(evaluator.evaluate());
/*     */       } 
/*     */       
/*  67 */       return (IReturnValue)new ReturnValue("§b" + Util.convertAmpCodes(provider.expand(macro, expression, false)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class ReplVariableCache
/*     */     extends VariableCache
/*     */   {
/*     */     public Object getVariable(String variableName) {
/*  76 */       return getCachedValue(variableName);
/*     */     }
/*     */   }
/*     */   
/*  80 */   private static final IVariableProvider VARIABLE_CACHE = (IVariableProvider)new ReplVariableCache();
/*     */   
/*     */   private final Macros macros;
/*     */   
/*     */   private final bib mc;
/*     */   
/*     */   private final ScriptContext context;
/*     */   
/*     */   private final IReplConsole console;
/*     */   
/*     */   private final IScriptParser parser;
/*     */   
/*     */   private final IMacroActionContext actionContext;
/*     */   
/*     */   private final IMacro macro;
/*     */   
/*     */   private final IMacroParamStorage paramStorage;
/*     */   
/*  98 */   private final List<IReplConsoleCommand> commands = new ArrayList<>();
/*     */   
/*     */   private MacroActionProcessor runtime;
/*     */   
/*     */   private IVariableProvider contextProvider;
/*     */   
/*     */   private IReplConsoleCommandBlocking currentCommand;
/*     */   
/*     */   private boolean verbose;
/*     */   
/*     */   private boolean live;
/*     */ 
/*     */   
/*     */   public Repl(Macros macros, bib minecraft, IReplConsole console) {
/* 112 */     this(macros, minecraft, console, ScriptContext.MAIN);
/*     */   }
/*     */ 
/*     */   
/*     */   public Repl(Macros macros, bib minecraft, IReplConsole console, ScriptContext context) {
/* 117 */     this.macros = macros;
/* 118 */     this.mc = minecraft;
/* 119 */     this.console = console;
/*     */     
/* 121 */     this.context = context;
/* 122 */     this.parser = initParser(this.context);
/* 123 */     this.actionContext = (IMacroActionContext)new MacroActionContext(this.context, this.context.getScriptActionProvider(), VARIABLE_CACHE);
/* 124 */     this.macro = this.macros.createFloatingTemplate("", "REPL").createInstance(false, this.actionContext);
/* 125 */     this.paramStorage = (IMacroParamStorage)this.macro.getTemplate();
/*     */     
/* 127 */     this.console.addLine(I18n.get("repl.message"));
/* 128 */     this.console.addLine(I18n.get("repl.console.live." + (isLive() ? "enabled" : "disabled")));
/* 129 */     this.console.addLine(I18n.get("repl.ready"));
/*     */     
/* 131 */     initConsoleCommands();
/*     */   }
/*     */ 
/*     */   
/*     */   private IScriptParser initParser(ScriptContext context) {
/* 136 */     ReplParser replParser = new ReplParser(context, this);
/* 137 */     replParser.addActionParser((ActionParser)new ActionParserEval(context, (IScriptAction)new ScriptActionEval(context)));
/* 138 */     replParser.addActionParser((ActionParser)new ActionParserAction(context));
/* 139 */     replParser.addActionParser((ActionParser)new ActionParserDirective(context));
/* 140 */     replParser.addActionParser((ActionParser)new ActionParserAssignment(context));
/* 141 */     return (IScriptParser)replParser;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initConsoleCommands() {
/* 146 */     this.commands.add(new ReplConsoleCommandHelp(this));
/* 147 */     this.commands.add(new ReplConsoleCommandBegin(this));
/* 148 */     this.commands.add(new ReplConsoleCommandEnd(this));
/* 149 */     this.commands.add(new ReplConsoleCommandCls(this));
/* 150 */     this.commands.add(new ReplConsoleCommandCat(this, this.macros));
/* 151 */     this.commands.add(new ReplConsoleCommandEdit(this, this.macros));
/* 152 */     this.commands.add(new ReplConsoleCommandRun(this, this.macros, this.mc, this.macro, this.paramStorage));
/* 153 */     this.commands.add(new ReplConsoleCommandExit(this));
/* 154 */     this.commands.add(new ReplConsoleCommandList(this, this.macros));
/* 155 */     this.commands.add(new ReplConsoleCommandLive(this));
/* 156 */     this.commands.add(new ReplConsoleCommandSay(this, this.macros, this.mc));
/* 157 */     this.commands.add(new ReplConsoleCommandShutdown(this, this.mc));
/* 158 */     this.commands.add(new ReplConsoleCommandRemove(this, this.macros));
/* 159 */     this.commands.add(new ReplConsoleCommandTasks(this, this.macros));
/* 160 */     this.commands.add(new ReplConsoleCommandKill(this, this.macros));
/* 161 */     this.commands.add(new ReplConsoleCommandWhoami(this, this.mc));
/* 162 */     this.commands.add(new ReplConsoleCommandVersion(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLive() {
/* 167 */     return this.live;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLive(boolean live) {
/* 172 */     this.live = live;
/*     */   }
/*     */ 
/*     */   
/*     */   public ScriptContext getContext() {
/* 177 */     return this.context;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IReplConsoleCommand> getCommands() {
/* 182 */     return Collections.unmodifiableList(this.commands);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick(boolean clock) {
/* 187 */     if (this.currentCommand != null && !this.currentCommand.isBlocked())
/*     */     {
/* 189 */       this.currentCommand = null;
/*     */     }
/*     */     
/* 192 */     runMacro(clock);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyTyped(char keyChar, int keyCode) {
/* 197 */     if (isMacroRunning()) {
/*     */       
/* 199 */       if (keyChar == '\003' || keyChar == '\005' || keyChar == '\032' || keyCode == 1) {
/*     */         
/* 201 */         stopMacro();
/* 202 */         this.console.addLine(I18n.get("repl.aborted"));
/*     */       } 
/* 204 */       return true;
/*     */     } 
/*     */     
/* 207 */     if (this.currentCommand != null) {
/*     */       
/* 209 */       this.currentCommand.keyTyped(this.console, keyCode, keyChar);
/* 210 */       if (!this.currentCommand.isBlocked())
/*     */       {
/* 212 */         this.currentCommand = null;
/*     */       }
/* 214 */       return true;
/*     */     } 
/*     */     
/* 217 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(String commandLine) {
/*     */     try {
/* 224 */       if (commandLine.matches("^/[a-zA-Z].*")) {
/*     */         
/* 226 */         if (!this.live) {
/*     */           
/* 228 */           this.console.addLine(I18n.get("repl.console.live.cmdfailed"));
/*     */           return;
/*     */         } 
/* 231 */         commandLine = "say " + commandLine;
/*     */       } 
/*     */       
/* 234 */       String command = commandLine;
/* 235 */       if (command.matches("^\\.[^ ].*$"))
/*     */       {
/* 237 */         command = "run " + command.substring(1);
/*     */       }
/*     */       
/* 240 */       StringBuilder rawString = new StringBuilder();
/* 241 */       String[] parts = ScriptCore.tokenize(command, ' ', false, '"', '\\', rawString);
/* 242 */       if (parts.length > 0) {
/*     */         
/* 244 */         parts[0] = parts[0].toLowerCase().trim();
/*     */         
/* 246 */         for (IReplConsoleCommand cmd : this.commands) {
/*     */           
/* 248 */           if (cmd.execute(this.console, parts, commandLine)) {
/*     */             
/* 250 */             if (cmd instanceof IReplConsoleCommandBlocking && ((IReplConsoleCommandBlocking)cmd).isBlocked())
/*     */             {
/* 252 */               this.currentCommand = (IReplConsoleCommandBlocking)cmd;
/*     */             }
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 260 */       playMacro(commandLine);
/*     */     }
/* 262 */     catch (Exception ex) {
/*     */       
/* 264 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void playMacro(String commandLine) {
/* 270 */     IScriptActionPreProcessor prompt = (IScriptActionPreProcessor)this.context.getCore().getAction("prompt");
/* 271 */     if (prompt != null)
/*     */     {
/* 273 */       commandLine = prompt.preProcess(commandLine);
/*     */     }
/*     */     
/* 276 */     playMacro(commandLine, true, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void playMacro(String macro, boolean verbose, IVariableProvider contextProvider) {
/* 281 */     if (this.runtime != null)
/*     */     {
/* 283 */       stopMacro();
/*     */     }
/*     */     
/* 286 */     this.verbose = verbose;
/* 287 */     this.runtime = MacroActionProcessor.compile(this.parser, "$${" + macro + "}$$", 100, 100, this);
/* 288 */     this.runtime.setReturnValueHandler(this);
/* 289 */     this.contextProvider = contextProvider;
/* 290 */     if (this.contextProvider != null)
/*     */     {
/* 292 */       this.macro.registerVariableProvider(this.contextProvider);
/*     */     }
/* 294 */     runMacro(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private void runMacro(boolean clock) {
/* 299 */     if (this.runtime != null) {
/*     */       
/* 301 */       boolean run = false;
/*     */ 
/*     */       
/*     */       try {
/* 305 */         run = this.runtime.execute(this.macro, this.actionContext, false, true, clock);
/*     */       }
/* 307 */       catch (ScriptException ex) {
/*     */         
/* 309 */         this.console.addLine(I18n.get("repl.error.action", new Object[] { ex.getMessage() }));
/*     */       
/*     */       }
/* 312 */       catch (Exception ex) {
/*     */         
/* 314 */         String message = (ex.getMessage() != null) ? ("" + ex.getMessage()) : ("" + ex.getClass().getSimpleName());
/* 315 */         this.console.addLine(I18n.get("repl.error.action", new Object[] { message }));
/*     */       } 
/*     */       
/* 318 */       if (!run)
/*     */       {
/* 320 */         clearRuntime();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopMacro() {
/* 327 */     if (this.runtime != null) {
/*     */       
/* 329 */       this.runtime.onStopped(this.macro, this.actionContext);
/* 330 */       clearRuntime();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void clearRuntime() {
/* 336 */     this.runtime = null;
/* 337 */     if (this.contextProvider != null) {
/*     */       
/* 339 */       this.macro.unregisterVariableProvider(this.contextProvider);
/* 340 */       this.contextProvider = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReady() {
/* 346 */     return (!isMacroRunning() && this.currentCommand == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlocked() {
/* 351 */     return (this.currentCommand != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMacroRunning() {
/* 356 */     return (this.runtime != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleReturnValue(IScriptActionProvider provider, IMacro macro, IMacroAction instance, IReturnValue returnValue) throws ScriptExceptionVoidResult {
/* 363 */     if (returnValue == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 368 */     boolean logged = false;
/*     */     
/* 370 */     String remoteMessage = returnValue.getRemoteMessage();
/* 371 */     if (remoteMessage != null) {
/*     */       
/* 373 */       if (!remoteMessage.isEmpty())
/*     */       {
/* 375 */         if (isLive()) {
/*     */           
/* 377 */           provider.actionSendChatMessage(macro, instance, remoteMessage);
/*     */         }
/*     */         else {
/*     */           
/* 381 */           this.console.addLine(I18n.get("repl.return.chat", new Object[] { this.mc.h.h_(), remoteMessage }));
/*     */         } 
/*     */       }
/* 384 */       logged = true;
/*     */     } 
/*     */     
/* 387 */     String localMessage = returnValue.getLocalMessage();
/* 388 */     if (localMessage != null) {
/*     */       
/* 390 */       if (!localMessage.isEmpty())
/*     */       {
/* 392 */         if (localMessage.equals("")) {
/*     */           
/* 394 */           this.console.clearScreen();
/*     */         }
/* 396 */         else if (returnValue instanceof ReturnValueLogTo) {
/*     */           
/* 398 */           this.console.addLine(I18n.get("repl.return.logto", new Object[] { ((ReturnValueLogTo)returnValue).getTarget(), localMessage }));
/*     */         }
/*     */         else {
/*     */           
/* 402 */           this.console.addLine(I18n.get("repl.return.log", new Object[] { localMessage }));
/*     */         } 
/*     */       }
/* 405 */       logged = true;
/*     */     } 
/*     */     
/* 408 */     if (!this.verbose) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 413 */     if (returnValue instanceof IReturnValueArray) {
/*     */       
/* 415 */       IReturnValueArray array = (IReturnValueArray)returnValue;
/*     */       
/* 417 */       this.console.addLine(I18n.get("repl.return.array.start"));
/* 418 */       List<String> strings = array.getStrings();
/* 419 */       for (int i = 0; i < strings.size(); i++) {
/*     */         
/* 421 */         this.console.addLine(I18n.get("repl.return.array.entry", new Object[] { Integer.valueOf(i), strings.get(i) }));
/*     */       } 
/* 423 */       this.console.addLine(I18n.get("repl.return.array.end"));
/*     */     }
/* 425 */     else if (!logged) {
/*     */       
/* 427 */       if (returnValue.isVoid()) {
/*     */         
/* 429 */         this.console.addLine(I18n.get("repl.return.void"));
/*     */       }
/*     */       else {
/*     */         
/* 433 */         this.console.addLine(I18n.get("repl.return.value", new Object[] { returnValue.getString() }));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void logError(String errorMessage) {
/* 441 */     this.console.addLine(errorMessage);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void trace(int id, int pointer, String state, String action) {
/* 447 */     this.macros.trace(id, pointer, state, action);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addScriptError(IScriptActionProvider provider, IMacro macro, String message) {
/* 453 */     this.console.addLine(I18n.get("repl.error.message", new Object[] { message }));
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\Repl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */