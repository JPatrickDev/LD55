package me.jack.ld55.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.entity.tower.Tower;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.GrassTile;
import me.jack.ld55.level.tile.Tile;

public class InGameState extends Screen {

    private Level currentLevel;

    public static Tower inHand = null;
    @Override
    public void show() {
        currentLevel = new Level(12,12); //10x10 level with 1 tile border for spawners/exits
       // inHand = new Tower(0,0);
        shapeRenderer.setAutoShapeType(true);
    }
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    SpriteBatch batchRenderer = new SpriteBatch();
    @Override
    public void render() {
        update();
        currentLevel.update();
        currentLevel.render();
        if(inHand != null){
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            x /= 64;
            y /= 64;
            inHand.setX(x * 64);
            inHand.setY(y * 64);
            shapeRenderer.begin();;
            batchRenderer.begin();
            inHand.drawAsPlacing(shapeRenderer,batchRenderer,currentLevel.getTileAt(x,y) instanceof GrassTile);
            shapeRenderer.end();
            batchRenderer.end();
        }
    }

    public void update(){
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            x /= 64;
            y /= 64;
            System.out.println("Clicked " + x + "," + y);
            currentLevel.placeTower(new Tower(x*64,y*64)); //TODO Copy in hand
            inHand = null;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            inHand = new Tower(0,0);
        }
    }
    @Override
    public void dispose() {

    }


}
