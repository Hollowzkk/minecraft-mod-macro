/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileWriter;
/*    */ import java.io.PrintWriter;
/*    */ import net.eq2online.macros.scripting.ReturnValueLogTo;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.util.Util;
/*    */ 
/*    */ 
/*    */ public class ScriptActionLogTo
/*    */   extends ScriptAction
/*    */ {
/*    */   File logsFolder;
/*    */   
/*    */   public ScriptActionLogTo(ScriptContext context) {
/* 22 */     super(context, "logto");
/*    */   }
/*    */ 
/*    */   
/*    */   private void initLogsFolder(IScriptActionProvider provider) {
/* 27 */     if (this.logsFolder == null) {
/*    */       
/* 29 */       this.logsFolder = provider.getMacroEngine().getFile("logs");
/*    */ 
/*    */       
/*    */       try {
/* 33 */         if (!this.logsFolder.exists())
/*    */         {
/* 35 */           this.logsFolder.mkdirs();
/*    */         }
/*    */       }
/* 38 */       catch (Exception exception) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 45 */     if (params.length > 1) {
/*    */       
/* 47 */       String target = provider.expand(macro, params[0], false);
/*    */       
/* 49 */       if (target.toLowerCase().endsWith(".txt")) {
/*    */         
/* 51 */         String fileName = Util.sanitiseFileName(target, ".txt");
/* 52 */         initLogsFolder(provider);
/*    */ 
/*    */         
/*    */         try {
/* 56 */           if (fileName != null && this.logsFolder != null && this.logsFolder.exists())
/*    */           {
/* 58 */             File logFile = new File(this.logsFolder, fileName);
/*    */             
/* 60 */             PrintWriter printWriter = new PrintWriter(new FileWriter(logFile, true));
/* 61 */             printWriter.println(provider.expand(macro, params[1], false));
/* 62 */             printWriter.close();
/*    */           }
/*    */         
/* 65 */         } catch (Exception exception) {}
/*    */       }
/*    */       else {
/*    */         
/* 69 */         String message = Util.convertAmpCodes(provider.expand(macro, params[1], false));
/* 70 */         return (IReturnValue)new ReturnValueLogTo(message, target);
/*    */       } 
/*    */     } 
/*    */     
/* 74 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionLogTo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */