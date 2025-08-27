/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ public class ReplConsoleCommandKill
/*    */   extends ReplConsoleCommand
/*    */ {
/*    */   private final Macros macros;
/*    */   
/*    */   public ReplConsoleCommandKill(Repl repl, Macros macros) {
/* 15 */     super(repl, "kill");
/* 16 */     this.macros = macros;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(IReplConsole console, String[] commandLine, String rawCommand) {
/* 22 */     if (!getName().equals(commandLine[0]))
/*    */     {
/* 24 */       return false;
/*    */     }
/*    */     
/* 27 */     for (IMacro.IMacroStatus status : this.macros.getExecutingMacroStatus()) {
/*    */       
/* 29 */       if (commandLine.length < 2 || commandLine[1].equals(String.valueOf(status.getMacro().getID()))) {
/*    */         
/* 31 */         console.addLine(I18n.get("repl.console.kill.term", new Object[] { Integer.valueOf(status.getMacro().getID()) }));
/* 32 */         status.getMacro().kill();
/*    */       } 
/*    */     } 
/* 35 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandKill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */