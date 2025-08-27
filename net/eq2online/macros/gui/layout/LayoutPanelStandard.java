/*     */ package net.eq2online.macros.gui.layout;
/*     */ 
/*     */ import bib;
/*     */ import bip;
/*     */ import bir;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.MacroTriggerType;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.interfaces.IEditablePanel;
/*     */ import net.eq2online.macros.interfaces.ILayoutWidget;
/*     */ import net.eq2online.macros.interfaces.ISettingsObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
/*     */ import net.eq2online.macros.rendering.FontRendererLegacy;
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
/*     */ public class LayoutPanelStandard
/*     */   extends LayoutPanel<LayoutWidget>
/*     */   implements ISettingsObserver
/*     */ {
/*     */   protected GuiControl btnCopy;
/*     */   protected GuiControl btnMove;
/*     */   protected GuiControl btnDelete;
/*     */   protected GuiControl btnEdit;
/*  36 */   protected Pattern layoutPattern = Pattern.compile("\\{([0-9]+),([0-9]+),([0-9]+)\\}");
/*     */ 
/*     */ 
/*     */   
/*     */   protected int lastMouseX;
/*     */ 
/*     */   
/*     */   protected int lastMouseY;
/*     */ 
/*     */   
/*  46 */   protected String layoutSettingName = "keyboard.layout";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   protected String defaultLayout = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   protected String layoutSettingDescription = "Serialised version of the keyboard layout, each param is {MappingID,X,Y}";
/*     */   
/*  58 */   protected float scaleFactor = 1.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int drawX;
/*     */ 
/*     */   
/*     */   protected int drawY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected LayoutPanelStandard(Macros macros, bib minecraft, int controlId, String layoutPanelSettingName, MacroTriggerType type) {
/*  70 */     super(macros, minecraft, controlId, type);
/*     */     
/*  72 */     this.layoutSettingName = layoutPanelSettingName;
/*  73 */     this.layoutSettingDescription = "Serialised version of the keyboard layout, each param is {MappingID,X,Y}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() {
/*  82 */     this.btnCopy = new GuiControl(1, this.h + 4, this.i + this.g - 24, 60, 20, I18n.get("gui.copy"), 0);
/*  83 */     this.btnMove = new GuiControl(2, this.h + 68, this.i + this.g - 24, 60, 20, I18n.get("gui.move"), 2);
/*  84 */     this.btnDelete = new GuiControl(3, this.h + 132, this.i + this.g - 24, 60, 20, I18n.get("gui.delete"), 1);
/*  85 */     this.btnEdit = new GuiControl(4, this.h + 196, this.i + this.g - 24, 60, 20, I18n.get("gui.edit"), 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSizeAndPosition(int left, int top, int controlWidth, int controlHeight) {
/*  95 */     if (this.settings.autoScaleKeyboard) {
/*     */       
/*  97 */       this.scaleFactor = Math.min(controlWidth / 427.0F, controlHeight / 202.0F);
/*     */       
/*  99 */       this.drawX = left;
/* 100 */       this.drawY = top;
/*     */       
/* 102 */       controlWidth = 427;
/* 103 */       controlHeight = 202;
/* 104 */       left = 0;
/* 105 */       top = 0;
/*     */     } 
/*     */     
/* 108 */     super.setSizeAndPosition(left, top, controlWidth, controlHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ILayoutWidget getWidgetAt(int mouseX, int mouseY) {
/* 118 */     if (this.settings.autoScaleKeyboard) {
/*     */       
/* 120 */       mouseX = adjustMouse(this.drawX, mouseX);
/* 121 */       mouseY = adjustMouse(this.drawY, mouseY);
/*     */     } 
/*     */     
/* 124 */     return getWidgetAtAdjustedPosition(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ILayoutWidget getWidgetAtAdjustedPosition(int mouseX, int mouseY) {
/* 134 */     if (mouseX < this.h || mouseY < this.i || mouseX > this.h + this.f || mouseY > this.i + this.f)
/*     */     {
/* 136 */       return null;
/*     */     }
/*     */     
/* 139 */     ILayoutWidget topMost = null;
/*     */     
/* 141 */     for (int i = this.macroType.getMinId(); i <= this.macroType.getAbsoluteMaxId(); i++) {
/*     */       
/* 143 */       if (this.widgets[i] != null && this.widgets[i].mouseOver(null, mouseX - this.h, mouseY - this.i, false))
/*     */       {
/* 145 */         if (topMost == null || this.widgets[i].getZIndex() > topMost.getZIndex())
/*     */         {
/* 147 */           topMost = this.widgets[i];
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 152 */     return topMost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ILayoutWidget createWidget(int id) {
/* 162 */     return new LayoutButton(this.macros, this.mc, (bip)FontRendererLegacy.getInstance(), id, this.macroType.getName(this.macros, id), this.widgetWidth, this.widgetHeight, this.centreAlign);
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
/*     */   public void loadPanelLayout(String panelLayout) {
/* 174 */     Matcher layoutPatternMatcher = this.layoutPattern.matcher(panelLayout);
/*     */     
/* 176 */     this.widgets = new ILayoutWidget[10000];
/*     */     
/* 178 */     while (layoutPatternMatcher.find()) {
/*     */       
/* 180 */       int id = Integer.parseInt(layoutPatternMatcher.group(1));
/* 181 */       int xCoord = Integer.parseInt(layoutPatternMatcher.group(2));
/* 182 */       int yCoord = Integer.parseInt(layoutPatternMatcher.group(3));
/* 183 */       ILayoutWidget<LayoutPanelStandard> key = getWidget(id, true);
/*     */       
/* 185 */       if (key != null)
/*     */       {
/* 187 */         key.setPosition((bir)this, xCoord, yCoord);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String savePanelLayout() {
/* 197 */     String panelLayout = "";
/*     */     
/* 199 */     for (int i = this.macroType.getMinId(); i <= this.macroType.getAbsoluteMaxId(); i++) {
/*     */       
/* 201 */       if (this.widgets[i] != null)
/*     */       {
/* 203 */         panelLayout = panelLayout + this.widgets[i].toString();
/*     */       }
/*     */     } 
/*     */     
/* 207 */     return panelLayout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean keyPressed(char keyChar, int keyCode) {
/* 217 */     if (super.keyPressed(keyChar, keyCode))
/*     */     {
/* 219 */       return true;
/*     */     }
/*     */     
/* 222 */     if (this.mode == IEditablePanel.EditMode.EDIT_ALL && this.macroType.supportsId(keyCode)) {
/*     */       
/* 224 */       if (this.widgets[keyCode] == null) {
/*     */         
/* 226 */         this.widgets[keyCode] = new LayoutButton(this.macros, this.mc, (bip)FontRendererLegacy.getInstance(), keyCode, this.macroType
/* 227 */             .getName(this.macros, keyCode), this.widgetWidth, this.widgetHeight, this.centreAlign);
/* 228 */         this.widgets[keyCode].setPositionSnapped((bir)this, this.lastMouseX, this.lastMouseY - 7);
/*     */       }
/* 230 */       else if (keyCode == 211 && this.selected != null) {
/*     */         
/* 232 */         this.widgets[this.selected.getId()] = null;
/* 233 */         this.selected = null;
/*     */       }
/*     */       else {
/*     */         
/* 237 */         this.selected = (LayoutWidget)this.widgets[keyCode];
/*     */       } 
/*     */       
/* 240 */       return true;
/*     */     } 
/*     */     
/* 243 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int adjustMouse(int drawOffset, int mousePosition) {
/* 248 */     return (int)((mousePosition - drawOffset) / this.scaleFactor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean b(bib minecraft, int mouseX, int mouseY) {
/* 258 */     if (this.settings.autoScaleKeyboard) {
/*     */       
/* 260 */       mouseX = adjustMouse(this.drawX, mouseX);
/* 261 */       mouseY = adjustMouse(this.drawY, mouseY);
/*     */     } 
/*     */     
/* 264 */     if (this.settings.drawLargeEditorButtons) {
/*     */       
/* 266 */       if (this.btnCopy != null && this.btnCopy.b(minecraft, mouseX, mouseY)) {
/*     */         
/* 268 */         this.panelManager.setMode(IEditablePanel.EditMode.COPY);
/* 269 */         return false;
/*     */       } 
/*     */       
/* 272 */       if (this.btnMove != null && this.btnMove.b(minecraft, mouseX, mouseY)) {
/*     */         
/* 274 */         this.panelManager.setMode(IEditablePanel.EditMode.MOVE);
/* 275 */         return false;
/*     */       } 
/*     */       
/* 278 */       if (this.btnDelete != null && this.btnDelete.b(minecraft, mouseX, mouseY)) {
/*     */         
/* 280 */         this.panelManager.setMode(IEditablePanel.EditMode.DELETE);
/* 281 */         return false;
/*     */       } 
/*     */       
/* 284 */       if (this.btnEdit != null && this.btnEdit.b(minecraft, mouseX, mouseY)) {
/*     */         
/* 286 */         this.panelManager.setMode(IEditablePanel.EditMode.EDIT_ALL);
/* 287 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 291 */     return super.b(minecraft, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseRelease(int mouseX, int mouseY) {
/* 300 */     if (this.settings.autoScaleKeyboard) {
/*     */       
/* 302 */       mouseX = adjustMouse(this.drawX, mouseX);
/* 303 */       mouseY = adjustMouse(this.drawY, mouseY);
/*     */     } 
/*     */     
/* 306 */     super.handleMouseRelease(mouseX, mouseY);
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
/*     */   protected void drawControl(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/* 319 */     GL.glPushMatrix();
/*     */     
/* 321 */     if (this.settings.autoScaleKeyboard) {
/*     */       
/* 323 */       GL.glTranslatef(this.drawX, this.drawY, 0.0F);
/* 324 */       GL.glScalef(this.scaleFactor, this.scaleFactor, 1.0F);
/*     */       
/* 326 */       mouseX = adjustMouse(this.drawX, mouseX);
/* 327 */       mouseY = adjustMouse(this.drawY, mouseY);
/*     */     } 
/*     */     
/* 330 */     drawSpecial(minecraft, mouseX, mouseY);
/*     */     
/* 332 */     if (this.mode == IEditablePanel.EditMode.EDIT_ALL || this.mode == IEditablePanel.EditMode.RESERVE) {
/*     */       
/* 334 */       this.hovering = null;
/* 335 */       this.hoverWidgetTime = this.layoutPanelTick;
/*     */     }
/*     */     else {
/*     */       
/* 339 */       calcHover(mouseX, mouseY);
/*     */     } 
/*     */ 
/*     */     
/* 343 */     drawLargeButtons(minecraft, mouseX, mouseY, partialTicks);
/*     */ 
/*     */     
/* 346 */     drawWidgets(mouseX, mouseY);
/*     */ 
/*     */     
/* 349 */     drawHover(mouseX, mouseY, minecraft.k);
/*     */     
/* 351 */     a(minecraft, mouseX, mouseY);
/*     */ 
/*     */     
/* 354 */     this.lastMouseX = mouseX - this.h;
/* 355 */     this.lastMouseY = mouseY - this.i;
/*     */     
/* 357 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postRender(int mouseX, int mouseY) {
/* 366 */     GL.glPushMatrix();
/*     */     
/* 368 */     if (this.settings.autoScaleKeyboard) {
/*     */       
/* 370 */       GL.glTranslatef(this.drawX, this.drawY, 0.0F);
/* 371 */       GL.glScalef(this.scaleFactor, this.scaleFactor, 1.0F);
/*     */       
/* 373 */       mouseX = adjustMouse(this.drawX, mouseX);
/* 374 */       mouseY = adjustMouse(this.drawY, mouseY);
/*     */     } 
/*     */     
/* 377 */     super.postRender(mouseX, mouseY);
/*     */     
/* 379 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawSpecial(bib minecraft, int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawLargeButtons(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/* 394 */     if (this.settings.drawLargeEditorButtons) {
/*     */       
/* 396 */       int widgetY = this.i + this.g - 24;
/* 397 */       this.btnCopy.setYPosition(widgetY);
/* 398 */       this.btnMove.setYPosition(widgetY);
/* 399 */       this.btnDelete.setYPosition(widgetY);
/* 400 */       this.btnEdit.setYPosition(widgetY);
/* 401 */       this.btnCopy.setXPosition(this.h + 4);
/* 402 */       this.btnMove.setXPosition(this.h + 68);
/* 403 */       this.btnDelete.setXPosition(this.h + 132);
/* 404 */       this.btnEdit.setXPosition(this.h + 196);
/*     */ 
/*     */       
/* 407 */       GuiControl btn = getActiveButton();
/* 408 */       if (btn != null)
/*     */       {
/* 410 */         a(btn.getXPosition() - 1, btn.getYPosition() - 1, btn.getXPosition() + 61, btn.getYPosition() + 21, this.mode.colour);
/*     */       }
/*     */       
/* 413 */       if (this.mode != IEditablePanel.EditMode.RESERVE) {
/*     */ 
/*     */         
/* 416 */         this.btnCopy.a(minecraft, mouseX, mouseY, partialTicks);
/* 417 */         this.btnMove.a(minecraft, mouseX, mouseY, partialTicks);
/* 418 */         this.btnDelete.a(minecraft, mouseX, mouseY, partialTicks);
/* 419 */         if (this.panelManager.isKeyboardEditable())
/*     */         {
/* 421 */           this.btnEdit.a(minecraft, mouseX, mouseY, partialTicks);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private GuiControl getActiveButton() {
/* 429 */     switch (this.mode) {
/*     */       case COPY:
/* 431 */         return this.btnCopy;
/* 432 */       case MOVE: return this.btnMove;
/* 433 */       case DELETE: return this.btnDelete;
/* 434 */       case EDIT_ALL: return this.btnEdit;
/* 435 */     }  return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClearSettings() {
/* 445 */     loadPanelLayout(this.defaultLayout);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoadSettings(ISettingsStore settings) {
/* 456 */     LayoutButton.notifySettingsLoaded(settings);
/*     */     
/* 458 */     if (this.layoutSettingName.length() > 0) {
/*     */       
/* 460 */       String keyboardLayout = settings.getSetting(this.layoutSettingName, "");
/*     */       
/* 462 */       if (keyboardLayout.length() > 0)
/*     */       {
/* 464 */         loadPanelLayout(keyboardLayout);
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
/*     */   public void onSaveSettings(ISettingsStore settings) {
/* 478 */     if (this.layoutSettingName.length() > 0) {
/*     */       
/* 480 */       settings.setSetting(this.layoutSettingName, savePanelLayout());
/* 481 */       settings.setSettingComment(this.layoutSettingName, this.layoutSettingDescription);
/*     */     } 
/*     */     
/* 484 */     LayoutButton.saveSettings(settings);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\layout\LayoutPanelStandard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */