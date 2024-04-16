package me.jack.ld55.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import me.jack.ld55.LD55Game;
import me.jack.ld55.entity.tower.*;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.GrassTile;
import me.jack.ld55.level.tile.PathTile;
import me.jack.ld55.level.tile.Tile;
import me.jack.ld55.spells.RockSmashSpell;
import me.jack.ld55.spells.SpikeStripSpell;
import me.jack.ld55.ui.*;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class InGameState extends Screen {

    public InGameState(String levelName){
        instance = this;
        currentLevel = Level.loadLevel(levelName);
    }

    public static InGameState instance;
    public Level currentLevel;

    public static Tower inHand = null;

    private CopyOnWriteArrayList<UIContainer> containers = new CopyOnWriteArrayList<>();

    private UIContainer roundEndDialog = null;
    private SidebarContainer sidebar = null;

    private TextAreaElement nextRoundButton = null;


    BitmapFont font = new BitmapFont();

    @Override
    public void show() {
        nextRoundButton = new TextAreaElement(300,600,200,40,"Start Next Round");
        shapeRenderer.setAutoShapeType(true);
        sidebar = new SidebarContainer(775, 0, 175 + 25 + 32, 768);
        sidebar.setCardSelectionListener(new UIContainer.ClickListener() {

            @Override
            public void onClick(UIElement clicked) {
                if (clicked instanceof CardElement) {
                    if (inHand != null) {
                        sidebar.addCard(new CardElement(0, 0, 150, 300, inHand));
                    }
                    inHand = ((CardElement) clicked).getTower().clone();
                    ((CardElement) clicked).setCount(((CardElement) clicked).getCount() - 1);
                    if (((CardElement) clicked).getCount() <= 0) {
                        sidebar.removeCard(clicked);
                    }
                } else {

                }
            }

            @Override
            public void onMouseIn(UIElement in) {
                if (in instanceof CardElement) {
                    ((CardElement) in).mode = true;
                }
            }

            @Override
            public void onMouseOut(UIElement out) {
                if (out instanceof CardElement) {
                    ((CardElement) out).mode = true;
                }
            }
        });
        // sidebar.addElement();
        new RuneCollectionElement(0, 210, 300, 400);
        sidebar.addElement(new InventoryElement(24, 100, 125  +60, 70));
        sidebar.addElement(new CurrentRoundStateElement(24,25,125 + 60,70));
    }


    private void showRoundEndDialog() {
        if (inHand != null) {
            sidebar.addCard(new CardElement(0, 0, 150, 300, inHand));
            inHand = null;
        }

        UIContainer cards = new UIContainer(128, 128, 524, 518);
        //  cards.background = new Texture("gui/levelendbackground.png");

        List<Tower> choices = new ArrayList<>();
        choices.add(new VineTower(0,0));
        choices.add(new WizardsTower(0,0));
        choices.add(new IceTower(0,0));
        choices.add(new SpikeStripSpell(0,0));
        choices.add(new RockSmashSpell(0,0));
        choices = choices.stream().filter(RuneCollectionElement::canAfford).collect(Collectors.toList());

        List<Tower> finalChoices = new ArrayList<>();
        int found = 0;
        int toFind = LD55Game.rand(3) + 1;
        while(found != toFind){
            if(choices.isEmpty())
                break;
            int i = LD55Game.rand(choices.size());
            finalChoices.add(choices.get(i));
            choices.remove(i);
            found++;
        }
        int x = 10;
        x += (232 - (finalChoices.size() * 150)/2);
        for(Tower t : finalChoices){
            CardElement el = new CardElement(x, 150, 150, 200, t);
            el.setCount(LD55Game.rand(5) + 1);
            cards.addElement(el);
            x+=160;
        }

        if (currentLevel.roundNum != 0)
            cards.addElement(new TextAreaElement(50, 450, 412, 30, "Round " + currentLevel.roundNum + " Completed"));
        if (currentLevel.roundNum == 0)
            cards.addElement(new TextAreaElement(50, 450, 412, 30, "Game Starting"));
        cards.addElement(new TextAreaElement(50, 400, 412, 30, "Available Cards:"));
        cards.listener = new UIContainer.ClickListener() {
            @Override
            public void onClick(UIElement clicked) {
                if (clicked instanceof CardElement) {
                    if(((CardElement) clicked).getCount() <= 0){
                        System.out.println("Clicked on empty");
                        return;
                    }
                    CardElement e = new CardElement(0, 0, 150, 300, ((CardElement) clicked).getTower().clone());
                    e.setCount(1);
                    e.cost = new HashMap<>();
                    if (RuneCollectionElement.buy((CardElement) clicked)) {
                        sidebar.addCard(e);
                        ((CardElement) clicked).setCount(((CardElement) clicked).getCount()-1);
                       // cards.removeElement(clicked);
                        if (cards.elements.stream().noneMatch(x -> x instanceof CardElement)) {
                            hideRoundEndDialog();
                        }

                    }
                    //     inHand = ((CardElement) clicked).getTower().clone();
                    //  hideRoundEndDialog();
                    //  currentLevel.startRound();
                } else if (clicked instanceof TextAreaElement) {
                    if (sidebar.hasCardsInHand() || noMoreAffordableCards() || true) {
                        if (((TextAreaElement) clicked).text.contains("Place Towers")) {
                            hideRoundEndDialog();
                        } else if (((TextAreaElement) clicked).text.startsWith("Start Round")) {
                            hideRoundEndDialog();
                            currentLevel.startRound();
                        }
                    }
                    System.out.println(((TextAreaElement) clicked).text);
                }
            }

            @Override
            public void onMouseIn(UIElement in) {
                if (in instanceof CardElement) {
                    ((CardElement) in).mode = true;
                }
            }

            @Override
            public void onMouseOut(UIElement out) {
                if (out instanceof CardElement) {
                    ((CardElement) out).mode = false;
                }
            }
        };


        cards.addElement(new TextAreaElement(50, 25, 150, 40, "Start Round " + (currentLevel.roundNum + 1)));
        cards.addElement(new TextAreaElement(300, 25, 150, 40, "Place Towers "));

        roundEndDialog = cards;
    }

    private boolean noMoreAffordableCards() {
        if (this.roundEndDialog == null) {
            return true;
        }
        for (UIElement e : this.roundEndDialog.elements) {
            if (e instanceof CardElement) {
                if (RuneCollectionElement.canAfford((CardElement) e)) {
                    return false;
                }
            }
        }
        return true;
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

        for (UIContainer c : containers) {
            // c.draw();
        }
        if (roundEndDialog != null)
            roundEndDialog.draw();

        sidebar.draw();

        shapeRenderer.begin();
        batchRenderer.begin();
        if (inHand != null) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            x /= 64;
            y /= 64;
            inHand.setX(x * 64);
            inHand.setY(y * 64);

            inHand.drawAsPlacing(shapeRenderer, batchRenderer, (inHand.type != TowerTypeEnum.SPELL && (currentLevel.getTileAt(x, y) instanceof GrassTile)) || (inHand.type == TowerTypeEnum.SPELL && currentLevel.getTileAt(x, y) instanceof PathTile), currentLevel);

        } else {
            if (roundEndDialog == null && currentLevel.remainingToSpawn == 0 && !currentLevel.mobsRemaining()) {
                Rectangle r = new Rectangle(nextRoundButton.getX(), nextRoundButton.getY(), nextRoundButton.getW(), nextRoundButton.getH());
                //font.draw(batchRenderer, "START NEXT ROUND", 300, 300);
                //batchRenderer.draw(nextRoundButton, 300, 300);
                nextRoundButton.draw(shapeRenderer,batchRenderer,0,0);
                int x = Gdx.input.getX();
                int y = Gdx.graphics.getHeight() - Gdx.input.getY();
                if (r.contains(x, y) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    currentLevel.startRound();
                }
            }
        }

        shapeRenderer.end();
        batchRenderer.end();
    }

    public void update() {
        if (currentLevel.livesRemaining <= 0) {
            this.dispose();
            LD55Game.currentScreen = new GameOverScreen(currentLevel.roundNum, currentLevel.totalKills);
            LD55Game.currentScreen.show();
        }
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

        if (roundEndDialog != null && noMoreAffordableCards()) {
            hideRoundEndDialog();
        }
    }

    @Override
    public void dispose() {

    }


    public void notifyRoundEnd() {
        System.out.println("Round ended: " + currentLevel.roundNum);
        showRoundEndDialog();
    }


}
