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
        // Тёмный фон
        context.fill(0, 0, this.width, this.height, 0x90000000);

        int w = 160; // Сделал чуть уже, чтобы выглядело аккуратнее
        int h = 130;
        int x = (this.width - w) / 2;
        int y = (this.height - h) / 2;

        // 1. ЧЁТКАЯ РАМКА (2 пикселя для жесткости)
        context.fill(x - 1, y - 1, x + w + 1, y + h + 1, 0xFF000000); // Черный контур
        context.fill(x, y, x + w, y + h, 0xFF0F0F0F); // Основной фон

        // 2. ШАПКА
        context.fill(x, y, x + w, y + 16, LunaireClient.accentColor);
        context.drawTextWithShadow(this.textRenderer, "LUNAIRE", x + 5, y + 4, 0xFF000000); // Черный текст на цветном фоне — сочно

        // 3. МОДУЛИ (Список)
        drawModule(context, "Armor HUD", LunaireClient.enableArmorHud, x + 5, y + 25, mouseX, mouseY);
        drawModule(context, "Nametags", LunaireClient.enableNametags, x + 5, y + 40, mouseX, mouseY);
        drawModule(context, "No Hurt Cam", LunaireClient.enableNoHurtCam, x + 5, y + 55, mouseX, mouseY);

        // 4. КОМПАКТНЫЙ ВЫБОР ЦВЕТА
        int colorY = y + h - 18;
        context.drawTextWithShadow(this.textRenderer, "COLOR:", x + 6, colorY + 2, 0xFF777777);
        
        // Компактные квадратики (10x10 пикселей)
        drawSmallColor(context, x + 50, colorY, 0xFF00FFFF);  // Cyan
        drawSmallColor(context, x + 65, colorY, 0xFFFF00FF);  // Pink
        drawSmallColor(context, x + 80, colorY, 0xFF00FF00);  // Green
        drawSmallColor(context, x + 95, colorY, 0xFFFF0000);  // Red
        drawSmallColor(context, x + 110, colorY, 0xFFFFFFFF); // White
        drawSmallColor
