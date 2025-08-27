/*     */ package net.eq2online.macros.core.settings;
/*     */ 
/*     */ import aed;
/*     */ import bib;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.SpamFilter;
/*     */ import net.eq2online.macros.gui.overlay.OverrideOverlay;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
/*     */ import net.eq2online.macros.scripting.api.ISettings;
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
/*     */ public final class Settings
/*     */   extends SettingsBase
/*     */   implements ISettings
/*     */ {
/*     */   public static final int MAX_CHAT_LENGTH = 256;
/*     */   private static final String DEFAULT_CONFIG_DIR = "/liteconfig/common/macros/";
/*     */   private final Macros macros;
/*     */   private final bib mc;
/*  35 */   private String macrosDirName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   public final Set<Integer> reservedKeys = new HashSet<>(); @Setting("compiler.flags")
/*     */   @Comment("Compiler flags, CASE SENSITIVE. Only change this if you know what you're doing")
/*  42 */   public String compilerFlags = "";
/*     */ 
/*     */   
/*     */   @Setting("input.compatibilitymode.enabled")
/*     */   @Comment("Enable the compatibility mode key interception. Use this if the enhanced key capture mode is causing issues with other mods.")
/*     */   public boolean compatibilityMode = false;
/*     */   
/*     */   @Setting("input.deepinjection.disabled")
/*     */   @Comment("Disable the deep injection proxy. This proxy enables more reliable key event injection but can cause the game to crash with certain mods.")
/*     */   public boolean disableDeepInjection = false;
/*     */   
/*     */   @Setting("gui.bindingmode")
/*     */   @Comment("NORMAL, DIRECT or DISABLED. Configures the action of the MACRO ACTIVATE key binding")
/*  55 */   public InputHandler.BindingComboMode bindingMode = InputHandler.BindingComboMode.NORMAL;
/*     */ 
/*     */   
/*     */   @Setting("configs.enable.friends")
/*     */   @Comment("Enable per-config friends list, setting this to 0 uses a single list for all configurations")
/*     */   public boolean configsForFriends = false;
/*     */   
/*     */   @Setting("configs.enable.homes")
/*     */   @Comment("Enable per-config homes list, setting this to 0 uses a single list for all configurations")
/*     */   public boolean configsForHomes = false;
/*     */   
/*     */   @Setting("configs.enable.towns")
/*     */   @Comment("Enable per-config towns list, setting this to 0 uses a single list for all configurations")
/*     */   public boolean configsForTowns = false;
/*     */   
/*     */   @Setting("configs.enable.warps")
/*     */   @Comment("Enable per-config warps list, setting this to 0 uses a single list for all configurations")
/*     */   public boolean configsForWarps = false;
/*     */   
/*     */   @Setting("configs.enable.places")
/*     */   @Comment("Enable per-config places list, setting this to 0 uses a single list for all configurations")
/*     */   public boolean configsForPlaces = false;
/*     */   
/*     */   @Setting("configs.enable.presets")
/*     */   @Comment("Enable per-config presets list, setting this to 0 uses a single list for all configurations")
/*     */   public boolean configsForPresets = false;
/*     */   
/*     */   @Setting("discover.enabled")
/*     */   public boolean enableAutoDiscovery = true;
/*     */   
/*     */   @Setting("config.autoswitch")
/*     */   @Comment("Enable automatically switching to configurations which match the server address upon joining a new game")
/*     */   public boolean enableAutoConfigSwitch = true;
/*     */   
/*     */   @Setting("override.enabled")
/*     */   @Comment("Enable the MACRO OVERRIDE function")
/*     */   public boolean enableOverride = true;
/*     */   
/*     */   @Setting("override.chatgui.enabled")
/*     */   @Comment("Enable the override function when in the chat GUI, disable if using CTRL")
/*     */   public boolean enableOverrideChat = true;
/*     */   
/*     */   @Setting("override.commandblockgui.enabled")
/*     */   @Comment("Enable the override function when in the command block GUI, disable if using CTRL")
/*     */   public boolean enableOverrideCmdBlock = true;
/*     */   
/*     */   @Setting("runstatus.enabled")
/*     */   @Comment("Enable the 'running macros' display")
/*     */   public boolean enableStatus = true;
/*     */   
/*     */   @Setting("debug.enabled")
/*     */   @Comment("Enables on-screen debugging information to help diagnose problems with the mod")
/*     */   public boolean enableDebug = false;
/*     */   
/*     */   @Setting("debug.environment.enabled")
/*     */   @Comment("Displays all environment variables on the HUD when debugging is enabled and the minecraft debug info is visible. WARNING THIS CAN DESTROY YOUR FRAMERATE, USE WITH CAUTION")
/*     */   public boolean debugEnvironment = false;
/*     */   
/*     */   @Setting("debug.environment.keys")
/*     */   @Comment("Includes all KEY_ environment variables in the environment variable display")
/*     */   public boolean debugEnvKeys = false;
/*     */   
/*     */   @Setting("gui.simple")
/*     */   public boolean simpleGui = false;
/*     */   
/*     */   @Setting("gui.bindafteredit")
/*     */   @Comment("Pressing ENTER or ESC in the MACRO EDIT screen will return to the MACRO BIND screen")
/*     */   public boolean alwaysGoBack = false;
/*     */   
/*     */   @Setting("gui.optionsfirst")
/*     */   public boolean defaultToOptions = false;
/*     */   
/*     */   @Setting("overlay.configpopup.enabled")
/*     */   @Comment("Enable the config-switched popup when changing configs and imports")
/*     */   public boolean enableConfigOverlay = true;
/*     */   
/*     */   @Setting("colourcodeformat")
/*     */   @Comment("Set this to the colour code format used by your server")
/* 133 */   public String colourCodeFormat = "&%s";
/*     */   
/*     */   @Setting("place.coordsformat")
/*     */   @Comment("Uses standard java formatting string, param 1 is X, 2 is Y and 3 is Z")
/* 137 */   public String coordsFormat = "%1$s %2$s %3$s";
/*     */   @Setting("colourcodehelperkey")
/*     */   @Comment("Use 0 to disable or -1 to use the MACRO OVERRIDE key")
/*     */   @Range(min = -1, max = 255)
/* 141 */   private int colourCodeHelperKey = 0;
/*     */ 
/*     */   
/*     */   @Setting("onlineuser.trimchars.start")
/*     */   @Comment("Number of characters to trim from the START of online user names")
/* 146 */   public int trimCharsUserListStart = 0;
/*     */   
/*     */   @Setting("onlineuser.trimchars.end")
/*     */   @Comment("Number of characters to trim from the END of online user names")
/* 150 */   public int trimCharsUserListEnd = 0;
/*     */   
/*     */   @Setting("onlineuser.includeself")
/*     */   @Comment("Include self in the 'online users' list ($$u)")
/*     */   public boolean includeSelf = true;
/*     */   
/*     */   @Setting("gui.animation")
/*     */   @Comment("Enable the slide animation in the macro binding GUI")
/*     */   public boolean enableGuiAnimation = true;
/*     */   
/*     */   @Setting("gui.rememberpage")
/*     */   @Comment("Remember which binding page was used last when opening the binding GUI, otherwise always starts with the KEYS screen")
/*     */   public boolean rememberBindPage = true;
/*     */   
/*     */   @Setting("output.enablehistory")
/*     */   @Comment("Saves outbound chat messages generated by the mod to the local chat history. Defaults to false")
/*     */   public boolean chatHistory = false;
/*     */   
/*     */   @Setting("editor.minimiseprompt")
/*     */   @Comment("Set which action the editor will take when the minimise button is clicked. Valid options are \"save\", \"nosave\" or blank to always prompt")
/* 170 */   public String editorMinimisePromptAction = "";
/*     */ 
/*     */   
/*     */   @Setting("gui.showslotids")
/*     */   @Comment("Show the slot IDs when hovering over container slots. Useful if you need to figure out which slots are which")
/*     */   public boolean showSlotInfo = false;
/*     */ 
/*     */   
/*     */   @Setting("gui.showlargebuttons")
/*     */   @Comment("True to display the old style large copy, move and delete buttons in the binding screen")
/*     */   public boolean drawLargeEditorButtons = false;
/*     */   
/*     */   @Setting("gui.autoscale")
/*     */   @Comment("True if the keyboard (and event) layouts should be scaled to fit the screen rather than remaining a fixed size")
/*     */   public boolean autoScaleKeyboard = false;
/*     */   
/*     */   @Setting("overlay.craftingstatus.enabled")
/*     */   @Comment("Enable the auto-crafting status display")
/*     */   public boolean showCraftingStatus = true;
/*     */   
/*     */   @Setting("guiparams.chathistory")
/*     */   @Comment("Show the chat history in the 'Macro Parameter' screens")
/*     */   public boolean showChatInParamScreen = true;
/*     */   
/*     */   @Setting("floodprotection.enabled")
/*     */   public boolean spamFilterEnabled = true;
/*     */   
/*     */   @Setting("floodprotection.style")
/* 198 */   public SpamFilter.FilterStyle spamFilterStyle = SpamFilter.FilterStyle.QUEUE;
/*     */   
/*     */   @Setting("floodprotection.queuesize")
/* 201 */   public int spamFilterQueueSize = 32;
/*     */   
/*     */   @Setting("floodprotection.ignorecommands")
/*     */   public boolean spamFilterIgnoreCommands = false;
/*     */   @Setting("override.simplepopup")
/*     */   @Comment("True to show only the \"simple\" MACRO OVERRIDE popup instead of the full prompt bar")
/* 207 */   public int simpleOverridePopup = 1;
/*     */   
/*     */   @Setting("script.warn.permissions.enabled")
/*     */   @Comment("True if the script engine should generate warnings for blocked script actions")
/*     */   public boolean generatePermissionsWarnings = false;
/*     */   
/*     */   @Setting("script.trace")
/*     */   public boolean scriptTrace = false;
/*     */   
/*     */   @Setting("editor.help.enabled")
/*     */   @Comment("True if the text editor should display context-sensitive help popups")
/*     */   public boolean showTextEditorHelp = true;
/*     */   
/*     */   @Setting("editor.colours.dark")
/*     */   @Comment("True if the text editor should use dark colour scheme instead of the default")
/*     */   public boolean useDarkEditorColours = false;
/*     */   
/*     */   @Setting("editor.syntax.enabled")
/*     */   @Comment("True if the text editor should enable syntax highlighting")
/*     */   public boolean showTextEditorSyntax = true;
/*     */   
/*     */   @Setting("chatgui.enabled")
/*     */   @Comment("True to enable custom (designable) GUI in the chat screen")
/*     */   public boolean enableButtonsOnChatGui = true;
/*     */   
/*     */   @Setting("gui.textboxhighlight.enabled")
/*     */   public boolean enableHighlightTextFields = true;
/*     */   
/*     */   @Setting("gui.configname.linkstosettings")
/*     */   @Comment("By default the config name banner now shows the configs dropdown, enable this setting to use the old behaviour")
/*     */   public boolean configNameLinksToSettings = false;
/*     */   
/*     */   @Setting("script.run.limitpertick")
/*     */   @Comment("Limit the number of instructions that can be processed per macro per tick")
/* 241 */   public int maxInstructionsPerTick = 100;
/*     */ 
/*     */   
/*     */   @Setting("script.run.maxtimesharems")
/* 245 */   public int maxExecutionTime = 100;
/*     */   
/*     */   @Setting("input.debounce.enabled")
/*     */   public boolean templateDebounceEnabled = true;
/*     */   @Setting("input.debounce.ticks")
/*     */   @Comment("Minimum number of game ticks that must elapse before a template can be retriggered")
/* 251 */   public int templateDebounceTicks = 10;
/*     */   
/*     */   @Setting("autocraft.failed.popup")
/*     */   public boolean showAutoCraftingFailedMessage = true;
/*     */   
/*     */   @Setting("layout.bindings.loadatstartup")
/*     */   @Comment("If true, then the custom GUI -> slot mappings are loaded from the config at startup, otherwise the defaults are always applied")
/*     */   public boolean loadLayoutBindings = true;
/*     */   
/*     */   @Setting("compiler.maxincludes")
/*     */   @Comment("Maximum number of file includes which can be inserted during a single compiler iteration")
/* 262 */   public int maxIncludes = 10;
/*     */   
/*     */   @Setting("filterablechat.enabled")
/*     */   @Comment("True to enable the filterable chat functionality")
/*     */   public boolean showFilterableChat = false;
/*     */   
/*     */   @Setting("script.craft.ratelimit")
/*     */   @Comment("Rate limit in ticks for auto-craft slot clicks, defaults to no delay (one action per tick) increasing this value waits the specifed number of ticks between slot actions")
/* 270 */   public int autoCraftRate = 0;
/*     */ 
/*     */   
/*     */   @Setting("gui.liveconditional")
/*     */   @Comment("Display the results of a conditional macro expression interactively within the conditional macro GUI")
/*     */   public boolean showConditionalStateInGui = true;
/*     */   
/*     */   @Setting("config.initial")
/*     */   @Comment("The name of the configuration to select at startup, still overridden by config.autoswitch if enabled")
/* 279 */   public String initialConfiguration = "";
/*     */ 
/*     */   
/*     */   @Setting("gui.bindonanypage")
/*     */   @Comment("Allows pressing a key to enter binding mode from any page of macro binding GUI, including events and buttons")
/*     */   public boolean bindIgnoresPage = false;
/*     */ 
/*     */   
/*     */   @Setting("repl.enabled")
/*     */   @Comment("Enable the Console button")
/*     */   public boolean enableRepl = false;
/*     */ 
/*     */   
/*     */   public Settings(Macros macros, bib minecraft) {
/* 293 */     this.macros = macros;
/* 294 */     this.mc = minecraft;
/* 295 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColourCodeHelperKey(int overrideKeyCode) {
/* 305 */     return (this.colourCodeHelperKey < 0) ? overrideKeyCode : this.colourCodeHelperKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCompilerFlagItem() {
/* 314 */     return this.compilerFlags.contains("i");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCompilerFlagTown() {
/* 323 */     return this.compilerFlags.contains("t");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCompilerFlagWarp() {
/* 332 */     return this.compilerFlags.contains("w");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCompilerFlagHome() {
/* 341 */     return this.compilerFlags.contains("h");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCompilerFlagUser() {
/* 350 */     return this.compilerFlags.contains("u");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCompilerFlagFriend() {
/* 359 */     return this.compilerFlags.contains("f");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMacrosDirName() {
/* 367 */     return (this.macrosDirName == null) ? "/liteconfig/common/macros/" : this.macrosDirName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClearSettings() {
/* 377 */     super.onClearSettings();
/*     */     
/* 379 */     this.reservedKeys.clear();
/*     */     
/* 381 */     this.reservedKeys.add(Integer.valueOf(59));
/*     */     
/* 383 */     this.reservedKeys.add(Integer.valueOf(61));
/* 384 */     this.reservedKeys.add(Integer.valueOf(62));
/*     */ 
/*     */     
/* 387 */     this.reservedKeys.add(Integer.valueOf(68));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoadSettings(ISettingsStore settings) {
/* 398 */     super.onLoadSettings(settings);
/*     */     
/* 400 */     this.colourCodeHelperKey = Math.min(Math.max(this.colourCodeHelperKey, -1), 255);
/*     */     
/* 402 */     if (settings.getSetting("chathidden", false))
/*     */     {
/* 404 */       this.mc.t.o = aed.b.c;
/*     */     }
/*     */     
/* 407 */     if (this.macrosDirName == null) {
/*     */       
/* 409 */       this.macrosDirName = settings.getSetting("macrosdirectory", "/liteconfig/common/macros/");
/* 410 */       Log.info("Using config dir {0}", new Object[] { this.macrosDirName });
/*     */     } 
/*     */     
/* 413 */     if (!"*".equals(settings.getSetting("input.keys.reserved", "*"))) {
/*     */       
/* 415 */       this.reservedKeys.clear();
/* 416 */       String[] strReservedKeys = settings.getSetting("input.keys.reserved", "59,61,68,87").split(",");
/*     */       
/* 418 */       for (String strReservedKey : strReservedKeys) {
/*     */ 
/*     */         
/*     */         try {
/* 422 */           int keyCode = Integer.parseInt(strReservedKey.trim());
/* 423 */           if (!this.reservedKeys.contains(Integer.valueOf(keyCode)))
/*     */           {
/* 425 */             this.reservedKeys.add(Integer.valueOf(keyCode));
/*     */           }
/*     */         }
/* 428 */         catch (NumberFormatException numberFormatException) {}
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
/*     */ 
/*     */   
/*     */   public void onSaveSettings(ISettingsStore settings) {
/* 442 */     String serializedReserved = "";
/*     */     
/* 444 */     String separator = "";
/* 445 */     for (Integer reservedKey : this.reservedKeys) {
/*     */       
/* 447 */       serializedReserved = serializedReserved + separator + String.valueOf(reservedKey);
/* 448 */       separator = ",";
/*     */     } 
/*     */     
/* 451 */     super.onSaveSettings(settings);
/*     */     
/* 453 */     settings.setSetting("macrosdirectory", getMacrosDirName());
/* 454 */     settings.setSetting("input.keys.reserved", serializedReserved);
/*     */     
/* 456 */     settings.setSettingComment("input.keys.reserved", "Keys which will require MACRO OVERRIDE to function as macro keys");
/* 457 */     settings.setSettingComment("macrosdirectory", "Base directory for ancilliary macro config files, relative to the Minecraft directory. Use . to use the Minecraft directory (old behaviour)");
/*     */   }
/*     */ 
/*     */   
/*     */   public void save() {
/* 462 */     this.macros.save();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getSetting(String settingName) {
/* 468 */     if ("simpleOverridePopup".equals(settingName))
/*     */     {
/* 470 */       return (T)OverrideOverlay.Style.values()[this.simpleOverridePopup];
/*     */     }
/*     */     
/* 473 */     if ("keyboardLayoutEditable".equals(settingName))
/*     */     {
/* 475 */       return (T)Boolean.valueOf(this.macros.getLayoutPanels().isKeyboardEditable());
/*     */     }
/*     */     
/* 478 */     SettingsBase.SettingField setting = getByName(settingName);
/* 479 */     if (setting != null)
/*     */     {
/* 481 */       return (T)setting.getValue(this);
/*     */     }
/*     */     
/* 484 */     return null;
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
/*     */   public <T> void setSetting(String settingName, T settingValue) {
/* 496 */     if ("simpleOverridePopup".equals(settingName)) {
/*     */       
/* 498 */       this.simpleOverridePopup = ((OverrideOverlay.Style)settingValue).ordinal();
/*     */       
/*     */       return;
/*     */     } 
/* 502 */     if ("keyboardLayoutEditable".equals(settingName)) {
/*     */       
/* 504 */       this.macros.getLayoutPanels().setKeyboardEditable(((Boolean)settingValue).booleanValue());
/*     */       
/*     */       return;
/*     */     } 
/* 508 */     this.macros.getEventManager().reloadEvents();
/*     */     
/* 510 */     SettingsBase.SettingField setting = getByName(settingName);
/* 511 */     if (setting != null)
/*     */     {
/* 513 */       setting.setValue(this, settingValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReservedKeyState(int key, boolean newState) {
/* 519 */     if (newState) {
/*     */       
/* 521 */       this.reservedKeys.add(Integer.valueOf(key));
/*     */     }
/*     */     else {
/*     */       
/* 525 */       this.reservedKeys.remove(Integer.valueOf(key));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void toggleReservedKeyState(int key) {
/* 531 */     if (this.reservedKeys.contains(Integer.valueOf(key))) {
/*     */       
/* 533 */       this.reservedKeys.remove(Integer.valueOf(key));
/*     */     }
/*     */     else {
/*     */       
/* 537 */       this.reservedKeys.add(Integer.valueOf(key));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReservedKey(int key) {
/* 544 */     return this.reservedKeys.contains(Integer.valueOf(key));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean generatePermissionsWarnings() {
/* 550 */     return this.generatePermissionsWarnings;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDebugEnabled() {
/* 556 */     return this.enableDebug;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\settings\Settings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */