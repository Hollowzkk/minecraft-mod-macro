/*     */ package net.eq2online.macros.gui.designable.editor;
/*     */ 
/*     */ import bib;
/*     */ import bja;
/*     */ import blk;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.controls.GuiListItemSocket;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.designable.LayoutManager;
/*     */ import net.eq2online.macros.gui.designable.editor.browse.GuiDialogBoxBrowseImportable;
/*     */ import net.eq2online.macros.gui.designable.editor.browse.GuiDialogBoxImportLayout;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxConfirm;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxCreateItem;
/*     */ import net.eq2online.macros.gui.list.GuiLayoutListEntry;
/*     */ import net.eq2online.macros.gui.screens.GuiDesigner;
/*     */ import net.eq2online.macros.gui.screens.GuiScreenWithHeader;
/*     */ import net.eq2online.macros.input.IProhibitOverride;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.interfaces.IDragDrop;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.ISocketListener;
/*     */ import net.eq2online.xml.Xml;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class GuiLayoutPatch
/*     */   extends GuiScreenWithHeader
/*     */   implements ISocketListener<DesignableGuiLayout>, IProhibitOverride
/*     */ {
/*     */   private static final String MENU_BACK = "back";
/*     */   private static final String MENU_RESET = "reset";
/*     */   private static final String MENU_CREATE = "create";
/*     */   private static final String MENU_IMPORT = "import";
/*     */   private static final int SOCKET_SPACING = 24;
/*     */   private static final int SOCKET_TOP = 92;
/*     */   private final Macros macros;
/*     */   private final LayoutManager layoutManager;
/*     */   private blk parentScreen;
/*     */   private GuiListBox<DesignableGuiLayout> availableGuiList;
/*  62 */   private Map<GuiListItemSocket<DesignableGuiLayout>, String> sockets = new HashMap<>();
/*     */   
/*  64 */   private List<GuiListItemSocket<DesignableGuiLayout>> socketList = new ArrayList<>();
/*     */   
/*  66 */   private List<String> socketNames = new ArrayList<>();
/*     */   
/*     */   private GuiControl btnOk;
/*     */   
/*     */   private GuiCheckBox chkReset;
/*     */   
/*     */   private GuiLayoutListEntry mouseOverObject;
/*     */   
/*     */   private long mouseOverObjectTime;
/*     */   
/*  76 */   private int assignId = -1;
/*     */ 
/*     */   
/*     */   public GuiLayoutPatch(Macros macros, bib minecraft, blk parentScreen) {
/*  80 */     super(minecraft, 0, 0);
/*     */     
/*  82 */     this.parentScreen = parentScreen;
/*  83 */     this.drawMenuButton = true;
/*  84 */     this.drawBackground = false;
/*  85 */     this.bannerCentred = false;
/*  86 */     this.bannerColour = 4259648;
/*  87 */     this.title = I18n.get("layout.editor.title");
/*  88 */     this.macros = macros;
/*  89 */     this.layoutManager = macros.getLayoutManager();
/*     */     
/*  91 */     this.dropdown.addItem("create", I18n.get("layout.editor.menu.create"), "Ctrl+N")
/*  92 */       .addItem("import", I18n.get("layout.editor.menu.import"))
/*  93 */       .addItem("reset", "§8" + I18n.get("layout.editor.menu.reset"))
/*  94 */       .addSeparator()
/*  95 */       .addItem("back", I18n.get("gui.exit"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 101 */     clearControlList();
/*     */     
/* 103 */     int selectedIndex = -1;
/* 104 */     if (this.availableGuiList != null)
/*     */     {
/* 106 */       selectedIndex = this.availableGuiList.getSelectedItemIndex();
/*     */     }
/*     */     
/* 109 */     addControl((GuiControl)(this.availableGuiList = new GuiListBox(this.j, 0, 4, 40, 194, this.m - 70, 20, true, true, false)));
/* 110 */     addControl(this.btnOk = new GuiControl(1, this.l - 64, this.m - 24, 60, 20, I18n.get("gui.ok")));
/* 111 */     addControl(
/* 112 */         (GuiControl)(this.chkReset = new GuiCheckBox(this.j, 2, 6, this.m - 24, I18n.get("layout.options.loadatstartup"), (this.macros.getSettings()).loadLayoutBindings)));
/*     */     
/* 114 */     int layoutId = 0;
/*     */     
/* 116 */     Map<String, GuiLayoutListEntry> layoutListObjects = new HashMap<>();
/*     */     
/* 118 */     for (String layoutName : this.layoutManager.getLayoutNames()) {
/*     */       
/* 120 */       GuiLayoutListEntry layoutListObject = getLayoutListObject(layoutId++, layoutName);
/* 121 */       layoutListObjects.put(layoutName, layoutListObject);
/* 122 */       this.availableGuiList.addItem((IListEntry)layoutListObject);
/*     */     } 
/*     */     
/* 125 */     this.sockets.clear();
/* 126 */     this.socketList.clear();
/* 127 */     this.socketNames.clear();
/*     */     
/* 129 */     int socketId = 3;
/* 130 */     int socketPosition = 68;
/*     */     
/* 132 */     for (String socketName : this.layoutManager.getSlotNames()) {
/*     */       
/* 134 */       socketPosition += 24;
/* 135 */       GuiListItemSocket<DesignableGuiLayout> newSocket = new GuiListItemSocket(this.j, socketId++, 275, socketPosition, Math.min(200, this.l - 281), 20, I18n.get("layout.editor.nobinding"), this);
/* 136 */       this.sockets.put(newSocket, socketName);
/* 137 */       this.socketList.add(newSocket);
/* 138 */       this.socketNames.add(socketName);
/* 139 */       addControl((GuiControl)newSocket);
/* 140 */       this.availableGuiList.addDragTarget((IDragDrop)newSocket, true);
/*     */       
/* 142 */       newSocket.setItem((IListEntry)layoutListObjects.get(this.layoutManager.getBoundLayoutName(socketName)));
/*     */     } 
/*     */     
/* 145 */     if (selectedIndex > -1)
/*     */     {
/* 147 */       this.availableGuiList.setSelectedItemIndex(selectedIndex);
/*     */     }
/*     */     
/* 150 */     this.banner = I18n.get("macro.currentconfig", new Object[] { this.macros.getActiveConfigName() });
/* 151 */     this.assignId = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiLayoutListEntry getLayoutListObject(int id, String layoutName) {
/* 161 */     return new GuiLayoutListEntry(id, this.layoutManager.getLayout(layoutName));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTick) {
/* 167 */     if (this.j.f == null)
/*     */     {
/* 169 */       c();
/*     */     }
/*     */     
/* 172 */     a(2, 22, 200, 38, -1607257293);
/* 173 */     a(2, 38, 200, this.m - 28, this.backColour);
/* 174 */     a(202, 22, this.l - 2, 38, -1607257293);
/* 175 */     a(202, 38, this.l - 2, this.m - 28, this.backColour);
/* 176 */     a(2, this.m - 26, this.l - 2, this.m - 2, this.backColour);
/*     */     
/* 178 */     c(this.q, I18n.get("layout.editor.available"), 8, 26, -256);
/* 179 */     c(this.q, I18n.get("layout.editor.assignments"), 210, 26, -256);
/*     */     
/* 181 */     c(this.q, I18n.get("layout.editor.help.line1"), 210, 44, 16755200);
/* 182 */     c(this.q, I18n.get("layout.editor.help.line2"), 210, 54, 16755200);
/* 183 */     c(this.q, I18n.get("layout.editor.help.line3"), 210, 64, 16755200);
/*     */     
/* 185 */     c(this.q, I18n.get("layout.editor.slot"), 210, 79, -256);
/* 186 */     c(this.q, I18n.get("layout.editor.screen"), 279, 79, -256);
/*     */     
/* 188 */     int yPos = 74;
/* 189 */     for (String socketName : this.socketNames) {
/*     */       
/* 191 */       yPos += 24; c(this.q, socketName, 210, yPos, -171);
/*     */     } 
/*     */     
/* 194 */     drawSocketSelections(mouseX, mouseY, partialTick);
/*     */     
/* 196 */     super.a(mouseX, mouseY, partialTick);
/*     */     
/* 198 */     drawSocketDecorations(mouseX, mouseY, partialTick);
/* 199 */     drawTooltips(mouseX, mouseY, partialTick);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawSocketSelections(int mouseX, int mouseY, float partialTick) {
/* 204 */     if (this.assignId == -1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 209 */     int index = this.availableGuiList.getSelectedItemIndex();
/* 210 */     Rectangle rect = this.availableGuiList.getItemBoundingBox(index);
/*     */     
/* 212 */     int x = rect.x;
/* 213 */     int y = rect.y;
/* 214 */     int w = rect.width;
/* 215 */     int h = rect.height;
/*     */     
/* 217 */     a(x - 1, y - 1, x + w + 1, y + h + 1, -1);
/*     */     
/* 219 */     GuiListItemSocket<DesignableGuiLayout> socket = this.socketList.get(this.assignId);
/*     */     
/* 221 */     int xPos = socket.getXPosition();
/* 222 */     int yPos = socket.getYPosition();
/* 223 */     int width = socket.getWidth();
/* 224 */     int height = socket.getHeight();
/*     */     
/* 226 */     a(xPos - 2, yPos - 2, xPos + width + 2, yPos + height + 2, -1);
/*     */     
/* 228 */     int colour = -1325400065;
/* 229 */     this.renderer.drawArrow(x + w + 1, y + 9, x + w + 1 + 10, y + 9, 100, 2.0F, colour, false, 8);
/* 230 */     this.renderer.drawArrow(x + w + 1 + 10, y + 9, xPos - 2 - 15, yPos + height / 2, 100, 2.0F, colour, false, 8);
/* 231 */     this.renderer.drawArrow(xPos - 2 - 16, yPos + height / 2, xPos - 2, yPos + height / 2, 100, 2.0F, 8, colour);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawSocketDecorations(int mouseX, int mouseY, float partialTick) {}
/*     */ 
/*     */   
/*     */   private void drawTooltips(int mouseX, int mouseY, float partialTick) {
/* 240 */     if (!this.availableGuiList.isMouseOver(this.j, mouseX, mouseY)) {
/*     */       
/* 242 */       this.mouseOverObject = null;
/*     */       
/*     */       return;
/*     */     } 
/* 246 */     IListEntry<DesignableGuiLayout> mouseOverObject = this.availableGuiList.getItemAtPosition(mouseX, mouseY);
/* 247 */     if (mouseOverObject == null) {
/*     */       
/* 249 */       this.mouseOverObject = null;
/*     */       
/*     */       return;
/*     */     } 
/* 253 */     GuiLayoutListEntry layoutEntry = (GuiLayoutListEntry)mouseOverObject;
/* 254 */     if (this.mouseOverObject != layoutEntry) {
/*     */       
/* 256 */       this.mouseOverObject = layoutEntry;
/* 257 */       this.mouseOverObjectTime = this.updateCounter;
/*     */     } 
/*     */     
/* 260 */     if (this.mouseOverObject != null && this.updateCounter - this.mouseOverObjectTime > 6L && 
/* 261 */       !this.mouseOverObject.getLayoutName().equals(this.mouseOverObject.getLayoutDisplayName()))
/*     */     {
/* 263 */       drawTooltip(this.mouseOverObject.getLayoutName(), mouseX, mouseY, -171, -1342177280);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bja button) {
/* 270 */     if (button.k == this.availableGuiList.k) {
/*     */       
/* 272 */       IListEntry<DesignableGuiLayout> selectedItem = this.availableGuiList.getSelectedItem();
/*     */       
/* 274 */       if (selectedItem != null) {
/*     */         
/* 276 */         IListEntry.CustomAction customAction = selectedItem.getCustomAction(true);
/*     */         
/* 278 */         GuiLayoutListEntry selectedLayout = (GuiLayoutListEntry)selectedItem;
/* 279 */         if (customAction == IListEntry.CustomAction.DELETE)
/*     */         {
/* 281 */           deleteLayout(selectedLayout);
/*     */         }
/* 283 */         else if (customAction == IListEntry.CustomAction.EDIT || this.availableGuiList.isDoubleClicked(true))
/*     */         {
/* 285 */           this.j.a((blk)new GuiDesigner(this.macros, this.j, selectedLayout.getLayout(), (blk)this, true));
/*     */         }
/*     */       
/*     */       } 
/* 289 */     } else if (button.k == this.btnOk.k) {
/*     */       
/* 291 */       onCloseClick();
/*     */     }
/* 293 */     else if (button.k == this.chkReset.k) {
/*     */       
/* 295 */       (this.macros.getSettings()).loadLayoutBindings = this.chkReset.checked;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 302 */     this.assignId = -1;
/* 303 */     super.a(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/* 309 */     if (keyCode == 1) {
/*     */       
/* 311 */       if (this.assignId > -1) {
/*     */         
/* 313 */         this.assignId = -1;
/*     */         
/*     */         return;
/*     */       } 
/* 317 */       onCloseClick();
/*     */     } 
/*     */     
/* 320 */     GuiLayoutListEntry selectedLayout = (GuiLayoutListEntry)this.availableGuiList.getSelectedItem();
/*     */     
/* 322 */     if (this.assignId > -1) {
/*     */       
/* 324 */       if (keyCode == 200)
/*     */       {
/* 326 */         this.assignId = Math.max(0, this.assignId - 1);
/*     */       }
/* 328 */       else if (keyCode == 208)
/*     */       {
/* 330 */         this.assignId = Math.min(this.socketList.size() - 1, this.assignId + 1);
/*     */       }
/* 332 */       else if (keyCode == 28)
/*     */       {
/* 334 */         ((GuiListItemSocket)this.socketList.get(this.assignId)).setItem((IListEntry)selectedLayout);
/* 335 */         this.assignId = -1;
/*     */       }
/*     */       else
/*     */       {
/* 339 */         this.assignId = -1;
/*     */       }
/*     */     
/* 342 */     } else if (keyCode == 200 || keyCode == 208 || keyCode == 209 || keyCode == 201) {
/*     */       
/* 344 */       this.availableGuiList.listBoxKeyTyped(keyChar, keyCode);
/*     */     }
/* 346 */     else if (keyCode == 205 || keyCode == 57) {
/*     */       
/* 348 */       this.assignId = 0;
/*     */       
/* 350 */       int index = this.availableGuiList.getSelectedItemIndex();
/* 351 */       Rectangle rect = this.availableGuiList.getItemBoundingBox(index);
/*     */       
/* 353 */       if (rect.y > this.availableGuiList.h + this.availableGuiList.getHeight() || rect.y + rect.height < this.availableGuiList.i)
/*     */       {
/* 355 */         this.availableGuiList.scrollToCentre();
/*     */       }
/*     */     }
/* 358 */     else if (keyCode == 211 || keyCode == 14) {
/*     */       
/* 360 */       deleteLayout(selectedLayout);
/*     */     }
/* 362 */     else if (keyCode == 28) {
/*     */       
/* 364 */       this.j.a((blk)new GuiDesigner(this.macros, this.j, selectedLayout.getLayout(), (blk)this, true));
/*     */     }
/* 366 */     else if (keyCode == 49 && InputHandler.isControlDown()) {
/*     */       
/* 368 */       onMenuItemClicked("create");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMenuItemClicked(String menuItem) {
/* 375 */     if ("back".equals(menuItem)) {
/*     */       
/* 377 */       onCloseClick();
/*     */     }
/* 379 */     else if ("create".equals(menuItem)) {
/*     */       
/* 381 */       displayDialog((GuiDialogBox)new GuiDialogBoxCreateItem(this.j, (GuiScreenEx)this, I18n.get("patch.title"), I18n.get("patch.prompt")));
/*     */     }
/* 383 */     else if ("import".equals(menuItem)) {
/*     */       
/* 385 */       displayDialog((GuiDialogBox)new GuiDialogBoxBrowseImportable(this.j, (blk)this, I18n.get("patch.import.title"), this.macros.getMacrosDirectory()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSocketChanged(GuiListItemSocket<DesignableGuiLayout> socket, IListEntry<DesignableGuiLayout> object) {
/* 392 */     String layoutName = (object != null) ? ((GuiLayoutListEntry)object).getLayoutName() : null;
/* 393 */     this.layoutManager.setBinding(this.sockets.get(socket), layoutName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSocketCleared(GuiListItemSocket<DesignableGuiLayout> socket) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSocketClicked(GuiListItemSocket<DesignableGuiLayout> socket, boolean doubleClicked) {
/* 404 */     if (socket.getItem() != null) {
/*     */       
/* 406 */       IListEntry.CustomAction customAction = socket.getItem().getCustomAction(true);
/* 407 */       GuiLayoutListEntry selectedLayout = (GuiLayoutListEntry)socket.getItem();
/* 408 */       if (customAction == IListEntry.CustomAction.DELETE) {
/*     */         
/* 410 */         deleteLayout(selectedLayout);
/*     */       }
/* 412 */       else if (customAction == IListEntry.CustomAction.EDIT || doubleClicked) {
/*     */         
/* 414 */         this.j.a((blk)new GuiDesigner(this.macros, this.j, selectedLayout.getLayout(), (blk)this, 
/* 415 */               !((String)this.sockets.get(socket)).equals("ingame")));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deleteLayout(GuiLayoutListEntry layout) {
/* 425 */     if (!this.layoutManager.isBuiltinLayout(layout.getLayoutName())) {
/*     */       
/* 427 */       String title = I18n.get("layout.delete.title");
/* 428 */       String prompt = I18n.get("layout.delete.line1");
/* 429 */       displayDialog((GuiDialogBox)new GuiDialogBoxConfirm(this.j, (GuiScreenEx)this, title, prompt, layout
/* 430 */             .getLayoutName() + " ?", 0, layout.getLayout()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCloseClick() {
/* 437 */     this.layoutManager.saveSettings();
/* 438 */     this.j.a(this.parentScreen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogClosed(GuiDialogBox dialog) {
/* 445 */     super.onDialogClosed(dialog);
/*     */     
/* 447 */     if (dialog.getResult() == GuiDialogBox.DialogResult.OK)
/*     */     {
/* 449 */       if (dialog instanceof GuiDialogBoxConfirm) {
/*     */         
/* 451 */         GuiDialogBoxConfirm<DesignableGuiLayout> confirmDeleteDialog = (GuiDialogBoxConfirm<DesignableGuiLayout>)dialog;
/* 452 */         this.layoutManager.deleteLayout(((DesignableGuiLayout)confirmDeleteDialog.getMetaData()).getName());
/* 453 */         b();
/*     */       }
/* 455 */       else if (dialog instanceof GuiDialogBoxCreateItem) {
/*     */         
/* 457 */         GuiDialogBoxCreateItem createDialog = (GuiDialogBoxCreateItem)dialog;
/* 458 */         if (this.layoutManager.layoutExists(createDialog.getNewItemName()))
/*     */         {
/* 460 */           displayDialog((GuiDialogBox)createDialog);
/* 461 */           createDialog.onDialogSubmissionFailed(I18n.get("patch.create.exists", new Object[] { createDialog.getNewItemName() }));
/*     */         }
/*     */         else
/*     */         {
/* 465 */           DesignableGuiLayout newLayout = this.layoutManager.getLayout(createDialog.getNewItemName());
/* 466 */           newLayout.setDisplayName(createDialog.getNewItemDisplayName());
/* 467 */           b();
/* 468 */           this.j.a((blk)new GuiDesigner(this.macros, this.j, newLayout, (blk)this, true));
/*     */         }
/*     */       
/* 471 */       } else if (dialog instanceof GuiDialogBoxBrowseImportable) {
/*     */         
/* 473 */         File file = (File)((GuiDialogBoxBrowseImportable)dialog).getValue();
/* 474 */         if (file != null && file.exists())
/*     */         {
/* 476 */           Xml.xmlClearNamespace();
/* 477 */           Xml.xmlAddNamespace("gc", "http://eq2online.net/macros/guiconfiguration");
/* 478 */           Xml.xmlAddNamespace("gb", "http://eq2online.net/macros/guibinding");
/*     */ 
/*     */           
/*     */           try {
/* 482 */             Document xml = Xml.xmlCreate(file);
/* 483 */             Node layoutNode = Xml.xmlGet(xml, "/gui/gc:guilayout");
/* 484 */             if (layoutNode != null)
/*     */             {
/* 486 */               displayDialog((GuiDialogBox)new GuiDialogBoxImportLayout(this.j, (GuiScreenEx)this, 
/* 487 */                     I18n.get("patch.import.name.title"), 
/* 488 */                     I18n.get("patch.import.name.prompt"), this.macros, this.layoutManager, layoutNode));
/*     */             
/*     */             }
/*     */           }
/* 492 */           catch (Exception ex) {
/*     */             
/* 494 */             ex.printStackTrace();
/*     */           } 
/*     */           
/* 497 */           Xml.xmlClearNamespace();
/*     */         }
/*     */       
/* 500 */       } else if (dialog instanceof GuiDialogBoxImportLayout) {
/*     */         
/* 502 */         b();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\editor\GuiLayoutPatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */