package me.jack.ld55.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.LD55Game;

public class StatBarElement extends UIElement {


    public Texture texture = null;

    private Color base,highlight;
    public StatBarElement(int x, int y, int w, int h, int barsToFill, Color baseColor, Color highlightColor) {
        super(x, y, w, h);
this.base = baseColor;
this.highlight = highlightColor;
      this.texture = generateNewSprite(barsToFill,baseColor,highlightColor);

    }

    public Texture generateNewSprite(int barsToFill, Color baseColor, Color highlightColor) {
        Texture t = null;
        Pixmap baseMap = new Pixmap(Gdx.files.internal("cards/statbar/" + barsToFill + ".png"));
        System.out.println(baseMap.getFormat());
        for (int xx = 0; xx != baseMap.getWidth(); xx++) {
            for (int yy = 0; yy != baseMap.getHeight(); yy++) {
                int c = baseMap.getPixel(xx, yy);
                if (c == -1308557313) {
                    baseMap.drawPixel(xx, yy, Color.rgba8888(highlightColor));
                } else if (c == -16720641) {
                    baseMap.drawPixel(xx, yy, Color.rgba8888(baseColor));
                } else {
                    System.out.println("No match" + c);
                }
            }
        }

        t = new Texture(baseMap);
        baseMap.dispose();
        return t;
    }

    int i = 0;
    @Override
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, int px, int py) {
        batch.draw(texture, px + getX(), py + getY());
    }
}
