package com.lunaire.mod;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.math.Vec3d;

public class LunaireClient implements ClientModInitializer {
    // Состояния функций (потом вынесем в GUI)
    public static boolean noRenderParticles = true;
    public static boolean fullBright = true;
    public static boolean fastSwap = true;
    public static float[] hitColor = {1.0f, 0.0f, 0.0f, 1.0f}; // RGBA (Красный по дефолту)
    
    public static Vec3d activeWaypoint = null;

    @Override
    public void onInitializeClient() {
        System.out.println("Lunaire Client: Modules Initialized!");
    }
}
