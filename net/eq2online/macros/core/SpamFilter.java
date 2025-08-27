/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import bib;
/*     */ import bj;
/*     */ import bn;
/*     */ import bud;
/*     */ import ht;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Deque;
/*     */ import java.util.LinkedList;
/*     */ import la;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.interfaces.ISettingsObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
/*     */ import net.eq2online.macros.interfaces.annotations.DropdownLocalisationRoot;
/*     */ import net.eq2online.macros.interfaces.annotations.DropdownStyle;
/*     */ import net.eq2online.macros.permissions.MacroModPermissions;
/*     */ import net.eq2online.macros.scripting.api.IMessageFilter;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.util.Game;
/*     */ import rp;
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
/*     */ public final class SpamFilter
/*     */ {
/*     */   private static final String SPAM_NOLIMIT_PERMISSION = "spam.nolimit";
/*     */   private static final int MAX_PACKETS_LIMITED = 20;
/*     */   private static final int MAX_PACKETS_NORMAL = 250;
/*     */   
/*     */   @DropdownLocalisationRoot("spamfilter.style")
/*     */   public enum FilterStyle
/*     */   {
/*  44 */     NONE,
/*     */ 
/*     */ 
/*     */     
/*  48 */     QUEUE,
/*     */ 
/*     */     
/*  51 */     DISCARD,
/*     */     
/*  53 */     LOG;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Settings
/*     */     implements ISettingsObserver
/*     */   {
/*  62 */     protected int spamMessageLimit = 180;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     protected int spamMessageTicks = 20;
/*     */     
/*  69 */     protected int maxPacketsPerSecond = 250;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onClearSettings() {
/*  77 */       this.spamMessageLimit = 180;
/*  78 */       this.spamMessageTicks = 20;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onLoadSettings(ISettingsStore settings) {
/*  89 */       this.spamMessageLimit = settings.getSetting("floodprotection.behaviour.tickslimit", 180);
/*  90 */       this.spamMessageTicks = settings.getSetting("floodprotection.behaviour.ticksmessage", 20);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onSaveSettings(ISettingsStore settings) {
/* 101 */       settings.setSetting("floodprotection.behaviour.tickslimit", this.spamMessageLimit);
/* 102 */       settings.setSetting("floodprotection.behaviour.ticksmessage", this.spamMessageTicks);
/*     */       
/* 104 */       settings.setSettingComment("floodprotection.behaviour.tickslimit", "Flood protection settings");
/*     */     }
/*     */   }
/*     */   
/* 108 */   public static final Settings SETTINGS = new Settings();
/*     */   
/*     */   private final Macros macros;
/*     */   
/*     */   private final bib mc;
/*     */   
/* 114 */   private long packetCapTime = 0L;
/*     */   
/* 116 */   private int packetCapLevel = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   private int spamLevel = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   private Deque<String> queuedMessages = new LinkedList<>();
/*     */ 
/*     */   
/*     */   private volatile int sending;
/*     */ 
/*     */   
/*     */   private bj forgeClientCommandHandler;
/*     */ 
/*     */ 
/*     */   
/*     */   public SpamFilter(Macros macros, bib minecraft) {
/* 137 */     this.macros = macros;
/* 138 */     this.mc = minecraft;
/*     */ 
/*     */     
/*     */     try {
/* 142 */       Class<?> clientCommandHandler = Class.forName("net.minecraftforge.client.ClientCommandHandler");
/* 143 */       if (clientCommandHandler != null)
/*     */       {
/* 145 */         Field fInstance = clientCommandHandler.getDeclaredField("instance");
/* 146 */         this.forgeClientCommandHandler = (bj)fInstance.get(null);
/* 147 */         Log.info("Forge ClientCommandHandler was found, outbound commands will be processed by Forge");
/*     */       }
/*     */     
/* 150 */     } catch (Throwable th) {
/*     */       
/* 152 */       Log.info("Forge ClientCommandHandler was not found, outbound commands will not be processed by Forge");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 158 */     MacroModPermissions.registerPermission("spam.nolimit");
/*     */     
/* 160 */     this.packetCapTime = 0L;
/* 161 */     this.packetCapLevel = 0;
/* 162 */     this.spamLevel = 0;
/* 163 */     this.queuedMessages.clear();
/* 164 */     this.sending = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 173 */     if (this.spamLevel > 0) this.spamLevel--;
/*     */     
/* 175 */     long systemTime = bib.I();
/* 176 */     if (systemTime - this.packetCapTime > 1000L) {
/*     */       
/* 178 */       this.packetCapTime = systemTime;
/* 179 */       this.packetCapLevel = 0;
/*     */     } 
/*     */     
/* 182 */     while (this.spamLevel < SETTINGS.spamMessageLimit - SETTINGS.spamMessageTicks) {
/*     */       
/* 184 */       if (!processQueue()) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean processQueue() {
/* 197 */     if (this.queuedMessages.size() > 0 && (this.macros.getSettings()).spamFilterStyle != FilterStyle.QUEUE) {
/*     */       
/* 199 */       this.queuedMessages.clear();
/* 200 */       return false;
/*     */     } 
/*     */     
/* 203 */     String chatMessage = this.queuedMessages.poll();
/*     */     
/* 205 */     if (chatMessage != null) {
/*     */       
/* 207 */       dispatchMessage(chatMessage);
/* 208 */       return true;
/*     */     } 
/*     */     
/* 211 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearQueue() {
/* 220 */     this.queuedMessages.clear();
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
/*     */   public void sendChatMessage(String chatMessage, ScriptContext context) {
/* 232 */     if (chatMessage == null || chatMessage.length() < 1)
/*     */       return; 
/* 234 */     IMessageFilter messageFilter = context.getMessageFilter();
/* 235 */     if (messageFilter != null)
/*     */     {
/* 237 */       if (!messageFilter.enqueueMessage(chatMessage))
/*     */         return; 
/*     */     }
/* 240 */     if (this.forgeClientCommandHandler != null)
/*     */     {
/* 242 */       if (this.forgeClientCommandHandler.a((bn)this.mc.h, chatMessage) == 1) {
/*     */         return;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 248 */     if (this.spamLevel < SETTINGS.spamMessageLimit - SETTINGS.spamMessageTicks || 
/* 249 */       !(this.macros.getSettings()).spamFilterEnabled || 
/* 250 */       (this.macros.getSettings()).spamFilterStyle == FilterStyle.NONE) {
/*     */       
/* 252 */       dispatchMessage(chatMessage);
/*     */     }
/* 254 */     else if ((this.macros.getSettings()).spamFilterStyle == FilterStyle.QUEUE && this.queuedMessages
/* 255 */       .size() < (this.macros.getSettings()).spamFilterQueueSize) {
/*     */       
/* 257 */       if ((this.macros.getSettings()).spamFilterIgnoreCommands && chatMessage.startsWith("/")) {
/*     */         
/* 259 */         dispatchMessage(chatMessage);
/*     */         
/*     */         return;
/*     */       } 
/* 263 */       this.queuedMessages.offer(chatMessage);
/*     */     }
/* 265 */     else if ((this.macros.getSettings()).spamFilterStyle == FilterStyle.LOG) {
/*     */       
/* 267 */       Game.addChatMessage("§c[Flood protection] " + rp.a(chatMessage));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dispatchMessage(String chatMessage) {
/* 278 */     bud thePlayer = this.mc.h;
/* 279 */     if (thePlayer == null)
/*     */       return; 
/* 281 */     this.spamLevel += SETTINGS.spamMessageTicks;
/*     */     
/* 283 */     SETTINGS
/* 284 */       .maxPacketsPerSecond = MacroModPermissions.hasPermission("spam.nolimit") ? 250 : 20;
/* 285 */     if (this.packetCapLevel < SETTINGS.maxPacketsPerSecond) {
/*     */       
/* 287 */       this.packetCapLevel++;
/* 288 */       sendChatPacket(chatMessage);
/*     */     }
/*     */     else {
/*     */       
/* 292 */       Game.addChatMessage("§4[Flood protection] [Discarded] §c" + rp.a(chatMessage));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendChatPacket(String message) {
/* 301 */     bud thePlayer = this.mc.h;
/* 302 */     if (thePlayer == null) {
/*     */       return;
/*     */     }
/*     */     try {
/* 306 */       if ((this.macros.getSettings()).chatHistory)
/*     */       {
/*     */ 
/*     */         
/* 310 */         this.mc.q.d().a(message);
/* 311 */         sendChatpacket(message, this.mc);
/*     */       
/*     */       }
/*     */       else
/*     */       {
/* 316 */         sendChatpacket(message, this.mc);
/*     */       }
/*     */     
/* 319 */     } catch (NullPointerException nullPointerException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendChatpacket(String message, bib mc) {
/* 328 */     this.sending++;
/* 329 */     la packet = new la(message);
/* 330 */     this.sending--;
/*     */     
/* 332 */     mc.h.d.a((ht)packet);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSending() {
/* 337 */     return (this.sending > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFilteringEnabled() {
/* 347 */     return ((this.macros.getSettings()).spamFilterEnabled && (this.macros.getSettings()).spamFilterStyle != FilterStyle.NONE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpamLimit() {
/* 357 */     return SETTINGS.spamMessageLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpamMessageTicks() {
/* 367 */     return SETTINGS.spamMessageTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSpamLevel() {
/* 372 */     return this.spamLevel + this.queuedMessages.size() * SETTINGS.spamMessageTicks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getHasQueue() {
/* 382 */     return (this.queuedMessages.size() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getQueueStatusText() {
/* 392 */     return String.format("§aFlood protection: §f[§4%s§f]§a messages in queue", new Object[] { Integer.valueOf(this.queuedMessages.size()) });
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
/*     */ 
/*     */   
/*     */   public boolean mousePressed(int left, int top, int mouseX, int mouseY) {
/* 406 */     if (mouseX > 2 && mouseX < 18 && mouseY > top - 2 && mouseY < top + 10) {
/*     */       
/* 408 */       clearQueue();
/* 409 */       return true;
/*     */     } 
/*     */     
/* 412 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\SpamFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */