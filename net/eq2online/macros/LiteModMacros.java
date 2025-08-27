/*     */ package net.eq2online.macros;
/*     */ import acl;
/*     */ import aed;
/*     */ import aip;
/*     */ import bib;
/*     */ import bit;
/*     */ import blk;
/*     */ import bse;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.realmsclient.dto.RealmsServer;
/*     */ import com.mumfrey.liteloader.ChatListener;
/*     */ import com.mumfrey.liteloader.Configurable;
/*     */ import com.mumfrey.liteloader.GameLoopListener;
/*     */ import com.mumfrey.liteloader.InitCompleteListener;
/*     */ import com.mumfrey.liteloader.JoinGameListener;
/*     */ import com.mumfrey.liteloader.OutboundChatFilter;
/*     */ import com.mumfrey.liteloader.PacketHandler;
/*     */ import com.mumfrey.liteloader.Permissible;
/*     */ import com.mumfrey.liteloader.PostRenderListener;
/*     */ import com.mumfrey.liteloader.RenderListener;
/*     */ import com.mumfrey.liteloader.Tickable;
/*     */ import com.mumfrey.liteloader.ViewportListener;
/*     */ import com.mumfrey.liteloader.core.LiteLoader;
/*     */ import com.mumfrey.liteloader.messaging.Message;
/*     */ import com.mumfrey.liteloader.messaging.MessageBus;
/*     */ import com.mumfrey.liteloader.messaging.Messenger;
/*     */ import com.mumfrey.liteloader.modconfig.ConfigPanel;
/*     */ import com.mumfrey.liteloader.permissions.PermissionsManager;
/*     */ import com.mumfrey.liteloader.permissions.PermissionsManagerClient;
/*     */ import hb;
/*     */ import hh;
/*     */ import ht;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import jh;
/*     */ import ks;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.handler.ChatHandler;
/*     */ import net.eq2online.macros.core.overlays.IPacketCollectItem;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroConfigPanel;
/*     */ import net.eq2online.macros.permissions.MacroModPermissions;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.variable.providers.VariableProviderIMC;
/*     */ import vg;
/*     */ import vp;
/*     */ 
/*     */ public class LiteModMacros implements Tickable, InitCompleteListener, ChatListener, RenderListener, GameLoopListener, JoinGameListener, Permissible, OutboundChatFilter, Configurable, ViewportListener, PacketHandler, Messenger, PostRenderListener {
/*     */   private MacroModCore core;
/*     */   
/*     */   public Class<? extends ConfigPanel> getConfigPanelClass() {
/*  52 */     return (Class)GuiMacroConfigPanel.class;
/*     */   }
/*     */   private VariableProviderIMC imcVariables; private ChatHandler chatHandler;
/*     */   private bib mc;
/*     */   
/*     */   public void init(File configPath) {
/*  58 */     this.core = new MacroModCore(bib.z());
/*  59 */     this.chatHandler = this.core.getChatHandler();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInitCompleted(bib minecraft, LiteLoader loader) {
/*  69 */     this.mc = minecraft;
/*  70 */     this.core.onInitCompleted();
/*  71 */     this.imcVariables = new VariableProviderIMC();
/*  72 */     for (ScriptContext context : ScriptContext.getAvailableContexts())
/*     */     {
/*  74 */       context.getCore().getScriptActionProvider().registerVariableProvider((IVariableProvider)this.imcVariables);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Class<? extends ht<?>>> getHandledPackets() {
/*  81 */     return (List<Class<? extends ht<?>>>)ImmutableList.of(ks.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handlePacket(hb netHandler, ht<?> packet) {
/*  87 */     onPickupItem(netHandler, (ks)packet);
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void upgradeSettings(String version, File configPath, File oldConfigPath) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onViewportResized(bit resolution, int displayWidth, int displayHeight) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFullScreenToggled(boolean fullScreen) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRender() {
/* 109 */     this.core.onRender();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRenderGui(blk currentScreen) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick(bib minecraft, float partialTicks, boolean inGame, boolean clock) {
/* 120 */     this.core.onTick(partialTicks, inGame, clock);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRunGameLoop(bib minecraft) {
/* 126 */     this.core.onTimerUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 132 */     return "Macro / Keybind Mod";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 138 */     return MacroModCore.getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onChat(hh chat, String message) {
/* 144 */     this.chatHandler.onChat(message);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSendChatMessage(String message) {
/* 150 */     return this.chatHandler.onSendChatMessage(message);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onJoinGame(hb netHandler, jh joinGamePacket, bse serverData, RealmsServer realmsServer) {
/* 156 */     this.core.onServerConnect(netHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSetupCameraTransform() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getMessageChannels() {
/* 167 */     return (List<String>)ImmutableList.of("macros:version", "macros:variable");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void receiveMessage(Message message) {
/* 176 */     if (message.isChannel("macros:version")) {
/*     */       
/* 178 */       String replyChannel = message.getReplyChannel();
/* 179 */       if (replyChannel != null)
/*     */       {
/* 181 */         MessageBus.send(replyChannel, getVersion());
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 186 */     if (message.isChannel("macros:variable"))
/*     */     {
/* 188 */       this.imcVariables.setVariable((String)message.get("name"), message.get("value"));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPostRender(float partialTicks) {
/* 195 */     this.core.onPostRender(partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPostRenderEntities(float partialTicks) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerPermissions(PermissionsManagerClient permissionsManager) {
/* 206 */     MacroModPermissions.init(this, permissionsManager);
/* 207 */     this.core.onPermissionsChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPermissionsCleared(PermissionsManager manager) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPermissionsChanged(PermissionsManager manager) {
/* 218 */     this.core.onPermissionsChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissibleModName() {
/* 224 */     return "macros";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPermissibleModVersion() {
/* 230 */     return Float.valueOf(1.54F).floatValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private void onPickupItem(hb netHandler, ks packet) {
/* 235 */     vg entity = getEntityByID(packet.a());
/* 236 */     vp collector = getEntityByID(packet.b());
/*     */     
/* 238 */     if (collector == this.mc.h && entity instanceof acl) {
/*     */       
/* 240 */       aip itemStack = ((acl)entity).k();
/* 241 */       int quantity = itemStack.E();
/*     */       
/* 243 */       if (packet instanceof IPacketCollectItem && ((IPacketCollectItem)packet).hasQuantity())
/*     */       {
/* 245 */         quantity = ((IPacketCollectItem)packet).getQuantity();
/*     */       }
/*     */       
/* 248 */       this.core.onItemPickup((aed)this.mc.h, itemStack, quantity);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <T extends vg> T getEntityByID(int entityId) {
/* 255 */     int playerId = this.mc.h.S();
/* 256 */     return (entityId != playerId) ? ((this.mc.f != null) ? (T)this.mc.f.a(entityId) : null) : (T)this.mc.h;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\LiteModMacros.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */