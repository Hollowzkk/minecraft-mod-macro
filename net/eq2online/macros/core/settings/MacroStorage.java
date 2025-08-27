/*      */ package net.eq2online.macros.core.settings;
/*      */ 
/*      */ import bib;
/*      */ import com.google.common.io.Files;
/*      */ import com.mumfrey.liteloader.core.LiteLoader;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import net.eq2online.console.Log;
/*      */ import net.eq2online.macros.compatibility.I18n;
/*      */ import net.eq2online.macros.core.MacroModCore;
/*      */ import net.eq2online.macros.core.MacroTemplate;
/*      */ import net.eq2online.macros.core.params.MacroParam;
/*      */ import net.eq2online.macros.interfaces.IConfigs;
/*      */ import net.eq2online.macros.interfaces.ISettingsStore;
/*      */ import net.eq2online.macros.scripting.api.IMacroTemplate;
/*      */ import net.eq2online.xml.Xml;
/*      */ import org.apache.commons.io.IOUtils;
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
/*      */ public abstract class MacroStorage
/*      */   implements ISettingsStore, IConfigs
/*      */ {
/*   41 */   private static Pattern PATTERN_BEGIN_CONFIG = Pattern.compile("^DIRECTIVE BEGINCONFIG\\(\\) (.+)$");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   46 */   private static Pattern PATTERN_MACRO = Pattern.compile("^Macro\\[([0-9]{1,4})\\]\\.([a-z0-9_\\-\\[\\]]+)=(.+)$", 2);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   51 */   private static Pattern PATTERN_SETTING = Pattern.compile("^([a-zA-Z\\.]+)=(.*)$", 2);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   56 */   private static Pattern PATTERN_LEGACY_MACRO = Pattern.compile("^(|@|#|&)([0-9]{1,3}):(.+)$");
/*      */ 
/*      */ 
/*      */   
/*      */   protected final bib mc;
/*      */ 
/*      */ 
/*      */   
/*      */   private final MacroModCore mod;
/*      */ 
/*      */ 
/*      */   
/*   68 */   private final Map<String, String> comments = new TreeMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   73 */   private final Map<String, String> settings = new TreeMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final File oldLegacyMacrosFile;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final File newLegacyMacrosFile;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private File variablesFile;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private File macrosFile;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   98 */   protected String activeConfig = "";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  103 */   protected String overlayConfig = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  108 */   protected String singlePlayerConfigName = "";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  113 */   protected MacroTemplate[] baseTemplates = new MacroTemplate[10000];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  118 */   protected Map<String, MacroTemplate[]> configs = (Map)new HashMap<>();
/*      */ 
/*      */   
/*      */   public MacroStorage(bib minecraft, MacroModCore mod) {
/*  122 */     this.mc = minecraft;
/*  123 */     this.mod = mod;
/*      */     
/*  125 */     this.newLegacyMacrosFile = new File(LiteLoader.getGameDirectory(), "/mods/macros/.macros.txt");
/*  126 */     this.oldLegacyMacrosFile = new File(LiteLoader.getGameDirectory(), "macros.txt");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void initDefaults() {
/*  131 */     this.macrosFile = getFile(".macros.txt");
/*  132 */     this.variablesFile = getFile(".vars.xml");
/*      */   }
/*      */ 
/*      */   
/*      */   protected abstract void onClear();
/*      */ 
/*      */   
/*      */   protected abstract void onLoad();
/*      */ 
/*      */   
/*      */   protected abstract void onSave();
/*      */ 
/*      */   
/*      */   protected abstract void onConfigChanged();
/*      */ 
/*      */   
/*      */   protected abstract void onConfigAdded(String paramString, boolean paramBoolean);
/*      */ 
/*      */   
/*      */   protected abstract void onConfigRemoved(String paramString);
/*      */ 
/*      */   
/*      */   protected abstract int getNextFreeIndex(String paramString);
/*      */   
/*      */   protected abstract MacroTemplate createTemplate(int paramInt);
/*      */   
/*      */   protected abstract boolean getMacroShouldBeSaved(int paramInt);
/*      */   
/*      */   public abstract String getMacroNameWithPrefix(int paramInt);
/*      */   
/*      */   public abstract File getFile(String paramString);
/*      */   
/*      */   public abstract File getMacrosDirectory();
/*      */   
/*      */   public String[] getConfigNames() {
/*  167 */     return (String[])this.configs.keySet().toArray((Object[])new String[0]);
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
/*      */   public String getConfigDisplayName(String configName) {
/*  181 */     return "".equals(configName) ? I18n.get("options.defaultconfig") : configName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getActiveConfigName() {
/*  192 */     return getConfigDisplayName(this.activeConfig);
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
/*      */   public String getOverlayConfigName(String noneColourCode) {
/*  204 */     return (this.overlayConfig != null) ? getConfigDisplayName(this.overlayConfig) : (noneColourCode + "None");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getActiveConfig() {
/*  214 */     return this.activeConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOverlayConfig() {
/*  224 */     return this.overlayConfig;
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
/*      */   public boolean hasConfig(String configName) {
/*  236 */     return ("".equals(configName) || this.configs.containsKey(configName));
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
/*      */   public void setActiveConfig(String configName) {
/*  249 */     this.activeConfig = configName;
/*  250 */     this.overlayConfig = null;
/*      */     
/*  252 */     onConfigChanged();
/*      */     
/*  254 */     if (this.mc.E())
/*      */     {
/*  256 */       this.singlePlayerConfigName = configName;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOverlayConfig(String configName) {
/*  263 */     this.overlayConfig = configName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MacroTemplate[] getConfig(String configName) {
/*  274 */     if ("".equals(configName))
/*      */     {
/*  276 */       return this.baseTemplates;
/*      */     }
/*      */     
/*  279 */     if (!this.configs.containsKey(configName))
/*      */     {
/*  281 */       this.configs.put(configName, new MacroTemplate[10000]);
/*      */     }
/*      */     
/*  284 */     return this.configs.get(configName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addConfig(String configName, boolean copy) {
/*  294 */     if (configName.equals(this.activeConfig) || this.configs.containsKey(configName))
/*      */       return; 
/*  296 */     if (!this.configs.containsKey(configName))
/*      */     {
/*  298 */       this.configs.put(configName, new MacroTemplate[10000]);
/*      */     }
/*      */ 
/*      */     
/*  302 */     if (copy) {
/*      */       
/*  304 */       MacroTemplate[] currentConfig = getConfig(this.activeConfig);
/*  305 */       MacroTemplate[] newConfig = getConfig(configName);
/*      */       
/*  307 */       for (int i = 0; i < 10000; i++) {
/*      */         
/*  309 */         if (currentConfig[i] != null)
/*      */         {
/*  311 */           newConfig[i] = new MacroTemplate(i, currentConfig[i]);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  316 */     onConfigAdded(configName, copy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteConfig(String configName) {
/*  326 */     if ("".equals(configName) || !this.configs.containsKey(configName)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  331 */     this.configs.remove(configName);
/*  332 */     onConfigRemoved(configName);
/*  333 */     save();
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
/*      */   public MacroTemplate getMacroTemplate(int key, boolean useOverlay) {
/*  345 */     return useOverlay ? getMacroTemplateWithOverlay(key) : getMacroTemplate(key);
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
/*      */   public MacroTemplate getMacroTemplate(int key) {
/*  357 */     return getMacroTemplate(this.activeConfig, key, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public MacroTemplate getMacroTemplateWithOverlay(int key) {
/*  362 */     if (this.overlayConfig != null && !this.overlayConfig.equals(this.activeConfig) && hasConfig(this.overlayConfig)) {
/*      */       
/*  364 */       MacroTemplate tpl = getMacroTemplate(this.overlayConfig, key, false);
/*  365 */       if (tpl != null && !tpl.isEmpty()) return tpl;
/*      */     
/*      */     } 
/*  368 */     return getMacroTemplate(this.activeConfig, key, true);
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
/*      */   public MacroTemplate getMacroTemplate(String configName, int key, boolean createIfNotFound) {
/*  381 */     if (key > -1 && key < 10000) {
/*      */       
/*  383 */       if (this.baseTemplates[key] != null && (this.baseTemplates[key]).global)
/*      */       {
/*  385 */         return this.baseTemplates[key];
/*      */       }
/*      */       
/*  388 */       MacroTemplate[] templates = getConfig(configName);
/*      */       
/*  390 */       if (templates[key] == null && createIfNotFound)
/*      */       {
/*  392 */         templates[key] = createTemplate(key);
/*      */       }
/*      */       
/*  395 */       return templates[key];
/*      */     } 
/*      */     
/*  398 */     return null;
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
/*      */   public void setMacroTemplate(String configName, int key, MacroTemplate template) {
/*  410 */     if (key > -1 && key < 10000) {
/*      */       
/*  412 */       MacroTemplate[] templates = getConfig(configName);
/*  413 */       templates[key] = template;
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
/*      */   public void deleteMacroTemplate(int key) {
/*  425 */     deleteMacroTemplate(this.activeConfig, key);
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
/*      */   public void deleteMacroTemplate(String configName, int key) {
/*  437 */     if (key > -1 && key < 10000) {
/*      */       
/*  439 */       MacroTemplate delTemplate = getMacroTemplate(configName, key, true);
/*      */       
/*  441 */       if (delTemplate.global) {
/*      */         
/*  443 */         this.baseTemplates[key] = null;
/*      */       }
/*      */       else {
/*      */         
/*  447 */         MacroTemplate[] templates = getConfig(configName);
/*  448 */         templates[key] = null;
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
/*      */   public void deleteMacroTemplateFromAllConfigurations(int key) {
/*  460 */     this.baseTemplates[key] = null;
/*      */     
/*  462 */     for (MacroTemplate[] templates : this.configs.values())
/*      */     {
/*  464 */       templates[key] = null;
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
/*      */   public void copyMacroTemplate(int source, int dest) {
/*  477 */     copyMacroTemplate(this.activeConfig, source, dest);
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
/*      */   public void copyMacroTemplate(String configName, int source, int dest) {
/*  490 */     MacroTemplate sourceTemplate = getMacroTemplate(configName, source, true);
/*  491 */     MacroTemplate destTemplate = getMacroTemplate(configName, dest, false);
/*      */     
/*  493 */     if (sourceTemplate != null && dest > -1 && dest < 10000) {
/*      */       
/*  495 */       if (sourceTemplate.global || (destTemplate != null && destTemplate.global))
/*      */       {
/*  497 */         deleteMacroTemplateFromAllConfigurations(dest);
/*      */       }
/*      */       
/*  500 */       if (sourceTemplate.global) {
/*      */         
/*  502 */         this.baseTemplates[dest] = new MacroTemplate(dest, sourceTemplate);
/*      */       }
/*      */       else {
/*      */         
/*  506 */         MacroTemplate[] templates = getConfig(configName);
/*  507 */         templates[dest] = new MacroTemplate(dest, sourceTemplate);
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
/*      */   public void moveMacroTemplate(int source, int dest) {
/*  521 */     moveMacroTemplate(this.activeConfig, source, dest);
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
/*      */   public void moveMacroTemplate(String configName, int source, int dest) {
/*  534 */     MacroTemplate sourceTemplate = getMacroTemplate(configName, source, true);
/*      */     
/*  536 */     if (sourceTemplate != null && dest > -1 && dest < 10000)
/*      */     {
/*  538 */       if (sourceTemplate.global) {
/*      */         
/*  540 */         deleteMacroTemplateFromAllConfigurations(dest);
/*  541 */         this.baseTemplates[dest] = sourceTemplate;
/*  542 */         this.baseTemplates[source] = null;
/*  543 */         sourceTemplate.setID(dest);
/*      */       }
/*      */       else {
/*      */         
/*  547 */         MacroTemplate[] templates = getConfig(configName);
/*  548 */         templates[dest] = templates[source];
/*  549 */         templates[dest].setID(dest);
/*  550 */         templates[source] = null;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateMacroTemplate(int key, MacroTemplate template, MacroTemplate.Options options, String keyDownMacro, String keyHeldMacro, String keyUpMacro, String condition) {
/*  561 */     template.setKeyDownMacro(keyDownMacro);
/*  562 */     template.setKeyHeldMacro(keyHeldMacro);
/*  563 */     template.setKeyUpMacro(keyUpMacro);
/*  564 */     template.setMacroCondition(condition);
/*      */ 
/*      */     
/*  567 */     if (template.global != options.isGlobal)
/*      */     {
/*  569 */       if (template.global) {
/*      */         
/*  571 */         deleteMacroTemplate("", key);
/*  572 */         setMacroTemplate(this.activeConfig, key, template);
/*      */       }
/*      */       else {
/*      */         
/*  576 */         deleteMacroTemplateFromAllConfigurations(key);
/*  577 */         setMacroTemplate("", key, template);
/*      */       } 
/*      */     }
/*      */     
/*  581 */     template.setOptions(options);
/*  582 */     save();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IMacroTemplate createFloatingTemplate(String macro, String name) {
/*  593 */     int key = getNextFreeIndex(name);
/*      */     
/*  595 */     if (key < 10000) {
/*      */       
/*  597 */       MacroTemplate template = createTemplate(key);
/*      */       
/*  599 */       template.setKeyDownMacro(macro);
/*  600 */       setMacroTemplate("", key, template);
/*      */       
/*  602 */       return (IMacroTemplate)template;
/*      */     } 
/*      */     
/*  605 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void load() {
/*  614 */     BufferedReader bufferedreader = null;
/*      */ 
/*      */     
/*      */     try {
/*  618 */       Log.info("Loading macro templates...");
/*      */ 
/*      */       
/*  621 */       this.baseTemplates = new MacroTemplate[10000];
/*  622 */       onClear();
/*      */       
/*  624 */       if (!this.macrosFile.exists())
/*      */       {
/*  626 */         if (this.oldLegacyMacrosFile.exists()) {
/*      */           
/*  628 */           this.macrosFile = this.oldLegacyMacrosFile;
/*      */ 
/*      */         
/*      */         }
/*  632 */         else if (this.newLegacyMacrosFile.exists()) {
/*      */           
/*  634 */           this.macrosFile = this.newLegacyMacrosFile;
/*      */         }
/*      */         else {
/*      */           
/*  638 */           this.mod.setFirstRun();
/*      */           
/*      */           return;
/*      */         } 
/*      */       }
/*      */       
/*  644 */       String currentConfigName = "";
/*      */       
/*  646 */       bufferedreader = new BufferedReader(new FileReader(this.macrosFile));
/*      */       
/*  648 */       for (String configLine = ""; (configLine = bufferedreader.readLine()) != null;) {
/*      */ 
/*      */         
/*      */         try {
/*  652 */           Matcher macroPatternMatcher = PATTERN_MACRO.matcher(configLine);
/*  653 */           Matcher settingPatternMatcher = PATTERN_SETTING.matcher(configLine);
/*  654 */           Matcher beginConfigPatternMatcher = PATTERN_BEGIN_CONFIG.matcher(configLine);
/*      */           
/*  656 */           if (macroPatternMatcher.matches()) {
/*      */             
/*  658 */             int key = Integer.parseInt(macroPatternMatcher.group(1));
/*      */             
/*  660 */             if (key > -1 && key < 10000) {
/*      */               
/*  662 */               MacroTemplate tpl = getMacroTemplate(currentConfigName, key, true);
/*  663 */               tpl.loadFrom(configLine, macroPatternMatcher.group(2), macroPatternMatcher.group(3));
/*      */ 
/*      */               
/*  666 */               if (tpl.global && currentConfigName.length() > 0)
/*      */               {
/*  668 */                 deleteMacroTemplate(currentConfigName, key); } 
/*      */             } 
/*      */             continue;
/*      */           } 
/*  672 */           if (settingPatternMatcher.matches()) {
/*      */             
/*  674 */             if ("".equals(currentConfigName)) {
/*      */               
/*  676 */               if (this.settings.containsKey(settingPatternMatcher.group(1).toLowerCase())) {
/*      */                 
/*  678 */                 Log.info("WARNING! Duplicate directive '{0}' found in file", new Object[] { settingPatternMatcher.group(1) });
/*  679 */                 this.settings.remove(settingPatternMatcher.group(1));
/*      */               } 
/*      */               
/*  682 */               this.settings.put(settingPatternMatcher.group(1).toLowerCase(), settingPatternMatcher.group(2));
/*      */             }  continue;
/*      */           } 
/*  685 */           if (beginConfigPatternMatcher.matches()) {
/*      */             
/*  687 */             currentConfigName = beginConfigPatternMatcher.group(1);
/*  688 */             Log.info("Loading additional config: {0}", new Object[] { currentConfigName });
/*      */             
/*      */             continue;
/*      */           } 
/*  692 */           Matcher oldMacroPatternMatcher = PATTERN_LEGACY_MACRO.matcher(configLine);
/*      */           
/*  694 */           if (oldMacroPatternMatcher.matches())
/*      */           {
/*  696 */             int key = Integer.parseInt(oldMacroPatternMatcher.group(2));
/*      */             
/*  698 */             if (key > -1 && key < 10000)
/*      */             {
/*  700 */               MacroTemplate tpl = getMacroTemplate(currentConfigName, key, true);
/*      */               
/*  702 */               if (oldMacroPatternMatcher.group(1).equals("")) {
/*      */                 
/*  704 */                 tpl.setKeyDownMacro(oldMacroPatternMatcher.group(3)); continue;
/*      */               } 
/*  706 */               if (oldMacroPatternMatcher.group(1).equals("@")) {
/*      */                 
/*  708 */                 tpl.setStoredParam(MacroParam.Type.NORMAL, 0, oldMacroPatternMatcher.group(3)); continue;
/*      */               } 
/*  710 */               if (oldMacroPatternMatcher.group(1).equals("#")) {
/*      */                 
/*  712 */                 tpl.setStoredParam(MacroParam.Type.ITEM, 0, oldMacroPatternMatcher.group(3)); continue;
/*      */               } 
/*  714 */               if (oldMacroPatternMatcher.group(1).equals("&"))
/*      */               {
/*  716 */                 tpl.setStoredParam(MacroParam.Type.FRIEND, 0, oldMacroPatternMatcher.group(3));
/*      */               }
/*      */             }
/*      */           
/*      */           }
/*      */         
/*  722 */         } catch (Exception ex) {
/*      */           
/*  724 */           Log.info("Skipping bad macro/setting: {0}", new Object[] { configLine });
/*      */         } 
/*      */       } 
/*      */       
/*  728 */       bufferedreader.close();
/*  729 */       bufferedreader = null;
/*      */       
/*  731 */       if (getSetting("version", 0) < 1540) {
/*      */         
/*  733 */         handleVersionUpgrade(getSetting("version", 0));
/*      */         
/*  735 */         File backupFile = new File(this.macrosFile.getParentFile(), ".macros.backup." + getSetting("version", 0) + ".txt");
/*  736 */         Log.info("Creating backup of existing config at {0}", new Object[] { backupFile.getAbsoluteFile() });
/*  737 */         Files.copy(this.macrosFile, backupFile);
/*      */       } 
/*      */       
/*  740 */       loadVariables();
/*  741 */       onLoad();
/*      */     }
/*  743 */     catch (Exception ex) {
/*      */       
/*  745 */       Log.info("Failed to load macro templates:");
/*  746 */       Log.printStackTrace(ex);
/*      */     }
/*      */     finally {
/*      */       
/*  750 */       if (bufferedreader != null)
/*      */       {
/*  752 */         IOUtils.closeQuietly(bufferedreader);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onClearSettings() {
/*  759 */     this.configs.clear();
/*  760 */     this.settings.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadVariables() {
/*  765 */     Xml.xmlClearNamespace();
/*      */ 
/*      */     
/*      */     try {
/*  769 */       if (this.variablesFile.exists()) {
/*      */         
/*  771 */         Document xml = Xml.xmlCreate(this.variablesFile);
/*  772 */         if (xml != null) loadVariablesFromXml(xml);
/*      */       
/*      */       } 
/*  775 */     } catch (Exception ex) {
/*      */       
/*  777 */       Log.printStackTrace(ex);
/*      */     } 
/*      */     
/*  780 */     Xml.xmlClearNamespace();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadVariablesFromXml(Document xml) {
/*  786 */     for (Node configNode : Xml.xmlNodes(xml.getFirstChild(), "config")) {
/*      */       
/*  788 */       String configName = Xml.xmlGetAttribute(configNode, "name", "");
/*  789 */       if (configName.length() < 1)
/*  790 */         continue;  if ("@default".equalsIgnoreCase(configName)) configName = "";
/*      */       
/*  792 */       for (Node templateNode : Xml.xmlNodes(configNode, "template")) {
/*      */         
/*  794 */         int templateId = Xml.xmlGetAttribute(templateNode, "id", 0);
/*  795 */         if (templateId < 1)
/*      */           continue; 
/*  797 */         MacroTemplate tpl = getMacroTemplate(configName, templateId, false);
/*  798 */         if (tpl != null)
/*      */         {
/*  800 */           tpl.loadVariables(templateNode);
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
/*      */   public void save() {
/*      */     try {
/*  814 */       this.settings.clear();
/*  815 */       onSave();
/*      */ 
/*      */       
/*      */       try {
/*  819 */         this.macrosFile.getParentFile().mkdirs();
/*      */       }
/*  821 */       catch (Exception exception) {}
/*      */       
/*  823 */       PrintWriter printwriter = new PrintWriter(new FileWriter(this.macrosFile));
/*      */       
/*  825 */       printwriter.println("#");
/*  826 */       printwriter.println("# macros.txt");
/*  827 */       printwriter.println("# This file stores the macro definitions, parameters and options for mod_macros");
/*  828 */       printwriter.println("#\n");
/*      */       
/*  830 */       printwriter.println("# Version number of the mod which saved this file. ***Do not alter this value!***");
/*  831 */       printwriter.println("version=1540\n");
/*      */       
/*  833 */       printwriter.println("#\n# Settings\n#\n");
/*      */       
/*  835 */       String nextBreak = "";
/*      */       
/*  837 */       for (Map.Entry<String, String> setting : this.settings.entrySet()) {
/*      */         
/*  839 */         printwriter.print(nextBreak);
/*      */         
/*  841 */         if (this.comments.containsKey(setting.getKey())) {
/*      */           
/*  843 */           if (nextBreak.length() == 0) printwriter.println(); 
/*  844 */           printwriter.println("# " + (String)this.comments.get(setting.getKey()));
/*  845 */           nextBreak = "\n";
/*      */         }
/*      */         else {
/*      */           
/*  849 */           nextBreak = "";
/*      */         } 
/*      */         
/*  852 */         printwriter.println((String)setting.getKey() + "=" + (String)setting.getValue());
/*      */       } 
/*      */       
/*  855 */       printwriter.println("\n#\n# Macros\n#\n");
/*      */       
/*  857 */       for (int index = 0; index < 10000; index++) {
/*      */         
/*  859 */         if (this.baseTemplates[index] != null && getMacroShouldBeSaved(index)) {
/*      */           
/*      */           try {
/*      */             
/*  863 */             this.baseTemplates[index].saveTemplate(printwriter);
/*      */           }
/*  865 */           catch (Exception exception) {}
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  870 */       if (this.configs.size() > 0)
/*      */       {
/*  872 */         for (Map.Entry<String, MacroTemplate[]> config : this.configs.entrySet()) {
/*      */           
/*  874 */           printwriter.println("\nDIRECTIVE BEGINCONFIG() " + (String)config.getKey() + "\n");
/*      */           
/*  876 */           MacroTemplate[] configTemplates = config.getValue();
/*      */           
/*  878 */           for (int i = 0; i < 10000; i++) {
/*      */             
/*  880 */             if (configTemplates[i] != null && !(configTemplates[i]).global && getMacroShouldBeSaved(i)) {
/*      */               
/*      */               try {
/*      */                 
/*  884 */                 configTemplates[i].saveTemplate(printwriter);
/*      */               }
/*  886 */               catch (Exception exception) {}
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/*  892 */       printwriter.close();
/*      */     }
/*  894 */     catch (Exception ex) {
/*      */       
/*  896 */       Log.info("Failed to save .macros.txt");
/*  897 */       Log.printStackTrace(ex);
/*      */     } 
/*      */     
/*  900 */     saveVariables();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveVariables() {
/*      */     try {
/*  907 */       Document xml = Xml.xmlCreate();
/*      */       
/*  909 */       Element root = xml.createElement("variables");
/*  910 */       xml.appendChild(root);
/*      */       
/*  912 */       Element baseConfigNode = xml.createElement("config");
/*  913 */       baseConfigNode.setAttribute("name", "@default");
/*      */       
/*  915 */       for (int index = 0; index < 10000; index++) {
/*      */         
/*  917 */         if (this.baseTemplates[index] != null && getMacroShouldBeSaved(index)) {
/*      */           
/*  919 */           Element templateNode = xml.createElement("template");
/*  920 */           templateNode.setAttribute("id", String.valueOf(index));
/*      */ 
/*      */           
/*      */           try {
/*  924 */             this.baseTemplates[index].saveVariables(xml, templateNode);
/*      */           }
/*  926 */           catch (Exception exception) {}
/*      */           
/*  928 */           if (templateNode.hasChildNodes()) {
/*      */             
/*  930 */             baseConfigNode.appendChild(xml.createComment(" " + getMacroNameWithPrefix(index) + " "));
/*  931 */             baseConfigNode.appendChild(templateNode);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  936 */       root.appendChild(baseConfigNode);
/*      */ 
/*      */       
/*  939 */       if (this.configs.size() > 0)
/*      */       {
/*  941 */         for (Map.Entry<String, MacroTemplate[]> config : this.configs.entrySet()) {
/*      */           
/*  943 */           Element configNode = xml.createElement("config");
/*  944 */           configNode.setAttribute("name", config.getKey());
/*      */           
/*  946 */           MacroTemplate[] configTemplates = config.getValue();
/*      */           
/*  948 */           for (int i = 0; i < 10000; i++) {
/*      */             
/*  950 */             if (configTemplates[i] != null && !(configTemplates[i]).global && getMacroShouldBeSaved(i)) {
/*      */               
/*  952 */               Element templateNode = xml.createElement("template");
/*  953 */               templateNode.setAttribute("id", String.valueOf(i));
/*      */ 
/*      */               
/*      */               try {
/*  957 */                 configTemplates[i].saveVariables(xml, templateNode);
/*      */               }
/*  959 */               catch (Exception exception) {}
/*      */               
/*  961 */               if (templateNode.hasChildNodes()) {
/*      */                 
/*  963 */                 configNode.appendChild(xml.createComment(" " + getMacroNameWithPrefix(i) + " "));
/*  964 */                 configNode.appendChild(templateNode);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/*  969 */           if (configNode.hasChildNodes()) {
/*      */             
/*  971 */             root.appendChild(xml.createComment(" ================================================================================ "));
/*  972 */             root.appendChild(configNode);
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/*  977 */       Xml.xmlSave(this.variablesFile, xml);
/*      */     }
/*  979 */     catch (Exception ex) {
/*      */       
/*  981 */       Log.info("Failed to save .vars.xml");
/*  982 */       Log.printStackTrace(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSetting(String settingName, String settingValue) {
/*  993 */     if (this.settings.containsKey(settingName))
/*      */     {
/*  995 */       this.settings.remove(settingName);
/*      */     }
/*      */     
/*  998 */     if (settingValue != null)
/*      */     {
/* 1000 */       this.settings.put(settingName, settingValue);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSettingComment(String settingName, String settingComment) {
/* 1011 */     if (this.comments.containsKey(settingName))
/*      */     {
/* 1013 */       this.comments.remove(settingName);
/*      */     }
/*      */     
/* 1016 */     if (settingComment != null)
/*      */     {
/* 1018 */       this.comments.put(settingName, settingComment);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSetting(String settingName, boolean settingValue) {
/* 1029 */     setSetting(settingName, settingValue ? "1" : "0");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends Enum<T>> void setSetting(String settingName, T settingValue) {
/* 1039 */     if (settingValue == null) {
/*      */       
/* 1041 */       setSetting(settingName, (String)null);
/*      */     }
/*      */     else {
/*      */       
/* 1045 */       setSetting(settingName, settingValue.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSetting(String settingName, int settingValue) {
/* 1056 */     setSetting(settingName, "" + settingValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSetting(String settingName, String defaultValue) {
/* 1066 */     return this.settings.containsKey(settingName) ? this.settings.get(settingName) : defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSetting(String settingName, int defaultValue) {
/* 1076 */     String sValue = getSetting(settingName, "" + defaultValue);
/*      */ 
/*      */     
/*      */     try {
/* 1080 */       int result = Integer.parseInt(sValue);
/* 1081 */       return result;
/*      */     }
/* 1083 */     catch (NumberFormatException ex) {
/*      */       
/* 1085 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSetting(String settingName, int defaultValue, int minValue, int maxValue) {
/* 1096 */     return Math.min(Math.max(getSetting(settingName, defaultValue), minValue), maxValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getSetting(String settingName, boolean defaultValue) {
/* 1106 */     String sValue = getSetting(settingName, "*").toLowerCase();
/* 1107 */     if ("*".equals(sValue))
/*      */     {
/* 1109 */       return defaultValue;
/*      */     }
/*      */     
/* 1112 */     return ("1".equals(sValue) || "true".equals(sValue) || "yes".equals(sValue));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends Enum> T getSetting(String settingName, T defaultValue) {
/* 1123 */     Class<? extends T> enumClass = defaultValue.getDeclaringClass();
/*      */     
/* 1125 */     String sValue = getSetting(settingName, "*");
/* 1126 */     if ("*".equals(sValue)) return defaultValue;
/*      */ 
/*      */     
/*      */     try {
/* 1130 */       return Enum.valueOf(enumClass, sValue);
/*      */     }
/* 1132 */     catch (IllegalArgumentException ex) {
/*      */ 
/*      */       
/*      */       try {
/* 1136 */         return Enum.valueOf(enumClass, sValue.toUpperCase());
/*      */       }
/* 1138 */       catch (IllegalArgumentException ex1) {
/*      */         
/* 1140 */         return defaultValue;
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
/*      */   private void handleVersionUpgrade(int oldVersion) {
/* 1153 */     Log.info("Handling version upgrade: old version is {0}, current version is {1}", new Object[] { Double.valueOf(oldVersion * 0.001D), Double.valueOf(1.54D) });
/*      */ 
/*      */     
/* 1156 */     if (oldVersion < 630) {
/*      */       
/* 1158 */       File macrosDir = getMacrosDirectory();
/*      */       
/* 1160 */       if (macrosDir.exists()) {
/*      */         
/* 1162 */         String[] moveFiles = { "friends", "homes", "places", "warps", "towns", "places", "presettext0", "presettext1", "presettext2", "presettext3", "presettext4", "presettext5", "presettext6", "presettext7", "presettext8", "presettext9" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1168 */         for (String moveFile : moveFiles) {
/*      */           
/* 1170 */           File sourceFile = new File(LiteLoader.getGameDirectory(), "." + moveFile + ".txt");
/* 1171 */           File destFile = new File(macrosDir, "." + moveFile + ".txt");
/*      */           
/* 1173 */           if (sourceFile.exists()) {
/*      */             
/* 1175 */             Log.info("Moving {0} to {1}", new Object[] { sourceFile.getName(), destFile.getAbsolutePath() });
/*      */ 
/*      */             
/*      */             try {
/* 1179 */               sourceFile.renameTo(destFile);
/*      */             }
/* 1181 */             catch (Exception ex) {
/*      */               
/* 1183 */               Log.printStackTrace(ex);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1191 */     if (oldVersion < 750) {
/*      */       
/* 1193 */       String keyboardLayout = getSetting("keyboard.layout", "");
/*      */       
/* 1195 */       if (keyboardLayout.length() > 0) {
/*      */         
/* 1197 */         keyboardLayout = keyboardLayout + "{250,26,108}{251,164,108}{252,168,152}{253,26,152}{254,26,136}";
/* 1198 */         setSetting("keyboard.layout", keyboardLayout);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1203 */     if (oldVersion < 870)
/*      */     {
/* 1205 */       setSetting("events.layout", "{1000,6,4}{1001,6,24}{1002,6,44}{1003,6,64}{1004,6,84}{1005,6,104}{1006,6,124}{1007,6,144}{1008,160,4}{1009,160,24}{1010,160,44}{1011,160,64}{1012,160,84}{1013,160,104}{1014,160,124}");
/*      */     }
/*      */ 
/*      */     
/* 1209 */     if (oldVersion < 880) {
/*      */       
/* 1211 */       if (getSetting("colourcodehelperkey", 0) == -1)
/*      */       {
/* 1213 */         setSetting("colourcodehelperkey", 0);
/*      */       }
/*      */       
/* 1216 */       String eventsLayout = getSetting("events.layout", "");
/*      */       
/* 1218 */       if (eventsLayout.length() > 0) {
/*      */         
/* 1220 */         eventsLayout = eventsLayout + "{1015,160,144}";
/* 1221 */         setSetting("events.layout", eventsLayout);
/*      */       } 
/*      */     } 
/*      */     
/* 1225 */     if (oldVersion < 902) {
/*      */       
/* 1227 */       String eventsLayout = getSetting("events.layout", "");
/*      */       
/* 1229 */       if (eventsLayout.length() > 0) {
/*      */         
/* 1231 */         eventsLayout = eventsLayout + "{1016,6,164}{1017,160,164}";
/* 1232 */         setSetting("events.layout", eventsLayout);
/*      */       } 
/*      */     } 
/*      */     
/* 1236 */     if (oldVersion < 903)
/*      */     {
/* 1238 */       this.mod.setFirstRun();
/*      */     }
/*      */     
/* 1241 */     if (oldVersion < 952) {
/*      */       
/* 1243 */       String symbolOverrides = getSetting("keyboard.symbols", "");
/*      */       
/* 1245 */       if (symbolOverrides.length() > 0) {
/*      */         
/* 1247 */         symbolOverrides = symbolOverrides + "{248,24}{249,25}";
/* 1248 */         setSetting("keyboard.symbols", symbolOverrides);
/*      */       } 
/*      */       
/* 1251 */       String keyboardLayout = getSetting("keyboard.layout", "");
/*      */       
/* 1253 */       if (keyboardLayout.length() > 0) {
/*      */         
/* 1255 */         keyboardLayout = keyboardLayout + "{248,92,116}{249,92,132}";
/* 1256 */         setSetting("keyboard.layout", keyboardLayout);
/*      */       } 
/*      */     } 
/*      */     
/* 1260 */     if (oldVersion < 980) {
/*      */       
/* 1262 */       String eventsLayout = "{1000,6,4}{1001,6,24}{1002,6,44}{1003,6,64}{1004,6,84}{1005,6,104}{1006,6,124}{1007,6,144}{1016,6,164}{1008,145,4}{1009,145,24}{1010,145,44}{1011,145,64}{1012,145,84}{1013,145,104}{1014,145,124}{1015,145,144}{1017,145,164}{1018,284,4}";
/*      */ 
/*      */       
/* 1265 */       setSetting("events.layout", eventsLayout);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\settings\MacroStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */