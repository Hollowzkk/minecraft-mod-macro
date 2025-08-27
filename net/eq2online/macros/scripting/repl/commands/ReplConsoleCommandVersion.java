/*    */ package net.eq2online.macros.scripting.repl.commands;
/*    */ 
/*    */ import com.google.common.base.Strings;
/*    */ import com.mumfrey.liteloader.core.LiteLoader;
/*    */ import net.eq2online.macros.LiteModMacros;
/*    */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*    */ import net.eq2online.macros.scripting.repl.Repl;
/*    */ 
/*    */ 
/*    */ public class ReplConsoleCommandVersion
/*    */   extends ReplConsoleCommand
/*    */ {
/*    */   public ReplConsoleCommandVersion(Repl repl) {
/* 14 */     super(repl, "version");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(IReplConsole console, String[] commandLine, String rawCommand) {
/* 20 */     if (!"version".equals(commandLine[0]) && !"ver".equals(commandLine[0]))
/*    */     {
/* 22 */       return false;
/*    */     }
/*    */     
/* 25 */     LiteLoader loader = LiteLoader.getInstance();
/* 26 */     LiteModMacros mod = (LiteModMacros)loader.getMod(LiteModMacros.class);
/*    */     
/* 28 */     console.addLine(String.format("§b%s %s", new Object[] { LiteLoader.getVersionDisplayString(), Strings.nullToEmpty(LiteLoader.getBranding()) }));
/* 29 */     console.addLine(String.format("§a%s v%s", new Object[] { mod.getName(), mod.getVersion() }));
/*    */     
/* 31 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\ReplConsoleCommandVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */