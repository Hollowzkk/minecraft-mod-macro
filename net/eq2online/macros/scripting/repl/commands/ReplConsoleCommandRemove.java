/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ public class ReplConsoleCommandRemove
/*    */   extends ReplConsoleCommandFile
/*    */   implements IReplConsoleCommandBlocking
/*    */ {
/*    */   private String pendingFile;
/*    */   
/*    */   public ReplConsoleCommandRemove(Repl repl, Macros macros) {
/* 16 */     super(repl, "rm", macros);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(IReplConsole console, String[] commandLine, String rawCommand) {
/* 22 */     this.pendingFile = null;
/*    */     
/* 24 */     if (!getName().equals(commandLine[0]) && !"remove".equals(commandLine[0]) && !"del".equals(commandLine[0]))
/*    */     {
/* 26 */       return false;
/*    */     }
/*    */     
/* 29 */     if (commandLine.length < 2) {
/*    */       
/* 31 */       console.addLine("§a" + getUsage());
/* 32 */       console.addLine("§6" + getDescription());
/* 33 */       return true;
/*    */     } 
/*    */     
/* 36 */     String fileName = getFileName(console, commandLine[1], true);
/* 37 */     if (fileName == null)
/*    */     {
/* 39 */       return true;
/*    */     }
/*    */     
/* 42 */     console.addLine(I18n.get("repl.console.rm.prompt", new Object[] { fileName }));
/* 43 */     this.pendingFile = fileName;
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isBlocked() {
/* 51 */     return (this.pendingFile != null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void keyTyped(IReplConsole console, int keyCode, char keyChar) {
/* 57 */     String fileName = this.pendingFile;
/* 58 */     this.pendingFile = null;
/* 59 */     if (keyChar == 'y' || keyChar == 'Y') {
/*    */       
/* 61 */       console.append(String.valueOf(keyChar));
/* 62 */       File file = this.macros.getFile(fileName);
/* 63 */       if (file.delete())
/*    */       {
/* 65 */         console.addLine(I18n.get("repl.console.rm.done", new Object[] { fileName }));
/*    */       }
/*    */     }
/* 68 */     else if (keyChar == 'n' || keyChar == 'N') {
/*    */       
/* 70 */       console.append("n");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandRemove.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */