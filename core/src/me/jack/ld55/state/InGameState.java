package me.jack.ld55.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import me.jack.ld55.entity.tower.Tower;
import me.jack.ld55.level.Level;

public class InGameState extends Screen {

    private Level currentLevel;

    @Override
    public void show() {
        currentLevel = new Level(12,12); //10x10 level with 1 tile border for spawners/exits
    }

    @Override
    public void render() {
        update();
        currentLevel.update();
        currentLevel.render();
    }

    public void update(){
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            x /= 64;
            y /= 64;
            System.out.println("Clicked " + x + "," + y);
            currentLevel.placeTower(new Tower(x*64,y*64));
        }
    }
    @Override
    public void dispose() {

    }
}
