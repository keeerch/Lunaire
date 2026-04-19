package com.lunaire.mod;

import com.lunaire.mod.gui.ClickGuiScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LunaireClient implements ClientModInitializer {
    public static boolean enableNametags = true;
    public static boolean enableArmorHud = true;
    public static boolean enableNoHurtCam = true;
    public static boolean fullBright = true;
    public static boolean fastRender = true; // Новая опция для ФПС
    public static int accentColor = 0xFF00FFFF;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                // Быстрое открытие меню
                if (GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS) {
                    if (!(client.currentScreen instanceof ClickGuiScreen)) {
                        client.setScreen(new ClickGuiScreen());
                    }
                }
                
                // Оптимизация: убираем частицы, если ФПС проседает
                if (fastRender && client.world != null) {
                    // Это заставит игру меньше нагружать видюху частицами
                    client.options.getParticles().setValue(net.minecraft.client.option.ParticlesMode.MINIMAL);
                }
            }
        });

        HudRenderCallback.EVENT.register((drawContext, delta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || !enableArmorHud || client.options.hudHidden) return;

            // Оптимизированный Armor HUD
            int x = client.getWindow().getScaledWidth() / 2 - 91;
            int y = client.getWindow().getScaledHeight() - 55;
            
            int offset = 0;
            for (ItemStack stack : client.player.getArmorItems()) {
                if (!stack.isEmpty()) {
                    drawContext.drawItem(stack, x + offset, y);
                    drawContext.drawStackOverlay(client.textRenderer, stack, x + offset, y);
                }
                offset += 20;
            }
        });
    }
}
