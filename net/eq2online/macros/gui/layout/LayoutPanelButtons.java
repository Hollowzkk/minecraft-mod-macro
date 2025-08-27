/*     */ package net.eq2online.macros.gui.layout;
/*     */ 
/*     */ import bib;
/*     */ import bir;
/*     */ import blk;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.MacroTriggerType;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxControlProperties;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxConfirm;
/*     */ import net.eq2online.macros.gui.screens.GuiDesignerBase;
/*     */ import net.eq2online.macros.interfaces.IDialogClosedListener;
/*     */ import net.eq2online.macros.interfaces.IEditablePanel;
/*     */ import net.eq2online.macros.interfaces.ILayoutPanelContainer;
/*     */ import net.eq2online.macros.interfaces.ILayoutWidget;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
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
/*     */ public class LayoutPanelButtons
/*     */   extends LayoutPanel<DesignableGuiControl>
/*     */   implements IDialogClosedListener
/*     */ {
/*     */   private DesignableGuiLayout layout;
/*     */   private Rectangle bounds;
/*     */   private boolean moved;
/*     */   private DesignableGuiControl rightClickedWidget;
/*     */   private DesignableGuiControl copySourceWidget;
/*     */   private GuiDesignerBase parentScreen;
/*  43 */   private DesignableGuiControl.Handle selectedHandle = null;
/*     */   
/*  45 */   private int propertiesDialogX = 30;
/*  46 */   private int propertiesDialogY = 42;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LayoutPanelButtons(Macros macros, bib minecraft, int controlId, GuiDesignerBase parent, DesignableGuiLayout layout) {
/*  57 */     super(macros, minecraft, controlId, MacroTriggerType.CONTROL);
/*  58 */     connect((ILayoutPanelContainer)parent);
/*     */     
/*  60 */     this.parentScreen = parent;
/*  61 */     this.layout = layout;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogClosed(GuiDialogBox dialog) {
/*  67 */     this.propertiesDialogX = dialog.getDialogX();
/*  68 */     this.propertiesDialogY = dialog.getDialogY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/*  77 */     super.release();
/*     */     
/*  79 */     this.macros.getLayoutManager().onSaveSettings((ISettingsStore)this.macros);
/*  80 */     if (this.layout != null)
/*     */     {
/*  82 */       this.layout.beginPlacingControl(null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawControl(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/*  93 */     if (this.mode == IEditablePanel.EditMode.EDIT_ALL || this.mode == IEditablePanel.EditMode.EDIT_BUTTONS || this.mode == IEditablePanel.EditMode.RESERVE) {
/*     */       
/*  95 */       this.hovering = null;
/*  96 */       this.hoverWidgetTime = this.layoutPanelTick;
/*     */     }
/*     */     else {
/*     */       
/* 100 */       calcHover(mouseX, mouseY);
/*     */     } 
/*     */ 
/*     */     
/* 104 */     if (this.layout != null) {
/*     */       
/* 106 */       this.layout.draw(this.bounds, mouseX, mouseY, this.mode, this.selected, this.copySourceWidget);
/* 107 */       a(minecraft, mouseX, mouseY);
/*     */     } 
/*     */ 
/*     */     
/* 111 */     drawHover(mouseX, mouseY, minecraft.k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSizeAndPosition(int left, int top, int controlWidth, int controlHeight) {
/* 126 */     super.setSizeAndPosition(left, top, controlWidth, controlHeight);
/* 127 */     this.bounds = new Rectangle(this.h, this.i, this.f, this.g);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getWidgetPosition(ILayoutWidget widget) {
/* 138 */     Point position = widget.getPosition((bir)this.layout);
/* 139 */     position.translate(-this.h, -this.i);
/* 140 */     return position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ILayoutWidget getWidgetAt(int mouseX, int mouseY) {
/* 150 */     return getWidgetAtAdjustedPosition(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ILayoutWidget getWidgetAtAdjustedPosition(int mouseX, int mouseY) {
/* 157 */     if (mouseX < this.h || mouseY < this.i || mouseX > this.h + this.f || mouseY > this.i + this.f) {
/*     */       
/* 159 */       if ((this.mode == IEditablePanel.EditMode.EDIT_ALL || this.mode == IEditablePanel.EditMode.EDIT_BUTTONS) && this.selected != null && this.layout != null) {
/*     */         
/* 161 */         DesignableGuiControl.Handle handle = this.layout.getControlHandleAt(this.bounds, mouseX, mouseY, this.selected);
/* 162 */         if (handle != null)
/*     */         {
/* 164 */           return (ILayoutWidget)this.selected;
/*     */         }
/*     */       } 
/*     */       
/* 168 */       return null;
/*     */     } 
/*     */     
/* 171 */     if (this.layout == null)
/*     */     {
/* 173 */       return null;
/*     */     }
/*     */     
/* 176 */     DesignableGuiControl selectedControl = (this.mode == IEditablePanel.EditMode.EDIT_ALL || this.mode == IEditablePanel.EditMode.EDIT_BUTTONS) ? this.selected : null;
/* 177 */     DesignableGuiLayout.ClickedControlInfo controlAt = this.layout.getControlAt(this.bounds, mouseX, mouseY, selectedControl);
/* 178 */     return (controlAt != null) ? (ILayoutWidget)controlAt.control : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ILayoutWidget createWidget(int id) {
/* 185 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean keyPressed(char keyChar, int keyCode) {
/* 194 */     if (keyCode == 1)
/*     */     {
/* 196 */       this.layout.beginPlacingControl(null);
/*     */     }
/*     */     
/* 199 */     if (super.keyPressed(keyChar, keyCode))
/*     */     {
/* 201 */       return true;
/*     */     }
/*     */     
/* 204 */     if ((this.mode == IEditablePanel.EditMode.EDIT_ALL || this.mode == IEditablePanel.EditMode.EDIT_BUTTONS) && (keyCode == 211 || keyCode == 14) && this.selected != null) {
/*     */ 
/*     */ 
/*     */       
/* 208 */       this.panelManager.lockMode();
/* 209 */       String title = I18n.get("control.delete.title");
/* 210 */       this.mc.a((blk)new GuiDialogBoxConfirm(this.mc, (GuiScreenEx)this.parentScreen, title, I18n.get("control.delete.line1"), this.selected
/* 211 */             .getName() + " ?", 2, Integer.valueOf(this.selected.getId())));
/*     */     } 
/*     */     
/* 214 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteWidget(int widgetIndex) {
/* 222 */     if (this.layout != null)
/*     */     {
/* 224 */       this.layout.removeControl(widgetIndex);
/*     */     }
/*     */     
/* 227 */     widgetIndex = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dialogClosed() {
/* 232 */     this.panelManager.unlockMode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean b(bib minecraft, int mouseX, int mouseY) {
/* 242 */     DesignableGuiControl clickedWidget = (DesignableGuiControl)getWidgetAtAdjustedPosition(mouseX, mouseY);
/*     */     
/* 244 */     if (clickedWidget != null) {
/*     */       Point gridPos; ILayoutWidget.MousePressedResult result;
/* 246 */       switch (this.mode) {
/*     */         
/*     */         case NONE:
/*     */         case EDIT_ALL:
/*     */         case EDIT_BUTTONS:
/* 251 */           gridPos = this.layout.getDragPoint(this.bounds, clickedWidget);
/* 252 */           result = clickedWidget.mousePressed(minecraft, mouseX - gridPos.x, mouseY - gridPos.y);
/* 253 */           if (result == ILayoutWidget.MousePressedResult.HIT) {
/*     */             
/* 255 */             this.clicked = clickedWidget;
/* 256 */             this.selected = clickedWidget;
/* 257 */             this.dragging = clickedWidget;
/* 258 */             this.mouseDown.x = mouseX;
/* 259 */             this.mouseDown.y = mouseY;
/* 260 */             this.selectedHandle = this.layout.getControlHandleAt(this.bounds, mouseX, mouseY, clickedWidget);
/* 261 */             this.dragging.draggingHandle = this.selectedHandle;
/* 262 */             this.moved = false;
/*     */           }
/* 264 */           else if (result == ILayoutWidget.MousePressedResult.COPY) {
/*     */             
/* 266 */             this.copySourceWidget = (this.copySourceWidget == clickedWidget) ? null : clickedWidget;
/*     */           }
/* 268 */           else if (result == ILayoutWidget.MousePressedResult.PASTE && this.copySourceWidget != null) {
/*     */             
/* 270 */             clickedWidget.copyPropertiesFrom(this.copySourceWidget);
/*     */           } 
/*     */           
/* 273 */           return true;
/*     */         
/*     */         case COPY:
/*     */         case MOVE:
/* 277 */           if (clickedWidget.isBound() && clickedWidget.isBindable()) {
/*     */             
/* 279 */             this.clicked = clickedWidget;
/* 280 */             this.selected = clickedWidget;
/*     */           } 
/*     */           
/* 283 */           return true;
/*     */         
/*     */         case DELETE:
/* 286 */           this.clicked = clickedWidget;
/* 287 */           this.selected = clickedWidget;
/* 288 */           return true;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     } else if (this.layout != null && this.layout.isPlacingControl()) {
/*     */       
/* 296 */       Point clickedPos = this.layout.getRowAndColumnAt(this.bounds, mouseX, mouseY);
/* 297 */       if (clickedPos.x != -1 && clickedPos.y != -1) return true;
/*     */ 
/*     */       
/* 300 */       this.layout.beginPlacingControl(null);
/*     */     } 
/*     */     
/* 303 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean rightClicked(bib minecraft, int mouseX, int mouseY, boolean state) {
/* 313 */     if (this.layout != null) this.layout.beginPlacingControl(null);
/*     */     
/* 315 */     DesignableGuiControl clickedWidget = (DesignableGuiControl)getWidgetAtAdjustedPosition(mouseX, mouseY);
/*     */     
/* 317 */     if (state) {
/*     */       
/* 319 */       this.rightClickedWidget = clickedWidget;
/* 320 */       return false;
/*     */     } 
/* 322 */     if (this.rightClickedWidget != null && this.rightClickedWidget == clickedWidget)
/*     */     {
/* 324 */       displayPropertiesDialog(this.rightClickedWidget);
/*     */     }
/*     */     
/* 327 */     this.rightClickedWidget = null;
/* 328 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void displayPropertiesDialog(DesignableGuiControl control) {
/* 333 */     GuiDialogBoxControlProperties propertiesDialog = control.getPropertiesDialog((GuiScreenEx)this.parentScreen);
/* 334 */     this.mc.a((blk)propertiesDialog);
/* 335 */     propertiesDialog.addClosedListener(this);
/* 336 */     propertiesDialog.setPosition(this.propertiesDialogX, this.propertiesDialogY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleWidgetClick(int id, boolean bindable) {
/* 346 */     if (!this.moved && this.layout != null) {
/*     */       
/* 348 */       this.layout.beginPlacingControl(null);
/*     */       
/* 350 */       DesignableGuiControl control = this.layout.getManager().getControls().getControl(id);
/*     */       
/* 352 */       if (control != null)
/*     */       {
/* 354 */         super.handleWidgetClick(id, control.isBindable());
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
/*     */   protected void handleWidgetDrag(bib minecraft, int mouseX, int mouseY) {
/* 366 */     if (this.layout != null) {
/*     */       
/* 368 */       this.layout.beginPlacingControl(null);
/*     */       
/* 370 */       if (this.selectedHandle == null) {
/*     */         
/* 372 */         this.moved |= this.layout.setWidgetPosition(this.bounds, mouseX, mouseY, this.dragging);
/*     */       }
/*     */       else {
/*     */         
/* 376 */         this.moved = true;
/* 377 */         this.layout.setControlSpan(this.bounds, mouseX, mouseY, this.dragging, this.selectedHandle);
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
/*     */   protected void handleMouseRelease(int mouseX, int mouseY, DesignableGuiControl selectedWidget, ILayoutWidget releasedWidget) {
/* 391 */     if (selectedWidget != null)
/*     */     {
/* 393 */       selectedWidget.draggingHandle = null;
/*     */     }
/*     */     
/* 396 */     if (this.layout != null) {
/*     */       
/* 398 */       String controlType = this.layout.getPlacingControlType();
/* 399 */       DesignableGuiControl control = this.layout.placeControl(this.bounds, mouseX, mouseY, this.copySourceWidget);
/*     */       
/* 401 */       if (control != null && this.copySourceWidget == null)
/*     */       {
/* 403 */         displayPropertiesDialog(control);
/*     */       }
/*     */       
/* 406 */       if (this.copySourceWidget != null)
/*     */       {
/* 408 */         this.layout.beginPlacingControl(controlType);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleMacroDelete(int id) {
/* 419 */     if (this.layout != null && this.selected != null && !this.selected.isBound()) {
/*     */       
/* 421 */       this.panelManager.lockMode();
/* 422 */       this.mc.a((blk)new GuiDialogBoxConfirm(this.mc, (GuiScreenEx)this.parentScreen, I18n.get("control.delete.title"), 
/* 423 */             I18n.get("control.delete.line1"), this.layout.getControl(id).getName() + " ?", 2, Integer.valueOf(id)));
/* 424 */       return true;
/*     */     } 
/*     */     
/* 427 */     return super.handleMacroDelete(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addControl(String controlType) {
/* 437 */     if (this.layout != null)
/*     */     {
/* 439 */       this.layout.beginPlacingControl(controlType);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public DesignableGuiLayout getLayout() {
/* 445 */     return this.layout;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLayout(DesignableGuiLayout layout) {
/* 450 */     this.layout = layout;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\layout\LayoutPanelButtons.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */