package net.eq2online.macros.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.eq2online.macros.core.MacroModCore;
import net.eq2online.macros.core.handler.ChatHandler;
import net.eq2online.macros.scripting.api.IVariableProvider;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.eq2online.macros.scripting.variable.providers.VariableProviderIMC;

import java.util.List;

public class MacrosFabricMod implements ClientModInitializer {
    private MacroModCore core;
    private ChatHandler chatHandler;
    private VariableProviderIMC imcVariables;
    private MinecraftClient mc;

    @Override
    public void onInitializeClient() {
        // Initialize the core mod
        this.mc = MinecraftClient.getInstance();
        this.core = new MacroModCore(mc);
        this.chatHandler = this.core.getChatHandler();
        this.imcVariables = new VariableProviderIMC();

        // Register variable providers
        for (ScriptContext context : ScriptContext.getAvailableContexts()) {
            context.getCore().getScriptActionProvider().registerVariableProvider(this.imcVariables);
        }

        // Initialize core
        this.core.onInitCompleted();

        // Register client tick events
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                this.core.onTick(client.getTickDelta(), true, true);
            }
        });

        // Register render events
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            this.core.onRender();
        });

        // Register post render events
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            this.core.onPostRender(client.getTickDelta());
        });

        // Register screen events for chat handling
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof ChatScreen) {
                ScreenKeyboardEvents.afterKeyPress(screen).register((screen1, key, scancode, modifiers) -> {
                    // Handle chat input
                    return false;
                });
            }
        });

        // Register client join events
        ClientLifecycleEvents.CLIENT_LEVEL_LOAD.register(world -> {
            this.core.onServerConnect(null); // We'll need to adapt this for Fabric
        });

        // Register networking for IMC variables
        ServerPlayNetworking.registerGlobalReceiver(
            new Identifier("macros", "variable"),
            (server, player, handler, buf, responseSender) -> {
                String name = buf.readString();
                String value = buf.readString();
                this.imcVariables.setVariable(name, value);
            }
        );

        System.out.println("Macro / Keybind Mod initialized for Fabric!");
    }

    public String getName() {
        return "Macro / Keybind Mod";
    }

    public String getVersion() {
        return MacroModCore.getVersion();
    }

    public void onChat(String message) {
        this.chatHandler.onChat(message);
    }

    public boolean onSendChatMessage(String message) {
        return this.chatHandler.onSendChatMessage(message);
    }

    public void onItemPickup(Object player, Object itemStack, int quantity) {
        // This will need to be adapted for the new Minecraft version
        // this.core.onItemPickup(player, itemStack, quantity);
    }
}
