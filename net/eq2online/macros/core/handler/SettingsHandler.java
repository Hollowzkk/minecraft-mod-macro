/*     */ package net.eq2online.macros.core.handler;
/*     */ 
/*     */ import bib;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.settings.Settings;
/*     */ import net.eq2online.macros.interfaces.IConfigObserver;
/*     */ import net.eq2online.macros.interfaces.IConfigs;
/*     */ import net.eq2online.macros.interfaces.IObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SettingsHandler
/*     */ {
/*     */   private final IConfigs configs;
/*     */   private final Settings settings;
/*  24 */   private final List<ISettingsObserver> settingsObservers = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   private final List<IConfigObserver> configObservers = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public SettingsHandler(Macros macros, bib minecraft) {
/*  33 */     this.configs = (IConfigs)macros;
/*  34 */     this.settings = new Settings(macros, minecraft);
/*  35 */     registerObserver((IObserver)this.settings);
/*     */   }
/*     */ 
/*     */   
/*     */   public Settings getSettings() {
/*  40 */     return this.settings;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerObserver(IObserver observer) {
/*  45 */     if (observer instanceof ISettingsObserver)
/*     */     {
/*  47 */       registerSettingsObserver((ISettingsObserver)observer);
/*     */     }
/*     */     
/*  50 */     if (observer instanceof IConfigObserver)
/*     */     {
/*  52 */       registerConfigObserver((IConfigObserver)observer);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerSettingsObserver(ISettingsObserver observer) {
/*  63 */     if (!this.settingsObservers.contains(observer))
/*     */     {
/*  65 */       this.settingsObservers.add(observer);
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
/*     */   private void registerConfigObserver(IConfigObserver observer) {
/*  77 */     if (!this.configObservers.contains(observer))
/*     */     {
/*  79 */       this.configObservers.add(observer);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClear() {
/*  90 */     for (ISettingsObserver settingsObserver : this.settingsObservers)
/*     */     {
/*  92 */       settingsObserver.onClearSettings();
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
/*     */   public void onLoad(ISettingsStore settings) {
/* 104 */     for (ISettingsObserver settingsObserver : this.settingsObservers)
/*     */     {
/* 106 */       settingsObserver.onLoadSettings(settings);
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
/*     */   public void onSave(ISettingsStore settings) {
/* 119 */     for (ISettingsObserver settingsObserver : this.settingsObservers)
/*     */     {
/* 121 */       settingsObserver.onSaveSettings(settings);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConfigChanged() {
/* 132 */     for (IConfigObserver configObserver : this.configObservers)
/*     */     {
/* 134 */       configObserver.onConfigChanged(this.configs);
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
/*     */ 
/*     */   
/*     */   public void onConfigAdded(String configName, boolean copy) {
/* 149 */     for (IConfigObserver configObserver : this.configObservers)
/*     */     {
/* 151 */       configObserver.onConfigAdded(this.configs, configName, copy);
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
/*     */   public void onConfigRemoved(String configName) {
/* 164 */     for (IConfigObserver configObserver : this.configObservers)
/*     */     {
/* 166 */       configObserver.onConfigRemoved(this.configs, configName);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\handler\SettingsHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */