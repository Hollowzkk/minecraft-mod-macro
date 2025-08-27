package net.eq2online.macros.scripting.api;

import java.util.List;
import net.eq2online.macros.scripting.ActionParser;
import net.eq2online.macros.scripting.parser.ScriptContext;

public interface IScriptParser {
  void addActionParser(ActionParser paramActionParser);
  
  List<IMacroAction> parseScript(IMacroActionProcessor paramIMacroActionProcessor, String paramString);
  
  ScriptContext getContext();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IScriptParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */