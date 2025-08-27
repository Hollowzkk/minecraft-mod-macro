/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ 
/*    */ public class ReplConsoleCommandSay
/*    */   extends ReplConsoleCommand
/*    */ {
/*    */   private final Macros macros;
/*    */   private final bib mc;
/*    */   
/*    */   public ReplConsoleCommandSay(Repl repl, Macros macros, bib minecraft) {
/* 17 */     super(repl, "say");
/* 18 */     this.macros = macros;
/* 19 */     this.mc = minecraft;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(IReplConsole console, String[] commandLine, String rawCommand) {
/* 25 */     if (!getName().equals(commandLine[0]))
/*    */     {
/* 27 */       return false;
/*    */     }
/*    */     
/* 30 */     if (commandLine.length > 1) {
/*    */       
/* 32 */       int pos = rawCommand.indexOf(' ');
/* 33 */       String message = rawCommand.substring(pos + 1);
/*    */       
/* 35 */       if (this.repl.isLive()) {
/*    */         
/* 37 */         this.macros.dispatchChatMessage(message, this.repl.getContext());
/*    */       }
/*    */       else {
/*    */         
/* 41 */         console.addLine(I18n.get("repl.return.chat", new Object[] { this.mc.h.h_(), message }));
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandSay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */