package com.lunaire.mod.mixin;

import com.lunaire.mod.LunaireClient;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightmapTextureManager.class)
public class LightmapMixin {
    
    // В 1.21.4 используем максимально безопасный метод, который возвращает яркость
    // Если LunaireClient.fullBright включен, мы просто форсим максимальное значение
    @Inject(method = "getBrightness", at = @At("HEAD"), cancellable = true)
    private static void onGetBrightness(CallbackInfoReturnable<Float> cir) {
        if (LunaireClient.fullBright) {
            cir.setReturnValue(15.0f);
        }
    }
}
