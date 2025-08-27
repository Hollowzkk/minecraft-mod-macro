/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bib;
/*     */ import bja;
/*     */ import blk;
/*     */ import cgp;
/*     */ import cgt;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.gui.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxConfigs;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiLayoutPatch;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxAddConfig;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxConfirm;
/*     */ import net.eq2online.macros.gui.hook.CustomScreenManager;
/*     */ import net.eq2online.macros.gui.interfaces.IMinimisableHost;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanelEvents;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanelKeys;
/*     */ import net.eq2online.macros.gui.layout.PanelManager;
/*     */ import net.eq2online.macros.gui.repl.GuiMacroRepl;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.interfaces.IEditablePanel;
/*     */ import net.eq2online.macros.interfaces.ILayoutPanel;
/*     */ import net.eq2online.macros.interfaces.ILayoutWidget;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import qf;
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
/*     */ public class GuiMacroBind
/*     */   extends GuiDesignerBase
/*     */   implements IMinimisableHost
/*     */ {
/*     */   private static final String MENU_KEYS = "keys";
/*     */   private static final String MENU_EVENTS = "events";
/*     */   private static final String MENU_BUTTONS = "buttons";
/*     */   private static final String MENU_OPTIONS = "options";
/*     */   private static final String MENU_PERMISSIONS = "perms";
/*     */   private static final String MENU_EDIT_GUI = "editgui";
/*     */   private static final String MENU_CUSTOM_PREFIX = "custom";
/*     */   protected static final int TOOLTIP_FOREGROUND = -16711936;
/*     */   protected static final int TOOLTIP_BACKGROUND = -1157627904;
/*     */   private final InputHandler inputHandler;
/*  62 */   private int mouseNavHoverTime = 0;
/*  63 */   private int acceleratorTipTime = 0;
/*     */ 
/*     */   
/*     */   private LayoutPanelEvents eventLayout;
/*     */ 
/*     */   
/*     */   private LayoutPanelKeys keyboardLayout;
/*     */ 
/*     */   
/*     */   private GuiListBoxConfigs configs;
/*     */ 
/*     */   
/*     */   private boolean waitingFeedback;
/*     */   
/*     */   private boolean unMinimise;
/*     */   
/*     */   private boolean minimised;
/*     */ 
/*     */   
/*     */   public GuiMacroBind(Macros macros, bib minecraft, boolean resetPage, boolean unMinimise) {
/*  83 */     this(macros, minecraft, resetPage, unMinimise, (blk)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiMacroBind(Macros macros, bib minecraft, boolean resetPage, boolean unMinimise, blk lastScreen) {
/*  88 */     super(macros, minecraft, 3, (resetPage && !(macros.getSettings()).rememberBindPage) ? 0 : GuiDesignerBase.page);
/*     */     
/*  90 */     if (resetPage && !this.settings.rememberBindPage)
/*     */     {
/*  92 */       GuiDesignerBase.page = 0;
/*     */     }
/*     */     
/*  95 */     this.drawMenuButton = true;
/*  96 */     this.bannerCentred = false;
/*  97 */     this.drawToolButtons = true;
/*  98 */     this.drawActionButtons = true;
/*  99 */     this.buttonPanelPage = 2;
/*     */     
/* 101 */     init();
/*     */     
/* 103 */     this.lastScreen = lastScreen;
/* 104 */     this.inputHandler = this.macros.getInputHandler();
/* 105 */     this.unMinimise = unMinimise;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiMacroBind(Macros macros, bib minecraft, int initialPage, blk lastScreen) {
/* 110 */     super(macros, minecraft, 3, initialPage);
/*     */     
/* 112 */     this.drawMenuButton = true;
/* 113 */     this.bannerCentred = false;
/* 114 */     this.drawToolButtons = true;
/* 115 */     this.drawActionButtons = true;
/* 116 */     this.buttonPanelPage = 2;
/*     */     
/* 118 */     init();
/*     */     
/* 120 */     this.lastScreen = lastScreen;
/* 121 */     this.inputHandler = this.macros.getInputHandler();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiScreenEx getDelegate() {
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() {
/* 133 */     this.layout = this.macros.getLayoutManager().getBoundLayout("playback", false);
/*     */     
/* 135 */     super.init();
/*     */     
/* 137 */     PanelManager panels = this.macros.getLayoutPanels();
/* 138 */     this.keyboardLayout = panels.getKeyboardLayout();
/* 139 */     this.eventLayout = panels.getEventLayout();
/*     */     
/* 141 */     this.dropdown.addItem("keys", "§o" + I18n.get("macro.trigger.type.keys") + "§r", "Ctrl+1")
/* 142 */       .addItem("events", "§o" + I18n.get("macro.trigger.type.events") + "§r", "Ctrl+2")
/* 143 */       .addItem("buttons", "§o" + I18n.get("macro.trigger.type.control") + "§r", "Ctrl+3")
/* 144 */       .addSeparator()
/* 145 */       .addItem("options", I18n.get("tooltip.options"), "Ctrl+S", 26, 0)
/* 146 */       .addItem("perms", I18n.get("tooltip.perms"), "Ctrl+P", 32, 20)
/* 147 */       .addSeparator()
/* 148 */       .addItem("editgui", I18n.get("tooltip.guiedit"), "Ctrl+G", 26, 16);
/*     */     
/* 150 */     CustomScreenManager customScreenManager = this.macros.getCustomScreenManager();
/* 151 */     if (customScreenManager.hasCustomScreens()) {
/*     */       
/* 153 */       this.dropdown.addSeparator();
/* 154 */       for (CustomScreenManager.CustomScreen customScreen : customScreenManager.getCustomScreens())
/*     */       {
/* 156 */         this.dropdown.addItem("custom" + customScreen.getIndex(), customScreen.toString());
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
/*     */   
/*     */   public void captureWidgetAt(int mouseX, int mouseY) {
/* 171 */     this.capturedWidget = null;
/*     */     
/* 173 */     if ((this.capturedWidget = this.keyboardLayout.getWidgetAt(mouseX, mouseY)) != null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 178 */     if ((this.capturedWidget = this.eventLayout.getWidgetAt(mouseX, mouseY)) != null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 183 */     this.capturedWidget = this.buttonsLayout.getWidgetAt(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 192 */     this.layout = this.macros.getLayoutManager().getBoundLayout("playback", false);
/* 193 */     this.buttonsLayout.setLayout(this.layout);
/*     */     
/* 195 */     super.b();
/*     */ 
/*     */     
/* 198 */     if (!this.settings.simpleGui) {
/*     */       
/* 200 */       this.keyboardLayout.connect(this);
/* 201 */       this.n.add(this.keyboardLayout);
/*     */       
/* 203 */       this.eventLayout.connect(this);
/* 204 */       this.n.add(this.eventLayout);
/*     */       
/* 206 */       if (this.configs == null) {
/*     */         
/* 208 */         this.configs = new GuiListBoxConfigs(this.macros, this.j, 20, this.l - 224, this.m - 40);
/* 209 */         this.configs.backColour = -16777202;
/* 210 */         this.configs.m = false;
/*     */       }
/*     */       else {
/*     */         
/* 214 */         this.configs.setSize(this.l - 224, this.m - 40);
/* 215 */         this.configs.m = false;
/*     */       } 
/*     */       
/* 218 */       this.configs.refresh();
/*     */     }
/*     */     else {
/*     */       
/* 222 */       beginTweening(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onHeaderClick() {
/* 229 */     if (this.settings.configNameLinksToSettings) {
/*     */       
/* 231 */       this.j.a((blk)new GuiMacroConfig(this.macros, this.j, this, false));
/*     */     }
/* 233 */     else if (this.configs != null) {
/*     */       
/* 235 */       this.configs.m = !this.configs.m;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void m() {
/* 245 */     this.keyboardLayout.release();
/* 246 */     this.eventLayout.release();
/*     */     
/* 248 */     super.m();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 257 */     this.minimised = (this.macros.getMinimised(false) != null);
/* 258 */     if (this.minimised && this.unMinimise) {
/*     */       
/* 260 */       this.minimised = false;
/* 261 */       this.macros.getMinimised(true).show(this);
/*     */       
/*     */       return;
/*     */     } 
/* 265 */     if (this.configs != null)
/*     */     {
/* 267 */       this.configs.updateCounter++;
/*     */     }
/*     */     
/* 270 */     DesignableGuiLayout layout = this.buttonsLayout.getLayout();
/* 271 */     if (layout != null)
/*     */     {
/* 273 */       layout.onTick(this.updateCounter);
/*     */     }
/*     */     
/* 276 */     super.e();
/*     */     
/* 278 */     boolean flash = (this.minimised && this.updateCounter % 20 < 10);
/* 279 */     this.btnEditFile.setColours(flash ? -16777216 : -1118720, flash ? -1118720 : -1342177280);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onEditFile() {
/* 285 */     if (this.minimised) {
/*     */       
/* 287 */       this.unMinimise = true;
/*     */       
/*     */       return;
/*     */     } 
/* 291 */     super.onEditFile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleWidgetClick(ILayoutPanel<? extends ILayoutWidget> source, int keyCode, boolean bindable) {
/* 302 */     if (bindable) {
/*     */       
/* 304 */       this.macros.save();
/* 305 */       this.keyboardLayout.release();
/* 306 */       this.eventLayout.release();
/* 307 */       this.j.a((blk)GuiMacroEdit.create(this.macros, this.j, (blk)this, keyCode));
/*     */     }
/* 309 */     else if (source == this.buttonsLayout) {
/*     */       
/* 311 */       this.panelManager.setMode(IEditablePanel.EditMode.EDIT_BUTTONS);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 318 */     boolean configsHide = false;
/*     */     
/* 320 */     if (this.configs != null && this.configs.m) {
/*     */       
/* 322 */       GuiControlEx.HandledState handled = this.configs.mousePressed(mouseX, mouseY, button);
/* 323 */       if (handled == GuiControlEx.HandledState.ACTION_PERFORMED) {
/*     */         
/* 325 */         this.j.U().a((cgt)cgp.a(qf.ic, 1.0F));
/* 326 */         a((bja)this.configs);
/*     */         
/*     */         return;
/*     */       } 
/* 330 */       if (handled == GuiControlEx.HandledState.HANDLED) {
/*     */         return;
/*     */       }
/*     */       
/* 334 */       if (button == 0)
/*     */       {
/* 336 */         configsHide = true;
/*     */       }
/*     */     } 
/*     */     
/* 340 */     super.a(mouseX, mouseY, button);
/*     */     
/* 342 */     if (this.configs != null && configsHide)
/*     */     {
/* 344 */       this.configs.m = false;
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
/*     */   protected void a(char keyChar, int keyCode) {
/* 356 */     if (this.updateCounter < 2) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 361 */     int currentPage = getPage();
/*     */     
/* 363 */     if (this.configs != null && this.configs.m) {
/*     */       
/* 365 */       GuiControlEx.HandledState result = this.configs.keyTyped(keyChar, keyCode);
/* 366 */       if (result == GuiControlEx.HandledState.ACTION_PERFORMED) {
/*     */         
/* 368 */         a((bja)this.configs);
/*     */         return;
/*     */       } 
/* 371 */       if (result == GuiControlEx.HandledState.HANDLED) {
/*     */         return;
/*     */       }
/*     */     } else {
/*     */       
/* 376 */       if (currentPage == 0 && this.keyboardLayout.keyPressed(keyChar, keyCode)) {
/*     */         return;
/*     */       }
/*     */       
/* 380 */       if (currentPage == 1 && this.eventLayout.keyPressed(keyChar, keyCode)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 385 */     super.a(keyChar, keyCode);
/*     */     
/* 387 */     if (keyCode == 1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 392 */     int override = this.inputHandler.getOverrideKeyCode();
/* 393 */     int sneak = this.inputHandler.getSneakKeyCode();
/*     */     
/* 395 */     if (InputHandler.isKeyDown(override, new int[] { 29, 157 }) && !this.settings.simpleGui) {
/*     */       
/* 397 */       if (keyCode != override && handleAccelerators(keyChar, keyCode))
/*     */       {
/* 399 */         this.acceleratorTipTime = 0;
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 404 */     if ((currentPage == 0 || this.settings.bindIgnoresPage) && keyCode > 0 && keyCode != override) {
/*     */       
/* 406 */       if (keyCode == sneak) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 411 */       if (keyCode == InputHandler.KEY_ACTIVATE.j() && InputHandler.isKeyDown(sneak)) {
/*     */         
/* 413 */         this.j.a((blk)new GuiMacroConfig(this.macros, this.j, this, false));
/*     */         
/*     */         return;
/*     */       } 
/* 417 */       handleWidgetClick((ILayoutPanel<? extends ILayoutWidget>)null, keyCode, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean handleAccelerators(char keyChar, int keyCode) {
/* 423 */     if (keyCode == 209 || keyCode == 205) {
/*     */       
/* 425 */       onPageUpClick();
/* 426 */       return true;
/*     */     } 
/* 428 */     if (keyCode == 201 || keyCode == 203) {
/*     */       
/* 430 */       onPageDownClick();
/* 431 */       return true;
/*     */     } 
/* 433 */     if (keyCode == 208 || keyCode == 200) {
/*     */       
/* 435 */       if (this.configs != null)
/*     */       {
/* 437 */         this.configs.m = !this.configs.m;
/*     */       }
/* 439 */       return true;
/*     */     } 
/* 441 */     if (keyCode == 45) {
/*     */       
/* 443 */       this.panelManager.setMode(IEditablePanel.EditMode.MOVE);
/* 444 */       return true;
/*     */     } 
/* 446 */     if (keyCode == 46) {
/*     */       
/* 448 */       this.panelManager.setMode(IEditablePanel.EditMode.COPY);
/* 449 */       return true;
/*     */     } 
/* 451 */     if (keyCode == 32 || keyCode == 211) {
/*     */       
/* 453 */       this.panelManager.setMode(IEditablePanel.EditMode.DELETE);
/* 454 */       return true;
/*     */     } 
/* 456 */     if (keyCode == 20) {
/*     */       
/* 458 */       onMiniButtonClicked(this.btnEditFile);
/* 459 */       return true;
/*     */     } 
/* 461 */     if (keyCode == 31) {
/*     */       
/* 463 */       onMiniButtonClicked(this.btnOptions);
/* 464 */       return true;
/*     */     } 
/* 466 */     if (keyCode == 53 && this.j.aa() != null && (this.j.aa()).l != null) {
/*     */       
/* 468 */       this.j.a((blk)new GuiMacroRepl(this.macros, this.j, this));
/* 469 */       return true;
/*     */     } 
/* 471 */     if (keyCode == 2) {
/*     */       
/* 473 */       onMenuItemClicked("keys");
/*     */     }
/* 475 */     else if (keyCode == 3) {
/*     */       
/* 477 */       onMenuItemClicked("events");
/*     */     }
/* 479 */     else if (keyCode == 4) {
/*     */       
/* 481 */       onMenuItemClicked("buttons");
/*     */     }
/* 483 */     else if (keyCode == 25) {
/*     */       
/* 485 */       onMenuItemClicked("perms");
/*     */     }
/* 487 */     else if (keyCode == 34) {
/*     */       
/* 489 */       onMenuItemClicked("editgui");
/*     */     }
/* 491 */     else if (keyCode == 23) {
/*     */       
/* 493 */       onMiniButtonClicked(this.btnIcons);
/*     */     } 
/*     */     
/* 496 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bja guibutton) {
/* 502 */     if (this.configs != null && guibutton.k == this.configs.k) {
/*     */       
/* 504 */       IListEntry<String> selectedItem = this.configs.getSelectedItem();
/*     */       
/* 506 */       if (selectedItem.getId() == -2) {
/*     */         
/* 508 */         this.waitingFeedback = true;
/* 509 */         displayDialog((GuiDialogBox)new GuiDialogBoxAddConfig(this.macros, this.j, this));
/*     */         
/*     */         return;
/*     */       } 
/* 513 */       if (selectedItem.getCustomAction(true).equals("delete")) {
/*     */         
/* 515 */         this.waitingFeedback = true;
/* 516 */         String title = I18n.get("gui.delete");
/* 517 */         String prompt = I18n.get("param.action.confirmdelete");
/* 518 */         displayDialog((GuiDialogBox)new GuiDialogBoxConfirm(this.j, this, title, prompt, ((String)selectedItem.getData()).toString(), 2));
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 523 */       this.macros.setActiveConfig((String)selectedItem.getData());
/*     */       
/* 525 */       if (this.configs.isDoubleClicked(true))
/*     */       {
/* 527 */         this.configs.m = false;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogClosed(GuiDialogBox dialog) {
/* 538 */     if (this.waitingFeedback && this.configs != null) {
/*     */       
/* 540 */       this.waitingFeedback = false;
/*     */       
/* 542 */       if (dialog.getResult() == GuiDialogBox.DialogResult.OK) {
/*     */         
/* 544 */         if (dialog instanceof GuiDialogBoxAddConfig) {
/*     */           
/* 546 */           GuiDialogBoxAddConfig addConfigDialog = (GuiDialogBoxAddConfig)dialog;
/*     */           
/* 548 */           this.macros.addConfig(addConfigDialog.getNewConfigName(), addConfigDialog.copySettings);
/* 549 */           this.macros.setActiveConfig(addConfigDialog.getNewConfigName());
/*     */         } 
/*     */         
/* 552 */         if (dialog instanceof GuiDialogBoxConfirm && dialog.getId() == 2) {
/*     */           
/* 554 */           this.macros.setActiveConfig("");
/* 555 */           this.macros.deleteConfig(((String)this.configs.getSelectedItem().getData()).toString());
/*     */         } 
/*     */       } 
/*     */       
/* 559 */       this.configs.refresh();
/* 560 */       this.j.a((blk)this);
/*     */     }
/*     */     else {
/*     */       
/* 564 */       super.onDialogClosed(dialog);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) {
/* 571 */     if (this.configs != null && this.configs.m) {
/*     */       
/* 573 */       mouseWheelDelta /= 120;
/*     */       
/* 575 */       while (mouseWheelDelta > 0) {
/*     */         
/* 577 */         this.configs.up();
/* 578 */         a((bja)this.configs);
/* 579 */         mouseWheelDelta--;
/*     */       } 
/*     */       
/* 582 */       while (mouseWheelDelta < 0) {
/*     */         
/* 584 */         this.configs.down();
/* 585 */         a((bja)this.configs);
/* 586 */         mouseWheelDelta++;
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
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTicks) {
/* 601 */     GL.glEnableAlphaTest();
/* 602 */     GL.glAlphaFunc(516, 0.1F);
/*     */     
/* 604 */     super.a(mouseX, mouseY, partialTicks);
/*     */     
/* 606 */     if (this.configs != null && this.configs.m) {
/*     */       
/* 608 */       a(201, 1, this.l - 21, this.configs.getHeight() + 22, -1);
/* 609 */       this.renderer.drawTitle(this.banner, this.bannerCentred, 202, 2, this.l - (this.drawMinButton ? 44 : 22), this.bannerColour, -16777216);
/*     */       
/* 611 */       this.configs.a(this.j, mouseX, mouseY, partialTicks);
/*     */     } 
/*     */     
/* 614 */     handleHoverNav(mouseX, mouseY, partialTicks);
/* 615 */     drawAcceleratorTips(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleHoverNav(int mouseX, int mouseY, float partialTicks) {
/* 621 */     GuiScreenWithHeader.NavAction navAction = GuiScreenWithHeader.NavAction.NONE;
/*     */ 
/*     */     
/* 624 */     if (this.keyboardLayout.isDragging() || this.eventLayout.isDragging() || this.buttonsLayout.isDragging())
/*     */     {
/* 626 */       navAction = getNavAction(mouseX, mouseY);
/*     */     }
/*     */ 
/*     */     
/* 630 */     if (navAction == GuiScreenWithHeader.NavAction.NONE) {
/*     */       
/* 632 */       this.mouseNavHoverTime = 0;
/*     */ 
/*     */     
/*     */     }
/* 636 */     else if (this.mouseNavHoverTime == 0) {
/*     */       
/* 638 */       this.mouseNavHoverTime = this.updateCounter;
/*     */     }
/* 640 */     else if (this.updateCounter - this.mouseNavHoverTime > 10) {
/*     */ 
/*     */       
/* 643 */       this.mouseNavHoverTime = this.updateCounter + 10;
/*     */       
/* 645 */       if (navAction == GuiScreenWithHeader.NavAction.PREVIOUS) {
/*     */         
/* 647 */         onPageDownClick();
/*     */       }
/*     */       else {
/*     */         
/* 651 */         onPageUpClick();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawAcceleratorTips(int mouseX, int mouseY, float partialTicks) {
/* 659 */     if (this.settings.simpleGui) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 664 */     int override = this.inputHandler.getOverrideKeyCode();
/* 665 */     if (!InputHandler.isKeyDown(override, new int[] { 29, 157 })) {
/*     */       
/* 667 */       this.acceleratorTipTime = 0;
/*     */ 
/*     */     
/*     */     }
/* 671 */     else if (this.acceleratorTipTime == 0) {
/*     */       
/* 673 */       this.acceleratorTipTime = this.updateCounter;
/*     */     }
/* 675 */     else if (this.updateCounter - this.acceleratorTipTime > 20) {
/*     */       
/* 677 */       drawAcceleratorTips(-16711936, -1157627904);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawAcceleratorTips(int colour, int backColour) {
/* 684 */     drawTooltip("LEFT", 37, 30, colour, backColour);
/* 685 */     drawTooltip("RIGHT", 195, 30, colour, backColour);
/* 686 */     drawTooltip("C", 12, this.m - 6, colour, backColour);
/* 687 */     drawTooltip("X", 32, this.m - 6, colour, backColour);
/* 688 */     drawTooltip("D", 52, this.m - 6, colour, backColour);
/* 689 */     drawTooltip("G", this.l - 72, this.m - 6, colour, backColour);
/* 690 */     drawTooltip("I", this.l - 52, this.m - 6, colour, backColour);
/* 691 */     drawTooltip("T", this.l - 32, this.m - 6, colour, backColour);
/* 692 */     drawTooltip("S", this.l - 12, this.m - 6, colour, backColour);
/*     */     
/* 694 */     if (this.configs == null || !this.configs.m)
/*     */     {
/* 696 */       drawTooltip("DOWN", this.l - 60, 30, colour, backColour);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean drawComplexGuiElements() {
/* 706 */     return !this.settings.simpleGui;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawLayouts() {
/* 715 */     this.keyboardLayout.setSizeAndPosition(0, 22, this.l, this.m - 38);
/* 716 */     this.eventLayout.setSizeAndPosition(this.l, 22, this.l, this.m - 38);
/* 717 */     this.buttonsLayout.setSizeAndPosition(this.l * 2 + 2, 22, this.l - 4, this.m - 38);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setTitles(int currentPage) {
/* 723 */     if (currentPage == 0) {
/*     */       
/* 725 */       this.title = I18n.get("macro.bind.title") + ": " + I18n.get("macro.trigger.type.keys");
/* 726 */       this.prompt = I18n.get("macro.prompt." + (this.settings.simpleGui ? "edit.simple" : "edit"));
/*     */     }
/* 728 */     else if (currentPage == 1) {
/*     */       
/* 730 */       this.title = I18n.get("macro.bind.title") + ": " + I18n.get("macro.trigger.type.events");
/* 731 */       this.prompt = I18n.get("macro.prompt.edit.event");
/*     */     }
/* 733 */     else if (currentPage == 2) {
/*     */       
/* 735 */       this.title = I18n.get("macro.bind.title") + ": " + I18n.get("macro.trigger.type.control");
/* 736 */       this.prompt = I18n.get("macro.prompt.edit.control");
/*     */     }
/*     */     else {
/*     */       
/* 740 */       this.title = I18n.get("macro.bind.title");
/* 741 */       this.prompt = I18n.get("macro.prompt.edit.unknown");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postRender(int currentPage, int mouseX, int mouseY, float partialTick) {
/* 752 */     super.postRender(currentPage, mouseX, mouseY, partialTick);
/*     */     
/* 754 */     this.keyboardLayout.postRender(mouseX, mouseY);
/* 755 */     this.eventLayout.postRender(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMenuItemClicked(String menuItem) {
/* 765 */     if ("keys".equals(menuItem)) {
/*     */       
/* 767 */       beginTweening(0);
/*     */     }
/* 769 */     else if ("events".equals(menuItem)) {
/*     */       
/* 771 */       beginTweening(1);
/*     */     }
/* 773 */     else if ("buttons".equals(menuItem)) {
/*     */       
/* 775 */       beginTweening(2);
/*     */     }
/* 777 */     else if ("options".equals(menuItem)) {
/*     */       
/* 779 */       this.j.a((blk)new GuiMacroConfig(this.macros, this.j, this, false));
/*     */     }
/* 781 */     else if ("perms".equals(menuItem)) {
/*     */       
/* 783 */       this.j.a((blk)new GuiPermissions(this.macros, this.j, (blk)this));
/*     */     }
/* 785 */     else if ("editgui".equals(menuItem)) {
/*     */       
/* 787 */       this.j.a((blk)new GuiLayoutPatch(this.macros, this.j, (blk)this));
/*     */     }
/* 789 */     else if (menuItem != null && menuItem.startsWith("custom")) {
/*     */       
/* 791 */       int index = Integer.parseInt(menuItem.substring(6));
/* 792 */       this.macros.getCustomScreenManager().displayCustomScreen(this, index);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiMacroBind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */