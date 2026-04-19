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
        // РАЗМЕРЫ МЕНЮ КАК НА ФОТО (Широкое)
        int w = 360; 
        int h = 160; 
        int x = (this.width - w) / 2;
        int y = (this.height - h) / 2;

        // Прямоугольник с закругленными краями (стабильный способ)
        context.fill(x + 2, y, x + w - 2, y + h, 0xFF0A0A0A);
        context.fill(x, y + 2, x + 2, y + h - 2, 0xFF0A0A0A);
        context.fill(x + w - 2, y + 2, x + w, y + h - 2, 0xFF0A0A0A);

        // Шапка
        context.fill(x + 2, y, x + w - 2, y + 20, LunaireClient.accentColor);
        context.drawTextWithShadow(this.textRenderer, "LUNAIRE CLIENT - GUI", x + (w / 2) - (this.textRenderer.getWidth("LUNAIRE CLIENT - GUI") / 2), y + 6, 0xFF000000);

        // --- ЛЕВАЯ КОЛОНКА (Категория Visuals) ---
        context.drawTextWithShadow(this.textRenderer, "VISUALS:", x + 15, y + 35, 0xFF999999);
        
        // Кнопки переключатели
        drawButton(context, "Armor HUD", LunaireClient.enableArmorHud, x + 15, y + 50, 90, 16, mouseX, mouseY);
        drawButton(context, "Nametags", LunaireClient.enableNametags, x + 15, y + 70, 90, 16, mouseX, mouseY);
        drawButton(context, "No Hurt Cam", LunaireClient.enableNoHurtCam, x + 15, y + 90, 90, 16, mouseX, mouseY);

        // --- ПРАВАЯ КОЛОНКА (Категория Optimizations) ---
        context.drawTextWithShadow(this.textRenderer, "OPTIMIZATIONS:", x + (w / 2) + 15, y + 35, 0xFF999999);
        
        drawButton(context, "Full Bright", LunaireClient.fullBright, x + (w / 2) + 15, y + 50, 90, 16, mouseX, mouseY);
        drawButton(context, "Min Particles", LunaireClient.noRenderParticles, x + (w / 2) + 15, y + 70, 90, 16, mouseX, mouseY);

        // --- ЦВЕТА (Внизу) ---
        int cY = y + h - 25;
        context.drawTextWithShadow(this.textRenderer, "STYLE:", x + 15, cY + 2, 0xFFFFFFFF);
        
        int[] clrs = {0xFF00FFFF, 0xFFFF00FF, 0xFF00FF00, 0xFFFF0000, 0xFFFFFFFF, 0xFFFFFF00};
        for (int i = 0; i < clrs.length; i++) {
            drawColorPicker(context, x + 55 + (i * 18), cY, clrs[i], 12);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    // МЕТОД ДЛЯ РИСОВАНИЯ ПЕРЕКЛЮЧАТЕЛЯ-КНОПКИ
    private void drawButton(DrawContext context, String name, boolean enabled, int x, int y, int bw, int bh, int mx, int my) {
        int rectColor = enabled ? LunaireClient.accentColor : 0xFF151515; // Фон кнопки
        int textColor = enabled ? 0xFF000000 : 0xFFFFFFFF; // Цвет текста внутри
        
        // Чёткий контур кнопки
        context.fill(x - 1, y - 1, x + bw + 1, y + bh + 1, 0xFF222222);
        // Сама кнопка
        context.fill(x, y, x + bw, y + bh, rectColor);
        
        // Эффект наведения
        if (mx >= x && mx <= x + bw && my >= y && my <= y + bh) {
            context.fill(x, y, x + bw, y + bh, 0x15FFFFFF);
        }
        
        // Текст внутри кнопки (выровнен по центру)
        context.drawCenteredTextWithShadow(this.textRenderer, name, x + (bw / 2), y + (bh / 2) - 4, textColor);
    }

    // Упрощенный метод выбора цвета
    private void drawColorPicker(DrawContext context, int x, int y, int c, int size) {
        context.fill(x - 1, y - 1, x + size + 1, y + size + 1, 0xFF222222); // Контур
        context.fill(x, y, x + size, y + size, c);
        if (LunaireClient.accentColor == c) {
            // Метка выбора (точка в центре)
            context.fill(x + (size / 2) - 1, y + (size / 2) - 1, x + (size / 2) + 1, y + (size / 2) + 1, 0xFF000000);
        }
    }

    @Override
    public boolean mouseClicked(double mx, double my, int b) {
        int w = 360; 
        int h = 160; 
        int x = (this.width - w) / 2;
        int y = (this.height - h) / 2;

        // КЛИКИ ЛЕВАЯ КОЛОНКА (Visuals)
        if (mx >= x + 15 && mx <= x + 105) {
            if (my >= y + 50 && my <= y + 66) LunaireClient.enableArmorHud = !LunaireClient.enableArmorHud;
            if (my >= y + 70 && my <= y + 86) LunaireClient.enableNametags = !LunaireClient.enableNametags;
            if (my >= y + 90 && my <= y + 106) LunaireClient.enableNoHurtCam = !LunaireClient.enableNoHurtCam;
        }

        // КЛИКИ ПРАВАЯ КОЛОНКА (Optimizations)
        if (mx >= x + (w / 2) + 15 && mx <= x + (w / 2) + 105) {
            if (my >= y + 50 && my <= y + 66) LunaireClient.fullBright = !LunaireClient.fullBright;
            if (my >= y + 70 && my <= y + 86) LunaireClient.noRenderParticles = !LunaireClient.noRenderParticles;
        }

        // КЛИКИ ЦВЕТА
        int cY = y + h - 25;
        if (my >= cY && my <= cY + 12) {
            int[] clrs = {0xFF00FFFF, 0xFFFF00FF, 0xFF00FF00, 0xFFFF0000, 0xFFFFFFFF, 0xFFFFFF00};
            for (int i = 0; i < clrs.length; i++) {
                if (mx >= x + 55 + (i * 18) && mx <= x + 55 + (i * 18) + 12) {
                    LunaireClient.accentColor = clrs[i];
                }
            }
        }
        return super.mouseClicked(mx, my, b);
    }

    @Override
    public boolean shouldPause() { return false; }
}
