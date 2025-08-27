/*     */ package net.eq2online.macros.scripting.actions.lang;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.scripting.Variable;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptedIterator;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.iterators.ScriptedIteratorArray;
/*     */ import net.eq2online.macros.scripting.iterators.ScriptedIteratorControls;
/*     */ import net.eq2online.macros.scripting.iterators.ScriptedIteratorEffects;
/*     */ import net.eq2online.macros.scripting.iterators.ScriptedIteratorEnchantments;
/*     */ import net.eq2online.macros.scripting.iterators.ScriptedIteratorEnvironment;
/*     */ import net.eq2online.macros.scripting.iterators.ScriptedIteratorPlayers;
/*     */ import net.eq2online.macros.scripting.iterators.ScriptedIteratorProperties;
/*     */ import net.eq2online.macros.scripting.iterators.ScriptedIteratorRunning;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ 
/*     */ public class ScriptActionForEach
/*     */   extends ScriptActionDo
/*     */ {
/*  28 */   private static final Pattern PATTERN_ARRAY_EXPRESSIVE = Pattern.compile("^(.+?)\\s+as(.+?)(=>(.+?))?$", 2);
/*     */ 
/*     */   
/*     */   public ScriptActionForEach(ScriptContext context) {
/*  32 */     super(context, "foreach");
/*     */ 
/*     */     
/*  35 */     ScriptCore scriptCore = this.context.getCore();
/*  36 */     scriptCore.registerIterator("enchantments", ScriptedIteratorEnchantments.class);
/*  37 */     scriptCore.registerIterator("players", ScriptedIteratorPlayers.class);
/*  38 */     scriptCore.registerIterator("effects", ScriptedIteratorEffects.class);
/*  39 */     scriptCore.registerIterator("env", ScriptedIteratorEnvironment.class);
/*  40 */     scriptCore.registerIterator("properties", ScriptedIteratorProperties.class);
/*  41 */     scriptCore.registerIterator("controls", ScriptedIteratorControls.class);
/*  42 */     scriptCore.registerIterator("running", ScriptedIteratorRunning.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/*  48 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExpectedPopCommands() {
/*  54 */     return I18n.get("script.error.stackhint", new Object[] { this, "NEXT" });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean executeStackPush(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*     */     ScriptedIteratorArray scriptedIteratorArray;
/*  60 */     IScriptedIterator state = (IScriptedIterator)instance.getState();
/*     */     
/*  62 */     if (params.length > 0) {
/*     */       
/*  64 */       if (state == null) {
/*     */ 
/*     */         
/*  67 */         String iteratorName = provider.expand(macro, params[0], false);
/*  68 */         String iteratorVarName = (params.length > 1) ? params[1].toLowerCase() : null;
/*  69 */         String positionVarName = null;
/*     */         
/*  71 */         if (params.length == 1) {
/*     */           
/*  73 */           Matcher syntaxMatcher = PATTERN_ARRAY_EXPRESSIVE.matcher(iteratorName);
/*  74 */           if (syntaxMatcher.matches()) {
/*     */             
/*  76 */             iteratorName = syntaxMatcher.group(1).trim();
/*     */             
/*  78 */             if (syntaxMatcher.group(4) != null) {
/*     */               
/*  80 */               positionVarName = syntaxMatcher.group(2).trim();
/*  81 */               iteratorVarName = syntaxMatcher.group(4).trim();
/*     */             }
/*     */             else {
/*     */               
/*  85 */               iteratorVarName = syntaxMatcher.group(2).trim();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/*  90 */         if (this.context.getCore().hasIterator(iteratorName)) {
/*     */           
/*     */           try
/*     */           {
/*  94 */             state = this.context.getCore().createIterator(iteratorName, provider, macro);
/*  95 */             instance.setState(state);
/*     */           }
/*  97 */           catch (Exception ex)
/*     */           {
/*  99 */             return false;
/*     */           }
/*     */         
/* 102 */         } else if (iteratorVarName != null && 
/* 103 */           Variable.getValidVariableOrArraySpecifier(iteratorName) != null && 
/* 104 */           Variable.isValidScalarVariableName(iteratorVarName)) {
/*     */           
/* 106 */           iteratorName = Variable.getValidVariableOrArraySpecifier(iteratorName);
/* 107 */           if (positionVarName == null)
/*     */           {
/*     */             
/* 110 */             positionVarName = (params.length > 2 && Variable.isValidScalarVariableName(params[2].toLowerCase())) ? params[2].toLowerCase() : null;
/*     */           }
/*     */           
/* 113 */           scriptedIteratorArray = new ScriptedIteratorArray(provider, macro, iteratorName, iteratorVarName, positionVarName);
/* 114 */           instance.setState(scriptedIteratorArray);
/*     */         }
/*     */         else {
/*     */           
/* 118 */           return false;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 123 */         scriptedIteratorArray.increment();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 128 */       return false;
/*     */     } 
/*     */     
/* 131 */     if (scriptedIteratorArray.isActive()) {
/*     */       
/* 133 */       macro.registerVariableProvider((IVariableProvider)scriptedIteratorArray);
/*     */     }
/*     */     else {
/*     */       
/* 137 */       instance.setState(null);
/* 138 */       macro.unregisterVariableProvider((IVariableProvider)scriptedIteratorArray);
/*     */     } 
/*     */     
/* 141 */     return scriptedIteratorArray.isActive();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStopped(IScriptActionProvider provider, IMacro macro, IMacroAction instance) {
/* 147 */     super.onStopped(provider, macro, instance);
/*     */     
/* 149 */     IScriptedIterator state = (IScriptedIterator)instance.getState();
/* 150 */     macro.unregisterVariableProvider((IVariableProvider)state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeStackPop(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params, IMacroAction popAction) {
/*     */     try {
/* 159 */       IScriptedIterator state = (IScriptedIterator)instance.getState();
/* 160 */       macro.unregisterVariableProvider((IVariableProvider)state);
/*     */     }
/* 162 */     catch (Exception exception) {}
/*     */     
/* 164 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBreak(IMacroActionProcessor processor, IScriptActionProvider provider, IMacro macro, IMacroAction instance, IMacroAction breakAction) {
/* 171 */     IScriptedIterator state = (IScriptedIterator)instance.getState();
/* 172 */     if (state != null) {
/*     */       
/* 174 */       state.terminate();
/* 175 */       return true;
/*     */     } 
/*     */     
/* 178 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePoppedBy(IScriptAction action) {
/* 184 */     return action instanceof ScriptActionNext;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionForEach.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */