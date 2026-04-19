package com.lunaire.mod;

import com.lunaire.mod.gui.ClickGuiScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class LunaireClient implements ClientModInitializer {
    public static boolean enableNametags = true;
    public static boolean enableNoHurtCam = true;
    public static boolean fullBright = true;
    public static boolean noRenderParticles = true;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Проверяем нажатие Правого Шифта (GLFW_KEY_RIGHT_SHIFT)
            if (client.player != null && InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_SHIFT)) {
                if (!(client.currentScreen instanceof ClickGuiScreen)) {
                    client.setScreen(new ClickGuiScreen());
                }
            }
        });
    }
}
