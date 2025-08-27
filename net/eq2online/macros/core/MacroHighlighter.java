/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import net.eq2online.macros.interfaces.IHighlighter;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.util.Util;
/*     */ 
/*     */ 
/*     */ public class MacroHighlighter
/*     */   implements IHighlighter
/*     */ {
/*     */   private final MacroPlaybackType playbackType;
/*  13 */   private char highlightColour = '6';
/*  14 */   private char normalColour = 'f';
/*  15 */   private char endColour = '7';
/*  16 */   private char scriptColour = 'd';
/*     */ 
/*     */   
/*     */   public MacroHighlighter(MacroPlaybackType playbackType) {
/*  20 */     this.playbackType = playbackType;
/*     */   }
/*     */ 
/*     */   
/*     */   public MacroHighlighter setColours(char highlightColour, char normalColour, char endColour, char scriptColour) {
/*  25 */     this.highlightColour = highlightColour;
/*  26 */     this.normalColour = normalColour;
/*  27 */     this.endColour = endColour;
/*  28 */     this.scriptColour = scriptColour;
/*  29 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MacroHighlighter highlight(char highlightColour) {
/*  34 */     this.highlightColour = highlightColour;
/*  35 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MacroHighlighter normal(char normalColour) {
/*  40 */     this.normalColour = normalColour;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MacroHighlighter end(char endColour) {
/*  46 */     this.endColour = endColour;
/*  47 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MacroHighlighter script(char scriptColour) {
/*  52 */     this.scriptColour = scriptColour;
/*  53 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String generateHighlightMask(String text) {
/*  63 */     return Util.stringToHighlightMask(highlight(text));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String highlight(String text) {
/*  69 */     return highlightMacro(text, this.highlightColour, this.normalColour, this.endColour, this.scriptColour);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String highlightMacro(String macro, char highlightColour, char normalColour, char endColour, char scriptColour) {
/*  77 */     macro = highlightParams(macro, "§" + highlightColour, "§" + normalColour);
/*  78 */     return highlightMacroBody(macro, highlightColour, normalColour, endColour, scriptColour, '5');
/*     */   }
/*     */ 
/*     */   
/*     */   public String highlightInteractive(String macro, char highlightColour, char normalColour, char endColour, char scriptColour) {
/*  83 */     String enclosed = "$${" + macro + "}$$";
/*  84 */     String highlighted = highlightMacroBody(enclosed, highlightColour, normalColour, endColour, scriptColour, 'b');
/*  85 */     int pos = highlighted.lastIndexOf("}$$");
/*  86 */     return highlighted.substring(5, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   private String highlightMacroBody(String macro, char highlightColour, char normalColour, char endColour, char scriptColour, char actionColour) {
/*  91 */     String tempMacro = macro;
/*  92 */     macro = "";
/*     */ 
/*     */     
/*  95 */     Matcher scriptPatternMatcher = Macro.PATTERN_SCRIPT.matcher(tempMacro);
/*     */ 
/*     */     
/*  98 */     while (scriptPatternMatcher.find()) {
/*     */       
/* 100 */       char thisScriptColour = (scriptPatternMatcher.group(2).length() > 0) ? scriptColour : '9';
/*     */ 
/*     */       
/* 103 */       macro = macro + tempMacro.substring(0, scriptPatternMatcher.start()) + "§" + thisScriptColour + highlightScript(scriptPatternMatcher.group(), actionColour, thisScriptColour).replaceAll("§" + normalColour, "§" + thisScriptColour) + "§" + normalColour;
/* 104 */       tempMacro = tempMacro.substring(scriptPatternMatcher.end());
/* 105 */       scriptPatternMatcher.reset(tempMacro);
/*     */     } 
/*     */     
/* 108 */     macro = macro + tempMacro;
/*     */     
/* 110 */     if (this.playbackType != MacroPlaybackType.KEYSTATE)
/*     */     {
/* 112 */       macro = Macro.PATTERN_STOP.matcher(macro).replaceAll("§" + highlightColour + "$0§" + endColour);
/*     */     }
/*     */     
/* 115 */     return macro;
/*     */   }
/*     */ 
/*     */   
/*     */   private String highlightScript(String macro, char actionColour, char scriptColour) {
/* 120 */     return ScriptContext.MAIN.getCore().highlight(macro, "§" + actionColour, "§" + scriptColour);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String highlightParams(String macro, String prefix, String suffix) {
/* 131 */     macro = MacroIncludeProcessor.PATTERN_FILE_PARAM.matcher(macro).replaceAll(prefix + "$0" + suffix);
/*     */     
/* 133 */     if (this.playbackType == MacroPlaybackType.KEYSTATE) return macro;
/*     */     
/* 135 */     macro = MacroParams.highlightParams(macro, prefix, suffix);
/* 136 */     return macro;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\MacroHighlighter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */