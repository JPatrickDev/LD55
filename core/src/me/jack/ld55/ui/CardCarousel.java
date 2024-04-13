package me.jack.ld55.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import me.jack.ld55.entity.tower.IceTower;
import me.jack.ld55.entity.tower.VineTower;
import me.jack.ld55.entity.tower.WizardsTower;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CardCarousel extends UIElement{


    CopyOnWriteArrayList<UIElement> elements = new CopyOnWriteArrayList<>();
    public CardCarousel(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    int scroll = 0;
    @Override
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, int px, int py) {

        int yPos = py;
        for(UIElement e : elements){
            e.setY(getY() + yPos);
            e.setX(getX());
            e.draw(shapeRenderer,batch,px,py);
            yPos += e.getH()  + 5;
        }
        if(Gdx.input.getY() > Gdx.graphics.getHeight()/2){
            scroll++;
        }else{
            scroll--;
        }

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            System.out.println("checking");
            int mx = Gdx.input.getX();
            int my = Gdx.graphics.getHeight() -  Gdx.input.getY();
            for(UIElement e : elements){
                Rectangle box = new Rectangle(px + e.getX() , py + e.getY() ,e.getW(),e.getH());
                if(box.contains(mx,my)){
                    if(listener != null)
                        listener.onClick(e);
                }
            }
        }

        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.rect(getX() + px,getY() + py,getW(),getH());
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
    }


    public void addCard(CardElement toAdd){
        for(UIElement e : elements){
            if(e instanceof CardElement)
            if(((CardElement) e).getTower().getName().equals(toAdd.getTower().getName())){
                System.out.println("Merging");
                ((CardElement) e).count += toAdd.count;
                return;
            }
        }
        toAdd.inCarouselMode = true;
        elements.add(toAdd);
    }
    UIContainer.ClickListener listener;
    public void setCardClickListener(UIContainer.ClickListener listener) {
        this.listener = listener;
    }


    public void removeCard(CardElement clicked) {
        elements.remove(clicked);
    }
}
