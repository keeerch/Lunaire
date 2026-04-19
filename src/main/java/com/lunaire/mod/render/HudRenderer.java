package com.lunaire.mod.render;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

public class HudRenderer {
    public static void register() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null) return;

            // --- REACH INDICATOR ---
            if (client.targetedEntity != null) {
                double distance = client.player.getPos().distanceTo(client.targetedEntity.getPos());
                int color = (distance <= 3.0) ? 0xFF00FF00 : 0xFFFFFFFF; // Зеленый если достаешь
                
                String distText = String.format("%.2fm", distance);
                drawContext.drawText(client.textRenderer, distText, 
                    client.getWindow().getScaledWidth() / 2 + 10, 
                    client.getWindow().getScaledHeight() / 2 + 10, 
                    color, false);
            }

            // --- DIRECTION HUD (Стрелочка к метке) ---
            Vec3d waypoint = LunaireClient.activeWaypoint; // Твоя метка
            if (waypoint != null) {
                double distToWaypoint = client.player.getPos().distanceTo(waypoint);
                
                // Простая логика: пишем расстояние и рисуем символ
                String navText = "⬆ " + (int)distToWaypoint + "m";
                drawContext.drawText(client.textRenderer, navText, 
                    client.getWindow().getScaledWidth() / 2 - 20, 
                    client.getWindow().getScaledHeight() / 2 - 30, 
                    0x00AAAAFF, true);
            }
        });
    }
}
