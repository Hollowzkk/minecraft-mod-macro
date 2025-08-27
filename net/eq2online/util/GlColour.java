/*    */ package net.eq2online.util;
/*    */ 
/*    */ import com.mumfrey.liteloader.gl.GL;
/*    */ 
/*    */ 
/*    */ public class GlColour
/*    */ {
/*    */   public final float red;
/*    */   public final float green;
/*    */   public final float blue;
/*    */   
/*    */   public GlColour(float red, float green, float blue) {
/* 13 */     this.red = red;
/* 14 */     this.green = green;
/* 15 */     this.blue = blue;
/*    */   }
/*    */ 
/*    */   
/*    */   public void apply() {
/* 20 */     GL.glColor3f(this.red, this.green, this.blue);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2onlin\\util\GlColour.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */