/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ public class ReplConsoleCommandEdit
/*    */   extends ReplConsoleCommandFile
/*    */ {
/*    */   public ReplConsoleCommandEdit(Repl repl, Macros macros) {
/* 11 */     super(repl, "edit", macros);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(IReplConsole console, String[] commandLine, String rawCommand) {
/* 17 */     if (!getName().equals(commandLine[0]))
/*    */     {
/* 19 */       return false;
/*    */     }
/*    */     
/* 22 */     if (commandLine.length < 2) {
/*    */       
/* 24 */       console.editFile(null);
/*    */ 
/*    */       
/* 27 */       return true;
/*    */     } 
/*    */     
/* 30 */     String fileName = getFileName(console, commandLine[1], false);
/* 31 */     if (fileName == null)
/*    */     {
/* 33 */       return true;
/*    */     }
/*    */     
/* 36 */     console.editFile(fileName);
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandEdit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */