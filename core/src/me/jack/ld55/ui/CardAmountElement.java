package me.jack.ld55.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888;

public class CardAmountElement extends UIElement {
    private int count;
    public CardAmountElement(int x, int y, int w, int h, int count) {
        super(x, y, w, h);
        this.background = new Texture("gui/stacksize.png");
        this.count = count;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, int px, int py) {
        super.draw(shapeRenderer, batch, px, py);
        RomanNumerals.drawNumeral( px + getX() + getW()/2 - 8,py+getY() + getH()/2 - 4 ,batch,count);

    }



    public void setCount(int count) {
        this.count = count;
    }
}
