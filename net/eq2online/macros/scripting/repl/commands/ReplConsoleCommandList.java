/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ 
/*    */ public class ReplConsoleCommandList
/*    */   extends ReplConsoleCommandFiles
/*    */ {
/*    */   public ReplConsoleCommandList(Repl repl, Macros macros) {
/* 14 */     super(repl, "list", macros);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(IReplConsole console, String[] commandLine, String rawCommand) {
/* 20 */     if (!"list".equals(commandLine[0]) && !"ls".equals(commandLine[0]) && !"dir".equals(commandLine[0]))
/*    */     {
/* 22 */       return false;
/*    */     }
/*    */     
/* 25 */     console.addLine(I18n.get("repl.console.list.message"));
/*    */     
/* 27 */     File[] files = getFiles();
/*    */     
/* 29 */     for (File file : files)
/*    */     {
/* 31 */       console.addLine("  " + file.getName());
/*    */     }
/*    */     
/* 34 */     console.addLine(I18n.get("repl.console.list.summary", new Object[] { Integer.valueOf(files.length) }));
/*    */     
/* 36 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */