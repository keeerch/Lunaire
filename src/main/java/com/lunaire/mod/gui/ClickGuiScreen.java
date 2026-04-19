package com.lunaire.mod.gui;

import com.lunaire.mod.LunaireClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.RenderTickCounter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen {
    public ClickGuiScreen() {
        super(Text.literal("Lunaire Menu"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, RenderTickCounter tickCounter) {
        // Полупрозрачный фон
        context.fill(0, 0, this.width, this.height, 0x80000000);
        
        int x = this.width / 2 - 50;
        drawButton(context, "FullBright", LunaireClient.fullBright, x, 50);
        drawButton(context, "Armor HUD", LunaireClient.enableArmorHud, x, 80);
        
        super.render(context, mouseX, mouseY, tickCounter);
    }

    private void drawButton(DrawContext context, String name, boolean state, int x, int y) {
        int color = state ? 0xFF00FF00 : 0xFFFF0000;
        context.drawTextWithShadow(this.textRenderer, name + ": " + (state ? "ON" : "OFF"), x, y, color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = this.width / 2 - 50;
        if (mouseX >= x && mouseX <= x + 100) {
            if (mouseY >= 50 && mouseY <= 65) LunaireClient.fullBright = !LunaireClient.fullBright;
            if (mouseY >= 80 && mouseY <= 95) LunaireClient.enableArmorHud = !LunaireClient.enableArmorHud;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() { return false; }
}
