package com.lunaire.mod.gui;

import com.lunaire.mod.LunaireClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen {
    public ClickGuiScreen() { super(Text.literal("Lunaire")); }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0x90000000); // Фон
        
        int x = this.width / 2 - 50;
        drawBtn(context, "FullBright", LunaireClient.fullBright, x, 50);
        drawBtn(context, "Armor HUD", LunaireClient.enableArmorHud, x, 70);
        
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawBtn(DrawContext context, String name, boolean on, int x, int y) {
        int color = on ? 0xFF00FF00 : 0xFFFF0000;
        context.drawTextWithShadow(this.textRenderer, name + (on ? " [ON]" : " [OFF]"), x, y, color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = this.width / 2 - 50;
        if (mouseX >= x && mouseX <= x + 100) {
            if (mouseY >= 50 && mouseY <= 60) LunaireClient.fullBright = !LunaireClient.fullBright;
            if (mouseY >= 70 && mouseY <= 80) LunaireClient.enableArmorHud = !LunaireClient.enableArmorHud;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public boolean shouldPause() { return false; }
}
