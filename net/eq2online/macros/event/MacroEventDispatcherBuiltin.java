/*     */ package net.eq2online.macros.event;
/*     */ 
/*     */ import ain;
/*     */ import aip;
/*     */ import bib;
/*     */ import blk;
/*     */ import bsb;
/*     */ import bsc;
/*     */ import bud;
/*     */ import cey;
/*     */ import com.mumfrey.liteloader.core.runtime.Obf;
/*     */ import hb;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.SpamFilter;
/*     */ import net.eq2online.macros.core.settings.Settings;
/*     */ import net.eq2online.macros.event.providers.OnSendChatMessageProvider;
/*     */ import net.eq2online.macros.interfaces.IChatEventListener;
/*     */ import net.eq2online.macros.interfaces.IConfigObserver;
/*     */ import net.eq2online.macros.interfaces.IConfigs;
/*     */ import net.eq2online.macros.interfaces.IObserver;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventDispatcher;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventManager;
/*     */ import net.eq2online.obfuscation.ObfTbl;
/*     */ import net.eq2online.util.Game;
/*     */ import rp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroEventDispatcherBuiltin
/*     */   implements IMacroEventDispatcher, IChatEventListener, IConfigObserver
/*     */ {
/*  39 */   private static final Obf POINTER = (Obf)ObfTbl.scrollTo;
/*  40 */   private static final String SEARGE = POINTER.srg;
/*     */   
/*     */   private final bib mc;
/*     */   
/*     */   private final Macros macros;
/*     */   
/*     */   private final Settings settings;
/*     */   
/*     */   private SpamFilter spamFilter;
/*     */   
/*     */   private MacroEventValueWatcher<Float> healthWatcher;
/*     */   
/*     */   private MacroEventValueWatcher<Float> xpWatcher;
/*     */   
/*     */   private MacroEventValueWatcher<Float> weatherWatcher;
/*     */   
/*     */   private MacroEventValueWatcher<Integer> foodWatcher;
/*     */   
/*     */   private MacroEventValueWatcher<Integer> armourWatcher;
/*     */   
/*     */   private MacroEventValueWatcher<Integer> modeWatcher;
/*     */   
/*     */   private MacroEventValueWatcher<Integer> inventorySlotWatcher;
/*     */   
/*     */   private MacroEventValueWatcher<Integer> oxygenWatcher;
/*     */   
/*     */   private MacroEventValueWatcher<Integer> levelWatcher;
/*     */   
/*     */   private MacroEventValueWatcher<Integer> durabilityWatcher;
/*     */   
/*  70 */   private MacroEventValueWatcher<Integer>[] armourWatchers = (MacroEventValueWatcher<Integer>[])new MacroEventValueWatcher[4];
/*     */ 
/*     */ 
/*     */   
/*     */   private MacroEventValueWatcher<blk> guiWatcher;
/*     */ 
/*     */ 
/*     */   
/*     */   private MacroEventValueWatcher<bsb> worldWatcher;
/*     */ 
/*     */ 
/*     */   
/*     */   private MacroEventListWatcher<Collection<bsc>, bsc> playerListWatcher;
/*     */ 
/*     */ 
/*     */   
/*  86 */   private aip lastItemStack = aip.a;
/*  87 */   private aip[] lastArmour = new aip[] { aip.a, aip.a, aip.a, aip.a };
/*     */   
/*     */   private boolean dispatchedArmourEvent = true;
/*  90 */   private long joinedGameDelay = 0L;
/*     */   
/*  92 */   private Map<String, OnSendChatMessageProvider> chatMessageHandlers = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroEventDispatcherBuiltin(Macros macros, bib minecraft) {
/* 102 */     this.macros = macros;
/* 103 */     this.settings = macros.getSettings();
/* 104 */     this.mc = minecraft;
/*     */     
/* 106 */     this.healthWatcher = new MacroEventValueWatcher<>(this.macros, BuiltinEvent.onHealthChange);
/* 107 */     this.xpWatcher = new MacroEventValueWatcher<>(this.macros, BuiltinEvent.onXPChange);
/* 108 */     this.weatherWatcher = new MacroEventValueWatcher<>(this.macros, BuiltinEvent.onWeatherChange);
/*     */     
/* 110 */     this.foodWatcher = new MacroEventValueWatcher<>(this.macros, BuiltinEvent.onFoodChange);
/* 111 */     this.armourWatcher = new MacroEventValueWatcher<>(this.macros, BuiltinEvent.onArmourChange);
/* 112 */     this.modeWatcher = new MacroEventValueWatcher<>(this.macros, BuiltinEvent.onModeChange);
/* 113 */     this.inventorySlotWatcher = new MacroEventValueWatcher<>(this.macros, BuiltinEvent.onInventorySlotChange);
/* 114 */     this.oxygenWatcher = new MacroEventValueWatcher<>(this.macros, BuiltinEvent.onOxygenChange);
/* 115 */     this.levelWatcher = new MacroEventValueWatcher<>(this.macros, BuiltinEvent.onLevelChange);
/* 116 */     this.durabilityWatcher = new MacroEventValueWatcher<>(this.macros, BuiltinEvent.onItemDurabilityChange);
/*     */     
/* 118 */     this.guiWatcher = new MacroEventValueWatcher<>(this.macros, BuiltinEvent.onShowGui);
/* 119 */     this.worldWatcher = new MacroEventValueWatcher<>(this.macros, BuiltinEvent.onWorldChange);
/*     */     
/* 121 */     this.armourWatchers[3] = new MacroEventValueWatcher<>(this.macros, BuiltinEvent.onArmourDurabilityChange);
/* 122 */     this.armourWatchers[2] = new MacroEventValueWatcher<>(this.macros, BuiltinEvent.onArmourDurabilityChange);
/* 123 */     this.armourWatchers[1] = new MacroEventValueWatcher<>(this.macros, BuiltinEvent.onArmourDurabilityChange);
/* 124 */     this.armourWatchers[0] = new MacroEventValueWatcher<>(this.macros, BuiltinEvent.onArmourDurabilityChange);
/*     */     
/* 126 */     this.playerListWatcher = new MacroEventListWatcher<>(this.macros, BuiltinEvent.onPlayerJoined);
/*     */ 
/*     */     
/* 129 */     this.macros.getChatHandler().registerListener(this);
/* 130 */     this.macros.getSettingsHandler().registerObserver((IObserver)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initSinglePlayer() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick(IMacroEventManager manager, bib minecraft) {
/* 145 */     bud player = this.mc.h;
/*     */     
/* 147 */     if (player == null || player.bv == null || this.mc.c == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 152 */     if (this.joinedGameDelay > 0L)
/*     */     {
/* 154 */       this.joinedGameDelay--;
/*     */     }
/*     */     
/* 157 */     bsc playerInfo = this.mc.v().a(player.bm());
/*     */     
/* 159 */     if (this.worldWatcher.checkValue(this.mc.f)) {
/*     */       
/* 161 */       this.worldWatcher.sendEvent(new String[0]);
/*     */ 
/*     */       
/* 164 */       this.healthWatcher.checkValueAndSuppress(Float.valueOf(player.cd()));
/* 165 */       this.foodWatcher.checkValueAndSuppress(Integer.valueOf(player.di().a()));
/* 166 */       this.armourWatcher.checkValueAndSuppress(Integer.valueOf(player.cg()));
/* 167 */       this.modeWatcher.checkValueAndSuppress(Integer.valueOf(playerInfo.b().a()));
/* 168 */       this.oxygenWatcher.checkValueAndSuppress(Integer.valueOf(player.aZ()));
/* 169 */       this.xpWatcher.checkValueAndSuppress(Float.valueOf(player.bR));
/* 170 */       this.levelWatcher.checkValueAndSuppress(Integer.valueOf(player.bP));
/* 171 */       this.weatherWatcher.checkValueAndSuppress(Float.valueOf(this.mc.f.j(0.0F)));
/* 172 */       this.guiWatcher.checkValueAndSuppress(minecraft.m);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 178 */     aip currentItem = player.bv.i();
/* 179 */     boolean isSameItem = currentItem.b(this.lastItemStack);
/*     */     
/* 181 */     if (isSameItem && currentItem.c().m()) {
/*     */ 
/*     */       
/* 184 */       if (!currentItem.b() && this.durabilityWatcher.checkValue(Integer.valueOf(currentItem.j())))
/*     */       {
/* 186 */         this.durabilityWatcher.sendEvent(new String[0]);
/*     */       }
/*     */     }
/* 189 */     else if (this.lastItemStack.b() || (!currentItem.b() && !isSameItem)) {
/*     */       
/* 191 */       this.durabilityWatcher.suppressNext();
/*     */     } 
/*     */ 
/*     */     
/* 195 */     this.lastItemStack = currentItem;
/*     */     
/* 197 */     this.dispatchedArmourEvent = false;
/* 198 */     checkArmourAndDispatch(player, 3);
/* 199 */     checkArmourAndDispatch(player, 2);
/* 200 */     checkArmourAndDispatch(player, 1);
/* 201 */     checkArmourAndDispatch(player, 0);
/*     */ 
/*     */     
/* 204 */     this.weatherWatcher.checkValueAndDispatch(Float.valueOf(this.mc.f.j(0.0F)), new String[0]);
/* 205 */     this.modeWatcher.checkValueAndDispatch(Integer.valueOf(playerInfo.b().a()), new String[0]);
/* 206 */     this.healthWatcher.checkValueAndDispatch(Float.valueOf(player.cd()), new String[0]);
/* 207 */     this.foodWatcher.checkValueAndDispatch(Integer.valueOf(player.di().a()), new String[0]);
/* 208 */     this.armourWatcher.checkValueAndDispatch(Integer.valueOf(player.cg()), new String[0]);
/* 209 */     this.oxygenWatcher.checkValueAndDispatch(Integer.valueOf(player.aZ()), new String[0]);
/* 210 */     this.xpWatcher.checkValueAndDispatch(Float.valueOf(player.bR), new String[0]);
/* 211 */     this.levelWatcher.checkValueAndDispatch(Integer.valueOf(player.bP), new String[0]);
/* 212 */     this.guiWatcher.checkValueAndDispatch(minecraft.m, new String[0]);
/*     */     
/* 214 */     if (this.inventorySlotWatcher.checkValue(Integer.valueOf(player.bv.d)))
/*     */     {
/* 216 */       this.inventorySlotWatcher.sendEvent(new String[] { ((Integer)this.inventorySlotWatcher.getLastValue()).toString() });
/*     */     }
/*     */     
/* 219 */     if (this.mc.f.G && this.playerListWatcher.checkValue(player.d.d()) && this.joinedGameDelay <= 0L) {
/*     */       
/* 221 */       bsc networkPlayerInfo = this.playerListWatcher.getNewObject();
/*     */       
/* 223 */       String trimmedPlayerName = rp.a(networkPlayerInfo.a().getName());
/* 224 */       if (trimmedPlayerName.length() > this.settings.trimCharsUserListStart)
/*     */       {
/* 226 */         trimmedPlayerName = trimmedPlayerName.substring(this.settings.trimCharsUserListStart);
/*     */       }
/*     */       
/* 229 */       if (trimmedPlayerName.length() > this.settings.trimCharsUserListEnd)
/*     */       {
/* 231 */         trimmedPlayerName = trimmedPlayerName.substring(0, trimmedPlayerName.length() - this.settings.trimCharsUserListEnd);
/*     */       }
/*     */       
/* 234 */       if (!trimmedPlayerName.equals(player.h_()))
/*     */       {
/* 236 */         this.playerListWatcher.sendEvent(new String[] { trimmedPlayerName });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkArmourAndDispatch(bud player, int index) {
/* 243 */     MacroEventValueWatcher<Integer> watcher = this.armourWatchers[index];
/* 244 */     aip currentItem = player.bv.g(index);
/*     */     
/* 246 */     if (currentItem.b() != this.lastArmour[index].b())
/*     */     {
/* 248 */       watcher.suppressNext();
/*     */     }
/*     */     
/* 251 */     if (!currentItem.b() && watcher.checkValue(Integer.valueOf(currentItem.j())) && !this.dispatchedArmourEvent) {
/*     */       
/* 253 */       this.dispatchedArmourEvent = true;
/* 254 */       watcher.sendEvent(new String[0]);
/*     */     } 
/*     */     
/* 257 */     this.lastArmour[index] = currentItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onJoinGame(String serverName, SpamFilter spamFilter) {
/* 267 */     this.macros.sendEvent(BuiltinEvent.onJoinGame.getName(), 0, new String[] { serverName });
/* 268 */     this.playerListWatcher.reset();
/* 269 */     this.joinedGameDelay = 200L;
/* 270 */     this.spamFilter = spamFilter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onItemPickup(aip itemStack, int quantity) {
/* 275 */     String itemName = "Unknown Item";
/* 276 */     ain item = itemStack.c();
/*     */     
/* 278 */     if (item != null)
/*     */     {
/* 280 */       itemName = cey.a(item.j(itemStack) + ".name", new Object[0]);
/*     */     }
/*     */     
/* 283 */     String name = Game.getItemName(item);
/* 284 */     String stackSize = String.valueOf(quantity);
/* 285 */     String metaData = String.valueOf(itemStack.j());
/*     */     
/* 287 */     this.macros.sendEvent(BuiltinEvent.onPickupItem.getName(), 20, new String[] { name, itemName, stackSize, metaData });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onChatMessage(String chatMessage, String chatMessageNoColours) {
/* 293 */     String[] chatLines = chatMessage.split("\\r?\\n");
/*     */     
/* 295 */     for (String chatLine : chatLines) {
/*     */       
/* 297 */       this.macros.sendEvent(BuiltinEvent.onChat.getName(), 40, new String[] { chatLine, rp.a(chatLine) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSendChatMessage(String message) {
/* 304 */     boolean pass = true;
/*     */     
/* 306 */     if (!this.spamFilter.isSending()) {
/*     */       
/* 308 */       String messageId = UUID.randomUUID().toString();
/* 309 */       this.macros.sendEvent(BuiltinEvent.onSendChatMessage.getName(), 2147483647, new String[] { message, messageId });
/* 310 */       OnSendChatMessageProvider handler = this.chatMessageHandlers.get(messageId);
/* 311 */       if (handler != null)
/*     */       {
/* 313 */         pass = handler.isPass();
/*     */       }
/*     */     } 
/*     */     
/* 317 */     return pass;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLogMessage(String message) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChatMessageHandler(String uuid, OnSendChatMessageProvider provider) {
/* 327 */     this.chatMessageHandlers.put(uuid, provider);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onServerConnect(hb netclienthandler, SpamFilter spamFilter) {
/* 332 */     this.playerListWatcher.reset();
/* 333 */     this.joinedGameDelay = 200L;
/* 334 */     this.spamFilter = spamFilter;
/*     */     
/* 336 */     if (!SEARGE.equals(new String(new char[] { 'f', 'u', 'n', 'c', '_', '1', '4', '8', '3', '2', '9', '_', 'a'
/*     */           
/*     */           })))
/*     */     {
/* 340 */       this.healthWatcher = new MacroEventValueWatcher<>(this.macros, null, 0, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConfigChanged(IConfigs configs) {
/* 347 */     this.macros.sendEvent(BuiltinEvent.onConfigChange.getName(), 5, new String[0]);
/*     */   }
/*     */   
/*     */   public void onConfigAdded(IConfigs configs, String configurationName, boolean copy) {}
/*     */   
/*     */   public void onConfigRemoved(IConfigs configs, String configurationName) {}
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\event\MacroEventDispatcherBuiltin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */