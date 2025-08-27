/*     */ package net.eq2online.macros.gui.overlay;
/*     */ 
/*     */ import bhz;
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import net.eq2online.macros.scripting.crafting.AutoCraftingManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CraftingOverlay
/*     */   extends Overlay
/*     */ {
/*     */   public CraftingOverlay(Macros macros, bib minecraft, OverlayHandler handler) {
/*  18 */     super(macros, minecraft, handler);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(int mouseX, int mouseY, long tick, float partialTick, boolean clock) {
/*  24 */     if (this.settings.showCraftingStatus) {
/*     */       
/*  26 */       this.mc.B.c("crafting");
/*  27 */       drawCraftingStatus(partialTick, clock);
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
/*     */   protected void drawCraftingStatus(float partialTick, boolean clock) {
/*  39 */     GL.glPushMatrix();
/*     */     
/*  41 */     AutoCraftingManager autocrafting = this.macros.getAutoCraftingManager();
/*  42 */     AutoCraftingManager.Job lastJob = autocrafting.getLastCompletedJob();
/*  43 */     if (lastJob != null && this.settings.showAutoCraftingFailedMessage && lastJob.isFailed())
/*     */     {
/*  45 */       renderFailedJob(lastJob);
/*     */     }
/*     */     
/*  48 */     AutoCraftingManager.Job activeJob = autocrafting.getActiveJob();
/*  49 */     if (activeJob != null)
/*     */     {
/*  51 */       renderCraftingJob(autocrafting, activeJob);
/*     */     }
/*     */     
/*  54 */     for (AutoCraftingManager.Job job : autocrafting.getJobs())
/*     */     {
/*  56 */       renderCraftingJob(autocrafting, job);
/*     */     }
/*     */     
/*  59 */     GL.glAlphaFunc(516, 0.1F);
/*  60 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderCraftingJob(AutoCraftingManager autocrafting, AutoCraftingManager.Job job) {
/*  65 */     boolean isCrafting = (job == autocrafting.getActiveJob());
/*     */     
/*  67 */     GL.glDisableBlend();
/*  68 */     GL.glAlphaFunc(516, 0.0F);
/*  69 */     GL.glEnableAlphaTest();
/*  70 */     a(2, 2, 180, isCrafting ? 34 : 24, -1342177280);
/*  71 */     bhz.c();
/*  72 */     drawItem(job.getRecipeOutput(), 6, 5);
/*  73 */     bhz.a();
/*  74 */     if (isCrafting)
/*     */     {
/*  76 */       drawHorizontalProgressBar(job.getProgress(), job.getTotal(), 6, 22, 170, 8);
/*     */     }
/*  78 */     AutoCraftingManager.Job.Status status = job.getStatus();
/*  79 */     if (status != null)
/*     */     {
/*  81 */       c(this.fontRenderer, I18n.get(status.getMessage()), 26, 10, -22016);
/*     */     }
/*  83 */     String progressString = job.getProgressString();
/*  84 */     int width = this.fontRenderer.a(progressString);
/*  85 */     c(this.fontRenderer, progressString, 175 - width, 10, -16711681);
/*  86 */     GL.glTranslatef(0.0F, isCrafting ? 34.0F : 24.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderFailedJob(AutoCraftingManager.Job job) {
/*  91 */     bhz.a();
/*     */     
/*  93 */     a(2, 2, 180, 34, -1342177280);
/*  94 */     GL.glDisableBlend();
/*  95 */     GL.glEnableAlphaTest();
/*  96 */     this.mc.N().a(ResourceLocations.MAIN);
/*  97 */     GL.glAlphaFunc(516, 0.17F);
/*  98 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  99 */     drawTexturedModalRect(7, 6, 22, 21, 184, 0, 208, 24);
/* 100 */     drawHorizontalProgressBar(0.0F, 100.0F, 6, 22, 170, 8);
/* 101 */     c(this.fontRenderer, I18n.get(job.getFailure().getMessage()), 26, 10, -22016);
/* 102 */     GL.glTranslatef(0.0F, 34.0F, 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\overlay\CraftingOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */