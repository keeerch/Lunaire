package com.lunaire.mod.gui;

import com.lunaire.mod.LunaireClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.client.render.RenderTickCounter;

public class ClickGuiScreen extends Screen {
    public ClickGuiScreen() {
        super(Text.literal("Lunaire"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Затемнение
        context.fill(0, 0, this.width, this.height, 0x90000000);

        int w = 180;
        int h = 120;
        int x = (this.width - w) / 2;
        int y = (this.height - h) / 2;

        // Тело меню
        context.fill(x, y, x + w, y + h, 0xFF121212);
        // Полоска сверху
        context.fill(x, y, x + w, y + 2, LunaireClient.accentColor);

        // Текст
        context.drawTextWithShadow(this.textRenderer, "LUNAIRE CLIENT", x + 45, y + 10, LunaireClient.accentColor);

        // Модули
        drawMod(context, "Armor HUD", LunaireClient.enableArmorHud, x + 10, y + 35);
        drawMod(context, "Nametags", LunaireClient.enableNametags, x + 10, y + 55);
        drawMod(context, "No Hurt Cam", LunaireClient.enableNoHurtCam, x + 10, y + 75);

        // Цвет
        context.drawTextWithShadow(this.textRenderer, "COLOR:", x + 10, y + 100, 0xFFFFFFFF);
        context.fill(x + 60, y + 98, x + 75, y + 110, 0xFF00FFFF);
        context.fill(x + 80, y + 98, x + 95, y + 110, 0xFFFF00FF);
        context.fill(x + 100, y + 98, x + 115, y + 110, 0xFF00FF00);

        super.render(context, mouseX, mouseY, delta);
    }

    private void drawMod(DrawContext context, String name, boolean on, int x, int y) {
        int color = on ? LunaireClient.accentColor : 0xFF777777;
        context.drawTextWithShadow(this.textRenderer, (on ? "[X] " : "[ ] ") + name, x, y, color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = (this.width - 180) / 2;
        int y = (this.height - 120) / 2;

        if (mouseX >= x + 10 && mouseX <= x + 150) {
            if (mouseY >= y + 35 && mouseY <= y + 45) LunaireClient.enableArmorHud = !LunaireClient.enableArmorHud;
            if (mouseY >= y + 55 && mouseY <= y + 65) LunaireClient.enableNametags = !LunaireClient.enableNametags;
            if (mouseY >= y + 75 && mouseY <= y + 85) LunaireClient.enableNoHurtCam = !LunaireClient.enableNoHurtCam;
        }

        if (mouseY >= y + 98 && mouseY <= y + 110) {
            if (mouseX >= x + 60 && mouseX <= x + 75) LunaireClient.accentColor = 0xFF00FFFF;
            if (mouseX >= x + 80 && mouseX <= x + 95) LunaireClient.accentColor = 0xFFFF00FF;
            if (mouseX >= x + 100 && mouseX <= x + 115) LunaireClient.accentColor = 0xFF00FF00;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() { return false; }
}
