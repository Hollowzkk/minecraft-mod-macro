/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import aed;
/*     */ import aip;
/*     */ import bib;
/*     */ import bit;
/*     */ import blk;
/*     */ import cen;
/*     */ import ceq;
/*     */ import com.mumfrey.liteloader.core.LiteLoader;
/*     */ import hb;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.AutoDiscoveryHandler;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.handler.BetaExpiryCheckHandler;
/*     */ import net.eq2online.macros.core.handler.ChatHandler;
/*     */ import net.eq2online.macros.core.handler.LocalisationHandler;
/*     */ import net.eq2online.macros.core.handler.ScreenTransformHandler;
/*     */ import net.eq2online.macros.core.handler.ServerSwitchHandler;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxBetaExpired;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxErrorList;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxFirstRunPrompt;
/*     */ import net.eq2online.macros.gui.overlay.OverlayHandler;
/*     */ import net.eq2online.macros.gui.screens.GuiPermissions;
/*     */ import net.eq2online.macros.gui.skins.UserSkinHandler;
/*     */ import net.eq2online.macros.gui.thumbnail.ThumbnailHandler;
/*     */ import net.eq2online.macros.input.IInputHandlerModule;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.input.InputHandlerModuleJInput;
/*     */ import net.eq2online.macros.interfaces.ILocalisationProvider;
/*     */ import net.eq2online.macros.rendering.FontRendererLegacy;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import org.lwjgl.input.Mouse;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MacroModCore
/*     */ {
/*     */   public static final int VERSION = 1540;
/*     */   private static final String VERSION_BASE = "0.15";
/*     */   private static final String VERSION_MINOR = "4";
/*     */   private static final String VERSION_SUFFIX = "";
/*     */   private static final String VERSION_TEXT = "0.15.4";
/*     */   private final bib mc;
/*     */   private final Macros macros;
/*     */   private final InputHandler inputHandler;
/*     */   private final ServerSwitchHandler switchHandler;
/*     */   private final ThumbnailHandler thumbnailHandler;
/*     */   private final ChatHandler chatHandler;
/*     */   private final AutoDiscoveryHandler autoDiscoveryHandler;
/*     */   private final LocalisationHandler localisationHandler;
/*     */   private OverlayHandler overlayHandler;
/*     */   private UserSkinHandler userSkinHandler;
/*     */   private ScreenTransformHandler screenTransformHandler;
/*     */   private boolean firstRun = false;
/*  97 */   private final List<String> startupErrors = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private boolean displayedStartupErrors = false;
/*     */ 
/*     */   
/*     */   private boolean inputInitDone = false;
/*     */ 
/*     */   
/* 106 */   private long globalTickCounter = 0L;
/*     */   
/*     */   private int displayWidth;
/*     */   
/*     */   private int displayHeight;
/*     */   
/*     */   private int guiScale;
/*     */   
/*     */   private boolean displayBetaMessage;
/* 115 */   private final BetaExpiryCheckHandler expiryCheck = null;
/*     */ 
/*     */   
/*     */   private boolean displayedExpiryWarning = false;
/*     */ 
/*     */   
/*     */   private bit resolution;
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroModCore(bib minecraft) {
/* 126 */     if (this.expiryCheck != null)
/*     */     {
/* 128 */       this.expiryCheck.start();
/*     */     }
/*     */     
/* 131 */     this.mc = minecraft;
/* 132 */     this.localisationHandler = new LocalisationHandler(this.mc);
/* 133 */     this.localisationHandler.initialiseLocalisationTable("en_gb");
/* 134 */     I18n.setProvider((ILocalisationProvider)this.localisationHandler);
/* 135 */     ((cen)this.mc.O()).a((ceq)this.localisationHandler);
/*     */     
/* 137 */     this.chatHandler = new ChatHandler(this.mc);
/*     */ 
/*     */     
/* 140 */     this.macros = new Macros(this.mc, this);
/* 141 */     this.macros.prepare();
/*     */     
/* 143 */     this.inputHandler = new InputHandler(this, this.macros, this.mc);
/* 144 */     this.autoDiscoveryHandler = new AutoDiscoveryHandler(this.macros, this.mc);
/* 145 */     this.switchHandler = new ServerSwitchHandler(this.macros, this.mc, this);
/* 146 */     this.thumbnailHandler = new ThumbnailHandler(this.macros, this.mc);
/* 147 */     this.screenTransformHandler = new ScreenTransformHandler(this.macros, this.mc);
/*     */     
/* 149 */     this.resolution = new bit(this.mc);
/*     */     
/* 151 */     this.displayBetaMessage = (getVersion().contains("beta") || getVersion().contains("build"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onInitCompleted() {
/* 156 */     FontRendererLegacy.createInstance(this.mc.t, ResourceLocations.DEFAULTFONT, this.mc.N());
/*     */     
/* 158 */     this.inputHandler.init(LiteLoader.getInput());
/* 159 */     this.thumbnailHandler.init();
/* 160 */     this.macros.init(this.inputHandler);
/*     */ 
/*     */     
/* 163 */     this.userSkinHandler = new UserSkinHandler(this.mc, ResourceLocations.PLAYERS);
/*     */ 
/*     */     
/* 166 */     this.overlayHandler = new OverlayHandler(this.macros, this.mc);
/*     */ 
/*     */     
/* 169 */     this.macros.postInit();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initInputHandler() {
/* 174 */     this.inputInitDone = true;
/*     */ 
/*     */     
/* 177 */     if (!(this.macros.getSettings()).compatibilityMode)
/*     */     {
/* 179 */       this.inputHandler.addModule((IInputHandlerModule)new InputHandlerModuleJInput(this.macros));
/*     */     }
/*     */ 
/*     */     
/* 183 */     this.inputHandler.register();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 193 */     return "0.15.4";
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalisationHandler getLocalisationHandler() {
/* 198 */     return this.localisationHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public OverlayHandler getOverlayHandler() {
/* 203 */     return this.overlayHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public UserSkinHandler getUserSkinHandler() {
/* 208 */     return this.userSkinHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSwitchHandler getServerSwitchHandler() {
/* 213 */     return this.switchHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChatHandler getChatHandler() {
/* 218 */     return this.chatHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public ThumbnailHandler getThumbnailHandler() {
/* 223 */     return this.thumbnailHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public AutoDiscoveryHandler getAutoDiscoveryHandler() {
/* 228 */     return this.autoDiscoveryHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRender() {
/* 233 */     this.screenTransformHandler.onRender();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTimerUpdate() {
/* 238 */     this.inputHandler.onTimerUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick(float partialTick, boolean inGame, boolean clock) {
/* 243 */     if (inGame)
/*     */     {
/* 245 */       onTickInGame(partialTick, clock);
/*     */     }
/*     */     
/* 248 */     if (this.mc.m != null)
/*     */     {
/* 250 */       onTickInGUI(partialTick, clock);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void onTickInGame(float partialTick, boolean clock) {
/* 257 */     updateUserSkins();
/*     */     
/* 259 */     updateResolution();
/* 260 */     renderOverlay(partialTick, clock);
/*     */     
/* 262 */     this.inputHandler.processBuffers(true, false);
/*     */ 
/*     */     
/* 265 */     this.macros.onTick(clock);
/*     */     
/* 267 */     if (clock)
/*     */     {
/* 269 */       update();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void onTickInGUI(float partialTick, boolean clock) {
/* 275 */     this.thumbnailHandler.cancelCaptureThumbnail();
/*     */     
/* 277 */     updateUserSkins();
/*     */     
/* 279 */     if (!this.inputInitDone)
/*     */     {
/* 281 */       initInputHandler();
/*     */     }
/*     */     
/* 284 */     if (this.mc.m instanceof bky || this.mc.m instanceof bnf)
/*     */     {
/* 286 */       onDisconnected();
/*     */     }
/*     */     
/* 289 */     if (this.mc.m instanceof blr)
/*     */     {
/* 291 */       onTickInMainMenu();
/*     */     }
/*     */     
/* 294 */     updateResolution();
/*     */     
/* 296 */     if (this.mc.f == null)
/*     */     {
/* 298 */       this.inputHandler.processBuffers(false, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void onTickInMainMenu() {
/* 304 */     if (this.firstRun) {
/*     */       
/* 306 */       this.mc.a((blk)new GuiDialogBoxFirstRunPrompt(this.mc, this.mc.m, this.screenTransformHandler));
/* 307 */       this.firstRun = false;
/*     */     }
/* 309 */     else if (this.expiryCheck != null && this.expiryCheck.isExpired() && !this.displayedExpiryWarning) {
/*     */       
/* 311 */       this.displayedExpiryWarning = true;
/* 312 */       this.mc.a((blk)new GuiDialogBoxBetaExpired(this.mc, this.mc.m, "0.15"));
/*     */     } 
/*     */     
/* 315 */     if (!this.displayedStartupErrors && this.startupErrors.size() > 0) {
/*     */       
/* 317 */       this.displayedStartupErrors = true;
/* 318 */       this.mc.a((blk)new GuiDialogBoxErrorList(this.mc, this.mc.m, this.startupErrors, I18n.get("startuperrors.line1"), 
/* 319 */             I18n.get("startuperrors.title")));
/* 320 */       this.startupErrors.clear();
/*     */     } 
/*     */     
/* 323 */     onDisconnected();
/*     */     
/* 325 */     if (this.displayBetaMessage) {
/*     */       
/* 327 */       int top = this.mc.m.getClass().getSimpleName().contains("Voxel") ? 14 : 2;
/* 328 */       this.mc.k.a("Macros version " + getVersion(), 2.0F, top, -43691);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void update() {
/* 334 */     this.globalTickCounter++;
/*     */     
/* 336 */     if (this.globalTickCounter % 20L == 0L)
/*     */     {
/* 338 */       this.inputHandler.update();
/*     */     }
/*     */     
/* 341 */     this.localisationHandler.onTick();
/*     */ 
/*     */     
/* 344 */     this.switchHandler.handleAutoSwitch();
/*     */     
/* 346 */     this.inputHandler.onTick();
/*     */     
/* 348 */     if (this.overlayHandler != null)
/*     */     {
/* 350 */       this.overlayHandler.onTick();
/*     */     }
/*     */     
/* 353 */     this.autoDiscoveryHandler.onTick();
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateUserSkins() {
/* 358 */     if (this.userSkinHandler != null) {
/*     */       
/* 360 */       this.mc.B.a("skinmanager");
/* 361 */       this.userSkinHandler.onTick();
/* 362 */       this.mc.B.b();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateResolution() {
/* 368 */     if (this.displayWidth == this.mc.d && this.displayHeight == this.mc.e && this.guiScale == this.mc.t.aG) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 375 */     this.resolution = new bit(this.mc);
/* 376 */     this.screenTransformHandler.reset();
/* 377 */     this.displayWidth = this.mc.d;
/* 378 */     this.displayHeight = this.mc.e;
/* 379 */     this.guiScale = this.mc.t.aG;
/* 380 */     if (this.overlayHandler != null)
/*     */     {
/* 382 */       this.overlayHandler.setScreenSize(this.resolution);
/*     */     }
/* 384 */     this.inputHandler.update();
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderOverlay(float partialTick, boolean clock) {
/* 389 */     if (this.overlayHandler == null || this.mc.f == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 394 */     this.mc.B.a("overlay");
/* 395 */     int mouseX = Mouse.getX() * this.resolution.a() / this.mc.d;
/* 396 */     int mouseY = this.resolution.b() - Mouse.getY() * this.resolution.b() / this.mc.e - 1;
/* 397 */     this.overlayHandler.drawOverlays(mouseX, mouseY, this.mc.f.R(), partialTick, clock);
/* 398 */     this.mc.B.b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void onDisconnected() {
/* 406 */     this.switchHandler.onDisconnected();
/* 407 */     this.macros.onDisconnected();
/* 408 */     this.localisationHandler.onDisconnected();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onJoinGame(String serverName, int serverPort) {
/* 419 */     Log.info("onConnectToServer: {0}:{1}", new Object[] { serverName, Integer.valueOf(serverPort) });
/* 420 */     this.macros.onJoinGame(serverName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onItemPickup(aed entityPlayer, aip itemStack, int quantity) {
/* 431 */     if (entityPlayer == this.mc.h)
/*     */     {
/* 433 */       this.macros.getBuiltinEventDispatcher().onItemPickup(itemStack, quantity);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onServerConnect(hb netclienthandler) {
/* 444 */     this.macros.onServerConnect(netclienthandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPostRender(float partialTicks) {
/* 449 */     this.inputHandler.performDeepInjection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPermissionsChanged() {
/* 457 */     this.macros.refreshPermissions();
/*     */     
/* 459 */     if (this.mc.m != null && this.mc.m instanceof GuiPermissions)
/*     */     {
/* 461 */       ((GuiPermissions)this.mc.m).refreshPermissions();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logStartupError(String error) {
/* 472 */     if (!this.displayedStartupErrors)
/*     */     {
/* 474 */       this.startupErrors.add(error);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFirstRun() {
/* 480 */     this.firstRun = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\MacroModCore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */