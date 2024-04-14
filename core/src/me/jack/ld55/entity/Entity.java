package me.jack.ld55.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.Tile;

public abstract class Entity {

    private int x,y;
    private int w,h;

    public long spawnTime;

    public Entity(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public abstract void drawShapes(ShapeRenderer renderer);
    public abstract void drawImages(SpriteBatch batch);

    public abstract void update(Level parent);


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

    public void move(int x, int y){
        this.x += x;
        this.y += y;
    }

    public void onCollide(Entity with,Level parent){

    }


    public int getTileX(){
        return getX() / Tile.TILE_SIZE;
    }

    public int getTileY(){
        return getY() / Tile.TILE_SIZE;
    }
}
