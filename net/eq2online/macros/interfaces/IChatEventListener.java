package net.eq2online.macros.interfaces;

public interface IChatEventListener {
  void onChatMessage(String paramString1, String paramString2);
  
  boolean onSendChatMessage(String paramString);
  
  void onLogMessage(String paramString);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\IChatEventListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */