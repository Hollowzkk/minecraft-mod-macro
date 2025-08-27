/*     */ package net.eq2online.macros.gui.controls.specialised;
/*     */ 
/*     */ import bip;
/*     */ import bir;
/*     */ import buk;
/*     */ import bve;
/*     */ import com.mumfrey.liteloader.client.overlays.IGuiTextField;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.interfaces.IHighlighter;
/*     */ import rp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiTextFieldWithHighlight
/*     */   extends GuiTextFieldEx
/*     */ {
/*     */   private IHighlighter lastHighlighter;
/*     */   private String lastText;
/*     */   private String highlight;
/*     */   protected int m;
/*     */   
/*     */   public GuiTextFieldWithHighlight(int id, bip fontrenderer, int xPos, int yPos, int width, int height, String initialText) {
/*  26 */     super(id, fontrenderer, xPos, yPos, width, height, initialText.replace('§', '&'));
/*  27 */     this.h.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiTextFieldWithHighlight(int id, bip fontrenderer, int xPos, int yPos, int width, int height, int initialValue, int digits) {
/*  32 */     super(id, fontrenderer, xPos, yPos, width, height, initialValue, digits);
/*  33 */     this.h.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a() {
/*  42 */     this.m++;
/*  43 */     super.a();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b(boolean newFocused) {
/*  52 */     if (newFocused && !m())
/*     */     {
/*  54 */       this.m = 0;
/*     */     }
/*     */     
/*  57 */     super.b(newFocused);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHighlighter(IHighlighter highlighter) {
/*  62 */     this.lastHighlighter = highlighter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(String text) {
/*  68 */     super.a(text.replace('§', '&'));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void g() {
/*  77 */     drawHighlightTextBox(this.a, this.f, ((IGuiTextField)this).getInternalWidth(), ((IGuiTextField)this).getHeight(), this.lastHighlighter);
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
/*     */   protected void drawHighlightTextBox(int xPos, int yPos, int width, int height, IHighlighter highlighter) {
/*  90 */     if (j()) {
/*     */       
/*  92 */       a(xPos - 1, yPos - 1, xPos + width + 1, yPos + height + 1, -6250336);
/*  93 */       a(xPos, yPos, xPos + width, yPos + height, -16777216);
/*     */     } 
/*     */     
/*  96 */     boolean isEnabled = ((IGuiTextField)this).isEnabled();
/*  97 */     int scrollPos = ((IGuiTextField)this).getLineScrollOffset();
/*  98 */     int selectionStart = i();
/*  99 */     int selectionEnd = o();
/* 100 */     int enabledColour = ((IGuiTextField)this).getTextColor();
/* 101 */     int disabledColour = ((IGuiTextField)this).getDisabledTextColour();
/*     */     
/* 103 */     String text = rp.a(b());
/*     */     
/* 105 */     if (text != this.lastText || highlighter != this.lastHighlighter) {
/*     */       
/* 107 */       this.lastText = text;
/* 108 */       this.lastHighlighter = highlighter;
/* 109 */       this.highlight = highlighter.generateHighlightMask(text);
/*     */     } 
/*     */     
/* 112 */     int fontColour = isEnabled ? enabledColour : disabledColour;
/* 113 */     int adjustedSelectionPos = selectionStart - scrollPos;
/* 114 */     int adjustedSelectionEnd = selectionEnd - scrollPos;
/*     */     
/* 116 */     String trimmedText = this.h.a(text.substring(scrollPos), p());
/* 117 */     String trimmedHighlight = this.highlight.substring(scrollPos);
/*     */     
/* 119 */     boolean cursorVisible = (adjustedSelectionPos >= 0 && adjustedSelectionPos <= trimmedText.length());
/* 120 */     boolean drawCursor = (m() && this.m / 6 % 2 == 0 && cursorVisible);
/*     */     
/* 122 */     int textLeft = xPos + 4;
/* 123 */     int textTop = yPos + (height - 8) / 2;
/* 124 */     int currentDrawPosition = textLeft;
/*     */     
/* 126 */     if (adjustedSelectionEnd > trimmedText.length())
/*     */     {
/* 128 */       adjustedSelectionEnd = trimmedText.length();
/*     */     }
/*     */     
/* 131 */     if (trimmedText.length() > 0) {
/*     */       
/* 133 */       String beforeCursor = cursorVisible ? trimmedText.substring(0, adjustedSelectionPos) : trimmedText;
/* 134 */       currentDrawPosition = drawMaskedStringWithShadow(beforeCursor, trimmedHighlight, currentDrawPosition, textTop, fontColour);
/*     */     } 
/*     */     
/* 137 */     boolean adjustSelection = (selectionStart < text.length() || text.length() >= h());
/* 138 */     int cursorLocation = currentDrawPosition;
/* 139 */     this.cursorPos = cursorLocation;
/*     */     
/* 141 */     if (!cursorVisible) {
/*     */       
/* 143 */       cursorLocation = (adjustedSelectionPos <= 0) ? textLeft : (textLeft + width);
/*     */     }
/* 145 */     else if (adjustSelection) {
/*     */       
/* 147 */       cursorLocation--;
/* 148 */       currentDrawPosition--;
/*     */     } 
/*     */     
/* 151 */     if (trimmedText.length() > 0 && cursorVisible && adjustedSelectionPos < trimmedText.length() && adjustedSelectionPos >= 0)
/*     */     {
/* 153 */       currentDrawPosition = drawMaskedStringWithShadow(trimmedText.substring(adjustedSelectionPos), trimmedHighlight
/* 154 */           .substring(adjustedSelectionPos), currentDrawPosition, textTop, fontColour);
/*     */     }
/*     */     
/* 157 */     if (drawCursor)
/*     */     {
/* 159 */       if (adjustSelection) {
/*     */         
/* 161 */         bir.a(cursorLocation, textTop - 1, cursorLocation + 1, textTop + 1 + this.h.a, -3092272);
/*     */       }
/*     */       else {
/*     */         
/* 165 */         this.h.a("_", cursorLocation, textTop, fontColour);
/*     */       } 
/*     */     }
/*     */     
/* 169 */     if (adjustedSelectionPos > trimmedText.length())
/*     */     {
/* 171 */       adjustedSelectionPos = trimmedText.length();
/*     */     }
/*     */     
/* 174 */     if (adjustedSelectionEnd != adjustedSelectionPos) {
/*     */       
/* 176 */       int selectionWidth = textLeft + this.h.a(trimmedText.substring(0, adjustedSelectionEnd));
/* 177 */       drawInverseRect(cursorLocation, textTop - 1, selectionWidth - 1, textTop + 1 + this.h.a);
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
/*     */ 
/*     */   
/*     */   public int drawMaskedStringWithShadow(String text, String highlight, int xPosition, int yPosition, int colour) {
/* 194 */     StringBuilder highlightedText = new StringBuilder();
/*     */     
/* 196 */     char lastColour = 'z';
/*     */     
/* 198 */     for (int i = 0; i < text.length() && i < highlight.length(); i++) {
/*     */       
/* 200 */       char col = highlight.charAt(i);
/* 201 */       char c = text.charAt(i);
/*     */       
/* 203 */       if (col != lastColour) {
/*     */         
/* 205 */         lastColour = col;
/* 206 */         highlightedText.append('§').append(col);
/*     */       } 
/*     */       
/* 209 */       highlightedText.append(c);
/*     */     } 
/*     */     
/* 212 */     return this.h.a(highlightedText.toString(), xPosition, yPosition, colour);
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
/*     */   private void drawInverseRect(int x1, int y1, int x2, int y2) {
/* 225 */     if (x1 < x2) {
/*     */       
/* 227 */       int i = x1;
/* 228 */       x1 = x2;
/* 229 */       x2 = i;
/*     */     } 
/*     */     
/* 232 */     if (y1 < y2) {
/*     */       
/* 234 */       int j = y1;
/* 235 */       y1 = y2;
/* 236 */       y2 = j;
/*     */     } 
/*     */     
/* 239 */     bve tessellator = bve.a();
/* 240 */     buk buf = tessellator.c();
/* 241 */     GL.glColor4f(0.0F, 0.0F, 255.0F, 255.0F);
/* 242 */     GL.glDisableTexture2D();
/* 243 */     GL.glEnableColorLogic();
/* 244 */     GL.glLogicOp(5387);
/* 245 */     buf.a(7, GL.VF_POSITION);
/* 246 */     buf.b(x1, y2, 0.0D).d();
/* 247 */     buf.b(x2, y2, 0.0D).d();
/* 248 */     buf.b(x2, y1, 0.0D).d();
/* 249 */     buf.b(x1, y1, 0.0D).d();
/* 250 */     tessellator.b();
/* 251 */     GL.glDisableColorLogic();
/* 252 */     GL.glEnableTexture2D();
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\specialised\GuiTextFieldWithHighlight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */