package com.lunaire.mod;

import com.lunaire.mod.gui.ClickGuiScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import org.lwjgl.glfw.GLFW;

public class LunaireClient implements ClientModInitializer {
    public static boolean enableArmorHud = true;
    public static boolean fullBright = true;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                long window = client.getWindow().getHandle();
                if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS) {
                    if (!(client.currentScreen instanceof ClickGuiScreen)) {
                        client.setScreen(new ClickGuiScreen());
                    }
                }
            }
        });

        // Используем float delta вместо RenderTickCounter для совместимости
        HudRenderCallback.EVENT.register((drawContext, delta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || !enableArmorHud) return;

            int y = 10;
            for (ItemStack stack : client.player.getArmorItems()) {
                if (!stack.isEmpty()) {
                    drawContext.drawItem(stack, 10, y);
                    drawContext.drawStackOverlay(client.textRenderer, stack, 10, y);
                    y += 20;
                }
            }
        });
    }
}
