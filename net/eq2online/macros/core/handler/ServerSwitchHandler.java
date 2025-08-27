/*     */ package net.eq2online.macros.core.handler;
/*     */ 
/*     */ import bib;
/*     */ import brz;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.overlay.ConfigOverlay;
/*     */ import net.eq2online.util.Game;
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
/*     */ public class ServerSwitchHandler
/*     */ {
/*     */   private final bib mc;
/*     */   private final MacroModCore core;
/*     */   private final Macros macros;
/*     */   private boolean haveAutoSwitched;
/*     */   private String lastServerName;
/*     */   
/*     */   public ServerSwitchHandler(Macros macros, bib minecraft, MacroModCore core) {
/*  32 */     this.haveAutoSwitched = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  37 */     this.lastServerName = "";
/*     */     this.mc = minecraft;
/*     */     this.core = core;
/*     */     this.macros = macros; } public String getLastServerName() {
/*  41 */     return this.lastServerName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleAutoSwitch() {
/*  46 */     if (this.haveAutoSwitched) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  51 */     if (this.mc.E()) {
/*     */       
/*  53 */       this.haveAutoSwitched = true;
/*     */       
/*  55 */       if ((this.macros.getSettings()).enableAutoConfigSwitch) {
/*     */         
/*  57 */         this.macros.setActiveConfig(this.macros.getSinglePlayerConfigName());
/*  58 */         Game.addChatMessage(I18n.get("message.autosp", new Object[] { this.macros.getActiveConfigName() }));
/*     */       } 
/*     */       
/*  61 */       this.macros.getBuiltinEventDispatcher().initSinglePlayer();
/*  62 */       this.lastServerName = "SP";
/*  63 */       this.macros.onJoinGame("SP");
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*     */     try {
/*  70 */       brz sendQueue = this.mc.h.d;
/*  71 */       SocketAddress socketAddress = sendQueue.a().b();
/*     */       
/*  73 */       if (socketAddress instanceof InetSocketAddress) {
/*     */         
/*  75 */         InetSocketAddress inetAddr = (InetSocketAddress)socketAddress;
/*     */         
/*  77 */         String serverName = inetAddr.getHostName();
/*  78 */         int serverPort = inetAddr.getPort();
/*     */         
/*  80 */         this.haveAutoSwitched = true;
/*     */         
/*  82 */         if (serverName != null && !serverName.equalsIgnoreCase(this.lastServerName))
/*     */         {
/*  84 */           onConnectToServer(serverName, serverPort);
/*  85 */           this.lastServerName = serverName;
/*     */         }
/*     */       
/*     */       } 
/*  89 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onConnectToServer(String serverName, int serverPort) {
/* 100 */     if ((this.macros.getSettings()).enableAutoConfigSwitch) {
/*     */       
/* 102 */       String serverNameAndPort = String.format("%s:%s", new Object[] { serverName, Integer.valueOf(serverPort) });
/*     */       
/* 104 */       if (this.macros.hasConfig(serverNameAndPort)) {
/*     */         
/* 106 */         this.macros.setActiveConfig(serverNameAndPort);
/* 107 */         ((ConfigOverlay)this.core.getOverlayHandler().getOverlay(ConfigOverlay.class)).setNextConfigTimer(160L);
/*     */       }
/* 109 */       else if (this.macros.hasConfig(serverName)) {
/*     */         
/* 111 */         this.macros.setActiveConfig(serverName);
/* 112 */         ((ConfigOverlay)this.core.getOverlayHandler().getOverlay(ConfigOverlay.class)).setNextConfigTimer(160L);
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     this.core.onJoinGame(serverName, serverPort);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisconnected() {
/* 124 */     this.haveAutoSwitched = false;
/* 125 */     this.lastServerName = "";
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\handler\ServerSwitchHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */