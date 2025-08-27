/*      */ package net.eq2online.macros.gui.controls;
/*      */ 
/*      */ import bib;
/*      */ import bip;
/*      */ import blk;
/*      */ import com.mumfrey.liteloader.gl.GL;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import net.eq2online.console.Log;
/*      */ import net.eq2online.macros.gui.GuiControlEx;
/*      */ import net.eq2online.macros.interfaces.IHighlighter;
/*      */ import net.eq2online.macros.rendering.FontRendererTextEditor;
/*      */ import net.eq2online.macros.res.ResourceLocations;
/*      */ import net.eq2online.macros.scripting.IDocumentationEntry;
/*      */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GuiTextEditor
/*      */   extends GuiControlEx
/*      */ {
/*      */   static class Selection
/*      */   {
/*      */     private boolean valid;
/*      */     private boolean active;
/*      */     int startRow;
/*      */     int startCol;
/*      */     int endRow;
/*      */     int endCol;
/*      */     int row;
/*      */     int col;
/*      */     
/*      */     boolean isValid() {
/*   46 */       return this.valid;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isActive() {
/*   51 */       return this.active;
/*      */     }
/*      */ 
/*      */     
/*      */     void invalidate() {
/*   56 */       this.valid = false;
/*      */     }
/*      */ 
/*      */     
/*      */     void update() {
/*   61 */       if (this.startCol == this.endCol && this.startRow == this.endRow)
/*      */       {
/*   63 */         this.active = false;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     void clear() {
/*   69 */       this.valid = false;
/*   70 */       this.active = false;
/*      */     }
/*      */ 
/*      */     
/*      */     void set(int row, int col) {
/*   75 */       this.row = row;
/*   76 */       this.col = col;
/*   77 */       set(row, col, row, col);
/*      */     }
/*      */ 
/*      */     
/*      */     void set(int startRow, int startCol, int endRow, int endCol) {
/*   82 */       setStart(startRow, startCol);
/*   83 */       setEnd(endRow, endCol);
/*   84 */       this.valid = true;
/*   85 */       this.active = (startRow != endRow || startCol != endCol);
/*      */     }
/*      */ 
/*      */     
/*      */     void startDrag(int row, int col) {
/*   90 */       this.active = true;
/*   91 */       setStart(row, col);
/*   92 */       setEnd(this.row, this.col);
/*      */     }
/*      */ 
/*      */     
/*      */     void endDrag(int row, int col) {
/*   97 */       this.active = true;
/*   98 */       setStart(this.row, this.col);
/*   99 */       setEnd(row, col);
/*      */     }
/*      */ 
/*      */     
/*      */     void setStart(int row, int col) {
/*  104 */       this.startRow = row;
/*  105 */       this.startCol = col;
/*      */     }
/*      */ 
/*      */     
/*      */     void setEnd(int row, int col) {
/*  110 */       this.endRow = row;
/*  111 */       this.endCol = col;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class DocumentationPopup
/*      */   {
/*      */     final IDocumentationEntry documentation;
/*      */     final int xPos;
/*      */     final int yPos;
/*      */     
/*      */     DocumentationPopup(ScriptContext context, String actionName, int xPos, int yPos) {
/*  123 */       this.documentation = context.getDocumentor().getDocumentation(actionName);
/*  124 */       this.xPos = xPos;
/*  125 */       this.yPos = yPos;
/*      */     }
/*      */ 
/*      */     
/*      */     void draw(bip fontRenderer, int cursorX) {
/*  130 */       if (this.documentation != null)
/*      */       {
/*  132 */         this.documentation.drawAt(fontRenderer, cursorX - this.xPos, this.yPos);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*  137 */   private static final Pattern PATTERN_SCRIPTACTION = Pattern.compile("([a-z\\_]+)\\([^\\)]*$", 2);
/*      */ 
/*      */   
/*      */   private static final int CTRL_A = 1;
/*      */ 
/*      */   
/*      */   private static final int CTRL_C = 3;
/*      */ 
/*      */   
/*      */   private static final int CTRL_K = 11;
/*      */ 
/*      */   
/*      */   private static final int CTRL_V = 22;
/*      */ 
/*      */   
/*      */   private static final int CTRL_X = 24;
/*      */ 
/*      */   
/*      */   private static final int CHAR_WIDTH = 7;
/*      */ 
/*      */   
/*      */   private static final int ROW_HEIGHT = 10;
/*      */ 
/*      */   
/*      */   private static FontRendererTextEditor editorFontRenderer;
/*      */ 
/*      */   
/*      */   private final IHighlighter highlighter;
/*      */ 
/*      */   
/*      */   private final boolean highlight;
/*      */   
/*      */   private final ScriptContext context;
/*      */   
/*      */   private final GuiScrollBar vScrollBar;
/*      */   
/*      */   private final GuiScrollBar hScrollBar;
/*      */   
/*  175 */   private final List<String> text = new ArrayList<>();
/*      */   
/*  177 */   private final Selection selection = new Selection();
/*      */ 
/*      */ 
/*      */   
/*      */   private File file;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fileChanged = false;
/*      */ 
/*      */ 
/*      */   
/*  189 */   private int rows = 0, cols = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  194 */   private int cursorRow = 0; private int cursorCol = 0;
/*      */   
/*      */   private boolean dragging = false;
/*      */   
/*      */   private boolean showDocs = true;
/*      */   
/*  200 */   private DocumentationPopup lastDocPopup = null;
/*      */   
/*  202 */   private int docPopupDelay = 0;
/*      */   
/*      */   private boolean useDarkColours = false;
/*      */   
/*  206 */   private int textColour = -16777216;
/*      */   
/*  208 */   private int commentColour = -16751104;
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
/*      */   public GuiTextEditor(bib minecraft, int id, int xPos, int yPos, int width, int height, IHighlighter highlighter, boolean highlight, ScriptContext context, boolean showDocs) {
/*  223 */     super(minecraft, id, xPos, yPos, width, height, null);
/*      */     
/*  225 */     if (editorFontRenderer == null)
/*      */     {
/*  227 */       editorFontRenderer = new FontRendererTextEditor(minecraft, ResourceLocations.FIXEDWIDTHFONT, 7);
/*      */     }
/*      */     
/*  230 */     this.highlighter = highlighter;
/*  231 */     this.highlight = highlight;
/*  232 */     this.context = context;
/*      */     
/*  234 */     this.rows = (this.g - 20) / 10;
/*  235 */     this.cols = (this.f - 20) / 7;
/*      */     
/*  237 */     this.vScrollBar = new GuiScrollBar(minecraft, id, this.h + this.f - 20, this.i - 1, 20, height + 2, 0, 0, GuiScrollBar.ScrollBarOrientation.VERTICAL);
/*      */     
/*  239 */     this.hScrollBar = new GuiScrollBar(minecraft, id, this.h - 2, this.i + this.g - 19, width - 18, 20, 0, 0, GuiScrollBar.ScrollBarOrientation.HORIZONTAL);
/*      */ 
/*      */     
/*  242 */     this.showDocs = showDocs;
/*  243 */     setUseDarkColours(false);
/*      */     
/*  245 */     update(false);
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
/*      */   public void setSizeAndPosition(int left, int top, int controlWidth, int controlHeight) {
/*  258 */     this.h = left;
/*  259 */     this.i = top;
/*  260 */     this.f = controlWidth;
/*  261 */     this.g = controlHeight;
/*      */     
/*  263 */     this.rows = (this.g - 20) / 10;
/*  264 */     this.cols = (this.f - 20) / 7;
/*      */     
/*  266 */     this.vScrollBar.setSizeAndPosition(this.h + this.f - 20, this.i - 1, 20, controlHeight + 2);
/*  267 */     this.hScrollBar.setSizeAndPosition(this.h - 2, this.i + this.g - 19, controlWidth - 18, 20);
/*      */     
/*  269 */     update(false);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isChanged() {
/*  274 */     return this.fileChanged;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isHighlighting() {
/*  279 */     return this.highlight;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isHelpVisible() {
/*  284 */     return this.showDocs;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHelpVisible(boolean showCommandHelp) {
/*  289 */     this.showDocs = showCommandHelp;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isUsingDarkColours() {
/*  294 */     return this.useDarkColours;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUseDarkColours(boolean useDarkColours) {
/*  299 */     this.useDarkColours = useDarkColours;
/*      */     
/*  301 */     if (this.useDarkColours) {
/*      */       
/*  303 */       editorFontRenderer.setDarkColours();
/*  304 */       this.textColour = -5592406;
/*  305 */       this.commentColour = -13382605;
/*      */     }
/*      */     else {
/*      */       
/*  309 */       editorFontRenderer.setDefaultColours();
/*  310 */       this.textColour = -16777216;
/*  311 */       this.commentColour = -16751104;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String getActionUnderCursor() {
/*  317 */     if (this.lastDocPopup == null)
/*      */     {
/*  319 */       return null;
/*      */     }
/*      */     
/*  322 */     return this.lastDocPopup.documentation.isHidden() ? null : this.lastDocPopup.documentation.getName();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  327 */     if (this.docPopupDelay > 0)
/*      */     {
/*  329 */       this.docPopupDelay--;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void update(boolean canWrap) {
/*  339 */     this.updateCounter = 0;
/*  340 */     int totalLines = this.text.size();
/*      */     
/*  342 */     if (totalLines == 0) {
/*      */       
/*  344 */       this.text.add("");
/*  345 */       totalLines = 1;
/*  346 */       this.cursorRow = 0;
/*  347 */       this.cursorCol = 0;
/*      */     } 
/*      */ 
/*      */     
/*  351 */     if (this.cursorRow < 0) {
/*      */       
/*  353 */       this.cursorRow = 0;
/*      */     }
/*  355 */     else if (this.cursorRow >= totalLines) {
/*      */       
/*  357 */       this.cursorRow = totalLines - 1;
/*      */     } 
/*      */     
/*  360 */     if (canWrap) {
/*      */       
/*  362 */       if (this.cursorCol == ((String)this.text.get(this.cursorRow)).length() + 1) {
/*      */         
/*  364 */         this.cursorRow++;
/*  365 */         this.cursorCol = 0;
/*  366 */         update(false);
/*      */         return;
/*      */       } 
/*  369 */       if (this.cursorCol == -1) {
/*      */         
/*  371 */         this.cursorRow--;
/*  372 */         this.cursorCol = Integer.MAX_VALUE;
/*  373 */         update(false);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  379 */     if (this.cursorCol < 0) {
/*      */       
/*  381 */       this.cursorCol = 0;
/*      */     }
/*  383 */     else if (this.cursorCol > ((String)this.text.get(this.cursorRow)).length()) {
/*      */       
/*  385 */       this.cursorCol = ((String)this.text.get(this.cursorRow)).length();
/*      */     } 
/*      */     
/*  388 */     if (this.cursorRow >= this.vScrollBar.getValue() + this.rows)
/*      */     {
/*  390 */       this.vScrollBar.setValue(this.cursorRow - this.rows + 1);
/*      */     }
/*      */     
/*  393 */     if (this.cursorRow < this.vScrollBar.getValue())
/*      */     {
/*  395 */       this.vScrollBar.setValue(this.cursorRow);
/*      */     }
/*      */     
/*  398 */     if (this.cursorCol >= this.hScrollBar.getValue() + this.cols)
/*      */     {
/*  400 */       this.hScrollBar.setValue(this.cursorCol - this.cols + 1);
/*      */     }
/*      */     
/*  403 */     if (this.cursorCol < this.hScrollBar.getValue())
/*      */     {
/*  405 */       this.hScrollBar.setValue(this.cursorCol);
/*      */     }
/*      */     
/*  408 */     int maxLineLength = 0;
/*      */     
/*  410 */     for (int lineNumber = 0; lineNumber < this.text.size(); lineNumber++)
/*      */     {
/*  412 */       maxLineLength = Math.max(maxLineLength, ((String)this.text.get(lineNumber)).length());
/*      */     }
/*      */     
/*  415 */     this.vScrollBar.setMax(Math.max(totalLines - this.rows, 0));
/*  416 */     this.hScrollBar.setMax(Math.max(maxLineLength - this.cols + 1, 0));
/*      */     
/*  418 */     this.selection.update();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  426 */     this.file = null;
/*  427 */     this.fileChanged = false;
/*  428 */     this.text.clear();
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
/*      */   public boolean load(File textFile) {
/*  440 */     this.file = textFile;
/*  441 */     this.fileChanged = false;
/*      */     
/*  443 */     this.text.clear();
/*      */     
/*  445 */     if (this.file != null && this.file.exists()) {
/*      */       
/*      */       try {
/*      */         
/*  449 */         BufferedReader bufferedreader = new BufferedReader(new FileReader(this.file));
/*      */         
/*  451 */         for (String fileLine = ""; (fileLine = bufferedreader.readLine()) != null; ) {
/*      */           
/*  453 */           while (fileLine.indexOf('\t') > -1) {
/*      */             
/*  455 */             String head = fileLine.substring(0, fileLine.indexOf('\t'));
/*  456 */             String tail = fileLine.substring(fileLine.indexOf('\t') + 1);
/*  457 */             fileLine = head + getTabCharsForColumn(head.length()) + tail;
/*      */           } 
/*      */           
/*  460 */           this.text.add(fileLine);
/*      */         } 
/*      */         
/*  463 */         bufferedreader.close();
/*      */       }
/*  465 */       catch (Exception e) {
/*      */         
/*  467 */         this.text.clear();
/*  468 */         update(false);
/*  469 */         return false;
/*      */       } 
/*      */     }
/*      */     
/*  473 */     update(false);
/*  474 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setText(String newText) {
/*  479 */     this.text.clear();
/*  480 */     this.fileChanged = false;
/*      */     
/*  482 */     if (newText != null) {
/*      */ 
/*      */       
/*  485 */       String[] lines = newText.split("\\r?\\n");
/*      */       
/*  487 */       for (String line : lines) {
/*      */         
/*  489 */         while (line.indexOf('\t') > -1) {
/*      */           
/*  491 */           String head = line.substring(0, line.indexOf('\t'));
/*  492 */           String tail = line.substring(line.indexOf('\t') + 1);
/*  493 */           line = head + getTabCharsForColumn(head.length()) + tail;
/*      */         } 
/*      */         
/*  496 */         this.text.add(line);
/*      */       } 
/*      */     } 
/*      */     
/*  500 */     update(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getText() {
/*  511 */     StringBuilder textBuilder = new StringBuilder();
/*      */     
/*  513 */     for (int lineNumber = 0; lineNumber < this.text.size(); lineNumber++)
/*      */     {
/*  515 */       textBuilder.append(this.text.get(lineNumber)).append("\n");
/*      */     }
/*      */     
/*  518 */     this.fileChanged = false;
/*  519 */     return textBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean save() {
/*  529 */     this.fileChanged = false;
/*      */     
/*  531 */     if (this.file != null)
/*      */     {
/*  533 */       return save(this.file);
/*      */     }
/*      */     
/*  536 */     return false;
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
/*      */   public boolean save(File textFile) {
/*      */     try {
/*  549 */       PrintWriter printwriter = new PrintWriter(new FileWriter(textFile));
/*      */       
/*  551 */       for (int lineNumber = 0; lineNumber < this.text.size(); lineNumber++)
/*      */       {
/*  553 */         printwriter.println(this.text.get(lineNumber));
/*      */       }
/*      */       
/*  556 */       printwriter.close();
/*  557 */       this.fileChanged = false;
/*  558 */       return true;
/*      */     }
/*  560 */     catch (Exception exception) {
/*      */       
/*  562 */       Log.info("Failed to save file: {0}", new Object[] { textFile.getName() });
/*  563 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void alterSelection() {
/*  569 */     if (isShiftDown()) {
/*      */       
/*  571 */       alterSelection(this.cursorRow, this.cursorCol);
/*      */     }
/*      */     else {
/*      */       
/*  575 */       this.selection.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void alterSelection(int row, int col) {
/*  581 */     if (this.selection.isValid()) {
/*      */       
/*  583 */       if (row < this.selection.row || (row == this.selection.row && col < this.selection.col))
/*      */       {
/*  585 */         this.selection.startDrag(row, col);
/*      */       }
/*  587 */       else if (row > this.selection.row || (row == this.selection.row && col >= this.selection.col))
/*      */       {
/*  589 */         this.selection.endDrag(row, col);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  594 */       this.selection.set(row, col);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void deleteSelection() {
/*  600 */     if (!this.selection.isActive())
/*      */       return; 
/*  602 */     int startRow = this.selection.startRow;
/*  603 */     int startCol = this.selection.startCol;
/*      */     
/*  605 */     if (startRow < this.selection.endRow && startCol > 0) {
/*      */       
/*  607 */       this.text.set(startRow, ((String)this.text.get(startRow)).substring(0, startCol));
/*  608 */       startCol = 0;
/*  609 */       startRow++;
/*      */     } 
/*      */ 
/*      */     
/*  613 */     while (startRow < this.selection.endRow) {
/*      */       
/*  615 */       this.text.remove(startRow);
/*  616 */       this.selection.endRow--;
/*      */     } 
/*      */     
/*  619 */     String rowText = this.text.get(this.selection.endRow);
/*      */     
/*  621 */     if (this.selection.endCol > -1)
/*      */     {
/*  623 */       if (startRow > this.selection.startRow) {
/*      */         
/*  625 */         this.text.set(this.selection.startRow, (String)this.text.get(this.selection.startRow) + rowText.substring(this.selection.endCol));
/*  626 */         this.text.remove(this.selection.endRow);
/*      */       }
/*      */       else {
/*      */         
/*  630 */         String prepend = (startCol > 0) ? rowText.substring(0, startCol) : "";
/*  631 */         this.text.set(this.selection.endRow, prepend + rowText.substring(this.selection.endCol));
/*      */       } 
/*      */     }
/*      */     
/*  635 */     moveCursorTo(this.selection.startRow, this.selection.startCol);
/*  636 */     update(false);
/*  637 */     this.selection.clear();
/*      */   }
/*      */   
/*      */   private void indentSelection(boolean shift) {
/*      */     int i, j, k;
/*  642 */     if (!this.selection.isActive())
/*      */       return; 
/*  644 */     boolean shiftedTop = false;
/*  645 */     boolean shiftedBottom = false;
/*  646 */     boolean shiftedCurrent = false;
/*      */     
/*  648 */     for (int rowIndex = this.selection.startRow; rowIndex <= this.selection.endRow; rowIndex++) {
/*      */       
/*  650 */       String rowText = this.text.get(rowIndex);
/*      */       
/*  652 */       if (shift) {
/*      */         
/*  654 */         if (rowText.startsWith("    "))
/*      */         {
/*  656 */           i = shiftedTop | ((rowIndex == this.selection.startRow) ? 1 : 0);
/*  657 */           j = shiftedBottom | ((rowIndex == this.selection.endRow) ? 1 : 0);
/*  658 */           k = shiftedCurrent | ((rowIndex == this.cursorRow) ? 1 : 0);
/*  659 */           this.text.set(rowIndex, rowText.substring(4));
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  664 */       else if (rowIndex < this.selection.endRow || this.selection.endCol > 0) {
/*      */         
/*  666 */         this.text.set(rowIndex, "    " + rowText);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  671 */     if (shift) {
/*      */       
/*  673 */       if (k != 0) this.cursorCol -= 4; 
/*  674 */       if (i != 0) this.selection.startCol -= 4; 
/*  675 */       if (j != 0) this.selection.endCol -= 4;
/*      */       
/*  677 */       if (this.cursorCol < 0) this.cursorCol = 0; 
/*  678 */       if (this.selection.startCol < 0) this.selection.startCol = 0; 
/*  679 */       if (this.selection.endCol < 0) this.selection.endCol = 0;
/*      */     
/*      */     } else {
/*      */       
/*  683 */       this.cursorCol += 4;
/*  684 */       this.selection.startCol += 4;
/*  685 */       if (this.selection.endCol > 0)
/*      */       {
/*  687 */         this.selection.endCol += 4;
/*      */       }
/*      */     } 
/*      */     
/*  691 */     update(false);
/*      */   }
/*      */ 
/*      */   
/*      */   private void copySelection() {
/*  696 */     if (!this.selection.isActive()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  701 */     blk.e(getSelectedText());
/*      */   }
/*      */ 
/*      */   
/*      */   private void cutSelection() {
/*  706 */     if (!this.selection.isActive()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  711 */     blk.e(getSelectedText());
/*  712 */     deleteSelection();
/*      */   }
/*      */ 
/*      */   
/*      */   private void paste() {
/*  717 */     String pasteText = blk.o();
/*      */     
/*  719 */     if (pasteText.length() > 0) {
/*      */       
/*  721 */       deleteSelection();
/*      */       
/*  723 */       String currentLine = this.text.get(this.cursorRow);
/*  724 */       String tail = currentLine.substring(this.cursorCol);
/*  725 */       String head = currentLine.substring(0, this.cursorCol);
/*  726 */       this.text.remove(this.cursorRow);
/*      */       
/*  728 */       if (pasteText.endsWith("\n"))
/*      */       {
/*  730 */         pasteText = pasteText + "§";
/*      */       }
/*      */       
/*  733 */       String[] pasteLines = pasteText.split("\\r?\\n");
/*      */       
/*  735 */       if (pasteText.endsWith("\n§"))
/*      */       {
/*  737 */         pasteLines[pasteLines.length - 1] = "";
/*      */       }
/*      */       
/*  740 */       pasteLines[0] = head + pasteLines[0];
/*  741 */       pasteLines[pasteLines.length - 1] = pasteLines[pasteLines.length - 1] + tail;
/*      */       
/*  743 */       for (String pasteLine : pasteLines) {
/*      */         
/*  745 */         this.text.add(this.cursorRow, tabsToSpaces(pasteLine));
/*  746 */         this.cursorRow++;
/*      */       } 
/*      */       
/*  749 */       this.cursorRow--;
/*  750 */       this.cursorCol = ((String)this.text.get(this.cursorRow)).length() - tail.length();
/*  751 */       update(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private String getSelectedText() {
/*  757 */     String selectedText = "";
/*      */     
/*  759 */     if (this.selection.startRow == this.selection.endRow) {
/*      */       
/*  761 */       selectedText = selectedText + ((String)this.text.get(this.selection.startRow)).substring(this.selection.startCol, this.selection.endCol);
/*      */     }
/*      */     else {
/*      */       
/*  765 */       selectedText = selectedText + ((String)this.text.get(this.selection.startRow)).substring(this.selection.startCol) + "\n";
/*      */       
/*  767 */       for (int rowIndex = this.selection.startRow + 1; rowIndex < this.selection.endRow; rowIndex++)
/*      */       {
/*  769 */         selectedText = selectedText + (String)this.text.get(rowIndex) + "\n";
/*      */       }
/*      */       
/*  772 */       if (this.selection.endCol > -1)
/*      */       {
/*  774 */         selectedText = selectedText + ((String)this.text.get(this.selection.endRow)).substring(0, this.selection.endCol);
/*      */       }
/*      */     } 
/*      */     
/*  778 */     return selectedText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveCursorBy(int deltaRows, int deltaColumns, boolean updateSelection) {
/*  789 */     if (updateSelection) {
/*      */       
/*  791 */       alterSelection();
/*      */     }
/*      */     else {
/*      */       
/*  795 */       this.selection.clear();
/*      */     } 
/*      */     
/*  798 */     this.cursorRow += deltaRows;
/*  799 */     this.cursorCol += deltaColumns;
/*  800 */     update(true);
/*      */     
/*  802 */     if (updateSelection)
/*      */     {
/*  804 */       alterSelection();
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
/*      */   public void moveCursorTo(int row, int column) {
/*  816 */     alterSelection();
/*  817 */     if (row > -1) this.cursorRow = row; 
/*  818 */     if (column > -1) this.cursorCol = column; 
/*  819 */     update(false);
/*  820 */     alterSelection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void scroll(int deltaRows) {
/*  830 */     this.vScrollBar.setValue(this.vScrollBar.getValue() + deltaRows);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void keyTyped(char keyChar, int keyCode) {
/*  841 */     if (!handleCursorKey(keyCode))
/*      */     {
/*  843 */       handleCharacterKey(keyChar, keyCode);
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
/*      */   private boolean handleCursorKey(int keyCode) {
/*  856 */     switch (keyCode) {
/*      */       case 1:
/*  858 */         return true;
/*  859 */       case 200: moveCursorBy(-1, 0, true); return true;
/*  860 */       case 208: moveCursorBy(1, 0, true); return true;
/*  861 */       case 203: moveCursorBy(0, -1, true); return true;
/*  862 */       case 205: moveCursorBy(0, 1, true); return true;
/*  863 */       case 199: moveCursorTo(-1, 0); return true;
/*  864 */       case 207: moveCursorTo(-1, ((String)this.text.get(this.cursorRow)).length()); return true;
/*  865 */       case 201: scroll(-1 * this.rows); moveCursorBy(-1 * this.rows, 0, true); return true;
/*  866 */       case 209: scroll(this.rows); moveCursorBy(this.rows, 0, true); return true;
/*  867 */     }  return false;
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
/*      */   private void handleCharacterKey(char keyChar, int keyCode) {
/*  879 */     if (keyChar > '\000' || keyCode == 211) {
/*      */       
/*  881 */       this.fileChanged = true;
/*      */       
/*  883 */       this.selection.update();
/*      */       
/*  885 */       if (keyChar == '\001') {
/*      */         
/*  887 */         moveCursorTo(this.text.size(), 2147483647);
/*  888 */         this.selection.set(0, 0, this.cursorRow, this.cursorCol);
/*      */         return;
/*      */       } 
/*  891 */       if (keyChar == '\026') {
/*      */         
/*  893 */         paste();
/*      */         return;
/*      */       } 
/*  896 */       if (keyChar == '\013')
/*      */       {
/*  898 */         keyChar = '§';
/*      */       }
/*      */       
/*  901 */       if (this.selection.isActive()) {
/*      */         
/*  903 */         if (keyChar == '\003') {
/*      */           
/*  905 */           copySelection();
/*      */           return;
/*      */         } 
/*  908 */         if (keyChar == '\030') {
/*      */           
/*  910 */           cutSelection();
/*      */           
/*      */           return;
/*      */         } 
/*  914 */         if (keyCode == 14 || keyCode == 211)
/*      */         {
/*  916 */           deleteSelection();
/*      */         }
/*  918 */         else if (keyCode == 15)
/*      */         {
/*  920 */           indentSelection(isShiftDown());
/*      */         }
/*  922 */         else if (keyChar > '\037')
/*      */         {
/*  924 */           deleteSelection();
/*  925 */           handleCharacterKey(keyChar, keyCode);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  930 */         String currentLine = this.text.get(this.cursorRow);
/*  931 */         String tail = currentLine.substring(this.cursorCol);
/*  932 */         String head = currentLine.substring(0, this.cursorCol);
/*      */         
/*  934 */         if (keyCode == 28 || keyCode == 156) {
/*      */           
/*  936 */           this.text.set(this.cursorRow, head);
/*  937 */           this.text.add(this.cursorRow + 1, tail);
/*  938 */           moveCursorTo(this.cursorRow + 1, 0);
/*  939 */           update(true);
/*      */         }
/*  941 */         else if (keyCode == 14) {
/*      */           
/*  943 */           if (head.length() > 0)
/*      */           {
/*  945 */             this.text.set(this.cursorRow, head.substring(0, head.length() - 1) + tail);
/*  946 */             moveCursorBy(0, -1, false);
/*      */           }
/*  948 */           else if (this.cursorRow > 0)
/*      */           {
/*  950 */             String oldText = this.text.get(this.cursorRow - 1);
/*  951 */             this.text.set(this.cursorRow - 1, oldText + tail);
/*  952 */             this.text.remove(this.cursorRow);
/*  953 */             this.cursorCol = 0;
/*  954 */             moveCursorBy(-1, oldText.length(), false);
/*      */           }
/*      */         
/*  957 */         } else if (keyCode == 15) {
/*      */           
/*  959 */           String tabChars = getTabCharsForColumn(this.cursorCol);
/*  960 */           this.text.set(this.cursorRow, head + tabChars + tail);
/*  961 */           moveCursorBy(0, tabChars.length(), false);
/*      */         }
/*  963 */         else if (keyCode == 211) {
/*      */           
/*  965 */           if (tail.length() > 0)
/*      */           {
/*  967 */             this.text.set(this.cursorRow, head + tail.substring(1));
/*  968 */             update(false);
/*      */           }
/*  970 */           else if (this.cursorRow < this.text.size() - 1)
/*      */           {
/*  972 */             this.text.set(this.cursorRow, head + (String)this.text.get(this.cursorRow + 1));
/*  973 */             this.text.remove(this.cursorRow + 1);
/*      */           }
/*      */         
/*  976 */         } else if (keyChar > '\037') {
/*      */           
/*  978 */           this.text.set(this.cursorRow, head + keyChar + tail);
/*  979 */           moveCursorBy(0, 1, false);
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
/*      */   protected void a(bib minecraft, int mouseX, int mouseY) {
/*  995 */     if (this.dragging) {
/*      */       
/*  997 */       this.cursorRow = (mouseY - this.i) / 10 + this.vScrollBar.getValue();
/*  998 */       this.cursorCol = (mouseX - this.h) / 7 + this.hScrollBar.getValue();
/*  999 */       update(false);
/* 1000 */       alterSelection(this.cursorRow, this.cursorCol);
/*      */     } 
/*      */     
/* 1003 */     super.a(minecraft, mouseX, mouseY);
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
/*      */   public void a(int mouseX, int mouseY) {
/* 1015 */     this.dragging = false;
/* 1016 */     this.selection.update();
/* 1017 */     if (!this.selection.isActive())
/*      */     {
/* 1019 */       moveCursorTo(this.cursorRow, this.cursorCol);
/*      */     }
/*      */     
/* 1022 */     this.vScrollBar.a(mouseX, mouseY);
/* 1023 */     this.hScrollBar.a(mouseX, mouseY);
/*      */     
/* 1025 */     super.a(mouseX, mouseY);
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
/*      */   public boolean b(bib minecraft, int mouseX, int mouseY) {
/* 1039 */     if (super.b(minecraft, mouseX, mouseY)) {
/*      */       
/* 1041 */       if (!this.vScrollBar.b(minecraft, mouseX, mouseY) && !this.hScrollBar.b(minecraft, mouseX, mouseY)) {
/*      */         
/* 1043 */         this.cursorRow = (mouseY - this.i) / 10 + this.vScrollBar.getValue();
/* 1044 */         this.cursorCol = (mouseX - this.h) / 7 + this.hScrollBar.getValue();
/* 1045 */         this.dragging = true;
/* 1046 */         update(false);
/* 1047 */         this.selection.set(this.cursorRow, this.cursorCol);
/*      */       } 
/*      */       
/* 1050 */       return true;
/*      */     } 
/*      */     
/* 1053 */     return false;
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
/*      */   public void drawControl(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/* 1066 */     a(minecraft, mouseX, mouseY);
/*      */ 
/*      */     
/* 1069 */     minecraft.N().a(ResourceLocations.GUI_PARTS);
/* 1070 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1071 */     this.renderer.setTexMapSize(128);
/* 1072 */     int u = this.useDarkColours ? 18 : 0;
/* 1073 */     this.renderer.drawTessellatedModalBorderRect(this.h - 2, this.i - 1, this.h + this.f - 20, this.i + this.g - 20, u, 0, u + 18, 18, 8);
/*      */ 
/*      */     
/* 1076 */     DocumentationPopup documentation = drawRows(minecraft, mouseY, mouseY);
/* 1077 */     int cursorX = drawCursor(minecraft, mouseY, mouseY);
/*      */     
/* 1079 */     this.vScrollBar.drawControl(minecraft, mouseX, mouseY, partialTicks);
/* 1080 */     this.hScrollBar.drawControl(minecraft, mouseX, mouseY, partialTicks);
/*      */     
/* 1082 */     if (this.showDocs)
/*      */     {
/* 1084 */       drawDocumentationPopup(mouseX, mouseY, documentation, cursorX);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private DocumentationPopup drawRows(bib minecraft, int mouseX, int mouseY) {
/* 1090 */     DocumentationPopup documentation = null;
/* 1091 */     int rowY = this.i + 1;
/*      */ 
/*      */     
/* 1094 */     for (int row = this.vScrollBar.getValue(); row < this.text.size() && row < this.vScrollBar.getValue() + this.rows; row++) {
/*      */       
/* 1096 */       String rowText = this.text.get(row);
/*      */       
/* 1098 */       if (this.selection.isValid() && this.selection.endRow == row) {
/*      */         
/* 1100 */         if (this.selection.endCol > rowText.length())
/*      */         {
/* 1102 */           this.selection.endCol = rowText.length();
/*      */         }
/* 1104 */         rowText = rowText.substring(0, this.selection.endCol) + '￾' + rowText.substring(this.selection.endCol);
/*      */       } 
/*      */       
/* 1107 */       if (this.selection.isValid() && this.selection.startRow == row) {
/*      */         
/* 1109 */         if (this.selection.startCol > rowText.length())
/*      */         {
/* 1111 */           this.selection.startCol = rowText.length();
/*      */         }
/*      */         
/* 1114 */         rowText = (this.selection.startCol > -1) ? (rowText.substring(0, this.selection.startCol) + Character.MAX_VALUE + rowText.substring(this.selection.startCol)) : (Character.MAX_VALUE + rowText);
/*      */       } 
/*      */ 
/*      */       
/* 1118 */       if (this.selection.isValid() && this.selection.startRow < row && this.selection.endRow >= row)
/*      */       {
/* 1120 */         rowText = Character.MAX_VALUE + rowText;
/*      */       }
/*      */       
/* 1123 */       if (this.showDocs && row == this.cursorRow && this.cursorCol > 1) {
/*      */         
/* 1125 */         String beforeCursor = rowText.substring(0, this.cursorCol);
/* 1126 */         Matcher beforeCursorMatch = PATTERN_SCRIPTACTION.matcher(beforeCursor);
/*      */         
/* 1128 */         if (beforeCursorMatch.find()) {
/*      */           
/* 1130 */           String actionName = beforeCursorMatch.group(1);
/* 1131 */           if (this.context.getAction(actionName) != null)
/*      */           {
/*      */             
/* 1134 */             documentation = new DocumentationPopup(this.context, actionName, beforeCursorMatch.group().length() * 7, rowY + 10);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1139 */       if (rowText.length() >= this.hScrollBar.getValue()) {
/*      */         
/* 1141 */         int colour = this.textColour;
/*      */         
/* 1143 */         if (this.highlight && this.highlighter != null)
/*      */         {
/* 1145 */           rowText = this.highlighter.highlight(rowText);
/*      */         }
/*      */         
/* 1148 */         boolean isComment = (this.highlight && rowText.trim().startsWith("//"));
/* 1149 */         if (this.highlight && isComment) colour = this.commentColour; 
/* 1150 */         editorFontRenderer.renderFixedWidthString(rowText, this.h, rowY, colour, !isComment, this.hScrollBar.getValue(), this.cols);
/*      */       } 
/*      */ 
/*      */       
/* 1154 */       rowY += 10;
/*      */     } 
/*      */     
/* 1157 */     return documentation;
/*      */   }
/*      */ 
/*      */   
/*      */   private int drawCursor(bib minecraft, int mouseX, int mouseY) {
/* 1162 */     int cursorX = this.h + (this.cursorCol - this.hScrollBar.getValue()) * 7;
/* 1163 */     boolean flashState = (this.updateCounter / 6 % 2 == 0);
/* 1164 */     if (flashState) {
/*      */       
/* 1166 */       boolean cursorColVisible = (this.cursorCol >= this.hScrollBar.getValue() && this.cursorCol < this.hScrollBar.getValue() + this.cols);
/* 1167 */       boolean cursorRowVisible = (this.cursorRow >= this.vScrollBar.getValue() && this.cursorRow < this.vScrollBar.getValue() + this.rows);
/* 1168 */       if (cursorColVisible && cursorRowVisible) {
/*      */         
/* 1170 */         int cursorY = this.i + (this.cursorRow - this.vScrollBar.getValue()) * 10;
/*      */         
/* 1172 */         GL.glEnableColorLogic();
/* 1173 */         GL.glLogicOp(5382);
/* 1174 */         a(cursorX, cursorY, cursorX + 7, cursorY + 10, -5592406);
/* 1175 */         GL.glDisableColorLogic();
/*      */       } 
/*      */     } 
/* 1178 */     return cursorX;
/*      */   }
/*      */ 
/*      */   
/*      */   private void drawDocumentationPopup(int mouseX, int mouseY, DocumentationPopup documentation, int cursorX) {
/* 1183 */     if (documentation == null || this.lastDocPopup == null || documentation.documentation != this.lastDocPopup.documentation)
/*      */     {
/* 1185 */       this.docPopupDelay = 10;
/*      */     }
/*      */     
/* 1188 */     this.lastDocPopup = documentation;
/*      */     
/* 1190 */     if (documentation != null && this.docPopupDelay == 0)
/*      */     {
/* 1192 */       documentation.draw(this.mc.k, cursorX);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static String tabsToSpaces(String text) {
/* 1198 */     while (text.indexOf('\t') > -1) {
/*      */       
/* 1200 */       int col = text.indexOf('\t');
/* 1201 */       text = text.substring(0, col) + getTabCharsForColumn(col) + text.substring(col + 1);
/*      */     } 
/*      */     
/* 1204 */     return text;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getTabCharsForColumn(int col) {
/* 1215 */     int tabChars = col % 4;
/* 1216 */     return (tabChars == 0) ? "    " : ((tabChars == 1) ? "   " : ((tabChars == 2) ? "  " : " "));
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isShiftDown() {
/* 1221 */     return (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
/*      */   }
/*      */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\GuiTextEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */