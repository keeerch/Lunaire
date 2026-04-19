package com.lunaire.mod;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.math.Vec3d;

public class LunaireClient implements ClientModInitializer {
    // Включаем визуалы по умолчанию
    public static boolean enableVisuals = true;
    public static boolean enableNametags = true;
    public static boolean enableNoHurtCam = true;
    
    // Переменные для вращения квадрата
    public static float rotateAngle = 0.0f;
    public static long lastTickTime = 0;

    @Override
    public void onInitializeClient() {
        System.out.println("Lunaire Visuals Activated!");
        lastTickTime = System.currentTimeMillis();
    }
}
