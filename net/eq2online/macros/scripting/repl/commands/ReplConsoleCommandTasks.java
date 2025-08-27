/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ 
/*    */ public class ReplConsoleCommandTasks
/*    */   extends ReplConsoleCommand
/*    */ {
/*    */   private final Macros macros;
/*    */   
/*    */   public ReplConsoleCommandTasks(Repl repl, Macros macros) {
/* 17 */     super(repl, "tasks");
/* 18 */     this.macros = macros;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(IReplConsole console, String[] commandLine, String rawCommand) {
/* 24 */     if (!getName().equals(commandLine[0]) && !"ps".equals(commandLine[0]))
/*    */     {
/* 26 */       return false;
/*    */     }
/*    */     
/* 29 */     List<IMacro.IMacroStatus> executingMacroStatus = this.macros.getExecutingMacroStatus();
/* 30 */     for (IMacro.IMacroStatus status : executingMacroStatus) {
/*    */       
/* 32 */       float runtime = (float)status.getRunTime() / 1000.0F;
/* 33 */       console.addLine(I18n.get("repl.console.tasks.line", new Object[] { Integer.valueOf(status.getMacro().getID()), status.getMacro().getDisplayName(), Float.valueOf(runtime) }));
/*    */     } 
/* 35 */     console.addLine(I18n.get("repl.console.tasks.summary", new Object[] { Integer.valueOf(executingMacroStatus.size()) }));
/* 36 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandTasks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */