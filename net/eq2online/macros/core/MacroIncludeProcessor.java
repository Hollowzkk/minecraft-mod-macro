/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import bib;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.scripting.interfaces.IScriptActionPreProcessor;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MacroIncludeProcessor
/*     */ {
/*  20 */   public static final Pattern PATTERN_FILE_PARAM = Pattern.compile("(?<![\\x5C])\\x24\\x24\\<([a-z0-9\\x20_\\-\\.]+\\.txt)\\>", 2);
/*     */ 
/*     */   
/*  23 */   public static final Pattern PATTERN_FILE_NAME = Pattern.compile("^[A-Za-z0-9\\x20_\\-\\.]+\\.txt$");
/*     */   
/*  25 */   public static final Pattern PATTERN_FILE_NAME_OPTEXT = Pattern.compile("^[A-Za-z0-9\\x20_\\-\\.]+?(\\.txt|)$");
/*     */ 
/*     */   
/*     */   public static final char REPLACEMENT_FILE_CRLF = '';
/*     */ 
/*     */   
/*     */   protected final Macros macros;
/*     */ 
/*     */   
/*     */   protected final ScriptContext scriptContext;
/*     */ 
/*     */   
/*     */   public MacroIncludeProcessor(Macros macros, bib minecraft, ScriptContext scriptContext) {
/*  38 */     this.macros = macros;
/*  39 */     this.scriptContext = scriptContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String processIncludes(String macro) {
/*  50 */     if (macro == null)
/*     */     {
/*  52 */       return null;
/*     */     }
/*     */     
/*  55 */     int fileReplacementCounter = 0;
/*  56 */     Matcher filePatternMatcher = PATTERN_FILE_PARAM.matcher(macro);
/*     */     
/*  58 */     while (filePatternMatcher.find()) {
/*     */       
/*  60 */       fileReplacementCounter++;
/*     */       
/*  62 */       macro = macro.substring(0, filePatternMatcher.start()) + getFileContents(filePatternMatcher.group(1), fileReplacementCounter) + macro.substring(filePatternMatcher.end());
/*  63 */       filePatternMatcher.reset(macro);
/*     */     } 
/*     */     
/*  66 */     return macro;
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
/*     */   protected String getFileContents(String fileName, int fileReplacementCounter) {
/*  79 */     if (fileReplacementCounter > (this.macros.getSettings()).maxIncludes || !Macros.isValidFileName(fileName))
/*     */     {
/*  81 */       return "";
/*     */     }
/*     */     
/*  84 */     File includeFile = this.macros.getFile(fileName);
/*     */ 
/*     */     
/*  87 */     if (!includeFile.isFile())
/*     */     {
/*  89 */       return "";
/*     */     }
/*     */     
/*  92 */     StringBuilder fileContents = new StringBuilder();
/*  93 */     BufferedReader reader = null;
/*     */ 
/*     */     
/*     */     try {
/*  97 */       reader = new BufferedReader(new FileReader(includeFile));
/*     */       
/*  99 */       for (String line = ""; (line = reader.readLine()) != null; ) {
/*     */         
/* 101 */         line = line.trim();
/* 102 */         if (line.length() > 0)
/*     */         {
/* 104 */           fileContents.append((fileContents.length() == 0) ? line : ('' + line));
/*     */         }
/*     */       } 
/*     */       
/* 108 */       reader.close();
/*     */     }
/* 110 */     catch (Exception ex) {
/*     */       
/* 112 */       Log.info("Failed loading include file '{0}'", new Object[] { fileName });
/*     */     }
/*     */     finally {
/*     */       
/* 116 */       if (reader != null) {
/*     */         
/*     */         try {
/*     */           
/* 120 */           reader.close();
/*     */         }
/* 122 */         catch (IOException ex) {
/*     */           
/* 124 */           ex.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 129 */     return fileContents.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String processEscapes(String macro) {
/* 138 */     macro = macro.replaceAll("\\x5C\\x24\\x24", "" + 
/* 139 */         Macro.escapeReplacement("$$"));
/* 140 */     macro = macro.replaceAll("\\x5C\\x7C", "");
/* 141 */     return macro;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String processPrompts(String macro) {
/* 146 */     if (macro == null)
/*     */     {
/* 148 */       return null;
/*     */     }
/*     */     
/* 151 */     IScriptActionPreProcessor prompt = (IScriptActionPreProcessor)this.scriptContext.getCore().getAction("prompt");
/* 152 */     if (prompt != null)
/*     */     {
/* 154 */       return prompt.preProcess(macro);
/*     */     }
/*     */     
/* 157 */     return macro;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\MacroIncludeProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */