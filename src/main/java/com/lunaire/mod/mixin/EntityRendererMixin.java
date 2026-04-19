package com.lunaire.mod.mixin;

import com.lunaire.mod.LunaireClient;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    // Используем ModifyVariable, чтобы просто подменить цвет текста перед отрисовкой
    @ModifyVariable(method = "renderLabelIfPresent", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private int changeColor(int color, Entity entity) {
        if (LunaireClient.enableNametags && entity instanceof PlayerEntity) {
            // Возвращаем наш цвет клиента (убираем прозрачность, оставляем только RGB)
            return LunaireClient.accentColor & 0x00FFFFFF | 0xFF000000;
        }
        return color;
    }
}
