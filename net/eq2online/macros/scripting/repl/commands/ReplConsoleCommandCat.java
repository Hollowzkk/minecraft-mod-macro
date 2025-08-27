/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileReader;
/*    */ import java.io.IOException;
/*    */ import net.eq2online.console.Log;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.interfaces.IHighlighter;
/*    */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ 
/*    */ public class ReplConsoleCommandCat
/*    */   extends ReplConsoleCommandFile
/*    */ {
/*    */   public ReplConsoleCommandCat(Repl repl, Macros macros) {
/* 18 */     super(repl, "cat", macros);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(IReplConsole console, String[] commandLine, String rawCommand) {
/* 24 */     if (!getName().equals(commandLine[0]))
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
/* 42 */     catFile(console, fileName);
/*    */     
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   private void catFile(IReplConsole console, String fileName) {
/* 49 */     File file = this.macros.getFile(fileName);
/* 50 */     BufferedReader reader = null;
/*    */ 
/*    */     
/*    */     try {
/* 54 */       reader = new BufferedReader(new FileReader(file));
/*    */       
/* 56 */       for (String line = ""; (line = reader.readLine()) != null; ) {
/*    */         
/* 58 */         if (console instanceof IHighlighter)
/*    */         {
/* 60 */           line = ((IHighlighter)console).highlight(line);
/*    */         }
/*    */         
/* 63 */         console.addLine(line);
/*    */       } 
/*    */       
/* 66 */       reader.close();
/*    */     }
/* 68 */     catch (Exception ex) {
/*    */       
/* 70 */       Log.info("Failed loading include file '{0}'", new Object[] { fileName });
/*    */     }
/*    */     finally {
/*    */       
/* 74 */       if (reader != null)
/*    */         
/*    */         try {
/*    */           
/* 78 */           reader.close();
/*    */         }
/* 80 */         catch (IOException ex) {
/*    */           
/* 82 */           ex.printStackTrace();
/*    */         }  
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandCat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */