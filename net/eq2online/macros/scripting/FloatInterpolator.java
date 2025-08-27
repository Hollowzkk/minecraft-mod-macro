/*     */ package net.eq2online.macros.scripting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FloatInterpolator
/*     */ {
/*     */   protected InterpolationType interpolationType;
/*     */   private float start;
/*     */   private float target;
/*     */   private long startTime;
/*     */   private long duration;
/*     */   
/*     */   public enum InterpolationType
/*     */   {
/*  17 */     Linear,
/*  18 */     Smooth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean finished = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float interpolationMultiplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatInterpolator(float startValue, float targetValue, long duration, InterpolationType interpolationType) {
/*  48 */     this.interpolationType = interpolationType;
/*     */     
/*  50 */     this.startTime = System.currentTimeMillis();
/*  51 */     this.duration = duration;
/*     */     
/*  53 */     this.start = startValue;
/*  54 */     this.target = targetValue;
/*     */     
/*  56 */     if (startValue == targetValue || duration == 0L) {
/*     */       
/*  58 */       this.finished = true;
/*     */     }
/*  60 */     else if (startValue > targetValue) {
/*     */       
/*  62 */       this.interpolationMultiplier = -1.0F;
/*     */     }
/*     */     else {
/*     */       
/*  66 */       this.interpolationMultiplier = 1.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float interpolate() {
/*  77 */     if (this.finished)
/*     */     {
/*  79 */       return Float.valueOf(this.target);
/*     */     }
/*     */     
/*  82 */     float deltaTime = (float)(System.currentTimeMillis() - this.startTime) / (float)this.duration;
/*     */     
/*  84 */     if (deltaTime >= 1.0F) {
/*     */       
/*  86 */       this.finished = true;
/*  87 */       return Float.valueOf(this.target);
/*     */     } 
/*     */     
/*  90 */     if (this.interpolationType == InterpolationType.Smooth)
/*     */     {
/*  92 */       deltaTime = (float)(Math.sin((deltaTime - 0.5D) * Math.PI) * 0.5D) + 0.5F;
/*     */     }
/*     */     
/*  95 */     float value = this.start + deltaTime * (this.target - this.start);
/*     */     
/*  97 */     if ((this.interpolationMultiplier > 0.0F && value >= this.target) || (this.interpolationMultiplier < 0.0F && value <= this.target)) {
/*     */       
/*  99 */       value = this.target;
/* 100 */       this.finished = true;
/*     */     } 
/*     */     
/* 103 */     return Float.valueOf(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFinished() {
/* 108 */     return this.finished;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\FloatInterpolator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */