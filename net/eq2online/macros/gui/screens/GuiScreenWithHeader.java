/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bib;
/*     */ import blk;
/*     */ import bme;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.controls.GuiDropDownMenu;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiScreenWithHeader
/*     */   extends GuiScreenEx
/*     */ {
/*     */   public enum NavAction
/*     */   {
/*  23 */     NONE,
/*  24 */     NEXT,
/*  25 */     PREVIOUS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   protected String title = "screen.title";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   protected String banner = "screen.banner";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   protected int backColour = -1342177280;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   protected int titleColour = -256;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   protected int bannerColour = -22016;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean drawHeader = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean drawMinButton = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean drawMenuButton = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean drawBackground = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean superDraw = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean bannerCentred = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   protected int bgBottomMargin = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean animate = true;
/*     */ 
/*     */ 
/*     */   
/*     */   protected String tooltip;
/*     */ 
/*     */ 
/*     */   
/* 100 */   private int currentPage = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   private int totalPages = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   private int targetPage = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   private long tweenBeginTime = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiDropDownMenu dropdown;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiScreenWithHeader(bib minecraft, int pages, int initialPage) {
/* 129 */     super(minecraft);
/*     */     
/* 131 */     this.totalPages = pages;
/* 132 */     this.currentPage = this.targetPage = Math.min(Math.max(initialPage, 0), pages);
/* 133 */     this.dropdown = new GuiDropDownMenu(minecraft, true);
/* 134 */     this.backColour = (minecraft.f != null) ? -1342177280 : -1728053248;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPage() {
/* 144 */     return this.currentPage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreenWithEnabledState(int mouseX, int mouseY, float partialTick, boolean enabled) {
/* 154 */     a(mouseX, mouseY, partialTick);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTick) {
/* 160 */     GL.glEnableAlphaTest();
/* 161 */     GL.glAlphaFunc(516, 0.1F);
/*     */ 
/*     */     
/* 164 */     if (this.drawHeader)
/*     */     {
/* 166 */       drawHeader(mouseY, mouseY, partialTick);
/*     */     }
/*     */     
/* 169 */     int offsetMouseX = drawBackground(mouseX, mouseY, partialTick);
/*     */ 
/*     */     
/* 172 */     drawPages(this.targetPage, offsetMouseX, mouseY, partialTick);
/*     */ 
/*     */     
/* 175 */     if (this.superDraw)
/*     */     {
/* 177 */       super.a(offsetMouseX, mouseY, partialTick);
/*     */     }
/*     */ 
/*     */     
/* 181 */     postRender(this.targetPage, offsetMouseX, mouseY, partialTick);
/*     */ 
/*     */     
/* 184 */     if (this.tooltip != null)
/*     */     {
/* 186 */       drawTooltip(this.tooltip, mouseX, mouseY, 16777215, -805306368);
/*     */     }
/*     */ 
/*     */     
/* 190 */     if (this.totalPages > 0)
/*     */     {
/* 192 */       GL.glPopMatrix();
/*     */     }
/*     */     
/* 195 */     if (this.drawMenuButton) {
/*     */       
/* 197 */       this.dropdown.drawControlAt(2, 20, mouseX, mouseY);
/* 198 */       if (this.dropdown.isDropDownVisible())
/*     */       {
/* 200 */         a(2, 19, 20, 22, -16777216);
/*     */       }
/*     */     } 
/*     */     
/* 204 */     GL.glAlphaFunc(516, 0.1F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawHeader(int mouseX, int mouseY, float partialTick) {
/* 209 */     int left = 2;
/*     */     
/* 211 */     if (this.drawMenuButton) {
/*     */       
/* 213 */       if (this.dropdown.isDropDownVisible())
/*     */       {
/* 215 */         a(1, 1, 21, 21, -1118482);
/*     */       }
/*     */       
/* 218 */       a(left, 2, left + 18, 20, this.dropdown.isDropDownVisible() ? (0xFF000000 | this.backColour) : this.backColour);
/*     */       
/* 220 */       this.j.N().a(ResourceLocations.MAIN);
/*     */       
/* 222 */       GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 223 */       this.renderer.drawTexturedModalRect(left + 3, 7, left + 15, 15, 104, 80, 128, 96);
/*     */       
/* 225 */       left += 20;
/*     */     } 
/*     */     
/* 228 */     if (this.totalPages > 0) {
/*     */ 
/*     */       
/* 231 */       a(left, 2, left + 18, 20, this.backColour);
/* 232 */       a(left + 20, 2, left + 158, 20, this.backColour);
/* 233 */       a(left + 160, 2, left + 178, 20, this.backColour);
/*     */       
/* 235 */       this.j.N().a(ResourceLocations.FIXEDWIDTHFONT);
/*     */ 
/*     */       
/* 238 */       float arrowColour = (this.targetPage > 0) ? 1.0F : 0.3F;
/* 239 */       GL.glColor4f(arrowColour, arrowColour, 0.0F, 1.0F);
/* 240 */       this.renderer.drawTexturedModalRect(left + 5, 7, left + 13, 15, 16, 16, 32, 32);
/*     */ 
/*     */       
/* 243 */       arrowColour = (this.targetPage < this.totalPages - 1) ? 1.0F : 0.4F;
/* 244 */       GL.glColor4f(arrowColour, arrowColour, 0.0F, 1.0F);
/* 245 */       this.renderer.drawTexturedModalRect(left + 165, 7, left + 173, 15, 0, 16, 16, 32);
/*     */     }
/*     */     else {
/*     */       
/* 249 */       a(left, 2, left + 178, 20, this.backColour);
/*     */     } 
/*     */ 
/*     */     
/* 253 */     a(this.l - 20, 2, this.l - 2, 20, this.backColour);
/*     */ 
/*     */     
/* 256 */     a(this.q, this.title, left + 88, 7, this.titleColour);
/*     */ 
/*     */     
/* 259 */     this.renderer.drawTexturedModalRect(ResourceLocations.MAIN, this.l - 17, 5, this.l - 5, 17, 104, 104, 128, 128);
/*     */     
/* 261 */     if (this.drawMinButton) {
/*     */       
/* 263 */       a(this.l - 42, 2, this.l - 22, 20, this.backColour);
/*     */       
/* 265 */       this.j.N().a(ResourceLocations.FIXEDWIDTHFONT);
/*     */ 
/*     */       
/* 268 */       GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 269 */       this.renderer.drawTexturedModalRect(this.l - 36, 7, this.l - 28, 15, 240, 16, 256, 32);
/*     */     } 
/*     */     
/* 272 */     this.renderer.drawTitle(this.banner, this.bannerCentred, left + 180, 2, this.l - (this.drawMinButton ? 44 : 22), this.bannerColour, this.backColour);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int drawBackground(int mouseX, int mouseY, float partialTick) {
/* 278 */     int offsetMouseX = mouseX;
/*     */ 
/*     */     
/* 281 */     if (this.totalPages > 0) {
/*     */       
/* 283 */       GL.glPushMatrix();
/*     */       
/* 285 */       if (this.targetPage != this.currentPage && !this.animate)
/*     */       {
/* 287 */         this.currentPage = this.targetPage;
/*     */       }
/*     */       
/* 290 */       if (this.targetPage == this.currentPage) {
/*     */ 
/*     */         
/* 293 */         GL.glTranslatef((this.l * this.currentPage * -1), 0.0F, 0.0F);
/* 294 */         offsetMouseX = mouseX + this.l * this.currentPage;
/*     */       }
/*     */       else {
/*     */         
/* 298 */         float tweenPct = (float)(bib.I() - this.tweenBeginTime) * 0.00125F;
/* 299 */         if (tweenPct >= 0.5F)
/*     */         {
/* 301 */           this.currentPage = this.targetPage;
/*     */         }
/*     */         
/* 304 */         tweenPct = (float)Math.sin(tweenPct * Math.PI);
/*     */         
/* 306 */         float pageOffset = (this.l * this.currentPage * -1);
/* 307 */         float targetPageOffset = ((this.l * this.targetPage * -1) - pageOffset) * tweenPct;
/*     */         
/* 309 */         GL.glTranslatef(pageOffset + targetPageOffset, 0.0F, 0.0F);
/* 310 */         offsetMouseX = mouseX - (int)(pageOffset + targetPageOffset);
/*     */       } 
/*     */       
/* 313 */       if (this.drawBackground)
/*     */       {
/* 315 */         for (int pg = 0; pg < this.totalPages; pg++)
/*     */         {
/* 317 */           int offset = this.l * pg;
/* 318 */           a(2 + offset, 22, this.l - 2 + offset, this.m - this.bgBottomMargin, this.backColour);
/*     */         }
/*     */       
/*     */       }
/* 322 */     } else if (this.drawBackground) {
/*     */       
/* 324 */       a(2, 22, this.l - 2, this.m - this.bgBottomMargin, this.backColour);
/*     */     } 
/*     */     
/* 327 */     return offsetMouseX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void beginTweening(int targetPage) {
/* 336 */     if (targetPage >= 0 && targetPage < this.totalPages && targetPage != this.targetPage) {
/*     */       
/* 338 */       this.targetPage = targetPage;
/* 339 */       this.tweenBeginTime = bib.I();
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
/*     */   protected void drawPages(int currentPage, int mouseX, int mouseY, float partialTick) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postRender(int currentPage, int mouseX, int mouseY, float partialTick) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 372 */     if (this.drawHeader && button == 0) {
/*     */       
/* 374 */       boolean wasVisible = this.dropdown.isDropDownVisible();
/* 375 */       int headerLeft = handleMenuClick(mouseX, mouseY);
/*     */       
/* 377 */       if (mouseX > 2 && mouseY > 2 && mouseY < 21 && headerLeft > -1) {
/*     */         
/* 379 */         if (mouseX > headerLeft + 180)
/*     */         {
/* 381 */           handleHeaderClick(mouseX);
/*     */         }
/*     */         else
/*     */         {
/* 385 */           if (this.drawMenuButton && mouseX < headerLeft && mouseX > 2 && !wasVisible)
/*     */           {
/* 387 */             onMenuButtonClick();
/*     */           }
/*     */           
/* 390 */           if (mouseX > headerLeft)
/*     */           {
/* 392 */             handleTitleClick(mouseX, headerLeft);
/*     */           }
/*     */         }
/*     */       
/* 396 */       } else if (headerLeft == -1) {
/*     */         return;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 402 */     mouseX += this.l * this.currentPage;
/*     */     
/* 404 */     mouseClickedEx(mouseX, mouseY, button);
/* 405 */     super.a(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleTitleClick(int mouseX, int headerLeft) {
/* 410 */     if (this.totalPages > 0) {
/*     */       
/* 412 */       if (mouseX < headerLeft + 20)
/*     */       {
/* 414 */         onPageDownClick();
/*     */       }
/* 416 */       else if (mouseX > headerLeft + 160)
/*     */       {
/* 418 */         onPageUpClick();
/*     */       }
/*     */       else
/*     */       {
/* 422 */         onTitleClick();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 427 */       onTitleClick();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleHeaderClick(int mouseX) {
/* 433 */     if (mouseX > this.l - 22) {
/*     */       
/* 435 */       onCloseClick();
/*     */     }
/* 437 */     else if (mouseX > this.l - 44 && this.drawMinButton) {
/*     */       
/* 439 */       onMinimiseClick();
/*     */     }
/*     */     else {
/*     */       
/* 443 */       onHeaderClick();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int handleMenuClick(int mouseX, int mouseY) {
/* 449 */     if (!this.drawMenuButton)
/*     */     {
/* 451 */       return 2;
/*     */     }
/*     */     
/* 454 */     GuiDropDownMenu.Item menuItem = this.dropdown.mousePressed(mouseX, mouseY);
/* 455 */     if (menuItem != null) {
/*     */       
/* 457 */       onMenuItemClicked(menuItem.getTag());
/* 458 */       return -1;
/*     */     } 
/*     */     
/* 461 */     return 22;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NavAction getNavAction(int mouseX, int mouseY) {
/* 471 */     if (mouseX < 3)
/*     */     {
/* 473 */       return NavAction.PREVIOUS;
/*     */     }
/* 475 */     if (mouseX > this.l - 3)
/*     */     {
/* 477 */       return NavAction.NEXT;
/*     */     }
/* 479 */     if (mouseY < 20) {
/*     */       
/* 481 */       int headerLeft = this.drawMenuButton ? 20 : 2;
/* 482 */       if (mouseX > headerLeft && mouseX < headerLeft + 20)
/*     */       {
/* 484 */         return NavAction.PREVIOUS;
/*     */       }
/* 486 */       if (mouseX > headerLeft + 160 && mouseX < headerLeft + 182)
/*     */       {
/* 488 */         return NavAction.NEXT;
/*     */       }
/*     */     } 
/*     */     
/* 492 */     return NavAction.NONE;
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
/*     */   protected void mouseClickedEx(int mouseX, int mouseY, int button) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void b(int mouseX, int mouseY, int button) {
/* 513 */     mouseX += this.l * this.currentPage;
/* 514 */     super.b(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onPageUpClick() {
/* 519 */     if (this.currentPage < this.totalPages)
/*     */     {
/* 521 */       beginTweening(this.targetPage + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onPageDownClick() {
/* 527 */     if (this.currentPage > 0)
/*     */     {
/* 529 */       beginTweening(this.targetPage - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onHeaderClick() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTitleClick() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMinimiseClick() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMenuButtonClick() {
/* 550 */     if (this.drawMenuButton)
/*     */     {
/* 552 */       this.dropdown.showDropDown();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMenuItemClicked(String menuItem) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCloseClick() {
/* 563 */     this.j.a((this.j.f != null) ? null : (blk)new bme(null, this.j.t));
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiScreenWithHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */