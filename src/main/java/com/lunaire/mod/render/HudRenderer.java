package com.lunaire.mod.render;

import com.lunaire.mod.LunaireClient; // ОБЯЗАТЕЛЬНЫЙ ИМПОРТ
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.Vec3d;

public class HudRenderer {
    public static void register() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null) return;

            // --- DIRECTION HUD (Стрелочка к метке) ---
            Vec3d waypoint = LunaireClient.activeWaypoint; 
            if (waypoint != null) {
                double distToWaypoint = client.player.getPos().distanceTo(waypoint);
                
                String navText = "⬆ " + (int)distToWaypoint + "m";
                
                // В 1.21.4 используется DrawContext для текста
                drawContext.drawText(client.textRenderer, navText, 
                    client.getWindow().getScaledWidth() / 2 - 20, 
                    client.getWindow().getScaledHeight() / 2 - 30, 
                    0x00AAAAFF, true);
            }
        });
    }
}
