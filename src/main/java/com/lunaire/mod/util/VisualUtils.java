package com.lunaire.mod.util;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class VisualUtils {
    public static void register() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null) return;

            for (PlayerEntity player : client.world.getPlayers()) {
                if (player == client.player) continue;
                // Здесь мы позже добавим упрощенный рендер, 
                // когда убедимся, что база работает.
            }
        });
    }
}
