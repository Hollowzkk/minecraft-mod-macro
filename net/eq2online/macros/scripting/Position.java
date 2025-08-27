/*    */ package net.eq2online.macros.scripting;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Position
/*    */ {
/*    */   public float x;
/*    */   public float y;
/*    */   public float z;
/*    */   public long duration;
/*    */   
/*    */   public Position() {}
/*    */   
/*    */   public Position(float x, float y, float z) {
/* 29 */     this.x = x;
/* 30 */     this.y = y;
/* 31 */     this.z = z;
/*    */   }
/*    */ 
/*    */   
/*    */   public Position setX(float x) {
/* 36 */     this.x = x;
/* 37 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Position setY(float y) {
/* 42 */     this.y = y;
/* 43 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Position setZ(float z) {
/* 48 */     this.z = z;
/* 49 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Position setPosition(float x, float y, float z) {
/* 54 */     this.x = x;
/* 55 */     this.y = y;
/* 56 */     this.z = z;
/* 57 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Position setDuration(long duration) {
/* 66 */     this.duration = duration;
/* 67 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 72 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Position clonePosition() {
/* 82 */     return new Position(this.x, this.y, this.z);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\Position.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */