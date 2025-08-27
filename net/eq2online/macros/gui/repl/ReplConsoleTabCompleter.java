/*     */ package net.eq2online.macros.gui.repl;
/*     */ 
/*     */ import bje;
/*     */ import blq;
/*     */ import com.google.common.base.Joiner;
/*     */ import et;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import net.eq2online.macros.scripting.IDocumentationEntry;
/*     */ import net.eq2online.macros.scripting.docs.Documentor;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*     */ import net.eq2online.macros.scripting.repl.Repl;
/*     */ import net.eq2online.macros.scripting.repl.commands.IReplConsoleCommand;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ReplConsoleTabCompleter
/*     */   extends blq
/*     */ {
/*  27 */   private static final Pattern PATTERN_TAIL = Pattern.compile("(^|;|\\|help |)([a-zA-Z]+)$");
/*     */   
/*     */   private final Repl repl;
/*     */   
/*     */   private final IReplConsole console;
/*     */   
/*  33 */   private final List<String> suggestions = new ArrayList<>();
/*     */   
/*     */   private final Pattern commandRegex;
/*     */   
/*  37 */   private int suggestion = -1;
/*     */   
/*     */   private String lastLine;
/*     */ 
/*     */   
/*     */   ReplConsoleTabCompleter(Repl repl, IReplConsole console, bje textField) {
/*  43 */     super(textField, false);
/*     */     
/*  45 */     this.repl = repl;
/*  46 */     this.console = console;
/*  47 */     this.commandRegex = buildCommandRegex();
/*     */   }
/*     */ 
/*     */   
/*     */   private Pattern buildCommandRegex() {
/*  52 */     Set<String> commands = new TreeSet<>();
/*  53 */     for (IReplConsoleCommand command : this.repl.getCommands())
/*     */     {
/*  55 */       commands.add(command.getName());
/*     */     }
/*     */     
/*  58 */     String cmdRegex = " ?";
/*  59 */     char separator = ')';
/*  60 */     for (String cmd : commands) {
/*     */       
/*  62 */       cmdRegex = cmd + separator + cmdRegex;
/*  63 */       separator = '|';
/*     */     } 
/*     */     
/*  66 */     return Pattern.compile("^(" + cmdRegex, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   Pattern getCommandRegex() {
/*  71 */     return this.commandRegex;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a() {
/*  77 */     super.a();
/*     */     
/*  79 */     if (this.f.size() > 1) {
/*     */       
/*  81 */       String joined = "§d" + Joiner.on(", §d").join(this.f);
/*  82 */       if (!joined.equals(this.lastLine)) {
/*     */         
/*  84 */         this.console.addLineWrapped(joined);
/*  85 */         this.lastLine = joined;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void clear() {
/*  92 */     this.lastLine = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public et b() {
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void c() {
/* 105 */     super.c();
/* 106 */     clearSuggestion();
/*     */   }
/*     */ 
/*     */   
/*     */   void generateSuggestion() {
/* 111 */     if (this.suggestion > -1) {
/*     */       
/* 113 */       this.suggestion = (this.suggestion + 1) % this.suggestions.size();
/* 114 */       applySuggestion();
/*     */       
/*     */       return;
/*     */     } 
/* 118 */     String text = this.a.b();
/* 119 */     if (text.matches("^/[a-zA-Z].*")) {
/*     */       
/* 121 */       if (this.repl.isLive())
/*     */       {
/* 123 */         a();
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 128 */     if (this.console.getMode() == IReplConsole.ConsoleMode.EXEC) {
/*     */       
/* 130 */       boolean isRunShorthand = text.startsWith(".");
/* 131 */       Matcher cmdMatcher = this.commandRegex.matcher(text);
/* 132 */       if ((cmdMatcher.find() && cmdMatcher.group().endsWith(" ")) || isRunShorthand) {
/*     */         
/* 134 */         String cmd = isRunShorthand ? "run" : cmdMatcher.group(1).toLowerCase();
/* 135 */         int baseLength = isRunShorthand ? 1 : (cmd.length() + 1);
/*     */         
/* 137 */         for (IReplConsoleCommand command : this.repl.getCommands()) {
/*     */           
/* 139 */           if (cmd.equals(command.getName())) {
/*     */             
/* 141 */             List<String> commandSuggestions = command.getSuggestions(text.substring(baseLength));
/* 142 */             if (isRunShorthand)
/*     */             {
/* 144 */               for (int i = 0; i < commandSuggestions.size(); i++)
/*     */               {
/* 146 */                 commandSuggestions.set(i, ((String)commandSuggestions.get(i)).replace(".txt", ""));
/*     */               }
/*     */             }
/*     */             
/* 150 */             if (commandSuggestions != null && commandSuggestions.size() > 0) {
/*     */               
/* 152 */               this.suggestions.clear();
/* 153 */               this.suggestions.addAll(commandSuggestions);
/* 154 */               handleSuggestions(text.substring(0, baseLength));
/*     */               
/*     */               return;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 162 */     Matcher tailMatcher = Pattern.compile("(^|;|\\|help |^\\? |)([a-zA-Z]+)$").matcher(text);
/* 163 */     if (tailMatcher.find()) {
/*     */       
/* 165 */       String tail = tailMatcher.group(2).toUpperCase();
/* 166 */       updateSuggestions(tail);
/* 167 */       handleSuggestions(text.substring(0, text.length() - tail.length()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void handleSuggestions(String stub) {
/* 173 */     if (this.suggestions.size() == 1) {
/*     */       
/* 175 */       this.a.a(stub + getSuggestion(0));
/* 176 */       clearSuggestion();
/*     */     }
/* 178 */     else if (this.suggestions.size() > 1) {
/*     */       
/* 180 */       this.console.addLine(Joiner.on("§f, ").join(this.suggestions));
/* 181 */       this.suggestion = 0;
/* 182 */       applySuggestion();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void applySuggestion() {
/* 188 */     if (this.suggestion >= this.suggestions.size())
/*     */     {
/* 190 */       this.suggestion = 0;
/*     */     }
/*     */     
/* 193 */     String text = this.a.b();
/* 194 */     String suggestion = getSuggestion(this.suggestion);
/*     */     
/* 196 */     Matcher cmdMatcher = this.commandRegex.matcher(this.a.b());
/* 197 */     if (cmdMatcher.find() && cmdMatcher.group().endsWith(" ")) {
/*     */       
/* 199 */       String cmd = cmdMatcher.group(1).toLowerCase();
/* 200 */       int baseLength = cmd.length() + 1;
/* 201 */       this.a.a(text.substring(0, baseLength) + suggestion);
/*     */       
/*     */       return;
/*     */     } 
/* 205 */     Matcher tailMatcher = PATTERN_TAIL.matcher(text);
/* 206 */     if (tailMatcher.find())
/*     */     {
/* 208 */       this.a.a(text.substring(0, text.length() - tailMatcher.group(2).length()) + suggestion);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   String getSuggestion(int index) {
/* 214 */     return ((String)this.suggestions.get(index)).substring(2);
/*     */   }
/*     */ 
/*     */   
/*     */   void clearSuggestion() {
/* 219 */     this.suggestions.clear();
/* 220 */     this.suggestion = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   void updateSuggestions(String tail) {
/* 225 */     this.suggestions.clear();
/*     */     
/* 227 */     Documentor documentor = (Documentor)ScriptContext.MAIN.getDocumentor();
/* 228 */     for (String entry : documentor.getEntries()) {
/*     */       
/* 230 */       IDocumentationEntry documentation = documentor.getDocumentation(entry);
/* 231 */       if (documentation != null) {
/*     */         
/* 233 */         String actionName = documentation.getName().toUpperCase();
/* 234 */         if (actionName.startsWith(tail)) {
/*     */           
/* 236 */           String colour = documentation.isHidden() ? "3" : "b";
/* 237 */           this.suggestions.add("§" + colour + actionName);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 242 */     if (this.console.getMode() == IReplConsole.ConsoleMode.EXEC) {
/*     */       
/* 244 */       int commandsAdded = 0;
/*     */       
/* 246 */       for (IReplConsoleCommand command : this.repl.getCommands()) {
/*     */         
/* 248 */         String commandName = command.getName().toUpperCase();
/* 249 */         if (commandName.startsWith(tail)) {
/*     */           
/* 251 */           commandsAdded++;
/* 252 */           this.suggestions.add("§a" + commandName);
/*     */         } 
/*     */       } 
/*     */       
/* 256 */       if (commandsAdded == 1)
/*     */       {
/* 258 */         this.suggestions.set(0, (String)this.suggestions.get(0) + " ");
/*     */       }
/*     */     }
/* 261 */     else if ("END".startsWith(tail)) {
/*     */       
/* 263 */       this.suggestions.add("§aEND");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\repl\ReplConsoleTabCompleter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */