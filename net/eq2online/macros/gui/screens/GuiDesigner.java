/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bib;
/*     */ import blk;
/*     */ import java.io.File;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxConfirm;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxRenameItem;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxSaveItem;
/*     */ import net.eq2online.macros.interfaces.IEditablePanel;
/*     */ import net.eq2online.macros.interfaces.ILayoutPanel;
/*     */ import net.eq2online.macros.interfaces.ILayoutWidget;
/*     */ import net.eq2online.util.Util;
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
/*     */ public class GuiDesigner
/*     */   extends GuiDesignerBase
/*     */ {
/*     */   private static final String MENU_BACK = "back";
/*     */   private static final String MENU_RENAME = "rename";
/*     */   private static final String MENU_EXPORT = "export";
/*     */   protected boolean allowBinding = false;
/*     */   
/*     */   public GuiDesigner(Macros macros, bib minecraft, String guiSlotName, blk parentScreen, boolean allowBinding) {
/*  39 */     super(macros, minecraft, 0, 0);
/*     */     
/*  41 */     this.lastScreen = parentScreen;
/*  42 */     this.drawMenuButton = true;
/*  43 */     this.bannerCentred = false;
/*  44 */     this.allowBinding = allowBinding;
/*     */     
/*  46 */     this.layout = macros.getLayoutManager().getBoundLayout(guiSlotName, false);
/*     */     
/*  48 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDesigner(Macros macros, bib minecraft, DesignableGuiLayout layout, blk parentScreen, boolean allowBinding) {
/*  57 */     super(macros, minecraft, 0, 0);
/*     */     
/*  59 */     this.lastScreen = parentScreen;
/*  60 */     this.drawMenuButton = true;
/*  61 */     this.bannerCentred = false;
/*  62 */     this.allowBinding = allowBinding;
/*     */     
/*  64 */     this.layout = layout;
/*     */     
/*  66 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() {
/*  72 */     super.init();
/*     */     
/*  74 */     this.dropdown.addItem("rename", I18n.get("layout.editor.menu.rename"))
/*  75 */       .addItem("export", I18n.get("layout.editor.menu.export"))
/*  76 */       .addSeparator()
/*  77 */       .addItem("back", I18n.get("gui.exit"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  83 */     super.e();
/*     */     
/*  85 */     if (this.layout != null)
/*     */     {
/*  87 */       this.layout.onTick(this.updateCounter);
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
/*     */   public void handleWidgetClick(ILayoutPanel<? extends ILayoutWidget> source, int keyCode, boolean bindable) {
/*  99 */     if (bindable && this.allowBinding) {
/*     */       
/* 101 */       this.macros.save();
/* 102 */       this.j.a((blk)GuiMacroEdit.create(this.macros, this.j, (blk)this, keyCode));
/*     */     }
/*     */     else {
/*     */       
/* 106 */       this.panelManager.setMode(IEditablePanel.EditMode.EDIT_BUTTONS);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setTitles(int currentPage) {
/* 116 */     this.title = I18n.get("editor.editing", new Object[] { '"' + this.layout.getDisplayName() + '"' });
/* 117 */     this.prompt = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMenuItemClicked(String menuItem) {
/* 127 */     if ("back".equals(menuItem)) {
/*     */       
/* 129 */       onCloseClick();
/*     */     }
/* 131 */     else if ("rename".equals(menuItem)) {
/*     */       
/* 133 */       onTitleClick();
/*     */     }
/* 135 */     else if ("export".equals(menuItem)) {
/*     */       
/* 137 */       displayDialog((GuiDialogBox)new GuiDialogBoxSaveItem(this.j, this, 
/* 138 */             I18n.get("layout.editor.export.title"), 
/* 139 */             I18n.get("layout.editor.export.prompt")));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTitleClick() {
/* 146 */     displayDialog((GuiDialogBox)new GuiDialogBoxRenameItem(this.j, this, this.layout));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogClosed(GuiDialogBox dialog) {
/* 153 */     super.onDialogClosed(dialog);
/*     */     
/* 155 */     if (dialog.getResult() == GuiDialogBox.DialogResult.OK)
/*     */     {
/* 157 */       if (dialog instanceof GuiDialogBoxRenameItem) {
/*     */         
/* 159 */         GuiDialogBoxRenameItem renameDialog = (GuiDialogBoxRenameItem)dialog;
/* 160 */         renameDialog.layout.setDisplayName(renameDialog.getNewItemName());
/*     */       }
/* 162 */       else if (dialog instanceof GuiDialogBoxSaveItem) {
/*     */         
/* 164 */         export(((GuiDialogBoxSaveItem)dialog).getValue().toString());
/*     */       }
/* 166 */       else if (dialog instanceof GuiDialogBoxConfirm && dialog.getId() == 1) {
/*     */         
/* 168 */         this.layout.export((File)((GuiDialogBoxConfirm)dialog).getMetaData());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void export(String filename) {
/* 175 */     String fileName = Util.sanitiseFileName(filename, ".xml");
/* 176 */     if (fileName != null) {
/*     */       
/* 178 */       File file = new File(this.macros.getMacrosDirectory(), fileName);
/*     */       
/* 180 */       if (file.exists()) {
/*     */         
/* 182 */         displayDialog((GuiDialogBox)new GuiDialogBoxConfirm(this.j, this, 
/* 183 */               I18n.get("layout.editor.export.overwrite.title"), 
/* 184 */               I18n.get("layout.editor.export.overwrite.prompt"), file
/* 185 */               .getName(), 1, file));
/*     */         
/*     */         return;
/*     */       } 
/* 189 */       System.err.printf(">>> %s\n", new Object[] { file.getAbsolutePath() });
/* 190 */       this.layout.export(file);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiDesigner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */