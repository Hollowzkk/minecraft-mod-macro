/*     */ package net.eq2online.macros.core.mixin;
/*     */ 
/*     */ import bib;
/*     */ import bje;
/*     */ import bkn;
/*     */ import blk;
/*     */ import com.mumfrey.liteloader.client.overlays.IGuiTextField;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.overlays.IGuiChat;
/*     */ import net.eq2online.macros.core.settings.Settings;
/*     */ import net.eq2online.macros.gui.controls.GuiDropDownMenu;
/*     */ import net.eq2online.macros.gui.controls.GuiMiniToolbarButton;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.screens.GuiDesigner;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroEdit;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({bkn.class})
/*     */ public abstract class MixinGuiChat
/*     */   extends blk
/*     */   implements IGuiChat, DesignableGuiControl.Listener
/*     */ {
/*     */   private Macros macros;
/*     */   private Settings settings;
/*     */   private DesignableGuiLayout layout;
/*  44 */   private Rectangle boundingBox = new Rectangle();
/*     */   private GuiDropDownMenu contextMenu;
/*  46 */   private Point contextMenuLocation = new Point();
/*  47 */   private DesignableGuiLayout.ClickedControlInfo clickedControl = null;
/*     */   
/*     */   protected GuiMiniToolbarButton btnGui;
/*     */   private int realWidth;
/*     */   private int realHeight;
/*     */   private int tickNumber;
/*     */   private bje textField;
/*     */   
/*     */   @Inject(method = {"<init>*"}, at = {@At("RETURN")})
/*     */   private void onConstructed(CallbackInfo ci) {
/*  57 */     this.macros = Macros.getInstance();
/*  58 */     this.settings = this.macros.getSettings();
/*     */     
/*  60 */     this.contextMenu = new GuiDropDownMenu(bib.z(), true);
/*  61 */     this.contextMenu.addItem("execute", I18n.get("gui.context.execute"));
/*  62 */     this.contextMenu.addItem("edit", I18n.get("gui.context.edit"));
/*  63 */     this.contextMenu.addItem("design", "§e" + I18n.get("tooltip.guiedit"), 26, 16);
/*     */ 
/*     */     
/*  66 */     this.btnGui = new GuiMiniToolbarButton(bib.z(), 4, 104, 64);
/*  67 */     this.layout = this.macros.getLayoutManager().getBoundLayout("inchat", false);
/*     */   }
/*     */ 
/*     */   
/*     */   @Redirect(method = {"initGui()V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiTextField;setCanLoseFocus(Z)V"))
/*     */   private void onSetTextField(bje textField, boolean canLoseFocus) {
/*  73 */     this.textField = textField;
/*  74 */     textField.d(canLoseFocus);
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"keyTyped(CI)V"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onKeyTyped(char keyChar, int keyCode, CallbackInfo ci) {
/*  80 */     if (this.layout != null && keyChar > '\000' && InputHandler.isAltDown())
/*     */     {
/*  82 */       for (DesignableGuiControl control : this.layout.getControls())
/*     */       {
/*  84 */         control.handleKeyTyped(keyChar, keyCode, this);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"drawScreen(IIF)V"}, at = {@At("HEAD")})
/*     */   private void onDrawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
/*  92 */     if (this.settings.enableButtonsOnChatGui) {
/*     */       
/*  94 */       if (this.realWidth != this.l || this.realHeight != this.m) {
/*     */         
/*  96 */         this.realWidth = this.l;
/*  97 */         this.realHeight = this.m;
/*  98 */         this.boundingBox = updateBoundingBox();
/*     */         
/* 100 */         bje textField = this.textField;
/* 101 */         if (textField != null) {
/*     */           
/* 103 */           int internalWidth = ((IGuiTextField)textField).getInternalWidth();
/* 104 */           ((IGuiTextField)textField).setInternalWidth(internalWidth - 20);
/*     */         } 
/*     */       } 
/*     */       
/* 108 */       this.layout = this.macros.getLayoutManager().getBoundLayout("inchat", false);
/* 109 */       this.l = this.realWidth - 20;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Rectangle updateBoundingBox() {
/* 115 */     Rectangle boundingBox = new Rectangle(0, 0, this.realWidth, this.realHeight - 14);
/* 116 */     return this.macros.getLayoutManager().getBinding("inchat").getBoundingBox(boundingBox);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"drawScreen(IIF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiTextField;drawTextBox()V", shift = At.Shift.AFTER)})
/*     */   private void onDrawChatField(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
/* 125 */     if (this.settings.enableButtonsOnChatGui) {
/*     */       
/* 127 */       this.l = this.realWidth;
/*     */       
/* 129 */       if (this.layout != null && this.boundingBox != null)
/*     */       {
/* 131 */         this.layout.draw(this.boundingBox, mouseX, mouseY, true);
/*     */       }
/*     */       
/* 134 */       if (this.btnGui.setColours(-16716288, -2147483648).drawControlAt(this.j, mouseX, mouseY, partialTicks, this.realWidth - 20, this.realHeight - 14))
/*     */       {
/*     */         
/* 137 */         drawTooltip(I18n.get("tooltip.guiedit"), mouseX, mouseY, -1118482, -1342177280);
/*     */       }
/*     */       
/* 140 */       this.contextMenu.drawControlAt(this.contextMenuLocation, mouseX, mouseY);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"updateScreen()V"}, at = {@At("RETURN")})
/*     */   private void onUpdateChatField(CallbackInfo ci) {
/* 150 */     if (this.settings.enableButtonsOnChatGui && this.layout != null)
/*     */     {
/* 152 */       this.layout.onTick(this.tickNumber++);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"mouseClicked(III)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiTextField;mouseClicked(III)Z")}, cancellable = true)
/*     */   private void onMouseClicked(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
/* 163 */     if (this.settings.enableButtonsOnChatGui && handleMouseClicked(mouseX, mouseY, mouseButton))
/*     */     {
/* 165 */       ci.cancel();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button, long timeSinceLastClick) {
/* 172 */     if (this.layout != null && this.clickedControl != null && button == 0)
/*     */     {
/* 174 */       this.layout.handleMouseMove(this.boundingBox, mouseX, mouseY, this.clickedControl);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void b(int mouseX, int mouseY, int button) {
/* 181 */     if (this.layout != null && this.clickedControl != null && button == 0)
/*     */     {
/* 183 */       this.layout.handleMouseReleased(this.boundingBox, mouseX, mouseY, this.clickedControl);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeText(String chars) {
/* 190 */     this.textField.b(chars);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleMouseClicked(int mouseX, int mouseY, int button) {
/* 200 */     boolean handled = false;
/*     */     
/* 202 */     GuiDropDownMenu.Item item = this.contextMenu.mousePressed(mouseX, mouseY);
/* 203 */     if (item != null) {
/*     */       
/* 205 */       String tag = item.getTag();
/* 206 */       if ("execute".equals(tag) && this.clickedControl != null) {
/*     */         
/* 208 */         this.clickedControl.handleClick(mouseX, mouseY, this);
/* 209 */         return true;
/*     */       } 
/* 211 */       if ("edit".equals(tag) && this.clickedControl != null) {
/*     */         
/* 213 */         if (this.clickedControl.control.isBindable())
/*     */         {
/* 215 */           this.j.a((blk)GuiMacroEdit.create(this.macros, this.j, this.j.m, this.clickedControl.control.id));
/*     */         }
/* 217 */         return true;
/*     */       } 
/* 219 */       if ("design".equals(tag)) {
/*     */         
/* 221 */         this.j.a((blk)new GuiDesigner(this.macros, this.j, "inchat", this.j.m, true));
/* 222 */         return true;
/*     */       } 
/* 224 */       if (!"properties".equals(tag) || this.clickedControl != null);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 229 */       handled = true;
/*     */     } 
/*     */     
/* 232 */     if (button == 0 && this.btnGui.b(this.j, mouseX, mouseY)) {
/*     */       
/* 234 */       this.j.a((blk)new GuiDesigner(this.macros, this.j, "inchat", this.j.m, true));
/* 235 */       return true;
/*     */     } 
/*     */     
/* 238 */     if (!handled && this.layout != null && this.boundingBox != null) {
/*     */       
/* 240 */       this.clickedControl = this.layout.getControlAt(this.boundingBox, mouseX, mouseY, null);
/*     */       
/* 242 */       if (this.clickedControl != null && !this.clickedControl.control.isVisible())
/*     */       {
/* 244 */         this.clickedControl = null;
/*     */       }
/*     */       
/* 247 */       if (this.clickedControl != null)
/*     */       {
/* 249 */         if (button == 0) {
/*     */           
/* 251 */           handled = true;
/* 252 */           this.clickedControl.handleClick(mouseX, mouseY, this);
/* 253 */           return true;
/*     */         } 
/*     */       }
/*     */       
/* 257 */       if (!handled && button == 1) {
/*     */         
/* 259 */         this.contextMenu.getItem("execute").setDisabled((this.clickedControl == null || !this.clickedControl.control.isBindable()));
/* 260 */         this.contextMenu.getItem("edit").setDisabled((this.clickedControl == null || !this.clickedControl.control.isBindable()));
/* 261 */         this.contextMenu.showDropDown();
/* 262 */         Dimension contextMenuSize = this.contextMenu.getSize();
/* 263 */         this.contextMenuLocation = new Point(Math.min(mouseX, this.realWidth - contextMenuSize.width), Math.min(mouseY - 8, this.realHeight - contextMenuSize.height));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 268 */     return handled;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDispatch(DesignableGuiControl source) {
/* 274 */     this.j.a(null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawTooltip(String tooltipText, int mouseX, int mouseY, int colour, int backgroundColour) {
/* 279 */     int textSize = this.q.a(tooltipText);
/* 280 */     mouseX = Math.min(this.realWidth - textSize - 6, mouseX - 6);
/* 281 */     mouseY = Math.max(0, mouseY - 18);
/*     */     
/* 283 */     a(mouseX, mouseY, mouseX + textSize + 6, mouseY + 16, backgroundColour);
/* 284 */     c(this.q, tooltipText, mouseX + 3, mouseY + 4, colour);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\MixinGuiChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */