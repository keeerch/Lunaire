package com.lunaire.mod;

import com.lunaire.mod.gui.ClickGuiScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import org.lwjgl.glfw.GLFW;

public class LunaireClient implements ClientModInitializer {
    public static boolean enableNametags = true;
    public static boolean enableArmorHud = true;
    public static boolean enableNoHurtCam = true;
    public static boolean fullBright = true;
    public static boolean noRenderParticles = false;
    public static int accentColor = 0xFF00FFFF;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS) {
                if (!(client.currentScreen instanceof ClickGuiScreen)) {
                    client.setScreen(new ClickGuiScreen());
                }
            }
        });

        // ВОЗВРАЩАЕМ АРМОР ХУД С ПРОЦЕНТАМИ
        HudRenderCallback.EVENT.register((drawContext, delta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || !enableArmorHud || client.options.hudHidden) return;

            // Позиция над сердечками
            int x = client.getWindow().getScaledWidth() / 2 - 91;
            int y = client.getWindow().getScaledHeight() - 55;
            
            int i = 0;
            for (ItemStack stack : client.player.getArmorItems()) {
                if (!stack.isEmpty()) {
                    int ox = x + (3 - i) * 20;
                    drawContext.drawItem(stack, ox, y);
                    drawContext.drawStackOverlay(client.textRenderer, stack, ox, y);
                    
                    // Расчет процентов прочности
                    if (stack.isDamageable()) {
                        int pct = (int) (((stack.getMaxDamage() - stack.getDamage()) / (float) stack.getMaxDamage()) * 100);
                        String pctStr = pct + "%";
                        int tw = client.textRenderer.getWidth(pctStr);
                        // Рисуем проценты мелким текстом под броней
                        drawContext.drawTextWithShadow(client.textRenderer, pctStr, ox + 10 - (tw/2), y + 15, 0xFFFFFFFF);
                    }
                }
                i++;
            }
        });
    }
}
