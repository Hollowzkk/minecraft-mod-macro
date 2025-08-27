/*     */ package net.eq2online.macros.gui.layout;
/*     */ 
/*     */ import bib;
/*     */ import bip;
/*     */ import bir;
/*     */ import com.mumfrey.liteloader.gl.GLClippingPlanes;
/*     */ import java.awt.Point;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.MacroPlaybackType;
/*     */ import net.eq2online.macros.core.MacroTemplate;
/*     */ import net.eq2online.macros.core.MacroTriggerType;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.settings.Settings;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.interfaces.IEditablePanel;
/*     */ import net.eq2online.macros.interfaces.ILayoutPanel;
/*     */ import net.eq2online.macros.interfaces.ILayoutPanelContainer;
/*     */ import net.eq2online.macros.interfaces.ILayoutWidget;
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
/*     */ public abstract class LayoutPanel<TWidget extends ILayoutWidget>
/*     */   extends GuiControlEx
/*     */   implements ILayoutPanel<TWidget>
/*     */ {
/*     */   protected static final int TOOLTIP_DELAY_TICKS = 6;
/*     */   protected final Macros macros;
/*     */   protected final PanelManager panelManager;
/*     */   protected final Settings settings;
/*     */   protected final MacroTriggerType macroType;
/*  39 */   protected IEditablePanel.EditMode mode = IEditablePanel.EditMode.NONE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   protected ILayoutWidget[] widgets = new ILayoutWidget[10000];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TWidget clicked;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TWidget selected;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TWidget dragging;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TWidget hovering;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   protected Point mouseDown = new Point();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int layoutPanelTick;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int hoverWidgetTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String hoverModifier;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MacroTemplate hoverTemplate;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean centreAlign = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   protected int widgetWidth = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   protected int widgetHeight = 14;
/*     */ 
/*     */ 
/*     */   
/*     */   protected ILayoutPanelContainer parent;
/*     */ 
/*     */ 
/*     */   
/*     */   protected LayoutPanel(Macros macros, bib minecraft, int controlId, MacroTriggerType type) {
/* 114 */     super(minecraft, controlId, 0, 0, 0, 0, "");
/*     */     
/* 116 */     this.macros = macros;
/* 117 */     this.settings = macros.getSettings();
/* 118 */     this.panelManager = macros.getLayoutPanels();
/* 119 */     this.macroType = type;
/* 120 */     init();
/*     */     
/* 122 */     this.panelManager.registerPanel((IEditablePanel)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMode(IEditablePanel.EditMode mode) {
/* 128 */     this.mode = mode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tickInGui() {
/* 134 */     this.layoutPanelTick++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void init();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ILayoutPanelContainer parent) {
/* 149 */     this.panelManager.setMode(IEditablePanel.EditMode.NONE);
/* 150 */     this.parent = parent;
/* 151 */     this.selected = null;
/* 152 */     this.clicked = null;
/* 153 */     this.dragging = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/* 162 */     this.panelManager.setMode(IEditablePanel.EditMode.NONE);
/* 163 */     this.parent = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSizeAndPosition(int left, int top, int controlWidth, int controlHeight) {
/* 173 */     this.h = left;
/* 174 */     this.i = top;
/* 175 */     this.f = controlWidth;
/* 176 */     this.g = controlHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDragging() {
/* 185 */     return (this.clicked != null && (this.mode == IEditablePanel.EditMode.COPY || this.mode == IEditablePanel.EditMode.MOVE));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Point getWidgetPosition(ILayoutWidget widget) {
/* 191 */     return widget.getPosition((bir)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ILayoutWidget getWidgetAt(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ILayoutWidget getWidgetAtAdjustedPosition(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ILayoutWidget getWidget(int id, boolean create) {
/* 218 */     if (!this.macroType.supportsId(id))
/*     */     {
/* 220 */       return null;
/*     */     }
/*     */     
/* 223 */     if (this.widgets[id] == null && create)
/*     */     {
/* 225 */       this.widgets[id] = createWidget(id);
/*     */     }
/*     */     
/* 228 */     return this.widgets[id];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ILayoutWidget createWidget(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean keyPressed(char keyChar, int keyCode) {
/* 245 */     if (keyCode == 1 && !this.panelManager.isInMode(IEditablePanel.EditMode.NONE)) {
/*     */       
/* 247 */       this.panelManager.setMode(IEditablePanel.EditMode.NONE);
/* 248 */       return true;
/*     */     } 
/*     */     
/* 251 */     return false;
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
/*     */   protected void a(bib minecraft, int mouseX, int mouseY) {
/* 264 */     if (this.dragging != null && mouseHasMoved(mouseX, mouseY, true))
/*     */     {
/* 266 */       handleWidgetDrag(minecraft, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean mouseHasMoved(int mouseX, int mouseY, boolean updatePos) {
/* 272 */     if (this.mouseDown.x != mouseX || this.mouseDown.y != mouseY) {
/*     */       
/* 274 */       if (updatePos) {
/*     */         
/* 276 */         this.mouseDown.x = mouseX;
/* 277 */         this.mouseDown.y = mouseY;
/*     */       } 
/* 279 */       return true;
/*     */     } 
/*     */     
/* 282 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleWidgetDrag(bib minecraft, int mouseX, int mouseY) {
/* 292 */     this.dragging.mouseDragged(minecraft, mouseX - this.h, mouseY - this.i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY) {
/* 301 */     if (this.parent != null)
/*     */     {
/* 303 */       this.parent.captureWidgetAt(mouseX, mouseY);
/*     */     }
/*     */     
/* 306 */     handleMouseRelease(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleMouseRelease(int mouseX, int mouseY) {
/* 312 */     if (this.dragging != null) {
/*     */       
/* 314 */       this.selected = this.dragging;
/* 315 */       this.dragging.mouseReleased(mouseX - this.h, mouseY - this.i);
/* 316 */       this.dragging = null;
/*     */     } 
/*     */     
/* 319 */     ILayoutWidget toWidget = (this.parent != null) ? this.parent.getCapturedWidget() : getWidgetAtAdjustedPosition(mouseX, mouseY);
/*     */     
/* 321 */     if (this.clicked != null) {
/*     */       
/* 323 */       int id = this.clicked.getId();
/*     */       
/* 325 */       switch (this.panelManager.getMode()) {
/*     */ 
/*     */         
/*     */         case ONESHOT:
/* 329 */           if (toWidget == this.clicked)
/*     */           {
/* 331 */             handleWidgetClick(id, true);
/*     */           }
/*     */           break;
/*     */         
/*     */         case KEYSTATE:
/* 336 */           if (toWidget == this.clicked)
/*     */           {
/* 338 */             toWidget.mouseClickedEdit(mouseX - this.h, mouseY - this.i);
/*     */           }
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case CONDITIONAL:
/* 345 */           if (toWidget != null) {
/*     */             
/* 347 */             if (toWidget == this.clicked) {
/*     */               
/* 349 */               handleWidgetClick(id, true);
/*     */               
/*     */               break;
/*     */             } 
/* 353 */             handleMacroCopy(toWidget, id);
/*     */           } 
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case null:
/* 360 */           if (toWidget != null) {
/*     */             
/* 362 */             if (toWidget == this.clicked) {
/*     */               
/* 364 */               handleWidgetClick(id, true);
/*     */               
/*     */               break;
/*     */             } 
/* 368 */             handleMacroMove(toWidget, id);
/*     */           } 
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case null:
/* 375 */           if (toWidget == this.clicked)
/*     */           {
/* 377 */             handleMacroDelete(id);
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case null:
/* 383 */           if (toWidget == this.clicked)
/*     */           {
/* 385 */             toWidget.toggleReservedState();
/*     */           }
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 393 */       handleMouseRelease(mouseX, mouseY, this.clicked, toWidget);
/*     */       
/* 395 */       this.clicked = null;
/*     */     }
/*     */     else {
/*     */       
/* 399 */       handleMouseRelease(mouseX, mouseY, this.clicked, toWidget);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleMacroCopy(ILayoutWidget toWidget, int id) {
/* 409 */     if (toWidget.isBindable()) {
/*     */       
/* 411 */       this.macros.copyMacroTemplate(id, toWidget.getId());
/* 412 */       this.macros.save();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleMacroMove(ILayoutWidget toWidget, int id) {
/* 422 */     if (toWidget.isBindable()) {
/*     */       
/* 424 */       this.macros.moveMacroTemplate(id, toWidget.getId());
/* 425 */       this.macros.save();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleMacroDelete(int id) {
/* 434 */     this.macros.deleteMacroTemplate(id);
/* 435 */     this.macros.save();
/*     */     
/* 437 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleMouseRelease(int mouseX, int mouseY, TWidget selectedWidget, ILayoutWidget releasedWidget) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleWidgetClick(int id, boolean bindable) {
/* 456 */     if (this.parent != null)
/*     */     {
/* 458 */       this.parent.handleWidgetClick(this, id, bindable);
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
/*     */   public boolean b(bib minecraft, int mouseX, int mouseY) {
/* 470 */     ILayoutWidget iLayoutWidget = getWidgetAtAdjustedPosition(mouseX, mouseY);
/*     */     
/* 472 */     if (iLayoutWidget != null)
/*     */     {
/* 474 */       switch (this.mode) {
/*     */         
/*     */         case CONDITIONAL:
/*     */         case null:
/*     */         case null:
/* 479 */           if (!iLayoutWidget.isBound() || !iLayoutWidget.isBindable())
/*     */             break; 
/*     */         case ONESHOT:
/*     */         case null:
/* 483 */           this.clicked = (TWidget)iLayoutWidget;
/* 484 */           return true;
/*     */         
/*     */         case KEYSTATE:
/*     */         case null:
/* 488 */           if (iLayoutWidget.mousePressed(minecraft, mouseX - this.h, mouseY - this.i) == ILayoutWidget.MousePressedResult.HIT) {
/*     */             
/* 490 */             this.dragging = (TWidget)iLayoutWidget;
/* 491 */             this.mouseDown.x = mouseX;
/* 492 */             this.mouseDown.y = mouseY;
/* 493 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 502 */     return false;
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
/*     */   public void postRender(int mouseX, int mouseY) {
/* 514 */     if (this.clicked != null && (this.mode == IEditablePanel.EditMode.COPY || this.mode == IEditablePanel.EditMode.MOVE)) {
/*     */       
/* 516 */       Point sourcePos = getWidgetPosition((ILayoutWidget)this.clicked);
/*     */       
/* 518 */       if (!this.centreAlign)
/*     */       {
/* 520 */         sourcePos.x += this.clicked.getWidth((bir)this) / 2;
/*     */       }
/*     */ 
/*     */       
/* 524 */       int colour = (this.mode == IEditablePanel.EditMode.COPY) ? -16711936 : -16711681;
/*     */ 
/*     */       
/* 527 */       String label = (this.mode == IEditablePanel.EditMode.COPY) ? I18n.get("gui.copy") : I18n.get("gui.move");
/* 528 */       label = label + " " + this.clicked.getDisplayText();
/*     */       
/* 530 */       this.renderer.drawArrow(sourcePos.x + this.h, sourcePos.y + this.i + 7, mouseX, mouseY, 600, 1.0F, 6, colour);
/* 531 */       a(this.mc.k, label, mouseX, mouseY - 16, colour);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawWidgets(int mouseX, int mouseY) {
/* 542 */     GLClippingPlanes.glEnableClipping(this.h, this.h + this.f, -1, -1);
/*     */ 
/*     */     
/* 545 */     for (int i = this.macroType.getMinId(); i <= this.macroType.getAbsoluteMaxId(); i++) {
/*     */       
/* 547 */       ILayoutWidget widget = this.widgets[i];
/* 548 */       if (widget != null)
/*     */       {
/* 550 */         widget.draw((bir)this, null, mouseX - this.h, mouseY - this.i, this.mode, (widget == this.clicked || (this.mode == IEditablePanel.EditMode.EDIT_ALL && widget == this.selected)), false);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 555 */     GLClippingPlanes.glDisableClipping();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void calcHover(int mouseX, int mouseY) {
/* 565 */     ILayoutWidget iLayoutWidget = getWidgetAtAdjustedPosition(mouseX, mouseY);
/*     */     
/* 567 */     if (iLayoutWidget == null) {
/*     */       
/* 569 */       this.hovering = null;
/* 570 */       this.hoverWidgetTime = this.layoutPanelTick;
/*     */     }
/* 572 */     else if (iLayoutWidget != this.hovering) {
/*     */       
/* 574 */       this.hovering = (TWidget)iLayoutWidget;
/* 575 */       this.hoverTemplate = this.macros.getMacroTemplate(this.hovering.getId(), false);
/* 576 */       this.hoverModifier = this.hoverTemplate.getModifiers();
/* 577 */       this.hoverWidgetTime = this.layoutPanelTick;
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
/*     */   protected void drawHover(int mouseX, int mouseY, bip fontRenderer) {
/* 589 */     if (this.hovering != null && this.layoutPanelTick - this.hoverWidgetTime > 6) {
/*     */       
/* 591 */       boolean overlaid = this.macros.isKeyOverlaid(this.hovering.getId());
/* 592 */       boolean denied = this.hovering.isDenied();
/*     */       
/* 594 */       if (overlaid) {
/*     */         
/* 596 */         drawOverlayHoverTip(fontRenderer, mouseX, mouseY, denied);
/*     */       }
/*     */       else {
/*     */         
/* 600 */         drawHoverTip(fontRenderer, this.hoverTemplate, mouseX, mouseY, -1342177280, true, denied);
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
/*     */   protected void drawOverlayHoverTip(bip fontRenderer, int mouseX, int mouseY, boolean denied) {
/* 613 */     String overlayText = I18n.get("macro.hover.overlay", new Object[] { this.macros.getOverlayConfigName("§c") });
/* 614 */     int textWidth = fontRenderer.a(overlayText) + 10;
/* 615 */     int textLeft = Math.min(mouseX - 10, this.f - textWidth);
/*     */     
/* 617 */     a(textLeft, mouseY - 16, mouseX + textWidth - 10, mouseY, -1342177212);
/* 618 */     c(fontRenderer, overlayText, textLeft + 3, mouseY - 12, -15658497);
/*     */     
/* 620 */     MacroTemplate overlayTemplate = this.macros.getMacroTemplateWithOverlay(this.hovering.getId());
/*     */     
/* 622 */     drawHoverTip(fontRenderer, this.hoverTemplate, mouseX, mouseY - 18, -1342177280, true, denied);
/* 623 */     drawHoverTip(fontRenderer, overlayTemplate, mouseX, mouseY, -1342177212, false, denied);
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
/*     */ 
/*     */   
/*     */   protected void drawHoverTip(bip fontRenderer, MacroTemplate template, int mouseX, int mouseY, int backColour, boolean direction, boolean denied) {
/* 639 */     int textWidth = Math.min(200, getHoverWidth(fontRenderer, template) + 10);
/* 640 */     int textHeight = this.hovering.isDenied() ? 16 : 6;
/* 641 */     int textLeft = Math.max(this.h, Math.min(mouseX - 10, this.h + this.f - textWidth)) + 3;
/* 642 */     int yPos = Math.min(this.g - textHeight - 2, mouseY + 4);
/*     */     
/* 644 */     if (this.hovering.isBound()) {
/*     */       
/* 646 */       textHeight += (template.getPlaybackType() == MacroPlaybackType.ONESHOT) ? 10 : 30;
/* 647 */       textHeight += (this.hoverModifier.length() > 0) ? 12 : 0;
/* 648 */       if (direction) yPos -= textHeight;
/*     */       
/* 650 */       a(textLeft - 3, yPos - 4, mouseX + textWidth - 10, yPos + textHeight - 4, backColour);
/*     */       
/* 652 */       switch (template.getPlaybackType()) {
/*     */         
/*     */         case ONESHOT:
/* 655 */           this.renderer.drawStringWithEllipsis(template.getKeyDownMacroHoverText(), textLeft, yPos, textWidth - 4, -1711276033); yPos += 10;
/*     */           break;
/*     */         
/*     */         case KEYSTATE:
/* 659 */           this.renderer.drawStringWithEllipsis(template.getKeyDownMacroHoverText(), textLeft, yPos, textWidth - 4, -1711276033); yPos += 10;
/* 660 */           this.renderer.drawStringWithEllipsis(template.getKeyHeldMacroHoverText(), textLeft, yPos, textWidth - 4, -1711298048); yPos += 10;
/* 661 */           this.renderer.drawStringWithEllipsis(template.getKeyUpMacroHoverText(), textLeft, yPos, textWidth - 4, -1711306752); yPos += 10;
/*     */           break;
/*     */         
/*     */         case CONDITIONAL:
/* 665 */           this.renderer.drawStringWithEllipsis(template.getConditionHovertext(), textLeft, yPos, textWidth - 4, -1711276033); yPos += 10;
/* 666 */           this.renderer.drawStringWithEllipsis(template.getKeyDownMacroHoverText(), textLeft, yPos, textWidth - 4, -1721303194); yPos += 10;
/* 667 */           this.renderer.drawStringWithEllipsis(template.getKeyUpMacroHoverText(), textLeft, yPos, textWidth - 4, -1711315456); yPos += 10;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 674 */       if (this.hoverModifier.length() > 0)
/*     */       {
/* 676 */         yPos += 2;
/* 677 */         c(fontRenderer, this.hoverModifier, textLeft, yPos, -1727987968);
/* 678 */         yPos += 12;
/*     */       }
/*     */     
/* 681 */     } else if (denied) {
/*     */       
/* 683 */       a(textLeft - 3, yPos - 4, mouseX + textWidth - 10, yPos + textHeight - 4, backColour);
/*     */     } 
/*     */     
/* 686 */     if (denied)
/*     */     {
/* 688 */       c(fontRenderer, this.hovering.getDeniedText(), textLeft, yPos, -1711341568);
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
/*     */   protected int getHoverWidth(bip fontRenderer, MacroTemplate template) {
/* 701 */     int deniedWidth = this.hovering.isDenied() ? fontRenderer.a(this.hovering.getDeniedText()) : 0;
/*     */     
/* 703 */     if (template == null) return deniedWidth;
/*     */     
/* 705 */     if (template.getPlaybackType() == MacroPlaybackType.ONESHOT)
/*     */     {
/* 707 */       return Math.max(Math.max(fontRenderer
/* 708 */             .a(template.getKeyDownMacro()), fontRenderer
/* 709 */             .a(this.hoverModifier)), deniedWidth);
/*     */     }
/*     */ 
/*     */     
/* 713 */     if (template.getPlaybackType() == MacroPlaybackType.KEYSTATE)
/*     */     {
/* 715 */       return Math.max(Math.max(Math.max(Math.max(fontRenderer
/* 716 */                 .a(template.getKeyDownMacroHoverText()), fontRenderer
/* 717 */                 .a(template.getKeyHeldMacroHoverText())), fontRenderer
/* 718 */               .a(template.getKeyUpMacroHoverText())), fontRenderer
/* 719 */             .a(this.hoverModifier)), deniedWidth);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 725 */     return Math.max(Math.max(Math.max(Math.max(fontRenderer
/* 726 */               .a(template.getKeyDownMacroHoverText()), fontRenderer
/* 727 */               .a(template.getKeyUpMacroHoverText())), fontRenderer
/* 728 */             .a(template.getConditionHovertext())), fontRenderer
/* 729 */           .a(this.hoverModifier)), deniedWidth);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\layout\LayoutPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */