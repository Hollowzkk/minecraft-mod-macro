/*     */ package net.eq2online.macros.core;
/*     */ import bib;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.core.executive.MacroActionProcessor;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.interfaces.IMacroParamStorage;
/*     */ import net.eq2online.macros.scripting.VariableExpander;
/*     */ import net.eq2online.macros.scripting.api.ICounterProvider;
/*     */ import net.eq2online.macros.scripting.api.IExpressionEvaluator;
/*     */ import net.eq2online.macros.scripting.api.IFlagProvider;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*     */ import net.eq2online.macros.scripting.api.IMacroTemplate;
/*     */ import net.eq2online.macros.scripting.api.IMutableArrayProvider;
/*     */ import net.eq2online.macros.scripting.api.IParameterProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptParser;
/*     */ import net.eq2online.macros.scripting.api.IStringProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableListener;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.exceptions.ScriptException;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class Macro extends MacroParamTarget implements IMacro {
/*     */   public static final String PREFIX_PARAM = "$$";
/*     */   public static final String REPLACEMENT_PIPE = "";
/*     */   
/*     */   static class MacroStatus implements IMacro.IMacroStatus {
/*  40 */     private static final SimpleDateFormat FORMAT_STARTTIME = new SimpleDateFormat("hh:mm:ss");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  45 */     private static final SimpleDateFormat FORMAT_RUNTIME = new SimpleDateFormat("'§a'mm'§f':'§e'ss'§f'.'§a'SSS");
/*     */     
/*     */     private final Macro macro;
/*     */ 
/*     */     
/*     */     MacroStatus(Macro macro) {
/*  51 */       this.macro = macro;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public IMacro getMacro() {
/*  57 */       return this.macro;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long getRunTime() {
/*  63 */       return System.currentTimeMillis() - this.macro.buildTime;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long getStartTime() {
/*  69 */       return this.macro.buildTime;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getFormattedRunTime() {
/*  75 */       return FORMAT_RUNTIME.format(Long.valueOf(getRunTime()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getFormattedStartTime() {
/*  81 */       return FORMAT_STARTTIME.format(Long.valueOf(getStartTime()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/*     */       try {
/*  89 */         String displayName = this.macro.getDisplayName();
/*  90 */         String formattedStartTime = getFormattedStartTime();
/*  91 */         String formattedRunTime = getFormattedRunTime();
/*  92 */         return String.format("§f[§4%s§f]§b Start§f: §a%s§b Run§f: §2%s", new Object[] { displayName, formattedStartTime, formattedRunTime });
/*     */       
/*     */       }
/*  95 */       catch (Exception ex) {
/*     */         
/*  97 */         return ex.getMessage();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   protected static final Pattern PATTERN_STOP = Pattern.compile("(?<![\\x5C])\\x24\\x24!");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   protected static final Pattern PATTERN_SCRIPT = Pattern.compile("(?<![\\x5C])\\x24\\x24\\{(.*?)(\\}\\x24\\x24|$)");
/*     */ 
/*     */ 
/*     */   
/*     */   private final int id;
/*     */ 
/*     */ 
/*     */   
/*     */   private final MacroTemplate template;
/*     */ 
/*     */ 
/*     */   
/*     */   private final MacroStatus status;
/*     */ 
/*     */   
/*     */   private final IScriptActionProvider scriptActionProvider;
/*     */ 
/*     */   
/*     */   private final IMacroActionContext context;
/*     */ 
/*     */   
/* 142 */   private final List<IVariableProvider> extraProviders = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   private final Map<String, Object> variables = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   private final Map<String, Object> stateData = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   private MacroPlaybackType playbackType = MacroPlaybackType.ONESHOT;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   private MacroTriggerType triggerType = MacroTriggerType.KEY;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   private long repeatRate = 1000L; private long lastTriggerTime = 0L;
/*     */ 
/*     */ 
/*     */   
/*     */   protected String keyDownMacro;
/*     */ 
/*     */ 
/*     */   
/*     */   protected String keyHeldMacro;
/*     */ 
/*     */   
/*     */   protected String keyUpMacro;
/*     */ 
/*     */   
/*     */   protected String condition;
/*     */ 
/*     */   
/*     */   protected boolean stop;
/*     */ 
/*     */   
/*     */   protected boolean built = false;
/*     */ 
/*     */   
/* 190 */   protected long buildTime = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MacroActionProcessor keyDownActions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MacroActionProcessor keyHeldActions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MacroActionProcessor keyUpActions;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean keyWasDown = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean killed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean dirty = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean synchronous = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Macro(Macros macros, bib minecraft, MacroTemplate owner, int id, MacroPlaybackType playbackType, MacroTriggerType triggerType, IMacroActionContext context) {
/* 235 */     super(macros, minecraft, context.getScriptContext());
/*     */     
/* 237 */     this.template = owner;
/* 238 */     this.status = new MacroStatus(this);
/* 239 */     this.keyDownMacro = owner.getKeyDownMacro();
/* 240 */     this.id = id;
/* 241 */     this.context = context;
/* 242 */     this.scriptActionProvider = context.getActionProvider();
/*     */     
/* 244 */     this.triggerType = triggerType;
/* 245 */     this.playbackType = playbackType;
/*     */     
/* 247 */     readTemplate();
/* 248 */     initInstanceVariables((IVariableListener)this);
/* 249 */     compile();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readTemplate() {
/* 254 */     switch (this.playbackType) {
/*     */       case ONESHOT:
/*     */         return;
/*     */ 
/*     */       
/*     */       case KEYSTATE:
/* 260 */         this.keyHeldMacro = this.template.getKeyHeldMacro();
/* 261 */         this.keyUpMacro = this.template.getKeyUpMacro();
/* 262 */         this.repeatRate = this.template.getRepeatRate();
/*     */ 
/*     */       
/*     */       case CONDITIONAL:
/* 266 */         this.keyUpMacro = this.template.getKeyUpMacro();
/* 267 */         this.condition = this.template.getMacroCondition();
/*     */     } 
/*     */ 
/*     */     
/* 271 */     throw new IllegalStateException("Reading template: " + this.playbackType);
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
/*     */   public void compile() {
/* 283 */     if (this.playbackType == MacroPlaybackType.KEYSTATE) {
/*     */       
/* 285 */       compileKeyStateMacro();
/*     */     }
/*     */     else {
/*     */       
/* 289 */       if (this.playbackType == MacroPlaybackType.CONDITIONAL)
/*     */       {
/* 291 */         preCompileConditionalMacro();
/*     */       }
/*     */       
/* 294 */       super.compile();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preCompileConditionalMacro() {
/* 303 */     String expandedCondition = (new VariableExpander(this.scriptActionProvider, this, this.condition, true)).toString();
/* 304 */     IExpressionEvaluator evaluator = this.scriptActionProvider.getExpressionEvaluator(this, expandedCondition);
/*     */     
/* 306 */     if (evaluator.evaluate() == 0)
/*     */     {
/* 308 */       this.keyDownMacro = this.keyUpMacro;
/*     */     }
/*     */     
/* 311 */     this.playbackType = MacroPlaybackType.ONESHOT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String compileMacro(String macro) {
/* 317 */     macro = processStops(macro);
/* 318 */     macro = processIncludes(macro);
/* 319 */     macro = processEscapes(macro);
/* 320 */     macro = processStops(macro);
/* 321 */     macro = processPrompts(macro);
/* 322 */     return macro;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compileKeyStateMacro() {
/* 330 */     this.keyDownMacro = processIncludes(this.keyDownMacro);
/* 331 */     this.keyHeldMacro = processIncludes(this.keyHeldMacro);
/* 332 */     this.keyUpMacro = processIncludes(this.keyUpMacro);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSynchronous(boolean synchronous) {
/* 338 */     this.synchronous = synchronous;
/* 339 */     if (synchronous)
/*     */     {
/* 341 */       this.playbackType = MacroPlaybackType.ONESHOT;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSynchronous() {
/* 348 */     return this.synchronous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void build() {
/* 357 */     if (this.built) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 362 */     this.built = true;
/* 363 */     this.buildTime = System.currentTimeMillis();
/*     */     
/* 365 */     if (this.context.getVariableProvider() instanceof IParameterProvider) {
/*     */       
/* 367 */       IParameterProvider paramProvider = (IParameterProvider)this.context.getVariableProvider();
/* 368 */       this.keyDownMacro = paramProvider.provideParameters(this.keyDownMacro);
/*     */     } 
/*     */     
/* 371 */     IScriptParser defaultParser = ScriptContext.MAIN.getCore().getParser();
/* 372 */     int maxInstructionsPerTick = (this.macros.getSettings()).maxInstructionsPerTick;
/* 373 */     int maxExecutionTime = (this.macros.getSettings()).maxExecutionTime;
/*     */     
/* 375 */     this.keyDownActions = MacroActionProcessor.compile(defaultParser, this.keyDownMacro, maxInstructionsPerTick, maxExecutionTime, this.macros);
/*     */     
/* 377 */     if (this.playbackType == MacroPlaybackType.KEYSTATE) {
/*     */       
/* 379 */       this.keyHeldActions = MacroActionProcessor.compile(defaultParser, this.keyHeldMacro, maxInstructionsPerTick, maxExecutionTime, this.macros);
/*     */       
/* 381 */       this.keyUpActions = MacroActionProcessor.compile(defaultParser, this.keyUpMacro, maxInstructionsPerTick, maxExecutionTime, this.macros);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshPermissions() {
/* 392 */     if (this.keyDownActions != null)
/*     */     {
/* 394 */       this.keyDownActions.refreshPermissions();
/*     */     }
/*     */     
/* 397 */     if (this.keyHeldActions != null)
/*     */     {
/* 399 */       this.keyHeldActions.refreshPermissions();
/*     */     }
/*     */     
/* 402 */     if (this.keyUpActions != null)
/*     */     {
/* 404 */       this.keyUpActions.refreshPermissions();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleCompleted() {
/* 414 */     this.mc.a(null);
/* 415 */     this.template.getMacroManager().playMacro(this, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleCancelled() {
/* 424 */     this.mc.a(null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroActionContext getContext() {
/* 430 */     return this.context;
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
/*     */   public boolean play(boolean trigger, boolean clock) throws ScriptException {
/* 443 */     if (this.killed)
/*     */     {
/* 445 */       return false;
/*     */     }
/*     */     
/* 448 */     this.context.onTick(clock);
/*     */     
/* 450 */     build();
/*     */     
/* 452 */     if (this.playbackType == MacroPlaybackType.ONESHOT) {
/*     */       
/* 454 */       if (this.synchronous) {
/*     */         
/* 456 */         while (this.keyDownActions.execute(this, this.context, this.stop, true, clock) && !this.killed);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 461 */         return false;
/*     */       } 
/*     */       
/* 464 */       return (this.keyDownActions.execute(this, this.context, this.stop, true, clock) && !this.killed);
/*     */     } 
/*     */     
/* 467 */     this.keyWasDown |= trigger;
/*     */     
/* 469 */     boolean keyDownStillPlaying = this.keyDownActions.execute(this, this.context, this.stop, true, clock);
/*     */     
/* 471 */     if (trigger) {
/*     */       
/* 473 */       long sinceLastTrigger = System.currentTimeMillis() - this.lastTriggerTime;
/*     */       
/* 475 */       if (sinceLastTrigger > this.repeatRate) {
/*     */         
/* 477 */         this.lastTriggerTime = System.currentTimeMillis();
/* 478 */         this.keyHeldActions.execute(this, this.context, false, false, clock);
/*     */       } 
/*     */       
/* 481 */       return !this.killed;
/*     */     } 
/* 483 */     if (this.keyWasDown)
/*     */     {
/* 485 */       return (this.keyUpActions.execute(this, this.context, this.stop, true, clock) | keyDownStillPlaying && !this.killed);
/*     */     }
/*     */     
/* 488 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String processStops(String macro) {
/* 497 */     Matcher stopPatternMatcher = PATTERN_STOP.matcher(macro);
/*     */     
/* 499 */     if (stopPatternMatcher.find()) {
/*     */       
/* 501 */       macro = macro.substring(0, stopPatternMatcher.start());
/* 502 */       this.stop = true;
/*     */     } 
/*     */     
/* 505 */     return macro;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initInstanceVariables(IVariableListener localContext) {
/* 510 */     localContext.setVariable("~CTRL", InputHandler.isControlDown());
/* 511 */     localContext.setVariable("~ALT", InputHandler.isAltDown());
/* 512 */     localContext.setVariable("~SHIFT", InputHandler.isShiftDown());
/*     */     
/* 514 */     localContext.setVariable("~LMOUSE", Mouse.isButtonDown(0));
/* 515 */     localContext.setVariable("~RMOUSE", Mouse.isButtonDown(1));
/* 516 */     localContext.setVariable("~MIDDLEMOUSE", Mouse.isButtonDown(2));
/*     */     
/* 518 */     for (int key = 0; key < 255; key++) {
/*     */       
/* 520 */       String keyName = Keyboard.getKeyName(key);
/*     */       
/* 522 */       if (keyName != null)
/*     */       {
/* 524 */         localContext.setVariable("~KEY_" + keyName.toUpperCase(), Keyboard.isKeyDown(key));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVariables(boolean clock) {
/* 532 */     this.template.updateVariables(clock);
/*     */     
/* 534 */     if (this.context.getVariableProvider() != null)
/*     */     {
/* 536 */       this.context.getVariableProvider().updateVariables(clock);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/* 543 */     for (IVariableProvider extraProvider : this.extraProviders) {
/*     */       
/* 545 */       Object variableValue = extraProvider.getVariable(variableName);
/* 546 */       if (variableValue != null) return variableValue;
/*     */     
/*     */     } 
/* 549 */     if (this.context.getVariableProvider() != null) {
/*     */       
/* 551 */       Object variableValue = this.context.getVariableProvider().getVariable(variableName);
/* 552 */       if (variableValue != null) return variableValue;
/*     */     
/*     */     } 
/* 555 */     if (this.variables.containsKey(variableName))
/*     */     {
/* 557 */       return this.variables.get(variableName);
/*     */     }
/*     */     
/* 560 */     return this.template.getVariable(variableName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getVariables() {
/* 566 */     Set<String> variables = new HashSet<>();
/*     */     
/* 568 */     for (IVariableProvider extraProvider : this.extraProviders)
/*     */     {
/* 570 */       variables.addAll(extraProvider.getVariables());
/*     */     }
/*     */     
/* 573 */     if (this.context.getVariableProvider() != null)
/*     */     {
/* 575 */       variables.addAll(this.context.getVariableProvider().getVariables());
/*     */     }
/*     */     
/* 578 */     variables.addAll(this.variables.keySet());
/* 579 */     variables.addAll(this.template.getVariables());
/*     */     
/* 581 */     return variables;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVariable(String variableName, boolean variableValue) {
/* 587 */     this.variables.put(variableName, Boolean.valueOf(variableValue));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVariable(String variableName, int variableValue) {
/* 593 */     this.variables.put(variableName, Integer.valueOf(variableValue));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVariable(String variableName, String variableValue) {
/* 599 */     this.variables.put(variableName, variableValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVariables(Map<String, Object> variables) {
/* 605 */     this.variables.putAll(variables);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerVariableProvider(IVariableProvider variableProvider) {
/* 611 */     if (!this.extraProviders.contains(variableProvider))
/*     */     {
/* 613 */       this.extraProviders.add(variableProvider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterVariableProvider(IVariableProvider variableProvider) {
/* 620 */     this.extraProviders.remove(variableProvider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getID() {
/* 631 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/* 640 */     return this.triggerType.getName(this.macros, this.id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroPlaybackType getPlaybackType() {
/* 650 */     return this.playbackType;
/*     */   }
/*     */ 
/*     */   
/*     */   public MacroTriggerType getTriggerType() {
/* 655 */     return this.triggerType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroTemplate getTemplate() {
/* 666 */     return this.template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroParamStorage getParamStore() {
/* 675 */     return this.template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IFlagProvider getFlagProvider() {
/* 684 */     return (IFlagProvider)this.template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ICounterProvider getCounterProvider() {
/* 693 */     return (ICounterProvider)this.template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IStringProvider getStringProvider() {
/* 702 */     return (IStringProvider)this.template;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMutableArrayProvider getArrayProvider() {
/* 708 */     return (IMutableArrayProvider)this.template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTargetString() {
/* 719 */     return this.keyDownMacro;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetString(String newScript) {
/* 725 */     this.keyDownMacro = newScript;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 734 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDirty() {
/* 743 */     return this.dirty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void kill() {
/* 752 */     this.killed = true;
/*     */     
/* 754 */     if (this.keyDownActions != null)
/*     */     {
/* 756 */       this.keyDownActions.onStopped(this, this.context);
/*     */     }
/*     */     
/* 759 */     if (this.keyHeldActions != null)
/*     */     {
/* 761 */       this.keyHeldActions.onStopped(this, this.context);
/*     */     }
/*     */     
/* 764 */     if (this.keyUpActions != null)
/*     */     {
/* 766 */       this.keyUpActions.onStopped(this, this.context);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDead() {
/* 776 */     return this.killed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> getStateData() {
/* 785 */     return this.stateData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getState(String key) {
/* 795 */     return (T)this.stateData.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void setState(String key, T value) {
/* 805 */     this.stateData.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRemainingParams() {
/* 811 */     if (!this.triggerType.supportsParams())
/*     */     {
/* 813 */       return false;
/*     */     }
/*     */     
/* 816 */     if (this.triggerType == MacroTriggerType.CONTROL) {
/*     */       
/* 818 */       DesignableGuiControl control = this.macros.getLayoutManager().getControls().getControl(this.id);
/* 819 */       if (!control.dispatchOnClick())
/*     */       {
/* 821 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 825 */     return this.params.hasRemainingParams();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacro.IMacroStatus getStatus() {
/* 831 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 837 */     return getStatus().toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String escapeReplacement(String param) {
/* 853 */     if (param == null) return "";
/*     */     
/* 855 */     return param.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$");
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\Macro.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */