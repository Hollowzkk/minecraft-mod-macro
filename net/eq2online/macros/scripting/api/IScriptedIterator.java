package net.eq2online.macros.scripting.api;

public interface IScriptedIterator extends IVariableProvider {
  boolean isActive();
  
  void increment();
  
  void reset();
  
  void terminate();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IScriptedIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */