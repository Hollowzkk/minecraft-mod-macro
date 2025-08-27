/*     */ package net.eq2online.macros.scripting.parser;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeSet;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.Reflection;
/*     */ import net.eq2online.macros.scripting.IActionFilter;
/*     */ import net.eq2online.macros.scripting.IDocumentor;
/*     */ import net.eq2online.macros.scripting.IErrorLogger;
/*     */ import net.eq2online.macros.scripting.ScriptActionBase;
/*     */ import net.eq2online.macros.scripting.VariableExpander;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventManager;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventProvider;
/*     */ import net.eq2online.macros.scripting.api.IMessageFilter;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptParser;
/*     */ import net.eq2online.macros.scripting.api.IScriptedIterator;
/*     */ import net.eq2online.macros.scripting.api.IVariableListener;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ 
/*     */ public final class ScriptCore {
/*  31 */   private static final Map<ScriptContext, ScriptCore> contexts = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ScriptContext context;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IScriptActionProvider activeProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IMacroEventManager eventManager;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IScriptParser parser;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IMessageFilter messageFilter;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private final Map<String, IScriptAction> actions = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private final List<IScriptAction> actionsList = new LinkedList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private Map<String, Class<? extends IScriptedIterator>> iterators = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private Pattern actionRegex = Pattern.compile("");
/*     */ 
/*     */ 
/*     */   
/*     */   private IDocumentor documentor;
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean createCoreForContext(ScriptContext context, IScriptActionProvider provider, IMacroEventManager eventManager, IScriptParser defaultParser, IErrorLogger logger, IDocumentor documentor) {
/*  86 */     if (!contexts.containsKey(context) && !context.isCreated()) {
/*     */       
/*  88 */       ScriptCore core = new ScriptCore(context, provider, eventManager, defaultParser, logger, documentor);
/*  89 */       contexts.put(context, core);
/*  90 */       return true;
/*     */     } 
/*     */     
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ScriptCore(ScriptContext context, IScriptActionProvider provider, IMacroEventManager eventManager, IScriptParser parser, IErrorLogger logger, IDocumentor documentor) {
/* 104 */     this.context = context;
/* 105 */     this.context.setCore(this);
/*     */     
/* 107 */     registerScriptActionProvider(provider);
/* 108 */     this.eventManager = eventManager;
/* 109 */     this.parser = parser;
/* 110 */     this.documentor = documentor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerScriptAction(IScriptAction action) {
/*     */     try {
/* 122 */       registerAction(action);
/*     */     }
/* 124 */     catch (Throwable ex) {
/*     */       
/* 126 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IScriptAction getAction(String actionName) {
/* 132 */     return this.actions.get(actionName.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean registerAction(IScriptAction newAction) {
/* 142 */     if (this.actions.containsKey(newAction.toString())) {
/* 143 */       return false;
/*     */     }
/* 145 */     this.actions.put(newAction.toString(), newAction);
/* 146 */     this.actionsList.add(newAction);
/* 147 */     this.documentor.setDocumentation(newAction);
/* 148 */     updateScriptActionRegex();
/*     */     
/* 150 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateScriptActionRegex() {
/* 158 */     String actionList = "";
/* 159 */     char separator = ')';
/*     */     
/* 161 */     TreeSet<String> sortedActionNames = new TreeSet<>();
/* 162 */     for (IScriptAction action : this.actions.values())
/*     */     {
/* 164 */       sortedActionNames.add(action.toString());
/*     */     }
/*     */     
/* 167 */     for (String actionName : sortedActionNames) {
/*     */       
/* 169 */       actionList = actionName + separator + actionList;
/* 170 */       separator = '|';
/*     */     } 
/*     */     
/* 173 */     this.actionRegex = Pattern.compile("(" + actionList + "(?![a-zA-Z%])([\\(;]|)", 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, IScriptAction> getActions() {
/* 181 */     return Collections.unmodifiableMap(this.actions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IScriptAction> getActionsList() {
/* 189 */     return this.actionsList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerScriptActionProvider(IScriptActionProvider provider) {
/* 199 */     this.activeProvider = provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScriptActionProvider getScriptActionProvider() {
/* 209 */     return this.activeProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScriptParser getParser() {
/* 219 */     return this.parser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IDocumentor getDocumentor() {
/* 229 */     return this.documentor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerVariableProvider(IVariableProvider variableProvider) {
/* 239 */     if (this.activeProvider != null)
/*     */     {
/* 241 */       this.activeProvider.registerVariableProvider(variableProvider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerVariableListener(IVariableListener variableListener) {
/* 252 */     if (this.activeProvider != null)
/*     */     {
/* 254 */       this.activeProvider.registerVariableListener(variableListener);
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
/*     */   @Deprecated
/*     */   public static void setVariable(IScriptActionProvider provider, IMacro macro, String variableName, String variableValue) {
/* 271 */     int intValue = tryParseInt(variableValue, 0);
/* 272 */     boolean boolValue = parseBoolean(variableValue, intValue);
/*     */ 
/*     */     
/* 275 */     if (variableValue == null) variableValue = "";
/*     */     
/* 277 */     provider.setVariable(macro, variableName, variableValue, intValue, boolValue);
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
/*     */   @Deprecated
/*     */   public static void setVariable(IScriptActionProvider provider, IMacro macro, String variableName, int variableValue) {
/* 291 */     if (variableName.length() > 0)
/*     */     {
/* 293 */       provider.setVariable(macro, variableName, String.valueOf(variableValue), variableValue, (variableValue != 0));
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
/*     */   @Deprecated
/*     */   public static String parseVars(IScriptActionProvider provider, IMacro macro, String text, boolean quoteStrings) {
/* 311 */     return (new VariableExpander(provider, macro, text, quoteStrings)).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerIterator(String iteratorKey, Class<? extends IScriptedIterator> iterator) {
/* 322 */     if (this.iterators.containsKey(iteratorKey)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 328 */     this.iterators.put(iteratorKey, iterator);
/*     */   }
/*     */ 
/*     */   
/*     */   private String getIteratorKey(String iteratorName) {
/* 333 */     int pos = iteratorName.indexOf('(');
/* 334 */     if (pos > -1)
/*     */     {
/* 336 */       return iteratorName.substring(0, pos).toLowerCase();
/*     */     }
/* 338 */     return iteratorName.toLowerCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<? extends IScriptedIterator> getIterator(String iteratorName) {
/* 349 */     return this.iterators.get(getIteratorKey(iteratorName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasIterator(String iteratorName) {
/* 360 */     return this.iterators.containsKey(getIteratorKey(iteratorName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScriptedIterator createIterator(String iteratorName, IScriptActionProvider provider, IMacro macro) {
/* 369 */     Class<? extends IScriptedIterator> iteratorClass = getIterator(iteratorName);
/* 370 */     if (iteratorClass == null)
/*     */     {
/* 372 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 377 */       return createIterator(iteratorName, provider, macro, iteratorClass);
/*     */     }
/* 379 */     catch (InstantiationException ex) {
/*     */       
/* 381 */       Log.info("Unexpected InstantiationException creating iterator {0}: {1}", new Object[] { iteratorName, ex.getMessage() });
/*     */     }
/* 383 */     catch (IllegalAccessException ex) {
/*     */       
/* 385 */       Log.info("Unexpected security error creating iterator {0}: {1}", new Object[] { iteratorName, ex.getMessage() });
/*     */     }
/* 387 */     catch (InvocationTargetException ex) {
/*     */       
/* 389 */       Throwable cause = ex.getCause();
/* 390 */       Log.info("Unexpected error creating iterator {0}: {1}{2}", new Object[] { iteratorName, cause.getClass(), cause.getMessage() });
/*     */     } 
/*     */     
/* 393 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IScriptedIterator createIterator(String iteratorName, IScriptActionProvider provider, IMacro macro, Class<? extends IScriptedIterator> iteratorClass) throws InstantiationException, IllegalAccessException, InvocationTargetException {
/*     */     try {
/* 400 */       Constructor<? extends IScriptedIterator> ctor = iteratorClass.getDeclaredConstructor(new Class[] { IScriptActionProvider.class, IMacro.class });
/* 401 */       return ctor.newInstance(new Object[] { provider, macro });
/*     */     }
/* 403 */     catch (NoSuchMethodException noSuchMethodException) {
/*     */ 
/*     */       
/*     */       try {
/* 407 */         Constructor<? extends IScriptedIterator> ctor = iteratorClass.getDeclaredConstructor(new Class[] { IScriptActionProvider.class, IMacro.class, String.class });
/* 408 */         IScriptedIterator newInstance = ctor.newInstance(new Object[] { provider, macro, iteratorName });
/* 409 */         return newInstance;
/*     */       } catch (NoSuchMethodException ex) {
/* 411 */         ex.printStackTrace();
/*     */ 
/*     */         
/*     */         try {
/* 415 */           Constructor<? extends IScriptedIterator> ctor = iteratorClass.getDeclaredConstructor(new Class[] { String.class });
/* 416 */           return ctor.newInstance(new Object[] { iteratorName });
/*     */         }
/* 418 */         catch (NoSuchMethodException noSuchMethodException1) {
/*     */           
/* 420 */           return iteratorClass.newInstance();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } public void registerEventProvider(IMacroEventProvider eventProvider) {
/* 425 */     if (this.eventManager != null)
/*     */     {
/* 427 */       this.eventManager.registerEventProvider(eventProvider);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerMessageFilter(IMessageFilter messageFilter) {
/* 433 */     this.messageFilter = messageFilter;
/*     */   }
/*     */ 
/*     */   
/*     */   public IMessageFilter getMessageFilter() {
/* 438 */     return this.messageFilter;
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
/*     */   void initActions(IActionFilter actionFilter, IErrorLogger logger) {
/*     */     try {
/* 451 */       Class<? extends ScriptActionBase> packageClass = (Class)Class.forName("net.eq2online.macros.scripting.actions.lang.ScriptActionAssign");
/* 452 */       List<Class<? extends ScriptActionBase>> actions = Reflection.getSubclassesFor(ScriptActionBase.class, packageClass, "ScriptAction", logger);
/* 453 */       int loadedActions = 0;
/*     */       
/* 455 */       for (Class<? extends IScriptAction> action : actions) {
/*     */ 
/*     */         
/*     */         try {
/* 459 */           IScriptAction newAction = null;
/* 460 */           Constructor<IScriptAction> ctor = (Constructor)action.getDeclaredConstructor(new Class[] { ScriptContext.class });
/* 461 */           if (ctor != null) {
/*     */             
/* 463 */             newAction = ctor.newInstance(new Object[] { this.context });
/*     */             
/* 465 */             if (newAction != null)
/*     */             {
/* 467 */               if (actionFilter.pass(this.context, this, newAction))
/*     */               {
/* 469 */                 if (registerAction(newAction))
/* 470 */                   loadedActions++; 
/*     */               }
/*     */             }
/*     */           } 
/*     */         } catch (Exception ex) {
/* 475 */           ex.printStackTrace();
/*     */         } 
/*     */       } 
/* 478 */       Log.info("Script engine initialised, registered {0} script action(s)", new Object[] { Integer.valueOf(loadedActions) });
/*     */ 
/*     */       
/* 481 */       if (loadedActions == 0)
/*     */       {
/* 483 */         logger.logError("Script engine initialisation error");
/*     */       }
/*     */     }
/* 486 */     catch (ClassNotFoundException ex) {
/*     */       
/* 488 */       logger.logError("Script engine initialisation error, package not found");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLanguage(String language) {
/* 494 */     this.documentor.loadXml(language);
/*     */     
/* 496 */     for (IScriptAction action : this.actionsList)
/*     */     {
/* 498 */       this.documentor.setDocumentation(action);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean parseBoolean(String variableValue) {
/* 509 */     int intValue = tryParseInt(variableValue, 0);
/* 510 */     return parseBoolean(variableValue, intValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean parseBoolean(String variableValue, int intValue) {
/* 520 */     return (variableValue == null || variableValue.toLowerCase().equals("true") || intValue != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int tryParseInt(String value, int defaultValue) {
/* 531 */     if (value == null) return defaultValue;  
/* 532 */     try { return Integer.parseInt(value.trim().replaceAll(",", "")); } catch (NumberFormatException numberFormatException) { return defaultValue; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long tryParseLong(String value, long defaultValue) {
/*     */     
/* 543 */     try { return Long.parseLong(value.trim()); } catch (NumberFormatException numberFormatException) { return defaultValue; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float tryParseFloat(String value, float defaultValue) {
/*     */     
/* 554 */     try { return Float.parseFloat(value.trim()); } catch (NumberFormatException numberFormatException) { return defaultValue; }
/*     */   
/*     */   }
/*     */   
/*     */   public static int tryParseIntOffset(String value, int defaultValue, int source) {
/* 559 */     int offsetDirection = 1;
/*     */     
/* 561 */     if (value.startsWith("+")) {
/*     */       
/* 563 */       value = value.substring(1);
/*     */     }
/* 565 */     else if (value.startsWith("-")) {
/*     */       
/* 567 */       offsetDirection = -1;
/* 568 */       value = value.substring(1);
/*     */     }
/*     */     else {
/*     */       
/* 572 */       source = 0;
/*     */     } 
/*     */     
/* 575 */     return source + tryParseInt(value, defaultValue) * offsetDirection;
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
/*     */   public static final String[] tokenize(String text, char separator, char firstParamQuote, char otherParamsQuote, char escape, StringBuilder rawString) {
/* 588 */     List<String> params = new ArrayList<>();
/*     */ 
/*     */     
/* 591 */     StringBuilder currentParam = new StringBuilder();
/* 592 */     boolean escaped = false;
/* 593 */     boolean quoted = false;
/* 594 */     boolean emptyParam = true;
/* 595 */     char quote = firstParamQuote;
/*     */ 
/*     */     
/* 598 */     for (int charPos = 0; charPos < text.length(); charPos++) {
/*     */       
/* 600 */       char currentChar = text.charAt(charPos);
/*     */       
/* 602 */       if (currentChar == escape) {
/*     */         
/* 604 */         if (escaped)
/*     */         {
/* 606 */           escaped = false;
/* 607 */           currentParam.append(escape);
/*     */         }
/*     */         else
/*     */         {
/* 611 */           escaped = true;
/*     */         }
/*     */       
/* 614 */       } else if (currentChar == quote) {
/*     */         
/* 616 */         if (escaped)
/*     */         {
/* 618 */           escaped = false;
/* 619 */           currentParam.append(currentChar);
/* 620 */           emptyParam = false;
/*     */         }
/* 622 */         else if ((currentParam.length() == 0 || currentParam.toString().matches("^\\s+$")) && !quoted)
/*     */         {
/* 624 */           currentParam = new StringBuilder();
/* 625 */           quoted = true;
/* 626 */           emptyParam = false;
/*     */         }
/* 628 */         else if (quoted)
/*     */         {
/* 630 */           quoted = false;
/*     */         }
/*     */         else
/*     */         {
/* 634 */           currentParam.append(currentChar);
/* 635 */           emptyParam = false;
/*     */         }
/*     */       
/* 638 */       } else if (currentChar == separator) {
/*     */         
/* 640 */         if (escaped)
/*     */         {
/* 642 */           escaped = false;
/* 643 */           currentParam.append(currentChar);
/* 644 */           emptyParam = false;
/*     */         }
/* 646 */         else if (quoted)
/*     */         {
/* 648 */           currentParam.append(currentChar);
/* 649 */           emptyParam = false;
/*     */         }
/*     */         else
/*     */         {
/* 653 */           quote = otherParamsQuote;
/* 654 */           params.add(currentParam.toString());
/* 655 */           if (rawString != null) rawString.append(" ").append(currentParam); 
/* 656 */           currentParam = new StringBuilder();
/* 657 */           emptyParam = true;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 662 */         if (escaped) {
/*     */           
/* 664 */           escaped = false;
/* 665 */           currentParam.append(escape);
/*     */         } 
/*     */         
/* 668 */         currentParam.append(currentChar);
/* 669 */         emptyParam = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 674 */     if (!emptyParam) {
/*     */       
/* 676 */       params.add(currentParam.toString());
/* 677 */       if (rawString != null) rawString.append(" ").append(currentParam);
/*     */     
/* 679 */     } else if (text.length() > 0 && text.endsWith(String.valueOf(separator))) {
/*     */       
/* 681 */       params.add("");
/*     */     } 
/*     */     
/* 684 */     return params.<String>toArray(new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public String highlight(String text) {
/* 689 */     return highlight(text, String.valueOf('�'), String.valueOf('￼'));
/*     */   }
/*     */ 
/*     */   
/*     */   public String highlight(String text, String prefix, String suffix) {
/* 694 */     updateScriptActionRegex();
/* 695 */     return this.actionRegex.matcher(text).replaceAll(prefix + "$1" + suffix + "$2");
/*     */   }
/*     */ 
/*     */   
/*     */   public static <E extends Enum<E>> E fuzzyParseEnum(E defaultValue, String value, String... fuzzyNames) {
/* 700 */     Class<E> enumClass = defaultValue.getDeclaringClass();
/* 701 */     Enum[] arrayOfEnum = (Enum[])enumClass.getEnumConstants();
/*     */     
/* 703 */     if (arrayOfEnum.length != fuzzyNames.length)
/*     */     {
/* 705 */       throw new IllegalArgumentException("Supplied fuzzy names array differs in length to target enum " + enumClass.getSimpleName());
/*     */     }
/*     */     
/* 708 */     value = value.toLowerCase();
/* 709 */     for (int i = 0; i < fuzzyNames.length; i++) {
/*     */       
/* 711 */       String[] names = fuzzyNames[i].toLowerCase().split("\\|");
/* 712 */       for (String name : names) {
/*     */         
/* 714 */         if (value.contains(name))
/*     */         {
/* 716 */           return (E)arrayOfEnum[i];
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 722 */     return defaultValue;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\parser\ScriptCore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */