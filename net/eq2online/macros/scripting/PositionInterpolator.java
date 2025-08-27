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
/*    */ public final class PositionInterpolator
/*    */ {
/*    */   private final FloatInterpolator xInterpolator;
/*    */   private final FloatInterpolator yInterpolator;
/*    */   private final FloatInterpolator zInterpolator;
/*    */   private final int id;
/*    */   
/*    */   public PositionInterpolator(Position initialPos, Position targetPos, FloatInterpolator.InterpolationType interpolationType, int id) {
/* 23 */     this.id = id;
/*    */     
/* 25 */     this.xInterpolator = new FloatInterpolator(initialPos.x, targetPos.x, targetPos.duration, interpolationType);
/* 26 */     this.yInterpolator = new FloatInterpolator(initialPos.y, targetPos.y, targetPos.duration, interpolationType);
/* 27 */     this.zInterpolator = new FloatInterpolator(initialPos.z, targetPos.z, targetPos.duration, interpolationType);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 32 */     return this.id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Position interpolate() {
/* 42 */     return new Position(this.xInterpolator.interpolate().floatValue(), this.yInterpolator.interpolate().floatValue(), this.zInterpolator.interpolate().floatValue());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFinished() {
/* 52 */     return (this.xInterpolator.isFinished() && this.yInterpolator.isFinished() && this.zInterpolator.isFinished());
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\PositionInterpolator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */