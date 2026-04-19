package com.lunaire.mod.gui;

import com.lunaire.mod.LunaireClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen {
    public ClickGuiScreen() {
        super(Text.literal("Lunaire ClickGUI"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Темный фон
        this.renderBackground(context, mouseX, mouseY, delta);
        
        context.drawCenteredTextWithShadow(this.textRenderer, "LUNAIRE CLIENT", this.width / 2, 20, 0xFF00FFFF);
        
        // Отрисовка кнопок-статусов
        drawModuleButton(context, "FullBright", LunaireClient.fullBright, 50);
        drawModuleButton(context, "Nametags", LunaireClient.enableNametags, 70);
        drawModuleButton(context, "NoHurtCam", LunaireClient.enableNoHurtCam, 90);
        
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawModuleButton(DrawContext context, String name, boolean enabled, int y) {
        int color = enabled ? 0xFF00FF00 : 0xFFFF0000;
        context.drawTextWithShadow(this.textRenderer, name + ": " + (enabled ? "ON" : "OFF"), this.width / 2 - 50, y, color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Логика переключения (упрощенно по Y координате)
        if (mouseY >= 50 && mouseY < 65) LunaireClient.fullBright = !LunaireClient.fullBright;
        if (mouseY >= 70 && mouseY < 85) LunaireClient.enableNametags = !LunaireClient.enableNametags;
        if (mouseY >= 90 && mouseY < 105) LunaireClient.enableNoHurtCam = !LunaireClient.enableNoHurtCam;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false; // Игра не ставится на паузу в меню
    }
}
