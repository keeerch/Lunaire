package com.lunaire.mod;

import com.lunaire.mod.gui.ClickGuiScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class LunaireClient implements ClientModInitializer {
    public static boolean enableArmorHud = true;
    public static boolean fullBright = true;

    @Override
    public void onInitializeClient() {
        // Открытие GUI на Правый Шифт
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS) {
                if (!(client.currentScreen instanceof ClickGuiScreen)) {
                    client.setScreen(new ClickGuiScreen());
                }
            }
        });

        // ARMOR HUD (Отрисовка брони)
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || !enableArmorHud) return;

            int y = 5;
            // Проходим по броне игрока
            for (ItemStack stack : client.player.getArmorItems()) {
                if (!stack.isEmpty()) {
                    drawContext.drawItem(stack, 5, y);
                    drawContext.drawItemInSlot(client.textRenderer, stack, 5, y);
                    y += 18;
                }
            }
        });
    }
}
