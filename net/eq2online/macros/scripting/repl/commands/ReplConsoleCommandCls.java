/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ public class ReplConsoleCommandCls
/*    */   extends ReplConsoleCommand
/*    */ {
/*    */   public ReplConsoleCommandCls(Repl repl) {
/* 10 */     super(repl, "cls");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(IReplConsole console, String[] commandLine, String rawCommand) {
/* 16 */     if (!getName().equals(commandLine[0]))
/*    */     {
/* 18 */       return false;
/*    */     }
/*    */     
/* 21 */     console.clearScreen();
/* 22 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandCls.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */