/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bib;
/*     */ import blk;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.GuiPlaybackStatus;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.controls.GuiMiniToolbarButton;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*     */ import net.eq2online.macros.gui.interfaces.IMinimisable;
/*     */ import net.eq2online.macros.gui.interfaces.IMinimisableHost;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiMacroPlayback
/*     */   extends GuiCustomGui
/*     */   implements IMinimisableHost
/*     */ {
/*     */   private final InputHandler inputHandler;
/*     */   private final boolean activeOverride;
/*     */   private Rectangle playbackStatusRect;
/*     */   
/*     */   public GuiMacroPlayback(Macros macros, bib minecraft) {
/*  35 */     this(macros, minecraft, false, (blk)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiMacroPlayback(Macros macros, bib minecraft, boolean handleOverride, blk parentScreen) {
/*  40 */     super(macros, minecraft, macros.getLayoutManager().getBoundLayout("playback", false), parentScreen);
/*     */     
/*  42 */     this.activeOverride = handleOverride;
/*  43 */     this.p = handleOverride;
/*  44 */     this.prompt = I18n.get("macro.prompt.execute");
/*  45 */     this.inputHandler = macros.getInputHandler();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiScreenEx getDelegate() {
/*  51 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/*  60 */     super.b();
/*     */     
/*  62 */     this.prompt = I18n.get("macro.prompt.execute");
/*     */     
/*  64 */     this.miniButtons.add(this.btnBind = new GuiMiniToolbarButton(this.j, 0, 104, 48));
/*  65 */     this.miniButtons.add(this.btnEditFile = new GuiMiniToolbarButton(this.j, 1, 104, 16));
/*  66 */     this.miniButtons.add(this.btnOptions = new GuiMiniToolbarButton(this.j, 2, 104, 0));
/*     */     
/*  68 */     this.btnBind.setColours(-16716288, -1342177280).setTooltip("tooltip.bind");
/*  69 */     this.btnEditFile.setColours(-16716288, -1342177280).setTooltip("tooltip.editfile");
/*  70 */     this.btnOptions.setColours(-16716288, -1342177280).setTooltip("tooltip.options");
/*     */     
/*  72 */     this.playbackStatusRect = new Rectangle(4, 10, this.l - 4, this.m - 10);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Rectangle updateBoundingBox() {
/*  78 */     return this.macros.getLayoutManager().getBinding("playback").getBoundingBox(super.updateBoundingBox());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/*  88 */     if (this.updateCounter < 1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  93 */     if (keyCode == 1) {
/*     */       
/*  95 */       this.j.a(this.activeOverride ? this.parentScreen : null);
/*     */       
/*     */       return;
/*     */     } 
/*  99 */     if ((this.macros.getSettings()).enableOverride && keyCode == this.inputHandler.getOverrideKeyCode()) {
/*     */       
/* 101 */       this.j.a(this.activeOverride ? this.parentScreen : null);
/*     */       
/*     */       return;
/*     */     } 
/* 105 */     if (keyCode > 0) {
/*     */       
/* 107 */       this.macros.getInputHandler().notifyKeyDown(keyCode);
/* 108 */       if (!this.activeOverride)
/*     */       {
/* 110 */         this.j.a(null);
/*     */       }
/* 112 */       this.macros.playMacro(keyCode, this.activeOverride, ScriptContext.MAIN, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void l() throws IOException {
/* 123 */     if (this.activeOverride && !InputHandler.isKeyDown(this.inputHandler.getOverrideKeyCode())) {
/*     */       
/* 125 */       this.j.a(this.parentScreen);
/*     */       
/*     */       return;
/*     */     } 
/* 129 */     super.l();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 138 */     IMinimisable minimised = this.macros.getMinimised(true);
/* 139 */     if (minimised != null) {
/*     */       
/* 141 */       minimised.show(this);
/*     */       
/*     */       return;
/*     */     } 
/* 145 */     setLayout(this.macros.getLayoutManager().getBoundLayout("playback", false));
/* 146 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postDrawControls(int mouseX, int mouseY, float partialTicks) {
/* 156 */     if (!this.activeOverride || (this.macros.getSettings()).enableStatus)
/*     */     {
/* 158 */       this.macros.getPlaybackStatus().drawOverlay(this.playbackStatusRect, mouseX, mouseY, true);
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
/*     */   protected void preDrawScreen(int mouseX, int mouseY, float partialTicks) {
/* 170 */     if (this.activeOverride && this.parentScreen != null)
/*     */     {
/* 172 */       this.parentScreen.a(mouseX, mouseY, partialTicks);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 183 */     if (contextMenuClicked(mouseX, mouseY, button) || 
/* 184 */       miniToolbarClicked(mouseX, mouseY) || 
/* 185 */       handleSpecialClick(mouseX, mouseY, button) || 
/* 186 */       controlClicked(mouseX, mouseY, button)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 191 */     a(' ', button + 250);
/* 192 */     onUnhandledMouseClick(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleSpecialClick(int mouseX, int mouseY, int button) {
/* 202 */     GuiPlaybackStatus playbackStatus = this.macros.getPlaybackStatus();
/*     */ 
/*     */     
/* 205 */     if ((!this.activeOverride || (this.macros.getSettings()).enableStatus) && button == 0 && playbackStatus.isOverlayMode())
/*     */     {
/* 207 */       return playbackStatus.mouseClick(this.playbackStatusRect, mouseX, mouseY);
/*     */     }
/*     */     
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDispatch(DesignableGuiControl source) {
/* 216 */     if (!this.activeOverride)
/*     */     {
/* 218 */       super.onDispatch(source);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiMacroPlayback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */