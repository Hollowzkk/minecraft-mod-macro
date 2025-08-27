/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ 
/*    */ public class ReplConsoleCommandLive
/*    */   extends ReplConsoleCommand
/*    */ {
/* 14 */   private static final List<String> ARGS = (List<String>)ImmutableList.of("enable", "disable", "on", "off", "0", "1");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReplConsoleCommandLive(Repl repl) {
/* 20 */     super(repl, "live");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(IReplConsole console, String[] commandLine, String rawCommand) {
/* 26 */     if (!getName().equals(commandLine[0]))
/*    */     {
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     if (commandLine.length > 1) {
/*    */       
/* 33 */       String arg = commandLine[1].trim().toLowerCase();
/* 34 */       if (arg.startsWith("en") || "on".equals(arg) || "1".equals(arg))
/*    */       {
/* 36 */         this.repl.setLive(true);
/*    */       }
/* 38 */       else if (arg.startsWith("di") || "off".equals(arg) || "0".equals(arg))
/*    */       {
/* 40 */         this.repl.setLive(false);
/*    */       }
/* 42 */       else if (arg.isEmpty())
/*    */       {
/* 44 */         this.repl.setLive(!this.repl.isLive());
/*    */       }
/*    */       else
/*    */       {
/* 48 */         console.addLine("§a" + getUsage());
/* 49 */         console.addLine("§6" + getDescription());
/* 50 */         return true;
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 55 */       this.repl.setLive(!this.repl.isLive());
/*    */     } 
/*    */     
/* 58 */     console.addLine(I18n.get("repl.console.live." + (this.repl.isLive() ? "enabled" : "disabled")));
/*    */     
/* 60 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getSuggestions(String tail) {
/* 66 */     if (tail.trim().isEmpty())
/*    */     {
/* 68 */       return (List<String>)ImmutableList.of("§don", "§doff");
/*    */     }
/*    */     
/* 71 */     List<String> matching = new ArrayList<>();
/*    */     
/* 73 */     for (String arg : ARGS) {
/*    */       
/* 75 */       if (arg.startsWith(tail.toLowerCase()))
/*    */       {
/* 77 */         matching.add("§d" + arg);
/*    */       }
/*    */     } 
/*    */     
/* 81 */     return matching;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandLive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */