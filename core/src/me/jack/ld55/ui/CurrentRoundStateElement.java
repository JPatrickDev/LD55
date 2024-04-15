package me.jack.ld55.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.state.InGameState;

public class CurrentRoundStateElement extends UIElement{
    public CurrentRoundStateElement(int x, int y, int w, int h) {
        super(x, y, w, h);
        this.background = createBackgroundFromTiles("gui/dialogs/small");
    }


    BitmapFont font = new BitmapFont();

    @Override
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, int px, int py) {
        super.draw(shapeRenderer, batch, px, py);
         int currentRound = InGameState.instance.currentLevel.roundNum;
         int mobsInRound = InGameState.instance.currentLevel.mobsInRound;
         int remaining = InGameState.instance.currentLevel.remainingToSpawn + InGameState.instance.currentLevel.mobCountRemaining();

         font.draw(batch,"Round: " + currentRound,getX() + px + 10,getY() + py + getH() - 10);
         font.draw(batch, "Remaining: " + remaining + "/" + mobsInRound,getX()+px + 10,getY()+ py+ getH() - 30);
         font.draw(batch, "Lives: " + InGameState.instance.currentLevel.livesRemaining + "/" + 10,getX()+px + 10,getY()+ py + getH() - 50);
    }


}
