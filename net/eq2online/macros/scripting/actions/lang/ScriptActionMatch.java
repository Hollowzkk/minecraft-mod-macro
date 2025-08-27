/*     */ package net.eq2online.macros.scripting.actions.lang;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.regex.PatternSyntaxException;
/*     */ import net.eq2online.macros.scripting.ReturnValueLog;
/*     */ import net.eq2online.macros.scripting.Variable;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.ReturnValue;
/*     */ import net.eq2online.macros.scripting.api.ReturnValueArray;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import net.eq2online.util.Util;
/*     */ 
/*     */ 
/*     */ public class ScriptActionMatch
/*     */   extends ScriptAction
/*     */ {
/*     */   public ScriptActionMatch(ScriptContext context) {
/*  26 */     super(context, "match");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*     */     ReturnValue returnValue;
/*  34 */     IReturnValue retVal = null;
/*     */ 
/*     */     
/*     */     try {
/*  38 */       if (params.length > 1) {
/*     */         
/*  40 */         String regex = Util.convertAmpCodes(provider.expand(macro, params[1], false));
/*  41 */         String string = provider.expand(macro, params[0], false);
/*  42 */         Pattern pattern = Pattern.compile(regex, 2);
/*  43 */         Matcher match = pattern.matcher(string);
/*     */         
/*  45 */         boolean capture = (params.length > 2);
/*  46 */         boolean captureMultiple = (capture && params[2].startsWith("{") && params[params.length - 1].endsWith("}"));
/*     */         
/*  48 */         if (match.find()) {
/*     */           
/*  50 */           if (captureMultiple) {
/*     */             
/*  52 */             int groupNumber = 1;
/*     */             
/*  54 */             for (String varName : getCaptures(params, 2))
/*     */             {
/*  56 */               provider.setVariable(macro, varName, (groupNumber <= match.groupCount()) ? getGroup(match, groupNumber) : "");
/*  57 */               groupNumber++;
/*     */             }
/*     */           
/*  60 */           } else if (capture) {
/*     */             
/*  62 */             if (params[2].endsWith("[]") && params[2]
/*  63 */               .length() > 2 && 
/*  64 */               Variable.isValidVariableName(params[2].substring(0, params[2].length() - 2)))
/*     */             {
/*  66 */               String varBaseName = params[2].substring(0, params[2].length() - 2);
/*  67 */               provider.clearArray(macro, varBaseName);
/*  68 */               List<String> strings = new ArrayList<>();
/*  69 */               for (int groupNumber = 0; groupNumber <= match.groupCount(); groupNumber++) {
/*     */                 
/*  71 */                 String result = getGroup(match, groupNumber);
/*  72 */                 provider.setVariable(macro, varBaseName + "[" + groupNumber + "]", result);
/*     */               } 
/*     */               
/*  75 */               ReturnValueArray retValArray = new ReturnValueArray(false);
/*  76 */               retValArray.putStrings(strings);
/*  77 */               ReturnValueArray returnValueArray1 = retValArray;
/*     */             }
/*     */             else
/*     */             {
/*  81 */               int grpIndex = (params.length > 3) ? ScriptCore.tryParseInt(provider.expand(macro, params[3], false), 0) : 0;
/*  82 */               int groupNumber = Math.min(Math.max(grpIndex, 0), match.groupCount());
/*  83 */               String result = getGroup(match, groupNumber);
/*  84 */               returnValue = new ReturnValue(result);
/*  85 */               provider.setVariable(macro, params[2], result);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/*  90 */             List<String> strings = new ArrayList<>();
/*  91 */             for (int i = 0; i <= match.groupCount(); i++)
/*     */             {
/*  93 */               strings.add(match.group(i));
/*     */             }
/*  95 */             ReturnValueArray retValArray = new ReturnValueArray(false);
/*  96 */             retValArray.putStrings(strings);
/*  97 */             ReturnValueArray returnValueArray1 = retValArray;
/*     */           }
/*     */         
/* 100 */         } else if (captureMultiple && params.length > 3) {
/*     */           
/* 102 */           List<String> strings = new ArrayList<>();
/* 103 */           for (String varName : getCaptures(params, 2))
/*     */           {
/* 105 */             provider.setVariable(macro, varName, provider.expand(macro, params[3], false));
/*     */           }
/*     */           
/* 108 */           ReturnValueArray retValArray = new ReturnValueArray(false);
/* 109 */           retValArray.putStrings(strings);
/* 110 */           ReturnValueArray returnValueArray1 = retValArray;
/*     */         }
/* 112 */         else if (!captureMultiple && params.length > 4) {
/*     */           
/* 114 */           String defaultValue = provider.expand(macro, params[4], false);
/* 115 */           provider.setVariable(macro, params[2], defaultValue);
/* 116 */           returnValue = new ReturnValue(defaultValue);
/*     */         }
/*     */       
/*     */       } 
/* 120 */     } catch (PatternSyntaxException ex) {
/*     */       
/* 122 */       displayErrorMessage(provider, macro, instance, ex, "script.error.badregex");
/*     */     }
/* 124 */     catch (IndexOutOfBoundsException ex) {
/*     */       
/* 126 */       displayErrorMessage(provider, macro, instance, ex, "script.error.badreplacement");
/*     */     }
/* 128 */     catch (Exception ex) {
/*     */       
/* 130 */       return (IReturnValue)new ReturnValueLog("§cError: §e" + ex.getClass().getSimpleName() + " §c(§f" + ex.getMessage() + "§c)");
/*     */     } 
/*     */     
/* 133 */     return (IReturnValue)returnValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGroup(Matcher match, int groupNumber) {
/* 143 */     String group = match.group(groupNumber);
/* 144 */     return (group != null) ? group : "";
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] getCaptures(String[] captureList, int base) {
/* 149 */     String[] captures = new String[captureList.length - 2];
/*     */     
/* 151 */     for (int offset = 0; offset < captures.length; offset++)
/*     */     {
/* 153 */       captures[offset] = captureList[offset + base];
/*     */     }
/*     */     
/* 156 */     captures[0] = captures[0].substring(1);
/* 157 */     captures[captures.length - 1] = captures[captures.length - 1].substring(0, captures[captures.length - 1].length() - 1);
/*     */     
/* 159 */     return captures;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionMatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */