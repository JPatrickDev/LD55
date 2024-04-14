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


    public boolean highlight = false;
    public Tile(int x,int y){
        super(x,y);
        if(!textureMap.containsKey(getTexturePath())){
            textureMap.put(getTexturePath(),new Texture(getTexturePath()));
        }
    }

    public static final HashMap<String, Texture> textureMap = new HashMap<>();

    public boolean freeze = false;
    public void renderShapes(ShapeRenderer renderer){
        if(InGameState.inHand != null && !(this instanceof VoidTile)) {
            renderer.setColor(Color.GRAY);
            renderer.rect(this.getX() * Tile.TILE_SIZE, this.getY() * Tile.TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
        if(freeze && false){
            renderer.setColor(Color.BLUE);
            renderer.set(ShapeRenderer.ShapeType.Filled);
            renderer.rect(this.getX() * Tile.TILE_SIZE, this.getY() * Tile.TILE_SIZE, TILE_SIZE, TILE_SIZE);
            renderer.set(ShapeRenderer.ShapeType.Line);
        }
        if(highlight){
            renderer.setColor(new Color(0x00FF0000));
            renderer.set(ShapeRenderer.ShapeType.Filled);
            renderer.rect(this.getX() * Tile.TILE_SIZE, this.getY() * Tile.TILE_SIZE, TILE_SIZE, TILE_SIZE);
            renderer.set(ShapeRenderer.ShapeType.Line);
            highlight = false;
        }

    }

    public void renderImages(SpriteBatch batch){
         if(this.freeze && this instanceof PathTile){
             batch.draw(textureMap.get(((PathTile)this).getFrozenTexture()),this.getX() * Tile.TILE_SIZE,this.getY() * Tile.TILE_SIZE);

         }else{
             batch.draw(textureMap.get(getTexturePath()),this.getX() * Tile.TILE_SIZE,this.getY() * Tile.TILE_SIZE);

         }
    }

    public abstract String getTexturePath();
}
