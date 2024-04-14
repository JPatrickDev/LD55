package me.jack.ld55.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TextAreaElement extends UIElement{
    public String text;
    public TextAreaElement(int x, int y, int w, int h, String text) {
        super(x, y, w, h);
        this.text = text;
    }

    static BitmapFont font = new BitmapFont();
    @Override
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, int px, int py) {

        font.draw(batch,text,getX() + px,getY() + py + 25);
    }
}
