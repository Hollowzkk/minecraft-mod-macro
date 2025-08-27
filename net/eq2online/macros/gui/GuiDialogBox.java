/*     */ package net.eq2online.macros.gui;
/*     */ 
/*     */ import bib;
/*     */ import bir;
/*     */ import bja;
/*     */ import bje;
/*     */ import blk;
/*     */ import com.mumfrey.liteloader.client.overlays.IGuiTextField;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Point;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.input.IProhibitOverride;
/*     */ import net.eq2online.macros.interfaces.IDialogClosedListener;
/*     */ import net.eq2online.macros.interfaces.IDialogParent;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiDialogBox
/*     */   extends GuiScreenEx
/*     */   implements IProhibitOverride
/*     */ {
/*     */   protected IDialogParent parent;
/*     */   
/*     */   static class LegacyParentDelegate
/*     */     implements IDialogParent, IDialogClosedListener
/*     */   {
/*     */     private final bib minecraft;
/*     */     private final blk delegate;
/*     */     private int width;
/*     */     private int height;
/*     */     
/*     */     LegacyParentDelegate(bib minecraft, blk delegate) {
/*  43 */       this.minecraft = minecraft;
/*  44 */       this.delegate = delegate;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public blk getDelegate() {
/*  50 */       return this.delegate;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void displayDialog(GuiDialogBox dialog) {
/*  56 */       this.minecraft.a(dialog);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onDialogClosed(GuiDialogBox dialog) {
/*  62 */       this.minecraft.a(this.delegate);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void drawScreenWithEnabledState(int mouseX, int mouseY, float partialTick, boolean enabled) {
/*  68 */       if (this.delegate != null) {
/*     */         
/*  70 */         this.delegate.a(mouseX, mouseY, partialTick);
/*  71 */         bir.a(0, 0, this.width, this.height, -1442840576);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void initParentGui() {
/*  78 */       if (this.delegate != null)
/*     */       {
/*  80 */         this.delegate.b();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setResolution(bib minecraft, int width, int height) {
/*  87 */       this.width = width;
/*  88 */       this.height = height;
/*     */       
/*  90 */       if (this.delegate != null)
/*     */       {
/*  92 */         this.delegate.a(minecraft, width, height);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum DialogResult
/*     */   {
/* 102 */     NONE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     OK,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     CANCEL,
/*     */     
/* 114 */     YES,
/*     */     
/* 116 */     NO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   protected List<IDialogClosedListener> closedListeners = new ArrayList<>();
/*     */ 
/*     */   
/*     */   protected GuiControl btnOk;
/*     */ 
/*     */   
/*     */   protected GuiControl btnCancel;
/*     */ 
/*     */   
/*     */   protected int dialogX;
/*     */ 
/*     */   
/*     */   protected int dialogY;
/*     */ 
/*     */   
/*     */   protected int dialogWidth;
/*     */ 
/*     */   
/*     */   protected int dialogHeight;
/*     */ 
/*     */   
/*     */   protected String dialogTitle;
/*     */   
/*     */   protected boolean centreTitle = true;
/*     */   
/* 149 */   protected int dialogTitleColour = -256;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   protected DialogResult dialogResult = DialogResult.NONE;
/*     */ 
/*     */   
/*     */   protected boolean initPosition = true;
/*     */ 
/*     */   
/*     */   protected boolean initOnDrag = true;
/*     */ 
/*     */   
/*     */   protected boolean movable = false;
/*     */   
/*     */   protected boolean dragging = false;
/*     */   
/* 167 */   protected Point dragOffset = new Point(0, 0);
/*     */ 
/*     */   
/*     */   public static class ControlOffsetData
/*     */   {
/*     */     private final bir control;
/*     */     private final int offsetX;
/*     */     private final int offsetY;
/*     */     
/*     */     public ControlOffsetData(bja control, int dialogX, int dialogY) {
/* 177 */       this.control = (bir)control;
/* 178 */       this.offsetX = control.h - dialogX;
/* 179 */       this.offsetY = control.i - dialogY;
/*     */     }
/*     */ 
/*     */     
/*     */     public ControlOffsetData(bje control, int dialogX, int dialogY) {
/* 184 */       this.control = (bir)control;
/* 185 */       this.offsetX = control.a - dialogX;
/* 186 */       this.offsetY = control.f - dialogY;
/*     */     }
/*     */ 
/*     */     
/*     */     public void update(int dialogX, int dialogY) {
/* 191 */       int xPosition = dialogX + this.offsetX;
/* 192 */       int yPosition = dialogY + this.offsetY;
/*     */       
/* 194 */       if (this.control instanceof GuiControl) {
/*     */         
/* 196 */         ((GuiControl)this.control).setPosition(xPosition, yPosition);
/*     */       }
/* 198 */       else if (this.control instanceof bja) {
/*     */         
/* 200 */         bja button = (bja)this.control;
/* 201 */         button.h = xPosition;
/* 202 */         button.i = yPosition;
/*     */       }
/* 204 */       else if (this.control instanceof IGuiTextField) {
/*     */         
/* 206 */         ((IGuiTextField)this.control).setXPosition(xPosition);
/* 207 */         ((IGuiTextField)this.control).setYPosition(yPosition);
/*     */       }
/* 209 */       else if (this.control instanceof bje) {
/*     */         
/* 211 */         bje textField = (bje)this.control;
/* 212 */         textField.a = xPosition;
/* 213 */         textField.f = yPosition;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/* 218 */   protected final List<ControlOffsetData> dragOffsets = new ArrayList<>();
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
/*     */   protected GuiDialogBox(bib minecraft, blk parentScreen, int width, int height, String windowTitle) {
/* 230 */     super(minecraft, true);
/*     */     
/* 232 */     setParent(parentScreen);
/*     */     
/* 234 */     this.dialogWidth = width;
/* 235 */     this.dialogHeight = height;
/* 236 */     this.dialogTitle = windowTitle;
/*     */     
/* 238 */     this.e = 500.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setParent(blk screen) {
/* 243 */     if (this.parent != null)
/*     */     {
/* 245 */       this.closedListeners.remove(this.parent);
/*     */     }
/*     */     
/* 248 */     this.parent = (screen instanceof IDialogParent) ? (IDialogParent)screen : new LegacyParentDelegate(this.j, screen);
/*     */     
/* 250 */     if (this.parent instanceof IDialogClosedListener)
/*     */     {
/* 252 */       addClosedListener((IDialogClosedListener)this.parent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addClosedListener(IDialogClosedListener listener) {
/* 258 */     if (!this.closedListeners.contains(listener))
/*     */     {
/* 260 */       this.closedListeners.add(listener);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 266 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public DialogResult getResult() {
/* 271 */     return this.dialogResult;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValue() {
/* 276 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPosition(int xPosition, int yPosition) {
/* 281 */     this.dialogX = xPosition;
/* 282 */     this.dialogY = yPosition;
/*     */     
/* 284 */     if (this.dialogX < 0) this.dialogX = 0; 
/* 285 */     if (this.dialogX > this.l) this.dialogX = this.l - this.dialogWidth; 
/* 286 */     if (this.dialogY < 9) this.dialogY = 18; 
/* 287 */     if (this.dialogY > this.m) this.dialogY = this.m - this.dialogHeight;
/*     */     
/* 289 */     this.initPosition = false;
/* 290 */     b();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDialogX() {
/* 295 */     return this.dialogX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDialogY() {
/* 300 */     return this.dialogY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void closeDialog() {
/* 309 */     if (this.closedListeners.size() < 1) {
/*     */       
/* 311 */       this.j.a(null);
/*     */     }
/*     */     else {
/*     */       
/* 315 */       for (IDialogClosedListener closedListener : this.closedListeners)
/*     */       {
/* 317 */         closedListener.onDialogClosed(this);
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
/*     */   protected final void a(bja guibutton) {
/* 330 */     if (guibutton.k == this.btnCancel.k) {
/*     */       
/* 332 */       this.dialogResult = DialogResult.CANCEL;
/* 333 */       closeDialog();
/*     */     }
/* 335 */     else if (guibutton.k == this.btnOk.k) {
/*     */       
/* 337 */       if (validateDialog()) {
/*     */         
/* 339 */         this.dialogResult = DialogResult.OK;
/* 340 */         onSubmit();
/* 341 */         closeDialog();
/*     */       } 
/*     */     } 
/*     */     
/* 345 */     if (guibutton instanceof GuiControl)
/*     */     {
/* 347 */       actionPerformed((GuiControl)guibutton);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiControl control) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void a(char keyChar, int keyCode) {
/* 362 */     if (keyCode == 1) {
/*     */       
/* 364 */       dialogCancel();
/*     */     }
/* 366 */     else if (keyCode == 28 || keyCode == 156) {
/*     */       
/* 368 */       dialogSubmit();
/*     */     }
/*     */     else {
/*     */       
/* 372 */       dialogKeyTyped(keyChar, keyCode);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dialogSubmit() {
/* 378 */     a(this.btnOk);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dialogCancel() {
/* 383 */     a(this.btnCancel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void a(int mouseX, int mouseY, int button) throws IOException {
/* 393 */     if (button == 0 && this.movable && mouseX > this.dialogX && mouseX < this.dialogX + this.dialogWidth && mouseY > this.dialogY - 18 && mouseY < this.dialogY) {
/*     */ 
/*     */       
/* 396 */       beginDragging(mouseX, mouseY, button);
/*     */     }
/*     */     else {
/*     */       
/* 400 */       dialogMouseClicked(mouseX, mouseY, button);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void beginDragging(int mouseX, int mouseY, int button) {
/* 406 */     this.dragOffset = new Point(mouseX - this.dialogX, mouseY - this.dialogY);
/* 407 */     this.dragging = true;
/* 408 */     this.dragOffsets.clear();
/*     */     
/* 410 */     for (bja control : this.n)
/*     */     {
/* 412 */       this.dragOffsets.add(new ControlOffsetData(control, this.dialogX, this.dialogY));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
/* 422 */     if (this.dragging) {
/*     */       
/* 424 */       this.dialogX = mouseX - this.dragOffset.x;
/* 425 */       this.dialogY = mouseY - this.dragOffset.y;
/* 426 */       updateControls();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMouseClicked(int mouseX, int mouseY, int button) throws IOException {
/* 437 */     super.a(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void b(int mouseX, int mouseY, int button) {
/* 447 */     if (this.dragging) {
/*     */       
/* 449 */       this.dialogX = mouseX - this.dragOffset.x;
/* 450 */       this.dialogY = mouseY - this.dragOffset.y;
/* 451 */       updateControls();
/*     */     } 
/*     */     
/* 454 */     if (button == 0 && this.dragging) {
/*     */       
/* 456 */       if (this.dialogX < 0) this.dialogX = 0; 
/* 457 */       if (this.dialogX > this.l) this.dialogX = this.l - this.dialogWidth; 
/* 458 */       if (this.dialogY < 9) this.dialogY = 18; 
/* 459 */       if (this.dialogY > this.m) this.dialogY = this.m - this.dialogHeight; 
/* 460 */       updateControls();
/* 461 */       this.dragging = false;
/*     */       
/*     */       return;
/*     */     } 
/* 465 */     super.b(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateControls() {
/* 470 */     if (this.initOnDrag) {
/*     */       
/* 472 */       b();
/*     */       
/*     */       return;
/*     */     } 
/* 476 */     for (ControlOffsetData offset : this.dragOffsets)
/*     */     {
/* 478 */       offset.update(this.dialogX, this.dialogY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void b() {
/* 488 */     super.b();
/*     */     
/* 490 */     Keyboard.enableRepeatEvents(true);
/*     */ 
/*     */     
/* 493 */     this.parent.initParentGui();
/*     */     
/* 495 */     if (!this.dragging && this.initPosition) {
/*     */       
/* 497 */       this.initPosition = false;
/*     */ 
/*     */       
/* 500 */       this.dialogX = (this.l - this.dialogWidth) / 2;
/* 501 */       this.dialogY = (this.m - this.dialogHeight) / 2;
/*     */     } 
/*     */     
/* 504 */     int right = this.dialogX + this.dialogWidth;
/* 505 */     int bottom = this.dialogY + this.dialogHeight;
/* 506 */     this.btnOk = new GuiControl(-1, right - 62, bottom - 22, 60, 20, I18n.get("gui.ok"));
/* 507 */     this.btnCancel = new GuiControl(-2, right - 124, bottom - 22, 60, 20, I18n.get("gui.cancel"));
/*     */     
/* 509 */     clearControlList();
/* 510 */     addControl(this.btnOk);
/* 511 */     addControl(this.btnCancel);
/*     */     
/* 513 */     initDialog();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void m() {
/* 519 */     Keyboard.enableRepeatEvents(false);
/*     */     
/* 521 */     onDialogClosed();
/* 522 */     super.m();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(bib minecraft, int width, int height) {
/* 528 */     super.a(minecraft, width, height);
/*     */     
/* 530 */     this.parent.setResolution(minecraft, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void a(int mouseX, int mouseY, float partialTick) {
/* 536 */     drawParentScreen(mouseX, mouseY, partialTick);
/*     */     
/* 538 */     GL.glClear(256);
/*     */     
/* 540 */     GL.glPushMatrix();
/* 541 */     GL.glTranslatef(0.0F, 0.0F, 20.0F);
/* 542 */     GL.glEnableDepthTest();
/*     */     
/* 544 */     int backColour = -1442840576;
/* 545 */     int backColour2 = -869059789;
/*     */ 
/*     */     
/* 548 */     a(this.dialogX, this.dialogY - 18, this.dialogX + this.dialogWidth, this.dialogY, backColour2);
/*     */     
/* 550 */     if (this.centreTitle) {
/*     */       
/* 552 */       a(this.q, this.dialogTitle, this.dialogX + this.dialogWidth / 2, this.dialogY - 13, this.dialogTitleColour);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 557 */       c(this.q, this.dialogTitle, this.dialogX + 5, this.dialogY - 13, this.dialogTitleColour);
/*     */     } 
/*     */ 
/*     */     
/* 561 */     a(this.dialogX, this.dialogY, this.dialogX + this.dialogWidth, this.dialogY + this.dialogHeight, backColour);
/*     */ 
/*     */     
/* 564 */     drawDialog(mouseX, mouseY, partialTick);
/*     */ 
/*     */     
/* 567 */     super.a(mouseX, mouseY, partialTick);
/*     */     
/* 569 */     postRender(mouseX, mouseY, partialTick);
/*     */     
/* 571 */     renderShadow();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderShadow() {
/* 579 */     GL.glPopMatrix();
/*     */     
/* 581 */     int alphaFunc = GL.glGetInteger(3009);
/* 582 */     float alphaRef = GL.glGetFloat(3010);
/* 583 */     GL.glEnableDepthTest();
/* 584 */     GL.glDepthFunc(515);
/* 585 */     GL.glAlphaFunc(516, 0.0F);
/* 586 */     GL.glEnableBlend();
/* 587 */     this.e = 0.0F;
/* 588 */     this.renderer.drawTessellatedModalBorderRect(ResourceLocations.EXT, 128, this.dialogX - 6, this.dialogY - 24, this.dialogX + this.dialogWidth + 6, this.dialogY + this.dialogHeight + 6, 96, 80, 128, 112, 16);
/*     */     
/* 590 */     this.e = 500.0F;
/* 591 */     this.renderer.setTexMapSize(256);
/*     */     
/* 593 */     GL.glAlphaFunc(alphaFunc, alphaRef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawParentScreen(int mouseX, int mouseY, float partialTick) {
/* 601 */     if (this.parent != null) {
/*     */       
/* 603 */       this.parent.drawScreenWithEnabledState(0, 0, partialTick, false);
/*     */     }
/* 605 */     else if (this.j.f == null) {
/*     */       
/* 607 */       c();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawDialog(int mouseX, int mouseY, float f) {}
/*     */   
/*     */   protected void postRender(int mouseX, int mouseY, float f) {}
/*     */   
/*     */   public abstract void onSubmit();
/*     */   
/*     */   public void onDialogSubmissionFailed(String reason) {}
/*     */   
/*     */   public abstract boolean validateDialog();
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {}
/*     */   
/*     */   protected void initDialog() {}
/*     */   
/*     */   protected void onDialogClosed() {}
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\GuiDialogBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */