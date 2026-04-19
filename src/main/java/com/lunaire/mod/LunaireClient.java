package com.lunaire.mod;

import net.fabricmc.api.ClientModInitializer;

public class LunaireClient implements ClientModInitializer {
    // ТЕ САМЫЕ ПЕРЕМЕННЫЕ, КОТОРЫЕ ИЩЕТ КОМПИЛЯТОР:
    public static boolean noRenderParticles = true;
    public static boolean enableNametags = true;
    public static boolean enableNoHurtCam = true;
    public static boolean fullBright = true;

    @Override
    public void onInitializeClient() {
        System.out.println("Lunaire Client Loaded!");
    }
}
