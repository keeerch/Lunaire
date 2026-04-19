package com.lunaire.mod.gui;

import com.lunaire.mod.LunaireClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen {
    public ClickGuiScreen() {
        super(Text.literal("Lunaire"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0x90000000);

        int w = 160;
        int h = 130;
        int x = (this.width - w) / 2;
        int y = (this.height - h) / 2;

        // Фон и четкая рамка
        context.fill(x - 1, y - 1, x + w + 1, y + h + 1, 0xFF000000);
        context.fill(x, y, x + w, y + h, 0xFF0F0F0F);

        // Шапка
        context.fill(x, y, x + w, y + 16, LunaireClient.accentColor);
        context.drawTextWithShadow(this.textRenderer, "LUNAIRE", x + 5, y + 4, 0xFF000000);

        // Модули
        drawModule(context, "Armor HUD", LunaireClient.enableArmorHud, x + 5, y + 25, mouseX, mouseY);
        drawModule(context, "Nametags", LunaireClient.enableNametags, x + 5, y + 40, mouseX, mouseY);
        drawModule(context, "No Hurt Cam", LunaireClient.enableNoHurtCam, x + 5, y + 55, mouseX, mouseY);

        // Компактный выбор цвета
        int colorY = y + h - 18;
        context.drawTextWithShadow(this.textRenderer, "COLOR:", x + 6, colorY + 2, 0xFF777777);
        
        drawSmallColor(context, x + 50, colorY, 0xFF00FFFF);  // Cyan
        drawSmallColor(context, x + 65, colorY, 0xFFFF00FF);  // Pink
        drawSmallColor(context, x + 80, colorY, 0xFF00FF00);  // Green
        drawSmallColor(context, x + 95, colorY, 0xFFFF0000);  // Red
        drawSmallColor(context, x + 110, colorY, 0xFFFFFFFF); // White
        drawSmallColor(context, x + 125, colorY, 0xFFFFFF00); // Yellow

        super.render(context, mouseX, mouseY, delta);
    }

    private void drawModule(DrawContext context, String name, boolean on, int x, int y, int mx, int my) {
        int color = on ? LunaireClient.accentColor : 0xFF555555;
        if (mx >= x && mx <= x + 150 && my >= y && my <= y + 10) {
            context.fill(x, y, x + 150, y + 10, 0x15FFFFFF);
        }
        context.drawTextWithShadow(this.textRenderer, (on ? "> " : "  ") + name, x, y, color);
    }

    private void drawSmallColor(DrawContext context, int x, int y, int color) {
        context.fill(x - 1, y - 1, x + 11, y + 11, 0xFF000000);
        context.fill(x, y, x + 10, y + 10, color);
        if (LunaireClient.accentColor == color) {
            context.fill(x + 3, y + 3, x + 7, y + 7, 0xFF000000);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int w = 160; int h = 130;
        int x = (this.width - w) / 2;
        int y = (this.height - h) / 2;

        if (mouseX >= x + 5 && mouseX <= x + 155) {
            if (mouseY >= y + 25 && mouseY <= y + 35) LunaireClient.enableArmorHud = !LunaireClient.enableArmorHud;
            if (mouseY >= y + 40 && mouseY <= y + 50) LunaireClient.enableNametags = !LunaireClient.enableNamet
