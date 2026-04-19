package com.lunaire.mod.mixin;

import com.lunaire.mod.LunaireClient;
import net.minecraft.client.particle.ParticleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleMixin {
    @Inject(method = "addParticle", at = @At("HEAD"), cancellable = true)
    private void onAddParticle(CallbackInfo ci) {
        if (LunaireClient.noRenderParticles) {
            ci.cancel();
        }
    }
}
