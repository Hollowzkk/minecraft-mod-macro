/*      */ package net.eq2online.macros.gui;
/*      */ 
/*      */ import aip;
/*      */ import bib;
/*      */ import bip;
/*      */ import bir;
/*      */ import bit;
/*      */ import buk;
/*      */ import bve;
/*      */ import com.mumfrey.liteloader.gl.GL;
/*      */ import com.mumfrey.liteloader.util.render.Icon;
/*      */ import java.awt.Rectangle;
/*      */ import net.eq2online.macros.res.ResourceLocations;
/*      */ import nf;
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
/*      */ public class GuiRenderer
/*      */   extends bir
/*      */ {
/*      */   protected final bib mc;
/*      */   protected final bip fontRenderer;
/*   32 */   private float texMapScale = 0.00390625F;
/*      */ 
/*      */   
/*      */   public GuiRenderer(bib minecraft) {
/*   36 */     this.mc = minecraft;
/*   37 */     this.fontRenderer = (minecraft != null) ? minecraft.k : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public final GuiRenderer bindTexture(nf texture, int textureSize) {
/*   42 */     setTexMapSize(textureSize);
/*   43 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*   44 */     this.mc.N().a(texture);
/*   45 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final GuiRenderer setTexMapSize(int textureSize) {
/*   55 */     this.texMapScale = 1.0F / textureSize;
/*   56 */     return this;
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
/*      */   public final void drawTooltip(String tooltipText, int mouseX, int mouseY, int width, int height, int colour, int backgroundColour) {
/*   70 */     if (tooltipText == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*   75 */     int textSize = this.fontRenderer.a(tooltipText);
/*   76 */     mouseX = Math.max(0, Math.min(width - textSize - 6, mouseX - 6));
/*   77 */     mouseY = Math.max(0, Math.min(height - 16, mouseY - 18));
/*      */     
/*   79 */     a(mouseX, mouseY, mouseX + textSize + 6, mouseY + 16, backgroundColour);
/*   80 */     c(this.fontRenderer, tooltipText, mouseX + 3, mouseY + 4, colour);
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
/*      */   public final void drawFunkyTooltip(String tooltipText, int mouseX, int mouseY, int fontColour) {
/*   93 */     drawFunkyTooltip(tooltipText, mouseX, mouseY, fontColour, -267386864, 1347420415);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void drawFunkyTooltip(String tooltipText, int mouseX, int mouseY, int fontColour, int colour1, int colour2) {
/*   98 */     drawFunkyTooltip(tooltipText.split("\\n"), mouseX, mouseY, fontColour, colour1, colour2);
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
/*      */   public final void drawFunkyTooltip(String[] tooltipText, int mouseX, int mouseY, int fontColour, int col1, int col2) {
/*  113 */     int p1 = 3, p2 = 4, p3 = 1;
/*  114 */     int x = mouseX + 12, y = mouseY - 12;
/*  115 */     int textSize = 0;
/*      */     
/*  117 */     for (String toolTipLine : tooltipText)
/*      */     {
/*  119 */       textSize = Math.max(textSize, this.fontRenderer.a(toolTipLine));
/*      */     }
/*      */     
/*  122 */     int height = 8 * tooltipText.length + 2 * (tooltipText.length - 1);
/*  123 */     int col3 = (col2 & 0xFEFEFE) >> 1 | col2 & 0xFF000000;
/*      */     
/*  125 */     this.e = 300.0F;
/*  126 */     a(x - p1, y - p2, x + textSize + p1, y - p1, col1, col1);
/*  127 */     a(x - p1, y + height + p1, x + textSize + p1, y + height + p2, col1, col1);
/*  128 */     a(x - p1, y - p1, x + textSize + p1, y + height + p1, col1, col1);
/*  129 */     a(x - p2, y - p1, x - p1, y + height + p1, col1, col1);
/*  130 */     a(x + textSize + p1, y - p1, x + textSize + p2, y + height + p1, col1, col1);
/*  131 */     a(x - p1, y - p1 + p3, x - p1 + p3, y + height + p1 - p3, col2, col3);
/*  132 */     a(x + textSize + p1 * 2, y - p1 + p3, x + textSize + p1, y + height + p1 - p3, col2, col3);
/*  133 */     a(x - p1, y - p1, x + textSize + p1, y - p1 + p3, col2, col2);
/*  134 */     a(x - p1, y + height + p1 * 2, x + textSize + p1, y + height + p1, col3, col3);
/*      */     
/*  136 */     for (String toolTipLine : tooltipText) {
/*      */       
/*  138 */       this.fontRenderer.a(toolTipLine, x, y, fontColour);
/*  139 */       y += 10;
/*      */     } 
/*      */     
/*  142 */     this.e = 0.0F;
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
/*      */   public final void drawHorizontalProgressBar(float value, float maxValue, int x, int y, int width, int height) {
/*  158 */     value = Math.min(Math.max(value, 0.0F), maxValue);
/*      */     
/*  160 */     GL.glDepthFunc(518);
/*  161 */     GL.glEnableTexture2D();
/*  162 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  163 */     setTexMapSize(128);
/*      */     
/*  165 */     this.mc.N().a(ResourceLocations.EXT);
/*  166 */     drawTexturedModalRect(x, y, x + 4, y + height, 0, 120, 4, 128);
/*  167 */     drawTexturedModalRect(x + width - 4, y, x + width, y + height, 124, 120, 128, 128);
/*  168 */     drawTexturedModalRect(x + 4, y, x + width - 4, y + height, 4, 120, 124, 128);
/*      */     
/*  170 */     if (value > 0.0F && maxValue != 0.0F) {
/*      */       
/*  172 */       int endWidth = 4;
/*  173 */       int barWidth = (int)(value / maxValue * width);
/*  174 */       if (barWidth < 8) endWidth = barWidth / 2;
/*      */       
/*  176 */       drawTexturedModalRect(x, y, x + endWidth, y + height, 0, 112, 4, 120);
/*  177 */       drawTexturedModalRect(x + barWidth - endWidth, y, x + barWidth, y + height, 124, 112, 128, 120);
/*  178 */       setTexMapSize(256);
/*      */       
/*  180 */       drawTexturedModalRect(x + endWidth, y, x + barWidth - endWidth, y + height, 8, 224, Math.min(barWidth - endWidth * 2, 248), 240);
/*      */     } 
/*      */     
/*  183 */     setTexMapSize(256);
/*  184 */     GL.glDepthFunc(515);
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
/*      */   public final void drawNativeLine(float x1, float y1, float x2, float y2, float width, int colour) {
/*  199 */     float f = (colour >> 24 & 0xFF) / 255.0F;
/*  200 */     float f1 = (colour >> 16 & 0xFF) / 255.0F;
/*  201 */     float f2 = (colour >> 8 & 0xFF) / 255.0F;
/*  202 */     float f3 = (colour & 0xFF) / 255.0F;
/*      */     
/*  204 */     int scaleFactor = (new bit(this.mc)).e();
/*      */     
/*  206 */     GL.glEnableBlend();
/*  207 */     GL.glDisableTexture2D();
/*  208 */     GL.glBlendFunc(770, 771);
/*  209 */     GL.glColor4f(f1, f2, f3, f);
/*  210 */     GL.glLineWidth(scaleFactor * width);
/*      */     
/*  212 */     bve tessellator = bve.a();
/*  213 */     buk buf = tessellator.c();
/*  214 */     buf.a(1, GL.VF_POSITION);
/*  215 */     buf.b(x1, y1, 0.0D).d();
/*  216 */     buf.b(x2, y2, 0.0D).d();
/*  217 */     tessellator.b();
/*      */     
/*  219 */     GL.glEnableTexture2D();
/*  220 */     GL.glDisableBlend();
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
/*      */   public final void drawLine(int x1, int y1, int x2, int y2, float width, int colour) {
/*  235 */     drawArrow(x1, y1, x2, y2, 0, width, colour, false, 0);
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
/*      */   public final void drawArrow(int x1, int y1, int x2, int y2, int z, float width, int arrowHeadSize, int colour) {
/*  251 */     drawArrow(x1, y1, x2, y2, z, width, colour, true, arrowHeadSize);
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
/*      */ 
/*      */   
/*      */   public final void drawArrow(int x1, int y1, int x2, int y2, int z, float width, int colour, boolean arrowHead, int arrowHeadSize) {
/*  269 */     int length = (int)Math.sqrt(Math.pow((x2 - x1), 2.0D) + Math.pow((y2 - y1), 2.0D));
/*  270 */     float angle = (float)Math.toDegrees(Math.atan2((y2 - y1), (x2 - x1)));
/*      */ 
/*      */     
/*  273 */     GL.glPushMatrix();
/*  274 */     GL.glTranslatef(x1, y1, 0.0F);
/*  275 */     GL.glRotatef(angle, 0.0F, 0.0F, 1.0F);
/*      */ 
/*      */     
/*  278 */     x1 = 0;
/*  279 */     x2 = length - (arrowHead ? arrowHeadSize : 0);
/*  280 */     float dy1 = width * -0.5F;
/*  281 */     float dy2 = dy1 + width;
/*      */ 
/*      */     
/*  284 */     float f = (colour >> 24 & 0xFF) / 255.0F;
/*  285 */     float f1 = (colour >> 16 & 0xFF) / 255.0F;
/*  286 */     float f2 = (colour >> 8 & 0xFF) / 255.0F;
/*  287 */     float f3 = (colour & 0xFF) / 255.0F;
/*      */     
/*  289 */     GL.glEnableBlend();
/*  290 */     GL.glDisableTexture2D();
/*  291 */     GL.glBlendFunc(770, 771);
/*  292 */     GL.glColor4f(f1, f2, f3, f);
/*      */ 
/*      */     
/*  295 */     bve tessellator = bve.a();
/*  296 */     buk buf = tessellator.c();
/*  297 */     buf.a(7, GL.VF_POSITION);
/*  298 */     buf.b(x1, dy2, z).d();
/*  299 */     buf.b(x2, dy2, z).d();
/*  300 */     buf.b(x2, dy1, z).d();
/*  301 */     buf.b(x1, dy1, z).d();
/*  302 */     tessellator.b();
/*      */ 
/*      */     
/*  305 */     if (arrowHead && arrowHeadSize > 0) {
/*      */       
/*  307 */       buf.a(4, GL.VF_POSITION);
/*  308 */       buf.b(x2, (0 - arrowHeadSize / 2), z).d();
/*  309 */       buf.b(x2, (arrowHeadSize / 2), z).d();
/*  310 */       buf.b(length, 0.0D, z).d();
/*  311 */       tessellator.b();
/*      */     } 
/*      */     
/*  314 */     GL.glEnableTexture2D();
/*  315 */     GL.glDisableBlend();
/*      */     
/*  317 */     GL.glPopMatrix();
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
/*      */   public final void drawDoubleEndedArrowH(float x1, float x2, float y, float width, float arrowHeadSize, int colour) {
/*  333 */     float alpha = (colour >> 24 & 0xFF) / 255.0F;
/*  334 */     float red = (colour >> 16 & 0xFF) / 255.0F;
/*  335 */     float green = (colour >> 8 & 0xFF) / 255.0F;
/*  336 */     float blue = (colour & 0xFF) / 255.0F;
/*      */     
/*  338 */     int scaleFactor = (new bit(this.mc)).e();
/*      */     
/*  340 */     GL.glDisableBlend();
/*  341 */     GL.glDisableTexture2D();
/*  342 */     GL.glBlendFunc(770, 771);
/*  343 */     GL.glColor4f(red, green, blue, alpha);
/*  344 */     GL.glLineWidth(scaleFactor * width);
/*      */     
/*  346 */     float halfArrowHeadSize = arrowHeadSize * 0.5F;
/*      */     
/*  348 */     bve tessellator = bve.a();
/*  349 */     buk buf = tessellator.c();
/*      */     
/*  351 */     buf.a(1, GL.VF_POSITION);
/*  352 */     buf.b((x1 + arrowHeadSize), y, 0.0D).d();
/*  353 */     buf.b((x2 - arrowHeadSize), y, 0.0D).d();
/*  354 */     tessellator.b();
/*      */     
/*  356 */     buf.a(4, GL.VF_POSITION);
/*  357 */     buf.b(x1, y, 0.0D).d();
/*  358 */     buf.b((x1 + arrowHeadSize), (y + halfArrowHeadSize), 0.0D).d();
/*  359 */     buf.b((x1 + arrowHeadSize), (y - halfArrowHeadSize), 0.0D).d();
/*  360 */     tessellator.b();
/*      */     
/*  362 */     buf.a(4, GL.VF_POSITION);
/*  363 */     buf.b(x2, y, 0.0D).d();
/*  364 */     buf.b((x2 - arrowHeadSize), (y - halfArrowHeadSize), 0.0D).d();
/*  365 */     buf.b((x2 - arrowHeadSize), (y + halfArrowHeadSize), 0.0D).d();
/*  366 */     tessellator.b();
/*      */     
/*  368 */     GL.glEnableTexture2D();
/*  369 */     GL.glDisableBlend();
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
/*      */   public final void drawDoubleEndedArrowV(float x, float y1, float y2, float width, float arrowHeadSize, int colour) {
/*  385 */     float alpha = (colour >> 24 & 0xFF) / 255.0F;
/*  386 */     float red = (colour >> 16 & 0xFF) / 255.0F;
/*  387 */     float green = (colour >> 8 & 0xFF) / 255.0F;
/*  388 */     float blue = (colour & 0xFF) / 255.0F;
/*      */     
/*  390 */     int scaleFactor = (new bit(this.mc)).e();
/*      */     
/*  392 */     GL.glDisableBlend();
/*  393 */     GL.glDisableTexture2D();
/*  394 */     GL.glBlendFunc(770, 771);
/*  395 */     GL.glColor4f(red, green, blue, alpha);
/*  396 */     GL.glLineWidth(scaleFactor * width);
/*      */     
/*  398 */     float halfArrowHeadSize = arrowHeadSize * 0.5F;
/*      */     
/*  400 */     bve tessellator = bve.a();
/*  401 */     buk buf = tessellator.c();
/*      */     
/*  403 */     buf.a(1, GL.VF_POSITION);
/*  404 */     buf.b(x, (y1 + arrowHeadSize), 0.0D).d();
/*  405 */     buf.b(x, (y2 - arrowHeadSize), 0.0D).d();
/*  406 */     tessellator.b();
/*      */     
/*  408 */     buf.a(4, GL.VF_POSITION);
/*  409 */     buf.b(x, y1, 0.0D).d();
/*  410 */     buf.b((x - halfArrowHeadSize), (y1 + arrowHeadSize), 0.0D).d();
/*  411 */     buf.b((x + halfArrowHeadSize), (y1 + arrowHeadSize), 0.0D).d();
/*  412 */     tessellator.b();
/*      */     
/*  414 */     buf.a(4, GL.VF_POSITION);
/*  415 */     buf.b(x, y2, 0.0D).d();
/*  416 */     buf.b((x + halfArrowHeadSize), (y2 - arrowHeadSize), 0.0D).d();
/*  417 */     buf.b((x - halfArrowHeadSize), (y2 - arrowHeadSize), 0.0D).d();
/*  418 */     tessellator.b();
/*      */     
/*  420 */     GL.glEnableTexture2D();
/*  421 */     GL.glDisableBlend();
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
/*      */   protected void drawTriangle(int corner, int x1, int y1, int x2, int y2, int colour) {
/*  437 */     corner %= 4;
/*      */     
/*  439 */     if (x1 < x2) {
/*      */       
/*  441 */       int xTemp = x1;
/*  442 */       x1 = x2;
/*  443 */       x2 = xTemp;
/*      */     } 
/*      */     
/*  446 */     if (y1 < y2) {
/*      */       
/*  448 */       int yTemp = y1;
/*  449 */       y1 = y2;
/*  450 */       y2 = yTemp;
/*      */     } 
/*      */     
/*  453 */     float alpha = (colour >> 24 & 0xFF) / 255.0F;
/*  454 */     float red = (colour >> 16 & 0xFF) / 255.0F;
/*  455 */     float green = (colour >> 8 & 0xFF) / 255.0F;
/*  456 */     float blue = (colour & 0xFF) / 255.0F;
/*      */     
/*  458 */     GL.glDisableBlend();
/*  459 */     GL.glDisableTexture2D();
/*  460 */     GL.glBlendFunc(770, 771);
/*  461 */     GL.glColor4f(red, green, blue, alpha);
/*      */     
/*  463 */     bve tessellator = bve.a();
/*  464 */     buk buf = tessellator.c();
/*  465 */     buf.a(4, GL.VF_POSITION);
/*  466 */     if (corner != 3) buf.b(x1, y2, 0.0D).d(); 
/*  467 */     if (corner != 0) buf.b(x2, y2, 0.0D).d(); 
/*  468 */     if (corner != 1) buf.b(x2, y1, 0.0D).d(); 
/*  469 */     if (corner != 2) buf.b(x1, y1, 0.0D).d(); 
/*  470 */     tessellator.b();
/*      */     
/*  472 */     GL.glEnableTexture2D();
/*  473 */     GL.glDisableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawRectOutline(Rectangle rect, int colour, float width) {
/*  481 */     float alpha = (colour >> 24 & 0xFF) / 255.0F;
/*  482 */     float red = (colour >> 16 & 0xFF) / 255.0F;
/*  483 */     float green = (colour >> 8 & 0xFF) / 255.0F;
/*  484 */     float blue = (colour & 0xFF) / 255.0F;
/*      */     
/*  486 */     int scaleFactor = (new bit(this.mc)).e();
/*      */     
/*  488 */     GL.glLineWidth(scaleFactor * width);
/*  489 */     GL.glBlendFunc(770, 771);
/*  490 */     GL.glEnableBlend();
/*  491 */     GL.glDisableTexture2D();
/*  492 */     GL.glDisableLighting();
/*  493 */     GL.glColor4f(red, green, blue, alpha);
/*      */ 
/*      */     
/*  496 */     bve tessellator = bve.a();
/*  497 */     buk buf = tessellator.c();
/*  498 */     buf.a(2, GL.VF_POSITION);
/*  499 */     buf.b(rect.x, rect.y, 0.0D).d();
/*  500 */     buf.b((rect.x + rect.width), rect.y, 0.0D).d();
/*  501 */     buf.b((rect.x + rect.width), (rect.y + rect.height), 0.0D).d();
/*  502 */     buf.b(rect.x, (rect.y + rect.height), 0.0D).d();
/*  503 */     tessellator.b();
/*      */     
/*  505 */     GL.glEnableTexture2D();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawRect(Rectangle rect, int colour) {
/*  514 */     drawRect(rect, colour, 0, 0, 0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawRect(Rectangle rect, int colour, int inset) {
/*  524 */     drawRect(rect, colour, inset, inset, inset, inset);
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
/*      */   public final void drawRect(Rectangle rect, int colour, int xOffset, int yOffset, int widthOffset, int heightOffset) {
/*  537 */     a(rect.x + xOffset, rect.y + yOffset, rect.x + rect.width - widthOffset, rect.y + rect.height - heightOffset, colour);
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
/*      */   
/*      */   public final void drawTexturedModalRectRot(int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/*  554 */     bve tessellator = bve.a();
/*  555 */     buk buf = tessellator.c();
/*  556 */     buf.a(7, GL.VF_POSITION_TEX);
/*  557 */     buf.b(x2, y2, this.e).a((u * this.texMapScale), (v2 * this.texMapScale)).d();
/*  558 */     buf.b(x2, y, this.e).a((u2 * this.texMapScale), (v2 * this.texMapScale)).d();
/*  559 */     buf.b(x, y, this.e).a((u2 * this.texMapScale), (v * this.texMapScale)).d();
/*  560 */     buf.b(x, y2, this.e).a((u * this.texMapScale), (v * this.texMapScale)).d();
/*  561 */     tessellator.b();
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
/*      */   public final void drawTexturedModalRectRot(int x, int y, int u, int v, int width, int height) {
/*  576 */     bve tessellator = bve.a();
/*  577 */     buk buf = tessellator.c();
/*  578 */     buf.a(7, GL.VF_POSITION_TEX);
/*  579 */     buf.b((x + height), (y + width), this.e).a((u * this.texMapScale), ((v + height) * this.texMapScale)).d();
/*  580 */     buf.b((x + height), y, this.e).a(((u + width) * this.texMapScale), ((v + height) * this.texMapScale)).d();
/*  581 */     buf.b(x, y, this.e).a(((u + width) * this.texMapScale), (v * this.texMapScale)).d();
/*  582 */     buf.b(x, (y + width), this.e).a((u * this.texMapScale), (v * this.texMapScale)).d();
/*  583 */     tessellator.b();
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
/*      */   public final void drawStringWithEllipsis(String string, int x, int y, int width, int colour) {
/*  599 */     if (this.fontRenderer.a(string) <= width) {
/*      */       
/*  601 */       this.fontRenderer.a(string, x, y, colour);
/*      */     }
/*  603 */     else if (width < 8) {
/*      */       
/*  605 */       this.fontRenderer.a("..", x, y, colour);
/*      */     }
/*      */     else {
/*      */       
/*  609 */       String trimmedText = string;
/*      */       
/*  611 */       while (this.fontRenderer.a(trimmedText) > width - 8 && trimmedText.length() > 0)
/*      */       {
/*  613 */         trimmedText = trimmedText.substring(0, trimmedText.length() - 1);
/*      */       }
/*      */       
/*  616 */       this.fontRenderer.a(trimmedText + "...", x, y, colour);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawCrossHair(int x, int y, int size, int width, int colour) {
/*  625 */     float alpha = (colour >> 24 & 0xFF) / 255.0F;
/*  626 */     float red = (colour >> 16 & 0xFF) / 255.0F;
/*  627 */     float green = (colour >> 8 & 0xFF) / 255.0F;
/*  628 */     float blue = (colour & 0xFF) / 255.0F;
/*      */     
/*  630 */     int scaleFactor = (new bit(this.mc)).e();
/*      */     
/*  632 */     GL.glLineWidth((scaleFactor * width));
/*  633 */     GL.glBlendFunc(770, 771);
/*  634 */     GL.glEnableBlend();
/*  635 */     GL.glDisableTexture2D();
/*  636 */     GL.glDisableLighting();
/*  637 */     GL.glColor4f(red, green, blue, alpha);
/*  638 */     GL.glEnableColorLogic();
/*  639 */     GL.glLogicOp(5387);
/*      */ 
/*      */     
/*  642 */     bve tessellator = bve.a();
/*  643 */     buk buf = tessellator.c();
/*      */     
/*  645 */     buf.a(1, GL.VF_POSITION);
/*  646 */     buf.b((x - size), y, 0.0D).d();
/*  647 */     buf.b((x + size), y, 0.0D).d();
/*  648 */     tessellator.b();
/*      */     
/*  650 */     buf.a(1, GL.VF_POSITION);
/*  651 */     buf.b(x, (y - size), 0.0D).d();
/*  652 */     buf.b(x, (y + size), 0.0D).d();
/*  653 */     tessellator.b();
/*      */     
/*  655 */     GL.glDisableColorLogic();
/*  656 */     GL.glEnableTexture2D();
/*      */   }
/*      */ 
/*      */   
/*      */   public final void drawRotText(String text, int xPosition, int yPosition, int colour, boolean colourOrOp) {
/*  661 */     if (colourOrOp) {
/*      */       
/*  663 */       GL.glEnableColorLogic();
/*  664 */       GL.glLogicOp(5387);
/*      */     } 
/*      */     
/*  667 */     int textWidth = this.fontRenderer.a(text) / 2;
/*      */     
/*  669 */     GL.glPushMatrix();
/*  670 */     GL.glTranslatef(xPosition, yPosition, 0.0F);
/*  671 */     GL.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
/*  672 */     GL.glTranslatef(-textWidth, -4.0F, 0.0F);
/*      */     
/*  674 */     this.fontRenderer.a(text, 0, 0, colour);
/*      */     
/*  676 */     GL.glPopMatrix();
/*      */     
/*  678 */     if (colourOrOp) {
/*      */       
/*  680 */       GL.glDisableColorLogic();
/*  681 */       GL.glEnableTexture2D();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawIcon(nf texture, Icon icon, int x, int y, int x2, int y2) {
/*  687 */     this.mc.N().a(texture);
/*  688 */     drawTexturedModalRectF(x, y, x2, y2, icon.getMinU(), icon.getMinV(), icon.getMaxU(), icon.getMaxV());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawIcon(Icon icon, int x, int y, int width, int height) {
/*  696 */     bve tessellator = bve.a();
/*  697 */     buk buf = tessellator.c();
/*  698 */     buf.a(7, GL.VF_POSITION_TEX);
/*  699 */     buf.b((x + 0), (y + height), this.e).a(icon.getMinU(), icon.getMaxV()).d();
/*  700 */     buf.b((x + width), (y + height), this.e).a(icon.getMaxU(), icon.getMaxV()).d();
/*  701 */     buf.b((x + width), (y + 0), this.e).a(icon.getMaxU(), icon.getMinV()).d();
/*  702 */     buf.b((x + 0), (y + 0), this.e).a(icon.getMinU(), icon.getMinV()).d();
/*  703 */     tessellator.b();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawTexturedModalIcon(nf texture, int textureSize, int x, int y, int width, int height, int u, int v, int u2, int v2) {
/*  709 */     bindTexture(texture, textureSize).drawTexturedModalIcon(x, y, width, height, u, v, u2, v2);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void drawTexturedModalIcon(int x, int y, int width, int height, int u, int v, int u2, int v2) {
/*  714 */     x += (width - u2 - u) / 2;
/*  715 */     y += (height - v2 - v) / 2;
/*      */     
/*  717 */     float oldScale = this.texMapScale;
/*  718 */     drawTexturedModalRect(x, y, x + u2 - u, y + v2 - v, u, v, u2, v2);
/*  719 */     this.texMapScale = oldScale;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawTexturedModalRect(nf texture, int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/*  738 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  739 */     this.mc.N().a(texture);
/*      */     
/*  741 */     drawTexturedModalRect(x, y, x2, y2, u, v, u2, v2);
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
/*      */ 
/*      */   
/*      */   public final void drawTexturedModalRect(int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/*  759 */     bve tessellator = bve.a();
/*  760 */     buk buf = tessellator.c();
/*  761 */     buf.a(7, GL.VF_POSITION_TEX);
/*  762 */     buf.b(x, y2, this.e).a((u * this.texMapScale), (v2 * this.texMapScale)).d();
/*  763 */     buf.b(x2, y2, this.e).a((u2 * this.texMapScale), (v2 * this.texMapScale)).d();
/*  764 */     buf.b(x2, y, this.e).a((u2 * this.texMapScale), (v * this.texMapScale)).d();
/*  765 */     buf.b(x, y, this.e).a((u * this.texMapScale), (v * this.texMapScale)).d();
/*  766 */     tessellator.b();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawTessellatedModalRectV(nf texture, int textureSize, int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/*  787 */     bindTexture(texture, textureSize).drawTessellatedModalRectV(x, y, x2, y2, u, v, u2, v2);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawTessellatedModalRectV(int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/*  806 */     int tileSize = (v2 - v) / 2;
/*  807 */     int vMidTop = v + tileSize;
/*  808 */     int vMidBtm = vMidTop + 1;
/*      */     
/*  810 */     drawTexturedModalRect(x, y, x2, y + tileSize, u, v, u2, vMidTop);
/*  811 */     drawTexturedModalRect(x, y + tileSize, x2, y2 - tileSize + 1, u, vMidTop, u2, vMidBtm);
/*  812 */     drawTexturedModalRect(x, y2 - tileSize + 1, x2, y2, u, vMidBtm, u2, v2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawTessellatedModalRectH(nf texture, int textureSize, int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/*  833 */     bindTexture(texture, textureSize).drawTessellatedModalRectH(x, y, x2, y2, u, v, u2, v2);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawTessellatedModalRectH(int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/*  852 */     int tileSize = (u2 - u) / 2;
/*  853 */     int uMidLeft = u + tileSize;
/*  854 */     int uMidRight = uMidLeft + 1;
/*      */     
/*  856 */     drawTexturedModalRect(x, y, x + tileSize, y2, u, v, uMidLeft, v2);
/*  857 */     drawTexturedModalRect(x + tileSize, y, x2 - tileSize + 1, y2, uMidLeft, v, uMidRight, v2);
/*  858 */     drawTexturedModalRect(x2 - tileSize + 1, y, x2, y2, uMidRight, v, u2, v2);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawTessellatedModalBorderRect(int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/*  877 */     drawTessellatedModalBorderRect(x, y, x2, y2, u, v, u2, v2, Math.min((x2 - x) / 2 - 1, (y2 - y) / 2 - 1));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawTessellatedModalBorderRect(nf texture, int textureSize, int x, int y, int x2, int y2, int u, int v, int u2, int v2) {
/*  899 */     drawTessellatedModalBorderRect(texture, textureSize, x, y, x2, y2, u, v, u2, v2, Math.min((x2 - x) / 2 - 1, (y2 - y) / 2 - 1));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawTessellatedModalBorderRect(nf texture, int textureSize, int x, int y, int x2, int y2, int u, int v, int u2, int v2, int borderSize) {
/*  924 */     bindTexture(texture, textureSize).drawTessellatedModalBorderRect(x, y, x2, y2, u, v, u2, v2, borderSize);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawTessellatedModalBorderRect(int x, int y, int x2, int y2, int u, int v, int u2, int v2, int borderSize) {
/*  946 */     int tileSize = Math.min((u2 - u) / 2 - 1, (v2 - v) / 2 - 1);
/*      */     
/*  948 */     int ul = u + tileSize, ur = u2 - tileSize, vt = v + tileSize, vb = v2 - tileSize;
/*  949 */     int xl = x + borderSize, xr = x2 - borderSize, yt = y + borderSize, yb = y2 - borderSize;
/*      */     
/*  951 */     drawTexturedModalRect(x, y, xl, yt, u, v, ul, vt);
/*  952 */     drawTexturedModalRect(xl, y, xr, yt, ul, v, ur, vt);
/*  953 */     drawTexturedModalRect(xr, y, x2, yt, ur, v, u2, vt);
/*  954 */     drawTexturedModalRect(x, yb, xl, y2, u, vb, ul, v2);
/*  955 */     drawTexturedModalRect(xl, yb, xr, y2, ul, vb, ur, v2);
/*  956 */     drawTexturedModalRect(xr, yb, x2, y2, ur, vb, u2, v2);
/*  957 */     drawTexturedModalRect(x, yt, xl, yb, u, vt, ul, vb);
/*  958 */     drawTexturedModalRect(xr, yt, x2, yb, ur, vt, u2, vb);
/*  959 */     drawTexturedModalRect(xl, yt, xr, yb, ul, vt, ur, vb);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawTexturedModalRectEx(int x, int y, int x2, int y2, int u, int v, int u2, int v2, int zLevel) {
/*  968 */     float texMapScale = 0.015625F;
/*      */     
/*  970 */     bve tessellator = bve.a();
/*  971 */     buk buf = tessellator.c();
/*  972 */     buf.a(7, GL.VF_POSITION_TEX);
/*  973 */     buf.b(x, y2, zLevel).a((u * texMapScale), (v2 * texMapScale)).d();
/*  974 */     buf.b(x2, y2, zLevel).a((u2 * texMapScale), (v2 * texMapScale)).d();
/*  975 */     buf.b(x2, y, zLevel).a((u2 * texMapScale), (v * texMapScale)).d();
/*  976 */     buf.b(x, y, zLevel).a((u * texMapScale), (v * texMapScale)).d();
/*  977 */     tessellator.b();
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
/*      */   
/*      */   public final void drawTexturedModalRectF(int x, int y, int x2, int y2, float u, float v, float u2, float v2) {
/*  994 */     bve tessellator = bve.a();
/*  995 */     buk buf = tessellator.c();
/*  996 */     buf.a(7, GL.VF_POSITION_TEX);
/*  997 */     buf.b(x, y2, this.e).a(u, v2).d();
/*  998 */     buf.b(x2, y2, this.e).a(u2, v2).d();
/*  999 */     buf.b(x2, y, this.e).a(u2, v).d();
/* 1000 */     buf.b(x, y, this.e).a(u, v).d();
/* 1001 */     tessellator.b();
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
/*      */   public final void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, float texMapScale) {
/* 1017 */     bve tessellator = bve.a();
/* 1018 */     buk buf = tessellator.c();
/* 1019 */     buf.a(7, GL.VF_POSITION_TEX);
/* 1020 */     buf.b((x + 0), (y + height), this.e).a(((u + 0) * texMapScale), ((v + height) * texMapScale)).d();
/* 1021 */     buf.b((x + width), (y + height), this.e).a(((u + width) * texMapScale), ((v + height) * texMapScale)).d();
/* 1022 */     buf.b((x + width), (y + 0), this.e).a(((u + width) * texMapScale), ((v + 0) * texMapScale)).d();
/* 1023 */     buf.b((x + 0), (y + 0), this.e).a(((u + 0) * texMapScale), ((v + 0) * texMapScale)).d();
/* 1024 */     tessellator.b();
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
/*      */   public final void drawTitle(String banner, boolean centre, int left, int top, int right, int bannerColour, int backColour) {
/* 1039 */     int bottom = top + 18;
/*      */     
/* 1041 */     float a = (backColour >> 24 & 0xFF) / 255.0F;
/* 1042 */     float r = (backColour >> 16 & 0xFF) / 255.0F;
/* 1043 */     float g = (backColour >> 8 & 0xFF) / 255.0F;
/* 1044 */     float b = (backColour & 0xFF) / 255.0F;
/* 1045 */     bve tessellator = bve.a();
/* 1046 */     buk buf = tessellator.c();
/* 1047 */     GL.glEnableBlend();
/* 1048 */     GL.glDisableTexture2D();
/* 1049 */     GL.glBlendFunc(770, 771);
/* 1050 */     GL.glColor4f(r, g, b, a);
/* 1051 */     buf.a(7, GL.VF_POSITION);
/* 1052 */     buf.b(right, top, 0.0D).d();
/* 1053 */     buf.b(left, top, 0.0D).d();
/* 1054 */     buf.b(left, bottom, 0.0D).d();
/* 1055 */     buf.b(right, bottom, 0.0D).d();
/* 1056 */     tessellator.b();
/* 1057 */     GL.glEnableTexture2D();
/*      */     
/* 1059 */     if (centre) {
/*      */       
/* 1061 */       this.fontRenderer.a(banner, ((right - left) / 2 + left - this.fontRenderer.a(banner) / 2), (top + 5), bannerColour);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1066 */       drawStringWithEllipsis(banner, left + 4, top + 5, right - left - 8, bannerColour);
/*      */     } 
/*      */     
/* 1069 */     GL.glDisableBlend();
/*      */   }
/*      */ 
/*      */   
/*      */   public final void drawItem(aip itemStack, int left, int top) {
/* 1074 */     GL.glDepthFunc(515);
/* 1075 */     this.mc.ad().a(itemStack, left, top);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void drawGradientCornerRect(int x1, int y1, int x2, int y2, int colour1, int colour2, float blendFactor) {
/* 1083 */     float opacity2 = (colour2 >> 24 & 0xFF) / 255.0F;
/* 1084 */     drawGradientCornerRect(x1, y1, x2, y2, colour1, colour2, opacity2, blendFactor);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void drawGradientCornerRect(int x1, int y1, int x2, int y2, int colour1, int colour2, float opacity2, float blendFactor) {
/* 1089 */     float opacity1 = (colour1 >> 24 & 0xFF) / 255.0F;
/*      */     
/* 1091 */     float red1 = (colour1 >> 16 & 0xFF) / 255.0F;
/* 1092 */     float green1 = (colour1 >> 8 & 0xFF) / 255.0F;
/* 1093 */     float blue1 = (colour1 & 0xFF) / 255.0F;
/*      */     
/* 1095 */     float red2 = (colour2 >> 16 & 0xFF) / 255.0F;
/* 1096 */     float green2 = (colour2 >> 8 & 0xFF) / 255.0F;
/* 1097 */     float blue2 = (colour2 & 0xFF) / 255.0F;
/*      */     
/* 1099 */     float redmid = red1 * (1.0F - blendFactor) + red2 * blendFactor;
/* 1100 */     float greenmid = green1 * (1.0F - blendFactor) + green2 * blendFactor;
/* 1101 */     float bluemid = blue1 * (1.0F - blendFactor) + blue2 * blendFactor;
/* 1102 */     float alphamid = opacity1 * (1.0F - blendFactor) + opacity2 * blendFactor;
/*      */     
/* 1104 */     GL.glDisableTexture2D();
/* 1105 */     GL.glEnableBlend();
/* 1106 */     GL.glDisableAlphaTest();
/* 1107 */     GL.glBlendFunc(770, 771);
/* 1108 */     GL.glShadeModel(7425);
/* 1109 */     bve tessellator = bve.a();
/* 1110 */     buk buf = tessellator.c();
/* 1111 */     buf.a(7, GL.VF_POSITION_COLOR);
/* 1112 */     buf.b(x1, y2, this.e).a(redmid, greenmid, bluemid, alphamid).d();
/* 1113 */     buf.b(x2, y2, this.e).a(red1, green1, blue1, opacity1).d();
/* 1114 */     buf.b(x2, y1, this.e).a(redmid, greenmid, bluemid, alphamid).d();
/* 1115 */     buf.b(x1, y1, this.e).a(red2, green2, blue2, opacity2).d();
/* 1116 */     tessellator.b();
/* 1117 */     GL.glShadeModel(7424);
/* 1118 */     GL.glDisableBlend();
/* 1119 */     GL.glEnableAlphaTest();
/* 1120 */     GL.glEnableTexture2D();
/*      */   }
/*      */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\GuiRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */