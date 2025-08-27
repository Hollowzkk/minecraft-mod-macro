/*     */ package net.eq2online.macros.modules.chatfilter;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.ChatFilter;
/*     */ import com.mumfrey.liteloader.api.Listener;
/*     */ import com.mumfrey.liteloader.core.LiteLoader;
/*     */ import com.mumfrey.liteloader.core.LiteLoaderEventBroker;
/*     */ import hh;
/*     */ import ho;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.event.providers.OnChatProvider;
/*     */ import net.eq2online.macros.interfaces.IObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
/*     */ import net.eq2online.macros.modules.chatfilter.gui.GuiEditChatFilter;
/*     */ import net.eq2online.macros.modules.chatfilter.scriptactions.ScriptActionChatFilter;
/*     */ import net.eq2online.macros.modules.chatfilter.scriptactions.ScriptActionFilter;
/*     */ import net.eq2online.macros.modules.chatfilter.scriptactions.ScriptActionModify;
/*     */ import net.eq2online.macros.modules.chatfilter.scriptactions.ScriptActionPass;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.exceptions.ScriptException;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import rp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChatFilterManager
/*     */   implements ISettingsObserver, ChatFilter
/*     */ {
/*     */   private static ChatFilterManager instance;
/*  50 */   private static Pattern PATTERN_FILTER = Pattern.compile("^Filter\\[([^\\]]*)\\]\\.([a-z0-9_\\-\\[\\]]+)=(.+)$", 2);
/*     */ 
/*     */   
/*     */   private final Macros macros;
/*     */ 
/*     */   
/*     */   private final bib mc;
/*     */ 
/*     */   
/*  59 */   private TreeMap<String, ChatFilterTemplate> templates = new TreeMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private File configFile;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean enabled = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChatFilterManager getInstance() {
/*  74 */     if (instance == null) {
/*     */       
/*  76 */       instance = new ChatFilterManager();
/*  77 */       instance.macros.getCustomScreenManager().registerCustomScreen("menu.chatfilter.edit", GuiEditChatFilter.class);
/*  78 */       LiteLoader.getInterfaceManager().offer((Listener)instance);
/*     */     } 
/*     */     
/*  81 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatFilterManager() {
/*  91 */     this.macros = (Macros)ScriptContext.CHATFILTER.getScriptActionProvider().getMacroEngine();
/*  92 */     this.mc = bib.z();
/*  93 */     this.configFile = this.macros.getFile(".chatfilter.txt");
/*     */     
/*  95 */     ScriptCore chatCore = ScriptContext.CHATFILTER.getCore();
/*  96 */     chatCore.registerScriptAction((IScriptAction)new ScriptActionPass(ScriptContext.CHATFILTER));
/*  97 */     chatCore.registerScriptAction((IScriptAction)new ScriptActionFilter(ScriptContext.CHATFILTER));
/*  98 */     chatCore.registerScriptAction((IScriptAction)new ScriptActionModify(ScriptContext.CHATFILTER));
/*  99 */     ScriptCore mainCore = ScriptContext.MAIN.getCore();
/* 100 */     mainCore.registerScriptAction((IScriptAction)new ScriptActionPass(ScriptContext.MAIN));
/* 101 */     mainCore.registerScriptAction((IScriptAction)new ScriptActionFilter(ScriptContext.MAIN));
/*     */     
/* 103 */     mainCore.registerScriptAction((IScriptAction)new ScriptActionChatFilter(ScriptContext.MAIN));
/*     */ 
/*     */     
/* 106 */     load();
/*     */     
/* 108 */     this.macros.getSettingsHandler().registerObserver((IObserver)this);
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
/*     */   public ChatFilterTemplate getTemplate(String configName, boolean createIfNotFound) {
/* 121 */     if (this.templates.containsKey("") && ((ChatFilterTemplate)this.templates.get("")).global)
/*     */     {
/* 123 */       return this.templates.get("");
/*     */     }
/*     */     
/* 126 */     ChatFilterTemplate tpl = this.templates.get(configName);
/*     */     
/* 128 */     if (tpl == null && createIfNotFound) {
/*     */       
/* 130 */       tpl = new ChatFilterTemplate(this.macros, this.mc, this, configName);
/* 131 */       this.templates.put(configName, tpl);
/* 132 */       return tpl;
/*     */     } 
/*     */     
/* 135 */     return tpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void deleteTemplate(String configName) {
/* 145 */     this.templates.remove(configName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() {
/*     */     try {
/* 155 */       Log.info("Loading chat filter templates...");
/*     */ 
/*     */       
/* 158 */       this.templates.clear();
/*     */       
/* 160 */       if (!this.configFile.exists()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 165 */       BufferedReader bufferedreader = new BufferedReader(new FileReader(this.configFile));
/*     */       
/* 167 */       for (String configLine = ""; (configLine = bufferedreader.readLine()) != null;) {
/*     */ 
/*     */         
/*     */         try {
/* 171 */           Matcher filterPatternMatcher = PATTERN_FILTER.matcher(configLine);
/*     */           
/* 173 */           if (filterPatternMatcher.matches())
/*     */           {
/* 175 */             String currentConfigName = filterPatternMatcher.group(1);
/*     */             
/* 177 */             ChatFilterTemplate tpl = getTemplate(currentConfigName, true);
/* 178 */             tpl.loadFrom(configLine, filterPatternMatcher.group(2), filterPatternMatcher.group(3));
/*     */ 
/*     */             
/* 181 */             if (tpl.global && currentConfigName.length() > 0)
/*     */             {
/* 183 */               deleteTemplate(currentConfigName);
/*     */             }
/*     */           }
/*     */         
/* 187 */         } catch (Exception ex) {
/*     */           
/* 189 */           Log.info("Skipping bad filter declaration: {0}", new Object[] { configLine });
/*     */         } 
/*     */       } 
/*     */       
/* 193 */       bufferedreader.close();
/*     */     }
/* 195 */     catch (Exception ex) {
/*     */       
/* 197 */       Log.info("Failed to chat filters:");
/* 198 */       Log.printStackTrace(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save() {
/*     */     try {
/*     */       try {
/* 211 */         this.configFile.getParentFile().mkdirs();
/*     */       }
/* 213 */       catch (Exception exception) {}
/*     */       
/* 215 */       PrintWriter printwriter = new PrintWriter(new FileWriter(this.configFile));
/*     */       
/* 217 */       printwriter.println("#");
/* 218 */       printwriter.println("# .chatfilter.txt");
/* 219 */       printwriter.println("# This file stores the macro templates for the chat filter module. Do not edit this file.");
/* 220 */       printwriter.println("#\n");
/*     */       
/* 222 */       for (Map.Entry<String, ChatFilterTemplate> entry : this.templates.entrySet()) {
/*     */         
/* 224 */         if (this.macros.hasConfig(entry.getKey())) {
/*     */           
/*     */           try {
/*     */             
/* 228 */             ((ChatFilterTemplate)entry.getValue()).saveTemplate(printwriter);
/*     */           }
/* 230 */           catch (Exception exception) {}
/*     */         }
/*     */       } 
/*     */       
/* 234 */       printwriter.close();
/*     */     }
/* 236 */     catch (Exception ex) {
/*     */       
/* 238 */       Log.info("Failed to save .macros.txt");
/* 239 */       Log.printStackTrace(ex);
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
/*     */   public void onClearSettings() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoadSettings(ISettingsStore settings) {
/* 259 */     this.enabled = settings.getSetting("chatfilter.enabled", true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSaveSettings(ISettingsStore settings) {
/* 266 */     save();
/*     */     
/* 268 */     settings.setSetting("chatfilter.enabled", this.enabled);
/* 269 */     settings.setSettingComment("chatfilter.enabled", "Set to 1 to enable the chat filter module");
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
/*     */   public boolean onChat(hh chat, String message, LiteLoaderEventBroker.ReturnValue<hh> newMessage) {
/* 281 */     if (this.enabled) {
/*     */       
/* 283 */       ChatFilterTemplate activeTemplate = getTemplate(this.macros.getActiveConfig(), false);
/*     */ 
/*     */       
/*     */       try {
/* 287 */         if (activeTemplate != null)
/*     */         {
/* 289 */           this.macros.handleAutoSwitch();
/*     */           
/* 291 */           IScriptActionProvider scriptActionProvider = ScriptContext.CHATFILTER.getScriptActionProvider();
/* 292 */           scriptActionProvider.updateVariableProviders(false);
/*     */           
/* 294 */           List<String> messages = new ArrayList<>();
/* 295 */           String[] chatLines = message.split("\\r?\\n");
/* 296 */           boolean modified = false;
/*     */           
/* 298 */           for (String chatLine : chatLines) {
/*     */             
/* 300 */             ChatFilterMacro macro = processChatMessage(activeTemplate, chatLine);
/* 301 */             if (macro.pass) {
/*     */               
/* 303 */               if (macro.newMessage != null)
/*     */               {
/* 305 */                 modified = true;
/* 306 */                 messages.add(macro.newMessage);
/*     */               }
/*     */               else
/*     */               {
/* 310 */                 messages.add(chatLine);
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 315 */               modified = true;
/*     */             } 
/*     */           } 
/*     */           
/* 319 */           if (!modified) return true; 
/* 320 */           if (messages.size() == 0) return false;
/*     */           
/* 322 */           StringBuilder messageBuilder = new StringBuilder();
/* 323 */           boolean first = true;
/*     */           
/* 325 */           for (String msg : messages) {
/*     */             
/* 327 */             if (!first) messageBuilder.append("\n");  first = false;
/* 328 */             messageBuilder.append(msg);
/*     */           } 
/*     */           
/* 331 */           newMessage.set(new ho(messageBuilder.toString()));
/*     */           
/* 333 */           return true;
/*     */         }
/*     */       
/* 336 */       } catch (Exception ex) {
/*     */         
/* 338 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 342 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ChatFilterMacro processChatMessage(ChatFilterTemplate activeTemplate, String message) throws ScriptException {
/* 347 */     String chatClean = rp.a(message);
/*     */     
/* 349 */     OnChatProvider contextProvider = new OnChatProvider(null);
/* 350 */     contextProvider.initInstance(new String[] { message, chatClean });
/*     */     
/* 352 */     ChatFilterMacro macro = (ChatFilterMacro)activeTemplate.createInstance(false, ScriptContext.CHATFILTER.createActionContext((IVariableProvider)contextProvider));
/* 353 */     macro.play(false, true);
/*     */     
/* 355 */     return macro;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabledState) {
/* 365 */     this.enabled = enabledState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 375 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 381 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 387 */     return null;
/*     */   }
/*     */   
/*     */   public void init(File configPath) {}
/*     */   
/*     */   public void upgradeSettings(String version, File configPath, File oldConfigPath) {}
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\modules\chatfilter\ChatFilterManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */