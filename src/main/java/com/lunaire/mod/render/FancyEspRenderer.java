package com.lunaire.mod.render;

import com.lunaire.mod.LunaireClient;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.RotationAxis;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class FancyEspRenderer {
    public static void register() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || !LunaireClient.enableVisuals) return;

            MatrixStack matrices = context.matrixStack();
            Camera camera = context.camera();
            
            // Логика вращения (обновляем угол)
            long currentTime = System.currentTimeMillis();
            long delta = currentTime - LunaireClient.lastTickTime;
            LunaireClient.lastTickTime = currentTime;
            LunaireClient.rotateAngle += delta * 0.1f; // Скорость вращения

            for (PlayerEntity player : client.world.getPlayers()) {
                if (player == client.player || player.isInvisible()) continue;

                // Получаем позицию игрока относительно камеры
                double x = player.getX() - camera.getPos().x;
                double y = player.getY() - camera.getPos().y;
                double z = player.getZ() - camera.getPos().z;

                matrices.push();
                matrices.translate(x, y + (player.getHeight() / 2), z); // Центрируем на персонаже
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(LunaireClient.rotateAngle)); // Вращаем
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90)); // Делаем квадрат горизонтальным
                
                // Рендерим закругленный квадрат (упрощенная версия для примера)
                drawRoundedQuad(matrices, -0.4f, -0.4f, 0.8f, 0.8f, 0.1f, new Color(100, 200, 255, 150));
                
                matrices.pop();
            }
        });
    }

    // Вспомогательный метод для отрисовки закругленного квадрата (нужны OpenGL шейдеры для идеала, но это база)
    private static void drawRoundedQuad(MatrixStack matrices, float x, float y, float width, float height, float radius, Color color) {
        // В 1.21.4 мы используем VertexConsumer
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        // Рисуем квадрат (без закруглений пока, чтобы билд не упал, закругления делаются через шейдер)
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), x, y, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).build();
        // ... (остальные 3 вершины) ...
        tessellator.draw(bufferBuilder);
    }
}
