/*      */ package net.eq2online.macros.gui.designable;
/*      */ 
/*      */ import bib;
/*      */ import com.mumfrey.liteloader.gl.GL;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.io.File;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
/*      */ import net.eq2online.console.Log;
/*      */ import net.eq2online.macros.core.Macros;
/*      */ import net.eq2online.macros.gui.GuiRendererMacros;
/*      */ import net.eq2online.macros.interfaces.IEditablePanel;
/*      */ import net.eq2online.macros.res.ResourceLocations;
/*      */ import net.eq2online.xml.Xml;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class DesignableGuiLayout
/*      */   extends GuiRendererMacros
/*      */ {
/*      */   public static final int MIN_RENDER_SIZE = 16;
/*      */   private final LayoutManager manager;
/*      */   private final String name;
/*      */   
/*      */   public static class ClickedControlInfo
/*      */   {
/*      */     public final DesignableGuiControl control;
/*      */     public final Rectangle boundingBox;
/*      */     
/*      */     ClickedControlInfo(DesignableGuiControl control, Rectangle boundingBox) {
/*   44 */       this.control = control;
/*   45 */       this.boundingBox = boundingBox;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean handleClick(int mouseX, int mouseY, DesignableGuiControl.Listener listener) {
/*   50 */       return this.control.handleClick(this.boundingBox, mouseX, mouseY, listener);
/*      */     }
/*      */ 
/*      */     
/*      */     public void handleMouseMove(int mouseX, int mouseY) {
/*   55 */       this.control.handleMouseMove(this.boundingBox, mouseX, mouseY);
/*      */     }
/*      */ 
/*      */     
/*      */     public void handleMouseReleased(int mouseX, int mouseY) {
/*   60 */       this.control.handleMouseReleased(this.boundingBox, mouseX, mouseY);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class Grid
/*      */   {
/*      */     final int dynamicWidth;
/*      */     final int cellWidth;
/*      */     final int cellHeight;
/*      */     final int widthRemainder;
/*      */     
/*      */     Grid(Rectangle bounds, int fixedWidthAllocation, int rows, int columns, int dynamicColumns, int padding) {
/*   73 */       this.dynamicWidth = getDynamicWidth(bounds, fixedWidthAllocation);
/*   74 */       this.cellWidth = Math.max(0, this.dynamicWidth / dynamicColumns - padding * 2);
/*   75 */       this.cellHeight = bounds.height / rows - padding * 2;
/*   76 */       this.widthRemainder = this.dynamicWidth - (this.cellWidth + padding * 2) * dynamicColumns;
/*      */     }
/*      */ 
/*      */     
/*      */     protected int getDynamicWidth(Rectangle bounds, int fixedWidthAllocation) {
/*   81 */       if (bounds.width > fixedWidthAllocation)
/*      */       {
/*   83 */         return bounds.width - fixedWidthAllocation;
/*      */       }
/*      */       
/*   86 */       return 0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   99 */   private String displayName = "Layout";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  104 */   private int rows = 10;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  109 */   private int columns = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  114 */   private int padding = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean drawGrid = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean dotGrid = false;
/*      */ 
/*      */   
/*      */   private boolean interactive = true;
/*      */ 
/*      */   
/*  128 */   private Map<Integer, DesignableGuiControl> controls = new TreeMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  133 */   private int[] columnMetrics = new int[256];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  138 */   private int[] fixedWidthTo = new int[this.columnMetrics.length];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  145 */   private int[] dynamicWidthTo = new int[this.columnMetrics.length];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  150 */   private int fixedWidthAllocation = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  155 */   private int dynamicColumns = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  160 */   private String placingControlType = null;
/*      */   
/*  162 */   private int mouseOverColumn = -1;
/*      */   
/*  164 */   private int editingColumn = -1;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean drawing;
/*      */ 
/*      */ 
/*      */   
/*      */   private int tickNumber;
/*      */ 
/*      */ 
/*      */   
/*      */   public DesignableGuiLayout(Macros macros, bib mc, LayoutManager manager, String name) {
/*  177 */     super(macros, mc);
/*      */     
/*  179 */     this.manager = manager;
/*  180 */     this.name = name;
/*  181 */     this.displayName = name;
/*      */     
/*  183 */     updateColumnMetrics();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DesignableGuiLayout(Macros macros, bib mc, LayoutManager manager, Node xml) {
/*  193 */     super(macros, mc);
/*      */     
/*  195 */     this.manager = manager;
/*  196 */     this.name = Xml.xmlGetAttribute(xml, "name", "default");
/*  197 */     this.displayName = Xml.xmlGetAttribute(xml, "display", this.name);
/*  198 */     this.rows = Math.max(1, Xml.xmlGetAttribute(xml, "rows", 10));
/*  199 */     this.columns = Math.max(1, Xml.xmlGetAttribute(xml, "columns", 6));
/*  200 */     this.padding = Math.max(0, Xml.xmlGetAttribute(xml, "padding", this.name.equalsIgnoreCase("ingame") ? 1 : 2));
/*  201 */     this.drawGrid = Xml.xmlGetAttribute(xml, "grid", "false").equalsIgnoreCase("true");
/*  202 */     this.dotGrid = Xml.xmlGetAttribute(xml, "dots", "false").equalsIgnoreCase("true");
/*      */     
/*  204 */     for (Node controlNode : Xml.xmlNodes(xml, "gc:controls/gc:*")) {
/*      */       
/*  206 */       DesignableGuiControl newControl = this.manager.getControls().createControl(controlNode);
/*  207 */       if (newControl != null) {
/*      */         
/*  209 */         this.controls.put(Integer.valueOf(newControl.id), newControl);
/*  210 */         this.columns = Math.max(this.columns, newControl.xPosition + newControl.colSpan);
/*  211 */         this.rows = Math.max(this.rows, newControl.yPosition + newControl.rowSpan);
/*      */       } 
/*      */     } 
/*      */     
/*  215 */     for (Node metricNode : Xml.xmlNodes(xml, "gc:metrics/gc:column")) {
/*      */       
/*  217 */       int id = Xml.xmlGetAttribute(metricNode, "id", -1);
/*  218 */       int width = Math.max(0, Xml.xmlGetAttribute(metricNode, "width", 0));
/*  219 */       if (id > -1 && id < this.columns && width > 0)
/*      */       {
/*  221 */         this.columnMetrics[id] = width;
/*      */       }
/*      */     } 
/*      */     
/*  225 */     updateColumnMetrics();
/*      */   }
/*      */ 
/*      */   
/*      */   public void register() {
/*  230 */     this.manager.addLayout(this);
/*  231 */     this.manager.saveSettings();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getName() {
/*  236 */     return this.name;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDisplayName() {
/*  241 */     return this.displayName;
/*      */   }
/*      */ 
/*      */   
/*      */   public DesignableGuiLayout setDisplayName(String displayName) {
/*  246 */     this.displayName = displayName;
/*  247 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public LayoutManager getManager() {
/*  252 */     return this.manager;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInteractive() {
/*  257 */     return this.interactive;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void export(File xmlFile) {
/*      */     try {
/*  264 */       Document xml = Xml.xmlCreate();
/*      */       
/*  266 */       Element rootNode = xml.createElement("gui");
/*  267 */       rootNode.setAttribute("xmlns:gc", "http://eq2online.net/macros/guiconfiguration");
/*  268 */       rootNode.setAttribute("xmlns:gb", "http://eq2online.net/macros/guibinding");
/*  269 */       xml.appendChild(rootNode);
/*      */       
/*  271 */       Element layoutNode = xml.createElement("gc:guilayout");
/*  272 */       save(xml, layoutNode, true);
/*  273 */       rootNode.appendChild(xml.createComment(" Exported layout '" + getName() + "' "));
/*  274 */       rootNode.appendChild(layoutNode);
/*      */       
/*  276 */       Xml.xmlSave(xmlFile, xml);
/*      */     }
/*  278 */     catch (Exception ex) {
/*      */       
/*  280 */       ex.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void save(Document xml, Element node) {
/*  292 */     save(xml, node, false);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void save(Document xml, Element node, boolean export) {
/*  297 */     if (!export)
/*      */     {
/*  299 */       node.setAttribute("name", this.name);
/*      */     }
/*      */     
/*  302 */     node.setAttribute("display", this.displayName);
/*  303 */     node.setAttribute("rows", String.valueOf(this.rows));
/*  304 */     node.setAttribute("columns", String.valueOf(this.columns));
/*  305 */     node.setAttribute("padding", String.valueOf(this.padding));
/*  306 */     node.setAttribute("grid", this.drawGrid ? "true" : "false");
/*  307 */     node.setAttribute("dots", this.dotGrid ? "true" : "false");
/*      */     
/*  309 */     Element metricsNode = xml.createElement("gc:metrics");
/*  310 */     boolean hasMetrics = false;
/*      */     
/*  312 */     for (int i = 0; i < this.columnMetrics.length; i++) {
/*      */       
/*  314 */       if (this.columnMetrics[i] > 0) {
/*      */         
/*  316 */         hasMetrics = true;
/*  317 */         Element metricNode = xml.createElement("gc:column");
/*  318 */         metricNode.setAttribute("id", String.valueOf(i));
/*  319 */         metricNode.setAttribute("width", String.valueOf(this.columnMetrics[i]));
/*  320 */         metricsNode.appendChild(metricNode);
/*      */       } 
/*      */     } 
/*      */     
/*  324 */     if (hasMetrics)
/*      */     {
/*  326 */       node.appendChild(metricsNode);
/*      */     }
/*      */     
/*  329 */     Element controlsNode = xml.createElement("gc:controls");
/*      */     
/*  331 */     for (DesignableGuiControl control : this.controls.values()) {
/*      */       
/*  333 */       Element controlNode = xml.createElement("gc:" + control.getType());
/*  334 */       control.save(xml, controlNode, export);
/*  335 */       controlsNode.appendChild(controlNode);
/*      */     } 
/*      */     
/*  338 */     node.appendChild(controlsNode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateColumnMetrics() {
/*  346 */     this.placingControlType = null;
/*      */     
/*  348 */     int dynamicColumnsAt = 0;
/*  349 */     this.dynamicColumns = this.columns;
/*  350 */     this.fixedWidthAllocation = 0;
/*      */     
/*  352 */     for (int i = 0; i < this.columnMetrics.length; i++) {
/*      */       
/*  354 */       this.fixedWidthTo[i] = this.fixedWidthAllocation;
/*  355 */       this.dynamicWidthTo[i] = dynamicColumnsAt;
/*      */       
/*  357 */       if (i < this.columns) {
/*      */         
/*  359 */         this.fixedWidthAllocation += this.columnMetrics[i];
/*  360 */         if (this.columnMetrics[i] > 0) {
/*      */           
/*  362 */           this.dynamicColumns--;
/*      */         }
/*      */         else {
/*      */           
/*  366 */           dynamicColumnsAt++;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  371 */     if (this.dynamicColumns < 1) {
/*      */       
/*  373 */       this.columnMetrics[0] = 0;
/*  374 */       updateColumnMetrics();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DesignableGuiControl getControl(int controlId) {
/*  386 */     return this.controls.get(Integer.valueOf(controlId));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends DesignableGuiControl> T getControl(String name) {
/*  398 */     for (DesignableGuiControl control : this.controls.values()) {
/*      */       
/*  400 */       if (control.getName().equals(name)) {
/*      */         
/*      */         try {
/*      */ 
/*      */           
/*  405 */           return (T)control;
/*      */         
/*      */         }
/*  408 */         catch (ClassCastException ex) {
/*      */           
/*  410 */           return null;
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  415 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DesignableGuiControl> getControls() {
/*  425 */     return new ArrayList<>(this.controls.values());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTick(int tickNumber) {
/*  433 */     if (tickNumber == this.tickNumber) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  438 */     this.tickNumber = tickNumber;
/*      */     
/*  440 */     for (DesignableGuiControl control : this.controls.values()) {
/*      */       
/*  442 */       if (control.onlyTickWhenVisible())
/*      */       {
/*  444 */         control.onTick(tickNumber);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(Rectangle bounds, int mouseX, int mouseY, boolean interactive) {
/*  458 */     draw(bounds, mouseX, mouseY, false, true, interactive, (IEditablePanel.EditMode)null, (DesignableGuiControl)null, (DesignableGuiControl)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(Rectangle bounds, int mouseX, int mouseY, IEditablePanel.EditMode mode, DesignableGuiControl selected, DesignableGuiControl copySource) {
/*  472 */     draw(bounds, mouseX, mouseY, true, true, true, mode, selected, copySource);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void draw(Rectangle bounds, int mouseX, int mouseY, boolean drawGrid, boolean drawControls, boolean interactive, IEditablePanel.EditMode mode, DesignableGuiControl selected, DesignableGuiControl copySource) {
/*  488 */     if (this.drawing) {
/*      */       
/*  490 */       renderErrorBox(bounds, "Already rendered", this.name);
/*      */       
/*      */       return;
/*      */     } 
/*  494 */     if (bounds.width < this.fixedWidthAllocation || bounds.width < 16 || bounds.height < 16) {
/*      */ 
/*      */       
/*  497 */       renderErrorBox(bounds, "<?", (String)null);
/*      */       
/*      */       return;
/*      */     } 
/*  501 */     this.drawing = true;
/*      */ 
/*      */     
/*      */     try {
/*  505 */       this.interactive = interactive;
/*  506 */       int originalMouseY = mouseY;
/*      */       
/*  508 */       boolean editingGrid = this.mc.m instanceof net.eq2online.macros.gui.designable.editor.GuiDialogBoxSetGridSize;
/*      */       
/*  510 */       Grid grid = new Grid(bounds, this.fixedWidthAllocation, this.rows, this.columns, this.dynamicColumns, this.padding);
/*  511 */       float totalHeight = (bounds.height - bounds.height % this.rows);
/*      */       
/*  513 */       GL.glPushMatrix();
/*      */       
/*  515 */       int scaledMouseY = setupVerticalScaling(bounds, mouseY, totalHeight);
/*      */       
/*  517 */       if (!editingGrid)
/*      */       {
/*      */         
/*  520 */         drawGrid(bounds, mouseX, scaledMouseY, drawGrid, editingGrid, grid);
/*      */       }
/*      */       
/*  523 */       drawControls(bounds, editingGrid ? -100 : mouseX, editingGrid ? -100 : scaledMouseY, drawControls, mode, selected, copySource, grid);
/*      */       
/*  525 */       if (editingGrid)
/*      */       {
/*      */         
/*  528 */         drawGrid(bounds, mouseX, scaledMouseY, drawGrid, editingGrid, grid);
/*      */       }
/*      */ 
/*      */       
/*  532 */       drawPlacementCursor(bounds, mouseX, originalMouseY, copySource);
/*      */       
/*  534 */       GL.glPopMatrix();
/*  535 */       GL.glClear(256);
/*      */     }
/*      */     finally {
/*      */       
/*  539 */       this.drawing = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderErrorBox(Rectangle bounds, String message, String m2) {
/*  545 */     drawRectOutline(bounds, -43691, 1.0F);
/*  546 */     this.fontRenderer.a(message, bounds.x + 2, bounds.y + 2, -43691);
/*  547 */     if (m2 != null)
/*      */     {
/*  549 */       this.fontRenderer.a(m2, bounds.x + 2, bounds.y + 12, -1);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected int setupVerticalScaling(Rectangle bounds, int mouseY, float totalHeight) {
/*  555 */     int scaledMouseY = mouseY;
/*      */ 
/*      */     
/*  558 */     if (totalHeight != bounds.height) {
/*      */       
/*  560 */       float scale = 1.0F / totalHeight / bounds.height;
/*  561 */       scaledMouseY = (int)((mouseY - bounds.y) / scale + bounds.y);
/*  562 */       GL.glScalef(1.0F, scale, 1.0F);
/*  563 */       GL.glTranslatef(0.0F, (1.0F - scale) * bounds.y, 0.0F);
/*      */     } 
/*  565 */     return scaledMouseY;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawControls(Rectangle bounds, int mouseX, int mouseY, boolean drawControls, IEditablePanel.EditMode mode, DesignableGuiControl selected, DesignableGuiControl copySource, Grid grid) {
/*  571 */     for (DesignableGuiControl control : this.controls.values()) {
/*      */       
/*  573 */       Rectangle boundingBox = calcBoundingBox(bounds, grid, control);
/*  574 */       if (drawControls) {
/*      */         
/*  576 */         if (mode != null) {
/*      */           
/*  578 */           boolean sel = (control == selected && (mode == IEditablePanel.EditMode.EDIT_ALL || mode == IEditablePanel.EditMode.EDIT_BUTTONS));
/*  579 */           control.draw(this, boundingBox, mouseX, mouseY, mode, sel, false, (copySource == control), (copySource != null));
/*      */           
/*      */           continue;
/*      */         } 
/*  583 */         control.draw(this, boundingBox, mouseX, mouseY);
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  588 */       drawRectOutline(boundingBox, -10066330, 1.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawGrid(Rectangle bounds, int mouseX, int mouseY, boolean drawGrid, boolean editingGrid, Grid grid) {
/*  602 */     this.mouseOverColumn = -1;
/*      */     
/*  604 */     if (!editingGrid || this.editingColumn >= this.columns)
/*      */     {
/*  606 */       this.editingColumn = -1;
/*      */     }
/*      */     
/*  609 */     if (drawGrid || this.drawGrid) {
/*      */       
/*  611 */       int gridColour = editingGrid ? -1433892864 : (this.dotGrid ? -286379264 : -1718026240);
/*  612 */       int fixedColColour = -5614336;
/*  613 */       int dynamicColColour = -16733696;
/*      */       
/*  615 */       int xPos = bounds.x;
/*  616 */       for (int col = 0; col <= this.columns; col++) {
/*      */         
/*  618 */         if (this.dotGrid && !editingGrid) {
/*      */           
/*  620 */           int yPos = bounds.y;
/*  621 */           for (int row = 0; row <= this.rows; row++)
/*      */           {
/*  623 */             a(xPos, yPos, xPos + 1, yPos + 1, gridColour);
/*  624 */             yPos += grid.cellHeight + this.padding * 2;
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  629 */           drawNativeLine(xPos, bounds.y, xPos, (bounds.y + (grid.cellHeight + this.padding * 2) * this.rows), 1.0F, (this.editingColumn > -1 && (this.editingColumn == col || this.editingColumn + 1 == col)) ? -1 : gridColour);
/*      */         } 
/*      */ 
/*      */         
/*  633 */         int lastXPos = xPos;
/*  634 */         xPos += ((this.columnMetrics[col] > 0) ? this.columnMetrics[col] : (grid.cellWidth + this.padding * 2)) + ((col == this.columns - 1) ? grid.widthRemainder : 0);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  639 */         if (col < this.columns && editingGrid) {
/*      */ 
/*      */           
/*  642 */           if (mouseX >= lastXPos && mouseX < xPos)
/*      */           {
/*  644 */             this.mouseOverColumn = col;
/*      */           }
/*      */ 
/*      */           
/*  648 */           int arrowColour = (this.editingColumn == col) ? -1 : ((this.columnMetrics[col] > 0) ? fixedColColour : dynamicColColour);
/*  649 */           drawDoubleEndedArrowH(lastXPos, xPos, (bounds.y + 5), 2.0F, 6.0F, arrowColour);
/*      */ 
/*      */           
/*  652 */           String colText = (this.columnMetrics[col] > 0) ? String.valueOf(this.columnMetrics[col]) : "Auto";
/*  653 */           int xMid = lastXPos + (xPos - lastXPos) / 2;
/*  654 */           int textWidth = this.mc.k.a(colText) / 2;
/*  655 */           c(this.mc.k, colText, xMid - textWidth, bounds.y + 7, arrowColour);
/*      */         } 
/*      */       } 
/*      */       
/*  659 */       if (!this.dotGrid || editingGrid) {
/*      */         
/*  661 */         int yPos = bounds.y;
/*  662 */         for (int row = 0; row <= this.rows; row++) {
/*      */           
/*  664 */           drawNativeLine(bounds.x, yPos, (bounds.x + bounds.width), yPos, 1.0F, gridColour);
/*  665 */           yPos += grid.cellHeight + this.padding * 2;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int scaleMouseY(Rectangle bounds, int mouseY) {
/*  683 */     return scaleMouseY(bounds, mouseY, bounds.y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int scaleMouseY(Rectangle bounds, int mouseY, int top) {
/*  699 */     float totalHeight = (bounds.height - bounds.height % this.rows);
/*  700 */     if (totalHeight != bounds.height) {
/*      */       
/*  702 */       float scale = 1.0F / totalHeight / bounds.height;
/*  703 */       mouseY = (int)((mouseY - top) / scale + top);
/*      */     } 
/*      */     
/*  706 */     return mouseY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawPlacementCursor(Rectangle bounds, int mouseX, int mouseY, DesignableGuiControl copySource) {
/*  717 */     if (this.placingControlType != null) {
/*      */       
/*  719 */       Point pos = getRowAndColumnAt(bounds, mouseX, mouseY);
/*  720 */       Rectangle boundingBox = calcBoundingBox(bounds, pos.x, pos.y, 1, 1);
/*      */       
/*  722 */       if (!isCellOccupied(pos)) {
/*      */         
/*  724 */         drawRectOutline(boundingBox, (copySource != null) ? -16711936 : -256, 2.0F);
/*  725 */         bindTexture(ResourceLocations.MAIN, 128);
/*  726 */         GL.glColor4f((copySource != null) ? 0.0F : 1.0F, 1.0F, 0.0F, 1.0F);
/*  727 */         drawTexturedModalIcon(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height, 118, 48, 128, 58);
/*  728 */         if (copySource != null)
/*      */         {
/*  730 */           this.mc.N().a(ResourceLocations.EXT);
/*  731 */           drawTexturedModalRect(boundingBox.x + 2, boundingBox.y + 2, 16, 96, 6, 6, 0.0078125F);
/*      */         }
/*      */       
/*  734 */       } else if (pos.x > -1 && pos.y > -1) {
/*      */         
/*  736 */         drawRectOutline(boundingBox, -65536, 2.0F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point getRowAndColumnAt(Rectangle bounds, int mouseX, int mouseY) {
/*  751 */     return new Point(getColumnAt(bounds, mouseX, -1), getRowAt(bounds, mouseY, -1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point getLogicalRowAndColumnAt(Rectangle bounds, int mouseX, int mouseY) {
/*  764 */     return new Point(getColumnAt(bounds, mouseX, this.columns), getRowAt(bounds, mouseY, this.rows));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRowAt(Rectangle bounds, int mouseY, int outOfBoundsRow) {
/*  776 */     if (mouseY < bounds.y) return -1; 
/*  777 */     if (mouseY >= bounds.y + bounds.height) return outOfBoundsRow; 
/*  778 */     return (int)((mouseY - bounds.y) / bounds.height * this.rows);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColumnAt(Rectangle bounds, int mouseX, int outOfBoundsCol) {
/*  790 */     if (mouseX < bounds.x) return -1; 
/*  791 */     if (mouseX >= bounds.x + bounds.width) return outOfBoundsCol; 
/*  792 */     mouseX -= bounds.x;
/*      */     
/*  794 */     int dynamicWidth = ((bounds.width > this.fixedWidthAllocation) ? (bounds.width - this.fixedWidthAllocation) : 0) / this.dynamicColumns;
/*      */     
/*  796 */     for (int i = 0; i <= this.columns; i++) {
/*      */       
/*  798 */       if (mouseX <= dynamicWidth * this.dynamicWidthTo[i] + this.fixedWidthTo[i]) return i - 1;
/*      */     
/*      */     } 
/*  801 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public Point getNearestGridPoint(Rectangle bounds, int mouseX, int mouseY) {
/*  806 */     int yGrid = getRowAt(bounds, mouseY, -1);
/*  807 */     int yPos = (yGrid > -1) ? (bounds.y + bounds.height / this.rows * yGrid) : bounds.y;
/*      */     
/*  809 */     int xPos = bounds.x;
/*      */     
/*  811 */     if (mouseX > bounds.x && mouseX <= bounds.x + bounds.width) {
/*      */       
/*  813 */       mouseX -= bounds.x;
/*      */       
/*  815 */       int dynamicWidth = ((bounds.width > this.fixedWidthAllocation) ? (bounds.width - this.fixedWidthAllocation) : 0) / this.dynamicColumns;
/*      */       
/*  817 */       for (int i = 1; i <= this.columns; i++) {
/*      */         
/*  819 */         if (mouseX <= dynamicWidth * this.dynamicWidthTo[i] + this.fixedWidthTo[i]) {
/*      */           
/*  821 */           xPos = bounds.x + dynamicWidth * this.dynamicWidthTo[i - 1] + this.fixedWidthTo[i - 1];
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  827 */     return new Point(xPos, yPos);
/*      */   }
/*      */ 
/*      */   
/*      */   public Point getDragPoint(Rectangle bounds, DesignableGuiControl control) {
/*  832 */     float yGrid = control.yPosition + 0.5F;
/*  833 */     int yPos = (int)((yGrid > -1.0F) ? (bounds.y + (bounds.height / this.rows) * yGrid) : bounds.y);
/*      */     
/*  835 */     int xPos = bounds.x;
/*      */     
/*  837 */     int dynamicWidth = ((bounds.width > this.fixedWidthAllocation) ? (bounds.width - this.fixedWidthAllocation) : 0) / this.dynamicColumns;
/*  838 */     int columnWidth = (this.columnMetrics[control.xPosition] == 0) ? dynamicWidth : this.columnMetrics[control.xPosition];
/*  839 */     xPos = bounds.x + dynamicWidth * this.dynamicWidthTo[control.xPosition] + this.fixedWidthTo[control.xPosition] + columnWidth / 2;
/*      */     
/*  841 */     return new Point(xPos, yPos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCellOccupied(Point pos) {
/*  853 */     return isCellOccupied(pos.y, pos.x, (DesignableGuiControl)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCellOccupied(Point pos, DesignableGuiControl sourceControl) {
/*  866 */     return isCellOccupied(pos.y, pos.x, sourceControl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCellOccupied(int row, int col, DesignableGuiControl sourceControl) {
/*  881 */     if (row < 0 || col < 0 || row >= this.rows || col >= this.columns) return true;
/*      */     
/*  883 */     for (DesignableGuiControl control : this.controls.values()) {
/*      */       
/*  885 */       if (sourceControl != control && control.cccupies(row, col))
/*      */       {
/*  887 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  891 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleMouseMove(Rectangle bounds, int mouseX, int mouseY, DesignableGuiControl control) {
/*  896 */     control.handleMouseMove(calcBoundingBox(bounds, control), mouseX, scaleMouseY(bounds, mouseY));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleMouseReleased(Rectangle bounds, int mouseX, int mouseY, DesignableGuiControl control) {
/*  901 */     control.handleMouseReleased(calcBoundingBox(bounds, control), mouseX, scaleMouseY(bounds, mouseY));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleMouseMove(Rectangle bounds, int mouseX, int mouseY, ClickedControlInfo control) {
/*  906 */     control.control.handleMouseMove(control.boundingBox, mouseX, scaleMouseY(bounds, mouseY));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleMouseReleased(Rectangle bounds, int mouseX, int mouseY, ClickedControlInfo control) {
/*  911 */     control.control.handleMouseReleased(control.boundingBox, mouseX, scaleMouseY(bounds, mouseY));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClickedControlInfo getControlAt(Rectangle bounds, int mouseX, int mouseY, DesignableGuiControl selectedControl) {
/*  925 */     mouseY = scaleMouseY(bounds, mouseY);
/*      */     
/*  927 */     Grid grid = new Grid(bounds, this.fixedWidthAllocation, this.rows, this.columns, this.dynamicColumns, this.padding);
/*      */     
/*  929 */     ClickedControlInfo topMost = null;
/*      */     
/*  931 */     for (DesignableGuiControl control : this.controls.values()) {
/*      */       
/*  933 */       Rectangle boundingBox = calcBoundingBox(bounds, grid, control);
/*      */       
/*  935 */       if (control.mouseOver(boundingBox, mouseX, mouseY, (control == selectedControl)) && (topMost == null || control.zIndex > topMost.control.zIndex))
/*      */       {
/*      */         
/*  938 */         topMost = new ClickedControlInfo(control, boundingBox);
/*      */       }
/*      */     } 
/*      */     
/*  942 */     return topMost;
/*      */   }
/*      */ 
/*      */   
/*      */   public DesignableGuiControl.Handle getControlHandleAt(Rectangle bounds, int mouseX, int mouseY, DesignableGuiControl control) {
/*  947 */     mouseY = scaleMouseY(bounds, mouseY);
/*  948 */     Rectangle boundingBox = calcBoundingBox(bounds, control);
/*  949 */     return control.mouseOverHandle(boundingBox, mouseX, mouseY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle calcBoundingBox(Rectangle bounds, DesignableGuiControl control) {
/*  961 */     return calcBoundingBox(bounds, control.xPosition, control.yPosition, control.rowSpan, control.colSpan);
/*      */   }
/*      */ 
/*      */   
/*      */   public Rectangle calcBoundingBox(Rectangle bounds, int xPosition, int yPosition, int rowSpan, int colSpan) {
/*  966 */     Grid grid = new Grid(bounds, this.fixedWidthAllocation, this.rows, this.columns, this.dynamicColumns, this.padding);
/*  967 */     return calcBoundingBox(bounds, grid, xPosition, yPosition, rowSpan, colSpan);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rectangle calcBoundingBox(Rectangle bounds, Grid grid, DesignableGuiControl control) {
/*  982 */     return calcBoundingBox(bounds, grid, control.xPosition, control.yPosition, control.rowSpan, control.colSpan);
/*      */   }
/*      */ 
/*      */   
/*      */   protected Rectangle calcBoundingBox(Rectangle bounds, Grid grid, int xPosition, int yPosition, int rowSpan, int colSpan) {
/*  987 */     int cellPadding = this.padding * 2;
/*      */ 
/*      */     
/*  990 */     colSpan = Math.max(Math.min(colSpan, this.columns - xPosition), 1);
/*  991 */     rowSpan = Math.max(Math.min(rowSpan, this.rows - yPosition), 1);
/*      */ 
/*      */     
/*  994 */     int width = colSpan * grid.cellWidth + (colSpan - 1) * cellPadding + ((xPosition + colSpan >= this.columns) ? grid.widthRemainder : 0);
/*  995 */     int height = rowSpan * grid.cellHeight + (rowSpan - 1) * cellPadding;
/*      */ 
/*      */     
/*  998 */     int x = bounds.x + this.padding;
/*  999 */     int y = bounds.y + this.padding + yPosition * grid.cellHeight + yPosition * cellPadding;
/* 1000 */     for (int col = 0; col < xPosition + colSpan; col++) {
/*      */       
/* 1002 */       if (col < xPosition) x += (this.columnMetrics[col] > 0) ? this.columnMetrics[col] : (grid.cellWidth + cellPadding); 
/* 1003 */       if (col >= xPosition && this.columnMetrics[col] > 0) width += this.columnMetrics[col] - cellPadding + -grid.cellWidth;
/*      */     
/*      */     } 
/* 1006 */     return new Rectangle(x, y, width, height);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setWidgetPosition(Rectangle bounds, int mouseX, int mouseY, DesignableGuiControl control) {
/* 1011 */     int scaledDragOffset = scaleMouseY(bounds, control.dragOffsetY, 0);
/* 1012 */     Point pos = getRowAndColumnAt(bounds, mouseX - control.dragOffsetX + this.padding, mouseY - scaledDragOffset + this.padding);
/* 1013 */     if (pos.x < 0 || pos.y < 0) return false; 
/* 1014 */     return control.setPosition(this, pos.x, pos.y);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setControlSpan(Rectangle bounds, int mouseX, int mouseY, DesignableGuiControl control, DesignableGuiControl.Handle handle) {
/* 1019 */     Point pos = getLogicalRowAndColumnAt(bounds, mouseX, mouseY);
/*      */     
/* 1021 */     if (handle == DesignableGuiControl.Handle.EAST) {
/*      */       
/* 1023 */       if (pos.x < 0 || pos.y < 0)
/* 1024 */         return;  int newColspan = Math.max(1, pos.x - control.xPosition);
/* 1025 */       if (control.canPlaceAt(control.yPosition, control.xPosition, control.rowSpan, newColspan, this))
/*      */       {
/* 1027 */         control.colSpan = newColspan;
/*      */       }
/*      */     }
/* 1030 */     else if (handle == DesignableGuiControl.Handle.WEST) {
/*      */       
/* 1032 */       int newColspan = Math.max(0, control.colSpan - pos.x - control.xPosition);
/* 1033 */       if (control.canPlaceAt(control.yPosition, pos.x, control.rowSpan, newColspan, this))
/*      */       {
/* 1035 */         control.xPosition = pos.x;
/* 1036 */         control.colSpan = newColspan;
/*      */       }
/*      */     
/* 1039 */     } else if (handle == DesignableGuiControl.Handle.SOUTH) {
/*      */       
/* 1041 */       if (pos.x < 0 || pos.y < 0)
/* 1042 */         return;  int newRowspan = Math.max(1, pos.y - control.yPosition);
/* 1043 */       if (control.canPlaceAt(control.yPosition, control.xPosition, newRowspan, control.colSpan, this))
/*      */       {
/* 1045 */         control.rowSpan = newRowspan;
/*      */       }
/*      */     }
/* 1048 */     else if (handle == DesignableGuiControl.Handle.NORTH) {
/*      */       
/* 1050 */       int newRowspan = Math.max(0, control.rowSpan - pos.y - control.yPosition);
/* 1051 */       if (control.canPlaceAt(pos.y, control.xPosition, newRowspan, control.colSpan, this)) {
/*      */         
/* 1053 */         control.yPosition = pos.y;
/* 1054 */         control.rowSpan = newRowspan;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRows() {
/* 1064 */     return this.rows;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMinRows() {
/* 1069 */     int minRows = 1;
/*      */     
/* 1071 */     for (DesignableGuiControl control : this.controls.values())
/*      */     {
/* 1073 */       minRows = Math.max(minRows, control.yPosition + control.rowSpan);
/*      */     }
/*      */     
/* 1076 */     return minRows;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRows(int rows) {
/* 1084 */     this.rows = Math.min(Math.max(getMinRows(), rows), 255);
/* 1085 */     updateColumnMetrics();
/*      */   }
/*      */ 
/*      */   
/*      */   public void addRow() {
/* 1090 */     if (this.rows < 32) setRows(this.rows + 1);
/*      */   
/*      */   }
/*      */   
/*      */   public void removeRow() {
/* 1095 */     setRows(this.rows - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColumns() {
/* 1103 */     return this.columns;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMinColumns() {
/* 1108 */     int minColumns = 1;
/*      */     
/* 1110 */     for (DesignableGuiControl control : this.controls.values())
/*      */     {
/* 1112 */       minColumns = Math.max(minColumns, control.xPosition + control.colSpan);
/*      */     }
/*      */     
/* 1115 */     return minColumns;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumns(int columns) {
/* 1123 */     this.columns = Math.min(Math.max(getMinColumns(), columns), 256);
/* 1124 */     updateColumnMetrics();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setColumnWidth(int column, int width) {
/* 1129 */     if (column > -1 && column < this.columns) {
/*      */       
/* 1131 */       this.columnMetrics[column] = width;
/* 1132 */       updateColumnMetrics();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addColumn() {
/* 1138 */     if (this.columns < 32) setColumns(this.columns + 1);
/*      */   
/*      */   }
/*      */   
/*      */   public void removeColumn() {
/* 1143 */     setColumns(this.columns - 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void selectColumn() {
/* 1148 */     this.editingColumn = this.mouseOverColumn;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSelectedColumn() {
/* 1153 */     return this.editingColumn;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSelectedColumnWidth() {
/* 1158 */     return (this.editingColumn > -1) ? this.columnMetrics[this.editingColumn] : 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSelectedColumnWidth(int width) {
/* 1163 */     setColumnWidth(this.editingColumn, width);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getSelectedColumnWidthText() {
/* 1168 */     if (this.editingColumn < 0) return "";
/*      */     
/* 1170 */     int columnWidth = getSelectedColumnWidth();
/* 1171 */     return (columnWidth > 0) ? String.valueOf(columnWidth) : "§8Auto";
/*      */   }
/*      */ 
/*      */   
/*      */   public void beginPlacingControl(String controlType) {
/* 1176 */     this.placingControlType = controlType;
/*      */     
/* 1178 */     if (controlType != null) {
/*      */       
/* 1180 */       boolean freeCell = false;
/*      */       
/* 1182 */       for (int row = 0; row < this.rows && !freeCell; row++) {
/*      */         
/* 1184 */         for (int col = 0; col < this.columns && !freeCell; col++) {
/*      */           
/* 1186 */           if (!isCellOccupied(row, col, (DesignableGuiControl)null))
/*      */           {
/* 1188 */             freeCell = true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1193 */       if (!freeCell) {
/*      */         
/* 1195 */         Log.info("No free cells");
/* 1196 */         this.placingControlType = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPlacingControlType() {
/* 1203 */     return this.placingControlType;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPlacingControl() {
/* 1208 */     return (this.placingControlType != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public DesignableGuiControl placeControl(Rectangle bounds, int mouseX, int mouseY, DesignableGuiControl copySource) {
/* 1213 */     if (this.placingControlType != null) {
/*      */       
/* 1215 */       String controlType = this.placingControlType;
/* 1216 */       this.placingControlType = null;
/*      */       
/* 1218 */       Point pos = getRowAndColumnAt(bounds, mouseX, mouseY);
/*      */       
/* 1220 */       DesignableGuiControl newControl = addControl(controlType, pos.y, pos.x);
/* 1221 */       if (newControl != null)
/*      */       {
/* 1223 */         newControl.copyPropertiesFrom(copySource);
/*      */       }
/* 1225 */       return newControl;
/*      */     } 
/*      */     
/* 1228 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public DesignableGuiControl addControl(String controlType, int row, int column) {
/* 1233 */     if (isCellOccupied(row, column, (DesignableGuiControl)null))
/*      */     {
/* 1235 */       return null;
/*      */     }
/*      */     
/* 1238 */     DesignableGuiControl control = this.manager.getControls().createControl(controlType);
/*      */     
/* 1240 */     control.setName(control.getDefaultControlName());
/* 1241 */     control.xPosition = column;
/* 1242 */     control.yPosition = row;
/* 1243 */     control.update();
/*      */     
/* 1245 */     this.controls.put(Integer.valueOf(control.id), control);
/*      */     
/* 1247 */     return control;
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeControl(int controlId) {
/* 1252 */     this.controls.remove(Integer.valueOf(controlId));
/*      */     
/* 1254 */     if (!this.macros.getLayoutManager().checkControlExistsInLayouts(controlId))
/*      */     {
/* 1256 */       this.manager.getControls().removeControl(controlId);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\DesignableGuiLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */