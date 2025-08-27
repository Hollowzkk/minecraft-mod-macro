/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bip;
/*     */ import bje;
/*     */ import com.mumfrey.liteloader.client.overlays.IGuiTextField;
/*     */ import net.eq2online.macros.interfaces.IHighlighter;
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
/*     */ public class GuiTextFieldEx
/*     */   extends bje
/*     */ {
/*     */   public String allowedCharacters;
/*  23 */   public int minStringLength = 0;
/*     */   
/*     */   protected final bip h;
/*     */   
/*  27 */   protected int cursorPos = 0;
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
/*     */   public GuiTextFieldEx(int id, bip fontrenderer, int xPos, int yPos, int width, int height, String initialText, String allowedCharacters, int maxStringLength) {
/*  43 */     super(id, fontrenderer, xPos, yPos, width, height);
/*  44 */     this.allowedCharacters = allowedCharacters;
/*  45 */     f(maxStringLength);
/*  46 */     a(initialText);
/*  47 */     this.h = fontrenderer;
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
/*     */   public GuiTextFieldEx(int id, bip fontrenderer, int xPos, int yPos, int width, int height, String initialText) {
/*  63 */     super(id, fontrenderer, xPos, yPos, width, height);
/*  64 */     f(65536);
/*  65 */     a(initialText);
/*  66 */     this.h = fontrenderer;
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
/*     */   public GuiTextFieldEx(int id, bip fontrenderer, int xPos, int yPos, int width, int height, int initialValue, int digits) {
/*  82 */     super(id, fontrenderer, xPos, yPos, width, height);
/*  83 */     f(digits);
/*  84 */     a(String.valueOf(initialValue));
/*  85 */     this.allowedCharacters = "0123456789";
/*  86 */     this.h = fontrenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(char keyChar, int keyCode) {
/*  92 */     if (this.allowedCharacters == null || this.allowedCharacters.indexOf(keyChar) >= 0 || keyCode == 203 || keyCode == 205 || keyCode == 199 || keyCode == 207 || keyCode == 211 || keyCode == 14 || keyChar == '\003' || keyChar == '\026' || keyChar == '\030')
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  98 */       return super.a(keyChar, keyCode);
/*     */     }
/*     */     
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void f() {
/*     */     try {
/* 112 */       super.f();
/*     */     }
/* 114 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSizeAndPosition(int xPos, int yPos, int width, int height) {
/* 119 */     setPosition(xPos, yPos);
/* 120 */     setSize(width, height);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(int width, int height) {
/* 125 */     ((IGuiTextField)this).setInternalWidth(width);
/* 126 */     ((IGuiTextField)this).setHeight(height);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPosition(int xPos, int yPos) {
/* 131 */     ((IGuiTextField)this).setXPosition(xPos);
/* 132 */     ((IGuiTextField)this).setYPosition(yPos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void scrollToEnd() {
/* 137 */     e(0);
/* 138 */     e(b().length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e(int pos) {
/* 147 */     super.e(pos);
/* 148 */     super.e(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCursorLocation() {
/* 153 */     return this.cursorPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public void insertText(String appendCode) {
/* 158 */     if (m())
/*     */     {
/* 160 */       b(appendCode);
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
/*     */   public void drawTextBox(int xPos, int yPos, int width, int height, IHighlighter highlighter) {
/* 176 */     setSizeAndPosition(xPos, yPos, width, height);
/*     */     
/* 178 */     drawHighlightTextBox(xPos, yPos, width, height, highlighter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawHighlightTextBox(int xPos2, int yPos2, int width2, int height2, IHighlighter highlighter) {
/* 183 */     g();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTextBoxAt(int yPos) {
/*     */     try {
/* 190 */       ((IGuiTextField)this).setYPosition(yPos);
/* 191 */       g();
/*     */     }
/* 193 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int drawString(bip fontrenderer, String s, int x, int y, int width, int colour) {
/* 202 */     Boolean cursorOffset = Boolean.valueOf(false);
/*     */     
/* 204 */     if (!s.endsWith("_")) {
/*     */       
/* 206 */       s = s + "_";
/* 207 */       cursorOffset = Boolean.valueOf(true);
/*     */     } 
/*     */     
/* 210 */     int stringWidth = fontrenderer.a(s);
/*     */     
/* 212 */     if (stringWidth <= width - 4) {
/*     */       
/* 214 */       if (cursorOffset.booleanValue()) s = s.substring(0, s.length() - 1); 
/* 215 */       fontrenderer.a(s, x, y, colour);
/* 216 */       width = stringWidth;
/*     */     }
/*     */     else {
/*     */       
/* 220 */       String trimmedText = s;
/* 221 */       int w = fontrenderer.a(trimmedText);
/*     */       
/* 223 */       while (w > width - 4 && trimmedText.length() > 0) {
/*     */         
/* 225 */         trimmedText = trimmedText.substring(1);
/* 226 */         w = fontrenderer.a(trimmedText);
/*     */       } 
/*     */       
/* 229 */       String stub = s.substring(0, s.length() - trimmedText.length());
/* 230 */       int lastColourCodePos = stub.lastIndexOf('§');
/*     */       
/* 232 */       if (lastColourCodePos >= 0)
/*     */       {
/* 234 */         trimmedText = s.substring(lastColourCodePos, lastColourCodePos + 2) + trimmedText;
/*     */       }
/*     */       
/* 237 */       if (cursorOffset.booleanValue()) trimmedText = trimmedText.substring(0, trimmedText.length() - 1); 
/* 238 */       fontrenderer.a(trimmedText, x, y, colour);
/*     */     } 
/*     */ 
/*     */     
/* 242 */     return width;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValueInt(int defaultValue) {
/*     */     try {
/* 249 */       return Integer.parseInt(b());
/*     */     }
/* 251 */     catch (Exception ex) {
/*     */       
/* 253 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void b(String text) {
/* 260 */     String filteredText = filterAllowedCharacters(text);
/* 261 */     super.b(filteredText);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String filterAllowedCharacters(String text) {
/* 271 */     if (this.allowedCharacters == null) return text;
/*     */     
/* 273 */     StringBuilder filteredString = new StringBuilder();
/* 274 */     char[] charArray = text.toCharArray();
/* 275 */     int stringLength = charArray.length;
/*     */     
/* 277 */     for (int i = 0; i < stringLength; i++) {
/*     */       
/* 279 */       char charAt = charArray[i];
/*     */       
/* 281 */       if (this.allowedCharacters.indexOf(charAt) > -1)
/*     */       {
/* 283 */         filteredString.append(charAt);
/*     */       }
/*     */     } 
/*     */     
/* 287 */     return filteredString.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\GuiTextFieldEx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */