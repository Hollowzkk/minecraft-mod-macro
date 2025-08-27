/*     */ package net.eq2online.macros.core.handler;
/*     */ 
/*     */ import bib;
/*     */ import cep;
/*     */ import ceq;
/*     */ import cez;
/*     */ import com.google.common.base.Charsets;
/*     */ import com.mumfrey.liteloader.util.log.LiteLoaderLogger;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.mixin.II18n;
/*     */ import net.eq2online.macros.core.mixin.ILocale;
/*     */ import net.eq2online.macros.interfaces.ILocalisationChangeListener;
/*     */ import net.eq2online.macros.interfaces.ILocalisationProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.util.Util;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalisationHandler
/*     */   implements ILocalisationProvider, ceq
/*     */ {
/*     */   private final bib mc;
/*  32 */   private final List<ILocalisationChangeListener> listeners = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   private final Properties localisationTable = new Properties();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   private String currentLanguageCode = "en_gb";
/*     */ 
/*     */   
/*     */   public LocalisationHandler(bib minecraft) {
/*  46 */     this.mc = minecraft;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerListener(ILocalisationChangeListener listener) {
/*  51 */     if (!this.listeners.contains(listener))
/*     */     {
/*  53 */       this.listeners.add(listener);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  59 */     updateLocalisation();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisconnected() {
/*  64 */     updateLocalisation();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateLocalisation() {
/*  70 */     cez currentLanguage = this.mc.Q().c();
/*  71 */     if (currentLanguage != null && !currentLanguage.a().equals(this.currentLanguageCode)) {
/*     */       
/*  73 */       this.currentLanguageCode = currentLanguage.a();
/*  74 */       initialiseLocalisationTable(this.currentLanguageCode);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialiseLocalisationTable(String currentLanguage) {
/*  83 */     resetLocalisationTable();
/*     */     
/*  85 */     if (!"en_gb".equals(currentLanguage))
/*     */     {
/*  87 */       readLocalisationTables(currentLanguage, true);
/*     */     }
/*     */     
/*  90 */     for (ILocalisationChangeListener listener : this.listeners)
/*     */     {
/*  92 */       listener.onLocaleChanged(currentLanguage);
/*     */     }
/*     */     
/*  95 */     Map<String, String> translateTable = ((ILocale)II18n.getCurrentLocale()).getTranslationTable();
/*  96 */     if (translateTable != null) {
/*     */       
/*  98 */       setTranslation(translateTable, "key.categories.macros");
/*  99 */       setTranslation(translateTable, "key.macros");
/* 100 */       setTranslation(translateTable, "key.macro_override");
/*     */     } 
/*     */     
/* 103 */     for (ScriptContext context : ScriptContext.getAvailableContexts())
/*     */     {
/* 105 */       context.setLanguage(currentLanguage);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetLocalisationTable() {
/* 111 */     this.localisationTable.clear();
/* 112 */     readLocalisationTables("en_gb", false);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readLocalisationTables(String lang, boolean append) {
/* 117 */     Log.info("loading localisations for {0}", new Object[] { lang });
/* 118 */     readLocalisationTablesFromResource("/lang/macros/" + lang + ".lang", append);
/* 119 */     readLocalisationTablesFromResource("/lang/macros/events/" + lang + ".lang", append);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readLocalisationTablesFromResource(String path, boolean append) {
/* 124 */     InputStream is = MacroModCore.class.getResourceAsStream(path);
/* 125 */     if (is == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 130 */     if (append) {
/*     */       
/* 132 */       appendLocalisationsToTable(is);
/*     */       
/*     */       return;
/*     */     } 
/* 136 */     loadLocalisationTable(is);
/*     */   }
/*     */ 
/*     */   
/*     */   private void appendLocalisationsToTable(InputStream is) {
/* 141 */     InputStreamReader reader = null;
/*     */ 
/*     */     
/*     */     try {
/* 145 */       reader = new InputStreamReader(is, Charsets.UTF_8);
/* 146 */       BufferedReader bufferedreader = new BufferedReader(reader);
/*     */       
/* 148 */       for (String s = bufferedreader.readLine(); s != null; s = bufferedreader.readLine())
/*     */       {
/* 150 */         String[] as = s.split("=", 2);
/* 151 */         if (as != null && as.length == 2)
/*     */         {
/* 153 */           this.localisationTable.put(as[0], as[1]);
/*     */         }
/*     */       }
/*     */     
/* 157 */     } catch (Exception ex) {
/*     */       
/* 159 */       ex.printStackTrace();
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 165 */         if (reader != null)
/*     */         {
/* 167 */           reader.close();
/*     */         }
/*     */       }
/* 170 */       catch (IOException ex1) {
/*     */         
/* 172 */         ex1.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadLocalisationTable(InputStream is) {
/* 179 */     InputStreamReader reader = null;
/*     */ 
/*     */     
/*     */     try {
/* 183 */       reader = new InputStreamReader(is, Charsets.UTF_8);
/* 184 */       this.localisationTable.load(reader);
/*     */     }
/* 186 */     catch (Exception ex) {
/*     */       
/* 188 */       ex.printStackTrace();
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 194 */         if (reader != null)
/*     */         {
/* 196 */           reader.close();
/*     */         }
/*     */       }
/* 199 */       catch (IOException ex1) {
/*     */         
/* 201 */         ex1.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setTranslation(Map<String, String> translateTable, String key) {
/* 212 */     translateTable.put(key, get(key));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(cep var1) {
/*     */     try {
/* 220 */       this.currentLanguageCode = this.mc.Q().c().a();
/* 221 */       initialiseLocalisationTable(this.currentLanguageCode);
/*     */     }
/* 223 */     catch (Exception ex) {
/*     */       
/* 225 */       LiteLoaderLogger.warning("An error occurred updating the Macros localisation data", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRaw(String key) {
/* 236 */     return this.localisationTable.getProperty(key, key);
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
/*     */   public String get(String key) {
/* 248 */     String string = this.localisationTable.getProperty(key, key);
/* 249 */     return Util.convertAmpCodes(string);
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
/*     */   public String get(String key, Object... params) {
/* 263 */     return String.format(get(key), params);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\handler\LocalisationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */