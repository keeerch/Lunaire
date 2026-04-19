package com.lunaire.mod.render;

import com.lunaire.mod.LunaireClient;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;

public class FancyEspRenderer {
    public static void register() {
        WorldRenderEvents.LAST.register(context -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || !LunaireClient.enableVisuals) return;
            // Здесь будет упрощенная логика позже, сейчас главное — собрать билд
        });
    }
}
