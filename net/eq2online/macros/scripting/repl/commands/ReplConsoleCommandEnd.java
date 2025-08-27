/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ 
/*    */ public class ReplConsoleCommandEnd
/*    */   extends ReplConsoleCommand
/*    */ {
/*    */   public ReplConsoleCommandEnd(Repl repl) {
/* 11 */     super(repl, "end");
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
/* 22 */     console.setMode(IReplConsole.ConsoleMode.EXEC);
/*    */     
/* 24 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandEnd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */