package com.lunaire.mod;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.math.Vec3d;

public class LunaireClient implements ClientModInitializer {
    public static boolean noRenderParticles = true;
    public static Vec3d activeWaypoint = null; // Тот самый "символ", который ищет HudRenderer

    @Override
    public void onInitializeClient() {
        System.out.println("Lunaire Client Initialized!");
    }
}
