/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bib;
/*     */ import bja;
/*     */ import blk;
/*     */ import bud;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.compatibility.IconTiled;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.controls.GuiListBoxIconic;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.gui.list.ListEntry;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.struct.Place;
/*     */ import nf;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import rk;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiEditListEntry<TItem>
/*     */   extends GuiScreenEx
/*     */ {
/*     */   public static final String NUMERALS = "-0123456789";
/*     */   private final Macros macros;
/*     */   private final GuiMacroParam<TItem> parentScreen;
/*     */   private final MacroParam<TItem> param;
/*     */   private GuiTextFieldEx textFieldItemName;
/*     */   private GuiTextFieldEx textFieldDisplayName;
/*     */   private GuiTextFieldEx textFieldXCoord;
/*     */   private GuiTextFieldEx textFieldYCoord;
/*     */   private GuiTextFieldEx textFieldZCoord;
/*     */   private GuiListBoxIconic<Object> listBoxChooseIcon;
/*     */   private GuiControl btnOk;
/*     */   private GuiControl btnCancel;
/*     */   private GuiControl btnAutoPopulate;
/*     */   private GuiControl btnUseCurrentLocation;
/*     */   protected IListEntry<TItem> editingObject;
/*     */   public String displayText;
/*     */   public boolean enableIconChoice = true;
/*     */   public boolean enableDisplayName = false;
/*     */   public boolean enableCoords = false;
/*  90 */   public int windowHeight = 158;
/*     */   
/*  92 */   protected int startIconIndex = 0, endIconIndex = 255;
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
/*     */   public GuiEditListEntry(Macros macros, bib minecraft, GuiMacroParam<TItem> guiscreen, MacroParam<TItem> param, IListEntry<TItem> editingObject) {
/* 106 */     super(minecraft);
/*     */     
/* 108 */     this.macros = macros;
/* 109 */     this.parentScreen = guiscreen;
/* 110 */     this.param = param;
/* 111 */     this.editingObject = editingObject;
/*     */     
/* 113 */     this.param.setupEditEntryWindow(this, (editingObject != null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 122 */     Keyboard.enableRepeatEvents(true);
/*     */     
/* 124 */     nf texture = this.param.getIconTexture();
/* 125 */     Boolean populateList = Boolean.valueOf(false);
/*     */     
/* 127 */     if (this.textFieldItemName == null) {
/*     */       
/* 129 */       this.textFieldItemName = new GuiTextFieldEx(0, this.q, 188, 20, 196, 16, "");
/* 130 */       this.textFieldDisplayName = new GuiTextFieldEx(1, this.q, 188, this.windowHeight - 46, 196, 16, "");
/*     */     } 
/*     */     
/* 133 */     this.textFieldItemName.b(true);
/* 134 */     this.textFieldItemName.f(256);
/*     */     
/* 136 */     if (this.textFieldXCoord == null && this.enableCoords) {
/*     */       
/* 138 */       this.textFieldXCoord = new GuiTextFieldEx(2, this.q, 208, 50, 40, 16, "");
/* 139 */       this.textFieldYCoord = new GuiTextFieldEx(3, this.q, 208, 70, 40, 16, "");
/* 140 */       this.textFieldZCoord = new GuiTextFieldEx(4, this.q, 208, 90, 40, 16, "");
/* 141 */       setCoordsFromPlayerLocation();
/*     */     } 
/*     */     
/* 144 */     if (this.listBoxChooseIcon == null && this.enableIconChoice) {
/*     */       
/* 146 */       this.listBoxChooseIcon = new GuiListBoxIconic(this.j, 2, 188, 54, 196, 80, 20, true, false, false);
/* 147 */       populateList = Boolean.valueOf(true);
/*     */     } 
/*     */     
/* 150 */     this.param.setupEditEntryTextbox(this.textFieldItemName);
/*     */     
/* 152 */     if (this.param.isAutoPopulateSupported() && (this.macros.getSettings()).enableAutoDiscovery)
/*     */     {
/* 154 */       this.btnAutoPopulate = new GuiControl(3, 324, this.windowHeight - 22, 60, 20, I18n.get("gui.auto"));
/*     */     }
/*     */     
/* 157 */     if (this.enableCoords)
/*     */     {
/* 159 */       this.btnUseCurrentLocation = new GuiControl(4, 255, 68, 129, 20, I18n.get("entry.usemylocation"));
/*     */     }
/*     */ 
/*     */     
/* 163 */     if (this.editingObject != null) {
/*     */       
/* 165 */       texture = this.editingObject.getIconTexture();
/* 166 */       this.textFieldItemName.a(this.editingObject.getText());
/* 167 */       this.textFieldDisplayName.a(this.editingObject.getDisplayName());
/*     */       
/* 169 */       if (this.editingObject.getDisplayName().equals(this.editingObject.getText()))
/*     */       {
/* 171 */         this.textFieldDisplayName.a("");
/*     */       }
/*     */       
/* 174 */       this.btnAutoPopulate = null;
/*     */       
/* 176 */       if (this.editingObject instanceof net.eq2online.macros.gui.list.PlaceListEntry) {
/*     */         
/* 178 */         this.textFieldXCoord.a("" + ((Place)this.editingObject.getData()).x);
/* 179 */         this.textFieldYCoord.a("" + ((Place)this.editingObject.getData()).y);
/* 180 */         this.textFieldZCoord.a("" + ((Place)this.editingObject.getData()).z);
/*     */       } 
/*     */     } 
/*     */     
/* 184 */     if (populateList.booleanValue() && this.listBoxChooseIcon != null)
/*     */     {
/* 186 */       for (int icon = this.startIconIndex; icon <= this.endIconIndex; icon++)
/*     */       {
/* 188 */         this.listBoxChooseIcon.addItem((IListEntry)new ListEntry(icon, I18n.get("gui.icon") + " " + (icon - this.startIconIndex + 1), null, true, texture, icon));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 193 */     if (this.editingObject != null && this.listBoxChooseIcon != null) {
/*     */       
/* 195 */       IconTiled icon = (IconTiled)this.editingObject.getIcon();
/* 196 */       if (icon != null) {
/*     */         
/* 198 */         this.listBoxChooseIcon.selectId(icon.getIconId());
/* 199 */         this.listBoxChooseIcon.scrollToCentre();
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     this
/* 204 */       .btnOk = new GuiControl(0, 186, this.windowHeight - 22, 60, 20, (this.editingObject == null) ? I18n.get("gui.add") : I18n.get("gui.save"));
/* 205 */     this.btnCancel = new GuiControl(1, 250, this.windowHeight - 22, 60, 20, I18n.get("gui.cancel"));
/*     */     
/* 207 */     clearControlList();
/* 208 */     addControl(this.btnOk);
/* 209 */     addControl(this.btnCancel);
/*     */     
/* 211 */     if (this.btnAutoPopulate != null) addControl(this.btnAutoPopulate); 
/* 212 */     if (this.listBoxChooseIcon != null) addControl((GuiControl)this.listBoxChooseIcon); 
/* 213 */     if (this.btnUseCurrentLocation != null) addControl(this.btnUseCurrentLocation);
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void m() {
/* 219 */     Keyboard.enableRepeatEvents(false);
/*     */     
/* 221 */     super.m();
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
/* 232 */     String itemName = this.textFieldItemName.b();
/* 233 */     if (guibutton.k == 0 && itemName.length() >= this.textFieldItemName.minStringLength) {
/*     */       
/* 235 */       String displayName = this.textFieldDisplayName.b();
/* 236 */       int iconId = (this.listBoxChooseIcon != null) ? this.listBoxChooseIcon.getSelectedItem().getId() : -1;
/*     */       
/* 238 */       if (this.editingObject == null) {
/*     */         
/* 240 */         if (this.param.addItem(this, itemName, displayName, iconId, null))
/*     */         {
/*     */           return;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 247 */         this.param.editItem(this, itemName, displayName, iconId, this.editingObject);
/*     */       } 
/*     */       
/* 250 */       this.j.a((blk)this.parentScreen);
/*     */     }
/* 252 */     else if (guibutton.k == 1) {
/*     */       
/* 254 */       this.j.a((blk)this.parentScreen);
/*     */     }
/* 256 */     else if (guibutton.k == 3) {
/*     */       
/* 258 */       this.parentScreen.autoPopulate();
/*     */     }
/* 260 */     else if (guibutton.k == 4) {
/*     */       
/* 262 */       setCoordsFromPlayerLocation();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setCoordsFromPlayerLocation() {
/* 268 */     if (this.textFieldYCoord != null) {
/*     */       
/* 270 */       bud thePlayer = this.j.h;
/*     */       
/* 272 */       if (thePlayer != null) {
/*     */         
/* 274 */         int posX = rk.c(thePlayer.p);
/* 275 */         int posY = rk.c((thePlayer.bw()).b);
/* 276 */         int posZ = rk.c(thePlayer.r);
/*     */         
/* 278 */         this.textFieldXCoord.a(String.format("%d", new Object[] { Integer.valueOf(posX) }));
/* 279 */         this.textFieldYCoord.a(String.format("%d", new Object[] { Integer.valueOf(posY) }));
/* 280 */         this.textFieldZCoord.a(String.format("%d", new Object[] { Integer.valueOf(posZ) }));
/*     */       }
/*     */       else {
/*     */         
/* 284 */         this.textFieldXCoord.a("0");
/* 285 */         this.textFieldYCoord.a("0");
/* 286 */         this.textFieldZCoord.a("0");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 297 */     super.e();
/*     */     
/* 299 */     this.textFieldItemName.a();
/* 300 */     this.textFieldDisplayName.a();
/*     */     
/* 302 */     if (this.textFieldXCoord != null) this.textFieldXCoord.a(); 
/* 303 */     if (this.textFieldYCoord != null) this.textFieldYCoord.a(); 
/* 304 */     if (this.textFieldZCoord != null) this.textFieldZCoord.a();
/*     */     
/* 306 */     if (this.listBoxChooseIcon != null)
/*     */     {
/* 308 */       this.listBoxChooseIcon.updateCounter = this.updateCounter;
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
/*     */   public void a(int mouseX, int mouseY, float f) {
/* 323 */     this.parentScreen.drawScreenWithEnabledState(mouseX, mouseY, f, false);
/*     */ 
/*     */     
/* 326 */     a(184, 2, 388, this.windowHeight, -2146562560);
/*     */ 
/*     */     
/* 329 */     c(this.q, this.displayText, 188, 8, -1);
/* 330 */     if (this.listBoxChooseIcon != null)
/*     */     {
/* 332 */       c(this.q, I18n.get("entry.chooseicon"), 188, 42, -1);
/*     */     }
/*     */ 
/*     */     
/* 336 */     this.textFieldItemName.g();
/*     */     
/* 338 */     if (this.enableDisplayName) {
/*     */       
/* 340 */       c(this.q, I18n.get("entry.displayname"), 188, this.windowHeight - 58, -1);
/* 341 */       this.textFieldDisplayName.g();
/*     */     } 
/*     */     
/* 344 */     if (this.textFieldXCoord != null) {
/*     */       
/* 346 */       this.textFieldXCoord.g();
/* 347 */       c(this.q, "X:", 193, 54, -1);
/*     */     } 
/*     */     
/* 350 */     if (this.textFieldYCoord != null) {
/*     */       
/* 352 */       this.textFieldYCoord.g();
/* 353 */       c(this.q, "Y:", 193, 74, -1);
/*     */     } 
/*     */     
/* 356 */     if (this.textFieldZCoord != null) {
/*     */       
/* 358 */       this.textFieldZCoord.g();
/* 359 */       c(this.q, "Z:", 193, 95, -1);
/*     */     } 
/*     */ 
/*     */     
/* 363 */     super.a(mouseX, mouseY, f);
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
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 376 */     this.textFieldItemName.a(mouseX, mouseY, button);
/*     */     
/* 378 */     if (this.enableDisplayName)
/*     */     {
/* 380 */       this.textFieldDisplayName.a(mouseX, mouseY, button);
/*     */     }
/*     */     
/* 383 */     if (this.textFieldXCoord != null) this.textFieldXCoord.a(mouseX, mouseY, button); 
/* 384 */     if (this.textFieldYCoord != null) this.textFieldYCoord.a(mouseX, mouseY, button); 
/* 385 */     if (this.textFieldZCoord != null) this.textFieldZCoord.a(mouseX, mouseY, button);
/*     */     
/* 387 */     super.a(mouseX, mouseY, button);
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
/*     */   protected void a(char keyChar, int keyCode) {
/* 399 */     if (keyCode == 1) {
/*     */       
/* 401 */       this.j.a((blk)this.parentScreen);
/*     */     }
/* 403 */     else if (keyCode == 28 || keyCode == 156) {
/*     */       
/* 405 */       a((bja)this.btnOk);
/*     */     }
/* 407 */     else if (keyCode == 200) {
/*     */       
/* 409 */       if (this.listBoxChooseIcon != null)
/*     */       {
/* 411 */         this.textFieldItemName.b(false);
/* 412 */         this.textFieldDisplayName.b(false);
/* 413 */         this.listBoxChooseIcon.up();
/*     */       }
/*     */     
/* 416 */     } else if (keyCode == 208) {
/*     */       
/* 418 */       if (this.listBoxChooseIcon != null)
/*     */       {
/* 420 */         this.textFieldItemName.b(false);
/* 421 */         this.textFieldDisplayName.b(false);
/* 422 */         this.listBoxChooseIcon.down();
/*     */       }
/*     */     
/* 425 */     } else if (keyCode == 205 && this.listBoxChooseIcon != null && 
/*     */       
/* 427 */       !this.textFieldItemName.m() && 
/* 428 */       !this.textFieldDisplayName.m()) {
/*     */       
/* 430 */       this.listBoxChooseIcon.right();
/*     */     }
/* 432 */     else if (keyCode == 203 && this.listBoxChooseIcon != null && 
/*     */       
/* 434 */       !this.textFieldItemName.m() && 
/* 435 */       !this.textFieldDisplayName.m()) {
/*     */       
/* 437 */       this.listBoxChooseIcon.left();
/*     */     }
/* 439 */     else if (keyCode == 15) {
/*     */       
/* 441 */       selectNextField();
/*     */     }
/* 443 */     else if (this.textFieldItemName.m()) {
/*     */       
/* 445 */       this.textFieldItemName.a(keyChar, keyCode);
/*     */       
/* 447 */       if (this.editingObject == null && this.enableDisplayName)
/*     */       {
/* 449 */         this.textFieldDisplayName.a(this.textFieldItemName.b());
/*     */       }
/*     */     }
/* 452 */     else if (this.enableDisplayName && this.textFieldDisplayName.m()) {
/*     */       
/* 454 */       this.textFieldDisplayName.a(keyChar, keyCode);
/*     */     }
/* 456 */     else if (this.textFieldXCoord != null && (keyCode == 14 || "-0123456789".indexOf(keyChar) >= 0)) {
/*     */       
/* 458 */       if (this.textFieldXCoord.m())
/*     */       {
/* 460 */         this.textFieldXCoord.a(keyChar, keyCode);
/*     */       }
/* 462 */       else if (this.textFieldYCoord.m())
/*     */       {
/* 464 */         this.textFieldYCoord.a(keyChar, keyCode);
/*     */       }
/* 466 */       else if (this.textFieldZCoord.m())
/*     */       {
/* 468 */         this.textFieldZCoord.a(keyChar, keyCode);
/*     */       }
/*     */     
/* 471 */     } else if (keyCode == 203) {
/*     */       
/* 473 */       if (this.listBoxChooseIcon != null)
/*     */       {
/* 475 */         this.listBoxChooseIcon.left();
/*     */       }
/*     */     }
/* 478 */     else if (keyCode == 205) {
/*     */       
/* 480 */       if (this.listBoxChooseIcon != null)
/*     */       {
/* 482 */         this.listBoxChooseIcon.right();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectNextField() {
/* 489 */     if (this.textFieldXCoord == null) {
/*     */       
/* 491 */       if (this.enableDisplayName)
/*     */       {
/* 493 */         if (this.textFieldItemName.m())
/*     */         {
/* 495 */           this.textFieldItemName.b(false); this.textFieldDisplayName.b(true);
/*     */         }
/*     */         else
/*     */         {
/* 499 */           this.textFieldItemName.b(true); this.textFieldDisplayName.b(false);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     }
/* 505 */     else if (this.textFieldItemName.m()) {
/*     */       
/* 507 */       this.textFieldItemName.b(false);
/* 508 */       this.textFieldXCoord.b(true);
/* 509 */       this.textFieldYCoord.b(false);
/* 510 */       this.textFieldZCoord.b(false);
/*     */     }
/* 512 */     else if (this.textFieldXCoord.m()) {
/*     */       
/* 514 */       this.textFieldItemName.b(false);
/* 515 */       this.textFieldXCoord.b(false);
/* 516 */       this.textFieldYCoord.b(true);
/* 517 */       this.textFieldZCoord.b(false);
/*     */     }
/* 519 */     else if (this.textFieldYCoord.m()) {
/*     */       
/* 521 */       this.textFieldItemName.b(false);
/* 522 */       this.textFieldXCoord.b(false);
/* 523 */       this.textFieldYCoord.b(false);
/* 524 */       this.textFieldZCoord.b(true);
/*     */     }
/*     */     else {
/*     */       
/* 528 */       this.textFieldItemName.b(true);
/* 529 */       this.textFieldXCoord.b(false);
/* 530 */       this.textFieldYCoord.b(false);
/* 531 */       this.textFieldZCoord.b(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getXCoordText() {
/* 538 */     return this.textFieldXCoord.b();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getYCoordText() {
/* 543 */     return this.textFieldYCoord.b();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getZCoordText() {
/* 548 */     return this.textFieldZCoord.b();
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiEditListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */