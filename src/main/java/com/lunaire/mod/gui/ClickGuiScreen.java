package com.lunaire.mod.gui;

import com.lunaire.mod.LunaireClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen {
    public ClickGuiScreen() { super(Text.literal("Lunaire")); }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0x60000000); 
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int w = 220; // Увеличил ширину
        int h = 160; // Увеличил высоту
        int x = (this.width - w) / 2;
        int y = (this.height - h) / 2;

        // РИСУЕМ ЗАКРУГЛЕННОЕ МЕНЮ (Многослойная заливка для эффекта углов)
        drawRoundedRect(context, x - 1, y - 1, x + w + 1, y + h + 1, LunaireClient.accentColor); // Внешний контур
        drawRoundedRect(context, x, y, x + w, y + h, 0xFF0A0A0A); // Тело меню

        // Шапка (тоже чуть скругленная сверху)
        context.fill(x + 2, y, x + w - 2, y + 20, LunaireClient.accentColor);
        context.drawTextWithShadow(this.textRenderer, "LUNAIRE CLIENT", x + (w/2) - (this.textRenderer.getWidth("LUNAIRE CLIENT")/2), y + 6, 0xFF000000);

        // Модули (Увеличил отступы и размер текста визуально)
        drawMod(context, "ARMOR HUD", LunaireClient.enableArmorHud, x + 15, y + 35, mouseX, mouseY);
        drawMod(context, "NAMETAGS", LunaireClient.enableNametags, x + 15, y + 55, mouseX, mouseY);
        drawMod(context, "NO HURT CAM", LunaireClient.enableNoHurtCam, x + 15, y + 75, mouseX, mouseY);
        drawMod(context, "FULL BRIGHT", LunaireClient.fullBright, x + 15, y + 95, mouseX, mouseY);

        // Выбор цвета (стиль)
        int cY = y + h - 25;
        context.drawTextWithShadow(this.textRenderer, "ACCENT COLOR:", x + 15, cY + 2, 0xFF999999);
        
        int[] clrs = {0xFF00FFFF, 0xFFFF00FF, 0xFF00FF00, 0xFFFF0000, 0xFFFFFFFF, 0xFFFFFF00};
        for (int i = 0; i < clrs.length; i++) {
            drawClr(context, x + 105 + (i * 18), cY, clrs[i]);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    // МЕТОД ДЛЯ ЗАКРУГЛЕННЫХ УГЛОВ (БЕЗ МЫЛА)
    private void drawRoundedRect(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        context.fill(x1 + 2, y1, x2 - 2, y2, color); // Основное тело
        context.fill(x1, y1 + 2, x1 + 2, y2 - 2, color); // Левый бок
        context.fill(x2 - 2, y1 + 2, x2, y2 - 2, color); // Правый бок
        // Пиксели на углах для мягкости
        context.fill(x1 + 1, y1 + 1, x1 + 2, y1 + 2, color);
        context.fill(x2 - 2, y1 + 1, x2 - 1, y1 + 2, color);
        context.fill(x1 + 1, y2 - 2, x1 + 2, y2 - 1, color);
        context.fill(x2 - 2, y2 - 2, x2 - 1, y2 - 1, color);
    }

    private void drawMod(DrawContext context, String n, boolean o, int x, int y, int mx, int my) {
        int c = o ? LunaireClient.accentColor : 0xFF666666;
        if (mx >= x && mx <= x + 190 && my >= y && my <= y + 12) {
            context.fill(x - 5, y - 2, x + 195, y + 12, 0x15FFFFFF);
        }
        context.drawTextWithShadow(this.textRenderer, n, x + 10, y, c);
        context.drawTextWithShadow(this.textRenderer, o ? "[ON]" : "[OFF]", x + 160, y, c);
    }

    private void drawClr(DrawContext context, int x, int y, int c) {
        context.fill(x - 1, y - 1, x + 12, y + 12, 0xFF000000);
        context.fill(x, y, x + 11, y + 11, c);
        if (LunaireClient.accentColor == c) {
            context.fill(x + 4, y + 4, x + 7, y + 7, 0xFF000000);
        }
    }

    @Override
    public boolean mouseClicked(double mx, double my, int b) {
        int w = 220, h = 160;
        int x = (this.width - w) / 2, y = (this.height - h) / 2;

        if (mx >= x + 15 && mx <= x + 205) {
            if (my >= y + 35 && my <= y + 47) LunaireClient.enableArmorHud = !LunaireClient.enableArmorHud;
            if (my >= y + 55 && my <= y + 67) LunaireClient.enableNametags = !LunaireClient.enableNametags;
            if (my >= y + 75 && my <= y + 87) LunaireClient.enableNoHurtCam = !LunaireClient.enableNoHurtCam;
            if (my >= y + 95 && my <= y + 107) LunaireClient.fullBright = !LunaireClient.fullBright;
        }

        int cY = y + h - 25;
        if (my >= cY && my <= cY + 11) {
            int[] clrs = {0xFF00FFFF, 0xFFFF00FF, 0xFF00FF00, 0xFFFF0000, 0xFFFFFFFF, 0xFFFFFF00};
            for (int i = 0; i < clrs.length; i++) {
                if (mx >= x + 105 + (i * 18) && mx <= x + 105 + (i * 18) + 11) LunaireClient.accentColor = clrs[i];
            }
        }
        return super.mouseClicked(mx, my, b);
    }

    @Override
    public boolean shouldPause() { return false; }
}
