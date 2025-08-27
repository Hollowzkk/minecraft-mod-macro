/*     */ package net.eq2online.macros.gui.designable.editor;
/*     */ 
/*     */ import bib;
/*     */ import bip;
/*     */ import bir;
/*     */ import bja;
/*     */ import bje;
/*     */ import blk;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.GuiRenderer;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.controls.GuiColourButton;
/*     */ import net.eq2online.macros.gui.controls.GuiDropDownList;
/*     */ import net.eq2online.macros.gui.controls.GuiLabel;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiTextFieldWithHighlight;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.interfaces.IHighlighter;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import net.eq2online.util.Colour;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import rk;
/*     */ 
/*     */ public class GuiDialogBoxControlProperties
/*     */   extends GuiDialogBox
/*     */   implements GuiDropDownList.IDropDownContainer
/*     */ {
/*     */   protected final DesignableGuiControl control;
/*     */   
/*     */   static interface Metrics
/*     */   {
/*     */     public static final int LABEL_WIDTH = 85;
/*     */     public static final int CONTROL_WIDTH = 115;
/*     */     public static final int SIBLING_WIDTH = 30;
/*     */     public static final int SIBLING_SPACE = 15;
/*     */   }
/*     */   
/*     */   class GuiKeyBindButton
/*     */     extends GuiControl
/*     */   {
/*     */     private int keyCode;
/*     */     private boolean active;
/*     */     
/*     */     GuiKeyBindButton(int buttonId, int x, int y, int keyCode) {
/*  56 */       this(buttonId, x, y, keyCode, 80);
/*     */     }
/*     */ 
/*     */     
/*     */     GuiKeyBindButton(int buttonId, int x, int y, int keyCode, int width) {
/*  61 */       super(buttonId, x, y, width, 20, I18n.get("control.properties.bind"));
/*  62 */       this.keyCode = keyCode;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getKeyCode() {
/*  67 */       return this.keyCode;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void a(bip fontRendererIn, String text, int x, int y, int color) {
/*  73 */       super.a(fontRendererIn, getText(), x, y, color);
/*     */     }
/*     */ 
/*     */     
/*     */     private String getText() {
/*  78 */       if (this.active)
/*     */       {
/*  80 */         return this.j;
/*     */       }
/*     */       
/*  83 */       if (this.keyCode > 0 && this.keyCode < 256)
/*     */       {
/*  85 */         return Keyboard.getKeyName(this.keyCode);
/*     */       }
/*     */       
/*  88 */       return I18n.get("control.properties.none");
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mouseClicked(bib minecraft, int mouseX, int mouseY, int button) {
/*  93 */       if (button == 0 && b(minecraft, mouseX, mouseY)) {
/*     */         
/*  95 */         this.active = !this.active;
/*  96 */         return true;
/*     */       } 
/*     */       
/*  99 */       this.active = false;
/* 100 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean keyTyped(char keyChar, int keyCode) {
/* 105 */       if (!this.active)
/*     */       {
/* 107 */         return false;
/*     */       }
/*     */       
/* 110 */       if (keyCode == 1)
/*     */       {
/* 112 */         keyCode = 0;
/*     */       }
/*     */       
/* 115 */       this.keyCode = keyCode;
/* 116 */       this.active = false;
/* 117 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   class CopyToggle extends GuiRenderer {
/*     */     final String property;
/*     */     final int x;
/*     */     final int y;
/*     */     final int w;
/*     */     final int h;
/*     */     
/*     */     CopyToggle(String property, int x, int y, int width, int height) {
/* 129 */       super(bib.z());
/* 130 */       this.x = x;
/* 131 */       this.y = y;
/* 132 */       this.w = width;
/* 133 */       this.h = height;
/* 134 */       this.property = property;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean contains(int dx, int dy, int mouseX, int mouseY) {
/* 139 */       mouseX -= dx;
/* 140 */       mouseY -= dy;
/* 141 */       return (mouseX >= this.x && mouseX <= this.x + this.w && mouseY >= this.y && mouseY <= this.y + this.h);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void draw(int dialogX, int dialogY, int mouseX, int mouseY) {
/* 147 */       this.mc.N().a(ResourceLocations.EXT);
/* 148 */       GL.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
/*     */       
/* 150 */       int v = (int)(rk.d((this.h - 6)) / 2.0F);
/* 151 */       drawTexturedModalRect(dialogX + this.x, dialogY + this.y + v, 16, 96, 6, 6, 0.0078125F);
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
/* 163 */   protected final List<bje> textFields = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   protected final Map<String, bje> propertyFields = new HashMap<>();
/* 169 */   protected final Map<String, bje> numericpropertyFields = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 174 */   protected final Map<String, GuiCheckBox> checkBoxes = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 179 */   protected final Map<String, GuiKeyBindButton> bindButtons = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 184 */   protected final Map<String, GuiColourButton> colourButtons = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 189 */   protected final Map<String, GuiDropDownList.GuiDropDownListControl> dropDowns = new HashMap<>();
/*     */   
/* 191 */   protected final List<CopyToggle> copyToggles = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 196 */   private int controlTop = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 201 */   private int nextControlId = 100;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected bje txtName;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean clicked;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDialogBoxControlProperties(bib minecraft, GuiScreenEx parentScreen, DesignableGuiControl control) {
/* 216 */     super(minecraft, (blk)parentScreen, 210, 174, I18n.get("control.properties.title", new Object[] { control.getName() }));
/*     */     
/* 218 */     this.control = control;
/* 219 */     this.centreTitle = false;
/*     */     
/* 221 */     this.movable = true;
/* 222 */     this.initOnDrag = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void beginDragging(int mouseX, int mouseY, int button) {
/* 228 */     super.beginDragging(mouseX, mouseY, button);
/*     */     
/* 230 */     for (bje textField : this.textFields)
/*     */     {
/* 232 */       this.dragOffsets.add(new GuiDialogBox.ControlOffsetData(textField, this.dialogX, this.dialogY));
/*     */     }
/*     */     
/* 235 */     for (GuiDropDownList.GuiDropDownListControl dropDown : this.dropDowns.values())
/*     */     {
/* 237 */       this.dragOffsets.add(new GuiDialogBox.ControlOffsetData((bja)dropDown, this.dialogX, this.dialogY));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initDialog() {
/* 247 */     this.copyToggles.clear();
/*     */     
/* 249 */     super.initDialog();
/*     */     
/* 251 */     if (!this.dragging && this.initPosition) {
/*     */       
/* 253 */       this.dialogX = 30;
/* 254 */       this.dialogY = 42;
/*     */     } 
/*     */     
/* 257 */     this.btnOk.setXPosition(this.dialogX + this.dialogWidth - 62); this.btnOk.setYPosition(this.dialogY + this.dialogHeight - 22);
/* 258 */     this.btnCancel.setXPosition(this.dialogX + this.dialogWidth - 124); this.btnCancel.setYPosition(this.dialogY + this.dialogHeight - 22);
/*     */     
/* 260 */     this.textFields.clear();
/* 261 */     this.propertyFields.clear();
/* 262 */     this.numericpropertyFields.clear();
/* 263 */     this.checkBoxes.clear();
/* 264 */     this.bindButtons.clear();
/* 265 */     this.colourButtons.clear();
/* 266 */     this.dropDowns.clear();
/* 267 */     this.controlTop = 8;
/* 268 */     this.nextControlId = 100;
/*     */     
/* 270 */     this.txtName = addTextField(I18n.get("control.properties.name"), "name", false);
/* 271 */     this.txtName.b(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 280 */     super.e();
/*     */     
/* 282 */     for (bje textField : this.textFields)
/*     */     {
/* 284 */       textField.a();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawDialog(int mouseX, int mouseY, float f) {
/* 294 */     this.clicked = false;
/*     */     
/* 296 */     super.drawDialog(mouseX, mouseY, f);
/*     */     
/* 298 */     for (bje textField : this.textFields)
/*     */     {
/* 300 */       textField.g();
/*     */     }
/*     */     
/* 303 */     Set<String> copyMask = this.control.getCopyMask();
/*     */ 
/*     */     
/* 306 */     if (!copyMask.isEmpty())
/*     */     {
/* 308 */       for (CopyToggle toggle : this.copyToggles) {
/*     */         
/* 310 */         if (copyMask.contains(toggle.property))
/*     */         {
/* 312 */           toggle.draw(this.dialogX, this.dialogY, mouseX, mouseY);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postRender(int mouseX, int mouseY, float partialTicks) {
/* 324 */     for (GuiDropDownList.GuiDropDownListControl dropDown : this.dropDowns.values())
/*     */     {
/* 326 */       dropDown.a(this.j, mouseX, mouseY, partialTicks);
/*     */     }
/*     */     
/* 329 */     for (GuiColourButton colourButton : this.colourButtons.values())
/*     */     {
/* 331 */       colourButton.drawPicker(this.j, mouseX, mouseY, partialTicks);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogCancel() {
/* 338 */     boolean handled = false;
/*     */     
/* 340 */     for (GuiKeyBindButton bindButton : this.bindButtons.values())
/*     */     {
/* 342 */       handled |= bindButton.keyTyped(false, 1);
/*     */     }
/*     */     
/* 345 */     if (!handled)
/*     */     {
/* 347 */       super.dialogCancel();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/* 357 */     boolean handled = false;
/*     */     
/* 359 */     for (GuiColourButton colourButton : this.colourButtons.values())
/*     */     {
/* 361 */       handled |= colourButton.textBoxKeyTyped(keyChar, keyCode);
/*     */     }
/*     */     
/* 364 */     for (GuiKeyBindButton bindButton : this.bindButtons.values())
/*     */     {
/* 366 */       handled |= bindButton.keyTyped(keyChar, keyCode);
/*     */     }
/*     */     
/* 369 */     if (!handled) {
/*     */       
/* 371 */       for (bje textField : this.textFields) {
/*     */         
/* 373 */         if (!this.numericpropertyFields.containsValue(textField) || "-0123456789."
/* 374 */           .indexOf(keyChar) > -1 || keyCode == 203 || keyCode == 205 || keyCode == 199 || keyCode == 207 || keyCode == 211 || keyCode == 14)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 382 */           textField.a(keyChar, keyCode);
/*     */         }
/*     */       } 
/*     */       
/* 386 */       if (this.textFields.size() > 0 && keyChar == '\t') {
/*     */         
/* 388 */         int focusedId = 0;
/*     */         
/* 390 */         for (int i = 0; i < this.textFields.size(); i++) {
/*     */           
/* 392 */           if (((bje)this.textFields.get(i)).m()) {
/*     */             
/* 394 */             focusedId = i + 1;
/* 395 */             ((bje)this.textFields.get(i)).b(false);
/*     */           } 
/*     */         } 
/*     */         
/* 399 */         if (focusedId >= this.textFields.size())
/*     */         {
/* 401 */           focusedId = 0;
/*     */         }
/*     */         
/* 404 */         ((bje)this.textFields.get(focusedId)).b(true);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 409 */     super.dialogKeyTyped(keyChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMouseClicked(int mouseX, int mouseY, int button) throws IOException {
/* 419 */     if (InputHandler.isControlDown() && button == 0 && !this.clicked) {
/*     */       
/* 421 */       Set<String> toggles = new HashSet<>();
/* 422 */       this.clicked = true;
/*     */       
/* 424 */       for (CopyToggle copyToggle : this.copyToggles) {
/*     */         
/* 426 */         if (copyToggle.contains(this.dialogX, this.dialogY, mouseX, mouseY))
/*     */         {
/* 428 */           toggles.add(copyToggle.property);
/*     */         }
/*     */       } 
/*     */       
/* 432 */       for (String property : toggles)
/*     */       {
/* 434 */         this.control.toggleCopyProperty(property);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 440 */     for (bje textField : this.textFields)
/*     */     {
/* 442 */       textField.a(mouseX, mouseY, button);
/*     */     }
/*     */     
/* 445 */     for (GuiColourButton colourButton : this.colourButtons.values()) {
/*     */       
/* 447 */       if (colourButton.b(this.j, mouseX, mouseY)) {
/*     */         
/* 449 */         this.selectedButtonEx = (bja)colourButton;
/* 450 */         actionPerformed((GuiControl)colourButton);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 455 */     for (GuiDropDownList.GuiDropDownListControl dropDown : this.dropDowns.values()) {
/*     */       
/* 457 */       if (dropDown.b(this.j, mouseX, mouseY)) {
/*     */         
/* 459 */         this.selectedButtonEx = (bja)dropDown;
/* 460 */         actionPerformed((GuiControl)dropDown);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 465 */     for (GuiKeyBindButton bindButton : this.bindButtons.values())
/*     */     {
/* 467 */       bindButton.mouseClicked(this.j, mouseX, mouseY, button);
/*     */     }
/*     */     
/* 470 */     super.dialogMouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSubmit() {
/* 476 */     for (Map.Entry<String, bje> propertyField : this.propertyFields.entrySet())
/*     */     {
/* 478 */       this.control.setProperty(propertyField.getKey(), ((bje)propertyField.getValue()).b());
/*     */     }
/*     */     
/* 481 */     for (Map.Entry<String, bje> propertyField : this.numericpropertyFields.entrySet())
/*     */     {
/* 483 */       this.control.setProperty(propertyField.getKey(), tryParseInt(((bje)propertyField.getValue()).b(), 0));
/*     */     }
/*     */     
/* 486 */     for (Map.Entry<String, GuiCheckBox> checkBox : this.checkBoxes.entrySet())
/*     */     {
/* 488 */       this.control.setProperty(checkBox.getKey(), ((GuiCheckBox)checkBox.getValue()).checked);
/*     */     }
/*     */     
/* 491 */     for (Map.Entry<String, GuiKeyBindButton> bindButton : this.bindButtons.entrySet())
/*     */     {
/* 493 */       this.control.setProperty(bindButton.getKey(), ((GuiKeyBindButton)bindButton.getValue()).getKeyCode());
/*     */     }
/*     */     
/* 496 */     for (Map.Entry<String, GuiColourButton> colourButton : this.colourButtons.entrySet())
/*     */     {
/* 498 */       this.control.setProperty(colourButton.getKey(), Colour.getHexColour(((GuiColourButton)colourButton.getValue()).getColour()));
/*     */     }
/*     */     
/* 501 */     for (Map.Entry<String, GuiDropDownList.GuiDropDownListControl> dropDown : this.dropDowns.entrySet()) {
/*     */       
/* 503 */       if (((GuiDropDownList.GuiDropDownListControl)dropDown.getValue()).getSelectedItem() != null)
/*     */       {
/* 505 */         this.control.setProperty(dropDown.getKey(), ((GuiDropDownList.GuiDropDownListControl)dropDown.getValue()).getSelectedItem().getTag());
/*     */       }
/*     */     } 
/*     */     
/* 509 */     this.control.applyChanges(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onDialogClosed() {
/* 515 */     super.onDialogClosed();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateDialog() {
/* 521 */     return true;
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
/*     */   protected final bje addTextFieldWithCheckBox(String label, String textBoxPropName, String checkBoxLabel, String checkBoxPropertyName, boolean numeric) {
/* 534 */     int left = this.dialogX + 85;
/* 535 */     bje textField = new bje(0, this.q, left, getControlTop(), 70, 16);
/*     */     
/* 537 */     GuiCheckBox checkBox = new GuiCheckBox(this.j, this.nextControlId++, left + 15 + textField.p(), getControlTop() - 2, checkBoxLabel, this.control.getProperty(checkBoxPropertyName, false));
/* 538 */     checkBox.textColour = -22016;
/* 539 */     this.checkBoxes.put(checkBoxPropertyName, checkBox);
/* 540 */     addControl((GuiControl)checkBox);
/*     */     
/* 542 */     return addTextField(label, textBoxPropName, textField, numeric);
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
/*     */   protected final bje addTextFieldWithButton(String label, String textBoxPropName, String buttonLabel, String buttonTag, boolean numeric) {
/* 554 */     int left = this.dialogX + 85;
/* 555 */     int width = 85;
/*     */     
/* 557 */     bje textField = new bje(0, this.q, left, getControlTop(), width, 16);
/* 558 */     GuiControl button = new GuiControl(this.nextControlId++, left + 10 + textField.p(), getControlTop() - 2, 30, 20, buttonLabel);
/*     */     
/* 560 */     button.tag = buttonTag;
/* 561 */     addControl(button);
/*     */     
/* 563 */     return addTextField(label, textBoxPropName, textField, numeric);
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
/*     */   protected final bje addTextField(String label, String propertyName, boolean numeric) {
/* 575 */     return addTextField(label, propertyName, numeric, 115);
/*     */   }
/*     */ 
/*     */   
/*     */   protected bje addTextField(String label, String propertyName, boolean numeric, int width) {
/* 580 */     int left = this.dialogX + 85;
/* 581 */     bje textField = new bje(0, this.q, left, getControlTop(), width, 16);
/* 582 */     return addTextField(label, propertyName, textField, numeric);
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
/*     */   protected final bje addTextField(String label, String propertyName, boolean numeric, IHighlighter highlighter) {
/* 594 */     return addTextField(label, propertyName, numeric, highlighter, 115);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final bje addTextField(String label, String propertyName, boolean numeric, IHighlighter highlighter, int width) {
/* 599 */     int left = this.dialogX + 85;
/* 600 */     GuiTextFieldWithHighlight textField = new GuiTextFieldWithHighlight(0, this.q, left, getControlTop(), width, 16, "");
/* 601 */     textField.setHighlighter(highlighter);
/* 602 */     return addTextField(label, propertyName, (bje)textField, numeric);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final bje addTextField(String label, String propertyName, bje textField, boolean numeric) {
/* 613 */     textField.f(255);
/* 614 */     addControl(label, (bir)textField, false);
/* 615 */     addCopyToggle(propertyName, 2, this.controlTop, textField.a - this.dialogX - 8, 16);
/*     */     
/* 617 */     if (numeric) {
/*     */       
/* 619 */       textField.a(String.valueOf(this.control.getProperty(propertyName, 0)));
/* 620 */       this.numericpropertyFields.put(propertyName, textField);
/*     */     }
/*     */     else {
/*     */       
/* 624 */       textField.a(this.control.getProperty(propertyName, ""));
/* 625 */       this.propertyFields.put(propertyName, textField);
/*     */     } 
/*     */     
/* 628 */     this.controlTop += 20;
/* 629 */     return textField;
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
/*     */   protected final GuiCheckBox addCheckBox(String label, String propertyName) {
/* 642 */     GuiCheckBox checkBox = new GuiCheckBox(this.j, this.nextControlId++, this.dialogX + 10, getControlTop(), label, this.control.getProperty(propertyName, false));
/* 643 */     checkBox.textColour = -22016;
/* 644 */     this.checkBoxes.put(propertyName, checkBox);
/* 645 */     addControl((GuiControl)checkBox);
/* 646 */     addCopyToggle(propertyName, 2, this.controlTop + 2, this.dialogWidth - 48, 17);
/* 647 */     this.controlTop += 20;
/* 648 */     return checkBox;
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
/*     */   protected final GuiColourButton addColourButton(String label, String propertyName, int defaultColour) {
/* 660 */     GuiColourButton colourButton = new GuiColourButton(this.j, this.nextControlId++, this.dialogX + 160, getControlTop(), 40, 16, "", Colour.parseColour(this.control.getProperty(propertyName, ""), defaultColour));
/* 661 */     addControl(label, (bir)colourButton, true);
/* 662 */     addCopyToggle(propertyName, 2, this.controlTop, 154, 17);
/* 663 */     this.colourButtons.put(propertyName, colourButton);
/* 664 */     this.controlTop += 20;
/* 665 */     return colourButton;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final GuiKeyBindButton addKeybindButton(String label, String propertyName, int defaultKey) {
/* 671 */     GuiKeyBindButton bindButton = new GuiKeyBindButton(this.nextControlId++, this.dialogX + 120, getControlTop(), this.control.getProperty(propertyName, defaultKey));
/* 672 */     addControl(label, (bir)bindButton, true);
/* 673 */     addCopyToggle(propertyName, 2, this.controlTop, 114, 19);
/* 674 */     this.bindButtons.put(propertyName, bindButton);
/* 675 */     this.controlTop += 20;
/* 676 */     return bindButton;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final GuiKeyBindButton addKeybindButtonPair(String label, String propertyName1, int defaultKey1, String propertyName2, int defaultKey2) {
/* 682 */     GuiKeyBindButton bindButton1 = new GuiKeyBindButton(this.nextControlId++, this.dialogX + 80, getControlTop(), this.control.getProperty(propertyName1, defaultKey1), 60);
/*     */     
/* 684 */     GuiKeyBindButton bindButton2 = new GuiKeyBindButton(this.nextControlId++, this.dialogX + 140, getControlTop(), this.control.getProperty(propertyName2, defaultKey2), 60);
/* 685 */     addCopyToggle(propertyName1, 2, this.controlTop, 77, 19);
/* 686 */     addCopyToggle(propertyName2, 2, this.controlTop, 77, 19);
/* 687 */     addControl(label, (bir)bindButton1, true);
/* 688 */     addControl("", (bir)bindButton2, true);
/* 689 */     this.bindButtons.put(propertyName1, bindButton1);
/* 690 */     this.bindButtons.put(propertyName2, bindButton2);
/* 691 */     this.controlTop += 20;
/* 692 */     return bindButton1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final GuiDropDownList.GuiDropDownListControl addDropDown(String label, String propertyName, String emptyText) {
/* 703 */     int left = this.dialogX + 85;
/* 704 */     GuiDropDownList.GuiDropDownListControl dropDown = new GuiDropDownList.GuiDropDownListControl(this, this.j, this.nextControlId++, left, getControlTop(), 115, 16, 12, emptyText);
/*     */     
/* 706 */     addControl(label, (bir)dropDown, false);
/* 707 */     addCopyToggle(propertyName, 2, this.controlTop, 77, 19);
/* 708 */     this.dropDowns.put(propertyName, dropDown);
/* 709 */     this.controlTop += 20;
/* 710 */     return dropDown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void addControl(String label, bir control, boolean addToControlList) {
/* 720 */     if (control instanceof GuiControl) {
/*     */       
/* 722 */       GuiControl button = (GuiControl)control;
/* 723 */       if (label.length() > 0)
/*     */       {
/* 725 */         addControl((GuiControl)new GuiLabel(-999, this.dialogX + 10, getControlTop() + (button.getHeight() - 8) / 2, label, -22016));
/*     */       }
/* 727 */       if (addToControlList)
/*     */       {
/* 729 */         addControl(button);
/*     */       }
/*     */     }
/* 732 */     else if (control instanceof bje) {
/*     */       
/* 734 */       bje textField = (bje)control;
/* 735 */       if (label.length() > 0)
/*     */       {
/* 737 */         addControl((GuiControl)new GuiLabel(-999, this.dialogX + 10, getControlTop() + 4, label, -22016));
/*     */       }
/* 739 */       this.textFields.add(textField);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void addCopyToggle(String property, int x, int y, int width, int height) {
/* 745 */     if (!"name".equals(property))
/*     */     {
/* 747 */       this.copyToggles.add(new CopyToggle(property, x, y, width, height));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getControlTop() {
/* 756 */     return this.dialogY + this.controlTop;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getContainerWidth() {
/* 762 */     return this.l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getContainerHeight() {
/* 768 */     return this.m;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int tryParseInt(String value, int defaultValue) {
/*     */     try {
/* 775 */       return Integer.parseInt(value.trim().replaceAll(",", ""));
/*     */     }
/* 777 */     catch (NumberFormatException ex) {
/*     */       
/* 779 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxControlProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */