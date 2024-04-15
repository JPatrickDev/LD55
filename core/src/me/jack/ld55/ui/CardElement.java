package me.jack.ld55.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.LD55Game;
import me.jack.ld55.entity.rune.Rune;
import me.jack.ld55.entity.tower.Tower;
import me.jack.ld55.entity.tower.TowerTypeEnum;
import me.jack.ld55.entity.tower.WizardsTower;
import me.jack.ld55.level.tile.Tile;

import java.awt.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CardElement extends UIElement {
    private Texture cardTexture = null, smallTexture = null, lockedTexture = null, backTexture = null, spellcardfront = null, spellcardback = null, spellcardsmall = null;

    private Tower tower;


    private int count = 1;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        if (countDisplay != null) {
            countDisplay.setCount(count);
        }
    }

    public List<StatBarElement> statBars = new ArrayList<>();

    CardAmountElement countDisplay;

    public boolean mode = false; //False = front, true = back


    public static HashMap<String, Texture> textureMap = new HashMap<>();

    public Texture getCardTexture(String path) {
        if (textureMap.containsKey(path)) {
            return textureMap.get(path);
        } else {
            Texture t = new Texture(path);
            textureMap.put(path, t);
            return t;
        }
    }

    public CardElement(int x, int y, int w, int h, Tower tower) {
        super(x, y, w, h);
        cardTexture = getCardTexture("cards/basetower.png");
        backTexture = getCardTexture("cards/cardback.png");
        smallTexture = getCardTexture("cards/smallcards.png");
        lockedTexture = getCardTexture("cards/lockedcardmask.png");

        spellcardfront = getCardTexture("cards/spellcardfront.png");
        spellcardback = getCardTexture("cards/spellcardback.png");
        spellcardsmall = getCardTexture("cards/spellcardsmall.png");
        this.tower = tower;
        if (tower instanceof WizardsTower) {
            cost.put(Rune.RED, LD55Game.rand(1) + 2);
        } else {
            cost.put(Rune.RED, LD55Game.rand(3) + 2);
            cost.put(Rune.BLUE, LD55Game.rand(1) + 1);
        }
        statBars.add(new StatBarElement(42, 72, 94, 18, valueToRange((int) tower.range, 3 * Tile.TILE_SIZE), Color.LIME, Color.GREEN));
        statBars.add(new StatBarElement(42, 72 - 22, 94, 18, valueToRange((int) tower.damage, 75), Color.RED, Color.ORANGE));
        statBars.add(new StatBarElement(42, 72 - 22 - 20, 94, 18, 6 - valueToRange((int) tower.fireRate, 3000), Color.CYAN, Color.YELLOW));

        countDisplay = new CardAmountElement(50, getH() - 20, 64, 64, count);

    }

    public int valueToRange(int input, int max) {
        if (input == 0)
            return 0;
        int size = max / 6;
        return (int) Math.ceil(new Double((input)) / new Double(size));
    }

    public boolean inCarouselMode = false;

    public HashMap<Rune, Integer> cost = new HashMap<>();

    @Override
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, int x, int y) {
        for (int i = count; i != 0; i--) {
            drawCardUI(shapeRenderer, batch, x + i * 3, y + i * 8);
        }


        if (!this.inCarouselMode) {
            font.setColor(Color.WHITE);
            font.draw(batch, "Cost:", x + getX() + getW() / 2 - 20, y + getY() + 5);
        }
        int xof = 0;

        int startPos = cost.keySet().size();
        xof = getW() / 2 - (startPos * 25) / 2;
        for (Rune r : cost.keySet()) {
            int p = cost.get(r);
            Rune.renderMiniRuneAt(batch, shapeRenderer, x + getX() + xof, y + getY() - 25, p, r);
            xof += 25;
        }
        if ((!mode && !inCarouselMode) || (inCarouselMode && !mode)) {

            for (StatBarElement e : statBars) {
                e.draw(shapeRenderer, batch, getX() + x, getY() + y);
            }

        }


        if (!RuneCollectionElement.canAfford(this))
            batch.draw(lockedTexture, x + getX(), y + getY());
        countDisplay.draw(shapeRenderer, batch, getX() + x, getY() + y);
    }

    public void drawCardUI(ShapeRenderer shapeRenderer, SpriteBatch batch, int x, int y) {
        if (inCarouselMode) {
            if (tower.type != TowerTypeEnum.SPELL)
                batch.draw(smallTexture, x + getX(), y + getY());
            else {
                batch.draw(spellcardsmall, x + getX(), y + getY());
            }
            tower.setX(x + getX() + (118 / 2));
            tower.setY(y + getY() + 86 / 2 - tower.getH() / 2);
            tower.drawImages(batch);

            return;
        }
        if (!mode) {
            if (tower.type != TowerTypeEnum.SPELL)
                batch.draw(cardTexture, x + getX(), y + getY());
            else
                batch.draw(spellcardfront, x + getX(), y + getY());
            tower.setX(x + getX() + (118 / 2 - tower.getW() / 2) + 16);
            tower.setY(y + getY() + 86 / 2 - tower.getH() / 2 + 85);
            tower.drawImages(batch);

            font.setColor(Color.GRAY);
            //   font.draw(batch, tower.getName(), x + getX() + 5, y + getY() + 85);
        } else {
            if (tower.type != TowerTypeEnum.SPELL)
                batch.draw(backTexture, x + getX(), y + getY());
            else
                batch.draw(spellcardback, x + getX(), y + getY());
            font.setColor(Color.BLACK);
            // font.draw(batch, tower.getName(), x + getX() + 15, y + getY() + 160);
            drawTextInCenter(new Rectangle(x + getX() + 16, x + getY() + 171, 118, 31), tower.getName(), batch);
            drawTextInCenter(new Rectangle(x + getX() + 16, x + getY() + 138, 118, backTexture.getHeight()), tower.getDescription(), batch);
        }

        if (!RuneCollectionElement.canAfford(this))
            batch.draw(lockedTexture, x + getX(), y + getY());
    }

    GlyphLayout layout = new GlyphLayout();

    private void drawTextInCenter(Rectangle rectangle, String text, SpriteBatch batch) {
        String[] words = text.split(" ");
        String currentLine = "";
        List<String> lines = new ArrayList<>();
        for (String w : words) {
            layout.setText(font, currentLine + w);
            if (layout.width > rectangle.getWidth()) {
                lines.add(currentLine);
                currentLine = w + " ";
            } else {
                currentLine += w + " ";
            }
        }
        lines.add(currentLine);
        int drawH = (int) (lines.size() * layout.height);
        int start = (int) rectangle.getY();
        font.setColor(Color.BLACK);
        for (String s : lines) {
            layout.setText(font, s);
            font.draw((Batch) batch, s, (int) rectangle.getX() + (int) (rectangle.getWidth() / 2 - layout.width / 2), start);
            start -= layout.height * 2;
        }
    }


    public Tower getTower() {
        return this.tower;
    }

    @Override
    public int getH() {
        if (inCarouselMode) {
            return smallTexture.getHeight() + count * 10;
        } else
            return super.getH() + count * 10;
    }


    @Override
    public void setX(int x) {
        super.setX(x);
        tower.setX(x);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        tower.setY(y);
    }
}
