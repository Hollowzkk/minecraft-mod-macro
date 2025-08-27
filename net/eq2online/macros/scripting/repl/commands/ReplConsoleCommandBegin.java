/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ 
/*    */ public class ReplConsoleCommandBegin
/*    */   extends ReplConsoleCommand
/*    */ {
/*    */   public ReplConsoleCommandBegin(Repl repl) {
/* 11 */     super(repl, "begin");
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
/* 22 */     console.setMode(IReplConsole.ConsoleMode.APPEND);
/*    */     
/* 24 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandBegin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */