package com.lunaire.mod.mixin;

import com.lunaire.mod.LunaireClient;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @Inject(method = "hasLabel", at = @At("HEAD"), cancellable = true)
    private void onHasLabel(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        // Если это игрок и Наметаги включены
        if (entity instanceof PlayerEntity && LunaireClient.enableNametags) {
            // Заставляем игру думать, что у него всегда есть ник
            cir.setReturnValue(true);
        }
    }
}
