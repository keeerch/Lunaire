package com.lunaire.mod;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.math.Vec3d;

public class LunaireClient implements ClientModInitializer {
    // Добавляем эти переменные, чтобы ошибки исчезли:
    public static boolean noRenderParticles = true; // Включено по умолчанию
    public static Vec3d activeWaypoint = null;      // Метка (пока пустая)

    @Override
    public void onInitializeClient() {
        // Тут инициализация твоего мода
        System.out.println("Lunaire Client Loaded!");
    }
}
