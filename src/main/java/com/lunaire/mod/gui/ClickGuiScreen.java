package com.lunaire.mod.gui;

import com.lunaire.mod.LunaireClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen {
    public ClickGuiScreen() { super(Text.literal("Lunaire")); }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0x90000000);
        int w = 160, h = 130;
        int x = (this.width - w) / 2, y = (this.height - h) / 2;
        
        // Чёткий контур меню
        context.fill(x - 1, y - 1, x + w + 1, y + h + 1, 0xFF000000);
        context.fill(x, y, x + w, y + h, 0xFF0F0F0F);
        
        // Шапка
        context.fill(x, y, x + w, y + 16, LunaireClient.accentColor);
        context.drawTextWithShadow(this.textRenderer, "LUNAIRE", x + 5, y + 4, 0xFF000000);

        drawMod(context, "Armor HUD", LunaireClient.enableArmorHud, x + 5, y + 25, mouseX, mouseY);
        drawMod(context, "Nametags", LunaireClient.enableNametags, x + 5, y + 40, mouseX, mouseY);
        drawMod(context, "No Hurt Cam", LunaireClient.enableNoHurtCam, x + 5, y + 55, mouseX, mouseY);

        int cY = y + h - 18;
        context.drawTextWithShadow(this.textRenderer, "STYLE:", x + 6, cY + 2, 0xFF777777);
        
        // Отрисовка цветов без мыла (расстояние увеличено для четкости)
        int[] colors = {0xFF00FFFF, 0xFFFF00FF, 0xFF00FF00, 0xFFFF0000, 0xFFFFFFFF, 0xFFFFFF00};
        for (int i = 0; i < colors.size(); i++) {
            drawClr(context, x + 45 + (i * 18), cY, colors[i]);
        }
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawMod(DrawContext context, String n, boolean o, int x, int y, int mx, int my) {
        int c = o ? LunaireClient.accentColor : 0xFF555555;
        if (mx >= x && mx <= x + 150 && my >= y && my <= y + 10) context.fill(x, y, x + 150, y + 10, 0x15FFFFFF);
        context.drawTextWithShadow(this.textRenderer, (o ? "> " : "  ") + n, x, y, c);
    }

    private void drawClr(DrawContext context, int x, int y, int c) {
        // Жесткая черная рамка вокруг каждого квадрата (фикс мыла)
        context.fill(x - 1, y - 1, x + 11, y + 11, 0xFF000000);
        context.fill(x, y, x + 10, y + 10, c);
        // Точка выбора внутри
        if (LunaireClient.accentColor == c) {
            context.fill(x + 3, y + 3, x + 7, y + 7, 0xFF000000);
        }
    }

    @Override
    public boolean mouseClicked(double mx, double my, int b) {
        int x = (this.width - 160) / 2, y = (this.height - 130) / 2;
        if (mx >= x + 5 && mx <= x + 155) {
            if (my >= y + 25 && my <= y + 35) LunaireClient.enableArmorHud = !LunaireClient.enableArmorHud;
            if (my >= y + 40 && my <= y + 50) LunaireClient.enableNametags = !LunaireClient.enableNametags;
            if (my >= y + 55 && my <= y + 65) LunaireClient.enableNoHurtCam = !LunaireClient.enableNoHurtCam;
        }
        int cY = y + 130 - 18;
        if (my >= cY && my <= cY + 10) {
            int[] clrs = {0xFF00FFFF, 0xFFFF00FF, 0xFF00FF00, 0xFFFF0000, 0xFFFFFFFF, 0xFFFFFF00};
            for (int i = 0; i < clrs.size(); i++) {
                if (mx >= x + 45 + (i * 18) && mx <= x + 45 + (i * 18) + 10) LunaireClient.accentColor = clrs[i];
            }
        }
        return super.mouseClicked(mx, my, b);
    }

    @Override
    public boolean shouldPause() { return false; }
}
