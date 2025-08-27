/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.regex.Matcher;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.MacroIncludeProcessor;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ 
/*    */ public abstract class ReplConsoleCommandFile
/*    */   extends ReplConsoleCommandFiles
/*    */ {
/*    */   public ReplConsoleCommandFile(Repl repl, String name, Macros macros) {
/* 16 */     super(repl, name, macros);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getFileName(IReplConsole console, String arg, boolean mustExist) {
/* 21 */     Matcher matcher = MacroIncludeProcessor.PATTERN_FILE_NAME_OPTEXT.matcher(arg);
/* 22 */     if (!matcher.matches()) {
/*    */       
/* 24 */       console.addLine(I18n.get("repl.console.run.badfile", new Object[] { arg }));
/* 25 */       return null;
/*    */     } 
/*    */     
/* 28 */     String fileName = arg;
/*    */     
/* 30 */     if (!".txt".equals(matcher.group(1)))
/*    */     {
/* 32 */       fileName = fileName + ".txt";
/*    */     }
/*    */     
/* 35 */     if (!Macros.isValidFileName(fileName)) {
/*    */       
/* 37 */       console.addLine(I18n.get("repl.console.run.badfile", new Object[] { arg }));
/* 38 */       return null;
/*    */     } 
/*    */     
/* 41 */     if (mustExist) {
/*    */       
/* 43 */       File file = this.macros.getFile(fileName);
/* 44 */       if (!file.isFile()) {
/*    */         
/* 46 */         console.addLine(I18n.get("repl.console.run.nofile", new Object[] { fileName }));
/* 47 */         return null;
/*    */       } 
/*    */     } 
/*    */     
/* 51 */     return fileName;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */