package me.jack.ld55.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.LD55Game;
import me.jack.ld55.entity.tower.Tower;

import java.io.FileOutputStream;

public class CardElement extends UIElement {
    private Texture cardTexture = null,smallTexture = null;

    private Tower tower;


    public int count = 1;

    public CardElement(int x, int y, int w, int h, Tower tower) {
        super(x, y, w, h);
        cardTexture = new Texture("cards/basetower.png");
        smallTexture = new Texture("cards/smallcards.png");
        this.tower = tower;
    }

    public boolean inCarouselMode = false;

    @Override
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, int x, int y) {
      for(int i = 0; i != count;i++){
          drawCardUI(shapeRenderer,batch,x,y - i*8);
      }
      font.draw(batch,"x" + count,x + getX() + getW() -(count*8) - 15,y + getY() + 5);
    }

    public void drawCardUI(ShapeRenderer shapeRenderer,SpriteBatch batch,int x,int y){
        if(inCarouselMode){
            batch.draw(smallTexture, x + getX(), y + getY());
            tower.setX(x + getX() + (getW() / 2 - 32));
            tower.setY(y + getY());
            tower.drawImages(batch);

            return;
        }
        batch.draw(cardTexture, x + getX(), y + getY());
        tower.setX(x + getX() + (getW() / 2 - 32));
        tower.setY(y + getY() + 97);
        tower.drawImages(batch);

        font.setColor(Color.GRAY);
        font.draw(batch, tower.getName(), x + getX() + 5, y + getY() + 85);

        switch (tower.type){
            case AOE:
            case RANGED: {
                font.draw(batch, "Range: " + tower.range, x + getX() + 5, y + getY() + 60);
                font.draw(batch, "Damage: " + tower.range, x + getX() + 5, y + getY() + 42);
                font.draw(batch, "Fire Rate: " + tower.range, x + getX() + 5, y + getY() + 24);
                break;
            }
        }
    }


    public Tower getTower() {
        return this.tower;
    }

    @Override
    public int getH(){
        if(inCarouselMode){
            return smallTexture.getHeight() + count * 10;
        } else
        return  super.getH() + count * 10;
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
