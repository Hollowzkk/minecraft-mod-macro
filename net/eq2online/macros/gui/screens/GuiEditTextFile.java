/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import ain;
/*     */ import aip;
/*     */ import air;
/*     */ import bib;
/*     */ import bja;
/*     */ import bje;
/*     */ import blk;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.settings.Settings;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.controls.GuiTextEditor;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxConfirm;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxConfirmWithCheckbox;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxRenameItem;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxYesNoCancel;
/*     */ import net.eq2online.macros.gui.interfaces.IMinimisable;
/*     */ import net.eq2online.macros.gui.interfaces.IMinimisableHost;
/*     */ import net.eq2online.macros.gui.list.EditableListEntry;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.util.Util;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiEditTextFile
/*     */   extends GuiEditTextBase
/*     */   implements FilenameFilter, IMinimisable
/*     */ {
/*     */   private static final String MENU_NOSAVE = "nosave";
/*     */   private static final String MENU_MINIMISE = "mini";
/*     */   private static final String MENU_SAVE = "save";
/*     */   private static final String MENU_EDIT = "edit";
/*     */   private static final String MENU_HELP = "help";
/*     */   private static final String MENU_CLOSE = "close";
/*     */   private static final String MENU_COLOURS = "colours";
/*  51 */   protected static String lastFile = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiListBox<File> fileListBox;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected bje newFileTextBox;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected File file;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   protected String fileSuggestion = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean closeMe = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiEditTextFile(Macros macros, bib minecraft, GuiScreenEx parent, File file, ScriptContext context) {
/*  86 */     super(macros, minecraft, parent, context);
/*     */     
/*  88 */     this.file = file;
/*  89 */     this.title = I18n.get("editor.title");
/*  90 */     this.bgBottomMargin = 28;
/*  91 */     this.banner = (this.file == null) ? "" : I18n.get("editor.editing", new Object[] { this.file.getName() });
/*     */     
/*  93 */     this.dropdown.addItem("mini", I18n.get("editor.menu.min"), "Ctrl+Tab")
/*  94 */       .addItem("save", I18n.get("gui.save"), "Ctrl+S")
/*  95 */       .addItem("edit", I18n.get("editor.menu.editother"), "Ctrl+O")
/*  96 */       .addItem("help", I18n.get("editor.menu.reference"), "F1")
/*  97 */       .addSeparator()
/*  98 */       .addItem("colours", I18n.get("editor.menu.colours"), "F3")
/*  99 */       .addSeparator()
/* 100 */       .addItem("close", I18n.get("gui.exit"), "ESC");
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiEditTextFile(Macros macros, bib minecraft, GuiScreenEx parent, ScriptContext context) {
/* 105 */     this(macros, minecraft, parent, (File)null, context);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiEditTextFile(Macros macros, bib minecraft, GuiScreenEx parent, String suggestion, ScriptContext context) {
/* 110 */     this(macros, minecraft, parent, context);
/*     */     
/* 112 */     this.fileSuggestion = suggestion;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void show(IMinimisableHost host) {
/* 118 */     this.parent = host.getDelegate();
/* 119 */     this.j.a((blk)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 128 */     Keyboard.enableRepeatEvents(true);
/*     */     
/* 130 */     clearControlList();
/*     */     
/* 132 */     if (this.file != null) {
/*     */       
/* 134 */       lastFile = this.file.getName();
/*     */       
/* 136 */       Settings settings = this.macros.getSettings();
/*     */ 
/*     */       
/* 139 */       if (this.textEditor == null) {
/*     */         
/* 141 */         this.textEditor = new GuiTextEditor(this.j, 0, 7, 27, this.l - 12, this.m - 60, this, settings.showTextEditorSyntax, this.context, settings.showTextEditorHelp);
/*     */         
/* 143 */         this.textEditor.load(this.file);
/*     */       }
/*     */       else {
/*     */         
/* 147 */         this.textEditor.setSizeAndPosition(7, 27, this.l - 12, this.m - 60);
/*     */       } 
/*     */       
/* 150 */       this.textEditor.setUseDarkColours(settings.useDarkEditorColours);
/*     */       
/* 152 */       addControl((GuiControl)this.textEditor);
/* 153 */       addControl(new GuiControl(1, this.l - 64, this.m - 24, 60, 20, I18n.get("gui.save")));
/* 154 */       addControl(new GuiControl(2, this.l - 128, this.m - 24, 60, 20, I18n.get("gui.cancel")));
/* 155 */       addControl(new GuiControl(10, 5, this.m - 24, 30, 20, "..."));
/*     */       
/* 157 */       addControl((GuiControl)(this.chkShowHelp = new GuiCheckBox(this.j, 55, 40, this.m - 24, I18n.get("editor.option.help"), settings.showTextEditorHelp)));
/*     */ 
/*     */       
/* 160 */       this.drawMinButton = (this.parent instanceof GuiMacroBind || this.parent instanceof GuiMacroPlayback);
/* 161 */       this.dropdown.getItem("mini").setDisabled(!this.drawMinButton);
/* 162 */       this.drawMenuButton = true;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 167 */       if (this.fileListBox == null) {
/*     */         
/* 169 */         this.fileListBox = new GuiListBox(this.j, 5, 4, 40, 174, this.m - 70, 20, true, false, false);
/* 170 */         this.newFileTextBox = new bje(0, this.q, 199, 40, this.l - 225, 16);
/* 171 */         this.newFileTextBox.f(255);
/* 172 */         this.newFileTextBox.b(true);
/*     */         
/* 174 */         populateFileList();
/*     */       }
/*     */       else {
/*     */         
/* 178 */         this.fileListBox.setSizeAndPosition(4, 40, 174, this.m - 70, 20, true);
/*     */       } 
/*     */       
/* 181 */       addControl((GuiControl)this.fileListBox);
/* 182 */       addControl(new GuiControl(3, this.l - 64, this.m - 24, 60, 20, I18n.get("gui.ok")));
/* 183 */       addControl(new GuiControl(4, this.l - 128, this.m - 24, 60, 20, I18n.get("gui.cancel")));
/* 184 */       addControl(new GuiControl(6, this.l - 106, 60, 80, 20, I18n.get("gui.create")));
/*     */       
/* 186 */       this.drawMinButton = false;
/* 187 */       this.drawMenuButton = false;
/*     */     } 
/*     */     
/* 190 */     super.b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMenuItemClicked(String menuItem) {
/* 196 */     if ("mini".equals(menuItem)) {
/*     */       
/* 198 */       onMinimiseClick();
/*     */     }
/* 200 */     else if ("save".equals(menuItem)) {
/*     */       
/* 202 */       if (this.textEditor.isChanged())
/*     */       {
/* 204 */         this.textEditor.save();
/*     */       }
/*     */     }
/* 207 */     else if ("edit".equals(menuItem)) {
/*     */       
/* 209 */       onBackToMenuClick();
/*     */     }
/* 211 */     else if ("help".equals(menuItem)) {
/*     */       
/* 213 */       this.j.a((blk)new GuiCommandReference(this.j, (blk)this));
/*     */     }
/* 215 */     else if ("close".equals(menuItem)) {
/*     */       
/* 217 */       onCloseClick();
/*     */     }
/* 219 */     else if ("colours".equals(menuItem)) {
/*     */       
/* 221 */       Settings settings = this.macros.getSettings();
/* 222 */       settings.useDarkEditorColours = !settings.useDarkEditorColours;
/* 223 */       this.textEditor.setUseDarkColours(settings.useDarkEditorColours);
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
/*     */   protected void a(bja guibutton) {
/* 235 */     if (guibutton != null) {
/*     */ 
/*     */       
/* 238 */       if (guibutton.k == 1) {
/*     */         
/* 240 */         this.textEditor.save();
/* 241 */         close();
/*     */       } 
/*     */ 
/*     */       
/* 245 */       if (guibutton.k == 1 || guibutton.k == 2 || guibutton.k == 4)
/*     */       {
/* 247 */         close();
/*     */       }
/*     */ 
/*     */       
/* 251 */       if (guibutton.k == 3 || (guibutton.k == 5 && this.fileListBox.isDoubleClicked(true))) {
/*     */         
/* 253 */         if (this.newFileTextBox.b().length() > 0 && guibutton.k == 3)
/*     */         {
/* 255 */           createFile();
/*     */         }
/*     */         else
/*     */         {
/* 259 */           if (this.fileListBox.getSelectedItem() != null) {
/*     */             
/* 261 */             this.file = (File)this.fileListBox.getSelectedItem().getData();
/*     */           }
/*     */           else {
/*     */             
/* 265 */             this.file = new File(this.macros.getMacrosDirectory(), "New Text File.txt");
/*     */           } 
/*     */           
/* 268 */           this.banner = I18n.get("editor.editing", new Object[] { this.file.getName() });
/* 269 */           b();
/*     */         }
/*     */       
/* 272 */       } else if (guibutton.k == 5) {
/*     */         
/* 274 */         if (this.fileListBox.getSelectedItem() != null) {
/*     */           
/* 276 */           IListEntry.CustomAction customAction = this.fileListBox.getSelectedItem().getCustomAction(true);
/*     */           
/* 278 */           if (customAction == IListEntry.CustomAction.EDIT) {
/*     */             
/* 280 */             displayDialog((GuiDialogBox)new GuiDialogBoxRenameItem(this.j, this, (File)this.fileListBox.getSelectedItem().getData()));
/*     */           }
/* 282 */           else if (customAction == IListEntry.CustomAction.DELETE) {
/*     */             
/* 284 */             String title = I18n.get("gui.delete");
/* 285 */             String line1 = I18n.get("param.action.confirmdeletefile");
/* 286 */             String line2 = this.fileListBox.getSelectedItem().getText();
/* 287 */             displayDialog((GuiDialogBox)new GuiDialogBoxConfirm(this.j, this, title, line1, line2, 0));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 292 */       if (guibutton.k == 6)
/*     */       {
/* 294 */         createFile();
/*     */       }
/*     */       
/* 297 */       if (guibutton.k == 10)
/*     */       {
/* 299 */         onBackToMenuClick();
/*     */       }
/*     */       
/* 302 */       if (guibutton.k == 55) {
/*     */         
/* 304 */         (this.macros.getSettings()).showTextEditorHelp = this.chkShowHelp.checked;
/* 305 */         if (this.textEditor != null)
/*     */         {
/* 307 */           this.textEditor.setHelpVisible(this.chkShowHelp.checked);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onBackToMenuClick() {
/* 318 */     if (this.textEditor.isChanged()) {
/*     */       
/* 320 */       this.closeMe = false;
/* 321 */       String title = I18n.get("gui.save");
/* 322 */       String line1 = I18n.get("editor.savechanges");
/* 323 */       displayDialog((GuiDialogBox)new GuiDialogBoxYesNoCancel(this.j, this, title, line1, this.file.getName(), 0));
/*     */     }
/*     */     else {
/*     */       
/* 327 */       this.textEditor.clear();
/* 328 */       this.j.a((blk)new GuiEditTextFile(this.macros, this.j, this.parent, this.context));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/* 338 */     if (keyCode == 1) {
/*     */       
/* 340 */       onCloseClick();
/*     */       
/*     */       return;
/*     */     } 
/* 344 */     if (this.textEditor != null) {
/*     */       
/* 346 */       boolean ctrl = (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157));
/* 347 */       if (keyCode == 15 && ctrl) {
/*     */         
/* 349 */         onMinimiseClick();
/*     */         return;
/*     */       } 
/* 352 */       if (keyCode == 24 && ctrl) {
/*     */         
/* 354 */         onBackToMenuClick();
/*     */         return;
/*     */       } 
/* 357 */       if (keyCode == 31 && ctrl) {
/*     */         
/* 359 */         onMenuItemClicked("save");
/*     */         
/*     */         return;
/*     */       } 
/* 363 */       this.textEditor.keyTyped(keyChar, keyCode);
/*     */       
/* 365 */       if (keyCode == 59)
/*     */       {
/* 367 */         this.j.a((blk)new GuiCommandReference(this.j, (blk)this, this.textEditor.getActionUnderCursor()));
/*     */       }
/* 369 */       else if (keyCode == 61)
/*     */       {
/* 371 */         onMenuItemClicked("colours");
/*     */       }
/*     */     
/* 374 */     } else if (this.newFileTextBox != null && this.newFileTextBox.m()) {
/*     */       
/* 376 */       if (keyCode == 14 || "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_-. ".indexOf(keyChar) >= 0) {
/*     */         
/* 378 */         this.newFileTextBox.a(keyChar, keyCode);
/*     */       }
/* 380 */       else if (keyCode == 28 || keyCode == 156) {
/*     */         
/* 382 */         if (!createFile() && this.newFileTextBox.b().isEmpty())
/*     */         {
/* 384 */           selectFile();
/*     */         }
/*     */       }
/* 387 */       else if (this.fileListBox != null) {
/*     */         
/* 389 */         if (keyCode == 200) this.fileListBox.up(); 
/* 390 */         if (keyCode == 208) this.fileListBox.down();
/*     */       
/*     */       } 
/* 393 */     } else if (this.fileListBox != null) {
/*     */       
/* 395 */       if (keyCode == 200) this.fileListBox.up(); 
/* 396 */       if (keyCode == 208) this.fileListBox.down(); 
/* 397 */       if (keyCode == 201) this.fileListBox.pageUp(); 
/* 398 */       if (keyCode == 209) this.fileListBox.pageDown(); 
/* 399 */       if (keyCode == 28 || keyCode == 156)
/*     */       {
/* 401 */         selectFile();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void selectFile() {
/* 408 */     if (this.fileListBox != null) {
/*     */       
/* 410 */       this.file = (File)this.fileListBox.getSelectedItem().getData();
/* 411 */       this.banner = I18n.get("editor.editing", new Object[] { this.file.getName() });
/* 412 */       b();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 419 */     if (this.newFileTextBox != null)
/*     */     {
/* 421 */       this.newFileTextBox.a();
/*     */     }
/*     */     
/* 424 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float f) {
/* 433 */     if (this.j.f == null)
/*     */     {
/* 435 */       c();
/*     */     }
/*     */     
/* 438 */     drawScreenWithEnabledState(mouseX, mouseY, f, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreenWithEnabledState(int mouseX, int mouseY, float partialTicks, boolean enabled) {
/* 444 */     a(2, this.m - 26, this.l - 2, this.m - 2, this.backColour);
/*     */     
/* 446 */     if (this.fileListBox != null) this.fileListBox.setEnabled((this.newFileTextBox.b().length() == 0));
/*     */     
/* 448 */     super.a(mouseX, mouseY, partialTicks);
/*     */     
/* 450 */     if (this.file == null) {
/*     */       
/* 452 */       c(this.q, I18n.get("editor.choosefile"), 10, 26, 16777215);
/* 453 */       c(this.q, I18n.get("editor.createnewfile"), 205, 26, 16777215);
/* 454 */       this.banner = "";
/* 455 */       this.newFileTextBox.g();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClickedEx(int mouseX, int mouseY, int button) {
/* 463 */     if (this.newFileTextBox != null)
/*     */     {
/* 465 */       this.newFileTextBox.a(mouseX, mouseY, button);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) {
/* 475 */     if (this.textEditor != null) {
/*     */       
/* 477 */       mouseWheelDelta /= 120;
/*     */       
/* 479 */       while (mouseWheelDelta > 0) {
/*     */         
/* 481 */         this.textEditor.scroll(-1);
/* 482 */         mouseWheelDelta--;
/*     */       } 
/*     */       
/* 485 */       while (mouseWheelDelta < 0) {
/*     */         
/* 487 */         this.textEditor.scroll(1);
/* 488 */         mouseWheelDelta++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean accept(File dir, String name) {
/* 496 */     if (name.startsWith("."))
/*     */     {
/* 498 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 502 */     if (name.matches("^[A-Za-z0-9\\x20_\\-\\.]+\\.txt$"))
/*     */     {
/* 504 */       return true;
/*     */     }
/*     */     
/* 507 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populateFileList() {
/* 512 */     this.fileListBox.clear();
/*     */     
/* 514 */     if (this.fileSuggestion == null)
/*     */     {
/* 516 */       this.fileSuggestion = lastFile;
/*     */     }
/*     */     
/* 519 */     File[] files = this.macros.getMacrosDirectory().listFiles(this);
/* 520 */     File selectFile = null;
/*     */     
/* 522 */     if (files != null) {
/*     */       
/* 524 */       int id = 0;
/* 525 */       for (File file : files) {
/*     */         
/* 527 */         String name = file.getName();
/* 528 */         aip icon = (name.startsWith(".") || !Macros.isValidFileName(name)) ? new aip((ain)air.cg) : new aip(air.aS);
/* 529 */         this.fileListBox.addItem((IListEntry)new EditableListEntry(id++, icon, name, file));
/*     */         
/* 531 */         if (this.fileSuggestion != null && name.equals(this.fileSuggestion))
/*     */         {
/* 533 */           selectFile = file;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 538 */     if (selectFile != null)
/*     */     {
/* 540 */       this.fileListBox.selectData(selectFile);
/*     */     }
/*     */     
/* 543 */     this.fileSuggestion = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMinimiseClick() {
/* 553 */     if (!this.drawMinButton) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 558 */     if ((this.macros.getSettings()).editorMinimisePromptAction.equals("save")) {
/*     */       
/* 560 */       this.textEditor.save();
/* 561 */       minimise();
/*     */     }
/* 563 */     else if ((this.macros.getSettings()).editorMinimisePromptAction.equals("nosave")) {
/*     */       
/* 565 */       minimise();
/*     */     }
/* 567 */     else if (this.textEditor.isChanged()) {
/*     */       
/* 569 */       String title = I18n.get("editor.title.minimise");
/* 570 */       String line1 = I18n.get("editor.prompt.minimise");
/* 571 */       String line2 = I18n.get("editor.prompt.saveonmin");
/* 572 */       String checkboxText = I18n.get("editor.prompt.remember");
/* 573 */       this.j.a((blk)new GuiDialogBoxConfirmWithCheckbox(this.j, this, title, line1, line2, checkboxText, 0));
/*     */     }
/*     */     else {
/*     */       
/* 577 */       minimise();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void minimise() {
/* 583 */     if (this.parent instanceof IMinimisableHost) {
/*     */       
/* 585 */       this.macros.setMinimised(this);
/* 586 */       this.j.a(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCloseClick() {
/* 596 */     if (this.file != null && this.textEditor.isChanged()) {
/*     */       
/* 598 */       this.closeMe = true;
/* 599 */       String title = I18n.get("gui.save");
/* 600 */       String prompt = I18n.get("editor.savechanges");
/* 601 */       displayDialog((GuiDialogBox)new GuiDialogBoxYesNoCancel(this.j, this, title, prompt, this.file.getName(), 0));
/*     */     }
/*     */     else {
/*     */       
/* 605 */       close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void close() {
/* 615 */     if (this.textEditor != null) {
/*     */       
/* 617 */       (this.macros.getSettings()).showTextEditorSyntax = this.textEditor.isHighlighting();
/* 618 */       (this.macros.getSettings()).showTextEditorHelp = this.textEditor.isHelpVisible();
/*     */     } 
/*     */     
/* 621 */     if (this.parent != null) {
/*     */       
/* 623 */       this.parent.onFinishEditingTextFile(this, this.file);
/*     */     }
/*     */     else {
/*     */       
/* 627 */       this.j.a(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogClosed(GuiDialogBox dialog) {
/* 634 */     if (dialog instanceof GuiDialogBoxConfirmWithCheckbox) {
/*     */       
/* 636 */       if (((GuiDialogBoxConfirmWithCheckbox)dialog).getChecked()) {
/*     */         
/* 638 */         if (dialog.getResult() == GuiDialogBox.DialogResult.OK) {
/*     */           
/* 640 */           (this.macros.getSettings()).editorMinimisePromptAction = "save";
/*     */         }
/* 642 */         else if (dialog.getResult() == GuiDialogBox.DialogResult.CANCEL) {
/*     */           
/* 644 */           (this.macros.getSettings()).editorMinimisePromptAction = "nosave";
/*     */         } 
/*     */         
/* 647 */         this.macros.save();
/*     */       } 
/*     */       
/* 650 */       if (dialog.getResult() == GuiDialogBox.DialogResult.OK)
/*     */       {
/* 652 */         this.textEditor.save();
/*     */       }
/*     */       
/* 655 */       minimise();
/*     */       
/*     */       return;
/*     */     } 
/* 659 */     if (dialog instanceof GuiDialogBoxYesNoCancel) {
/*     */       
/* 661 */       if (dialog.getResult() == GuiDialogBox.DialogResult.YES) {
/*     */         
/* 663 */         this.textEditor.save();
/*     */         
/* 665 */         if (this.closeMe)
/*     */         {
/* 667 */           close();
/*     */         }
/*     */         else
/*     */         {
/* 671 */           this.j.a((blk)new GuiEditTextFile(this.macros, this.j, this.parent, this.context));
/*     */         }
/*     */       
/* 674 */       } else if (dialog.getResult() == GuiDialogBox.DialogResult.NO) {
/*     */         
/* 676 */         if (this.closeMe)
/*     */         {
/* 678 */           close();
/*     */         }
/*     */         else
/*     */         {
/* 682 */           this.textEditor.clear();
/* 683 */           this.j.a((blk)new GuiEditTextFile(this.macros, this.j, this.parent, this.context));
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 688 */         this.j.a((blk)this);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 693 */     if (dialog.getResult() == GuiDialogBox.DialogResult.OK)
/*     */     {
/* 695 */       if (dialog instanceof GuiDialogBoxRenameItem) {
/*     */         
/* 697 */         String fileName = Util.sanitiseFileName(((GuiDialogBoxRenameItem)dialog).getNewItemName(), ".txt");
/* 698 */         if (fileName != null) {
/*     */           
/* 700 */           File targetFile = new File(this.macros.getMacrosDirectory(), fileName);
/*     */           
/* 702 */           if (!targetFile.exists()) {
/*     */             try
/*     */             {
/*     */               
/* 706 */               ((GuiDialogBoxRenameItem)dialog).file.renameTo(targetFile);
/* 707 */               populateFileList();
/*     */             }
/* 709 */             catch (Exception ex)
/*     */             {
/* 711 */               Log.printStackTrace(ex);
/*     */             }
/*     */           
/*     */           }
/*     */         } 
/* 716 */       } else if (dialog instanceof GuiDialogBoxConfirm) {
/*     */         
/* 718 */         File deleteFile = (File)this.fileListBox.getSelectedItem().getData();
/*     */         
/* 720 */         if (deleteFile.exists()) {
/*     */           
/*     */           try {
/*     */             
/* 724 */             deleteFile.delete();
/* 725 */             populateFileList();
/*     */           }
/* 727 */           catch (Exception ex) {
/*     */             
/* 729 */             Log.printStackTrace(ex);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 735 */     super.onDialogClosed(dialog);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean createFile() {
/* 743 */     if (this.newFileTextBox != null) {
/*     */       
/* 745 */       String fileName = Util.sanitiseFileName(this.newFileTextBox.b(), ".txt");
/*     */       
/* 747 */       if (fileName != null) {
/*     */         
/* 749 */         this.file = new File(this.macros.getMacrosDirectory(), fileName);
/* 750 */         this.banner = I18n.get("editor.editing", new Object[] { this.file.getName() });
/* 751 */         b();
/* 752 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 756 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiEditTextFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */