package com.lunaire.mod.mixin;

import com.lunaire.mod.LunaireClient;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {
    @Inject(method = "hasLabel", at = @At("HEAD"), cancellable = true)
    private void onHasLabel(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (LunaireClient.enableNametags) {
            cir.setReturnValue(true);
        }
    }
}
