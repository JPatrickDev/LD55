package me.jack.ld55.entity.tower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import me.jack.ld55.entity.Entity;
import me.jack.ld55.entity.mob.Mob;
import me.jack.ld55.entity.projectile.Projectile;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.PathTile;
import me.jack.ld55.level.tile.Tile;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Tower extends Entity {
    public float damage = 0;
    public float range;
    public long fireRate;

    protected Texture texture;

    private Projectile weapon;

    protected String name;

    public TowerTypeEnum type;

    public Tower(int x, int y,TowerTypeEnum type) {
        super(x, y, Tile.TILE_SIZE, Tile.TILE_SIZE);
        this.type = type;
    }

    @Override
    public void drawShapes(ShapeRenderer renderer) {
    //    renderer.setColor(Color.BLACK);
      //  renderer.set(ShapeRenderer.ShapeType.Filled);
      //  renderer.circle(getX() + 32, getY() + 32, 16);
      //  renderer.set(ShapeRenderer.ShapeType.Line);
    }

    public void drawAsPlacing(ShapeRenderer renderer, SpriteBatch batch,boolean canPlace, Level parent){
        if(canPlace){
         //   renderer.set(ShapeRenderer.ShapeType.Line);
         //   renderer.circle(getX() + 32, getY() + 32, range);
            drawShapes(renderer);
            drawImages(batch);
            List<Tile> pathsInRange = parent.getTilesInRadius(getX() + 32,getY() + 32,range - 4).stream().filter(x -> x instanceof PathTile).collect(Collectors.toList());
            for(Tile t : pathsInRange){
                t.highlight = true;
            }
        }else{

        }
    }
    @Override
    public void drawImages(SpriteBatch batch) {
        batch.draw(texture,getX(),getY());
    }

    long lastShot = 0;
    @Override
    public void update(Level parent) {

    }

    public String getName() {
        return name;
    }

    public abstract Tower clone();


    public void onSpawn(Level level){

    }

    public abstract String getDescription();
}
