/*    */ package net.eq2online.macros.gui.overlay;
/*    */ 
/*    */ import bib;
/*    */ import buk;
/*    */ import bve;
/*    */ import com.mumfrey.liteloader.gl.GL;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.gui.thumbnail.ThumbnailHandler;
/*    */ 
/*    */ 
/*    */ public class ThumbnailOverlay
/*    */   extends Overlay
/*    */ {
/*    */   private final ThumbnailHandler thumbnailHandler;
/*    */   
/*    */   public ThumbnailOverlay(Macros macros, bib minecraft, OverlayHandler handler) {
/* 18 */     super(macros, minecraft, handler);
/*    */     
/* 20 */     this.thumbnailHandler = macros.getThumbnailHandler();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAlwaysRendered() {
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw(int mouseX, int mouseY, long tick, float partialTick, boolean clock) {
/* 33 */     if (this.mc.m == null && this.thumbnailHandler.isCapturingThumbnail()) {
/*    */       
/* 35 */       this.mc.t.av = true;
/* 36 */       drawThumbnailCaptureFrame();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawThumbnailCaptureFrame() {
/* 49 */     int frameSize = (int)Math.min(0.75F * this.width, 0.75F * this.height);
/*    */     
/* 51 */     int xOffset = (this.width - frameSize) / 2;
/* 52 */     int yOffset = (this.height - frameSize) / 2;
/*    */ 
/*    */     
/* 55 */     a(0, 0, this.width, yOffset, -1342177280);
/* 56 */     a(0, this.height - yOffset, this.width, this.height, -1342177280);
/* 57 */     a(0, yOffset, xOffset, this.height - yOffset, -1342177280);
/* 58 */     a(this.width - xOffset, yOffset, this.width, this.height - yOffset, -1342177280);
/*    */ 
/*    */     
/* 61 */     GL.glLineWidth(3.0F);
/* 62 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 63 */     GL.glBlendFunc(770, 771);
/* 64 */     GL.glDisableBlend();
/* 65 */     GL.glDisableAlphaTest();
/* 66 */     GL.glDisableTexture2D();
/* 67 */     GL.glDisableDepthTest();
/* 68 */     GL.glDisableLighting();
/* 69 */     GL.glDepthMask(false);
/*    */ 
/*    */     
/* 72 */     bve tessellator = bve.a();
/* 73 */     buk buf = tessellator.c();
/* 74 */     buf.a(2, GL.VF_POSITION);
/* 75 */     buf.b(xOffset, yOffset, 0.0D).d();
/* 76 */     buf.b(xOffset, (this.height - yOffset), 0.0D).d();
/* 77 */     buf.b((this.width - xOffset), (this.height - yOffset), 0.0D).d();
/* 78 */     buf.b((this.width - xOffset), yOffset, 0.0D).d();
/* 79 */     tessellator.b();
/*    */     
/* 81 */     GL.glEnableTexture2D();
/*    */ 
/*    */     
/* 84 */     a(this.fontRenderer, I18n.get("thumbnail.help.hint1"), this.width / 2, yOffset - 22, 16777215);
/* 85 */     a(this.fontRenderer, I18n.get("thumbnail.help.hint2"), this.width / 2, yOffset - 12, 16777215);
/*    */     
/* 87 */     GL.glDepthMask(true);
/* 88 */     GL.glEnableLighting();
/* 89 */     GL.glEnableDepthTest();
/* 90 */     GL.glEnableAlphaTest();
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\overlay\ThumbnailOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */