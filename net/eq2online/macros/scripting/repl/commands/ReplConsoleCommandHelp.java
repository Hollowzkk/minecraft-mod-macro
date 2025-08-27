/*     */ package net.eq2online.macros.scripting.repl.commands;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.interfaces.ICommandInfo;
/*     */ import net.eq2online.macros.scripting.IDocumentationEntry;
/*     */ import net.eq2online.macros.scripting.docs.Documentor;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*     */ import net.eq2online.macros.scripting.repl.Repl;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReplConsoleCommandHelp
/*     */   extends ReplConsoleCommand
/*     */ {
/*     */   public ReplConsoleCommandHelp(Repl repl) {
/*  20 */     super(repl, "help");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(IReplConsole console, String[] commandLine, String rawCommand) {
/*  26 */     if (!getName().equals(commandLine[0]))
/*     */     {
/*  28 */       return false;
/*     */     }
/*     */     
/*  31 */     List<IReplConsoleCommand> commands = this.repl.getCommands();
/*  32 */     Documentor documentor = (Documentor)ScriptContext.MAIN.getDocumentor();
/*  33 */     if (commandLine.length > 1) {
/*     */       
/*  35 */       String query = commandLine[1].trim().toUpperCase();
/*  36 */       boolean wildcard = query.endsWith("*");
/*  37 */       if (wildcard)
/*     */       {
/*  39 */         query = query.substring(0, query.length() - 1);
/*     */       }
/*     */       
/*  42 */       List<String> matches = new ArrayList<>();
/*  43 */       ICommandInfo doc = null;
/*  44 */       ICommandInfo exactMatch = null;
/*     */       
/*  46 */       for (String entry : documentor.getEntries()) {
/*     */         
/*  48 */         IDocumentationEntry documentation = documentor.getDocumentation(entry);
/*  49 */         if (documentation != null) {
/*     */           
/*  51 */           String actionName = documentation.getName().toUpperCase();
/*  52 */           if (actionName.startsWith(query) && (!wildcard || actionName.length() > query.length())) {
/*     */             
/*  54 */             String colour = documentation.isHidden() ? "3" : "b";
/*  55 */             matches.add("§" + colour + actionName);
/*  56 */             IDocumentationEntry iDocumentationEntry = documentation;
/*     */           } 
/*     */           
/*  59 */           if (actionName.equals(query) && !wildcard)
/*     */           {
/*  61 */             IDocumentationEntry iDocumentationEntry = documentation;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  66 */       for (IReplConsoleCommand cmd : commands) {
/*     */         
/*  68 */         String commandName = cmd.getName().toUpperCase();
/*  69 */         if (commandName.startsWith(query) && (!wildcard || commandName.length() > query.length())) {
/*     */           
/*  71 */           matches.add("§a" + commandName);
/*  72 */           doc = cmd;
/*     */         } 
/*     */         
/*  75 */         if (commandName.equals(query) && !wildcard)
/*     */         {
/*  77 */           exactMatch = cmd;
/*     */         }
/*     */       } 
/*     */       
/*  81 */       if (matches.size() < 1)
/*     */       {
/*  83 */         console.addLine(I18n.get("repl.console.error.noaction", new Object[] { query }));
/*     */       }
/*  85 */       else if (matches.size() == 1 && doc != null)
/*     */       {
/*  87 */         String colour = (doc instanceof IDocumentationEntry) ? "b" : "a";
/*  88 */         console.addLine("§" + colour + doc.getUsage());
/*  89 */         console.addLine("§6" + doc.getDescription());
/*     */       }
/*  91 */       else if (exactMatch != null)
/*     */       {
/*  93 */         String colour = (exactMatch instanceof IDocumentationEntry) ? "b" : "a";
/*  94 */         console.addLine("§" + colour + exactMatch.getUsage());
/*  95 */         console.addLine("§6" + exactMatch.getDescription());
/*     */       }
/*     */       else
/*     */       {
/*  99 */         String allMatches = Joiner.on("§f, ").join(matches);
/* 100 */         console.addLineWrapped(allMatches.toUpperCase());
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 105 */       List<String> actionNames = new ArrayList<>();
/* 106 */       for (String entry : documentor.getEntries()) {
/*     */         
/* 108 */         IDocumentationEntry documentation = documentor.getDocumentation(entry);
/* 109 */         if (documentation != null) {
/*     */           
/* 111 */           String actionName = documentation.getName().toUpperCase();
/* 112 */           String colour = documentation.isHidden() ? "3" : "b";
/* 113 */           actionNames.add("§" + colour + actionName);
/*     */         } 
/*     */       } 
/*     */       
/* 117 */       String allActions = Joiner.on("§f, ").join(actionNames);
/* 118 */       console.addLine(I18n.get("repl.console.help.actions"));
/* 119 */       console.addLineWrapped(allActions.toUpperCase());
/*     */       
/* 121 */       List<String> commandNames = new ArrayList<>();
/* 122 */       for (IReplConsoleCommand cmd : commands)
/*     */       {
/* 124 */         commandNames.add("§a" + cmd.getName());
/*     */       }
/*     */       
/* 127 */       String allCommands = Joiner.on("§f, ").join(commandNames);
/* 128 */       console.addLine(I18n.get("repl.console.help.commands"));
/* 129 */       console.addLineWrapped(allCommands.toUpperCase());
/*     */       
/* 131 */       console.addLine(I18n.get("repl.console.help.eval"));
/* 132 */       console.addLine(I18n.get("repl.console.help.eval.example1"));
/* 133 */       console.addLine(I18n.get("repl.console.help.eval.example2"));
/*     */     } 
/*     */ 
/*     */     
/* 137 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandHelp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */