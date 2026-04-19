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
        context.fill(0, 0, this.width, this.height, 0xAA000000);

        int w = 200;
        int h = 140; // Немного увеличил высоту под новые цвета
        int x = (this.width - w) / 2;
        int y = (this.height - h) / 2;

        // Обводка и фон меню
        context.fill(x - 1, y - 1, x + w + 1, y + h + 1, LunaireClient.accentColor);
        context.fill(x, y, x + w, y + h, 0xFF0A0A0A);
        context.fill(x, y, x + w, y + 20, 0xFF151515);
        context.fill(x, y + 20, x + w, y + 21, LunaireClient.accentColor);

        context.drawTextWithShadow(this.textRenderer, "LUNAIRE v1.0", x + (w / 2) - (this.textRenderer.getWidth("LUNAIRE v1.0") / 2), y + 6, 0xFFFFFFFF);

        drawButton(context, "Armor HUD", LunaireClient.enableArmorHud, x + 10, y + 35, mouseX, mouseY);
        drawButton(context, "Nametags", LunaireClient.enableNametags, x + 10, y + 55, mouseX, mouseY);
        drawButton(context, "No Hurt Cam", LunaireClient.enableNoHurtCam, x + 10, y + 75, mouseX, mouseY);

        // Выбор стиля без "мыла"
        context.drawTextWithShadow(this.textRenderer, "STYLE:", x + 10, y + 105, 0xFFAAAAAA);
        
        // Рисуем квадратики цветов с четкой рамкой
        drawColorBox(context, x + 55, y + 103, 0xFF00FFFF);  // Cyan
        drawColorBox(context, x + 75, y + 103, 0xFFFF00FF);  // Pink
        drawColorBox(context, x + 95, y + 103, 0xFF00FF00);  // Green
        drawColorBox(context, x + 115, y + 103, 0xFFFF0000); // Red
        drawColorBox(context, x + 135, y + 103, 0xFFFFFFFF); // White
        drawColorBox(context, x + 155, y + 103, 0xFFFFFF00); // Yellow

        super.render(context, mouseX, mouseY, delta);
    }

    private void drawButton(DrawContext context, String name, boolean on, int x, int y, int mx, int my) {
        int color = on ? LunaireClient.accentColor : 0xFF444444;
        if (mx >= x && mx <= x + 180 && my >= y && my <= y + 12) {
            context.fill(x - 2, y - 2, x + 182, y + 12, 0x20FFFFFF);
        }
        context.drawTextWithShadow(this.textRenderer, name.toUpperCase(), x + 5, y, color);
        context.drawTextWithShadow(this.textRenderer, on ? "ON" : "OFF", x + 160, y, color);
    }

    private void drawColorBox(DrawContext context, int x, int y, int color) {
        // Тонкая черная обводка вокруг самого квадратика, чтобы не мылило
        context.fill(x - 1, y - 1, x + 14, y + 14, 0xFF000000); 
        context.fill(x, y, x + 13, y + 13, color);
        
        // Индикатор выбора (полоска снизу)
        if (LunaireClient.accentColor == color) {
            context.fill(x, y + 12, x + 13, y + 13, 0xFF000000); // Подложка
            context.fill(x + 2, y + 15, x + 11, y + 16, 0xFFFFFFFF); 
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = (this.width - 200) / 2;
        int y = (this.height - 140) / 2;

        if (mouseX >= x + 10 && mouseX <= x + 190) {
            if (mouseY >= y + 35 && mouseY <= y + 47) LunaireClient.enableArmorHud = !LunaireClient.enableArmorHud;
            if (mouseY >= y + 55 && mouseY <= y + 67) LunaireClient.enableNametags = !LunaireClient.enableNametags;
            if (mouseY >= y + 75 && mouseY <= y + 87) LunaireClient.enableNoHurtCam = !LunaireClient.enableNoHurtCam;
        }

        // Клики по палитре
        if (mouseY >= y + 103 && mouseY <= y + 118) {
            if (mouseX >= x + 55 && mouseX <= x + 70) LunaireClient.accentColor = 0xFF00FFFF;
            if (mouseX >= x + 75 && mouseX <= x + 90) LunaireClient.accentColor = 0xFFFF00FF;
            if (mouseX >= x + 95 && mouseX <= x + 110) LunaireClient.accentColor = 0xFF00FF00;
            if (mouseX >= x + 115 && mouseX <= x + 130) LunaireClient.accentColor = 0xFFFF0000;
            if (mouseX >= x + 135 && mouseX <= x + 150) LunaireClient.accentColor = 0xFFFFFFFF;
            if (mouseX >= x + 155 && mouseX <= x + 170) LunaireClient.accentColor = 0xFFFFFF00;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() { return false; }
}
