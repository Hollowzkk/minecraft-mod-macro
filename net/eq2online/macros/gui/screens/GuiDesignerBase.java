/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bib;
/*     */ import blk;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.settings.Settings;
/*     */ import net.eq2online.macros.gui.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.controls.GuiDropDownMenu;
/*     */ import net.eq2online.macros.gui.controls.GuiMiniToolbarButton;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControls;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxSetGridSize;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiLayoutPatch;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxConfirm;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanelButtons;
/*     */ import net.eq2online.macros.gui.layout.PanelManager;
/*     */ import net.eq2online.macros.input.IProhibitOverride;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.interfaces.IEditablePanel;
/*     */ import net.eq2online.macros.interfaces.ILayoutPanel;
/*     */ import net.eq2online.macros.interfaces.ILayoutPanelContainer;
/*     */ import net.eq2online.macros.interfaces.ILayoutWidget;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import org.lwjgl.input.Keyboard;
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
/*     */ public abstract class GuiDesignerBase
/*     */   extends GuiScreenWithHeader
/*     */   implements ILayoutPanelContainer, IProhibitOverride
/*     */ {
/*     */   protected static final int DIALOGID_OVERWRITE = 1;
/*     */   public static final int DIALOGID_DELETE = 2;
/*     */   private static final int GREEN = -16716288;
/*     */   private static final int CYAN = -16716050;
/*     */   private static final int RED = -1179648;
/*     */   private static final int YELLOW = -1118720;
/*     */   private static final int GREY = -1118482;
/*     */   private static final int BG = -1342177280;
/*     */   private static final int ID_ICONS = 0;
/*     */   private static final int ID_EDITFILE = 1;
/*     */   private static final int ID_OPTIONS = 2;
/*     */   private static final int ID_COPY = 3;
/*     */   private static final int ID_MOVE = 4;
/*     */   private static final int ID_DELETE = 5;
/*     */   private static final int ID_EDIT = 6;
/*     */   private static final int ID_BUTTONS = 7;
/*     */   private static final int ID_GUI = 8;
/*     */   private static final int ID_BUTTONSLAYOUT = 11;
/*     */   protected static int page;
/*     */   protected final Macros macros;
/*     */   protected final Settings settings;
/*     */   protected final PanelManager panelManager;
/*  73 */   protected int buttonPanelPage = 0;
/*     */   
/*     */   protected GuiMiniToolbarButton btnGui;
/*     */   
/*     */   protected GuiMiniToolbarButton btnIcons;
/*     */   
/*     */   protected GuiMiniToolbarButton btnEditFile;
/*     */   
/*     */   protected GuiMiniToolbarButton btnOptions;
/*     */   
/*     */   protected GuiMiniToolbarButton btnCopy;
/*     */   
/*     */   protected GuiMiniToolbarButton btnMove;
/*     */   
/*     */   protected GuiMiniToolbarButton btnDelete;
/*     */   protected GuiMiniToolbarButton btnEdit;
/*     */   protected GuiMiniToolbarButton btnButtons;
/*     */   protected final GuiDropDownMenu buttonPanelMenu;
/*     */   protected blk lastScreen;
/*     */   protected LayoutPanelButtons buttonsLayout;
/*     */   protected DesignableGuiLayout layout;
/*  94 */   protected ILayoutWidget capturedWidget = null;
/*     */   
/*     */   protected boolean drawToolButtons;
/*     */   
/*     */   protected boolean drawActionButtons;
/*     */   
/*     */   public GuiDesignerBase(Macros macros, bib minecraft, int pages, int initialPage) {
/* 101 */     super(minecraft, pages, initialPage);
/*     */     
/* 103 */     this.macros = macros;
/* 104 */     this.settings = macros.getSettings();
/* 105 */     this.panelManager = macros.getLayoutPanels();
/*     */     
/* 107 */     this.drawMenuButton = true;
/* 108 */     this.bannerCentred = false;
/*     */     
/* 110 */     this.buttonPanelMenu = new GuiDropDownMenu(minecraft, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/* 115 */     setTitles(page);
/*     */     
/* 117 */     this.updateCounter = 0;
/*     */     
/* 119 */     this.buttonsLayout = new LayoutPanelButtons(this.macros, this.j, 11, this, this.layout);
/* 120 */     this.buttonsLayout.dialogClosed();
/*     */ 
/*     */ 
/*     */     
/* 124 */     for (DesignableGuiControls.ControlType controlType : this.layout.getManager().getControls().getAvailableControlTypes()) {
/*     */       
/* 126 */       String key = "new_" + controlType;
/* 127 */       String text = I18n.get("layout.editor.new." + controlType);
/*     */       
/* 129 */       if (controlType.hasIcon) {
/*     */         
/* 131 */         this.buttonPanelMenu.addItem(key, text, controlType.iconU / 2, controlType.iconV / 2, ResourceLocations.EXT);
/*     */         
/*     */         continue;
/*     */       } 
/* 135 */       this.buttonPanelMenu.addItem(key, text);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     this.buttonPanelMenu.addSeparator();
/* 142 */     this.buttonPanelMenu.addItem("grid", I18n.get("layout.editor.grid"), "Ctrl+.");
/*     */   }
/*     */ 
/*     */   
/*     */   public Macros getMacros() {
/* 147 */     return this.macros;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 156 */     Keyboard.enableRepeatEvents(true);
/*     */     
/* 158 */     this.n.clear();
/* 159 */     this.miniButtons.clear();
/*     */     
/* 161 */     if (this.drawToolButtons) {
/*     */       
/* 163 */       this.miniButtons.add(this.btnGui = new GuiMiniToolbarButton(this.j, 8, 104, 64));
/* 164 */       this.miniButtons.add(this.btnIcons = new GuiMiniToolbarButton(this.j, 0, 104, 32));
/* 165 */       this.miniButtons.add(this.btnEditFile = new GuiMiniToolbarButton(this.j, 1, 104, 16));
/* 166 */       this.miniButtons.add(this.btnOptions = new GuiMiniToolbarButton(this.j, 2, 104, 0));
/*     */       
/* 168 */       this.btnGui.setColours(-1118720, -1342177280).setTooltip("tooltip.guiedit");
/* 169 */       this.btnIcons.setColours(-1118720, -1342177280).setTooltip("tooltip.icons");
/* 170 */       this.btnEditFile.setColours(-1118720, -1342177280).setTooltip("tooltip.editfile");
/* 171 */       this.btnOptions.setColours(-1118720, -1342177280).setTooltip("tooltip.options");
/*     */     } 
/*     */     
/* 174 */     if (this.drawActionButtons) {
/*     */       
/* 176 */       this.miniButtons.add(this.btnCopy = new GuiMiniToolbarButton(this.j, 3, 128, 0));
/* 177 */       this.miniButtons.add(this.btnMove = new GuiMiniToolbarButton(this.j, 4, 128, 16));
/*     */       
/* 179 */       this.btnCopy.setColours(-16716288, -1342177280).setTooltip("gui.copy");
/* 180 */       this.btnMove.setColours(-16716050, -1342177280).setTooltip("gui.move");
/*     */     } 
/*     */     
/* 183 */     this.miniButtons.add(this.btnDelete = new GuiMiniToolbarButton(this.j, 5, 128, 32));
/* 184 */     this.miniButtons.add(this.btnEdit = new GuiMiniToolbarButton(this.j, 6, 128, 48));
/* 185 */     this.miniButtons.add(this.btnButtons = new GuiMiniToolbarButton(this.j, 7, 128, 64));
/*     */     
/* 187 */     this.btnDelete.setColours(-1179648, -1342177280).setTooltip("gui.delete");
/* 188 */     this.btnEdit.setColours(-1118720, -1342177280).setTooltip("gui.edit");
/* 189 */     this.btnButtons.setColours(-1118482, -1342177280).setTooltip("gui.buttonopts");
/*     */     
/* 191 */     if (drawComplexGuiElements()) {
/*     */       
/* 193 */       this.buttonsLayout.connect(this);
/* 194 */       this.n.add(this.buttonsLayout);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void m() {
/* 204 */     this.macros.save();
/* 205 */     Keyboard.enableRepeatEvents(false);
/* 206 */     this.buttonsLayout.release();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 215 */     this.updateCounter++;
/* 216 */     this.panelManager.tickInGui();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleWidgetClick(ILayoutPanel<? extends ILayoutWidget> source, int keyCode, boolean bindable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ILayoutWidget getCapturedWidget() {
/* 229 */     return this.capturedWidget;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void captureWidgetAt(int mouseX, int mouseY) {
/* 235 */     this.capturedWidget = null;
/* 236 */     this.capturedWidget = this.buttonsLayout.getWidgetAt(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogClosed(GuiDialogBox dialog) {
/* 247 */     super.onDialogClosed(dialog);
/*     */     
/* 249 */     if (dialog instanceof GuiDialogBoxConfirm && dialog.getId() == 2) {
/*     */       
/* 251 */       GuiDialogBoxConfirm<Integer> confirmDelete = (GuiDialogBoxConfirm<Integer>)dialog;
/* 252 */       if (dialog.getResult() == GuiDialogBox.DialogResult.OK)
/*     */       {
/* 254 */         this.buttonsLayout.deleteWidget(((Integer)confirmDelete.getMetaData()).intValue());
/*     */       }
/*     */       
/* 257 */       this.buttonsLayout.dialogClosed();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTicks) {
/* 268 */     if (this.j.f == null)
/*     */     {
/* 270 */       c();
/*     */     }
/*     */     
/* 273 */     this.bannerColour = 4259648;
/* 274 */     this.bgBottomMargin = 16;
/*     */     
/* 276 */     GL.glDisableLighting();
/* 277 */     GL.glDisableDepthTest();
/*     */ 
/*     */     
/* 280 */     if (drawComplexGuiElements())
/*     */     {
/* 282 */       drawLayouts();
/*     */     }
/*     */     
/* 285 */     this.animate = this.settings.enableGuiAnimation;
/* 286 */     this.drawBackground = this.drawHeader = drawComplexGuiElements();
/* 287 */     this.banner = I18n.get("macro.currentconfig", new Object[] { this.macros.getActiveConfigName() });
/*     */     
/* 289 */     drawMiniButtons(mouseX, mouseY, partialTicks);
/* 290 */     drawPromptBar(mouseX, mouseY, partialTicks, -1118720, -1342177280);
/* 291 */     super.a(mouseX, mouseY, partialTicks);
/*     */     
/* 293 */     if (drawComplexGuiElements()) {
/*     */       
/* 295 */       this.buttonPanelMenu.drawControlAt(this.promptBarStart - 20, this.m - 14, mouseX, mouseY);
/*     */       
/* 297 */       if (this.buttonPanelMenu.isDropDownVisible())
/*     */       {
/* 299 */         a(this.promptBarStart - 20, this.m - 15, this.promptBarStart - 2, this.m - 13, -16777216);
/*     */       }
/*     */     } 
/*     */     
/* 303 */     if (this.hoverButton != null)
/*     */     {
/* 305 */       drawTooltip(this.hoverButton.getTooltip(), mouseX, mouseY, -1118482, -1342177280);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean drawComplexGuiElements() {
/* 314 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postRender(int currentPage, int mouseX, int mouseY, float partialTicks) {
/* 324 */     super.postRender(currentPage, mouseX, mouseY, partialTicks);
/*     */     
/* 326 */     this.buttonsLayout.postRender(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawLayouts() {
/* 331 */     this.buttonsLayout.setSizeAndPosition(2, 22, this.l - 4, this.m - 38);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawMiniButtons(int mouseX, int mouseY, float partialTicks) {
/* 337 */     super.drawMiniButtons(mouseX, mouseY, partialTicks);
/*     */     
/* 339 */     drawMiniButton(this.btnOptions, mouseX, mouseY, partialTicks);
/*     */     
/* 341 */     if (drawComplexGuiElements()) {
/*     */       
/* 343 */       PanelManager layoutPanels = this.macros.getLayoutPanels();
/* 344 */       IEditablePanel.EditMode mode = layoutPanels.getMode();
/* 345 */       drawMiniButton(this.btnCopy, mouseX, mouseY, partialTicks, (mode == IEditablePanel.EditMode.COPY));
/* 346 */       drawMiniButton(this.btnMove, mouseX, mouseY, partialTicks, (mode == IEditablePanel.EditMode.MOVE));
/* 347 */       drawMiniButton(this.btnDelete, mouseX, mouseY, partialTicks, (mode == IEditablePanel.EditMode.DELETE));
/*     */       
/* 349 */       boolean selected = (mode == IEditablePanel.EditMode.EDIT_ALL || mode == IEditablePanel.EditMode.EDIT_BUTTONS);
/* 350 */       if (layoutPanels.isKeyboardEditable() || page == this.buttonPanelPage)
/*     */       {
/* 352 */         drawMiniButton(this.btnEdit, mouseX, mouseY, partialTicks, selected);
/*     */       }
/*     */       
/* 355 */       if (page == this.buttonPanelPage)
/*     */       {
/* 357 */         drawMiniButton(this.btnButtons, mouseX, mouseY, partialTicks, this.buttonPanelMenu.isDropDownVisible());
/*     */       }
/*     */       
/* 360 */       drawMiniButton(this.btnEditFile, mouseX, mouseY, partialTicks);
/* 361 */       drawMiniButton(this.btnIcons, mouseX, mouseY, partialTicks);
/* 362 */       drawMiniButton(this.btnGui, mouseX, mouseY, partialTicks);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawPages(int currentPage, int mouseX, int mouseY, float partialTick) {
/* 373 */     page = currentPage;
/* 374 */     setTitles(currentPage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setTitles(int currentPage) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/* 392 */     if (getPage() == this.buttonPanelPage && this.buttonsLayout.keyPressed(keyChar, keyCode)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 397 */     if (InputHandler.isControlDown())
/*     */     {
/* 399 */       if (keyCode == 52) {
/*     */         
/* 401 */         onItemClicked("grid");
/* 402 */         this.buttonPanelMenu.mousePressed(0, 0);
/*     */       } 
/*     */     }
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
/* 415 */     if (keyCode == 1)
/*     */     {
/* 417 */       onCloseClick();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 427 */     boolean wasVisible = this.buttonPanelMenu.isDropDownVisible();
/*     */     
/* 429 */     GuiDropDownMenu.Item item = this.buttonPanelMenu.mousePressed(mouseX, mouseY);
/* 430 */     if (onItemClicked(item)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 435 */     if (this.hoverButton != null) {
/*     */       
/* 437 */       onMiniButtonClicked(this.hoverButton);
/*     */       
/* 439 */       if (this.hoverButton.k == 7 && !wasVisible)
/*     */       {
/* 441 */         this.buttonPanelMenu.showDropDown();
/*     */       }
/*     */     } 
/*     */     
/* 445 */     super.a(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onItemClicked(GuiDropDownMenu.Item item) {
/* 450 */     if (item == null)
/*     */     {
/* 452 */       return false;
/*     */     }
/*     */     
/* 455 */     onItemClicked(item.getTag());
/* 456 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onItemClicked(String tag) {
/* 461 */     if (tag != null && tag.startsWith("new_")) {
/*     */       
/* 463 */       this.buttonsLayout.addControl(tag.substring(4));
/*     */     }
/* 465 */     else if ("grid".equals(tag) && this.layout != null) {
/*     */       
/* 467 */       this.j.a((blk)new GuiDialogBoxSetGridSize(this.j, this, this.layout));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMiniButtonClicked(GuiMiniToolbarButton button) {
/* 478 */     if (button.k == 8) {
/*     */       
/* 480 */       this.j.a((blk)new GuiLayoutPatch(this.macros, this.j, (blk)this));
/*     */     }
/* 482 */     else if (button.k == 0 && this.j.f != null) {
/*     */       
/* 484 */       this.j.a((blk)new GuiEditThumbnails(this.macros, this.j, (blk)this, this.macros.getThumbnailHandler()));
/*     */     }
/* 486 */     else if (button.k == 1) {
/*     */       
/* 488 */       onEditFile();
/*     */     }
/* 490 */     else if (button.k == 2) {
/*     */       
/* 492 */       this.j.a((blk)new GuiMacroConfig(this.macros, this.j, this, false));
/*     */     }
/* 494 */     else if (button.k == 3) {
/*     */       
/* 496 */       this.panelManager.setMode(IEditablePanel.EditMode.COPY);
/*     */     }
/* 498 */     else if (button.k == 4) {
/*     */       
/* 500 */       this.panelManager.setMode(IEditablePanel.EditMode.MOVE);
/*     */     }
/* 502 */     else if (button.k == 5) {
/*     */       
/* 504 */       this.panelManager.setMode(IEditablePanel.EditMode.DELETE);
/*     */     }
/* 506 */     else if (button.k == 6) {
/*     */       
/* 508 */       this.panelManager.setMode(this.panelManager.isKeyboardEditable() ? IEditablePanel.EditMode.EDIT_ALL : IEditablePanel.EditMode.EDIT_BUTTONS);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onEditFile() {
/* 514 */     this.j.a((blk)new GuiEditTextFile(this.macros, this.j, this, ScriptContext.MAIN));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCloseClick() {
/* 523 */     this.macros.save();
/* 524 */     this.j.a(this.lastScreen);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiDesignerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */