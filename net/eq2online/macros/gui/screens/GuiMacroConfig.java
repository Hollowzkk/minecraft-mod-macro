/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bib;
/*     */ import bja;
/*     */ import blk;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import com.mumfrey.liteloader.gl.GLClippingPlanes;
/*     */ import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.controls.GuiScrollBar;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxConfigs;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxAddConfig;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxConfirm;
/*     */ import net.eq2online.macros.gui.layout.PanelManager;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.res.ResourceLocations;
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
/*     */ 
/*     */ 
/*     */ public class GuiMacroConfig
/*     */   extends GuiScreenEx
/*     */   implements ConfigPanelHost
/*     */ {
/*     */   private final GuiScreenEx parentScreen;
/*     */   private final Macros macros;
/*     */   private final PanelManager panelManager;
/*     */   private final int sneakCode;
/*     */   private GuiListBoxConfigs configs;
/*     */   private GuiCheckBox chkAutoSwitch;
/*     */   private GuiControl btnOk;
/*     */   private GuiControl btnCancel;
/*     */   private GuiScrollBar scrollBar;
/*     */   private GuiMacroConfigPanel configPanel;
/*     */   private boolean disableConfigSwitch = false;
/*     */   private int lastMouseX;
/*     */   private int lastMouseY;
/*  82 */   private int suspendInput = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiMacroConfig(Macros macros, bib minecraft, GuiScreenEx parentScreen, boolean disableConfigSwitch) {
/*  92 */     super(minecraft);
/*     */     
/*  94 */     this.e = 999.0F;
/*     */     
/*  96 */     this.macros = macros;
/*  97 */     this.parentScreen = parentScreen;
/*  98 */     this.disableConfigSwitch = disableConfigSwitch;
/*     */     
/* 100 */     this.configPanel = new GuiMacroConfigPanel(macros, minecraft, this);
/* 101 */     this.panelManager = this.macros.getLayoutPanels();
/* 102 */     this.sneakCode = this.macros.getInputHandler().getSneakKeyCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void submit() {
/* 110 */     (this.macros.getSettings()).enableAutoConfigSwitch = this.chkAutoSwitch.checked;
/* 111 */     this.configPanel.onPanelHidden();
/* 112 */     this.macros.getSettingsHandler().onConfigChanged();
/* 113 */     this.j.a((blk)this.parentScreen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 122 */     this.suspendInput = 4;
/*     */     
/* 124 */     if (this.configs == null) {
/*     */       
/* 126 */       this.configs = new GuiListBoxConfigs(this.macros, this.j, 1, 4, 40, 174, this.m - 94);
/* 127 */       this
/* 128 */         .chkAutoSwitch = new GuiCheckBox(this.j, 21, 10, this.m - 50, I18n.get("options.option.enableautoswitch"), (this.macros.getSettings()).enableAutoConfigSwitch);
/* 129 */       this.scrollBar = new GuiScrollBar(this.j, 0, this.l - 24, 40, 20, this.m - 70, 0, 1000, GuiScrollBar.ScrollBarOrientation.VERTICAL);
/*     */       
/* 131 */       this.configs.l = !this.disableConfigSwitch;
/*     */     }
/*     */     else {
/*     */       
/* 135 */       this.configs.setSize(174, this.m - 94);
/* 136 */       this.chkAutoSwitch.setYPosition(this.m - 50);
/* 137 */       this.scrollBar.setSizeAndPosition(this.l - 24, 40, 20, this.m - 70);
/*     */     } 
/*     */     
/* 140 */     this.configs.refresh();
/*     */ 
/*     */     
/* 143 */     this.btnOk = new GuiControl(2, this.l - 64, this.m - 24, 60, 20, I18n.get("gui.ok"));
/* 144 */     this.btnCancel = new GuiControl(3, this.l - 128, this.m - 24, 60, 20, I18n.get("gui.cancel"));
/*     */ 
/*     */     
/* 147 */     addControl((GuiControl)this.configs);
/* 148 */     addControl((GuiControl)this.chkAutoSwitch);
/* 149 */     addControl((GuiControl)this.scrollBar);
/* 150 */     addControl(this.btnCancel);
/*     */     
/* 152 */     this.configPanel.onPanelResize(this);
/* 153 */     this.scrollBar.setMax(Math.max(0, this.configPanel.getContentHeight() - getHeight()));
/*     */     
/* 155 */     addControl(this.btnOk);
/*     */     
/* 157 */     super.b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 166 */     if (this.suspendInput > 0)
/*     */     {
/* 168 */       this.suspendInput--;
/*     */     }
/*     */     
/* 171 */     this.configPanel.onTick(this);
/* 172 */     this.panelManager.tickInGui();
/*     */     
/* 174 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bja guibutton) {
/* 185 */     if (guibutton.k == this.configs.k) {
/*     */       
/* 187 */       IListEntry<String> selectedItem = this.configs.getSelectedItem();
/*     */       
/* 189 */       if (selectedItem.getId() == -2) {
/*     */ 
/*     */         
/* 192 */         displayDialog((GuiDialogBox)new GuiDialogBoxAddConfig(this.macros, this.j, this));
/*     */         
/*     */         return;
/*     */       } 
/* 196 */       if (selectedItem.getCustomAction(true).equals("delete")) {
/*     */         
/* 198 */         String title = I18n.get("gui.delete");
/* 199 */         String prompt = I18n.get("param.action.confirmdelete");
/* 200 */         displayDialog((GuiDialogBox)new GuiDialogBoxConfirm(this.j, this, title, prompt, ((String)selectedItem.getData()).toString(), 0));
/*     */         
/*     */         return;
/*     */       } 
/* 204 */       this.macros.setActiveConfig((String)selectedItem.getData());
/*     */       
/* 206 */       if (this.configs.isDoubleClicked(true))
/*     */       {
/* 208 */         submit();
/*     */       }
/*     */     } 
/*     */     
/* 212 */     if (guibutton.k == this.btnOk.k)
/*     */     {
/* 214 */       submit();
/*     */     }
/*     */     
/* 217 */     if (guibutton.k == this.btnCancel.k)
/*     */     {
/* 219 */       this.j.a((blk)this.parentScreen);
/*     */     }
/*     */     
/* 222 */     if (guibutton.k == 4) {
/*     */       
/* 224 */       this.j.a((blk)new GuiEditReservedKeys(this.macros, this.j, this));
/* 225 */       b();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/* 236 */     if (keyCode == 1) {
/*     */       
/* 238 */       this.j.a((blk)this.parentScreen);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 243 */     if (keyCode == 28 || keyCode == 156 || (keyCode == InputHandler.KEY_ACTIVATE
/*     */       
/* 245 */       .j() && InputHandler.isKeyDown(this.sneakCode))) {
/*     */       
/* 247 */       submit();
/*     */       
/*     */       return;
/*     */     } 
/* 251 */     if (this.configPanel.handleKeyPress(this, keyChar, keyCode)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 256 */     if (keyCode == 200) {
/*     */       
/* 258 */       this.configs.up();
/* 259 */       a((bja)this.configs);
/*     */     }
/* 261 */     else if (keyCode == 208) {
/*     */       
/* 263 */       this.configs.down();
/* 264 */       a((bja)this.configs);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) {
/* 271 */     mouseWheelDelta /= 120;
/*     */     
/* 273 */     if (this.lastMouseY > 40 && this.lastMouseY < this.m - 54)
/*     */     {
/* 275 */       if (this.lastMouseX < 180) {
/*     */         
/* 277 */         while (mouseWheelDelta > 0) {
/*     */           
/* 279 */           this.configs.up();
/* 280 */           a((bja)this.configs);
/* 281 */           mouseWheelDelta--;
/*     */         } 
/*     */         
/* 284 */         while (mouseWheelDelta < 0)
/*     */         {
/* 286 */           this.configs.down();
/* 287 */           a((bja)this.configs);
/* 288 */           mouseWheelDelta++;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 293 */         while (mouseWheelDelta > 0) {
/*     */           
/* 295 */           this.scrollBar.setValue(this.scrollBar.getValue() - 30);
/* 296 */           mouseWheelDelta--;
/*     */         } 
/*     */         
/* 299 */         while (mouseWheelDelta < 0) {
/*     */           
/* 301 */           this.scrollBar.setValue(this.scrollBar.getValue() + 30);
/* 302 */           mouseWheelDelta++;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 311 */     if (this.suspendInput > 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 316 */     if (mouseX > 182 && mouseY > 40 && mouseY < this.m - 40)
/*     */     {
/* 318 */       this.configPanel.mousePressed(this, mouseX - 182, mouseY - getScrollPos(), button);
/*     */     }
/*     */     
/* 321 */     if (mouseX > this.l - 20 && mouseY < 20)
/*     */     {
/* 323 */       a((bja)this.btnCancel);
/*     */     }
/*     */     
/* 326 */     super.a(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float f) {
/* 332 */     this.lastMouseX = mouseX;
/* 333 */     this.lastMouseY = mouseY;
/*     */     
/* 335 */     if (this.j.f == null) {
/*     */       
/* 337 */       c();
/* 338 */       GL.glClear(256);
/*     */     } 
/*     */     
/* 341 */     drawScreenWithEnabledState(mouseX, mouseY, f, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreenWithEnabledState(int mouseX, int mouseY, float partialTicks, boolean enabled) {
/* 347 */     this.configs.setEnabled((!this.disableConfigSwitch && enabled));
/*     */     
/* 349 */     if (enabled)
/*     */     {
/* 351 */       drawBackground();
/*     */     }
/*     */ 
/*     */     
/* 355 */     this.renderer.drawStringWithEllipsis(I18n.get("macro.currentconfig", new Object[] { this.macros.getActiveConfigName() }), 186, 7, this.l - 210, 4259648);
/*     */ 
/*     */     
/* 358 */     a(this.q, I18n.get("options.title"), 90, 7, 16776960);
/* 359 */     this.renderer.drawTexturedModalRect(ResourceLocations.MAIN, this.l - 17, 5, this.l - 5, 17, 104, 104, 128, 128);
/*     */     
/* 361 */     c(this.q, I18n.get("options.selectconfig"), 8, 26, 16776960);
/* 362 */     c(this.q, I18n.get("options.option.title"), 188, 26, 16776960);
/*     */     
/* 364 */     this.btnOk.a(this.j, mouseX, mouseY, partialTicks);
/* 365 */     this.btnCancel.a(this.j, mouseX, mouseY, partialTicks);
/* 366 */     this.scrollBar.a(this.j, mouseX, mouseY, partialTicks);
/* 367 */     this.configs.a(this.j, mouseX, mouseY, partialTicks);
/* 368 */     this.chkAutoSwitch.a(this.j, mouseX, mouseY, partialTicks);
/*     */ 
/*     */     
/* 371 */     GLClippingPlanes.glEnableVerticalClipping(40, this.m - 30);
/*     */     
/* 373 */     int yPos = getScrollPos();
/* 374 */     GL.glPushMatrix();
/* 375 */     GL.glTranslatef(182.0F, yPos, 0.0F);
/*     */     
/* 377 */     this.configPanel.drawPanel(this, mouseX - 182, mouseY - yPos, partialTicks);
/*     */     
/* 379 */     GL.glPopMatrix();
/*     */     
/* 381 */     GLClippingPlanes.glDisableClipping();
/*     */     
/* 383 */     if (!enabled) drawBackground();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getScrollPos() {
/* 391 */     return 44 - this.scrollBar.getValue();
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
/*     */   private void drawBackground() {
/* 404 */     int bgCol1 = (this.j.f != null) ? -1342177280 : -1728053248, bgCol2 = -1607257293;
/*     */     
/* 406 */     a(2, 2, 180, 20, bgCol1);
/* 407 */     a(182, 2, this.l - 22, 20, bgCol1);
/* 408 */     a(this.l - 20, 2, this.l - 2, 20, bgCol1);
/*     */     
/* 410 */     a(2, 22, 180, 38, bgCol2);
/* 411 */     a(2, 38, 180, this.m - 28, bgCol1);
/* 412 */     a(182, 22, this.l - 2, 38, bgCol2);
/* 413 */     a(182, 38, this.l - 2, this.m - 28, bgCol1);
/*     */     
/* 415 */     a(2, this.m - 26, this.l - 2, this.m - 2, bgCol1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogClosed(GuiDialogBox dialog) {
/* 424 */     if (dialog.getResult() == GuiDialogBox.DialogResult.OK) {
/*     */       
/* 426 */       if (dialog instanceof GuiDialogBoxAddConfig) {
/*     */         
/* 428 */         GuiDialogBoxAddConfig addConfigDialog = (GuiDialogBoxAddConfig)dialog;
/*     */         
/* 430 */         this.macros.addConfig(addConfigDialog.getNewConfigName(), addConfigDialog.copySettings);
/* 431 */         this.macros.setActiveConfig(addConfigDialog.getNewConfigName());
/*     */       } 
/*     */       
/* 434 */       if (dialog instanceof GuiDialogBoxConfirm) {
/*     */         
/* 436 */         this.macros.setActiveConfig("");
/* 437 */         this.macros.deleteConfig(((String)this.configs.getSelectedItem().getData()).toString());
/*     */       } 
/*     */     } 
/*     */     
/* 441 */     this.configs.refresh();
/* 442 */     super.onDialogClosed(dialog);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <TModClass extends com.mumfrey.liteloader.LiteMod> TModClass getMod() {
/* 448 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 459 */     return this.l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 465 */     return this.m - 70;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiMacroConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */