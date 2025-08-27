/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ public class ReplConsoleCommandWhoami
/*    */   extends ReplConsoleCommand
/*    */ {
/*    */   private final bib mc;
/*    */   
/*    */   public ReplConsoleCommandWhoami(Repl repl, bib minecraft) {
/* 13 */     super(repl, "whoami");
/*    */     
/* 15 */     this.mc = minecraft;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(IReplConsole console, String[] commandLine, String rawCommand) {
/* 21 */     if (!getName().equals(commandLine[0]))
/*    */     {
/* 23 */       return false;
/*    */     }
/*    */     
/* 26 */     console.addLine(this.mc.h.h_());
/* 27 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandWhoami.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */