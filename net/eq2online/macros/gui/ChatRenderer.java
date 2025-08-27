/*    */ package net.eq2online.macros.gui;
/*    */ 
/*    */ import aed;
/*    */ import bib;
/*    */ import com.mumfrey.liteloader.gl.GL;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.gui.interfaces.IContentRenderer;
/*    */ 
/*    */ public class ChatRenderer
/*    */   extends GuiRendererMacros
/*    */   implements IContentRenderer
/*    */ {
/*    */   private int height;
/* 14 */   private aed.b oldChatVisibility = null;
/*    */ 
/*    */   
/*    */   public ChatRenderer(bib minecraft, Macros macros) {
/* 18 */     super(macros, minecraft);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void connect(int width, int height) {
/* 24 */     this.height = height;
/*    */     
/* 26 */     if (this.oldChatVisibility == null)
/*    */     {
/* 28 */       this.oldChatVisibility = this.mc.t.o;
/*    */     }
/*    */     
/* 31 */     if ((this.macros.getSettings()).showChatInParamScreen)
/*    */     {
/* 33 */       this.mc.t.o = aed.b.a;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void disconnect() {
/* 40 */     if (this.oldChatVisibility != null) {
/*    */       
/* 42 */       this.mc.t.o = this.oldChatVisibility;
/* 43 */       this.oldChatVisibility = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(int mouseX, int mouseY, float partialTicks) {
/* 50 */     if (!(this.macros.getSettings()).showChatInParamScreen || this.oldChatVisibility == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 55 */     this.mc.t.o = aed.b.a;
/* 56 */     GL.glDisableBlend();
/* 57 */     GL.glBlendFunc(770, 771);
/* 58 */     GL.glDisableAlphaTest();
/* 59 */     GL.glPushMatrix();
/* 60 */     GL.glTranslatef(0.0F, (this.height - 48), 0.0F);
/* 61 */     this.mc.q.d().a(0);
/* 62 */     GL.glPopMatrix();
/* 63 */     GL.glEnableAlphaTest();
/* 64 */     this.mc.t.o = aed.b.c;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\ChatRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */