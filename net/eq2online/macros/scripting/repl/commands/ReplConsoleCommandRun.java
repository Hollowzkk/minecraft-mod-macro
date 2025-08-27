/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.MacroExecVariableProvider;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.interfaces.IMacroParamStorage;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ import net.eq2online.macros.scripting.repl.ReplFileMacro;
/*    */ 
/*    */ public class ReplConsoleCommandRun
/*    */   extends ReplConsoleCommandFile
/*    */ {
/*    */   private final bib mc;
/*    */   private final IMacro macro;
/*    */   private final IMacroParamStorage paramStorage;
/*    */   
/*    */   public ReplConsoleCommandRun(Repl repl, Macros macros, bib minecraft, IMacro macro, IMacroParamStorage paramStorage) {
/* 22 */     super(repl, "run", macros);
/*    */     
/* 24 */     this.mc = minecraft;
/* 25 */     this.macro = macro;
/* 26 */     this.paramStorage = paramStorage;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(IReplConsole console, String[] commandLine, String rawCommand) {
/* 32 */     int offset = 1;
/*    */     
/* 34 */     if (!getName().equals(commandLine[0])) {
/*    */       
/* 36 */       if (".".equals(rawCommand)) {
/*    */         
/* 38 */         console.addLine(I18n.get("repl.console.run.nofile", new Object[] { "" }));
/* 39 */         return true;
/*    */       } 
/*    */       
/* 42 */       if (!commandLine[0].matches("^\\..+$"))
/*    */       {
/* 44 */         return false;
/*    */       }
/*    */       
/* 47 */       offset = 0;
/* 48 */       commandLine[0] = commandLine[0].substring(1);
/*    */     } 
/*    */     
/* 51 */     if (commandLine.length < offset + 1) {
/*    */       
/* 53 */       console.addLine("§a" + getUsage());
/* 54 */       console.addLine("§6" + getDescription());
/* 55 */       return true;
/*    */     } 
/*    */     
/* 58 */     String fileName = getFileName(console, commandLine[offset], true);
/* 59 */     if (fileName == null)
/*    */     {
/* 61 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 65 */     MacroExecVariableProvider contextProvider = new MacroExecVariableProvider(commandLine, offset + 1, ScriptContext.MAIN.getScriptActionProvider(), this.macro);
/*    */ 
/*    */     
/* 68 */     ReplFileMacro macro = new ReplFileMacro(this.macros, this.mc, fileName, this.repl, console, this.paramStorage, contextProvider);
/* 69 */     macro.play();
/*    */     
/* 71 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandRun.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */