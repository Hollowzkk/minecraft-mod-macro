/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import bib;
/*     */ import bir;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiRendererMacros;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxControlProperties;
/*     */ import net.eq2online.macros.gui.layout.LayoutButton;
/*     */ import net.eq2online.macros.gui.layout.LayoutWidget;
/*     */ import net.eq2online.macros.interfaces.IEditablePanel;
/*     */ import net.eq2online.macros.interfaces.ILayoutWidget;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.xml.Xml;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ public abstract class DesignableGuiControl
/*     */   extends GuiRendererMacros
/*     */   implements ILayoutWidget<DesignableGuiLayout>
/*     */ {
/*     */   public final int id;
/*     */   private String name;
/*     */   public int xPosition;
/*     */   public int yPosition;
/*     */   
/*     */   public static interface Listener
/*     */   {
/*     */     void onDispatch(DesignableGuiControl param1DesignableGuiControl);
/*     */   }
/*     */   
/*     */   public enum Handle
/*     */   {
/*  46 */     NORTH
/*     */     {
/*     */       
/*     */       public Rectangle rect(Rectangle boundingBox)
/*     */       {
/*  51 */         int xMid = boundingBox.width / 2;
/*  52 */         return new Rectangle(boundingBox.x + xMid - 2, boundingBox.y - 4 - 1, 4, 4);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public Rectangle draggedRect(Rectangle boundingBox, int mouseX, int mouseY) {
/*  58 */         Rectangle draggedRect = new Rectangle(boundingBox);
/*  59 */         double minY = draggedRect.getMinY();
/*  60 */         if (mouseY < minY) {
/*     */           
/*  62 */           double diff = (mouseY - 2) - minY;
/*  63 */           draggedRect.y = (int)(draggedRect.y + diff);
/*  64 */           draggedRect.height = (int)(draggedRect.height + diff);
/*     */         } 
/*  66 */         return draggedRect;
/*     */       }
/*     */     },
/*  69 */     SOUTH
/*     */     {
/*     */       
/*     */       public Rectangle rect(Rectangle boundingBox)
/*     */       {
/*  74 */         int xMid = boundingBox.width / 2;
/*  75 */         return new Rectangle(boundingBox.x + xMid - 2, boundingBox.y + boundingBox.height + 1, 4, 4);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public Rectangle draggedRect(Rectangle boundingBox, int mouseX, int mouseY) {
/*  81 */         Rectangle draggedRect = new Rectangle(boundingBox);
/*  82 */         double maxY = draggedRect.getMaxY();
/*  83 */         if (mouseY > maxY)
/*     */         {
/*  85 */           draggedRect.height = (int)(draggedRect.height + (mouseY - 2) - maxY);
/*     */         }
/*  87 */         return draggedRect;
/*     */       }
/*     */     },
/*  90 */     EAST
/*     */     {
/*     */       
/*     */       public Rectangle rect(Rectangle boundingBox)
/*     */       {
/*  95 */         int yMid = boundingBox.height / 2;
/*  96 */         return new Rectangle(boundingBox.x + boundingBox.width + 1, boundingBox.y + yMid - 2, 4, 4);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public Rectangle draggedRect(Rectangle boundingBox, int mouseX, int mouseY) {
/* 102 */         Rectangle draggedRect = new Rectangle(boundingBox);
/* 103 */         double maxX = draggedRect.getMaxX();
/* 104 */         if (mouseX > maxX)
/*     */         {
/* 106 */           draggedRect.width = (int)(draggedRect.width + (mouseX - 2) - maxX);
/*     */         }
/* 108 */         return draggedRect;
/*     */       }
/*     */     },
/* 111 */     WEST
/*     */     {
/*     */       
/*     */       public Rectangle rect(Rectangle boundingBox)
/*     */       {
/* 116 */         int yMid = boundingBox.height / 2;
/* 117 */         return new Rectangle(boundingBox.x - 4 - 1, boundingBox.y + yMid - 2, 4, 4);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public Rectangle draggedRect(Rectangle boundingBox, int mouseX, int mouseY) {
/* 123 */         Rectangle draggedRect = new Rectangle(boundingBox);
/* 124 */         double minX = draggedRect.getMinX();
/* 125 */         if (mouseX < minX) {
/*     */           
/* 127 */           double diff = (mouseX - 2) - minX;
/* 128 */           draggedRect.x = (int)(draggedRect.x + diff);
/* 129 */           draggedRect.width = (int)(draggedRect.width + diff);
/*     */         } 
/* 131 */         return draggedRect;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */     
/*     */     protected static final int SIZE = 2;
/*     */ 
/*     */     
/*     */     private static final int WIDTH = 4;
/*     */ 
/*     */     
/*     */     public boolean contains(Rectangle boundingBox, int mouseX, int mouseY) {
/* 144 */       return rect(boundingBox).contains(mouseX, mouseY);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract Rectangle rect(Rectangle param1Rectangle);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract Rectangle draggedRect(Rectangle param1Rectangle, int param1Int1, int param1Int2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   public int rowSpan = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   public int colSpan = 1;
/*     */ 
/*     */   
/*     */   public int zIndex;
/*     */ 
/*     */   
/*     */   public Handle draggingHandle;
/*     */ 
/*     */   
/* 185 */   protected Rectangle lastDrawnBoundingBox = new Rectangle();
/*     */   
/* 187 */   protected Point lastDrawnLocation = new Point();
/*     */ 
/*     */   
/*     */   private boolean mouseOverCopy;
/*     */   
/*     */   private boolean mouseOverPaste;
/*     */   
/* 194 */   protected int foreColour = -16711936;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 199 */   protected int backColour = -1342177280;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 204 */   protected int padding = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 209 */   private Map<String, String> properties = new TreeMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 214 */   private Set<String> copyMask = new HashSet<>();
/*     */ 
/*     */ 
/*     */   
/*     */   int dragOffsetX;
/*     */ 
/*     */ 
/*     */   
/*     */   int dragOffsetY;
/*     */ 
/*     */   
/*     */   private final String controlType;
/*     */ 
/*     */ 
/*     */   
/*     */   protected DesignableGuiControl(Macros macros, bib mc, int id) {
/* 230 */     super(macros, mc);
/*     */     
/* 232 */     this.id = id;
/* 233 */     this.zIndex = id;
/* 234 */     this.controlType = DesignableGuiControls.getControlType(this);
/*     */     
/* 236 */     doInit();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemoved() {
/* 241 */     if (isBindable())
/*     */     {
/* 243 */       this.macros.deleteMacroTemplateFromAllConfigurations(this.id);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doInit() {
/* 249 */     initProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initProperties() {
/* 258 */     setProperty("visible", true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onlyTickWhenVisible() {
/* 263 */     return true;
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
/*     */   public final String getType() {
/* 278 */     return this.controlType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 284 */     return getType();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultControlName() {
/* 289 */     return getType().toUpperCase() + " " + this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 294 */     return getProperty("visible", true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean dispatchOnClick() {
/* 299 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean executeOnClick() {
/* 304 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getName() {
/* 309 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setName(String name) {
/* 314 */     if (name.length() < 1)
/*     */     {
/* 316 */       name = getDefaultControlName();
/*     */     }
/*     */     
/* 319 */     this.name = name;
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
/*     */   public final void draw(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY, IEditablePanel.EditMode mode, boolean selected, boolean denied) {
/* 353 */     draw(parent, boundingBox, mouseX, mouseY, mode, selected, denied, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void draw(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY, IEditablePanel.EditMode mode, boolean selected, boolean denied, boolean source, boolean dest) {
/* 359 */     this.lastDrawnBoundingBox = boundingBox;
/* 360 */     this.lastDrawnLocation = new Point(boundingBox.x + boundingBox.width / 2, boundingBox.y);
/*     */     
/* 362 */     LayoutButton.Colours colours = getColours(boundingBox, mouseX, mouseY, mode, selected, denied);
/*     */     
/* 364 */     if (this.draggingHandle != null && selected) {
/*     */       
/* 366 */       Rectangle draggedRect = this.draggingHandle.draggedRect(boundingBox, mouseX, mouseY);
/* 367 */       drawRectOutline(draggedRect, -1439463373, 1.0F);
/* 368 */       drawRectOutline(this.draggingHandle.rect(draggedRect), -1, 1.0F);
/*     */     } 
/*     */     
/* 371 */     drawRect(boundingBox, colours.border);
/* 372 */     drawRect(boundingBox, 1711276032, 1, 1, 0, 0);
/* 373 */     drawRect(boundingBox, mouseOver(boundingBox, mouseX, mouseY, false) ? -13421773 : colours.background, 1);
/*     */     
/* 375 */     drawWidget(parent, boundingBox, mouseX, mouseY, colours.foreground);
/*     */     
/* 377 */     GL.glClear(256);
/*     */     
/* 379 */     Rectangle copy = getCopyRect(boundingBox);
/* 380 */     this.mouseOverCopy = copy.contains(mouseX, mouseY);
/* 381 */     this.mc.N().a(ResourceLocations.EXT);
/* 382 */     GL.glColor4f(0.0F, (this.mouseOverCopy || source) ? 1.0F : 0.4F, 0.0F, 1.0F);
/* 383 */     setTexMapSize(128);
/* 384 */     drawTexturedModalRect(copy.x, copy.y, copy.x + copy.width, copy.y + copy.height, source ? 16 : 0, 96, source ? 22 : 6, 102);
/*     */     
/* 386 */     if (dest && !source) {
/*     */       
/* 388 */       Rectangle paste = getPasteRect(boundingBox);
/* 389 */       this.mouseOverPaste = paste.contains(mouseX, mouseY);
/* 390 */       float hover = this.mouseOverPaste ? 1.0F : 0.4F;
/* 391 */       GL.glColor4f(hover, hover, 0.0F, 1.0F);
/* 392 */       drawTexturedModalRect(paste.x, paste.y, paste.x + paste.width, paste.y + paste.height, 8, 96, 14, 102);
/*     */     }
/*     */     else {
/*     */       
/* 396 */       this.mouseOverPaste = false;
/*     */     } 
/*     */     
/* 399 */     if (mode != IEditablePanel.EditMode.RESERVE)
/*     */     {
/* 401 */       drawControlDecorations(boundingBox);
/*     */     }
/*     */     
/* 404 */     if (selected) {
/*     */       
/* 406 */       drawDragHandles(boundingBox, mouseX, mouseY, mode, selected);
/*     */     }
/*     */     else {
/*     */       
/* 410 */       this.draggingHandle = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private LayoutButton.Colours getColours(Rectangle boundingBox, int mouseX, int mouseY, IEditablePanel.EditMode mode, boolean selected, boolean denied) {
/* 416 */     boolean special = this.macros.isReservedKey(this.id);
/* 417 */     boolean global = this.macros.isMacroGlobal(this.id, false);
/*     */     
/* 419 */     LayoutButton.Colours colours = new LayoutButton.Colours();
/*     */ 
/*     */     
/* 422 */     colours.background = -16777216;
/* 423 */     colours.foreground = isBound() ? LayoutWidget.COLOUR_BOUND : LayoutWidget.COLOUR_UNBOUND;
/* 424 */     if (special) colours.foreground = isBound() ? LayoutWidget.COLOUR_BOUNDSPECIAL : LayoutWidget.COLOUR_SPECIAL; 
/* 425 */     if (global) colours.foreground = LayoutWidget.COLOUR_BOUNDGLOBAL; 
/* 426 */     if (selected) colours.foreground = LayoutWidget.COLOUR_SELECTED; 
/* 427 */     if (denied) colours.foreground = LayoutWidget.COLOUR_DENIED;
/*     */     
/* 429 */     if (mouseOver(boundingBox, mouseX, mouseY, false)) {
/*     */ 
/*     */       
/* 432 */       colours.border = -1;
/*     */     }
/* 434 */     else if (mode == IEditablePanel.EditMode.EDIT_ALL || mode == IEditablePanel.EditMode.EDIT_BUTTONS) {
/*     */ 
/*     */       
/* 437 */       colours.border = selected ? -103 : -256;
/*     */     }
/* 439 */     else if (mode == IEditablePanel.EditMode.DELETE) {
/*     */       
/* 441 */       colours.border = isBound() ? -65536 : -22016;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 446 */     else if (isBound()) {
/*     */       
/* 448 */       switch (mode) {
/*     */         case COPY:
/* 450 */           colours.border = -16711936; break;
/* 451 */         case MOVE: colours.border = -16711681;
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 456 */     return colours;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Rectangle getPasteRect(Rectangle boundingBox) {
/* 461 */     return new Rectangle(boundingBox.x + boundingBox.width - 16, boundingBox.y + 2, 6, 6);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Rectangle getCopyRect(Rectangle boundingBox) {
/* 466 */     return new Rectangle(boundingBox.x + boundingBox.width - 8, boundingBox.y + 2, 6, 6);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void drawControlDecorations(Rectangle boundingBox) {
/* 471 */     if (this.macros.isKeyAlwaysOverridden(this.id, false, true)) {
/*     */       
/* 473 */       this.mc.N().a(ResourceLocations.MAIN);
/* 474 */       GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 475 */       setTexMapSize(256);
/* 476 */       drawTexturedModalRect(boundingBox.x + boundingBox.width - 11, boundingBox.y - 1, boundingBox.x + boundingBox.width - 1, boundingBox.y + 11, 72, 104, 96, 128);
/*     */     } 
/*     */ 
/*     */     
/* 480 */     if (this.macros.isKeyOverlaid(this.id))
/*     */     {
/* 482 */       drawRect(boundingBox, 1610612991);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void drawDragHandles(Rectangle boundingBox, int mouseX, int mouseY, IEditablePanel.EditMode mode, boolean selected) {
/* 488 */     int handleColour = -256;
/*     */     
/* 490 */     for (Handle handle : Handle.values()) {
/*     */       
/* 492 */       if (this.draggingHandle != handle)
/*     */       {
/* 494 */         drawRectOutline(handle.rect(boundingBox), handleColour, 1.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final Handle mouseOverHandle(Rectangle boundingBox, int mouseX, int mouseY) {
/* 501 */     for (Handle handle : Handle.values()) {
/*     */       
/* 503 */       if (handle.contains(boundingBox, mouseX, mouseY))
/*     */       {
/* 505 */         return handle;
/*     */       }
/*     */     } 
/*     */     
/* 509 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void clearCopyMask() {
/* 514 */     this.copyMask.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public final Set<String> getCopyMask() {
/* 519 */     return Collections.unmodifiableSet(this.copyMask);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void toggleCopyProperty(String property) {
/* 524 */     if (this.copyMask.contains(property)) {
/*     */       
/* 526 */       this.copyMask.remove(property);
/*     */     }
/*     */     else {
/*     */       
/* 530 */       this.copyMask.add(property);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean hasProperty(String property) {
/* 536 */     return this.properties.containsKey(property);
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
/*     */   public final String getProperty(String property, String defaultValue) {
/* 548 */     if ("name".equalsIgnoreCase(property))
/*     */     {
/* 550 */       return getName();
/*     */     }
/*     */     
/* 553 */     return this.properties.containsKey(property) ? this.properties.get(property) : defaultValue;
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
/*     */   public final int getProperty(String property, int defaultValue) {
/*     */     try {
/* 567 */       return this.properties.containsKey(property) ? Integer.parseInt(this.properties.get(property)) : defaultValue;
/*     */     }
/* 569 */     catch (NumberFormatException ex) {
/*     */       
/* 571 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean getProperty(String property, boolean defaultValue) {
/* 582 */     return getProperty(property, defaultValue ? "1" : "0").equals("1");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPropertyWithValidation(String property, String stringValue, int intValue, boolean boolValue) {
/* 587 */     if ("visible".equals(property))
/*     */     {
/* 589 */       setProperty(property, boolValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setProperty(String property, String value) {
/* 595 */     if ("name".equalsIgnoreCase(property)) {
/*     */       
/* 597 */       setName(value);
/*     */       
/*     */       return;
/*     */     } 
/* 601 */     loadProperty(property, value);
/* 602 */     update();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setProperty(String property, int value) {
/* 607 */     this.properties.put(property, String.valueOf(value));
/* 608 */     update();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setProperty(String property, boolean value) {
/* 613 */     this.properties.put(property, value ? "1" : "0");
/* 614 */     update();
/*     */   }
/*     */ 
/*     */   
/*     */   public final Set<String> getPropertyNames() {
/* 619 */     return Collections.unmodifiableSet(this.properties.keySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public final void copyPropertiesFrom(DesignableGuiControl other) {
/* 624 */     if (other == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 629 */     Set<String> mask = other.getCopyMask();
/*     */     
/* 631 */     for (Map.Entry<String, String> property : other.properties.entrySet()) {
/*     */       
/* 633 */       String key = property.getKey();
/* 634 */       if (!mask.isEmpty() && !mask.contains(key)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 639 */       if (hasProperty(key) && !"name".equals(key))
/*     */       {
/* 641 */         this.properties.put(key, property.getValue());
/*     */       }
/*     */     } 
/* 644 */     update();
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiDialogBoxControlProperties getPropertiesDialog(GuiScreenEx parentScreen) {
/* 649 */     return new GuiDialogBoxControlProperties(this.mc, parentScreen, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void applyChanges(GuiDialogBoxControlProperties guiDialogBoxControlProperties) {
/* 654 */     update();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void swapZ(DesignableGuiControl other) {
/* 659 */     int zIndex = other.zIndex;
/* 660 */     other.zIndex = this.zIndex;
/* 661 */     this.zIndex = zIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void bringToFront() {
/* 666 */     DesignableGuiControls controls = this.macros.getLayoutManager().getControls();
/*     */     
/* 668 */     for (DesignableGuiControl control : controls.getControls()) {
/*     */       
/* 670 */       if (control.id != this.id && control.zIndex > this.zIndex)
/*     */       {
/* 672 */         swapZ(control);
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
/*     */   public final boolean setPosition(DesignableGuiLayout parent, int column, int row) {
/* 684 */     if (canPlaceAt(row, column, parent) && (this.xPosition != column || this.yPosition != row)) {
/*     */       
/* 686 */       this.xPosition = column;
/* 687 */       this.yPosition = row;
/* 688 */       return true;
/*     */     } 
/*     */     
/* 691 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setPositionSnapped(DesignableGuiLayout parent, int x, int y) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Point getPosition(DesignableGuiLayout parent) {
/* 710 */     return this.lastDrawnLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getId() {
/* 719 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getWidth(DesignableGuiLayout parent) {
/* 728 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isBound() {
/* 737 */     return this.macros.isMacroBound(this.id, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isDenied() {
/* 746 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getZIndex() {
/* 755 */     return this.zIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getDisplayText() {
/* 764 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getDeniedText() {
/* 773 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void toggleReservedState() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ILayoutWidget.MousePressedResult mousePressed(bib minecraft, int dragOffsetX, int dragOffsetY) {
/* 791 */     if (this.mouseOverCopy)
/*     */     {
/* 793 */       return ILayoutWidget.MousePressedResult.COPY;
/*     */     }
/*     */     
/* 796 */     if (this.mouseOverPaste)
/*     */     {
/* 798 */       return ILayoutWidget.MousePressedResult.PASTE;
/*     */     }
/*     */     
/* 801 */     this.dragOffsetX = dragOffsetX;
/* 802 */     this.dragOffsetY = dragOffsetY;
/*     */     
/* 804 */     return ILayoutWidget.MousePressedResult.HIT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClickedEdit(int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void mouseDragged(bib minecraft, int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void mouseReleased(int mouseX, int mouseY) {
/* 828 */     this.dragOffsetX = 0;
/* 829 */     this.dragOffsetY = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean mouseOver(Rectangle boundingBox, int mouseX, int mouseY, boolean selected) {
/* 839 */     return (boundingBox.contains(mouseX, mouseY) || (selected && mouseOverHandle(boundingBox, mouseX, mouseY) != null));
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
/*     */   public final boolean cccupies(int row, int col) {
/* 851 */     return (row >= this.yPosition && row < this.yPosition + this.rowSpan && col >= this.xPosition && col < this.xPosition + this.colSpan);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean canPlaceAt(int row, int col, DesignableGuiLayout layout) {
/* 856 */     return canPlaceAt(row, col, this.rowSpan, this.colSpan, layout);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean canPlaceAt(int row, int col, int rowSpan, int colSpan, DesignableGuiLayout layout) {
/* 861 */     for (int x = col; x < col + colSpan; x++) {
/*     */       
/* 863 */       for (int y = row; y < row + rowSpan; y++) {
/*     */         
/* 865 */         if (layout.isCellOccupied(y, x, this))
/*     */         {
/* 867 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 872 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void load(Node xml) {
/* 877 */     this.xPosition = Xml.xmlGetAttribute(xml, "x", 0);
/* 878 */     this.yPosition = Xml.xmlGetAttribute(xml, "y", 0);
/* 879 */     this.rowSpan = Math.max(1, Xml.xmlGetAttribute(xml, "rowspan", 1));
/* 880 */     this.colSpan = Math.max(1, Xml.xmlGetAttribute(xml, "colspan", 1));
/*     */     
/* 882 */     for (Node propertyNode : Xml.xmlNodes(xml, "*"))
/*     */     {
/* 884 */       loadProperty(propertyNode.getLocalName(), propertyNode.getTextContent());
/*     */     }
/*     */     
/* 887 */     update();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadProperty(String name, String content) {
/* 892 */     this.properties.put(name, content);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(Document xml, Element controlNode, boolean export) {
/* 903 */     controlNode.setAttribute("id", export ? "*" : String.valueOf(this.id));
/* 904 */     if (!export)
/*     */     {
/* 906 */       controlNode.setAttribute("name", getName());
/*     */     }
/* 908 */     controlNode.setAttribute("x", String.valueOf(this.xPosition));
/* 909 */     controlNode.setAttribute("y", String.valueOf(this.yPosition));
/* 910 */     if (this.rowSpan != 1) controlNode.setAttribute("rowspan", String.valueOf(this.rowSpan)); 
/* 911 */     if (this.colSpan != 1) controlNode.setAttribute("colspan", String.valueOf(this.colSpan));
/*     */     
/* 913 */     for (Map.Entry<String, String> property : this.properties.entrySet()) {
/*     */       
/* 915 */       Element propertyNode = xml.createElement(property.getKey());
/* 916 */       propertyNode.setTextContent(property.getValue());
/* 917 */       controlNode.appendChild(propertyNode);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleClick(Rectangle boundingBox, int mouseX, int mouseY, Listener listener) {
/* 923 */     if (isBindable()) {
/*     */       
/* 925 */       if (dispatchOnClick() && listener != null)
/*     */       {
/* 927 */         listener.onDispatch(this);
/*     */       }
/*     */       
/* 930 */       if (executeOnClick()) {
/*     */         
/* 932 */         executeMacro();
/* 933 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 937 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseMove(Rectangle boundingBox, int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseReleased(Rectangle boundingBox, int mouseX, int mouseY) {}
/*     */ 
/*     */   
/*     */   public boolean handleKeyTyped(char keyChar, int keyCode, Listener listener) {
/* 950 */     int hotkey = getProperty("hotkey", 0);
/* 951 */     if (hotkey > 0 && hotkey == keyCode) {
/*     */       
/* 953 */       dispatch(listener);
/* 954 */       return true;
/*     */     } 
/*     */     
/* 957 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean dispatch(Listener listener) {
/* 962 */     if (isBindable()) {
/*     */       
/* 964 */       if (listener != null)
/*     */       {
/* 966 */         listener.onDispatch(this);
/*     */       }
/* 968 */       executeMacro();
/* 969 */       return true;
/*     */     } 
/*     */     
/* 972 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void executeMacro() {
/* 977 */     this.macros.playMacro(this.id, false, getScriptContext(), getScriptContextProvider());
/*     */   }
/*     */ 
/*     */   
/*     */   protected ScriptContext getScriptContext() {
/* 982 */     return ScriptContext.MAIN;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IVariableProvider getScriptContextProvider() {
/* 987 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static int brightenColour(int colour, int amt) {
/* 992 */     int alpha = colour & 0xFF000000;
/* 993 */     int red = colour >> 16 & 0xFF;
/* 994 */     int green = colour >> 8 & 0xFF;
/* 995 */     int blue = colour & 0xFF;
/*     */     
/* 997 */     return Math.min(255, blue + amt) + (Math.min(255, green + amt) << 8) + (Math.min(255, red + amt) << 16) + alpha;
/*     */   }
/*     */   
/*     */   protected abstract void onTick(int paramInt);
/*     */   
/*     */   protected abstract void update();
/*     */   
/*     */   protected abstract void draw(DesignableGuiLayout paramDesignableGuiLayout, Rectangle paramRectangle, int paramInt1, int paramInt2);
/*     */   
/*     */   protected abstract void drawWidget(DesignableGuiLayout paramDesignableGuiLayout, Rectangle paramRectangle, int paramInt1, int paramInt2, int paramInt3);
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\DesignableGuiControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */