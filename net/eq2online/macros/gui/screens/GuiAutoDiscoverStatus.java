/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bib;
/*     */ import bja;
/*     */ import blk;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.macros.AutoDiscoveryHandler;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiAutoDiscoverStatus
/*     */   extends GuiScreenEx
/*     */ {
/*     */   private final Macros macros;
/*     */   private final AutoDiscoveryHandler discoveryHandler;
/*  35 */   private int throbberIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean failed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiAutoDiscoverStatus(Macros macros, bib minecraft, AutoDiscoveryHandler discoveryHandler) {
/*  50 */     super(minecraft);
/*  51 */     this.macros = macros;
/*  52 */     this.discoveryHandler = discoveryHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyFailed() {
/*  60 */     this.failed = true;
/*  61 */     b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/*  67 */     clearControlList();
/*     */     
/*  69 */     int xMid = this.l / 2;
/*  70 */     int yMid = this.m / 2;
/*  71 */     if (this.failed) {
/*     */       
/*  73 */       addControl(new GuiControl(1, xMid - 40, yMid, 80, 20, "Close"));
/*  74 */       addControl(new GuiControl(2, xMid - 92, yMid + 30, 100, 20, I18n.get("gui.options")));
/*  75 */       addControl(new GuiControl(3, xMid + 10, yMid + 30, 80, 20, I18n.get("gui.refresh")));
/*     */     }
/*     */     else {
/*     */       
/*  79 */       addControl(new GuiControl(1, xMid - 40, yMid, 80, 20, I18n.get("gui.cancel")));
/*     */     } 
/*     */     
/*  82 */     super.b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void m() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  93 */     this.throbberIndex++;
/*  94 */     if (this.throbberIndex >= 27)
/*     */     {
/*  96 */       this.throbberIndex = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/* 103 */     if (keyCode == 1)
/*     */     {
/* 105 */       if (this.failed) {
/*     */         
/* 107 */         this.discoveryHandler.close();
/*     */       }
/*     */       else {
/*     */         
/* 111 */         this.discoveryHandler.cancel();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bja guibutton) {
/* 119 */     if (guibutton != null) {
/*     */       
/* 121 */       if (guibutton.k == 1)
/*     */       {
/* 123 */         if (this.failed) {
/*     */           
/* 125 */           this.discoveryHandler.close();
/*     */         }
/*     */         else {
/*     */           
/* 129 */           this.discoveryHandler.cancel();
/*     */         } 
/*     */       }
/*     */       
/* 133 */       if (guibutton.k == 2)
/*     */       {
/* 135 */         this.j.a((blk)new GuiMacroConfig(this.macros, this.j, this, true));
/*     */       }
/*     */       
/* 138 */       if (guibutton.k == 3)
/*     */       {
/* 140 */         this.discoveryHandler.retry();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTicks) {
/* 149 */     a(0, 0, this.l, this.m, -2147483648);
/* 150 */     GL.glDisableBlend();
/*     */ 
/*     */     
/* 153 */     int t = this.throbberIndex / 3;
/*     */     
/* 155 */     if (this.failed) {
/*     */       
/* 157 */       a(this.q, I18n.get("query.failed"), this.l / 2, this.m / 2 - 20, -1);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 162 */       this.renderer.drawTexturedModalRect(ResourceLocations.MAIN, this.l / 2 - 25, this.m / 2 - 30, this.l / 2 + 26, this.m / 2 - 25, 0, t * 10, 102, t * 10 + 10);
/*     */ 
/*     */       
/* 165 */       String pleaseWait = I18n.get("query.wait");
/* 166 */       int stringWidth = this.q.a(pleaseWait);
/* 167 */       c(this.q, pleaseWait, this.l / 2 - stringWidth / 2, this.m / 2 - 20, -1);
/*     */     } 
/*     */ 
/*     */     
/* 171 */     super.a(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiAutoDiscoverStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */