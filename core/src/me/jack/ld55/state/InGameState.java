package me.jack.ld55.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.jack.ld55.entity.tower.IceTower;
import me.jack.ld55.entity.tower.Tower;
import me.jack.ld55.entity.tower.VineTower;
import me.jack.ld55.entity.tower.WizardsTower;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.GrassTile;
import me.jack.ld55.level.tile.Tile;
import me.jack.ld55.ui.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InGameState extends Screen {

    private Level currentLevel;

    public static Tower inHand = null;

    private CopyOnWriteArrayList<UIContainer> containers = new CopyOnWriteArrayList<>();

    private UIContainer roundEndDialog = null;
    private UIContainer sidebar = null;

    @Override
    public void show() {
        currentLevel = new Level(12, 12); //10x10 level with 1 tile border for spawners/exits
        shapeRenderer.setAutoShapeType(true);
        sidebar = new SidebarContainer(775,0,175,768);
    }


    private void showRoundEndDialog() {


        UIContainer cards = new UIContainer(128, 128, 512, 512);
        cards.addElement(new CardElement(0, 280, 150, 200, new WizardsTower(0, 0)));
        cards.addElement(new CardElement(160, 280, 150, 200, new VineTower(0, 0)));
        cards.addElement(new CardElement(320, 280, 150, 200, new IceTower(0, 0)));
        if(currentLevel.roundNum != 0)
          cards.addElement(new TextAreaElement(150,500,512,100,"Round " + currentLevel.roundNum + " Completed"));
        if(currentLevel.roundNum == 0)
            cards.addElement(new TextAreaElement(150,500,512,100,"Game Starting"));


        if(currentLevel.roundNum != 0)
            cards.addElement(new TextAreaElement(150,500,512,100,"Round " + currentLevel.roundNum + " Completed"));
        if(currentLevel.roundNum == 0)
            cards.addElement(new TextAreaElement(150,500,512,100,"Game Starting"));
        cards.listener = clicked -> {
            if (clicked instanceof CardElement) {
                inHand = ((CardElement) clicked).getTower().clone();
                hideRoundEndDialog();
                currentLevel.startRound();
            }
        };
        cards.addElement(new TextAreaElement(150,200,512,100,"Start Round " + (currentLevel.roundNum + 1)));
        roundEndDialog = cards;
    }

    private void hideRoundEndDialog() {
        roundEndDialog = null;
    }

    ShapeRenderer shapeRenderer = new ShapeRenderer();
    SpriteBatch batchRenderer = new SpriteBatch();


    @Override
    public void render() {
        update();
        currentLevel.update(this);
        currentLevel.render();
        if (inHand != null) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            x /= 64;
            y /= 64;
            inHand.setX(x * 64);
            inHand.setY(y * 64);
            shapeRenderer.begin();
            batchRenderer.begin();
            inHand.drawAsPlacing(shapeRenderer, batchRenderer, currentLevel.getTileAt(x, y) instanceof GrassTile);
            shapeRenderer.end();
            batchRenderer.end();
        }
        for (UIContainer c : containers) {
            // c.draw();
        }
        if (roundEndDialog != null)
            roundEndDialog.draw();

        sidebar.draw();
    }

    public void update() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && inHand != null) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            x /= 64;
            y /= 64;
            System.out.println("Clicked " + x + "," + y);
            inHand.setX(x * 64);
            inHand.setY(y * 64);
            if (currentLevel.placeTower(inHand.clone()))
                inHand = null;
        }

        for (UIContainer c : containers) {
            c.update();
        }
        if (roundEndDialog != null)
            roundEndDialog.update();
    }

    @Override
    public void dispose() {

    }


    public void notifyRoundEnd() {
        System.out.println("Round ended: " + currentLevel.roundNum);
        showRoundEndDialog();
    }


}
