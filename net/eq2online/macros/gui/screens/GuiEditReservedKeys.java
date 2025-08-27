/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bib;
/*     */ import bja;
/*     */ import blk;
/*     */ import cey;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanelKeys;
/*     */ import net.eq2online.macros.gui.layout.PanelManager;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.interfaces.IEditablePanel;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiEditReservedKeys
/*     */   extends GuiScreenEx
/*     */ {
/*     */   private final Macros macros;
/*     */   private final GuiScreenEx parentScreen;
/*     */   private final PanelManager panelManager;
/*     */   private final int activateCode;
/*     */   private final int sneakCode;
/*     */   private GuiControl btnOk;
/*  33 */   private int suspendInput = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiEditReservedKeys(Macros macros, bib minecraft, GuiScreenEx parentScreen) {
/*  43 */     super(minecraft);
/*     */     
/*  45 */     this.e = 999.0F;
/*     */     
/*  47 */     this.macros = macros;
/*  48 */     this.parentScreen = parentScreen;
/*  49 */     this.panelManager = this.macros.getLayoutPanels();
/*  50 */     this.activateCode = InputHandler.KEY_ACTIVATE.j();
/*  51 */     this.sneakCode = this.macros.getInputHandler().getSneakKeyCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/*  60 */     this.suspendInput = 4;
/*     */     
/*  62 */     clearControlList();
/*     */     
/*  64 */     this.btnOk = new GuiControl(2, this.l / 2 - 40, this.m - 28, 80, 20, cey.a("gui.done", new Object[0]));
/*  65 */     addControl(this.btnOk);
/*     */     
/*  67 */     LayoutPanelKeys keyboard = this.panelManager.getKeyboardLayout();
/*  68 */     keyboard.connect(null);
/*  69 */     keyboard.setSizeAndPosition(0, 22, this.l, this.m - 50);
/*  70 */     addControl((GuiControl)keyboard);
/*     */     
/*  72 */     this.panelManager.setMode(IEditablePanel.EditMode.RESERVE);
/*     */     
/*  74 */     super.b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  83 */     if (this.suspendInput > 0)
/*     */     {
/*  85 */       this.suspendInput--;
/*     */     }
/*     */     
/*  88 */     this.panelManager.tickInGui();
/*  89 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bja guibutton) {
/* 100 */     if (guibutton.k == this.btnOk.k)
/*     */     {
/* 102 */       closeGui();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void closeGui() {
/* 108 */     this.panelManager.getKeyboardLayout().release();
/* 109 */     this.j.a((blk)this.parentScreen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/* 118 */     if (keyCode == 1 || keyCode == 28 || keyCode == 156 || (keyCode == this.activateCode && 
/*     */ 
/*     */       
/* 121 */       InputHandler.isKeyDown(this.sneakCode)))
/*     */     {
/* 123 */       closeGui();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 131 */     if (this.suspendInput > 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 136 */     if (mouseX > this.l - 20 && mouseY < 20)
/*     */     {
/* 138 */       closeGui();
/*     */     }
/*     */     
/* 141 */     super.a(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTicks) {
/* 147 */     if (this.j.f == null) {
/*     */       
/* 149 */       c();
/* 150 */       GL.glClear(256);
/*     */     } 
/*     */     
/* 153 */     drawBackground();
/*     */ 
/*     */     
/* 156 */     a(this.q, I18n.get("options.option.reserved"), 90, 7, 16776960);
/* 157 */     this.renderer.drawTexturedModalRect(ResourceLocations.MAIN, this.l - 17, 5, this.l - 5, 17, 104, 104, 128, 128);
/* 158 */     this.btnOk.a(this.j, mouseX, mouseY, partialTicks);
/*     */ 
/*     */     
/* 161 */     this.panelManager.getKeyboardLayout().a(this.j, mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawBackground() {
/* 172 */     int backColour = (this.j.f != null) ? -1342177280 : -1728053248;
/*     */     
/* 174 */     a(2, 2, 180, 20, backColour);
/* 175 */     a(182, 2, this.l - 22, 20, backColour);
/* 176 */     a(this.l - 20, 2, this.l - 2, 20, backColour);
/*     */     
/* 178 */     a(2, 22, this.l - 2, this.m - 2, backColour);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiEditReservedKeys.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */