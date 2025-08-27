/*     */ package net.eq2online.macros.gui.overlay;
/*     */ 
/*     */ import bib;
/*     */ import bit;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OverlayHandler
/*     */ {
/*     */   private final bib mc;
/*  21 */   private final List<IOverlay> overlays = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OverlayHandler(Macros macros, bib minecraft) {
/*  30 */     this.mc = minecraft;
/*     */     
/*  32 */     this.overlays.add(new DebugOverlay(macros, minecraft, this));
/*  33 */     this.overlays.add(new ThumbnailOverlay(macros, minecraft, this));
/*  34 */     this.overlays.add(new OverrideOverlay(macros, minecraft, this));
/*  35 */     this.overlays.add(new ConfigOverlay(macros, minecraft, this));
/*  36 */     this.overlays.add(new SlotIdOverlay(macros, minecraft, this));
/*  37 */     this.overlays.add(new CustomGuiOverlay(macros, minecraft, this));
/*  38 */     this.overlays.add(new CraftingOverlay(macros, minecraft, this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends IOverlay> T getOverlay(Class<T> type) {
/*  44 */     for (IOverlay overlay : this.overlays) {
/*     */       
/*  46 */       if (type.equals(overlay.getClass()))
/*     */       {
/*  48 */         return (T)overlay;
/*     */       }
/*     */     } 
/*     */     
/*  52 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  57 */     for (IOverlay overlay : this.overlays)
/*     */     {
/*  59 */       overlay.onTick();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setScreenSize(bit resolution) {
/*  65 */     int width = resolution.a();
/*  66 */     int height = resolution.b();
/*  67 */     int scaleFactor = resolution.e();
/*     */     
/*  69 */     for (IOverlay overlay : this.overlays)
/*     */     {
/*  71 */       overlay.setScreenSize(width, height, scaleFactor);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawOverlays(int mouseX, int mouseY, long tick, float partialTick, boolean clock) {
/*  83 */     boolean resetTransform = false;
/*     */     
/*  85 */     this.mc.B.a("hud");
/*     */     
/*  87 */     GL.glDisableLighting();
/*  88 */     GL.glEnableAlphaTest();
/*  89 */     GL.glAlphaFunc(516, 0.1F);
/*     */     
/*  91 */     GL.glDisableDepthTest();
/*     */     
/*  93 */     for (IOverlay overlay : this.overlays) {
/*     */       
/*  95 */       boolean guiHidden = this.mc.t.av;
/*  96 */       if (!guiHidden || overlay.isAlwaysRendered()) {
/*     */         
/*  98 */         if (guiHidden && !resetTransform) {
/*     */           
/* 100 */           resetTransform = true;
/* 101 */           this.mc.o.j();
/* 102 */           GL.glDisableBlend();
/*     */         } 
/* 104 */         overlay.draw(mouseX, mouseY, tick, partialTick, clock);
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     this.mc.B.b();
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\overlay\OverlayHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */