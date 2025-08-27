/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FilenameFilter;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.TreeSet;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ 
/*    */ public abstract class ReplConsoleCommandFiles
/*    */   extends ReplConsoleCommand
/*    */   implements FilenameFilter
/*    */ {
/*    */   protected final Macros macros;
/*    */   protected final File directory;
/*    */   
/*    */   public ReplConsoleCommandFiles(Repl repl, String name, Macros macros) {
/* 21 */     super(repl, name);
/* 22 */     this.macros = macros;
/* 23 */     this.directory = macros.getMacrosDirectory();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean accept(File dir, String name) {
/* 29 */     if (name.startsWith("."))
/*    */     {
/* 31 */       return false;
/*    */     }
/*    */     
/* 34 */     if (name.matches("^[A-Za-z0-9\\x20_\\-\\.]+\\.txt$"))
/*    */     {
/* 36 */       return true;
/*    */     }
/*    */     
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected File[] getFiles() {
/* 44 */     return this.directory.listFiles(this);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Set<String> getFileNames() {
/* 49 */     Set<String> fileNames = new TreeSet<>();
/* 50 */     for (File file : getFiles())
/*    */     {
/* 52 */       fileNames.add(file.getName());
/*    */     }
/* 54 */     return fileNames;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getSuggestions(String tail) {
/* 60 */     List<String> matching = new ArrayList<>();
/*    */     
/* 62 */     if (!tail.contains(" "))
/*    */     {
/* 64 */       for (String fileName : getFileNames()) {
/*    */         
/* 66 */         if (tail.isEmpty() || fileName.toLowerCase().startsWith(tail.toLowerCase()))
/*    */         {
/* 68 */           matching.add("§d" + fileName);
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 73 */     return matching;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandFiles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */