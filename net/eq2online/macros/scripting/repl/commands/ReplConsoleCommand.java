/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ReplConsoleCommand
/*    */   implements IReplConsoleCommand
/*    */ {
/*    */   protected final Repl repl;
/*    */   private final String name;
/*    */   
/*    */   public ReplConsoleCommand(Repl repl, String name) {
/* 16 */     this.repl = repl;
/* 17 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 23 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUsage() {
/* 29 */     return I18n.get("repl.console.command." + this.name + ".usage");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 35 */     return I18n.get("repl.console.command." + this.name + ".help");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getSuggestions(String tail) {
/* 41 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */