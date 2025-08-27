/*     */ package net.eq2online.macros.gui.thumbnail;
/*     */ 
/*     */ import bib;
/*     */ import blk;
/*     */ import cer;
/*     */ import com.mumfrey.liteloader.common.Resources;
/*     */ import com.mumfrey.liteloader.core.LiteLoader;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.screens.GuiEditThumbnails;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import nf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThumbnailHandler
/*     */ {
/*     */   private final Macros macros;
/*     */   private final bib mc;
/*     */   private final MacroThumbnailResourcePack thumbnailResourceProvider;
/*  27 */   private final Map<nf, Thumbnailer> thumbnailHandlers = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean initDone;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean oldHideGUI;
/*     */ 
/*     */ 
/*     */   
/*     */   private Thumbnailer activeThumbnailHandler;
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiEditThumbnails activeThumbnailGui;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThumbnailHandler(Macros macros, bib minecraft) {
/*  50 */     this.macros = macros;
/*  51 */     this.mc = minecraft;
/*  52 */     this.thumbnailResourceProvider = new MacroThumbnailResourcePack(this.macros.getMacrosDirectory());
/*     */     
/*  54 */     Resources<?, cer> resources = LiteLoader.getGameEngine().getResources();
/*  55 */     resources.registerResourcePack(this.thumbnailResourceProvider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  66 */     if (this.initDone) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  71 */     this.initDone = true;
/*  72 */     ResourceLocations.DYNAMIC_TOWNS = createThumbnailHandler(ResourceLocations.TOWNS);
/*  73 */     ResourceLocations.DYNAMIC_HOMES = createThumbnailHandler(ResourceLocations.HOMES);
/*  74 */     ResourceLocations.DYNAMIC_FRIENDS = createThumbnailHandler(ResourceLocations.FRIENDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected nf createThumbnailHandler(nf imageResource) {
/*     */     try {
/*  86 */       this.thumbnailResourceProvider.addFileFor(imageResource);
/*  87 */       String dynamicResourceName = "icon_" + imageResource.a().substring(imageResource.a().lastIndexOf('/') + 1, imageResource
/*  88 */           .a().lastIndexOf('.'));
/*  89 */       Thumbnailer thumbnailManager = new Thumbnailer(this.macros, this.mc, dynamicResourceName, imageResource, 16);
/*  90 */       this.thumbnailHandlers.put(imageResource, thumbnailManager);
/*  91 */       return thumbnailManager.getDynamicResource();
/*     */     }
/*  93 */     catch (Exception exception) {
/*     */       
/*  95 */       return imageResource;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Thumbnailer getThumbnailHandler(nf imageResource) {
/* 106 */     return this.thumbnailHandlers.get(imageResource);
/*     */   }
/*     */ 
/*     */   
/*     */   public Thumbnailer getActiveThumbnailHandler() {
/* 111 */     return this.activeThumbnailHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCapturingThumbnail() {
/* 116 */     return (this.activeThumbnailHandler != null);
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
/*     */   public void beginThumbnailCapture(GuiEditThumbnails gui, nf imageResource, int iconIndex) {
/* 128 */     if (this.thumbnailHandlers.containsKey(imageResource)) {
/*     */       
/* 130 */       this.oldHideGUI = this.mc.t.av;
/* 131 */       this.activeThumbnailHandler = this.thumbnailHandlers.get(imageResource);
/* 132 */       this.activeThumbnailHandler.prepareCapture(iconIndex);
/* 133 */       this.activeThumbnailGui = gui;
/*     */     } 
/*     */     
/* 136 */     this.mc.a(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void captureThumbnail() {
/* 144 */     if (this.activeThumbnailHandler != null) {
/*     */ 
/*     */       
/* 147 */       this.activeThumbnailHandler.captureNow(this.mc.d, this.mc.e);
/* 148 */       this.activeThumbnailHandler = null;
/*     */ 
/*     */       
/* 151 */       this.mc.a((blk)this.activeThumbnailGui);
/* 152 */       this.activeThumbnailGui = null;
/*     */ 
/*     */       
/* 155 */       this.mc.t.av = this.oldHideGUI;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancelCaptureThumbnail() {
/* 161 */     if (this.activeThumbnailHandler != null)
/*     */     {
/* 163 */       cancelCaptureThumbnail(false);
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
/*     */   public void cancelCaptureThumbnail(boolean showOldGui) {
/* 175 */     if (this.activeThumbnailHandler != null) {
/*     */ 
/*     */       
/* 178 */       this.activeThumbnailHandler.prepareCapture(-1);
/* 179 */       this.activeThumbnailHandler = null;
/*     */ 
/*     */       
/* 182 */       if (showOldGui) this.mc.a((blk)this.activeThumbnailGui); 
/* 183 */       this.activeThumbnailGui = null;
/*     */ 
/*     */       
/* 186 */       this.mc.t.av = this.oldHideGUI;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\thumbnail\ThumbnailHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */