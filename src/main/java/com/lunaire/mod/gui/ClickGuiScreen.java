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
        // Прозрачный черный фон на весь экран (чуть темнее для контраста)
        context.fill(0, 0, this.width, this.height, 0xAA000000);

        int w = 200; // Немного шире для солидности
        int h = 130;
        int x = (this.width - w) / 2;
        int y = (this.height - h) / 2;

        // 1. Внешняя обводка (Outline) - делает меню чётким
        context.fill(x - 1, y - 1, x + w + 1, y + h + 1, LunaireClient.accentColor); 
        
        // 2. Основной фон (Глубокий черный)
        context.fill(x, y, x + w, y + h, 0xFF0A0A0A);

        // 3. Плашка заголовка
        context.fill(x, y, x + w, y + 20, 0xFF151515);
        context.fill(x, y + 20, x + w, y + 21, LunaireClient.accentColor); // Полоска под заголовком

        // Заголовок (Резкий белый текст)
        context.drawTextWithShadow(this.textRenderer, "LUNAIRE v1.0", x + (w / 2) - (this.textRenderer.getWidth("LUNAIRE v1.0") / 2), y + 6, 0xFFFFFFFF);

        // Кнопки модулей
        drawButton(context, "Armor HUD", LunaireClient.enableArmorHud, x + 10, y + 35, mouseX, mouseY);
        drawButton(context, "Nametags", LunaireClient.enableNametags, x + 10, y + 55, mouseX, mouseY);
        drawButton(context, "No Hurt Cam", LunaireClient.enableNoHurtCam, x + 10, y + 75, mouseX, mouseY);

        // Смена цвета (внизу)
        context.drawTextWithShadow(this.textRenderer, "Style:", x + 10, y + 105, 0xFFAAAAAA);
        drawColorCircle(context, x + 60, y + 103, 0xFF00FFFF);  // Cyan
        drawColorCircle(context, x + 85, y + 103, 0xFFFF00FF);  // Pink
        drawColorCircle(context, x + 110, y + 103, 0xFF00FF00); // Green
        drawColorCircle(context, x + 135, y + 103, 0xFFFFFF00); // Yellow

        super.render(context, mouseX, mouseY, delta);
    }

    private void drawButton(DrawContext context, String name, boolean on, int x, int y, int mx, int my) {
        int color = on ? LunaireClient.accentColor : 0xFF444444;
        // Подсветка при наведении
        if (mx >= x && mx <= x + 180 && my >= y && my <= y + 12) {
            context.fill(x - 2, y - 2, x + 182, y + 12, 0x20FFFFFF);
        }
        context.drawTextWithShadow(this.textRenderer, name.toUpperCase(), x + 5, y, color);
        context.drawTextWithShadow(this.textRenderer, on ? "ON" : "OFF", x + 160, y, color);
    }

    private void drawColorCircle(DrawContext context, int x, int y, int color) {
        context.fill(x, y, x + 15, y + 15, color);
        // Белая обводка вокруг выбранного цвета
        if (LunaireClient.accentColor == color) {
            context.fill(x, y + 14, x + 15, y + 15, 0xFFFFFFFF);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = (this.width - 200) / 2;
        int y = (this.height - 130) / 2;

        // Клики по кнопкам
        if (mouseX >= x + 10 && mouseX <= x + 190) {
            if (mouseY >= y + 35 && mouseY <= y + 47) LunaireClient.enableArmorHud = !LunaireClient.enableArmorHud;
            if (mouseY >= y + 55 && mouseY <= y + 67) LunaireClient.enableNametags = !LunaireClient.enableNametags;
            if (mouseY >= y + 75 && mouseY <= y + 87) LunaireClient.enableNoHurtCam = !LunaireClient.enableNoHurtCam;
        }

        // Клики по цветам
        if (mouseY >= y + 103 && mouseY <= y + 118) {
            if (mouseX >= x + 60 && mouseX <= x + 75) LunaireClient.accentColor = 0xFF00FFFF;
            if (mouseX >= x + 85 && mouseX <= x + 100) LunaireClient.accentColor = 0xFFFF00FF;
            if (mouseX >= x + 110 && mouseX <= x + 125) LunaireClient.accentColor = 0xFF00FF00;
            if (mouseX >= x + 135 && mouseX <= x + 150) LunaireClient.accentColor = 0xFFFFFF00;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() { return false; }
}
