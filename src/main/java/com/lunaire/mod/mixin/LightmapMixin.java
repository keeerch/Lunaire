package com.lunaire.mod.mixin;

import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LightmapTextureManager.class)
public class LightmapMixin {
    // Удалили инъекцию, которая вешала загрузку на 70%
}
