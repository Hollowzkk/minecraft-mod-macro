package net.eq2online.macros.fabric.mixin;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.eq2online.macros.compatibility.I18n;
import net.eq2online.macros.core.Macros;
import net.eq2online.macros.core.overlays.IGuiChat;
import net.eq2online.macros.core.settings.Settings;
import net.eq2online.macros.gui.controls.GuiDropDownMenu;
import net.eq2online.macros.gui.controls.GuiMiniToolbarButton;
import net.eq2online.macros.gui.designable.DesignableGuiControl;
import net.eq2online.macros.gui.designable.DesignableGuiLayout;
import net.eq2online.macros.gui.screens.GuiDesigner;
import net.eq2online.macros.gui.screens.GuiMacroEdit;
import net.eq2online.macros.input.InputHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

@Mixin(ChatScreen.class)
public abstract class MixinGuiChat implements IGuiChat, DesignableGuiControl.Listener {
    
    @Shadow
    private TextFieldWidget chatField;
    
    private Macros macros;
    private Settings settings;
    private DesignableGuiLayout layout;
    private Rectangle boundingBox = new Rectangle();
    private GuiDropDownMenu contextMenu;
    private Point contextMenuLocation = new Point();
    private DesignableGuiLayout.ClickedControlInfo clickedControl = null;
    
    protected GuiMiniToolbarButton btnGui;
    private int realWidth;
    private int realHeight;
    private int tickNumber;
    private TextFieldWidget textField;
    
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onConstructed(CallbackInfo ci) {
        this.macros = Macros.getInstance();
        this.settings = this.macros.getSettings();
        
        this.contextMenu = new GuiDropDownMenu(null, true); // We'll need to adapt this
        this.contextMenu.addItem("execute", I18n.get("gui.context.execute"));
        this.contextMenu.addItem("edit", I18n.get("gui.context.edit"));
        this.contextMenu.addItem("design", "§e" + I18n.get("tooltip.guiedit"), 26, 16);
        
        this.btnGui = new GuiMiniToolbarButton(null, 4, 104, 64); // We'll need to adapt this
        this.layout = this.macros.getLayoutManager().getBoundLayout("inchat", false);
    }
    
    @Inject(method = "init", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        this.textField = this.chatField;
    }
    
    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (this.layout != null && InputHandler.isAltDown()) {
            char keyChar = (char) keyCode;
            if (keyChar > '\000') {
                for (DesignableGuiControl control : this.layout.getControls()) {
                    control.handleKeyTyped(keyChar, keyCode, this);
                }
            }
        }
    }
    
    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (this.settings.enableButtonsOnChatGui) {
            // Adapt rendering logic for new Minecraft version
            // This will need significant updates for the new rendering system
        }
    }
    
    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void onMouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        // Handle mouse clicks for the macro system
        // This will need to be adapted for the new input system
    }
    
    // Implement IGuiChat interface methods
    @Override
    public TextFieldWidget getChatField() {
        return this.chatField;
    }
    
    @Override
    public void setChatField(TextFieldWidget chatField) {
        this.chatField = chatField;
    }
    
    // Implement DesignableGuiControl.Listener interface methods
    @Override
    public void onControlClicked(DesignableGuiControl control, int mouseX, int mouseY) {
        // Handle control clicks
    }
    
    @Override
    public void onControlRightClicked(DesignableGuiControl control, int mouseX, int mouseY) {
        // Handle right clicks on controls
    }
    
    private Rectangle updateBoundingBox() {
        // Calculate bounding box for the chat GUI
        return new Rectangle(0, 0, this.realWidth, this.realHeight);
    }
}
