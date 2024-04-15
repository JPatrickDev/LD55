package me.jack.ld55.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TextAreaElement extends UIElement {
    public String text;

    public TextAreaElement(int x, int y, int w, int h, String text) {
        super(x, y, w, h);
        this.text = text;
        this.background = createBackgroundFromTiles("gui/dialogs/small");
    }

    static BitmapFont font = new BitmapFont();
    GlyphLayout layout = new GlyphLayout();

    @Override
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, int px, int py) {
        super.draw(shapeRenderer, batch, px, py);
        layout.setText(font, text);
        font.draw(batch, text, getX() + px + getW() / 2 - layout.width / 2, getY() + py + getH() / 2 + layout.height / 2);
    }
}
