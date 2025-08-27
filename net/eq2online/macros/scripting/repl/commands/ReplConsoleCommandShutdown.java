/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ public class ReplConsoleCommandShutdown
/*    */   extends ReplConsoleCommand
/*    */ {
/*    */   private final bib mc;
/*    */   
/*    */   public ReplConsoleCommandShutdown(Repl repl, bib minecraft) {
/* 13 */     super(repl, "shutdown");
/* 14 */     this.mc = minecraft;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(IReplConsole console, String[] commandLine, String rawCommand) {
/* 20 */     if (!getName().equals(commandLine[0]))
/*    */     {
/* 22 */       return false;
/*    */     }
/*    */     
/* 25 */     this.mc.n();
/* 26 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandShutdown.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */