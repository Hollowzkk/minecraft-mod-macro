/*     */ package net.eq2online.macros.gui.overlay;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ 
/*     */ 
/*     */ public class ConfigOverlay
/*     */   extends Overlay
/*     */ {
/*     */   private long lastWorldTime;
/*     */   private long activeConfigTimer;
/*  14 */   private long nextConfigTimer = 40L;
/*     */ 
/*     */   
/*     */   private String lastActiveConfigName;
/*     */   
/*     */   private String lastActiveOverlayName;
/*     */ 
/*     */   
/*     */   public ConfigOverlay(Macros macros, bib minecraft, OverlayHandler handler) {
/*  23 */     super(macros, minecraft, handler);
/*     */     
/*  25 */     this.lastActiveConfigName = macros.getActiveConfigName();
/*  26 */     this.lastActiveOverlayName = macros.getOverlayConfigName("");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNextConfigTimer(long timeout) {
/*  37 */     this.nextConfigTimer = timeout;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(int mouseX, int mouseY, long tick, float partialTick, boolean clock) {
/*  43 */     if (this.settings.enableConfigOverlay)
/*     */     {
/*  45 */       drawPopups(tick, partialTick);
/*     */     }
/*     */     
/*  48 */     this.lastWorldTime = tick;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawPopups(long worldTime, float partialTick) {
/*  60 */     if (this.lastWorldTime != worldTime && this.activeConfigTimer > 0L)
/*     */     {
/*  62 */       this.activeConfigTimer = Math.max(0L, this.activeConfigTimer - worldTime - this.lastWorldTime);
/*     */     }
/*     */     
/*  65 */     if (!this.lastActiveConfigName.equals(this.macros.getActiveConfigName()) || 
/*  66 */       !this.lastActiveOverlayName.equals(this.macros.getOverlayConfigName(""))) {
/*     */       
/*  68 */       this.activeConfigTimer = this.nextConfigTimer;
/*  69 */       this.lastActiveConfigName = this.macros.getActiveConfigName();
/*  70 */       this.lastActiveOverlayName = this.macros.getOverlayConfigName("");
/*     */     } 
/*     */     
/*  73 */     if (this.mc.m == null && this.activeConfigTimer > 0L) {
/*     */       
/*  75 */       float alpha = (this.activeConfigTimer > 5L) ? 1.0F : ((float)this.activeConfigTimer / 5.0F);
/*  76 */       int fgAlpha = ((int)(255.0F * alpha) & 0xFF) << 24;
/*  77 */       int bgAlpha = ((int)(176.0F * alpha) & 0xFF) << 24;
/*     */       
/*  79 */       int top1 = (int)((this.activeConfigTimer >= this.nextConfigTimer - 5L) ? (2L - ((int)this.activeConfigTimer - this.nextConfigTimer - 5L) * 4L) : 2L);
/*     */ 
/*     */ 
/*     */       
/*  83 */       GL.glDisableDepthTest();
/*  84 */       GL.glDisableLighting();
/*  85 */       GL.glDisableBlend();
/*     */       
/*  87 */       String currentConfig = I18n.get("macro.currentconfig", new Object[] { this.macros.getActiveConfigName() });
/*  88 */       drawTitle(currentConfig, false, 180, top1, this.width - 2, fgAlpha | 0x40FF40, bgAlpha | 0x0);
/*     */       
/*  90 */       if (this.macros.getOverlayConfig() != null && this.activeConfigTimer < 34L) {
/*     */         
/*  92 */         int top2 = (int)((this.activeConfigTimer >= this.nextConfigTimer - 10L) ? (22L + ((int)this.activeConfigTimer - this.nextConfigTimer - 10L) * 4L) : 22L);
/*     */ 
/*     */         
/*  95 */         drawTitle(this.macros.getOverlayConfigName(""), false, 180, top2, this.width - 2, fgAlpha | 0xFF4040, bgAlpha | 0x0);
/*     */       } 
/*     */       
/*  98 */       GL.glDisableBlend();
/*     */     }
/*     */     else {
/*     */       
/* 102 */       this.nextConfigTimer = 40L;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\overlay\ConfigOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */