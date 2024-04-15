package me.jack.ld55.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888;

public abstract class UIElement {

    private int x,y,w,h;

    protected Texture background = null;
    public UIElement(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public BitmapFont font = new BitmapFont();
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, int px,int py){
        if(this.background != null){
            batch.draw(background,px + getX(), py + getY());
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void onClick(){

    }

    public Pixmap locate(String path,String type){
        if(Gdx.files.internal(path + type ).exists()){
            return new Pixmap(Gdx.files.internal(path + type));
        }else{
            return new Pixmap(Gdx.files.internal(path + "/all.png"));
        }
    }


    public Texture createBackgroundFromTiles(String path) {
        int w = getW();
        int h = getH();
        Pixmap inner =  locate(path,"/inner.png");
        Pixmap vertical = locate(path,"/vertical.png");
        Pixmap horizontal = locate(path,"/horizontal.png");
        Pixmap corner = locate(path,"/corner.png");
        Pixmap finalTexture = new Pixmap(w, h, RGBA8888);

        for (int i = 0; i != (h - corner.getHeight()) / vertical.getHeight(); i++) {
            finalTexture.drawPixmap(vertical, 0, corner.getHeight() + i * vertical.getHeight());
            finalTexture.drawPixmap(vertical, w - vertical.getWidth(), corner.getHeight() + i * vertical.getHeight());
        }
        for (int i = 0; i != (w - corner.getWidth()) / vertical.getWidth(); i++) {
            finalTexture.drawPixmap(horizontal, corner.getWidth() + i * horizontal.getWidth(), 0);
            finalTexture.drawPixmap(horizontal, corner.getWidth() + i * horizontal.getWidth(), w - vertical.getHeight() + corner.getHeight() / 2);
        }

        for (int xx = 0; xx != (w - corner.getWidth()) / inner.getWidth(); xx++) {
            for (int yy = 0; yy != (h - corner.getHeight()) / inner.getHeight(); yy++) {
                finalTexture.drawPixmap(inner, xx * inner.getWidth() + corner.getWidth(), yy * inner.getHeight() + corner.getHeight());
            }
        }

        finalTexture.drawPixmap(corner, 0, 0);
        finalTexture.drawPixmap(corner, w - corner.getWidth(), 0);
        finalTexture.drawPixmap(corner, 0, h - corner.getHeight());
        finalTexture.drawPixmap(corner, w - corner.getWidth(), h - corner.getHeight());
        inner.dispose();
        vertical.dispose();
        horizontal.dispose();
        corner.dispose();
        return new Texture(finalTexture);
    }
}
