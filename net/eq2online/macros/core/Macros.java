/*      */ package net.eq2online.macros.core;
/*      */ 
/*      */ import bhy;
/*      */ import bib;
/*      */ import blk;
/*      */ import com.google.common.base.Strings;
/*      */ import com.mumfrey.liteloader.core.LiteLoader;
/*      */ import g;
/*      */ import hb;
/*      */ import java.io.File;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.ArrayList;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import net.eq2online.console.Log;
/*      */ import net.eq2online.macros.AutoDiscoveryHandler;
/*      */ import net.eq2online.macros.core.executive.MacroActionContext;
/*      */ import net.eq2online.macros.core.executive.interfaces.IMacroHost;
/*      */ import net.eq2online.macros.core.handler.ChatHandler;
/*      */ import net.eq2online.macros.core.handler.LocalisationHandler;
/*      */ import net.eq2online.macros.core.handler.SettingsHandler;
/*      */ import net.eq2online.macros.core.settings.MacroStorage;
/*      */ import net.eq2online.macros.core.settings.Settings;
/*      */ import net.eq2online.macros.event.MacroEventDispatcherBuiltin;
/*      */ import net.eq2online.macros.event.MacroEventManager;
/*      */ import net.eq2online.macros.event.MacroEventProviderBuiltin;
/*      */ import net.eq2online.macros.gui.designable.LayoutManager;
/*      */ import net.eq2online.macros.gui.helpers.ListProvider;
/*      */ import net.eq2online.macros.gui.hook.CustomScreenManager;
/*      */ import net.eq2online.macros.gui.interfaces.IMinimisable;
/*      */ import net.eq2online.macros.gui.layout.PanelManager;
/*      */ import net.eq2online.macros.gui.repl.ReplConsoleHistory;
/*      */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*      */ import net.eq2online.macros.gui.skins.UserSkinHandler;
/*      */ import net.eq2online.macros.gui.thumbnail.ThumbnailHandler;
/*      */ import net.eq2online.macros.input.InputHandler;
/*      */ import net.eq2online.macros.input.InputHandlerInjector;
/*      */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*      */ import net.eq2online.macros.interfaces.IObserver;
/*      */ import net.eq2online.macros.interfaces.ISettingsObserver;
/*      */ import net.eq2online.macros.interfaces.ISettingsStore;
/*      */ import net.eq2online.macros.scripting.ActionParser;
/*      */ import net.eq2online.macros.scripting.IActionFilter;
/*      */ import net.eq2online.macros.scripting.IDocumentor;
/*      */ import net.eq2online.macros.scripting.IErrorLogger;
/*      */ import net.eq2online.macros.scripting.ModuleLoader;
/*      */ import net.eq2online.macros.scripting.ScriptActionProvider;
/*      */ import net.eq2online.macros.scripting.api.IMacro;
/*      */ import net.eq2online.macros.scripting.api.IMacroEngine;
/*      */ import net.eq2online.macros.scripting.api.IMacroEventManager;
/*      */ import net.eq2online.macros.scripting.api.IMacroEventProvider;
/*      */ import net.eq2online.macros.scripting.api.IMacroTemplate;
/*      */ import net.eq2online.macros.scripting.api.IScriptAction;
/*      */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*      */ import net.eq2online.macros.scripting.api.IScriptParser;
/*      */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*      */ import net.eq2online.macros.scripting.crafting.AutoCraftingManager;
/*      */ import net.eq2online.macros.scripting.docs.Documentor;
/*      */ import net.eq2online.macros.scripting.exceptions.ScriptException;
/*      */ import net.eq2online.macros.scripting.parser.ActionParserAction;
/*      */ import net.eq2online.macros.scripting.parser.ActionParserAssignment;
/*      */ import net.eq2online.macros.scripting.parser.ActionParserDirective;
/*      */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*      */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*      */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*      */ import net.eq2online.macros.scripting.parser.ScriptParser;
/*      */ import net.eq2online.macros.struct.ItemInfo;
/*      */ import net.eq2online.util.Game;
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
/*      */ public class Macros
/*      */   extends MacroStorage
/*      */   implements IErrorLogger, IMacroEngine, ISettingsObserver, IMacroHost
/*      */ {
/*      */   private static Macros instance;
/*      */   private final MacroModCore mod;
/*      */   private final SettingsHandler settingsHandler;
/*      */   private final Settings settings;
/*   91 */   private final List<IMacro> executingMacros = new ArrayList<>();
/*      */   
/*   93 */   private final List<IMacro> pendingAdditions = new ArrayList<>();
/*      */   
/*   95 */   private final List<IMacro> pendingRemovals = new ArrayList<>();
/*      */   
/*   97 */   private final Object executingMacrosLock = new Object();
/*      */ 
/*      */ 
/*      */   
/*      */   private final SpamFilter spamFilter;
/*      */ 
/*      */ 
/*      */   
/*      */   private final MacroEventManager eventManager;
/*      */ 
/*      */ 
/*      */   
/*      */   private final LayoutManager layoutManager;
/*      */ 
/*      */   
/*      */   private final PanelManager panelManager;
/*      */ 
/*      */   
/*      */   private final MacroEventProviderBuiltin eventProvider;
/*      */ 
/*      */   
/*      */   private final MacroEventDispatcherBuiltin eventDispatcher;
/*      */ 
/*      */   
/*      */   private InputHandler inputHandler;
/*      */ 
/*      */   
/*      */   private boolean insideRunLoop = false;
/*      */ 
/*      */   
/*      */   private boolean dirty = false;
/*      */ 
/*      */   
/*  130 */   private int dirtyCounter = 0;
/*      */   
/*      */   private List<IMacro.IMacroStatus> status;
/*      */   
/*      */   private final GuiPlaybackStatus playbackStatus;
/*      */   
/*      */   private final CustomScreenManager customScreenManager;
/*      */   
/*      */   private final AutoCraftingManager autoCraftingManager;
/*      */   
/*      */   private final ReplConsoleHistory replConsoleHistory;
/*      */   
/*  142 */   private final Map<MacroPlaybackType, MacroHighlighter> highlighters = new HashMap<>();
/*      */   
/*      */   private ListProvider listProvider;
/*      */   
/*      */   private IDocumentor documentor;
/*      */   
/*      */   private ScriptActionProvider scriptActionProvider;
/*      */   
/*  150 */   private Class<?> clChatFilterManager = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private IMinimisable minimised;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Macros(bib minecraft, MacroModCore mod) {
/*  161 */     super(minecraft, mod);
/*      */     
/*  163 */     this.mod = mod;
/*  164 */     instance = this;
/*      */     
/*  166 */     this.settingsHandler = new SettingsHandler(this, minecraft);
/*      */     
/*  168 */     this.settingsHandler.registerObserver((IObserver)this);
/*  169 */     this.settingsHandler.registerObserver((IObserver)ItemInfo.SETTINGS);
/*  170 */     this.settingsHandler.registerObserver((IObserver)SpamFilter.SETTINGS);
/*  171 */     this.settingsHandler.registerObserver((IObserver)Game.SETTINGS);
/*      */     
/*  173 */     this.settings = this.settingsHandler.getSettings();
/*      */     
/*  175 */     initDefaults();
/*      */     
/*  177 */     this.spamFilter = new SpamFilter(this, this.mc);
/*  178 */     this.eventManager = new MacroEventManager(this, this.mc);
/*  179 */     this.eventProvider = new MacroEventProviderBuiltin(this, this.mc);
/*  180 */     this.eventDispatcher = (MacroEventDispatcherBuiltin)this.eventProvider.getDispatcher();
/*  181 */     this.panelManager = new PanelManager(this, this.mc);
/*  182 */     this.panelManager.createDefaultPanels();
/*  183 */     this.layoutManager = new LayoutManager(this, this.mc);
/*  184 */     this.playbackStatus = new GuiPlaybackStatus(this, this.mc, this.spamFilter);
/*  185 */     this.customScreenManager = new CustomScreenManager(this, this.mc);
/*  186 */     this.autoCraftingManager = new AutoCraftingManager(this, this.mc);
/*  187 */     this.replConsoleHistory = new ReplConsoleHistory(this);
/*  188 */     this.listProvider = new ListProvider(this, this.mc);
/*      */     
/*  190 */     this.eventManager.registerEventProvider((IMacroEventProvider)this.eventProvider);
/*      */     
/*  192 */     initHighlighters();
/*      */   }
/*      */ 
/*      */   
/*      */   public static Macros getInstance() {
/*  197 */     return instance;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void prepare() {
/*  203 */     prepareScripting();
/*      */ 
/*      */     
/*  206 */     load();
/*  207 */     selectInitialConfig();
/*      */ 
/*      */     
/*  210 */     initScripting();
/*      */     
/*  212 */     MacroParams.initProviders(this, this.mc);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void prepareScripting() {
/*  218 */     this.documentor = Documentor.getInstance().loadXml("en_gb");
/*  219 */     this.scriptActionProvider = new ScriptActionProvider(this, this.mc);
/*      */     
/*  221 */     setupMainContext();
/*  222 */     setupChatFilterContext();
/*      */     
/*  224 */     ModuleLoader moduleLoader = new ModuleLoader(getMacrosDirectory());
/*  225 */     moduleLoader.loadModules(this);
/*      */   }
/*      */ 
/*      */   
/*      */   private void setupMainContext() {
/*  230 */     ScriptParser scriptParser = new ScriptParser(ScriptContext.MAIN);
/*  231 */     scriptParser.addActionParser((ActionParser)new ActionParserAction(ScriptContext.MAIN));
/*  232 */     scriptParser.addActionParser((ActionParser)new ActionParserDirective(ScriptContext.MAIN));
/*  233 */     scriptParser.addActionParser((ActionParser)new ActionParserAssignment(ScriptContext.MAIN));
/*      */     
/*  235 */     ScriptContext.MAIN.create((IScriptActionProvider)this.scriptActionProvider, (IMacroEventManager)this.eventManager, (IScriptParser)scriptParser, this, this.documentor, new IActionFilter()
/*      */         {
/*      */           
/*      */           public boolean pass(ScriptContext context, ScriptCore scriptCore, IScriptAction action)
/*      */           {
/*  240 */             return true;
/*      */           }
/*      */         },  MacroActionContext.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupChatFilterContext() {
/*      */     try {
/*  250 */       this.clChatFilterManager = Class.forName("net.eq2online.macros.modules.chatfilter.ChatFilterManager");
/*      */     }
/*  252 */     catch (Exception ex) {
/*      */       
/*  254 */       ex.printStackTrace();
/*      */     } 
/*      */     
/*  257 */     if (this.clChatFilterManager == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  262 */     ScriptParser scriptParser = new ScriptParser(ScriptContext.CHATFILTER);
/*  263 */     scriptParser.addActionParser((ActionParser)new ActionParserAction(ScriptContext.CHATFILTER));
/*  264 */     scriptParser.addActionParser((ActionParser)new ActionParserDirective(ScriptContext.CHATFILTER));
/*  265 */     scriptParser.addActionParser((ActionParser)new ActionParserAssignment(ScriptContext.CHATFILTER));
/*      */     
/*  267 */     ScriptContext.CHATFILTER.create((IScriptActionProvider)this.scriptActionProvider, (IMacroEventManager)this.eventManager, (IScriptParser)scriptParser, this, this.documentor, new IActionFilter()
/*      */         {
/*      */           
/*      */           public boolean pass(ScriptContext context, ScriptCore scriptCore, IScriptAction action)
/*      */           {
/*  272 */             return action.isThreadSafe();
/*      */           }
/*      */         },  MacroActionContext.class);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void initScripting() {
/*  280 */     this.scriptActionProvider.init();
/*  281 */     initMainContext();
/*  282 */     initChatFilterContext();
/*      */   }
/*      */ 
/*      */   
/*      */   private void initMainContext() {
/*  287 */     ScriptContext.MAIN.initActions();
/*      */   }
/*      */ 
/*      */   
/*      */   private void initChatFilterContext() {
/*  292 */     if (this.clChatFilterManager == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  297 */     ScriptContext.CHATFILTER.initActions();
/*      */ 
/*      */     
/*      */     try {
/*  301 */       Method getInstance = this.clChatFilterManager.getDeclaredMethod("getInstance", new Class[0]);
/*  302 */       getInstance.invoke(null, new Object[0]);
/*      */     }
/*  304 */     catch (Exception ex) {
/*      */       
/*  306 */       ex.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void selectInitialConfig() {
/*  312 */     String initialConfiguration = this.settings.initialConfiguration;
/*  313 */     if (!Strings.isNullOrEmpty(initialConfiguration) && hasConfig(initialConfiguration)) {
/*      */       
/*  315 */       this.activeConfig = initialConfiguration;
/*  316 */       this.overlayConfig = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void init(InputHandler inputHandler) {
/*  322 */     this.inputHandler = inputHandler;
/*  323 */     this.listProvider.init(this);
/*      */   }
/*      */ 
/*      */   
/*      */   void postInit() {
/*  328 */     this.panelManager.init();
/*  329 */     onLoad();
/*  330 */     save();
/*      */   }
/*      */ 
/*      */   
/*      */   private void initHighlighters() {
/*  335 */     for (MacroPlaybackType type : MacroPlaybackType.values())
/*      */     {
/*  337 */       this.highlighters.put(type, new MacroHighlighter(type));
/*      */     }
/*      */     
/*  340 */     this.highlighters.put(null, this.highlighters.get(MacroPlaybackType.ONESHOT));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void logError(String errorMessage) {
/*  346 */     this.mod.logStartupError(errorMessage);
/*      */   }
/*      */ 
/*      */   
/*      */   public SettingsHandler getSettingsHandler() {
/*  351 */     return this.settingsHandler;
/*      */   }
/*      */ 
/*      */   
/*      */   public Settings getSettings() {
/*  356 */     return this.settings;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public File getFile(String name) {
/*  362 */     return (name == null) ? null : new File(getMacrosDirectory(), name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File getMacrosDirectory() {
/*  373 */     File macrosDir = new File(LiteLoader.getGameDirectory(), this.settings.getMacrosDirName());
/*      */ 
/*      */     
/*      */     try {
/*  377 */       if (!macrosDir.exists())
/*      */       {
/*  379 */         macrosDir.mkdirs();
/*      */       }
/*      */       
/*  382 */       File soundsDir = new File(macrosDir, "sounds");
/*      */       
/*  384 */       if (!soundsDir.exists())
/*      */       {
/*  386 */         soundsDir.mkdirs();
/*      */       }
/*      */       
/*  389 */       File iconsDir = new File(macrosDir, "icons/custom");
/*      */       
/*  391 */       if (!iconsDir.exists())
/*      */       {
/*  393 */         iconsDir.mkdirs();
/*      */       }
/*      */     }
/*  396 */     catch (Exception exception) {}
/*      */     
/*  398 */     return macrosDir.exists() ? macrosDir : LiteLoader.getGameDirectory();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChatHandler getChatHandler() {
/*  403 */     return this.mod.getChatHandler();
/*      */   }
/*      */ 
/*      */   
/*      */   public InputHandler getInputHandler() {
/*  408 */     return this.inputHandler;
/*      */   }
/*      */ 
/*      */   
/*      */   public LocalisationHandler getLocalisationHandler() {
/*  413 */     return this.mod.getLocalisationHandler();
/*      */   }
/*      */ 
/*      */   
/*      */   public MacroEventManager getEventManager() {
/*  418 */     return this.eventManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public MacroEventDispatcherBuiltin getBuiltinEventDispatcher() {
/*  423 */     return this.eventDispatcher;
/*      */   }
/*      */ 
/*      */   
/*      */   public SpamFilter getSpamFilter() {
/*  428 */     return this.spamFilter;
/*      */   }
/*      */ 
/*      */   
/*      */   public LayoutManager getLayoutManager() {
/*  433 */     return this.layoutManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public PanelManager getLayoutPanels() {
/*  438 */     return this.panelManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public ListProvider getListProvider() {
/*  443 */     return this.listProvider;
/*      */   }
/*      */ 
/*      */   
/*      */   public MacroHighlighter getHighlighter(MacroPlaybackType type) {
/*  448 */     return this.highlighters.get(type);
/*      */   }
/*      */ 
/*      */   
/*      */   public UserSkinHandler getUserSkinHandler() {
/*  453 */     return this.mod.getUserSkinHandler();
/*      */   }
/*      */ 
/*      */   
/*      */   public ThumbnailHandler getThumbnailHandler() {
/*  458 */     return this.mod.getThumbnailHandler();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getSinglePlayerConfigName() {
/*  463 */     return this.singlePlayerConfigName;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getLastServerName() {
/*  468 */     return this.mod.getServerSwitchHandler().getLastServerName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AutoDiscoveryHandler getAutoDiscoveryHandler() {
/*  478 */     return this.mod.getAutoDiscoveryHandler();
/*      */   }
/*      */ 
/*      */   
/*      */   public GuiPlaybackStatus getPlaybackStatus() {
/*  483 */     return this.playbackStatus;
/*      */   }
/*      */ 
/*      */   
/*      */   public CustomScreenManager getCustomScreenManager() {
/*  488 */     return this.customScreenManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public AutoCraftingManager getAutoCraftingManager() {
/*  493 */     return this.autoCraftingManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public ReplConsoleHistory getReplConsoleHistory() {
/*  498 */     return this.replConsoleHistory;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMinimised(IMinimisable minimised) {
/*  503 */     this.minimised = minimised;
/*      */   }
/*      */ 
/*      */   
/*      */   public IMinimisable getMinimised(boolean clear) {
/*  508 */     IMinimisable minimised = this.minimised;
/*  509 */     if (clear)
/*      */     {
/*  511 */       this.minimised = null;
/*      */     }
/*  513 */     return minimised;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getNextFreeIndex(String macroName) {
/*  519 */     return MacroTriggerType.NONE.getNextFreeIndex(this, macroName);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected MacroTemplate createTemplate(int key) {
/*  525 */     return new MacroTemplate(this, this.mc, key);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMacroNameForId(int index) {
/*  531 */     return MacroTriggerType.getMacroName(this, index);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMacroNameWithPrefix(int index) {
/*  537 */     return MacroTriggerType.getMacroNameWithPrefix(this, index);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean getMacroShouldBeSaved(int index) {
/*  543 */     return MacroTriggerType.getMacroShouldBeSaved(this, index);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMacroIdForName(String name) {
/*  549 */     return MacroTriggerType.NONE.getIndexForName(this, name);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onClearSettings() {
/*  555 */     super.onClearSettings();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLoadSettings(ISettingsStore settings) {
/*  561 */     this.singlePlayerConfigName = settings.getSetting("singleplayerconfig", "");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onSaveSettings(ISettingsStore settings) {
/*  567 */     if (this.singlePlayerConfigName != null && this.singlePlayerConfigName.length() > 0)
/*      */     {
/*  569 */       settings.setSetting("singleplayerconfig", this.singlePlayerConfigName);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onClear() {
/*  576 */     this.settingsHandler.onClear();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onLoad() {
/*  582 */     this.settingsHandler.onLoad((ISettingsStore)this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onSave() {
/*  588 */     this.settingsHandler.onSave((ISettingsStore)this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onConfigChanged() {
/*  594 */     this.settingsHandler.onConfigChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onConfigAdded(String configName, boolean copy) {
/*  600 */     this.settingsHandler.onConfigAdded(configName, copy);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onConfigRemoved(String configName) {
/*  606 */     this.settingsHandler.onConfigRemoved(configName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void autoPlayMacro(int key, boolean overrideKeyDown) {
/*  617 */     if (canPlayMacro(key, overrideKeyDown)) playMacro(key, true, ScriptContext.MAIN, (IVariableProvider)null);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canPlayMacro(int key, boolean overrideKeyDown) {
/*  628 */     if (!overrideKeyDown) {
/*      */       
/*  630 */       MacroTemplate tpl = getMacroTemplateWithOverlay(key);
/*      */ 
/*      */       
/*  633 */       if (tpl.isEmpty()) return false;
/*      */       
/*  635 */       if (tpl.alwaysOverride && !this.inputHandler.isFallbackMode()) return true;
/*      */       
/*  637 */       if (isReservedKey(key)) return false;
/*      */     
/*      */     } 
/*  640 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMacroBound(int key, boolean useOverlay) {
/*  651 */     MacroTemplate tpl = getMacroTemplate(key, useOverlay);
/*  652 */     return (tpl != null && !tpl.isEmpty());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMacroGlobal(int key, boolean useOverlay) {
/*  663 */     MacroTemplate tpl = getMacroTemplate(key, useOverlay);
/*  664 */     return (tpl != null && tpl.global);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MacroPlaybackType getMacroType(int key, boolean useOverlay) {
/*  675 */     MacroTemplate tpl = getMacroTemplate(key, useOverlay);
/*  676 */     return (tpl != null) ? tpl.getPlaybackType() : MacroPlaybackType.ONESHOT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isKeyAlwaysOverridden(int key, boolean useOverlay, boolean check) {
/*  687 */     MacroTemplate tpl = getMacroTemplate(key, useOverlay);
/*  688 */     return (tpl != null && tpl.alwaysOverride && (check || this.mc.m == null));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isKeyOverlaid(int key) {
/*  699 */     if (this.overlayConfig != null && !this.overlayConfig.equals(this.activeConfig) && hasConfig(this.overlayConfig)) {
/*      */       
/*  701 */       MacroTemplate tpl = getMacroTemplate(this.overlayConfig, key, false);
/*  702 */       if (tpl != null && !tpl.global && !tpl.isEmpty()) return true;
/*      */     
/*      */     } 
/*  705 */     return false;
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
/*      */   public boolean isReservedKey(int key) {
/*  717 */     if (key > 254) return false;
/*      */ 
/*      */     
/*  720 */     if (key == InputHandler.KEY_ACTIVATE.j()) return true;
/*      */ 
/*      */     
/*  723 */     if (this.settings.isReservedKey(key)) return true;
/*      */ 
/*      */     
/*  726 */     if (key >= 250 && key <= 252)
/*      */     {
/*  728 */       key -= 350;
/*      */     }
/*      */     
/*  731 */     if (key == 248) key = 63; 
/*  732 */     if (key == 249) key = 64;
/*      */ 
/*      */     
/*  735 */     for (bhy bind : this.mc.t.as) {
/*      */       
/*  737 */       if (bind.j() == key)
/*      */       {
/*  739 */         return true;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  744 */     return false;
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
/*      */   public void playMacro(int key, boolean checkModifiers, ScriptContext scriptContext, IVariableProvider contextProvider) {
/*  757 */     playMacro(key, checkModifiers, scriptContext, contextProvider, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void playMacro(int key, boolean checkModifiers, ScriptContext scriptContext, IVariableProvider contextProvider, boolean synchronous) {
/*  763 */     MacroTemplate tpl = getMacroTemplate(key, true);
/*      */     
/*  765 */     if (tpl == null || tpl.isEmpty() || key == this.inputHandler.getOverrideKeyCode()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  771 */     IMacro macro = tpl.createInstance(checkModifiers, scriptContext.createActionContext(contextProvider));
/*      */ 
/*      */     
/*  774 */     if (macro == null)
/*      */       return; 
/*  776 */     playMacro(macro, synchronous);
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
/*      */   public void playMacro(IMacroTemplate template, boolean checkModifiers, ScriptContext scriptContext, IVariableProvider contextProvider) {
/*  789 */     playMacro(template, checkModifiers, scriptContext, contextProvider, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playMacro(IMacroTemplate template, boolean checkModifiers, ScriptContext scriptContext, IVariableProvider contextProvider, boolean synchronous) {
/*  796 */     IMacro macro = template.createInstance(checkModifiers, scriptContext.createActionContext(contextProvider));
/*      */ 
/*      */     
/*  799 */     if (macro == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  804 */     playMacro(macro, synchronous);
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
/*      */   protected void playMacro(IMacro macro, boolean synchronous) {
/*  817 */     if (macro instanceof IMacroParamTarget && ((IMacroParamTarget)macro).hasRemainingParams()) {
/*      */       
/*  819 */       this.mc.a((blk)new GuiMacroParam(this, this.mc, (IMacroParamTarget)macro));
/*      */       
/*      */       return;
/*      */     } 
/*  823 */     macro.setSynchronous(synchronous);
/*      */ 
/*      */     
/*      */     try {
/*  827 */       if (macro.play(true, true))
/*      */       {
/*  829 */         addRunningMacro(macro);
/*      */       }
/*  831 */       else if (macro.isDirty())
/*      */       {
/*      */         
/*  834 */         saveVariables();
/*      */       }
/*      */     
/*  837 */     } catch (ScriptException ex) {
/*      */       
/*  839 */       if (ex.getMessage() != null) Game.addChatMessage("§4Unhandled Exception " + ex.getMessage()); 
/*  840 */       Log.printStackTrace((Throwable)ex);
/*      */     }
/*  842 */     catch (Exception ex) {
/*      */       
/*  844 */       if (ex.getMessage() != null) Game.addChatMessage("§4Unhandled Exception " + ex.getMessage()); 
/*  845 */       Log.printStackTrace(ex);
/*      */       
/*  847 */       removeRunningMacro(macro);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void trace(int id, int pointer, String state, String action) {
/*  854 */     if (this.settings.scriptTrace)
/*      */     {
/*  856 */       Log.info("TRACE[{0}] PTR={1} ST={2} [{3}]", new Object[] { Integer.valueOf(id), Integer.valueOf(pointer), state, action });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void addScriptError(IScriptActionProvider provider, IMacro macro, String message) {
/*  863 */     provider.actionAddChatMessage("§c" + message);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addRunningMacro(IMacro macro) {
/*  871 */     synchronized (this.executingMacrosLock) {
/*      */ 
/*      */       
/*  874 */       if (this.insideRunLoop) {
/*      */         
/*  876 */         this.pendingAdditions.add(macro);
/*      */       }
/*      */       else {
/*      */         
/*  880 */         this.executingMacros.add(macro);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   boolean removeRunningMacro(int index) {
/*  887 */     synchronized (this.executingMacrosLock) {
/*      */       
/*  889 */       if (index > -1 && index < this.executingMacros.size()) {
/*      */         
/*  891 */         IMacro removeMacro = this.executingMacros.get(index);
/*  892 */         removeRunningMacro(removeMacro);
/*  893 */         if (removeMacro.isDirty())
/*      */         {
/*  895 */           saveVariables();
/*      */         }
/*  897 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  901 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeRunningMacro(IMacro macro) {
/*  909 */     synchronized (this.executingMacrosLock) {
/*      */       
/*  911 */       if (this.insideRunLoop) {
/*      */         
/*  913 */         this.pendingRemovals.add(macro);
/*      */       }
/*      */       else {
/*      */         
/*  917 */         this.executingMacros.remove(macro);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void terminateActiveMacros() {
/*  927 */     synchronized (this.executingMacrosLock) {
/*      */       
/*  929 */       if (this.executingMacros.size() > 0) {
/*      */         
/*  931 */         Log.info("Terminating {0} active macro(s)", new Object[] { Integer.valueOf(this.executingMacros.size()) });
/*  932 */         this.executingMacros.clear();
/*  933 */         this.pendingAdditions.clear();
/*  934 */         this.pendingRemovals.clear();
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
/*      */   public void terminateActiveMacros(ScriptContext context, int keyCode) {
/*  946 */     synchronized (this.executingMacrosLock) {
/*      */       
/*  948 */       for (IMacro macro : this.executingMacros) {
/*      */         
/*  950 */         if (macro.getContext().getScriptContext().equals(context) && macro.getID() == keyCode)
/*      */         {
/*  952 */           macro.kill();
/*      */         }
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
/*      */   
/*      */   public void sendEvent(String eventName, int priority, String... eventArgs) {
/*  967 */     this.eventManager.sendEvent(eventName, priority, eventArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearEvents() {
/*  975 */     this.eventManager.purgeQueue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTick(boolean clock) {
/*  983 */     this.mc.B.a("executive");
/*      */     
/*  985 */     this.mc.B.a("variables");
/*  986 */     IScriptActionProvider scriptActionProvider = ScriptContext.MAIN.getScriptActionProvider();
/*  987 */     scriptActionProvider.updateVariableProviders(clock);
/*  988 */     this.mc.B.b();
/*      */     
/*  990 */     if (clock) {
/*      */       
/*  992 */       this.status = null;
/*      */       
/*  994 */       this.mc.B.a("controls");
/*  995 */       this.layoutManager.onTick();
/*  996 */       this.mc.B.b();
/*      */ 
/*      */       
/*  999 */       this.mc.B.a("latent");
/* 1000 */       ScriptAction.onTickInGame(scriptActionProvider);
/* 1001 */       this.mc.B.b();
/*      */       
/* 1003 */       this.mc.B.a("templates");
/* 1004 */       for (int index = MacroTriggerType.KEY.getMinId(); index <= MacroTriggerType.KEY.getMaxId(); index++) {
/*      */         
/* 1006 */         if (this.baseTemplates[index] != null)
/*      */         {
/* 1008 */           this.baseTemplates[index].onTick();
/*      */         }
/*      */         
/* 1011 */         if (this.configs.size() > 0)
/*      */         {
/* 1013 */           for (MacroTemplate[] configTemplates : this.configs.values()) {
/*      */             
/* 1015 */             if (configTemplates[index] != null)
/*      */             {
/* 1017 */               configTemplates[index].onTick();
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/* 1022 */       this.mc.B.b();
/*      */ 
/*      */       
/* 1025 */       this.mc.B.a("dispatcher");
/* 1026 */       this.eventManager.onTick(this.mc);
/* 1027 */       this.mc.B.b();
/*      */ 
/*      */       
/* 1030 */       this.mc.B.a("spamfilter");
/* 1031 */       if (this.spamFilter != null) this.spamFilter.onTick(); 
/* 1032 */       this.mc.B.b();
/*      */       
/* 1034 */       if (this.dirtyCounter > 0)
/*      */       {
/* 1036 */         this.dirtyCounter--;
/*      */       }
/*      */       
/* 1039 */       this.replConsoleHistory.onTick();
/*      */     } 
/*      */     
/* 1042 */     this.mc.B.a("execute");
/*      */ 
/*      */     
/*      */     try {
/* 1046 */       synchronized (this.executingMacrosLock) {
/*      */         
/* 1048 */         this.insideRunLoop = true;
/*      */         
/* 1050 */         if (this.executingMacros.size() > 0)
/*      */         {
/* 1052 */           for (Iterator<IMacro> executingMacrosIterator = this.executingMacros.iterator(); executingMacrosIterator.hasNext(); ) {
/*      */             
/* 1054 */             IMacro macro = executingMacrosIterator.next();
/*      */             
/* 1056 */             if (macro.isDead()) {
/*      */               
/* 1058 */               this.pendingRemovals.add(macro);
/*      */               
/*      */               continue;
/*      */             } 
/*      */             
/*      */             try {
/* 1064 */               if (macro.play(InputHandler.isTriggerActive(macro.getID()), clock)) {
/*      */                 
/* 1066 */                 this.dirty |= macro.isDead();
/*      */                 
/*      */                 continue;
/*      */               } 
/* 1070 */               this.pendingRemovals.add(macro);
/*      */             
/*      */             }
/* 1073 */             catch (ScriptException ex) {
/*      */               
/* 1075 */               Log.printStackTrace((Throwable)ex);
/* 1076 */               Game.addChatMessage("" + ex.getMessage());
/*      */             }
/* 1078 */             catch (Exception ex) {
/*      */               
/* 1080 */               Log.printStackTrace(ex);
/* 1081 */               Game.addChatMessage((ex.getMessage() != null) ? ("" + ex.getMessage()) : ("" + ex.getClass().getSimpleName()));
/* 1082 */               this.pendingRemovals.add(macro);
/*      */             } 
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/* 1088 */         this.insideRunLoop = false;
/*      */         
/* 1090 */         for (Iterator<IMacro> pendingRemovalsIterator = this.pendingRemovals.iterator(); pendingRemovalsIterator.hasNext(); ) {
/*      */           
/* 1092 */           this.executingMacros.remove(pendingRemovalsIterator.next());
/* 1093 */           pendingRemovalsIterator.remove();
/*      */         } 
/*      */         
/* 1096 */         for (Iterator<IMacro> pendingAdditionsIterator = this.pendingAdditions.iterator(); pendingAdditionsIterator.hasNext(); )
/*      */         {
/* 1098 */           this.executingMacros.add(pendingAdditionsIterator.next());
/* 1099 */           pendingAdditionsIterator.remove();
/*      */         }
/*      */       
/*      */       } 
/* 1103 */     } catch (ConcurrentModificationException ex) {
/*      */       
/* 1105 */       Log.printStackTrace(ex);
/*      */     } 
/* 1107 */     this.mc.B.b();
/*      */ 
/*      */     
/* 1110 */     if (this.dirty && this.dirtyCounter == 0) {
/*      */       
/* 1112 */       saveVariables();
/* 1113 */       this.dirty = false;
/*      */ 
/*      */       
/* 1116 */       this.dirtyCounter = 100;
/*      */     } 
/*      */     
/* 1119 */     this.mc.B.b();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onJoinGame(String serverName) {
/* 1130 */     this.autoCraftingManager.clear();
/*      */     
/* 1132 */     this.spamFilter.reset();
/*      */     
/* 1134 */     this.mod.getAutoDiscoveryHandler().setSpamFilter(this.spamFilter);
/* 1135 */     this.eventDispatcher.onJoinGame(serverName, this.spamFilter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onServerConnect(hb netclienthandler) {
/* 1143 */     this.spamFilter.reset();
/*      */     
/* 1145 */     this.mod.getAutoDiscoveryHandler().setSpamFilter(this.spamFilter);
/* 1146 */     this.eventDispatcher.onServerConnect(netclienthandler, this.spamFilter);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onDisconnected() {
/* 1151 */     terminateActiveMacros();
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleAutoSwitch() {
/* 1156 */     this.mod.getServerSwitchHandler().handleAutoSwitch();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getExecutingMacroCount() {
/* 1167 */     synchronized (this.executingMacrosLock) {
/*      */       
/* 1169 */       return this.executingMacros.size();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public List<IMacro.IMacroStatus> getExecutingMacroStatus() {
/* 1176 */     if (this.status == null)
/*      */     {
/* 1178 */       synchronized (this.executingMacrosLock) {
/*      */         
/* 1180 */         this.status = new ArrayList<>(this.executingMacros.size());
/* 1181 */         for (IMacro macro : this.executingMacros)
/*      */         {
/* 1183 */           this.status.add(macro.getStatus());
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1188 */     return this.status;
/*      */   }
/*      */ 
/*      */   
/*      */   public void refreshPermissions() {
/* 1193 */     if (!InputHandlerInjector.q())
/*      */     {
/* 1195 */       this.listProvider = null;
/*      */     }
/*      */     
/* 1198 */     synchronized (this.executingMacrosLock) {
/*      */       
/* 1200 */       for (IMacro macro : this.executingMacros)
/*      */       {
/* 1202 */         macro.refreshPermissions();
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
/*      */ 
/*      */   
/*      */   public void dispatchChatMessage(String message, ScriptContext context) {
/*      */     try {
/* 1218 */       int cutoff = 246;
/*      */       
/* 1220 */       while (message.length() > 256) {
/*      */ 
/*      */         
/* 1223 */         int pos = message.substring(cutoff, 256).indexOf(" ") + cutoff;
/* 1224 */         if (pos == cutoff - 1) pos = 256;
/*      */         
/* 1226 */         this.spamFilter.sendChatMessage(replaceInvalidChars(message.substring(0, pos)), context);
/* 1227 */         message = message.substring(pos).trim();
/*      */       } 
/*      */       
/* 1230 */       this.spamFilter.sendChatMessage(replaceInvalidChars(message), context);
/*      */     }
/* 1232 */     catch (NullPointerException nullPointerException) {}
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
/*      */   public static String replaceInvalidChars(String message) {
/* 1244 */     if (message == null)
/*      */     {
/* 1246 */       return "";
/*      */     }
/*      */     
/* 1249 */     StringBuilder sb = new StringBuilder();
/* 1250 */     char[] chars = message.toCharArray();
/* 1251 */     for (int pos = 0; pos < chars.length; pos++) {
/*      */       
/* 1253 */       char charAt = chars[pos];
/* 1254 */       sb.append(g.a(charAt) ? charAt : 63);
/*      */     } 
/*      */     
/* 1257 */     return sb.toString();
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
/*      */   public static boolean isValidFileName(String textFileName) {
/* 1269 */     return !textFileName.startsWith(".");
/*      */   }
/*      */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\Macros.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */