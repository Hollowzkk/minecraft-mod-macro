/*     */ package net.eq2online.macros.gui.repl;
/*     */ 
/*     */ import bib;
/*     */ import bje;
/*     */ import blk;
/*     */ import blp;
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.base.Strings;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import com.mumfrey.liteloader.gl.GLClippingPlanes;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.MacroHighlighter;
/*     */ import net.eq2online.macros.core.MacroPlaybackType;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiTextFieldWithHighlight;
/*     */ import net.eq2online.macros.gui.interfaces.IContentRenderer;
/*     */ import net.eq2online.macros.gui.screens.GuiCommandReference;
/*     */ import net.eq2online.macros.gui.screens.GuiEditTextFile;
/*     */ import net.eq2online.macros.gui.screens.GuiScreenWithHeader;
/*     */ import net.eq2online.macros.input.IProhibitOverride;
/*     */ import net.eq2online.macros.interfaces.IChatEventListener;
/*     */ import net.eq2online.macros.interfaces.IHighlighter;
/*     */ import net.eq2online.macros.scripting.interfaces.IPromptOverridable;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.repl.IReplConsole;
/*     */ import net.eq2online.macros.scripting.repl.Repl;
/*     */ import net.eq2online.util.Util;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import rp;
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
/*     */ public class GuiMacroRepl
/*     */   extends GuiScreenWithHeader
/*     */   implements IReplConsole, IHighlighter, IPromptOverridable, IChatEventListener, ReplConsoleHistory.IHistoryTarget, blp, IProhibitOverride
/*     */ {
/*     */   private static final int PADDING = 4;
/*     */   private static final int LINE_HEIGHT = 9;
/*     */   private final Macros macros;
/*     */   private final GuiScreenEx parent;
/*     */   private final Repl repl;
/*     */   private final MacroHighlighter highlighter;
/*  58 */   private final List<String> output = new ArrayList<>();
/*     */   
/*     */   private final GuiTextFieldEx txtInput;
/*     */   
/*     */   private final ReplConsoleTabCompleter tabCompleter;
/*     */   
/*     */   private final ReplConsoleHistory history;
/*     */   
/*  66 */   private final List<String> accumulator = new ArrayList<>();
/*     */   
/*  68 */   private IReplConsole.ConsoleMode mode = IReplConsole.ConsoleMode.EXEC;
/*     */   
/*  70 */   private int editOffset = -1;
/*     */   
/*  72 */   private int editHoverOffset = -1;
/*     */   
/*     */   private int scrollPos;
/*     */ 
/*     */   
/*     */   public GuiMacroRepl(Macros macros, bib minecraft, GuiScreenEx parent) {
/*  78 */     super(minecraft, 0, 0);
/*     */     
/*  80 */     this.macros = macros;
/*  81 */     this.parent = parent;
/*  82 */     this.repl = new Repl(macros, minecraft, this);
/*  83 */     this.highlighter = macros.getHighlighter(MacroPlaybackType.ONESHOT);
/*     */     
/*  85 */     this.bgBottomMargin = 16;
/*  86 */     this.txtInput = (GuiTextFieldEx)new GuiTextFieldWithHighlight(0, this.q, 0, 0, 100, 10, "");
/*  87 */     this.tabCompleter = new ReplConsoleTabCompleter(this.repl, this, (bje)this.txtInput);
/*  88 */     this.history = macros.getReplConsoleHistory().connect(this);
/*     */     
/*  90 */     this.title = I18n.get("repl.title");
/*  91 */     this.banner = "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(String... newCompletions) {
/*  97 */     this.tabCompleter.a(newCompletions);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReplConsole.ConsoleMode getMode() {
/* 103 */     return this.mode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMode(IReplConsole.ConsoleMode mode) {
/* 109 */     if (this.mode != mode)
/*     */     {
/* 111 */       changeMode(mode);
/*     */     }
/*     */     
/* 114 */     this.accumulator.clear();
/* 115 */     this.editOffset = -1;
/* 116 */     this.mode = mode;
/*     */   }
/*     */ 
/*     */   
/*     */   private void changeMode(IReplConsole.ConsoleMode mode) {
/* 121 */     if (mode == IReplConsole.ConsoleMode.EXEC) {
/*     */       
/* 123 */       String script = Joiner.on('').join(this.accumulator).trim();
/* 124 */       if (!script.isEmpty())
/*     */       {
/* 126 */         this.repl.processCommand(script);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 134 */     return this.txtInput.b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/* 140 */     this.txtInput.a(text);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IContentRenderer getContentRenderer() {
/* 146 */     return (IContentRenderer)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void display() {
/* 152 */     this.j.a((blk)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLine(String line) {
/* 158 */     addLine(line, true);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addLine(String line, boolean beforeAppend) {
/* 163 */     if (this.tabCompleter != null)
/*     */     {
/* 165 */       this.tabCompleter.clear();
/*     */     }
/*     */     
/* 168 */     if (beforeAppend && this.mode == IReplConsole.ConsoleMode.APPEND && this.output.size() > 0) {
/*     */       
/* 170 */       int offset = this.output.size() - this.accumulator.size() - 1;
/* 171 */       this.output.add(offset, line);
/*     */     }
/*     */     else {
/*     */       
/* 175 */       this.output.add(line);
/*     */     } 
/*     */     
/* 178 */     this.scrollPos = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onChatMessage(String chatMessage, String chatMessageNoColours) {
/* 184 */     if (this.repl.isLive())
/*     */     {
/* 186 */       addLine(chatMessage);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSendChatMessage(String message) {
/* 193 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLogMessage(String message) {
/* 199 */     if (this.repl.isLive())
/*     */     {
/* 201 */       addLine(I18n.get("repl.return.log", new Object[] { message }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLineWrapped(String line) {
/* 208 */     List<String> parts = this.q.c(line, this.l - 8);
/* 209 */     for (String part : parts) {
/*     */       
/* 211 */       if (!part.isEmpty())
/*     */       {
/* 213 */         addLine(part);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(String text) {
/* 221 */     if (this.output.size() == 0) {
/*     */       
/* 223 */       addLine(text);
/*     */       
/*     */       return;
/*     */     } 
/* 227 */     int index = this.output.size() - 1;
/* 228 */     String line = this.output.get(index);
/* 229 */     line = line + text;
/* 230 */     this.output.set(index, line);
/* 231 */     this.scrollPos = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearScreen() {
/* 237 */     this.output.clear();
/* 238 */     this.scrollPos = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeConsole() {
/* 244 */     onCloseClick();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void editFile(String fileName) {
/* 250 */     this.j.a((blk)new GuiEditTextFile(this.macros, this.j, (GuiScreenEx)this, this.macros.getFile(fileName), ScriptContext.MAIN));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 256 */     Keyboard.enableRepeatEvents(true);
/* 257 */     super.b();
/* 258 */     this.macros.getChatHandler().registerListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(int width, int height) {
/* 264 */     this.l = width;
/* 265 */     this.m = height - 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disconnect() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 276 */     this.txtInput.a();
/* 277 */     this.repl.onTick(true);
/* 278 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, float partialTick) {
/* 284 */     GL.glDisableBlend();
/* 285 */     GL.glBlendFunc(770, 771);
/* 286 */     GL.glDisableAlphaTest();
/*     */     
/* 288 */     GL.glPushMatrix();
/* 289 */     GL.glTranslatef(0.0F, -20.0F, 0.0F);
/*     */     
/* 291 */     int offset = 0;
/* 292 */     a(2 + offset, 22, this.l - 2 + offset, this.m - this.bgBottomMargin, this.backColour);
/*     */     
/* 294 */     drawOutput(mouseX, mouseY, partialTick);
/*     */     
/* 296 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTick) {
/* 302 */     this.repl.onTick(false);
/*     */     
/* 304 */     super.a(mouseX, mouseY, partialTick);
/* 305 */     this.promptBarStart = 2;
/* 306 */     this.promptBarEnd = this.l - 2;
/* 307 */     drawPromptBar(mouseX, mouseY, partialTick, 65280, -1342177280);
/* 308 */     drawOutput(mouseX, mouseY, partialTick);
/* 309 */     drawScrollIndicator(mouseX, mouseY, partialTick);
/* 310 */     drawPrompt(mouseX, mouseY, partialTick);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawPrompt(int mouseX, int mouseY, float partialTick) {
/* 315 */     this.txtInput.a(false);
/* 316 */     this.txtInput.b(true);
/* 317 */     if (this.repl.isReady()) {
/*     */       
/* 319 */       int offset = (this.mode == IReplConsole.ConsoleMode.APPEND) ? 22 : 9;
/* 320 */       this.q.a(this.mode.getPrompt(), 4, this.m - 12, 16755200);
/* 321 */       this.txtInput.drawTextBox(offset, this.m - 13, this.l - 13 - offset, 10, this);
/*     */     }
/*     */     else {
/*     */       
/* 325 */       this.q.a(rp.a(this.mode.getPrompt()), 4, this.m - 12, 5592405);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawOutput(int mouseX, int mouseY, float partialTick) {
/* 331 */     GLClippingPlanes.glEnableClipping(GLClippingPlanes.Plane.TOP, 24);
/* 332 */     int yPos = this.m - 16;
/* 333 */     int startPos = this.output.size() - this.scrollPos;
/* 334 */     this.editHoverOffset = -1;
/*     */     
/* 336 */     for (int i = startPos; i > 0; i--) {
/*     */       
/* 338 */       int offset = this.output.size() - i;
/* 339 */       int yPos2 = yPos;
/* 340 */       String line = this.output.get(i - 1);
/* 341 */       String cursor = (this.repl.isBlocked() && this.scrollPos == 0 && i == startPos && this.updateCounter / 6 % 2 == 0) ? "§f_" : "";
/* 342 */       List<String> parts = this.q.c(line, this.l - 8);
/* 343 */       if (parts.size() < 2) {
/*     */         
/* 345 */         yPos -= 9;
/* 346 */         drawRowHighlight(mouseX, mouseY, yPos, yPos2, offset, 0);
/* 347 */         this.q.a(applyRowHighlight(offset, line) + cursor, 4, yPos, -1);
/*     */       }
/*     */       else {
/*     */         
/* 351 */         yPos -= 9 * parts.size();
/* 352 */         drawRowHighlight(mouseX, mouseY, yPos, yPos2, offset, 1);
/* 353 */         for (int p = 0; p < parts.size(); p++) {
/*     */           
/* 355 */           String part = applyRowHighlight(offset, (String)parts.get(p) + ((p == parts.size() - 1) ? cursor : ""));
/* 356 */           this.q.a(part, 4, yPos + p * 9, -1);
/*     */         } 
/*     */       } 
/*     */       
/* 360 */       if (yPos < 24) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 365 */     GLClippingPlanes.glDisableClipping();
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawScrollIndicator(int mouseX, int mouseY, float partialTick) {
/* 370 */     float availableHeight = (this.m - 16 - 22);
/* 371 */     float scrollPct = Math.min(availableHeight / 9.0F / this.output.size(), 1.0F);
/* 372 */     float scrollPos = Math.min(Math.max((this.output.size() > 0.0F) ? (this.scrollPos / (this.output.size() - 10.1F)) : 0.0F, 0.0F), 1.0F);
/* 373 */     float indicatorSize = Math.max(scrollPct * (availableHeight - 2.0F), 8.0F);
/* 374 */     int indicatorPos = (int)((this.m - 16 - 1) - scrollPos * (availableHeight - 2.0F - indicatorSize));
/* 375 */     a(this.l - 5, indicatorPos - (int)indicatorSize, this.l - 3, indicatorPos, -2130706433);
/*     */   }
/*     */ 
/*     */   
/*     */   private String applyRowHighlight(int offset, String part) {
/* 380 */     if (this.editOffset == offset && this.mode == IReplConsole.ConsoleMode.APPEND)
/*     */     {
/* 382 */       part = "§b" + rp.a(part);
/*     */     }
/* 384 */     return part;
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawRowHighlight(int mouseX, int mouseY, int yPos, int yPos2, int offset, int overlap) {
/* 389 */     if (this.mode != IReplConsole.ConsoleMode.APPEND) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 394 */     if (this.editOffset == offset) {
/*     */       
/* 396 */       int bgColour = -16777012;
/* 397 */       a(10, yPos - 1, this.l - 3, yPos2 - overlap, bgColour);
/* 398 */       a(3, yPos - 1, 10, this.m - 13, bgColour);
/* 399 */       a(3, this.m - 13, this.l - 3, this.m - 3, bgColour);
/*     */     }
/* 401 */     else if (mouseX > 4 && mouseY > yPos && mouseY <= yPos2 && offset < this.accumulator.size()) {
/*     */       
/* 403 */       a(4, yPos - 1, this.l - 4, yPos2 - overlap, -2143264560);
/* 404 */       this.editHoverOffset = offset;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 411 */     if (this.mode == IReplConsole.ConsoleMode.APPEND && this.editHoverOffset > -1)
/*     */     {
/* 413 */       beginEdit();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void beginEdit() {
/* 419 */     if (this.editHoverOffset != this.editOffset && this.accumulator.size() > 0) {
/*     */       
/* 421 */       this.editOffset = this.editHoverOffset;
/* 422 */       if (this.editOffset == -1) {
/*     */         
/* 424 */         this.txtInput.a("");
/*     */       }
/*     */       else {
/*     */         
/* 428 */         int offset = this.accumulator.size() - 1 - this.editOffset;
/* 429 */         this.txtInput.a(this.accumulator.get(offset));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isEditing() {
/* 436 */     return (this.editOffset > -1 && this.mode == IReplConsole.ConsoleMode.APPEND);
/*     */   }
/*     */ 
/*     */   
/*     */   private void applyEdit(String line) {
/* 441 */     int offset = this.accumulator.size() - 1 - this.editOffset;
/* 442 */     this.accumulator.set(offset, line);
/* 443 */     synchroniseEdit(line);
/* 444 */     this.editOffset = -1;
/* 445 */     applyIndent(this.accumulator.get(this.accumulator.size() - 1));
/*     */   }
/*     */ 
/*     */   
/*     */   private void cancelEdit() {
/* 450 */     synchroniseEdit(this.accumulator.get(this.accumulator.size() - 1 - this.editOffset));
/* 451 */     this.editOffset = -1;
/* 452 */     this.txtInput.a("");
/*     */   }
/*     */ 
/*     */   
/*     */   private void synchroniseEdit(String line) {
/* 457 */     this.output.set(this.output.size() - 1 - this.editOffset, this.mode.getPrompt() + highlight(line));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) throws IOException {
/* 463 */     mouseWheelDelta /= 30;
/* 464 */     setScrollPos(this.scrollPos + mouseWheelDelta);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setScrollPos(int scrollPos) {
/* 469 */     this.scrollPos = Math.max(Math.min(Math.max(scrollPos, 0), this.output.size() - 10), 0);
/* 470 */     if (isEditing())
/*     */     {
/* 472 */       cancelEdit();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) throws IOException {
/* 479 */     if (this.repl.keyTyped(keyChar, keyCode)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 484 */     if (keyCode == 1) {
/*     */       
/* 486 */       cancel();
/*     */       
/*     */       return;
/*     */     } 
/* 490 */     if (keyCode == 59) {
/*     */       
/* 492 */       this.j.a((blk)new GuiCommandReference(this.j, (blk)this));
/*     */       
/*     */       return;
/*     */     } 
/* 496 */     if (keyCode == 200) {
/*     */       
/* 498 */       this.tabCompleter.clearSuggestion();
/* 499 */       this.history.up();
/*     */       
/*     */       return;
/*     */     } 
/* 503 */     if (keyCode == 208) {
/*     */       
/* 505 */       this.tabCompleter.clearSuggestion();
/* 506 */       this.history.down();
/*     */       
/*     */       return;
/*     */     } 
/* 510 */     if (keyCode == 209) {
/*     */       
/* 512 */       setScrollPos(this.scrollPos - 4);
/*     */       
/*     */       return;
/*     */     } 
/* 516 */     if (keyCode == 201) {
/*     */       
/* 518 */       setScrollPos(this.scrollPos + 4);
/*     */       
/*     */       return;
/*     */     } 
/* 522 */     this.tabCompleter.d();
/* 523 */     this.history.cancel();
/*     */     
/* 525 */     if (keyCode == 15) {
/*     */       
/* 527 */       this.tabCompleter.generateSuggestion();
/*     */       
/*     */       return;
/*     */     } 
/* 531 */     this.tabCompleter.c();
/*     */     
/* 533 */     if (blk.e(keyCode)) {
/*     */       
/* 535 */       String clipboardText = blk.o();
/* 536 */       if (clipboardText.indexOf('\n') > -1) {
/*     */         
/* 538 */         pasteMultiline(clipboardText);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 543 */     if (keyCode == 28) {
/*     */       
/* 545 */       submit();
/*     */       
/*     */       return;
/*     */     } 
/* 549 */     this.txtInput.a(keyChar, keyCode);
/*     */     
/* 551 */     if (isEditing())
/*     */     {
/* 553 */       synchroniseEdit(this.txtInput.b());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void pasteMultiline(String clipboardText) {
/* 559 */     if (!this.txtInput.b().isEmpty())
/*     */     {
/* 561 */       submit();
/*     */     }
/*     */     
/* 564 */     String[] lines = clipboardText.split("\\r?\\n");
/* 565 */     IReplConsole.ConsoleMode mode = this.mode;
/*     */     
/* 567 */     if (mode == IReplConsole.ConsoleMode.EXEC)
/*     */     {
/* 569 */       submit("begin");
/*     */     }
/*     */     
/* 572 */     for (String line : lines) {
/*     */       
/* 574 */       if (!Strings.isNullOrEmpty(line))
/*     */       {
/* 576 */         submit(line);
/*     */       }
/*     */     } 
/*     */     
/* 580 */     if (mode == IReplConsole.ConsoleMode.EXEC)
/*     */     {
/* 582 */       submit("end");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void cancel() {
/* 588 */     if (isEditing()) {
/*     */       
/* 590 */       cancelEdit();
/*     */       
/*     */       return;
/*     */     } 
/* 594 */     if (this.txtInput.b().length() > 0) {
/*     */       
/* 596 */       this.txtInput.a("");
/* 597 */       this.tabCompleter.clearSuggestion();
/* 598 */       this.history.cancel();
/*     */       
/*     */       return;
/*     */     } 
/* 602 */     if (this.mode == IReplConsole.ConsoleMode.APPEND) {
/*     */       
/* 604 */       this.mode = IReplConsole.ConsoleMode.EXEC;
/* 605 */       addLine(I18n.get("repl.console.mode.cancelled"));
/*     */       
/*     */       return;
/*     */     } 
/* 609 */     onCloseClick();
/*     */   }
/*     */ 
/*     */   
/*     */   private void submit() {
/* 614 */     String line = this.txtInput.b();
/* 615 */     this.txtInput.a("");
/* 616 */     submit(line);
/*     */   }
/*     */ 
/*     */   
/*     */   private void submit(String line) {
/* 621 */     if (line.length() > 0)
/*     */     {
/* 623 */       this.history.add(line);
/*     */     }
/*     */     
/* 626 */     String outputLine = this.mode.getPrompt() + highlight(line);
/* 627 */     switch (this.mode) {
/*     */       
/*     */       case APPEND:
/* 630 */         if (this.editOffset > -1) {
/*     */           
/* 632 */           applyEdit(line);
/*     */           
/*     */           return;
/*     */         } 
/* 636 */         if ("end".equalsIgnoreCase(line.trim())) {
/*     */           
/* 638 */           addLine(this.mode.getPrompt().trim() + " " + highlight(line), false);
/* 639 */           this.repl.processCommand(line.trim());
/*     */           return;
/*     */         } 
/* 642 */         if ("begin".equals(line.trim())) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 647 */         addLine(outputLine, false);
/*     */         
/* 649 */         this.accumulator.add(line);
/* 650 */         applyIndent(line);
/*     */         break;
/*     */       
/*     */       case EXEC:
/* 654 */         addLine(outputLine);
/* 655 */         if (line.length() > 0)
/*     */         {
/* 657 */           this.repl.processCommand(line);
/*     */         }
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyIndent(String line) {
/* 668 */     Matcher indentMatcher = Pattern.compile("^(\\s+)(.+)$").matcher(line);
/* 669 */     if (indentMatcher.matches())
/*     */     {
/* 671 */       this.txtInput.a(indentMatcher.group(1));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String generateHighlightMask(String text) {
/* 678 */     return Util.stringToHighlightMask(highlight(text));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String highlight(String text) {
/* 684 */     if (text.matches("^/[a-zA-Z].*"))
/*     */     {
/* 686 */       return text;
/*     */     }
/*     */     
/* 689 */     Matcher matcher = this.tabCompleter.getCommandRegex().matcher(text);
/* 690 */     if (matcher.find())
/*     */     {
/* 692 */       return matcher.replaceFirst("§a" + matcher.group() + "§f");
/*     */     }
/*     */     
/* 695 */     return this.highlighter.highlightInteractive(text, '6', 'd', '7', 'f');
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCloseClick() {
/* 701 */     Keyboard.enableRepeatEvents(false);
/* 702 */     this.j.a((blk)this.parent);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void m() {
/* 708 */     this.macros.getChatHandler().unregisterListener(this);
/* 709 */     Keyboard.enableRepeatEvents(false);
/* 710 */     super.m();
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\repl\GuiMacroRepl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */