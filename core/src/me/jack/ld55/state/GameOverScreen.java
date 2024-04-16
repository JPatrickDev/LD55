package me.jack.ld55.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.jack.ld55.LD55Game;
import me.jack.ld55.level.Level;

import java.awt.*;

import static com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888;

public class GameOverScreen extends Screen {

    private int roundNum, killCount;

    public GameOverScreen(int roundNum, int killCount) {
        this.roundNum = roundNum;
        this.killCount = killCount;
    }

    Texture background = null;

    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();

    GlyphLayout layout = new GlyphLayout();

    @Override
    public void render() {
        batch.begin();

        batch.draw(background, 0, 0);
        layout.setText(font, "Game Over!");
        font.draw(batch, "Game Over!", Gdx.graphics.getWidth() / 2 - layout.width / 2, 400);
        layout.setText(font, "Thank You For Playing!");
        font.draw(batch, "Thank You For Playing!", Gdx.graphics.getWidth() / 2 - layout.width / 2, 370);
        layout.setText(font, "You Reached Round " + roundNum);
        font.draw(batch, "You Reached Round " + roundNum, Gdx.graphics.getWidth() / 2 - layout.width / 2, 340);
        layout.setText(font, "Killing " + killCount + " enemies.");
        font.draw(batch, "Killing " + killCount + " enemies.", Gdx.graphics.getWidth() / 2 - layout.width / 2, 310);
        layout.setText(font, "Press SpaceBar to return to Level Selection");
        font.draw(batch, "Press SpaceBar to return to Level Selection", Gdx.graphics.getWidth() / 2 - layout.width / 2, 280);
        batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            this.dispose();
            LD55Game.currentScreen = new LevelSelectState();
            LD55Game.currentScreen.show();;
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
        batch.dispose();
    }

    @Override
    public void show() {
        background = createBackgroundFromTiles("gui/dialogs");
    }

    public Pixmap locate(String path, String type) {
        if (Gdx.files.internal(path + type).exists()) {
            return new Pixmap(Gdx.files.internal(path + type));
        } else {
            return new Pixmap(Gdx.files.internal(path + "/all.png"));
        }
    }

    public Texture createBackgroundFromTiles(String path) {
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        Pixmap inner = locate(path, "/inner.png");
        Pixmap vertical = locate(path, "/vertical.png");
        Pixmap horizontal = locate(path, "/horizontal.png");
        Pixmap corner = locate(path, "/corner.png");
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
