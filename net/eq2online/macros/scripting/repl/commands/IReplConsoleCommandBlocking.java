package net.eq2online.macros.scripting.repl.commands;

import net.eq2online.macros.scripting.repl.IReplConsole;

public interface IReplConsoleCommandBlocking extends IReplConsoleCommand {
  boolean isBlocked();
  
  void keyTyped(IReplConsole paramIReplConsole, int paramInt, char paramChar);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\IReplConsoleCommandBlocking.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */