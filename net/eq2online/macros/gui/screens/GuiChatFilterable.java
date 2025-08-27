/*    */ package net.eq2online.macros.gui.screens;
/*    */ 
/*    */ import bkn;
/*    */ import blk;
/*    */ import java.io.IOException;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.event.BuiltinEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiChatFilterable
/*    */   extends bkn
/*    */ {
/*    */   private final Macros macros;
/*    */   private boolean canType = false;
/*    */   
/*    */   public GuiChatFilterable(Macros macros) {
/* 26 */     this.macros = macros;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void e() {
/* 32 */     super.e();
/* 33 */     this.canType = true;
/* 34 */     this.a.f(1024);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void a(char keyChar, int keyCode) throws IOException {
/* 40 */     if (!this.canType)
/*    */       return; 
/* 42 */     if (keyCode == 28 || keyCode == 156) {
/*    */       
/* 44 */       String text = this.a.b().trim();
/*    */       
/* 46 */       if (text.length() > 0) {
/*    */         
/* 48 */         this.j.q.d().a(text);
/* 49 */         this.macros.sendEvent(BuiltinEvent.onFilterableChat.getName(), 100, new String[] { text.replace("|", "\\|") });
/*    */       } 
/*    */       
/* 52 */       this.j.a((blk)null);
/*    */     }
/*    */     else {
/*    */       
/* 56 */       super.a(keyChar, keyCode);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiChatFilterable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */