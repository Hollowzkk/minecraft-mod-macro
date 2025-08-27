/*      */ package net.eq2online.macros.scripting;
/*      */ 
/*      */ import aec;
/*      */ import aed;
/*      */ import afy;
/*      */ import aip;
/*      */ import akt;
/*      */ import amu;
/*      */ import aow;
/*      */ import aox;
/*      */ import bcz;
/*      */ import bhy;
/*      */ import bib;
/*      */ import bid;
/*      */ import bjy;
/*      */ import bjz;
/*      */ import bka;
/*      */ import bkb;
/*      */ import bkc;
/*      */ import bkd;
/*      */ import bkn;
/*      */ import ble;
/*      */ import blg;
/*      */ import blk;
/*      */ import bls;
/*      */ import bme;
/*      */ import bmx;
/*      */ import bud;
/*      */ import cer;
/*      */ import ceu;
/*      */ import cgq;
/*      */ import cgr;
/*      */ import com.google.common.base.Strings;
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.mumfrey.liteloader.client.overlays.ISoundHandler;
/*      */ import com.mumfrey.liteloader.common.Resources;
/*      */ import com.mumfrey.liteloader.core.LiteLoader;
/*      */ import com.mumfrey.liteloader.gl.GL;
/*      */ import com.mumfrey.liteloader.util.ModUtilities;
/*      */ import et;
/*      */ import fi;
/*      */ import hh;
/*      */ import i;
/*      */ import java.io.File;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import n;
/*      */ import net.eq2online.console.Log;
/*      */ import net.eq2online.macros.compatibility.DelegateDisconnect;
/*      */ import net.eq2online.macros.compatibility.I18n;
/*      */ import net.eq2online.macros.core.Macros;
/*      */ import net.eq2online.macros.core.overlays.IGuiChat;
/*      */ import net.eq2online.macros.core.params.MacroParam;
/*      */ import net.eq2online.macros.gui.controls.GuiListBox;
/*      */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*      */ import net.eq2online.macros.gui.designable.DesignableGuiControlLabel;
/*      */ import net.eq2online.macros.gui.designable.DesignableGuiControlTextArea;
/*      */ import net.eq2online.macros.gui.designable.LayoutManager;
/*      */ import net.eq2online.macros.gui.helpers.SlotHelper;
/*      */ import net.eq2online.macros.gui.repl.GuiMacroRepl;
/*      */ import net.eq2online.macros.gui.screens.GuiChatFilterable;
/*      */ import net.eq2online.macros.gui.screens.GuiCustomGui;
/*      */ import net.eq2online.macros.gui.screens.GuiEditTextFile;
/*      */ import net.eq2online.macros.gui.screens.GuiMacroBind;
/*      */ import net.eq2online.macros.gui.screens.GuiMacroConfig;
/*      */ import net.eq2online.macros.gui.screens.GuiMacroPlayback;
/*      */ import net.eq2online.macros.interfaces.IListEntry;
/*      */ import net.eq2online.macros.scripting.api.IExpressionEvaluator;
/*      */ import net.eq2online.macros.scripting.api.IMacro;
/*      */ import net.eq2online.macros.scripting.api.IMacroAction;
/*      */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*      */ import net.eq2online.macros.scripting.api.IMacroEngine;
/*      */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*      */ import net.eq2online.macros.scripting.api.ISettings;
/*      */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*      */ import net.eq2online.macros.scripting.api.IVariableProviderShared;
/*      */ import net.eq2online.macros.scripting.crafting.AutoCraftingToken;
/*      */ import net.eq2online.macros.scripting.crafting.IAutoCraftingInitiator;
/*      */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*      */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*      */ import net.eq2online.macros.scripting.variable.ItemID;
/*      */ import net.eq2online.macros.scripting.variable.VariableManager;
/*      */ import net.eq2online.macros.scripting.variable.providers.VariableProviderInput;
/*      */ import net.eq2online.macros.scripting.variable.providers.VariableProviderPlayer;
/*      */ import net.eq2online.macros.scripting.variable.providers.VariableProviderSettings;
/*      */ import net.eq2online.macros.scripting.variable.providers.VariableProviderShared;
/*      */ import net.eq2online.macros.scripting.variable.providers.VariableProviderWorld;
/*      */ import net.eq2online.util.Game;
/*      */ import nf;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import qe;
/*      */ import qg;
/*      */ import r;
/*      */ import s;
/*      */ import ub;
/*      */ import ud;
/*      */ import vb;
/*      */ import vg;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ScriptActionProvider
/*      */   extends VariableManager
/*      */ {
/*      */   static class TickableToast
/*      */     extends bkd
/*      */   {
/*      */     private boolean ticking = false;
/*      */     private final int displayTicks;
/*  124 */     private int ticks = -20;
/*      */ 
/*      */     
/*      */     TickableToast(bkd.a icon, hh titleComponent, hh subtitleComponent, int ticks) {
/*  128 */       super(icon, titleComponent, subtitleComponent, true);
/*  129 */       this.displayTicks = ticks;
/*  130 */       a(1.0F);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public bkb.a a(bkc toastGui, long delta) {
/*  136 */       if (!this.ticking)
/*      */       {
/*  138 */         this.ticking = true;
/*      */       }
/*  140 */       return super.a(toastGui, delta);
/*      */     }
/*      */ 
/*      */     
/*      */     boolean onTick() {
/*  145 */       if (!this.ticking)
/*      */       {
/*  147 */         return true;
/*      */       }
/*      */       
/*  150 */       if (this.ticks++ >= this.displayTicks) {
/*      */         
/*  152 */         a();
/*  153 */         return false;
/*      */       } 
/*      */       
/*  156 */       a(1.0F - this.ticks / this.displayTicks);
/*  157 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     void clear() {
/*  162 */       a();
/*      */     }
/*      */   }
/*      */   
/*  166 */   private static final Pattern PATTERN_SOUNDNAME = Pattern.compile("^([a-z0-9_]+)((\\.[a-z0-9_]+)*)$");
/*      */   
/*  168 */   private static final nf FAKE = new nf("macros:fake");
/*      */   
/*  170 */   private static final Map<String, n> EMPTY_MAP = new HashMap<>();
/*      */   
/*  172 */   private static final aip DEFAULT_TOAST_ITEM = new aip((aow)aox.c, 1, 0);
/*      */   
/*  174 */   private final Set<String> customSounds = new HashSet<>();
/*      */   
/*  176 */   private final List<TickableToast> toasts = new ArrayList<>();
/*      */ 
/*      */   
/*      */   private final Macros macros;
/*      */ 
/*      */   
/*      */   private final SlotHelper slotHelper;
/*      */ 
/*      */   
/*  185 */   private int pendingResChangeCounter = 0;
/*      */   
/*      */   private int pendingResWidth;
/*      */   
/*      */   private int pendingResHeight;
/*      */   
/*      */   private PlaySoundResourcePack playSoundResourcePack;
/*      */   
/*  193 */   private String soundResourceNamespace = "macros";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ScriptActionProvider(Macros macros, bib minecraft) {
/*  203 */     super(minecraft);
/*      */     
/*  205 */     this.macros = macros;
/*  206 */     this.slotHelper = new SlotHelper(macros, minecraft);
/*  207 */     this.sharedVariableProvider = (IVariableProviderShared)new VariableProviderShared(this.macros);
/*      */     
/*  209 */     registerVariableProvider((IVariableProvider)new VariableProviderInput());
/*  210 */     registerVariableProvider((IVariableProvider)new VariableProviderSettings(this.mc));
/*  211 */     registerVariableProvider((IVariableProvider)new VariableProviderPlayer(this.macros, this.mc));
/*  212 */     registerVariableProvider((IVariableProvider)new VariableProviderWorld(this.macros, this.mc));
/*  213 */     registerVariableProvider((IVariableProvider)this.sharedVariableProvider);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void init() {
/*  219 */     File customSoundsDir = this.macros.getFile("sounds");
/*  220 */     this.playSoundResourcePack = new PlaySoundResourcePack(customSoundsDir, this.soundResourceNamespace);
/*  221 */     Resources<?, cer> resources = LiteLoader.getGameEngine().getResources();
/*  222 */     resources.registerResourcePack(this.playSoundResourcePack);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public IMacroEngine getMacroEngine() {
/*  228 */     return (IMacroEngine)this.macros;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ISettings getSettings() {
/*  234 */     return (ISettings)this.macros.getSettings();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSoundResourceNamespace() {
/*  240 */     return this.soundResourceNamespace;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTick() {
/*  246 */     super.onTick();
/*      */     
/*  248 */     this.macros.getAutoCraftingManager().onTick((IScriptActionProvider)this);
/*      */     
/*  250 */     if (this.pendingResChangeCounter > 0) {
/*      */       
/*  252 */       this.pendingResChangeCounter--;
/*      */       
/*  254 */       if (this.pendingResChangeCounter == 0) {
/*      */         
/*      */         try {
/*      */           
/*  258 */           Display.setResizable(false);
/*  259 */           ModUtilities.setWindowSize(this.pendingResWidth, this.pendingResHeight);
/*  260 */           Display.setResizable(true);
/*      */         }
/*  262 */         catch (Exception ex) {
/*      */           
/*  264 */           ex.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  269 */     for (Iterator<TickableToast> iter = this.toasts.iterator(); iter.hasNext(); ) {
/*      */       
/*  271 */       TickableToast toast = iter.next();
/*  272 */       if (!toast.onTick())
/*      */       {
/*  274 */         iter.remove();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ExpressionEvaluator getExpressionEvaluator(IMacro macro, String expression) {
/*  287 */     ExpressionEvaluator evaluator = new ExpressionEvaluator(expression, (IScriptActionProvider)this, macro);
/*      */     
/*  289 */     return evaluator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionSendChatMessage(IMacro macro, IMacroAction instance, String message) {
/*  300 */     if (message != null && message.length() > 0) {
/*      */       
/*  302 */       if (instance != null && instance.getActionProcessor() != null)
/*      */       {
/*  304 */         if (instance.getActionProcessor().isUnsafe() && !(this.macros.getSettings()).spamFilterEnabled) {
/*      */           return;
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*  310 */       String[] messages = message.replaceAll("\\x5C\\x7C", "").split("[\\x7C\\x82]");
/*      */       
/*  312 */       for (String msg : messages) {
/*      */         
/*  314 */         msg = msg.replaceAll("", "\\|").trim();
/*  315 */         this.macros.dispatchChatMessage(msg, macro.getContext().getScriptContext());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionAddChatMessage(String message) {
/*  327 */     this.macros.getChatHandler().onLogMessage(message);
/*  328 */     Game.addChatMessage(message);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionDisconnect() {
/*  339 */     GL.glClear(256);
/*      */ 
/*      */     
/*  342 */     GL.glDisableLighting();
/*  343 */     GL.glDisableDepthTest();
/*      */     
/*  345 */     Log.info("Disconnect");
/*  346 */     this.mc.a((blk)new DelegateDisconnect());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionDisplayGuiScreen(String guiScreenName, ScriptContext context) {
/*  357 */     if (guiScreenName == null || guiScreenName.length() == 0) {
/*      */       
/*  359 */       if (this.mc.m != null) {
/*      */         
/*  361 */         if (this.mc.m instanceof GuiCustomGui) this.mc.a(null); 
/*  362 */         if (this.mc.m instanceof bkn) this.mc.a(null); 
/*  363 */         if (this.mc.m instanceof blg) this.mc.a(null); 
/*  364 */         if (this.mc.m instanceof bmx) this.mc.a(null); 
/*  365 */         if (this.mc.m instanceof ble) this.mc.a(null); 
/*  366 */         if (this.mc.m instanceof bls) this.mc.a(null); 
/*  367 */         if (this.mc.m instanceof bme) this.mc.a(null); 
/*  368 */         if (this.mc.m instanceof GuiMacroBind) this.mc.a(null); 
/*  369 */         if (this.mc.m instanceof GuiMacroConfig) this.mc.a(null); 
/*  370 */         if (this.mc.m instanceof bmg) this.mc.a(null); 
/*  371 */         if (this.slotHelper.currentScreenIsContainer()) this.mc.a(null); 
/*  372 */         if (this.slotHelper.currentScreenIsInventory()) this.mc.a(null);
/*      */       
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*  378 */     if ("chat".equalsIgnoreCase(guiScreenName)) {
/*      */       
/*  380 */       this.mc.a((blk)new bkn());
/*      */     }
/*  382 */     else if ("filterablechat".equalsIgnoreCase(guiScreenName)) {
/*      */       
/*  384 */       if ((this.macros.getSettings()).showFilterableChat)
/*      */       {
/*  386 */         this.mc.a((blk)new GuiChatFilterable(this.macros));
/*      */       }
/*      */     }
/*  389 */     else if ("menu".equalsIgnoreCase(guiScreenName)) {
/*      */       
/*  391 */       this.mc.a((blk)new blg());
/*      */     }
/*  393 */     else if ("inventory".equalsIgnoreCase(guiScreenName) || "inv".equalsIgnoreCase(guiScreenName)) {
/*      */       
/*  395 */       this.mc.a((blk)new bmx((aed)this.mc.h));
/*      */     }
/*  397 */     else if ("options".equalsIgnoreCase(guiScreenName)) {
/*      */       
/*  399 */       this.mc.a((blk)new ble(null, this.mc.t));
/*      */     }
/*  401 */     else if ("video".equalsIgnoreCase(guiScreenName)) {
/*      */       
/*  403 */       this.mc.a((blk)new bls(null, this.mc.t));
/*      */     }
/*  405 */     else if ("controls".equalsIgnoreCase(guiScreenName)) {
/*      */       
/*  407 */       this.mc.a((blk)new bme(null, this.mc.t));
/*      */     }
/*  409 */     else if ("macrobind".equalsIgnoreCase(guiScreenName)) {
/*      */       
/*  411 */       this.mc.a((blk)new GuiMacroBind(this.macros, this.mc, true, false));
/*      */     }
/*  413 */     else if ("macroplayback".equalsIgnoreCase(guiScreenName)) {
/*      */       
/*  415 */       this.mc.a((blk)new GuiMacroPlayback(this.macros, this.mc));
/*      */     }
/*  417 */     else if ("macroconfig".equalsIgnoreCase(guiScreenName)) {
/*      */       
/*  419 */       this.mc.a((blk)new GuiMacroConfig(this.macros, this.mc, null, false));
/*      */     }
/*  421 */     else if ("texteditor".equalsIgnoreCase(guiScreenName)) {
/*      */       
/*  423 */       this.mc.a((blk)new GuiEditTextFile(this.macros, this.mc, null, context));
/*      */     }
/*  425 */     else if ("repl".equalsIgnoreCase(guiScreenName)) {
/*      */       
/*  427 */       if (!(this.mc.m instanceof GuiMacroRepl))
/*      */       {
/*  429 */         this.mc.a((blk)new GuiMacroRepl(this.macros, this.mc, null));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionDisplayCustomScreen(String screenName, String backScreenName, boolean enableTriggers) {
/*  442 */     if (screenName == null || screenName.trim().isEmpty()) {
/*      */       
/*  444 */       if (this.mc.m instanceof GuiCustomGui)
/*      */       {
/*  446 */         this.mc.a(null);
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*  451 */     LayoutManager layoutManager = this.macros.getLayoutManager();
/*  452 */     if (!layoutManager.layoutExists(screenName))
/*      */     {
/*  454 */       layoutManager.getLayout(screenName);
/*      */     }
/*      */     
/*  457 */     backScreenName = Strings.emptyToNull(backScreenName);
/*  458 */     if (!layoutManager.layoutExists(backScreenName))
/*      */     {
/*  460 */       backScreenName = null;
/*      */     }
/*      */     
/*  463 */     this.mc.a((blk)new GuiCustomGui(this.macros, this.mc, screenName, backScreenName, enableTriggers));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionBindScreenToSlot(String slotName, String screenName) {
/*  473 */     this.macros.getLayoutManager().setBinding(slotName, screenName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String actionSwitchConfig(String configName, boolean verbose) {
/*  483 */     String oldConfig = this.macros.getActiveConfig();
/*      */     
/*  485 */     if (this.macros.hasConfig(configName)) {
/*      */       
/*  487 */       Log.info("Switching to config {0}", new Object[] { configName });
/*  488 */       this.macros.setActiveConfig(configName);
/*      */       
/*  490 */       if (verbose)
/*      */       {
/*  492 */         Game.addChatMessage(I18n.get("message.config", new Object[] { this.macros.getActiveConfigName() }));
/*      */       }
/*      */     } 
/*      */     
/*  496 */     return oldConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String actionOverlayConfig(String configName, boolean toggle, boolean verbose) {
/*  506 */     String oldConfig = this.macros.getOverlayConfig();
/*      */     
/*  508 */     if (toggle && oldConfig != null && oldConfig.equals(configName)) {
/*      */       
/*  510 */       Log.info("Stripping overlaid config");
/*  511 */       this.macros.setOverlayConfig(null);
/*      */       
/*  513 */       if (verbose)
/*      */       {
/*  515 */         Game.addChatMessage(I18n.get("message.overlay.remove", new Object[] { this.macros.getOverlayConfigName("§c") }));
/*      */       }
/*      */     }
/*  518 */     else if (configName == null || this.macros.hasConfig(configName)) {
/*      */       
/*  520 */       Log.info("Overlaying config {0}", new Object[] { configName });
/*  521 */       this.macros.setOverlayConfig(configName);
/*      */       
/*  523 */       if (verbose)
/*      */       {
/*  525 */         Game.addChatMessage(I18n.get((configName == null) ? "message.overlay.remove" : "message.overlay.add", new Object[] { this.macros
/*  526 */                 .getOverlayConfigName("§c") }));
/*      */       }
/*      */     } 
/*      */     
/*  530 */     return oldConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionRenderDistance() {
/*  540 */     bid gameSettings = this.mc.t;
/*  541 */     int newDistance = 8;
/*  542 */     if (gameSettings.e < 9) newDistance = 4; 
/*  543 */     if (gameSettings.e < 5) newDistance = 2; 
/*  544 */     if (gameSettings.e < 3) newDistance = 16; 
/*  545 */     gameSettings.e = newDistance;
/*  546 */     gameSettings.b();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionSetRenderDistance(String distance) {
/*  556 */     int newDistance = 0;
/*  557 */     if ("far".equalsIgnoreCase(distance)) {
/*      */       
/*  559 */       newDistance = 16;
/*      */     }
/*  561 */     else if ("normal".equalsIgnoreCase(distance)) {
/*      */       
/*  563 */       newDistance = 8;
/*      */     }
/*  565 */     else if ("short".equalsIgnoreCase(distance)) {
/*      */       
/*  567 */       newDistance = 4;
/*      */     }
/*  569 */     else if ("tiny".equalsIgnoreCase(distance)) {
/*      */       
/*  571 */       newDistance = 2;
/*      */     }
/*      */     else {
/*      */       
/*  575 */       newDistance = Math.min(16, ScriptCore.tryParseInt(distance, 0));
/*      */     } 
/*      */     
/*  578 */     if (newDistance > 0) {
/*      */       
/*  580 */       bid gameSettings = this.mc.t;
/*  581 */       gameSettings.e = newDistance;
/*  582 */       gameSettings.b();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean actionInventoryPick(String itemIdString, int itemDamage) {
/*  593 */     ItemID itemId = new ItemID(itemIdString, itemDamage);
/*  594 */     bud thePlayer = this.mc.h;
/*      */     
/*  596 */     if (thePlayer != null) {
/*      */       
/*  598 */       aip itemStack = itemId.toItemStack(1);
/*  599 */       aec inventoryPlayer = thePlayer.bv;
/*      */       
/*  601 */       aip currentItem = inventoryPlayer.i();
/*  602 */       if (!currentItem.b() && currentItem.c() == itemStack.c() && (
/*  603 */         !currentItem.g() || currentItem.j() == itemStack.j()))
/*      */       {
/*  605 */         return true;
/*      */       }
/*      */       
/*  608 */       int slotForItem = getSlotFor(inventoryPlayer, itemStack);
/*      */       
/*  610 */       if (thePlayer.bO.d && slotForItem == -1) {
/*      */         
/*  612 */         if (!itemStack.g())
/*      */         {
/*  614 */           itemStack = new aip(itemStack.c(), itemStack.d());
/*      */         }
/*      */         
/*  617 */         inventoryPlayer.a(itemStack);
/*  618 */         this.mc.c.a(thePlayer.b(ub.a), 36 + inventoryPlayer.d);
/*      */       }
/*  620 */       else if (slotForItem != -1) {
/*      */         
/*  622 */         if (aec.e(slotForItem)) {
/*      */           
/*  624 */           inventoryPlayer.d = slotForItem;
/*      */         }
/*      */         else {
/*      */           
/*  628 */           this.mc.c.a(slotForItem);
/*      */         } 
/*      */       } 
/*  631 */       currentItem = inventoryPlayer.i();
/*  632 */       return (!currentItem.b() && itemId.equals(currentItem));
/*      */     } 
/*      */     
/*  635 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private int getSlotFor(aec inventoryPlayer, aip itemStack) {
/*  640 */     for (int i = 0; i < inventoryPlayer.a.size(); i++) {
/*      */       
/*  642 */       aip invStack = (aip)inventoryPlayer.a.get(i);
/*  643 */       if (!invStack.b() && invStack.c() == itemStack.c() && (
/*  644 */         !invStack.g() || invStack.j() == itemStack.j()))
/*      */       {
/*  646 */         return i;
/*      */       }
/*      */     } 
/*      */     
/*  650 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionInventorySlot(int slotId) {
/*  660 */     bud thePlayer = this.mc.h;
/*      */     
/*  662 */     if (slotId > 0 && slotId < 10)
/*      */     {
/*  664 */       thePlayer.bv.d = slotId - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionInventoryMove(int offset) {
/*  675 */     bud thePlayer = this.mc.h;
/*  676 */     thePlayer.bv.f(offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionSetSprinting(boolean sprint) {
/*  686 */     bud thePlayer = this.mc.h;
/*      */     
/*  688 */     if (sprint) {
/*      */       
/*  690 */       if (thePlayer.z && !thePlayer.e.h && 
/*      */         
/*  692 */         !thePlayer.aV() && (thePlayer
/*  693 */         .di().a() > 6.0F || thePlayer.bO.c) && 
/*  694 */         !thePlayer.cG() && !thePlayer.a(vb.o))
/*      */       {
/*  696 */         thePlayer.f(true);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  701 */       thePlayer.f(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionStopMacros() {
/*  712 */     this.macros.terminateActiveMacros();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionStopMacros(IMacro macro, int keyCode) {
/*  722 */     if (macro.getID() == keyCode)
/*      */     {
/*  724 */       macro.kill();
/*      */     }
/*      */     
/*  727 */     ScriptContext context = macro.getContext().getScriptContext();
/*      */     
/*  729 */     this.macros.terminateActiveMacros(context, keyCode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteArrayElement(IMacro macro, String arrayName, int offset) {
/*  740 */     arrayName = sanitiseArrayVariableName(macro, arrayName);
/*      */     
/*  742 */     if (arrayName != null)
/*      */     {
/*  744 */       if (arrayName.startsWith("@")) {
/*      */         
/*  746 */         this.sharedVariableProvider.delete(arrayName.substring(1), offset);
/*      */       }
/*      */       else {
/*      */         
/*  750 */         macro.markDirty();
/*  751 */         macro.getArrayProvider().delete(arrayName, offset);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionPumpCharacters(String chars) {
/*  763 */     if (chars == null)
/*      */       return; 
/*  765 */     blk currentScreen = this.mc.m;
/*  766 */     if (currentScreen instanceof IGuiChat) {
/*      */       
/*  768 */       ((IGuiChat)currentScreen).writeText(chars);
/*      */       
/*      */       return;
/*      */     } 
/*  772 */     this.macros.getInputHandler().pumpKeyChars(chars, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionPumpKeyPress(int keyCode, boolean deep) {
/*  782 */     this.macros.getInputHandler().pumpKeyCode(keyCode, deep);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionSelectResourcePacks(String[] resourcePackNames) {
/*  792 */     ceu resourcePackRepository = this.mc.P();
/*  793 */     resourcePackRepository.b();
/*      */     
/*  795 */     List<ceu.a> resourcePackList = resourcePackRepository.d();
/*  796 */     List<ceu.a> selectedResourcePacks = new ArrayList<>();
/*      */     
/*  798 */     for (int pos = 0; pos < resourcePackNames.length; pos++) {
/*      */       
/*  800 */       int packIndex = getIndexOfResourcePack(resourcePackList, resourcePackNames[pos]);
/*  801 */       if (packIndex > -1) {
/*      */         
/*  803 */         ceu.a pack = resourcePackList.get(packIndex);
/*  804 */         if (!selectedResourcePacks.contains(pack))
/*      */         {
/*  806 */           selectedResourcePacks.add(pack);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  811 */     this.mc.t.m.clear();
/*      */ 
/*      */     
/*      */     try {
/*  815 */       this.mc.P().a(selectedResourcePacks);
/*      */       
/*  817 */       for (ceu.a var14 : selectedResourcePacks)
/*      */       {
/*  819 */         this.mc.t.m.add(var14.d());
/*      */       }
/*      */     }
/*  822 */     catch (Exception exception) {
/*      */       
/*  824 */       resourcePackRepository.a(new ArrayList());
/*      */     } 
/*      */     
/*  827 */     this.mc.t.b();
/*  828 */     this.mc.f();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionUseItem(bib minecraft, bud thePlayer, aip itemstack, int slotID) {
/*  840 */     int oldItem = thePlayer.bv.d;
/*  841 */     thePlayer.bv.d = slotID;
/*      */     
/*  843 */     for (ub enumhand : ub.values()) {
/*      */       
/*  845 */       et blockpos = minecraft.s.a();
/*  846 */       if (minecraft.f.o(blockpos).a() != bcz.a) {
/*      */         
/*  848 */         int i = (itemstack != null) ? itemstack.E() : 0;
/*  849 */         ud result = minecraft.c.a(minecraft.h, minecraft.f, blockpos, minecraft.s.b, minecraft.s.c, enumhand);
/*      */ 
/*      */         
/*  852 */         if (result == ud.a) {
/*      */           
/*  854 */           if (itemstack != null) {
/*      */             
/*  856 */             if (itemstack.E() == 0) {
/*      */               
/*  858 */               minecraft.h.a(enumhand, (aip)null); break;
/*      */             } 
/*  860 */             if (itemstack.E() != i || minecraft.c.h())
/*      */             {
/*  862 */               minecraft.o.c.a(enumhand);
/*      */             }
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  871 */     thePlayer.bv.d = oldItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionBindKey(int keyBindId, int keyCode) {
/*      */     try {
/*  883 */       this.mc.t.a(this.mc.t.as[keyBindId], keyCode);
/*  884 */       bhy.c();
/*      */     }
/*  886 */     catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionSetEntityDirection(vg entity, float yaw, float pitch) {
/*  896 */     if (entity != null) {
/*      */       
/*  898 */       entity.v = yaw % 360.0F;
/*      */       
/*  900 */       pitch %= 360.0F;
/*      */       
/*  902 */       if (pitch <= 90.0F) {
/*      */         
/*  904 */         entity.w = pitch;
/*      */       }
/*  906 */       else if (pitch >= 270.0F) {
/*      */         
/*  908 */         entity.w = pitch - 360.0F;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionRespawnPlayer() {
/*  916 */     this.mc.h.cY();
/*  917 */     this.mc.a(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <TItem> IListEntry<TItem> actionAddItemToList(GuiListBox<TItem> listBox, MacroParam.Type itemType, String newItem, String displayName, int iconID) {
/*  932 */     return actionAddItemToList(listBox, itemType, newItem, displayName, iconID, (Object)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <TItem> IListEntry<TItem> actionAddItemToList(GuiListBox<TItem> listBox, MacroParam.Type itemType, String newItem, String displayName, int iconID, Object newItemData) {
/*  946 */     IListEntry<TItem> newListEntry = listBox.createObject(newItem, iconID, newItemData);
/*  947 */     newListEntry.setDisplayName(displayName);
/*  948 */     listBox.addItemAt(newListEntry, listBox.getItemCount() - 1);
/*  949 */     listBox.save();
/*  950 */     return newListEntry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionAddLogMessage(String targetName, String logMessage) {
/*      */     try {
/*  962 */       DesignableGuiControl textArea = this.macros.getLayoutManager().getControls().getControl(targetName);
/*      */       
/*  964 */       if (textArea != null && textArea instanceof DesignableGuiControlTextArea)
/*      */       {
/*  966 */         ((DesignableGuiControlTextArea)textArea).addMessage(logMessage);
/*      */       }
/*      */     }
/*  969 */     catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionSetLabel(String targetName, String text, String binding) {
/*      */     try {
/*  982 */       DesignableGuiControl label = this.macros.getLayoutManager().getControls().getControl(targetName);
/*      */       
/*  984 */       if (label != null && label instanceof DesignableGuiControlLabel)
/*      */       {
/*  986 */         if (text != null)
/*      */         {
/*  988 */           ((DesignableGuiControlLabel)label).setProperty("text", text);
/*      */         }
/*      */         
/*  991 */         if (binding != null)
/*      */         {
/*  993 */           ((DesignableGuiControlLabel)label).setProperty("binding", binding);
/*      */         }
/*      */       }
/*      */     
/*  997 */     } catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionClearCrafting() {
/* 1007 */     this.macros.getAutoCraftingManager().clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AutoCraftingToken actionCraft(IAutoCraftingInitiator initiator, bud thePlayer, String itemId, int damageValue, int amount, boolean shouldThrowResult, boolean verbose) {
/* 1021 */     return this.macros.getAutoCraftingManager().craft((IScriptActionProvider)this, initiator, thePlayer, itemId, damageValue, amount, shouldThrowResult, verbose);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionBreakLoop(IMacro macro, IMacroAction breakAction) {
/* 1032 */     if (breakAction != null) {
/*      */       
/* 1034 */       IMacroActionProcessor actionProcessor = breakAction.getActionProcessor();
/* 1035 */       actionProcessor.breakLoop((IScriptActionProvider)this, macro, breakAction);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionBeginUnsafeBlock(IMacro macro, IMacroAction instance, int maxActions) {
/* 1047 */     if (instance != null) {
/*      */       
/* 1049 */       IMacroActionProcessor actionProcessor = instance.getActionProcessor();
/* 1050 */       actionProcessor.beginUnsafeBlock((IScriptActionProvider)this, macro, instance, maxActions);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionEndUnsafeBlock(IMacro macro, IMacroAction instance) {
/* 1062 */     if (instance != null) {
/*      */       
/* 1064 */       IMacroActionProcessor actionProcessor = instance.getActionProcessor();
/* 1065 */       actionProcessor.endUnsafeBlock((IScriptActionProvider)this, macro, instance);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionScheduleResChange(int width, int height) {
/* 1076 */     if (this.pendingResChangeCounter == 0)
/*      */     {
/* 1078 */       this.pendingResChangeCounter = 5;
/*      */     }
/*      */     
/* 1081 */     this.pendingResWidth = width;
/* 1082 */     this.pendingResHeight = height;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionSetCameraMode(int mode) {
/* 1092 */     int previousMode = this.mc.t.aw;
/* 1093 */     this.mc.t.aw = Math.min(Math.max(mode, 0), 2);
/*      */     
/* 1095 */     if (this.mc.t.aw == 0) {
/*      */       
/* 1097 */       this.mc.o.a(this.mc.aa());
/*      */     }
/* 1099 */     else if (this.mc.t.aw == 1) {
/*      */       
/* 1101 */       this.mc.o.a((vg)null);
/*      */     } 
/*      */     
/* 1104 */     if (previousMode != this.mc.t.aw)
/*      */     {
/* 1106 */       this.mc.g.o();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionDisplayToast(IScriptActionProvider.ToastType type, String icon, hh text1, hh text2, int ticks) {
/* 1113 */     aip itemStack = DEFAULT_TOAST_ITEM;
/* 1114 */     if (type.ordinal() <= IScriptActionProvider.ToastType.RECIPE.ordinal()) {
/*      */       
/* 1116 */       ItemID itemId = new ItemID(icon);
/*      */       
/*      */       try {
/* 1119 */         itemStack = itemId.toItemStack(1);
/*      */       }
/* 1121 */       catch (Exception ex) {
/*      */         
/* 1123 */         itemStack = DEFAULT_TOAST_ITEM;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 1129 */       switch (type) {
/*      */         
/*      */         case ADVANCEMENT:
/* 1132 */           displayAdvancementToast(s.a, itemStack, text1, text2);
/*      */           return;
/*      */         case CHALLENGE:
/* 1135 */           displayAdvancementToast(s.b, itemStack, text1, text2);
/*      */           return;
/*      */         case GOAL:
/* 1138 */           displayAdvancementToast(s.c, itemStack, text1, text2);
/*      */           return;
/*      */         case RECIPE:
/* 1141 */           displayRecipeToast(itemStack);
/*      */           return;
/*      */         case HINT:
/* 1144 */           displaySystemToast(bka.a.a, text1, text2);
/*      */           return;
/*      */         case NARRATOR:
/* 1147 */           displaySystemToast(bka.a.b, text1, text2);
/*      */           return;
/*      */         case TUTORIAL:
/* 1150 */           displayTutorialToast(icon.toLowerCase(), text1, text2, ticks);
/*      */           return;
/*      */       } 
/* 1153 */       displayAdvancementToast(s.a, itemStack, text1, text2);
/*      */ 
/*      */     
/*      */     }
/* 1157 */     catch (Exception ex) {
/*      */       
/* 1159 */       ex.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void displayAdvancementToast(s frame, aip itemStack, hh text1, hh text2) {
/* 1165 */     r displayInfo = new r(itemStack, text1, text2, null, frame, true, false, false);
/* 1166 */     i advancement = new i(FAKE, null, displayInfo, null, EMPTY_MAP, (String[][])null);
/* 1167 */     bjy toast = new bjy(advancement);
/* 1168 */     this.mc.ao().a((bkb)toast);
/*      */   }
/*      */ 
/*      */   
/*      */   private void displayRecipeToast(final aip itemStack) {
/* 1173 */     bjz.a(this.mc.ao(), new akt()
/*      */         {
/*      */           
/*      */           public boolean a(afy inv, amu worldIn)
/*      */           {
/* 1178 */             return false;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public fi<aip> b(afy inv) {
/* 1184 */             return null;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public aip b() {
/* 1190 */             return itemStack;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public aip a(afy inv) {
/* 1196 */             return itemStack;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean a(int width, int height) {
/* 1202 */             return false;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   private void displaySystemToast(bka.a type, hh text1, hh text2) {
/* 1209 */     bka.a(this.mc.ao(), type, text1, text2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void displayTutorialToast(String icon, hh text1, hh text2, int ticks) {
/* 1215 */     bkd.a iconIn = (bkd.a)ScriptCore.fuzzyParseEnum((Enum)bkd.a.e, icon, new String[] { "key", "mouse", "tree", "recipe|book", "wood|plank" });
/* 1216 */     TickableToast toast = new TickableToast(iconIn, text1, text2, Math.max(5, ticks));
/* 1217 */     this.toasts.add(toast);
/* 1218 */     this.mc.ao().a((bkb)toast);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionClearToasts(boolean userOnly) {
/* 1224 */     if (userOnly) {
/*      */       
/* 1226 */       for (TickableToast toast : this.toasts)
/*      */       {
/* 1228 */         toast.clear();
/*      */       }
/*      */       
/* 1231 */       this.toasts.clear();
/*      */       
/*      */       return;
/*      */     } 
/* 1235 */     this.toasts.clear();
/* 1236 */     this.mc.ao().a();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionPlaySoundEffect(String soundName, qg category, float volume) {
/*      */     try {
/* 1249 */       Matcher soundPatternMatcher = PATTERN_SOUNDNAME.matcher(soundName);
/*      */       
/* 1251 */       if (soundPatternMatcher.matches())
/*      */       {
/* 1253 */         nf soundLocation = new nf(soundName);
/*      */         
/* 1255 */         String topLevel = soundPatternMatcher.group(1);
/* 1256 */         String soundPath = soundPatternMatcher.group(2);
/*      */         
/* 1258 */         if (topLevel.equals(PlaySoundResourcePack.CUSTOM_SOUND_PREFIX))
/*      */         {
/* 1260 */           soundLocation = initialiseCustomSound(soundName, soundPath);
/*      */         }
/*      */         
/* 1263 */         qe event = new qe(soundLocation);
/* 1264 */         Game.playSoundFX(event, category, volume, 1.0F);
/*      */       }
/*      */     
/* 1267 */     } catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */   
/*      */   private nf initialiseCustomSound(String soundName, String soundPath) {
/* 1272 */     String domain = getSoundResourceNamespace();
/* 1273 */     System.err.printf(">> %s:%s\n", new Object[] { domain, soundName });
/* 1274 */     nf soundLocation = new nf(domain, soundName);
/*      */     
/* 1276 */     if (!this.customSounds.contains(soundName)) {
/*      */       
/* 1278 */       soundPath = soundPath.replaceAll("\\.", "/").substring(1);
/* 1279 */       File soundFile = this.playSoundResourcePack.getResourceFile(soundPath);
/*      */       
/* 1281 */       if (soundFile.exists()) {
/*      */         
/* 1283 */         cgq sound = new cgq(this.playSoundResourcePack.getResourceLocation(soundPath), 1.0F, 1.0F, 1, cgq.a.a, false);
/* 1284 */         cgr soundList = new cgr((List)ImmutableList.of(sound), false, null);
/* 1285 */         ((ISoundHandler)this.mc.U()).addSound(soundLocation, soundList);
/*      */       } 
/*      */       
/* 1288 */       this.customSounds.add(soundName);
/*      */     } 
/*      */     
/* 1291 */     return soundLocation;
/*      */   }
/*      */ 
/*      */   
/*      */   private int getIndexOfResourcePack(List<ceu.a> resourcePackList, String resourcePackName) {
/* 1296 */     if ("default".equalsIgnoreCase(resourcePackName))
/*      */     {
/* 1298 */       return -1;
/*      */     }
/*      */     
/* 1301 */     int resourcePackIndex = 0;
/*      */     
/* 1303 */     for (ceu.a resourcePack : resourcePackList) {
/*      */       
/* 1305 */       if (resourcePack.d().equalsIgnoreCase(resourcePackName))
/*      */       {
/* 1307 */         return resourcePackIndex;
/*      */       }
/*      */       
/* 1310 */       resourcePackIndex++;
/*      */     } 
/*      */     
/* 1313 */     resourcePackIndex = 0;
/* 1314 */     resourcePackName = resourcePackName.toLowerCase();
/*      */     
/* 1316 */     for (ceu.a resourcePack : resourcePackList) {
/*      */       
/* 1318 */       if (resourcePack.d().toLowerCase().contains(resourcePackName))
/*      */       {
/* 1320 */         return resourcePackIndex;
/*      */       }
/*      */       
/* 1323 */       resourcePackIndex++;
/*      */     } 
/*      */     
/* 1326 */     return -1;
/*      */   }
/*      */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\ScriptActionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */