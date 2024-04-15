package me.jack.ld55.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.jack.ld55.LD55Game;
import me.jack.ld55.level.Level;

import java.awt.*;

import static com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888;

public class LevelSelectState extends Screen{
    Texture l1 = Level.loadLevelAsIcon("level1");
    Texture l2 = Level.loadLevelAsIcon("level2");
    Texture l3 = Level.loadLevelAsIcon("level3");

    Texture background = null;

    SpriteBatch batch = new SpriteBatch();
     BitmapFont font = new BitmapFont();
    @Override
    public void render() {
        batch.begin();

        batch.draw(background,0,0);

        font.setColor(Color.WHITE);
        batch.draw(l1,70, Gdx.graphics.getHeight()/2 - 270/2,270,270);
        font.draw(batch,"Level 1", 70+ 270/2 - 20,Gdx.graphics.getHeight()/2 - 270/2 - 20);
        batch.draw(l2,370,Gdx.graphics.getHeight()/2 - 270/2,270,270);
        font.draw(batch,"Level 2", 370+ 270/2 - 20,Gdx.graphics.getHeight()/2 - 270/2 - 20);
        batch.draw(l3,670,Gdx.graphics.getHeight()/2 - 270/2,270,270);
        font.draw(batch,"Level 3", 670+ 270/2 - 20,Gdx.graphics.getHeight()/2 - 270/2 - 20);
        batch.end();

        Rectangle level1 = new Rectangle(70,Gdx.graphics.getHeight()/2 - 270/2,270,270);
        Rectangle level2 = new Rectangle(370,Gdx.graphics.getHeight()/2 - 270/2,270,270);
        Rectangle level3 = new Rectangle(670,Gdx.graphics.getHeight()/2 - 270/2,270,270);
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            if(level1.contains(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY())){
                LD55Game.currentScreen = new InGameState("level1");
                LD55Game.currentScreen.show();
            }
            if(level2.contains(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY())){
                LD55Game.currentScreen = new InGameState("level2");
                LD55Game.currentScreen.show();
            }
            if(level3.contains(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY())){
                LD55Game.currentScreen = new InGameState("level3");
                LD55Game.currentScreen.show();
            }
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        l1.dispose();
        l2.dispose();
        l3.dispose();
        font.dispose();
        batch.dispose();
    }

    @Override
    public void show() {
        background = createBackgroundFromTiles("gui/dialogs");
    }
    public Pixmap locate(String path,String type){
        if(Gdx.files.internal(path + type ).exists()){
            return new Pixmap(Gdx.files.internal(path + type));
        }else{
            return new Pixmap(Gdx.files.internal(path + "/all.png"));
        }
    }
    public Texture createBackgroundFromTiles(String path) {
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        Pixmap inner =  locate(path,"/inner.png");
        Pixmap vertical = locate(path,"/vertical.png");
        Pixmap horizontal = locate(path,"/horizontal.png");
        Pixmap corner = locate(path,"/corner.png");
        Pixmap finalTexture = new Pixmap(w, h, RGBA8888);
        for (int xx = 0; xx != (w - corner.getWidth()) / inner.getWidth(); xx++) {
            for (int yy = 0; yy != (h - corner.getHeight()) / inner.getHeight(); yy++) {
                finalTexture.drawPixmap(inner, xx * inner.getWidth() + corner.getWidth(), yy * inner.getHeight() + corner.getHeight());
            }
        }
        for (int i = 0; i != (h - corner.getHeight()) / vertical.getHeight(); i++) {
            finalTexture.drawPixmap(vertical, 0, corner.getHeight() + i * vertical.getHeight());
            finalTexture.drawPixmap(vertical, w - vertical.getWidth(), corner.getHeight() + i * vertical.getHeight());
        }
        for (int i = 0; i != (w - corner.getWidth()) / vertical.getWidth(); i++) {
            finalTexture.drawPixmap(horizontal, corner.getWidth() + i * horizontal.getWidth(), 0);
            finalTexture.drawPixmap(horizontal, corner.getWidth() + i * horizontal.getWidth(), h - vertical.getHeight());
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
