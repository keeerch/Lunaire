package com.lunaire.mod;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LunaireClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("lunaire");
    
    public static boolean enableNametags = true;
    public static boolean enableNoHurtCam = true;
    public static boolean fullBright = true;
    public static boolean noRenderParticles = false;

    @Override
    public void onInitializeClient() {
        // Если ты увидишь это в консоли — значит вход выполнен!
        LOGGER.info("!!! LUNAIRE LOADED SUCCESSFULLY !!!");
    }
}
