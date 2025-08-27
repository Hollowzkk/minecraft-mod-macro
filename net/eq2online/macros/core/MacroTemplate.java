/*      */ package net.eq2online.macros.core;
/*      */ 
/*      */ import bib;
/*      */ import java.io.PrintWriter;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import net.eq2online.console.Log;
/*      */ import net.eq2online.macros.compatibility.I18n;
/*      */ import net.eq2online.macros.core.params.MacroParam;
/*      */ import net.eq2online.macros.core.params.providers.MacroParamProviderNamed;
/*      */ import net.eq2online.macros.core.params.providers.MacroParamProviderPreset;
/*      */ import net.eq2online.macros.core.settings.Settings;
/*      */ import net.eq2online.macros.input.InputHandler;
/*      */ import net.eq2online.macros.interfaces.IMacroParamStorage;
/*      */ import net.eq2online.macros.scripting.api.IMacro;
/*      */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*      */ import net.eq2online.macros.scripting.api.IMacroTemplate;
/*      */ import net.eq2online.macros.scripting.variable.VariableProviderArray;
/*      */ import net.eq2online.xml.Xml;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MacroTemplate
/*      */   extends VariableProviderArray
/*      */   implements IMacroTemplate, IMacroParamStorage
/*      */ {
/*      */   protected int id;
/*      */   protected final Macros macros;
/*      */   protected final bib mc;
/*      */   
/*      */   public class Options
/*      */   {
/*      */     private final MacroTriggerType triggerType;
/*   56 */     private MacroPlaybackType type = MacroPlaybackType.ONESHOT;
/*      */     
/*      */     public boolean control;
/*      */     public boolean alt;
/*      */     public boolean shift;
/*      */     public boolean inhibitParams;
/*      */     public boolean isGlobal;
/*      */     public boolean isOverride;
/*      */     public int repeatRateMs;
/*      */     
/*      */     Options(MacroTemplate template) {
/*   67 */       this.triggerType = template.getMacroType();
/*   68 */       this.type = template.getPlaybackType();
/*   69 */       this.control = template.requireControl;
/*   70 */       this.alt = template.requireAlt;
/*   71 */       this.shift = template.requireShift;
/*   72 */       this.inhibitParams = template.inhibitParamLoad;
/*   73 */       this.isGlobal = template.global;
/*   74 */       this.isOverride = template.alwaysOverride;
/*   75 */       this.repeatRateMs = template.getRepeatRate();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isType(MacroTriggerType type) {
/*   80 */       return (type == this.triggerType);
/*      */     }
/*      */ 
/*      */     
/*      */     public MacroTriggerType getMacroType() {
/*   85 */       return this.triggerType;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getName(int id) {
/*   90 */       return this.triggerType.getName(MacroTemplate.this.macros, id);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isPlaybackType(MacroPlaybackType playbackType) {
/*   95 */       return (this.type == playbackType);
/*      */     }
/*      */ 
/*      */     
/*      */     public MacroPlaybackType getPlaybackType() {
/*  100 */       return this.type;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPlaybackType(MacroPlaybackType playbackType) {
/*  105 */       this.type = playbackType;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isRequireControl() {
/*  110 */       return this.control;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setRequireControl(boolean requireControl) {
/*  115 */       this.control = requireControl;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isRequireAlt() {
/*  120 */       return this.alt;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setRequireAlt(boolean requireAlt) {
/*  125 */       this.alt = requireAlt;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isRequireShift() {
/*  130 */       return this.shift;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setRequireShift(boolean requireShift) {
/*  135 */       this.shift = requireShift;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isInhibitParamLoad() {
/*  140 */       return this.inhibitParams;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setInhibitParamLoad(boolean inhibitParamLoad) {
/*  145 */       this.inhibitParams = inhibitParamLoad;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isGlobal() {
/*  150 */       return this.isGlobal;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setGlobal(boolean global) {
/*  155 */       this.isGlobal = global;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isAlwaysOverride() {
/*  160 */       return this.isOverride;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setAlwaysOverride(boolean alwaysOverride) {
/*  165 */       this.isOverride = alwaysOverride;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setRepeatRate(String value) {
/*  170 */       this.repeatRateMs = MacroTemplate.tryParse(value, this.repeatRateMs);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  192 */   protected MacroPlaybackType playbackType = MacroPlaybackType.ONESHOT;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  198 */   protected MacroTriggerType macroType = MacroTriggerType.KEY;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  203 */   protected int repeatRate = 1000;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  208 */   protected String keyDownMacro = ""; protected String keyHeldMacro = ""; protected String keyUpMacro = ""; protected String macroCondition = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean requireControl;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean requireAlt;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean requireShift;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean alwaysOverride;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean inhibitParamLoad;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean global;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  244 */   private String parameter = "";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  249 */   private TreeMap<String, String> namedParameters = new TreeMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  254 */   private String item = "";
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firstItemOnly = false;
/*      */ 
/*      */   
/*  261 */   private String friend = "";
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firstFriendOnly = false;
/*      */ 
/*      */   
/*  268 */   private String onlineUser = "";
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firstOnlineUserOnly = false;
/*      */ 
/*      */   
/*  275 */   private String town = "";
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firstTownOnly = false;
/*      */ 
/*      */   
/*  282 */   private String warp = "";
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firstWarpOnly = false;
/*      */ 
/*      */   
/*  289 */   private String file = "";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  294 */   private String home = "";
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firstHomeOnly = false;
/*      */ 
/*      */   
/*  301 */   private String place = "";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  306 */   private String[] presetText = new String[] { "", "", "", "", "", "", "", "", "", "" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  311 */   private static Pattern presetTextConfigPattern = Pattern.compile("^PresetText\\[([0-9])\\]$");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  316 */   private static Pattern namedParameterConfigPattern = Pattern.compile("^NamedParam\\[([A-Za-z0-9]{1,32})\\]$");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  321 */   private static Pattern stringVariableConfigPattern = Pattern.compile("^String\\[([a-z]([a-z0-9_\\-]*))\\]$");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  326 */   public static final Pattern PATTERN_NAMEDPARAMETER = Pattern.compile("^[A-Za-z0-9]{1,32}$");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  331 */   public static final Pattern PATTERN_PROPERTY = Pattern.compile("^Property\\[([A-Za-z0-9]{1,32})\\]$");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  336 */   protected Map<String, Boolean> flags = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  341 */   protected Map<String, Integer> counters = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  346 */   protected Map<String, String> strings = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  351 */   protected TreeMap<String, String> properties = new TreeMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  356 */   protected int debounceTicks = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MacroTemplate(Macros macros, bib mc, int macroId) {
/*  367 */     this.macros = macros;
/*  368 */     this.mc = mc;
/*  369 */     this.id = macroId;
/*  370 */     this.macroType = MacroTriggerType.fromId(macroId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MacroTemplate(int macroId, MacroTemplate sourceTemplate) {
/*  381 */     this.id = macroId;
/*  382 */     this.macros = sourceTemplate.macros;
/*  383 */     this.mc = sourceTemplate.mc;
/*  384 */     this.playbackType = sourceTemplate.getPlaybackType();
/*  385 */     this.macroType = sourceTemplate.getMacroType();
/*  386 */     this.keyDownMacro = sourceTemplate.getKeyDownMacro();
/*  387 */     this.keyHeldMacro = sourceTemplate.getKeyHeldMacro();
/*  388 */     this.keyUpMacro = sourceTemplate.getKeyUpMacro();
/*  389 */     this.macroCondition = sourceTemplate.getMacroCondition();
/*  390 */     this.repeatRate = sourceTemplate.repeatRate;
/*  391 */     this.requireControl = sourceTemplate.requireControl;
/*  392 */     this.inhibitParamLoad = sourceTemplate.inhibitParamLoad;
/*  393 */     this.requireAlt = sourceTemplate.requireAlt;
/*  394 */     this.requireShift = sourceTemplate.requireShift;
/*  395 */     this.global = sourceTemplate.global;
/*  396 */     this.alwaysOverride = sourceTemplate.alwaysOverride;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onTick() {
/*  401 */     if (this.debounceTicks > 0)
/*      */     {
/*  403 */       this.debounceTicks--;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setID(int newID) {
/*  414 */     this.id = newID;
/*  415 */     this.macroType = MacroTriggerType.fromId(newID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Macros getMacroManager() {
/*  425 */     return this.macros;
/*      */   }
/*      */ 
/*      */   
/*      */   public Options getOptions() {
/*  430 */     return new Options(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setOptions(Options options) {
/*  435 */     setPlaybackType(options.getPlaybackType());
/*  436 */     this.requireControl = options.control;
/*  437 */     this.requireAlt = options.alt;
/*  438 */     this.requireShift = options.shift;
/*  439 */     this.inhibitParamLoad = options.inhibitParams;
/*  440 */     this.alwaysOverride = options.isOverride;
/*  441 */     this.repeatRate = options.repeatRateMs;
/*  442 */     this.global = options.isGlobal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getID() {
/*  452 */     return this.id;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRepeatRate() {
/*  458 */     return this.repeatRate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlaybackType(MacroPlaybackType playbackType) {
/*  466 */     if (this.macroType == MacroTriggerType.KEY || playbackType != MacroPlaybackType.KEYSTATE) {
/*      */       
/*  468 */       this.playbackType = playbackType;
/*      */     }
/*      */     else {
/*      */       
/*  472 */       this.playbackType = MacroPlaybackType.ONESHOT;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setKeyDownMacro(String macro) {
/*  483 */     this.keyDownMacro = macro;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setKeyHeldMacro(String macro) {
/*  493 */     this.keyHeldMacro = macro;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setKeyUpMacro(String macro) {
/*  503 */     this.keyUpMacro = macro;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMacroCondition(String condition) {
/*  513 */     if ("true".equalsIgnoreCase(condition) || "1".equals(condition)) {
/*      */       
/*  515 */       this.macroCondition = "";
/*      */     }
/*      */     else {
/*      */       
/*  519 */       this.macroCondition = condition;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MacroPlaybackType getPlaybackType() {
/*  529 */     return this.playbackType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MacroTriggerType getMacroType() {
/*  540 */     return this.macroType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyDownMacro() {
/*  551 */     return this.keyDownMacro;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyHeldMacro() {
/*  560 */     return this.keyHeldMacro;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyUpMacro() {
/*  569 */     return this.keyUpMacro;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMacroCondition() {
/*  575 */     return (this.macroCondition.length() > 0) ? this.macroCondition : "TRUE";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyDownMacroHoverText() {
/*  585 */     return (this.keyDownMacro.length() > 0) ? this.keyDownMacro : I18n.get("macro.hover.empty");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyHeldMacroHoverText() {
/*  595 */     return (this.keyHeldMacro.length() > 0) ? this.keyHeldMacro : I18n.get("macro.hover.empty");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyUpMacroHoverText() {
/*  605 */     return (this.keyUpMacro.length() > 0) ? this.keyUpMacro : I18n.get("macro.hover.empty");
/*      */   }
/*      */ 
/*      */   
/*      */   public String getConditionHovertext() {
/*  610 */     return "IF " + ((this.macroCondition.length() > 0) ? this.macroCondition : "TRUE");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  620 */     boolean dEmpty = this.keyDownMacro.isEmpty();
/*  621 */     boolean hEmpty = this.keyHeldMacro.isEmpty();
/*  622 */     boolean uEmpty = this.keyUpMacro.isEmpty();
/*      */     
/*  624 */     return ((this.playbackType == MacroPlaybackType.ONESHOT && dEmpty) || (this.playbackType == MacroPlaybackType.KEYSTATE && dEmpty && hEmpty && uEmpty) || (this.playbackType == MacroPlaybackType.CONDITIONAL && dEmpty && uEmpty));
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
/*      */   public String getModifiers() {
/*  639 */     String modifiers = this.requireControl ? "<CTRL> " : "";
/*  640 */     modifiers = modifiers + (this.requireAlt ? "<ALT> " : "");
/*  641 */     modifiers = modifiers + (this.requireShift ? "<SHIFT> " : "");
/*  642 */     return modifiers;
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
/*      */   public <TItem> String getStoredParam(MacroParamProvider<TItem> provider) {
/*      */     int index;
/*  657 */     if (this.inhibitParamLoad)
/*      */     {
/*  659 */       return "";
/*      */     }
/*      */     
/*  662 */     switch (provider.getType()) {
/*      */       case NORMAL:
/*  664 */         return this.parameter;
/*  665 */       case NAMED: return getNamedParameter(((MacroParamProviderNamed)provider).getNextNamedVar());
/*  666 */       case ITEM: return this.item;
/*  667 */       case FRIEND: return this.friend;
/*  668 */       case USER: return this.onlineUser;
/*  669 */       case TOWN: return this.town;
/*  670 */       case WARP: return this.warp;
/*  671 */       case HOME: return this.home;
/*  672 */       case PLACE: return this.place;
/*      */       case PRESET:
/*  674 */         index = ((MacroParamProviderPreset)provider).getNextPresetIndex();
/*  675 */         return (index > -1 && index < 10) ? this.presetText[index] : "";
/*  676 */       case FILE: return this.file;
/*  677 */     }  return "";
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
/*      */   public void setStoredParam(MacroParam.Type type, String param) {
/*  690 */     setStoredParam(type, 0, param);
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
/*      */   public void setStoredParam(MacroParam.Type type, int index, String param) {
/*  703 */     setStoredParam(type, index, "", param);
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
/*      */   public void setStoredParam(MacroParam.Type type, int index, String name, String param) {
/*  717 */     switch (type) {
/*      */       case NORMAL:
/*  719 */         this.parameter = param; break;
/*  720 */       case NAMED: setNamedParameter(name, param); break;
/*  721 */       case ITEM: this.item = param; break;
/*  722 */       case FRIEND: this.friend = param; break;
/*  723 */       case USER: this.onlineUser = param; break;
/*  724 */       case TOWN: this.town = param; break;
/*  725 */       case WARP: this.warp = param; break;
/*  726 */       case HOME: this.home = param; break;
/*  727 */       case PLACE: this.place = param; break;
/*  728 */       case PRESET: if (index > -1 && index < 10) this.presetText[index] = param; 
/*      */         break;
/*      */       default:
/*      */         return;
/*      */     } 
/*  733 */     if (type != MacroParam.Type.USER)
/*      */     {
/*  735 */       saveTemplates();
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
/*      */   public void setNamedParameter(String parameterName, String parameterValue) {
/*  747 */     if (parameterName.length() > 0 && PATTERN_NAMEDPARAMETER.matcher(parameterName).matches())
/*      */     {
/*  749 */       this.namedParameters.put(parameterName.toLowerCase(), parameterValue);
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
/*      */   public String getNamedParameter(String parameterName) {
/*  762 */     if (this.namedParameters.containsKey(parameterName.toLowerCase()))
/*      */     {
/*  764 */       return this.namedParameters.get(parameterName.toLowerCase());
/*      */     }
/*      */     
/*  767 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReplaceFirstOccurrenceOnly(MacroParam.Type type, boolean newValue) {
/*  778 */     switch (type) {
/*      */       case ITEM:
/*  780 */         this.firstItemOnly = newValue; break;
/*  781 */       case FRIEND: this.firstFriendOnly = newValue; break;
/*  782 */       case USER: this.firstOnlineUserOnly = newValue; break;
/*  783 */       case TOWN: this.firstTownOnly = newValue; break;
/*  784 */       case WARP: this.firstWarpOnly = newValue; break;
/*  785 */       case HOME: this.firstHomeOnly = newValue;
/*      */         break;
/*      */       default:
/*      */         return;
/*      */     } 
/*  790 */     saveTemplates();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldReplaceFirstOccurrenceOnly(MacroParam.Type type) {
/*  801 */     switch (type) {
/*      */       case ITEM:
/*  803 */         return this.firstItemOnly;
/*  804 */       case FRIEND: return this.firstFriendOnly;
/*  805 */       case USER: return this.firstOnlineUserOnly;
/*  806 */       case TOWN: return this.firstTownOnly;
/*  807 */       case WARP: return this.firstWarpOnly;
/*  808 */       case HOME: return this.firstHomeOnly;
/*  809 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void saveTemplates() {
/*  818 */     if (this.macros != null) this.macros.save();
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IMacro createInstance(boolean checkModifiers, IMacroActionContext context) {
/*  829 */     if (checkModifiers) {
/*      */       
/*  831 */       if (this.requireControl && !InputHandler.isControlDown()) return null; 
/*  832 */       if (this.requireAlt && !InputHandler.isAltDown()) return null; 
/*  833 */       if (this.requireShift && !InputHandler.isShiftDown()) return null;
/*      */     
/*      */     } 
/*  836 */     if (MacroTriggerType.KEY.supportsId(this.id)) {
/*      */       
/*  838 */       if (this.debounceTicks > 0)
/*      */       {
/*  840 */         return null;
/*      */       }
/*      */       
/*  843 */       Settings settings = this.macros.getSettings();
/*  844 */       this.debounceTicks = settings.templateDebounceEnabled ? settings.templateDebounceTicks : 1;
/*      */     } 
/*      */     
/*  847 */     return new Macro(this.macros, this.mc, this, this.id, this.playbackType, this.macroType, context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveTemplate(PrintWriter writer) {
/*  857 */     if (this.keyDownMacro.length() + this.keyHeldMacro.length() + this.keyUpMacro.length() + this.macroCondition.length() > 0) {
/*      */       
/*  859 */       writer.println("# " + MacroTriggerType.getMacroNameWithPrefix(this.macros, this.id));
/*      */       
/*  861 */       writer.println("Macro[" + this.id + "].Macro=" + this.keyDownMacro);
/*  862 */       if (this.keyHeldMacro.length() > 0) writer.println("Macro[" + this.id + "].OnKeyHeld=" + this.keyHeldMacro); 
/*  863 */       if (this.keyUpMacro.length() > 0) writer.println("Macro[" + this.id + "].OnKeyUp=" + this.keyUpMacro); 
/*  864 */       if (this.macroCondition.length() > 0) writer.println("Macro[" + this.id + "].Condition=" + this.macroCondition); 
/*  865 */       if (this.playbackType == MacroPlaybackType.KEYSTATE) writer.println("Macro[" + this.id + "].Mode=keystate"); 
/*  866 */       if (this.playbackType == MacroPlaybackType.CONDITIONAL) writer.println("Macro[" + this.id + "].Mode=conditional"); 
/*  867 */       if (this.repeatRate != 1000) writer.println("Macro[" + this.id + "].RepeatRate=" + this.repeatRate); 
/*  868 */       if (this.inhibitParamLoad) writer.println("Macro[" + this.id + "].Inhibit=1"); 
/*  869 */       if (this.requireControl) writer.println("Macro[" + this.id + "].Control=1"); 
/*  870 */       if (this.requireAlt) writer.println("Macro[" + this.id + "].Alt=1"); 
/*  871 */       if (this.requireShift) writer.println("Macro[" + this.id + "].Shift=1"); 
/*  872 */       if (this.global) writer.println("Macro[" + this.id + "].Global=1"); 
/*  873 */       if (this.alwaysOverride) writer.println("Macro[" + this.id + "].Override=1"); 
/*  874 */       if (this.parameter.length() > 0) writer.println("Macro[" + this.id + "].Param=" + this.parameter); 
/*  875 */       if (this.item.length() > 0) writer.println("Macro[" + this.id + "].Item=" + this.item); 
/*  876 */       if (this.friend.length() > 0) writer.println("Macro[" + this.id + "].Friend=" + this.friend); 
/*  877 */       if (this.town.length() > 0) writer.println("Macro[" + this.id + "].Town=" + this.town); 
/*  878 */       if (this.warp.length() > 0) writer.println("Macro[" + this.id + "].Warp=" + this.warp); 
/*  879 */       if (this.home.length() > 0) writer.println("Macro[" + this.id + "].Home=" + this.home); 
/*  880 */       if (this.place.length() > 0) writer.println("Macro[" + this.id + "].Place=" + this.place); 
/*  881 */       if (this.file.length() > 0) writer.println("Macro[" + this.id + "].File=" + this.file);
/*      */       
/*  883 */       String serialisedCompilerFlags = serialiseCompilerFlags();
/*  884 */       if (serialisedCompilerFlags.length() > 0)
/*      */       {
/*  886 */         writer.println("Macro[" + this.id + "].CompilerFlags=" + serialisedCompilerFlags);
/*      */       }
/*      */       
/*  889 */       for (int i = 0; i < 10; i++) {
/*      */         
/*  891 */         if (!"".equals(this.presetText[i]))
/*      */         {
/*  893 */           writer.println("Macro[" + this.id + "].PresetText[" + i + "]=" + this.presetText[i]);
/*      */         }
/*      */       } 
/*      */       
/*  897 */       for (Map.Entry<String, String> namedParam : this.namedParameters.entrySet()) {
/*      */         
/*  899 */         if (!"".equals(namedParam.getValue()))
/*      */         {
/*  901 */           writer.println("Macro[" + this.id + "].NamedParam[" + (String)namedParam.getKey() + "]=" + (String)namedParam.getValue());
/*      */         }
/*      */       } 
/*      */       
/*  905 */       writer.println();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveVariables(Document xml, Element templateNode) {
/*  916 */     for (Map.Entry<String, Boolean> flag : this.flags.entrySet()) {
/*      */       
/*  918 */       Element flagNode = xml.createElement("boolean");
/*  919 */       flagNode.setAttribute("key", flag.getKey());
/*  920 */       flagNode.setTextContent(((Boolean)flag.getValue()).booleanValue() ? "1" : "0");
/*  921 */       templateNode.appendChild(flagNode);
/*      */     } 
/*      */     
/*  924 */     for (Map.Entry<String, Integer> counter : this.counters.entrySet()) {
/*      */       
/*  926 */       Element counterNode = xml.createElement("int");
/*  927 */       counterNode.setAttribute("key", counter.getKey());
/*  928 */       counterNode.setTextContent(((Integer)counter.getValue()).toString());
/*  929 */       templateNode.appendChild(counterNode);
/*      */     } 
/*      */     
/*  932 */     for (Map.Entry<String, String> string : this.strings.entrySet()) {
/*      */       
/*  934 */       Element stringNode = xml.createElement("string");
/*  935 */       stringNode.setAttribute("key", string.getKey());
/*  936 */       stringNode.setTextContent(string.getValue());
/*  937 */       templateNode.appendChild(stringNode);
/*      */     } 
/*      */     
/*  940 */     super.saveVariables(xml, templateNode);
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
/*      */   public void loadFrom(String line, String key, String value) {
/*  953 */     if ("Macro".equalsIgnoreCase(key)) { this.keyDownMacro = value; }
/*  954 */     else if ("OnKeyHeld".equalsIgnoreCase(key)) { this.keyHeldMacro = value; }
/*  955 */     else if ("OnKeyUp".equalsIgnoreCase(key)) { this.keyUpMacro = value; }
/*  956 */     else if ("Condition".equalsIgnoreCase(key)) { this.macroCondition = value; }
/*  957 */     else if ("Mode".equalsIgnoreCase(key)) { this.playbackType = parsePlaybackType(value); }
/*  958 */     else if ("RepeatRate".equalsIgnoreCase(key)) { this.repeatRate = tryParse(value, 1000); }
/*  959 */     else if ("Inhibit".equalsIgnoreCase(key)) { this.inhibitParamLoad = "1".equals(value); }
/*  960 */     else if ("Control".equalsIgnoreCase(key)) { this.requireControl = "1".equals(value); }
/*  961 */     else if ("Alt".equalsIgnoreCase(key)) { this.requireAlt = "1".equals(value); }
/*  962 */     else if ("Shift".equalsIgnoreCase(key)) { this.requireShift = "1".equals(value); }
/*  963 */     else if ("Global".equalsIgnoreCase(key)) { this.global = "1".equals(value); }
/*  964 */     else if ("Override".equalsIgnoreCase(key)) { this.alwaysOverride = "1".equals(value); }
/*  965 */     else if ("Param".equalsIgnoreCase(key)) { this.parameter = value; }
/*  966 */     else if ("Item".equalsIgnoreCase(key)) { this.item = value; }
/*  967 */     else if ("Friend".equalsIgnoreCase(key)) { this.friend = value; }
/*  968 */     else if ("Town".equalsIgnoreCase(key)) { this.town = value; }
/*  969 */     else if ("Warp".equalsIgnoreCase(key)) { this.warp = value; }
/*  970 */     else if ("Home".equalsIgnoreCase(key)) { this.home = value; }
/*  971 */     else if ("Place".equalsIgnoreCase(key)) { this.place = value; }
/*  972 */     else if ("File".equalsIgnoreCase(key)) { this.file = value; }
/*  973 */     else if ("Flags".equalsIgnoreCase(key)) { deserialiseFlags(value); }
/*  974 */     else if ("Counters".equalsIgnoreCase(key)) { deserialiseCounters(value); }
/*  975 */     else if ("CompilerFlags".equalsIgnoreCase(key)) { deserialiseCompilerFlags(value);
/*      */        }
/*      */     
/*  978 */     else if (key.startsWith("PresetText"))
/*      */     
/*  980 */     { Matcher presetTextMatcher = presetTextConfigPattern.matcher(key);
/*  981 */       if (presetTextMatcher.matches())
/*      */       {
/*  983 */         int presetTextIndex = Integer.parseInt(presetTextMatcher.group(1));
/*  984 */         this.presetText[presetTextIndex] = value;
/*      */       }
/*      */        }
/*  987 */     else if (key.startsWith("NamedParam"))
/*      */     
/*  989 */     { Matcher namedParameterMatcher = namedParameterConfigPattern.matcher(key);
/*  990 */       if (namedParameterMatcher.matches())
/*      */       {
/*  992 */         this.namedParameters.put(namedParameterMatcher.group(1), value);
/*      */       } }
/*      */     
/*  995 */     else if (key.startsWith("String"))
/*      */     
/*  997 */     { Matcher stringVariableMatcher = stringVariableConfigPattern.matcher(key);
/*  998 */       if (stringVariableMatcher.matches())
/*      */       {
/* 1000 */         this.strings.put(stringVariableMatcher.group(1), value);
/*      */       } }
/*      */     
/* 1003 */     else if (this.macroType == MacroTriggerType.CONTROL)
/*      */     
/* 1005 */     { if (key.startsWith("Property"))
/*      */       {
/* 1007 */         Matcher propertyMatcher = PATTERN_PROPERTY.matcher(key);
/* 1008 */         if (propertyMatcher.matches())
/*      */         {
/* 1010 */           this.properties.put(propertyMatcher.group(1), value);
/*      */         }
/*      */       }
/*      */        }
/*      */     else
/*      */     
/* 1016 */     { Log.info("Unrecognised configuration directive for macro [" + this.id + "]: " + key); }
/*      */   
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
/*      */   public void loadVariables(Node templateNode) {
/* 1029 */     this.flags.clear();
/* 1030 */     this.counters.clear();
/* 1031 */     this.strings.clear();
/*      */     
/* 1033 */     for (Node boolNode : Xml.xmlNodes(templateNode, "boolean")) {
/*      */       
/* 1035 */       String flagName = Xml.xmlGetAttribute(boolNode, "key", "*");
/* 1036 */       if (!"*".equals(flagName) && ("1".equals(boolNode.getTextContent()) || "true".equalsIgnoreCase(boolNode.getTextContent())))
/*      */       {
/* 1038 */         setFlag(flagName);
/*      */       }
/*      */     } 
/*      */     
/* 1042 */     for (Node counterNode : Xml.xmlNodes(templateNode, "int")) {
/*      */       
/* 1044 */       String counterName = Xml.xmlGetAttribute(counterNode, "key", "*");
/* 1045 */       if (!"*".equals(counterName) && counterNode.getTextContent() != null)
/*      */       {
/* 1047 */         setCounter(counterName, tryParse(counterNode.getTextContent(), 0));
/*      */       }
/*      */     } 
/*      */     
/* 1051 */     for (Node stringNode : Xml.xmlNodes(templateNode, "string")) {
/*      */       
/* 1053 */       String stringName = Xml.xmlGetAttribute(stringNode, "key", "*");
/* 1054 */       if (!"*".equals(stringName) && stringNode.getTextContent() != null)
/*      */       {
/* 1056 */         setString(stringName, stringNode.getTextContent());
/*      */       }
/*      */     } 
/*      */     
/* 1060 */     super.loadVariables(templateNode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MacroPlaybackType parsePlaybackType(String value) {
/* 1069 */     if ("keystate".equals(value) && this.macroType == MacroTriggerType.KEY)
/*      */     {
/* 1071 */       return MacroPlaybackType.KEYSTATE;
/*      */     }
/* 1073 */     if ("conditional".equals(value))
/*      */     {
/* 1075 */       return MacroPlaybackType.CONDITIONAL;
/*      */     }
/*      */     
/* 1078 */     return MacroPlaybackType.ONESHOT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String serialiseCompilerFlags() {
/* 1086 */     StringBuilder compilerFlags = new StringBuilder();
/* 1087 */     if (this.firstItemOnly) compilerFlags.append("i"); 
/* 1088 */     if (this.firstTownOnly) compilerFlags.append("t"); 
/* 1089 */     if (this.firstWarpOnly) compilerFlags.append("w"); 
/* 1090 */     if (this.firstHomeOnly) compilerFlags.append("h"); 
/* 1091 */     if (this.firstOnlineUserOnly) compilerFlags.append("u"); 
/* 1092 */     if (this.firstFriendOnly) compilerFlags.append("f"); 
/* 1093 */     return compilerFlags.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void deserialiseCompilerFlags(String compilerFlags) {
/* 1101 */     compilerFlags = compilerFlags.toLowerCase();
/*      */     
/* 1103 */     this.firstItemOnly = compilerFlags.contains("i");
/* 1104 */     this.firstTownOnly = compilerFlags.contains("t");
/* 1105 */     this.firstWarpOnly = compilerFlags.contains("w");
/* 1106 */     this.firstHomeOnly = compilerFlags.contains("h");
/* 1107 */     this.firstOnlineUserOnly = compilerFlags.contains("u");
/* 1108 */     this.firstFriendOnly = compilerFlags.contains("f");
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
/*      */   public static int tryParse(String text, int defaultValue) {
/*      */     try {
/* 1123 */       return Integer.parseInt(text);
/*      */     }
/* 1125 */     catch (NumberFormatException ex) {
/*      */       
/* 1127 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deserialiseFlags(String serialisedFlags) {
/* 1138 */     String[] flagsList = serialisedFlags.split(",");
/*      */     
/* 1140 */     for (String flag : flagsList)
/*      */     {
/* 1142 */       setFlag(flag);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String serialiseFlags() {
/* 1153 */     StringBuilder serialisedFlags = new StringBuilder();
/* 1154 */     boolean append = false;
/*      */     
/* 1156 */     for (Map.Entry<String, Boolean> flag : this.flags.entrySet()) {
/*      */       
/* 1158 */       if (((Boolean)flag.getValue()).booleanValue()) {
/*      */         
/* 1160 */         if (append) serialisedFlags.append(","); 
/* 1161 */         serialisedFlags.append(flag.getKey());
/* 1162 */         append = true;
/*      */       } 
/*      */     } 
/*      */     
/* 1166 */     return serialisedFlags.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getFlag(String flag) {
/* 1176 */     if (this.flags.containsKey(flag.toLowerCase()))
/*      */     {
/* 1178 */       return ((Boolean)this.flags.get(flag.toLowerCase())).booleanValue();
/*      */     }
/*      */     
/* 1181 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlag(String flag, boolean value) {
/* 1191 */     this.flags.put(flag.toLowerCase(), Boolean.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlag(String flag) {
/* 1201 */     this.flags.put(flag.toLowerCase(), Boolean.valueOf(true));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unsetFlag(String flag) {
/* 1211 */     this.flags.put(flag.toLowerCase(), Boolean.valueOf(false));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deserialiseCounters(String serialisedCounters) {
/* 1221 */     String[] counterList = serialisedCounters.split(",");
/*      */     
/* 1223 */     for (String counter : counterList) {
/*      */       
/* 1225 */       Matcher matcher = Pattern.compile("^([^,=]+)=([\\d\\-]+)$").matcher(counter);
/*      */       
/* 1227 */       if (matcher.matches())
/*      */       {
/* 1229 */         setCounter(matcher.group(1), tryParse(matcher.group(2), 0));
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
/*      */   protected String serialiseCounters() {
/* 1242 */     StringBuilder serialisedCounters = new StringBuilder();
/* 1243 */     boolean append = false;
/*      */     
/* 1245 */     for (Map.Entry<String, Integer> counter : this.counters.entrySet()) {
/*      */       
/* 1247 */       if (append) serialisedCounters.append(","); 
/* 1248 */       serialisedCounters.append((String)counter.getKey() + "=" + ((Integer)counter.getValue()).toString());
/* 1249 */       append = true;
/*      */     } 
/*      */     
/* 1252 */     return serialisedCounters.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCounter(String counter) {
/* 1262 */     return this.counters.containsKey(counter) ? ((Integer)this.counters.get(counter)).intValue() : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCounter(String counter, int value) {
/* 1272 */     this.counters.put(counter, Integer.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unsetCounter(String counter) {
/* 1282 */     this.counters.remove(counter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void incrementCounter(String counter, int increment) {
/* 1292 */     int counterValue = getCounter(counter);
/* 1293 */     counterValue += increment;
/* 1294 */     setCounter(counter, counterValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void decrementCounter(String counter, int decrement) {
/* 1304 */     int counterValue = getCounter(counter);
/* 1305 */     counterValue -= decrement;
/* 1306 */     setCounter(counter, counterValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getString(String stringName) {
/* 1316 */     stringName = stringName.toLowerCase();
/* 1317 */     return this.strings.containsKey(stringName) ? this.strings.get(stringName) : "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setString(String stringName, String value) {
/* 1327 */     this.strings.put(stringName.toLowerCase(), sanitiseString(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unsetString(String stringName) {
/* 1337 */     this.strings.remove(stringName.toLowerCase());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateVariables(boolean clock) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getVariable(String variableName) {
/* 1356 */     if (variableName.startsWith("#") && this.counters.containsKey(variableName.substring(1)))
/*      */     {
/* 1358 */       return this.counters.get(variableName.substring(1));
/*      */     }
/*      */     
/* 1361 */     if (variableName.startsWith("&") && this.strings.containsKey(variableName.substring(1)))
/*      */     {
/* 1363 */       return this.strings.get(variableName.substring(1));
/*      */     }
/*      */     
/* 1366 */     if (this.flags.containsKey(variableName))
/*      */     {
/* 1368 */       return this.flags.get(variableName);
/*      */     }
/*      */     
/* 1371 */     return super.getVariable(variableName);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getVariables() {
/* 1377 */     Set<String> variables = super.getVariables();
/*      */     
/* 1379 */     for (String counterVar : this.counters.keySet())
/*      */     {
/* 1381 */       variables.add("#" + counterVar);
/*      */     }
/*      */     
/* 1384 */     for (String stringVar : this.strings.keySet())
/*      */     {
/* 1386 */       variables.add("&" + stringVar);
/*      */     }
/*      */     
/* 1389 */     variables.addAll(this.flags.keySet());
/*      */     
/* 1391 */     return variables;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onInit() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String sanitiseString(String string) {
/* 1405 */     return (string == null) ? "" : string.replaceAll("[\\n\\r]", "");
/*      */   }
/*      */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\MacroTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */