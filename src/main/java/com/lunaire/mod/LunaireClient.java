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
    public static boolean noRenderParticles = false;
    public static boolean enableArmorHud = true;
    public static boolean fullBright = true;
    public static boolean enableNoHurtCam = true;

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

        HudRenderCallback.EVENT.register((drawContext, delta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || !enableArmorHud || client.options.hudHidden) return;

            // Собираем броню в список, чтобы развернуть (по умолчанию идет от сапог к шлему)
            List<ItemStack> armor = new ArrayList<>();
            for (ItemStack stack : client.player.getArmorItems()) {
                armor.add(stack);
            }
            Collections.reverse(armor); // Теперь: шлем, нагрудник, поножи, ботинки

            int screenWidth = client.getWindow().getScaledWidth();
            int screenHeight = client.getWindow().getScaledHeight();
            
            // Координаты: над сердцами (обычно сердца на y = screenHeight - 39)
            // Ставим чуть выше — на y = screenHeight - 55
            int x = screenWidth / 2 - 91; 
            int y = screenHeight - 55;

            for (ItemStack stack : armor) {
                if (!stack.isEmpty()) {
                    // Рисуем саму иконку брони
                    drawContext.drawItem(stack, x, y);
                    drawContext.drawStackOverlay(client.textRenderer, stack, x, y);

                    // Считаем проценты прочности
                    if (stack.isDamageable()) {
                        double damage = ((double) (stack.getMaxDamage() - stack.getDamage()) / stack.getMaxDamage()) * 100;
                        String percent = (int) damage + "%";
                        
                        // Рисуем текст процентов под иконкой
                        drawContext.drawTextWithShadow(client.textRenderer, percent, x, y + 15, 0xFFFFFFFF);
                    }
                }
                x += 20; // Смещение вправо для следующего предмета
            }
        });
    }
}
