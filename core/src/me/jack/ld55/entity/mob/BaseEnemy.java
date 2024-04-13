package me.jack.ld55.entity.mob;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.Tile;
import org.xguzm.pathfinding.grid.GridCell;

import java.util.ArrayList;
import java.util.List;

public class BaseEnemy extends Mob{
    int tx,ty;
    public BaseEnemy(int x, int y, int targetX,int targetY, Level parent) {
        super(x, y, 32,32);
        this.tx = targetX;
        this.ty = targetY;

        path = new ArrayList<>(parent.finder.findPath(getX()/64,getY()/64,tx,ty,parent.pathfindingGrid));
        if(path != null && !path.isEmpty())
            currentTarget = path.get(pathPos);
    }

    List<GridCell> path = null;
    int pathPos = 0;
    GridCell currentTarget;

    @Override
    public void drawShapes(ShapeRenderer renderer) {
        renderer.setColor(Color.WHITE);
        renderer.rect(getX() + 16,getY() + 16,getW(),getH());
        if(path != null){
            for(GridCell c : path){
                if(c != currentTarget)
                renderer.setColor(Color.PINK);
                else
                    renderer.setColor(Color.RED);
             //   renderer.rect(c.getX() * Tile.TILE_SIZE,c.getY() * Tile.TILE_SIZE, Tile.TILE_SIZE,Tile.TILE_SIZE);
             //   renderer.rect(c.getX() * Tile.TILE_SIZE+ 16,c.getY() * Tile.TILE_SIZE +16,32,32);
            }
        }
    }

    @Override
    public void drawImages(SpriteBatch batch) {

    }

    @Override
    public void update(Level parent) {


        if(currentTarget != null){
            if(currentTarget.getX() * Tile.TILE_SIZE > this.getX()) {
                move(4,0);
            } else if(currentTarget.getX() * Tile.TILE_SIZE < this.getX())
                move(-4,0);
            else if(currentTarget.getY() * Tile.TILE_SIZE > this.getY())
                move(0,4);
            else if(currentTarget.getY() * Tile.TILE_SIZE < this.getY())
                move(0,-4);
            else {
                pathPos++;
                if(pathPos >= path.size()){
                    pathPos = 0;
                    path = null;
                    currentTarget = null;
                    return;
                }
                currentTarget = path.get(pathPos);
            }
            }
    }

    @Override
    public void onSpawn() {

    }

    @Override
    public void onDeath() {

    }

    @Override
    public float getMaxHealth() {
        return 25;
    }
}
