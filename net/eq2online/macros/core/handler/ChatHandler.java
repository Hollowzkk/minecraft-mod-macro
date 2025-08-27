/*    */ package net.eq2online.macros.core.handler;
/*    */ 
/*    */ import bib;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.eq2online.macros.interfaces.IChatEventListener;
/*    */ import rp;
/*    */ 
/*    */ 
/*    */ public class ChatHandler
/*    */ {
/* 12 */   private final List<IChatEventListener> chatListeners = new ArrayList<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public ChatHandler(bib mc) {}
/*    */ 
/*    */   
/*    */   public void registerListener(IChatEventListener listener) {
/* 20 */     if (!this.chatListeners.contains(listener))
/*    */     {
/* 22 */       this.chatListeners.add(listener);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void unregisterListener(IChatEventListener listener) {
/* 28 */     this.chatListeners.remove(listener);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onChat(String chatMessage) {
/* 38 */     String chatMessageNoColours = rp.a(chatMessage);
/*    */     
/* 40 */     for (IChatEventListener listener : this.chatListeners)
/*    */     {
/* 42 */       listener.onChatMessage(chatMessage, chatMessageNoColours);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onSendChatMessage(String message) {
/* 48 */     boolean pass = true;
/*    */     
/* 50 */     for (IChatEventListener listener : this.chatListeners)
/*    */     {
/* 52 */       pass &= listener.onSendChatMessage(message);
/*    */     }
/*    */     
/* 55 */     return pass;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onLogMessage(String message) {
/* 60 */     for (IChatEventListener listener : this.chatListeners)
/*    */     {
/* 62 */       listener.onLogMessage(message);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\handler\ChatHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */