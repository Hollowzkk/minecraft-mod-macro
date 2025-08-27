/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bib;
/*     */ import blk;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.controls.GuiDropDownMenu;
/*     */ import net.eq2online.macros.gui.controls.GuiMiniToolbarButton;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.input.IProhibitOverride;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import org.lwjgl.input.Mouse;
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
/*     */ public class GuiCustomGui
/*     */   extends GuiScreenEx
/*     */   implements DesignableGuiControl.Listener, IProhibitOverride
/*     */ {
/*     */   protected static final int MINI_BUTTON_TOOLTIP_FG = -1118482;
/*     */   protected static final int MINI_BUTTON_FG = -16716288;
/*     */   protected static final int MINI_BUTTON_BG = -1342177280;
/*     */   private static boolean mouseCoordsAvailable = false;
/*     */   private static int lastMouseX;
/*     */   private static int lastMouseY;
/*     */   protected final Macros macros;
/*  46 */   protected blk parentScreen = null;
/*     */   
/*     */   protected GuiMiniToolbarButton btnGui;
/*     */   protected GuiMiniToolbarButton btnBind;
/*     */   protected GuiMiniToolbarButton btnEditFile;
/*     */   protected GuiMiniToolbarButton btnOptions;
/*     */   private DesignableGuiLayout layout;
/*     */   private DesignableGuiLayout backLayout;
/*     */   private Rectangle boundingBox;
/*     */   private GuiDropDownMenu contextMenu;
/*  56 */   private Point contextMenuLocation = new Point();
/*     */   
/*  58 */   private DesignableGuiLayout.ClickedControlInfo clickedControl = null;
/*     */   
/*     */   private boolean enableTriggers = false;
/*     */ 
/*     */   
/*     */   public GuiCustomGui(Macros macros, bib minecraft, String layout, String backLayout, boolean enableTriggers) {
/*  64 */     this(macros, minecraft, macros.getLayoutManager().getLayout(layout), (blk)null);
/*     */     
/*  66 */     if (backLayout != null)
/*     */     {
/*  68 */       this.backLayout = this.macros.getLayoutManager().getLayout(backLayout);
/*     */     }
/*     */     
/*  71 */     this.enableTriggers = enableTriggers;
/*     */   }
/*     */ 
/*     */   
/*     */   protected GuiCustomGui(Macros macros, bib minecraft, DesignableGuiLayout layout, blk parentScreen) {
/*  76 */     super(minecraft);
/*     */     
/*  78 */     this.macros = macros;
/*  79 */     this.parentScreen = parentScreen;
/*  80 */     this.layout = layout;
/*     */     
/*  82 */     this.contextMenu = new GuiDropDownMenu(minecraft, true);
/*  83 */     this.contextMenu.addItem("execute", I18n.get("layout.contextmenu.execute"));
/*  84 */     this.contextMenu.addItem("edit", I18n.get("layout.contextmenu.edit"));
/*  85 */     this.contextMenu.addItem("design", "§e" + I18n.get("layout.contextmenu.design"), 26, 16);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean d() {
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public DesignableGuiLayout getLayout() {
/*  96 */     return this.layout;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setLayout(DesignableGuiLayout layout) {
/* 101 */     this.layout = layout;
/* 102 */     this.boundingBox = updateBoundingBox();
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiDropDownMenu getContextMenu() {
/* 107 */     return this.contextMenu;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 113 */     super.b();
/*     */     
/* 115 */     if (this.layout != null)
/*     */     {
/* 117 */       this.prompt = this.layout.getDisplayName();
/*     */     }
/*     */     
/* 120 */     this.miniButtons.clear();
/* 121 */     this.miniButtons.add(this.btnGui = new GuiMiniToolbarButton(this.j, 4, 104, 64));
/*     */     
/* 123 */     this.btnGui.setColours(-16716288, -1342177280).setTooltip("tooltip.guiedit");
/*     */     
/* 125 */     this.boundingBox = updateBoundingBox();
/*     */     
/* 127 */     if (mouseCoordsAvailable)
/*     */     {
/* 129 */       Mouse.setCursorPosition(lastMouseX, lastMouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Rectangle updateBoundingBox() {
/* 135 */     return new Rectangle(0, 0, this.l, this.m - 14);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/* 145 */     if (this.updateCounter < 1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 150 */     if (keyCode == 1) {
/*     */       
/* 152 */       closeGui();
/*     */       
/*     */       return;
/*     */     } 
/* 156 */     if (this.layout != null) {
/*     */       
/* 158 */       boolean handled = false;
/*     */       
/* 160 */       for (DesignableGuiControl control : this.layout.getControls())
/*     */       {
/* 162 */         handled |= control.handleKeyTyped(keyChar, keyCode, this);
/*     */       }
/*     */       
/* 165 */       if (handled) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 171 */     if (keyCode > 0 && this.enableTriggers) {
/*     */       
/* 173 */       this.macros.getInputHandler().notifyKeyDown(keyCode);
/* 174 */       this.macros.playMacro(keyCode, false, ScriptContext.MAIN, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean closeGui() {
/* 180 */     if (this.backLayout != null) {
/*     */       
/* 182 */       this.j.a((blk)new GuiCustomGui(this.macros, this.j, this.backLayout, null));
/* 183 */       return false;
/*     */     } 
/*     */     
/* 186 */     this.j.a(this.parentScreen);
/* 187 */     return (this.parentScreen == null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 193 */     if (this.layout != null)
/*     */     {
/* 195 */       this.layout.onTick(this.updateCounter);
/*     */     }
/*     */     
/* 198 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTicks) {
/* 207 */     preDrawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 209 */     GL.glDisableLighting();
/* 210 */     GL.glDisableDepthTest();
/*     */     
/* 212 */     drawControls(mouseX, mouseY);
/* 213 */     postDrawControls(mouseX, mouseY, partialTicks);
/*     */     
/* 215 */     drawMiniButtons(mouseX, mouseY, partialTicks);
/* 216 */     drawPromptBar(mouseX, mouseY, partialTicks, -16716288, -1342177280);
/* 217 */     super.a(mouseX, mouseY, partialTicks);
/* 218 */     postDrawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 220 */     if (this.hoverButton != null)
/*     */     {
/* 222 */       drawTooltip(this.hoverButton.getTooltip(), mouseX, mouseY, -1118482, -1342177280);
/*     */     }
/*     */     
/* 225 */     this.contextMenu.drawControlAt(this.contextMenuLocation, mouseX, mouseY);
/*     */     
/* 227 */     mouseCoordsAvailable = true;
/* 228 */     lastMouseX = Mouse.getX();
/* 229 */     lastMouseY = Mouse.getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preDrawScreen(int mouseX, int mouseY, float partialTicks) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postDrawControls(int mouseX, int mouseY, float partialTicks) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postDrawScreen(int mouseX, int mouseY, float partialTicks) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawMiniButtons(int mouseX, int mouseY, float partialTicks) {
/* 250 */     super.drawMiniButtons(mouseX, mouseY, partialTicks);
/*     */     
/* 252 */     drawMiniButton(this.btnOptions, mouseX, mouseY, partialTicks);
/* 253 */     drawMiniButton(this.btnEditFile, mouseX, mouseY, partialTicks);
/* 254 */     drawMiniButton(this.btnBind, mouseX, mouseY, partialTicks);
/* 255 */     drawMiniButton(this.btnGui, mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawControls(int mouseX, int mouseY) {
/* 264 */     if (this.layout != null && this.boundingBox != null)
/*     */     {
/* 266 */       this.layout.draw(this.boundingBox, mouseX, mouseY, true);
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
/* 277 */     if (contextMenuClicked(mouseX, mouseY, button))
/* 278 */       return;  if (miniToolbarClicked(mouseX, mouseY))
/* 279 */       return;  if (controlClicked(mouseX, mouseY, button))
/* 280 */       return;  super.a(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button, long timeSinceLastClick) {
/* 286 */     if (this.clickedControl != null && button == 0)
/*     */     {
/* 288 */       this.layout.handleMouseMove(this.boundingBox, mouseX, mouseY, this.clickedControl);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void b(int mouseX, int mouseY, int button) {
/* 295 */     if (this.clickedControl != null && button == 0)
/*     */     {
/* 297 */       this.layout.handleMouseReleased(this.boundingBox, mouseX, mouseY, this.clickedControl);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean contextMenuClicked(int mouseX, int mouseY, int button) {
/* 308 */     GuiDropDownMenu.Item item = this.contextMenu.mousePressed(mouseX, mouseY);
/* 309 */     if (item != null) {
/*     */       
/* 311 */       String tag = item.getTag();
/* 312 */       if ("execute".equals(tag)) {
/*     */         
/* 314 */         this.clickedControl.handleClick(mouseX, mouseY, this);
/*     */       }
/* 316 */       else if ("edit".equals(tag)) {
/*     */         
/* 318 */         if (this.clickedControl.control.isBindable())
/*     */         {
/* 320 */           this.j.a((blk)GuiMacroEdit.create(this.macros, this.j, (blk)this, this.clickedControl.control.id));
/*     */         }
/*     */       }
/* 323 */       else if ("design".equals(tag)) {
/*     */         
/* 325 */         this.j.a((blk)new GuiMacroBind(this.macros, this.j, 2, (blk)this));
/*     */       } 
/*     */       
/* 328 */       return true;
/*     */     } 
/*     */     
/* 331 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean miniToolbarClicked(int mouseX, int mouseY) {
/* 341 */     for (GuiMiniToolbarButton miniButton : this.miniButtons) {
/*     */       
/* 343 */       if (miniButton.b(this.j, mouseX, mouseY)) {
/*     */         
/* 345 */         onMiniButtonClicked(miniButton);
/* 346 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 350 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean controlClicked(int mouseX, int mouseY, int button) {
/* 360 */     if (this.layout != null && this.boundingBox != null) {
/*     */       
/* 362 */       this.clickedControl = this.layout.getControlAt(this.boundingBox, mouseX, mouseY, null);
/*     */       
/* 364 */       if (this.clickedControl != null && !this.clickedControl.control.isVisible())
/*     */       {
/* 366 */         this.clickedControl = null;
/*     */       }
/*     */       
/* 369 */       if (this.clickedControl != null) {
/*     */         
/* 371 */         if (button == 0) {
/*     */           
/* 373 */           this.clickedControl.handleClick(mouseX, mouseY, this);
/*     */         }
/* 375 */         else if (button == 1) {
/*     */           
/* 377 */           this.contextMenu.getItem("execute").setDisabled(!this.clickedControl.control.isBindable());
/* 378 */           this.contextMenu.getItem("edit").setDisabled(!this.clickedControl.control.isBindable());
/* 379 */           this.contextMenu.showDropDown();
/* 380 */           Dimension contextMenuSize = this.contextMenu.getSize();
/* 381 */           this
/*     */             
/* 383 */             .contextMenuLocation = new Point(Math.min(mouseX, this.l - contextMenuSize.width), Math.min(mouseY - 8, this.m - contextMenuSize.height));
/*     */         } 
/*     */ 
/*     */         
/* 387 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 391 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDispatch(DesignableGuiControl source) {
/* 397 */     this.j.a(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onUnhandledMouseClick(int mouseX, int mouseY, int button) throws IOException {
/* 407 */     super.a(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onMiniButtonClicked(GuiMiniToolbarButton button) {
/* 412 */     if (this.btnGui != null && button.k == this.btnGui.k) {
/*     */       
/* 414 */       this.j.a((blk)new GuiDesigner(this.macros, this.j, this.layout, (blk)this, true));
/*     */     }
/* 416 */     else if (this.btnBind != null && button.k == this.btnBind.k) {
/*     */       
/* 418 */       this.j.a((blk)new GuiMacroBind(this.macros, this.j, true, true, (blk)this));
/*     */     }
/* 420 */     else if (this.btnEditFile != null && button.k == this.btnEditFile.k) {
/*     */       
/* 422 */       this.j.a((blk)new GuiEditTextFile(this.macros, this.j, this, ScriptContext.MAIN));
/*     */     }
/* 424 */     else if (this.btnOptions != null && button.k == this.btnOptions.k) {
/*     */       
/* 426 */       this.j.a((blk)new GuiMacroConfig(this.macros, this.j, this, false));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiCustomGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */