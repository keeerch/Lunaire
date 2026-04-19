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
    public static boolean enableNoHurtCam = true;
    
    // Акцентный цвет мода (по умолчанию бирюзовый)
    public static int accentColor = 0xFF00FFFF; 

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                if (GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS) {
                    if (!(client.currentScreen instanceof ClickGuiScreen)) {
                        client.setScreen(new ClickGuiScreen());
                    }
                }
            }
        });

        HudRenderCallback.EVENT.register((drawContext, delta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || !enableArmorHud || client.options.hudHidden) return;

            List<ItemStack> armor = new ArrayList<>();
            client.player.getArmorItems().forEach(armor::add);
            Collections.reverse(armor);

            int x = client.getWindow().getScaledWidth() / 2 - 91;
            int y = client.getWindow().getScaledHeight() - 55;

            for (ItemStack stack : armor) {
                if (!stack.isEmpty()) {
                    drawContext.drawItem(stack, x, y);
                    drawContext.drawStackOverlay(client.textRenderer, stack, x, y);
                    if (stack.isDamageable()) {
                        int pct = (int) (((double) (stack.getMaxDamage() - stack.getDamage()) / stack.getMaxDamage()) * 100);
                        drawContext.drawTextWithShadow(client.textRenderer, pct + "%", x, y + 15, 0xFFFFFFFF);
                    }
                }
                x += 20;
            }
        });
    }
}
