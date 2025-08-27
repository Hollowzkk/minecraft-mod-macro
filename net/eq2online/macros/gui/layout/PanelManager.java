/*     */ package net.eq2online.macros.gui.layout;
/*     */ 
/*     */ import bib;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.handler.SettingsHandler;
/*     */ import net.eq2online.macros.interfaces.IEditablePanel;
/*     */ import net.eq2online.macros.interfaces.IObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PanelManager
/*     */   implements ISettingsObserver
/*     */ {
/*     */   private final Macros macros;
/*     */   private final bib mc;
/*  20 */   private final List<IEditablePanel> panels = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private LayoutPanelKeys keyboardLayout;
/*     */ 
/*     */   
/*     */   private LayoutPanelEvents eventLayout;
/*     */ 
/*     */   
/*     */   private boolean keyboardEditable;
/*     */ 
/*     */   
/*     */   private boolean lockMode;
/*     */ 
/*     */   
/*     */   private IEditablePanel.EditMode mode;
/*     */ 
/*     */ 
/*     */   
/*     */   public PanelManager(Macros macros, bib minecraft) {
/*  40 */     this.macros = macros;
/*  41 */     this.mc = minecraft;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createDefaultPanels() {
/*  46 */     if (this.keyboardLayout == null) {
/*     */       
/*  48 */       this.keyboardLayout = new LayoutPanelKeys(this.macros, this.mc, 9);
/*  49 */       this.eventLayout = new LayoutPanelEvents(this.macros, this.mc, 10);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  55 */     SettingsHandler settingsHandler = this.macros.getSettingsHandler();
/*     */     
/*  57 */     settingsHandler.registerObserver((IObserver)this.keyboardLayout);
/*  58 */     settingsHandler.registerObserver((IObserver)this.eventLayout);
/*  59 */     settingsHandler.registerObserver((IObserver)this);
/*     */     
/*  61 */     this.keyboardLayout.onClearSettings();
/*  62 */     this.eventLayout.onClearSettings();
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPanel(IEditablePanel panel) {
/*  67 */     if (!this.panels.contains(panel))
/*     */     {
/*  69 */       this.panels.add(panel);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tickInGui() {
/*  75 */     for (IEditablePanel panel : this.panels)
/*     */     {
/*  77 */       panel.tickInGui();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public LayoutPanelKeys getKeyboardLayout() {
/*  83 */     return this.keyboardLayout;
/*     */   }
/*     */ 
/*     */   
/*     */   public LayoutPanelEvents getEventLayout() {
/*  88 */     return this.eventLayout;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isKeyboardEditable() {
/*  93 */     return this.keyboardEditable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKeyboardEditable(boolean keyboardEditable) {
/*  98 */     this.keyboardEditable = keyboardEditable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void lockMode() {
/* 103 */     if (this.mode != IEditablePanel.EditMode.NONE)
/*     */     {
/* 105 */       this.lockMode = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void unlockMode() {
/* 111 */     this.lockMode = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public IEditablePanel.EditMode getMode() {
/* 116 */     return this.mode;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInMode(IEditablePanel.EditMode mode) {
/* 121 */     return (this.mode == mode);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMode(IEditablePanel.EditMode mode) {
/* 126 */     if (mode == IEditablePanel.EditMode.NONE && this.lockMode) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 131 */     this.lockMode = false;
/*     */     
/* 133 */     if (mode.isSettable()) {
/*     */       
/* 135 */       this.mode = mode;
/*     */     }
/* 137 */     else if (mode.isToggleable() || mode == IEditablePanel.EditMode.EDIT_BUTTONS || (mode == IEditablePanel.EditMode.EDIT_ALL && (this.keyboardEditable || mode == this.mode))) {
/*     */       
/* 139 */       this.mode = (this.mode == mode) ? IEditablePanel.EditMode.NONE : mode;
/*     */     } 
/*     */     
/* 142 */     for (IEditablePanel panel : this.panels)
/*     */     {
/* 144 */       panel.setMode(this.mode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClearSettings() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoadSettings(ISettingsStore settings) {
/* 156 */     this.keyboardEditable = settings.getSetting("keyboard.editable", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSaveSettings(ISettingsStore settings) {
/* 162 */     settings.setSetting("keyboard.editable", this.keyboardEditable);
/* 163 */     settings.setSettingComment("keyboard.editable", "0 to disable layout editing, 1 to enable the 'edit' button");
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\layout\PanelManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */