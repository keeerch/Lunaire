package com.lunaire.mod.mixin;

import com.lunaire.mod.LunaireClient;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @ModifyArgs(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I"))
    private void onDrawLabel(Args args, Entity entity) {
        if (LunaireClient.enableNametags && entity instanceof PlayerEntity) {
            // Заменяем цвет текста (аргумент №3 в методе draw) на наш акцентный цвет
            args.set(3, LunaireClient.accentColor);
        }
    }
}
