/*     */ package net.eq2online.macros.gui.layout;
/*     */ 
/*     */ import bib;
/*     */ import bip;
/*     */ import bir;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.MacroPlaybackType;
/*     */ import net.eq2online.macros.core.MacroTriggerType;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.interfaces.IEditablePanel;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import net.eq2online.util.Colour;
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
/*     */ public class LayoutButton
/*     */   extends LayoutWidget
/*     */ {
/*     */   public static class Colours
/*     */   {
/*     */     public static final int BORDER_DEFAULT = -8355712;
/*     */     public static final int BACKGROUND_DEFAULT = 0;
/*     */     public static final int BORDER_HOVER = -1;
/*     */     public static final int BORDER_COPY = -16711936;
/*     */     public static final int BORDER_MOVE = -16711681;
/*     */     public static final int BORDER_DELETE = -65536;
/*     */     public static final int BORDER_EDIT = -256;
/*     */     public static final int BORDER_EDITSELECTED = -103;
/*     */     public static final int RESERVED_BACKGROUND = 872349696;
/*     */     public static final int RESERVED_FOREGROUND = -65536;
/*  48 */     public int foreground = LayoutWidget.COLOUR_UNBOUND;
/*  49 */     public int background = 0;
/*  50 */     public int border = -8355712;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private static String[] nameOverrides = new String[10000];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static int[] symbolOverrides = new int[10000];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private static int[] widthOverrides = new int[10000];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   private static int[] heightOverrides = new int[10000]; private final int textOffsetX;
/*     */   private final int textOffsetY;
/*     */   
/*     */   static {
/*  82 */     nameOverrides[1] = "ESC"; nameOverrides[12] = "-"; nameOverrides[13] = "="; nameOverrides[14] = "<-"; nameOverrides[26] = "[";
/*  83 */     nameOverrides[27] = "]"; nameOverrides[28] = "RTN"; nameOverrides[29] = "CTRL"; nameOverrides[39] = ";"; nameOverrides[40] = "#";
/*  84 */     nameOverrides[41] = "'"; nameOverrides[42] = "#"; nameOverrides[43] = "\\"; nameOverrides[51] = ","; nameOverrides[52] = ".";
/*  85 */     nameOverrides[53] = "/"; nameOverrides[54] = "#"; nameOverrides[55] = "[*]"; nameOverrides[56] = "ALT"; nameOverrides[58] = "CAPS";
/*  86 */     nameOverrides[69] = "NUM"; nameOverrides[70] = "SCRL"; nameOverrides[71] = "[7]"; nameOverrides[72] = "[8]"; nameOverrides[73] = "[9]";
/*  87 */     nameOverrides[74] = "[-]"; nameOverrides[75] = "[4]"; nameOverrides[76] = "[5]"; nameOverrides[77] = "[6]"; nameOverrides[78] = "[+]";
/*  88 */     nameOverrides[79] = "[1]"; nameOverrides[80] = "[2]"; nameOverrides[81] = "[3]"; nameOverrides[82] = "[0]"; nameOverrides[83] = "[.]";
/*  89 */     nameOverrides[209] = "PGDN"; nameOverrides[201] = "PGUP"; nameOverrides[181] = "[/]"; nameOverrides[210] = "INS"; nameOverrides[211] = "DEL";
/*  90 */     nameOverrides[207] = "END"; nameOverrides[200] = "#"; nameOverrides[203] = "#"; nameOverrides[208] = "#"; nameOverrides[205] = "#";
/*  91 */     nameOverrides[156] = "ENT"; nameOverrides[157] = "CTRL";
/*     */     
/*  93 */     symbolOverrides[42] = 30; symbolOverrides[54] = 30; symbolOverrides[156] = 17; symbolOverrides[200] = 24; symbolOverrides[203] = 27;
/*  94 */     symbolOverrides[205] = 26; symbolOverrides[208] = 25; symbolOverrides[248] = 24; symbolOverrides[249] = 25;
/*     */     
/*  96 */     widthOverrides[14] = 36; widthOverrides[28] = 28; widthOverrides[42] = 16; widthOverrides[54] = 52; widthOverrides[57] = 112;
/*  97 */     widthOverrides[55] = 32; widthOverrides[181] = 32; widthOverrides[199] = 32; widthOverrides[210] = 32; widthOverrides[211] = 32;
/*  98 */     widthOverrides[207] = 32; widthOverrides[200] = 32; widthOverrides[203] = 32; widthOverrides[205] = 32; widthOverrides[208] = 32;
/*  99 */     widthOverrides[15] = 24; widthOverrides[69] = 32; widthOverrides[71] = 32; widthOverrides[72] = 32; widthOverrides[73] = 32;
/* 100 */     widthOverrides[74] = 32; widthOverrides[75] = 32; widthOverrides[76] = 32; widthOverrides[77] = 32; widthOverrides[78] = 32;
/* 101 */     widthOverrides[79] = 32; widthOverrides[80] = 32; widthOverrides[81] = 32; widthOverrides[82] = 68; widthOverrides[83] = 32;
/* 102 */     widthOverrides[156] = 32; widthOverrides[248] = 10; widthOverrides[249] = 10;
/*     */     
/* 104 */     heightOverrides[28] = 28; heightOverrides[78] = 30; heightOverrides[156] = 30;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean centreText;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int u;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int v;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LayoutButton(Macros macros, bib minecraft, bip fontRenderer, int id, String name, int width, int height, boolean centreAlign) {
/* 127 */     this(macros, minecraft, fontRenderer, id, name, width, height, 0, centreAlign, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LayoutButton(Macros macros, bib minecraft, bip fontRenderer, int id, String name, int width, int height, int textOffsetX, boolean centreAlign, boolean centreText) {
/* 133 */     super(macros, minecraft, fontRenderer, name, width, height, centreAlign);
/*     */     
/* 135 */     this.id = id;
/* 136 */     this.text = (nameOverrides[id] == null) ? this.name : nameOverrides[id];
/* 137 */     this.centreText = (centreText && symbolOverrides[this.id] == 0);
/*     */ 
/*     */     
/* 140 */     if (heightOverrides[id] > 0)
/*     */     {
/* 142 */       this.height = heightOverrides[id];
/*     */     }
/*     */ 
/*     */     
/* 146 */     this.textOffsetX = textOffsetX;
/* 147 */     this.textOffsetY = this.height / 2 - 4;
/*     */ 
/*     */     
/* 150 */     if (width > 0) {
/*     */       
/* 152 */       this.width = width;
/*     */     }
/* 154 */     else if (widthOverrides[id] > 0) {
/*     */       
/* 156 */       this.width = widthOverrides[id];
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 161 */       this.width = Math.max(height, fontRenderer.a(this.text) + this.textOffsetY * 2);
/* 162 */       this.width += 4 - width % 4;
/*     */     } 
/*     */ 
/*     */     
/* 166 */     this.u = symbolOverrides[id] % 16 * 16;
/* 167 */     this.v = symbolOverrides[id] / 16 * 16;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 177 */     return "{" + this.id + "," + this.xPosition + "," + this.yPosition + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleReservedState() {
/* 186 */     if (MacroTriggerType.KEY.supportsId(this.id)) {
/*     */       
/* 188 */       this.macros.getSettings().toggleReservedKeyState(this.id);
/* 189 */       this.macros.save();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Colours getColours(IEditablePanel.EditMode mode, boolean selected, boolean denied, boolean mouseOver) {
/* 195 */     Colours colour = new Colours();
/*     */     
/* 197 */     boolean special = this.macros.isReservedKey(this.id);
/* 198 */     boolean global = this.macros.isMacroGlobal(this.id, false);
/*     */     
/* 200 */     if (mode == IEditablePanel.EditMode.RESERVE) {
/*     */       
/* 202 */       if ((this.macros.getSettings()).reservedKeys.contains(Integer.valueOf(this.id)))
/*     */       {
/* 204 */         colour.foreground = -65536;
/* 205 */         colour.border = -65536;
/* 206 */         colour.background = 872349696;
/*     */ 
/*     */       
/*     */       }
/* 210 */       else if (special)
/*     */       {
/* 212 */         colour.border = LayoutWidget.COLOUR_SPECIAL;
/* 213 */         colour.foreground = LayoutWidget.COLOUR_SPECIAL;
/*     */       }
/*     */     
/*     */     }
/* 217 */     else if (denied) {
/*     */       
/* 219 */       colour.foreground = LayoutWidget.COLOUR_DENIED;
/*     */     }
/* 221 */     else if (selected) {
/*     */       
/* 223 */       colour.foreground = LayoutWidget.COLOUR_SELECTED;
/*     */     }
/* 225 */     else if (global) {
/*     */       
/* 227 */       colour.foreground = LayoutWidget.COLOUR_BOUNDGLOBAL;
/*     */     }
/* 229 */     else if (isBound()) {
/*     */       
/* 231 */       colour.foreground = special ? LayoutWidget.COLOUR_BOUNDSPECIAL : LayoutWidget.COLOUR_BOUND;
/*     */     }
/* 233 */     else if (special) {
/*     */       
/* 235 */       colour.foreground = LayoutWidget.COLOUR_SPECIAL;
/*     */     } 
/*     */     
/* 238 */     if (mouseOver) {
/*     */ 
/*     */       
/* 241 */       colour.border = -1;
/*     */     }
/* 243 */     else if (mode == IEditablePanel.EditMode.EDIT_ALL) {
/*     */ 
/*     */       
/* 246 */       colour.border = selected ? -103 : -256;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 251 */     else if (isBound()) {
/*     */       
/* 253 */       switch (mode) {
/*     */         case COPY:
/* 255 */           colour.border = -16711936; break;
/* 256 */         case MOVE: colour.border = -16711681; break;
/* 257 */         case DELETE: colour.border = -65536;
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 262 */     return colour;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(LayoutPanelStandard parent, Rectangle boundingBox, int mouseX, int mouseY, IEditablePanel.EditMode mode, boolean selected, boolean denied) {
/* 268 */     boolean mouseOver = mouseOver((Rectangle)null, mouseX, mouseY, selected);
/* 269 */     Colours colours = getColours(mode, selected, denied, mouseOver);
/* 270 */     int xPos = parent.getXPosition() + this.drawX;
/* 271 */     int yPos = parent.getYPosition() + this.yPosition;
/* 272 */     int width = this.width;
/*     */     
/* 274 */     String trimmedText = this.fontRenderer.a(this.text, width - this.textOffsetX - 8);
/* 275 */     if (trimmedText.length() < this.text.length() && mouseOver && widthOverrides[this.id] == 0) {
/*     */       
/* 277 */       int textWidth = this.fontRenderer.a(this.text);
/* 278 */       width = textWidth + this.textOffsetX + 6 + widthOverrides[this.id];
/*     */     } 
/*     */     
/* 281 */     drawButtonBackground(xPos, yPos, width, colours, mouseOver);
/* 282 */     drawButtonContent(xPos, yPos, width, colours, mouseOver, trimmedText);
/*     */     
/* 284 */     if (mode != IEditablePanel.EditMode.RESERVE)
/*     */     {
/* 286 */       drawButtonDecorations(xPos, yPos, width, colours, mouseOver);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawButtonBackground(int xPos, int yPos, int width, Colours colours, boolean mouseOver) {
/* 292 */     a(xPos, yPos, xPos + width, yPos + this.height, colours.border);
/* 293 */     a(xPos + 1, yPos + 1, xPos + width, yPos + this.height, 1711276032);
/* 294 */     a(xPos + 1, yPos + 1, xPos + width - 1, yPos + this.height - 1, -16777216);
/* 295 */     drawGradientCornerRect(xPos + 1, yPos + 1, xPos + width - 1, yPos + this.height - 1, mouseOver ? 872415231 : colours.background, colours.foreground, mouseOver ? 0.5F : 0.25F, mouseOver ? 0.75F : 0.5F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawButtonContent(int xPos, int yPos, int width, Colours colours, boolean mouseOver, String trimmedText) {
/* 301 */     if (symbolOverrides[this.id] != 0) {
/*     */       
/* 303 */       drawButtonSymbol(xPos, yPos, width, colours, mouseOver);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 310 */     drawButtonText(xPos, yPos, width, colours, mouseOver, trimmedText);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawButtonText(int xPos, int yPos, int width, Colours colours, boolean mouseOver, String trimmedText) {
/* 316 */     if (this.centreText) {
/*     */       
/* 318 */       a(this.fontRenderer, this.text, xPos + width / 2 + this.textOffsetX, yPos + this.textOffsetY, colours.foreground);
/*     */     }
/* 320 */     else if (trimmedText.length() < this.text.length() && !mouseOver) {
/*     */       
/* 322 */       c(this.fontRenderer, trimmedText + "...", xPos + this.textOffsetX, yPos + this.textOffsetY, colours.foreground);
/*     */     }
/*     */     else {
/*     */       
/* 326 */       int offset = 0;
/* 327 */       c(this.fontRenderer, this.text, xPos + this.textOffsetX + offset, yPos + this.textOffsetY, colours.foreground);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawButtonSymbol(int xPos, int yPos, int width, Colours colours, boolean mouseOver) {
/* 334 */     int colour = colours.foreground;
/* 335 */     float f = (colour >> 24 & 0xFF) / 255.0F;
/* 336 */     float f1 = (colour >> 16 & 0xFF) / 255.0F;
/* 337 */     float f2 = (colour >> 8 & 0xFF) / 255.0F;
/* 338 */     float f3 = (colour & 0xFF) / 255.0F;
/* 339 */     GL.glColor4f(f1, f2, f3, f);
/*     */ 
/*     */     
/* 342 */     GL.glEnableTexture2D();
/* 343 */     this.mc.N().a(ResourceLocations.DEFAULTFONT);
/* 344 */     GL.glDisableLighting();
/*     */ 
/*     */     
/* 347 */     int x = xPos + this.width / 2 - 4 + this.textOffsetX;
/* 348 */     int y = yPos + this.textOffsetY;
/*     */ 
/*     */     
/* 351 */     drawTexturedModalRect(x, y, x + 8, y + 8, this.u, this.v, this.u + 16, this.v + 16);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawButtonDecorations(int xPos, int yPos, int width, Colours colours, boolean mouseOver) {
/* 356 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 357 */     if (this.macros.isKeyAlwaysOverridden(this.id, false, true)) {
/*     */       
/* 359 */       this.mc.N().a(ResourceLocations.MAIN);
/* 360 */       drawTexturedModalRect(xPos + width - 11, yPos - 1, xPos + width - 1, yPos + 11, 72, 104, 96, 128);
/*     */     } 
/*     */     
/* 363 */     GL.glEnableBlend();
/* 364 */     MacroPlaybackType playbackType = this.macros.getMacroType(this.id, false);
/* 365 */     if (playbackType == MacroPlaybackType.CONDITIONAL) {
/*     */       
/* 367 */       this.mc.N().a(ResourceLocations.EXT);
/* 368 */       drawTexturedModalRect(xPos + 1, yPos - 1, xPos + 7, yPos + 5, 0, 208, 12, 220);
/*     */     }
/* 370 */     else if (playbackType == MacroPlaybackType.KEYSTATE) {
/*     */       
/* 372 */       this.mc.N().a(ResourceLocations.EXT);
/* 373 */       drawTexturedModalRect(xPos + 1, yPos - 1, xPos + 7, yPos + 5, 12, 208, 24, 220);
/*     */     } 
/* 375 */     GL.glDisableBlend();
/*     */     
/* 377 */     if (this.macros.isKeyOverlaid(this.id))
/*     */     {
/* 379 */       a(xPos, yPos, xPos + width, yPos + this.height, 1610612991);
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
/*     */   public static void notifySettingsLoaded(ISettingsStore settings) {
/* 391 */     Matcher nameOverrideMatcher = Pattern.compile("\\{([0-9]+),(.+?)\\}(?=[\\r\\n\\{])").matcher(settings.getSetting("keyboard.labels", ""));
/*     */     
/* 393 */     while (nameOverrideMatcher.find()) {
/*     */ 
/*     */       
/*     */       try {
/* 397 */         int key = Integer.parseInt(nameOverrideMatcher.group(1));
/* 398 */         if (key > -1 && key < 10000)
/*     */         {
/* 400 */           nameOverrides[key] = nameOverrideMatcher.group(2);
/*     */         
/*     */         }
/*     */       }
/* 404 */       catch (NumberFormatException numberFormatException) {}
/*     */     } 
/*     */     
/* 407 */     Matcher symbolOverrideConfigPatternMatcher = Pattern.compile("\\{([0-9]+),([0-9]+)\\}").matcher(settings.getSetting("keyboard.symbols", ""));
/*     */     
/* 409 */     while (symbolOverrideConfigPatternMatcher.find()) {
/*     */ 
/*     */       
/*     */       try {
/* 413 */         int key = Integer.parseInt(symbolOverrideConfigPatternMatcher.group(1));
/* 414 */         int symbol = Integer.parseInt(symbolOverrideConfigPatternMatcher.group(2));
/*     */         
/* 416 */         if (key > -1 && key < 10000)
/*     */         {
/* 418 */           symbolOverrides[key] = symbol % 256;
/*     */         
/*     */         }
/*     */       }
/* 422 */       catch (NumberFormatException ex) {
/*     */         
/* 424 */         Log.printStackTrace(ex);
/*     */       } 
/*     */     } 
/*     */     
/* 428 */     COLOUR_UNBOUND = Colour.parseColour(settings.getSetting("keyboard.colour.unbound", ""), -12566464);
/* 429 */     COLOUR_BOUND = Colour.parseColour(settings.getSetting("keyboard.colour.bound", ""), -256);
/* 430 */     COLOUR_SPECIAL = Colour.parseColour(settings.getSetting("keyboard.colour.reserved", ""), -7864320);
/* 431 */     COLOUR_BOUNDSPECIAL = Colour.parseColour(settings.getSetting("keyboard.colour.boundspecial", ""), -22016);
/* 432 */     COLOUR_BOUNDGLOBAL = Colour.parseColour(settings.getSetting("keyboard.colour.global", ""), -16711936);
/* 433 */     COLOUR_SELECTED = Colour.parseColour(settings.getSetting("keyboard.colour.selected", ""), -1);
/* 434 */     COLOUR_DENIED = Colour.parseColour(settings.getSetting("keyboard.colour.denied", ""), -65536);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveSettings(ISettingsStore settings) {
/* 445 */     String nameOverridesConfig = "";
/* 446 */     String symbolOverridesConfig = "";
/*     */     
/* 448 */     for (int key = 0; key < 10000; key++) {
/*     */ 
/*     */       
/* 451 */       if (nameOverrides[key] != null)
/*     */       {
/* 453 */         nameOverridesConfig = nameOverridesConfig + "{" + key + "," + nameOverrides[key].replaceAll("\\}", "\\}") + "}";
/*     */       }
/*     */ 
/*     */       
/* 457 */       if (symbolOverrides[key] > 0)
/*     */       {
/* 459 */         symbolOverridesConfig = symbolOverridesConfig + "{" + key + "," + symbolOverrides[key] + "}";
/*     */       }
/*     */     } 
/*     */     
/* 463 */     if (nameOverridesConfig.length() > 0) {
/*     */       
/* 465 */       settings.setSetting("keyboard.labels", nameOverridesConfig);
/* 466 */       settings.setSettingComment("keyboard.labels", "Overrides for some keys in the display, specifies alternate text for the key");
/*     */     } 
/*     */     
/* 469 */     if (symbolOverridesConfig.length() > 0) {
/*     */       
/* 471 */       settings.setSetting("keyboard.symbols", symbolOverridesConfig);
/* 472 */       settings.setSettingComment("keyboard.symbols", "Overrides for some keys in the display, show an ASCII symbol instead of text");
/*     */     } 
/*     */     
/* 475 */     settings.setSetting("keyboard.colour.unbound", Colour.getHexColour(COLOUR_UNBOUND));
/* 476 */     settings.setSetting("keyboard.colour.bound", Colour.getHexColour(COLOUR_BOUND));
/* 477 */     settings.setSetting("keyboard.colour.reserved", Colour.getHexColour(COLOUR_SPECIAL));
/* 478 */     settings.setSetting("keyboard.colour.boundspecial", Colour.getHexColour(COLOUR_BOUNDSPECIAL));
/* 479 */     settings.setSetting("keyboard.colour.global", Colour.getHexColour(COLOUR_BOUNDGLOBAL));
/* 480 */     settings.setSetting("keyboard.colour.selected", Colour.getHexColour(COLOUR_SELECTED));
/* 481 */     settings.setSetting("keyboard.colour.denied", Colour.getHexColour(COLOUR_DENIED));
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\layout\LayoutButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */