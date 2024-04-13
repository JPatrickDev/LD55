package me.jack.ld55.level.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.state.InGameState;
import org.xguzm.pathfinding.grid.GridCell;

import java.util.HashMap;

public abstract class Tile extends GridCell {

    public static final int TILE_SIZE = 64;

    public Tile(int x,int y){
        super(x,y);
        if(!textureMap.containsKey(getTexturePath())){
            textureMap.put(getTexturePath(),new Texture(getTexturePath()));
        }
    }

    public static final HashMap<String, Texture> textureMap = new HashMap<>();

    public void renderShapes(ShapeRenderer renderer){
        if(InGameState.inHand != null && !(this instanceof VoidTile)) {
            renderer.setColor(Color.GRAY);
            renderer.rect(this.getX() * Tile.TILE_SIZE, this.getY() * Tile.TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
    }

    public void renderImages(SpriteBatch batch){
        batch.draw(textureMap.get(getTexturePath()),this.getX() * Tile.TILE_SIZE,this.getY() * Tile.TILE_SIZE);
    }

    public abstract String getTexturePath();
}
