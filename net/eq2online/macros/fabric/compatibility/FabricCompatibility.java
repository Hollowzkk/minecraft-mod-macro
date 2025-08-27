package net.eq2online.macros.fabric.compatibility;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

/**
 * Compatibility layer to adapt LiteLoader-style event handling to Fabric
 */
public class FabricCompatibility {
    
    /**
     * Register a tick listener (equivalent to LiteLoader's Tickable)
     */
    public static void registerTickListener(Consumer<MinecraftClient> tickCallback) {
        ClientTickEvents.END_CLIENT_TICK.register(tickCallback::accept);
    }
    
    /**
     * Register a render listener (equivalent to LiteLoader's RenderListener)
     */
    public static void registerRenderListener(Runnable renderCallback) {
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> renderCallback.run());
    }
    
    /**
     * Register a post render listener (equivalent to LiteLoader's PostRenderListener)
     */
    public static void registerPostRenderListener(Consumer<Float> postRenderCallback) {
        ClientTickEvents.END_CLIENT_TICK.register(client -> 
            postRenderCallback.accept(client.getTickDelta()));
    }
    
    /**
     * Register a join game listener (equivalent to LiteLoader's JoinGameListener)
     */
    public static void registerJoinGameListener(Runnable joinCallback) {
        ClientLifecycleEvents.CLIENT_LEVEL_LOAD.register(world -> joinCallback.run());
    }
    
    /**
     * Register a chat screen listener
     */
    public static void registerChatScreenListener(Consumer<ChatScreen> chatCallback) {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof ChatScreen) {
                chatCallback.accept((ChatScreen) screen);
            }
        });
    }
    
    /**
     * Register a keyboard listener for screens
     */
    public static void registerScreenKeyboardListener(Consumer<Screen> screenCallback) {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            screenCallback.accept(screen);
        });
    }
    
    /**
     * Register a message receiver (equivalent to LiteLoader's Messenger)
     */
    public static void registerMessageReceiver(String channel, MessageReceiver receiver) {
        ServerPlayNetworking.registerGlobalReceiver(
            new Identifier("macros", channel),
            (server, player, handler, buf, responseSender) -> {
                receiver.onMessage(buf);
            }
        );
    }
    
    /**
     * Send a message (equivalent to LiteLoader's MessageBus)
     */
    public static void sendMessage(String channel, String message) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(message);
        // This would need to be implemented with proper networking
    }
    
    /**
     * Interface for message receivers
     */
    public interface MessageReceiver {
        void onMessage(PacketByteBuf buf);
    }
    
    /**
     * Get Minecraft client instance
     */
    public static MinecraftClient getMinecraft() {
        return MinecraftClient.getInstance();
    }
    
    /**
     * Check if player is in game
     */
    public static boolean isInGame() {
        MinecraftClient client = getMinecraft();
        return client.player != null && client.world != null;
    }
    
    /**
     * Get current screen
     */
    public static Screen getCurrentScreen() {
        return getMinecraft().currentScreen;
    }
    
    /**
     * Set current screen
     */
    public static void setCurrentScreen(Screen screen) {
        getMinecraft().setScreen(screen);
    }
}
