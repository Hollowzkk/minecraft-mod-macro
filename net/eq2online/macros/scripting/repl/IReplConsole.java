/*    */ package net.eq2online.macros.scripting.repl;public interface IReplConsole extends IContentRenderer { ConsoleMode getMode(); void setMode(ConsoleMode paramConsoleMode);
/*    */   void display();
/*    */   void addLine(String paramString);
/*    */   void addLineWrapped(String paramString);
/*    */   void append(String paramString);
/*    */   void clearScreen();
/*    */   void closeConsole();
/*    */   void editFile(String paramString);
/*  9 */   public enum ConsoleMode { EXEC("§6$§f "),
/* 10 */     APPEND("§a+§f    ");
/*    */     
/*    */     private final String prompt;
/*    */ 
/*    */     
/*    */     ConsoleMode(String prompt) {
/* 16 */       this.prompt = prompt;
/*    */     }
/*    */ 
/*    */     
/*    */     public String getPrompt() {
/* 21 */       return this.prompt;
/*    */     } }
/*    */    }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\IReplConsole.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */