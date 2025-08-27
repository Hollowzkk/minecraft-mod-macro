/*     */ package net.eq2online.macros.gui.overlay;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.interfaces.annotations.DropdownLocalisationRoot;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OverrideOverlay
/*     */   extends Overlay
/*     */ {
/*     */   private final InputHandler inputHandler;
/*     */   private long lastWorldTime;
/*     */   private float lastPartialTick;
/*     */   
/*     */   @DropdownLocalisationRoot("override.popup.style")
/*     */   public enum Style
/*     */   {
/*  24 */     NORMAL,
/*  25 */     MINI,
/*  26 */     NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   private float overrideTween = 0.0F;
/*  35 */   private float overrideTweenRate = 0.06F;
/*     */ 
/*     */   
/*     */   public OverrideOverlay(Macros macros, bib minecraft, OverlayHandler handler) {
/*  39 */     super(macros, minecraft, handler);
/*     */     
/*  41 */     this.inputHandler = macros.getInputHandler();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(int mouseX, int mouseY, long tick, float partialTick, boolean clock) {
/*  47 */     if (this.settings.enableOverride && !this.settings.compatibilityMode && this.inputHandler
/*     */       
/*  49 */       .isScreenOverridable(this.mc.m) && this.inputHandler
/*  50 */       .isOverrideKeyDown()) {
/*     */       
/*  52 */       drawOverride(tick, partialTick);
/*     */     }
/*     */     else {
/*     */       
/*  56 */       this.overrideTween = 0.0F;
/*     */     } 
/*     */     
/*  59 */     this.lastWorldTime = tick;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawOverride(long tick, float partialTick) {
/*  69 */     GL.glDisableDepthTest();
/*  70 */     GL.glDisableLighting();
/*     */     
/*  72 */     if (this.settings.simpleOverridePopup == 1) {
/*     */       
/*  74 */       drawSimpleOverride(tick, partialTick);
/*     */     }
/*  76 */     else if (this.settings.simpleOverridePopup == 0) {
/*     */       
/*  78 */       drawOverrideBar();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawOverrideBar() {
/*  84 */     a(2, this.height - 14, this.width - 2, this.height - 2, -2147483648);
/*  85 */     c(this.fontRenderer, I18n.get("macro.prompt.execute"), 4, this.height - 12, 60928);
/*     */     
/*  87 */     if (this.settings.enableStatus)
/*     */     {
/*  89 */       this.macros.getPlaybackStatus().drawOverlay(new Rectangle(4, 10, this.width - 4, this.height - 10), 0, 0, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawSimpleOverride(long tick, float partialTick) {
/*  95 */     if (this.overrideTween < 0.5F) {
/*     */       
/*  97 */       float deltaTime = (float)(tick - this.lastWorldTime) + partialTick + this.lastPartialTick;
/*  98 */       this.overrideTween += deltaTime * this.overrideTweenRate;
/*     */     } 
/*     */     
/* 101 */     if (this.overrideTween > 0.5F) this.overrideTween = 0.5F;
/*     */     
/* 103 */     float overrideTweenPos = -14.0F * (float)Math.sin(Math.PI * Math.min(this.overrideTween, 0.5F));
/*     */     
/* 105 */     GL.glPushMatrix();
/* 106 */     GL.glTranslatef(0.0F, this.height + overrideTweenPos, 0.0F);
/*     */     
/* 108 */     a(2, 0, 40, 12, (int)(255.0F * this.overrideTween) << 24);
/* 109 */     GL.glDisableBlend();
/* 110 */     GL.glColor4f(0.0F, 0.93F, 0.0F, this.overrideTween * 2.0F);
/* 111 */     this.mc.N().a(ResourceLocations.MAIN);
/* 112 */     drawTexturedModalRect(5, 2, 17, 10, 104, 48, 128, 64);
/* 113 */     this.fontRenderer.a("OVR", 20.0F, 3.0F, (int)(510.0F * this.overrideTween) << 24 | 0xEE00);
/*     */     
/* 115 */     GL.glDisableBlend();
/* 116 */     GL.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\overlay\OverrideOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */