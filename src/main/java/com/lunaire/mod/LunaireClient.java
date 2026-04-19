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

        HudRenderCallback.EVENT.register((drawContext, delta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || !enableArmorHud || client.options.hudHidden) return;

            int x = client.getWindow().getScaledWidth() / 2 - 91;
            // БЫЛО -55, СТАЛО -80 (ТЕПЕРЬ ВЫШЕ НАД ИНВЕНТАРЕМ/БРОНЕЙ)
            int y = client.getWindow().getScaledHeight() - 80; 
            
            int i = 0;
            for (ItemStack stack : client.player.getArmorItems()) {
                if (!stack.isEmpty()) {
                    int ox = x + (3 - i) * 20;
                    drawContext.drawItem(stack, ox, y);
                    drawContext.drawStackOverlay(client.textRenderer, stack, ox, y);
                    
                    if (stack.isDamageable()) {
                        int pct = (int) (((stack.getMaxDamage() - stack.getDamage()) / (float) stack.getMaxDamage()) * 100);
                        drawContext.drawTextWithShadow(client.textRenderer, pct + "%", ox + 2, y + 18, 0xFFFFFFFF);
                    }
                }
                i++;
            }
        });
    }
}
