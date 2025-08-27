package net.eq2online.macros.scripting.repl.commands;

import java.util.List;
import net.eq2online.macros.interfaces.ICommandInfo;
import net.eq2online.macros.scripting.repl.IReplConsole;

public interface IReplConsoleCommand extends ICommandInfo {
  boolean execute(IReplConsole paramIReplConsole, String[] paramArrayOfString, String paramString);
  
  List<String> getSuggestions(String paramString);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\commands\IReplConsoleCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */