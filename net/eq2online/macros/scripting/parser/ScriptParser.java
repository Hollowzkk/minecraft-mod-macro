/*     */ package net.eq2online.macros.scripting.parser;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.scripting.ActionParser;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*     */ import net.eq2online.macros.scripting.api.IScriptParser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScriptParser
/*     */   implements IScriptParser
/*     */ {
/*     */   private final ScriptContext context;
/*  17 */   private List<ActionParser> parsers = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public ScriptParser(ScriptContext context) {
/*  21 */     this.context = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addActionParser(ActionParser parser) {
/*  31 */     this.parsers.add(parser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IMacroAction> parseScript(IMacroActionProcessor actionProcessor, String script) {
/*  42 */     List<IMacroAction> actions = new ArrayList<>();
/*     */     
/*  44 */     for (String scriptEntry : tokeniseScript(script.replace('', ';'), ';')) {
/*     */       
/*  46 */       scriptEntry = scriptEntry.replaceAll("", "\\\\|").trim();
/*     */       
/*  48 */       if (!scriptEntry.startsWith("//")) {
/*     */         
/*  50 */         IMacroAction action = null;
/*     */         
/*  52 */         for (ActionParser parser : this.parsers) {
/*     */           
/*  54 */           action = parser.parse(actionProcessor, scriptEntry);
/*  55 */           if (action != null) {
/*     */             
/*  57 */             actions.add(action);
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*  62 */         if (action == null && !scriptEntry.trim().isEmpty())
/*     */         {
/*  64 */           onError(scriptEntry);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  69 */     return actions;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<String> tokeniseScript(String script, char delimiter) {
/*  74 */     int escape = 0;
/*  75 */     boolean quoted = false;
/*     */     
/*  77 */     List<String> entries = new ArrayList<>();
/*  78 */     StringBuilder entry = new StringBuilder();
/*     */     
/*  80 */     for (int i = 0; i < script.length(); i++) {
/*     */       
/*  82 */       char c = script.charAt(i);
/*  83 */       if (c == '"') {
/*     */         
/*  85 */         if (escape % 2 != 1)
/*     */         {
/*  87 */           quoted = !quoted;
/*     */         }
/*     */       }
/*  90 */       else if (c == delimiter && !quoted) {
/*     */         
/*  92 */         entries.add(entry.toString());
/*  93 */         entry = new StringBuilder();
/*  94 */         escape = 0;
/*     */         
/*     */         continue;
/*     */       } 
/*  98 */       if (c == '\\') {
/*     */         
/* 100 */         escape++;
/*     */       }
/*     */       else {
/*     */         
/* 104 */         escape = 0;
/*     */       } 
/*     */       
/* 107 */       entry.append(c);
/*     */       continue;
/*     */     } 
/* 110 */     if (entry.length() > 0)
/*     */     {
/* 112 */       entries.add(entry.toString());
/*     */     }
/*     */     
/* 115 */     return entries;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onError(String scriptEntry) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ScriptContext getContext() {
/* 125 */     return this.context;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\parser\ScriptParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */