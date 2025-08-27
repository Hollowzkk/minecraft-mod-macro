package net.eq2online.macros.scripting.api;

public interface IExpressionEvaluator extends IVariableListener {
  void dumpVariables();
  
  int addStringLiteral(String paramString);
  
  int evaluate();
  
  int getResult();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IExpressionEvaluator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */