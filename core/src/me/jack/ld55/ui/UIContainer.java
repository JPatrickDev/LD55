package me.jack.ld55.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class UIContainer {

    private List<UIElement> elements;


    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    private int w,h; //TODO: Some sort of 9patch type thing for easy UI dialogs?
    private int x,y;

    public ClickListener listener;
    public UIContainer(int x,int y,int w,int h){
        this.w = w;
        this.h = h;
        this.x = x;
        this.y = y;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        spriteBatch = new SpriteBatch();
        this.elements = new ArrayList<>();
    }

    public Texture background;

    public void addElement(UIElement element){
        elements.add(element);
    }


    public void draw(){
        shapeRenderer.begin();
        if(background == null) {
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.rect(x, y, w, h);
        }
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        spriteBatch.begin();
        if(background != null)
            spriteBatch.draw(background,x,y);
        for(UIElement e : elements){
            e.draw(shapeRenderer,spriteBatch, x,y);
        }

        spriteBatch.end();
        shapeRenderer.end();


    }

    public void update(){
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            System.out.println("Clicked");
            int mx = Gdx.input.getX();
            int my = Gdx.graphics.getHeight() -  Gdx.input.getY();
            for(UIElement e : elements){
                System.out.println(e.getH());
                Rectangle box = new Rectangle(e.getX() + x, e.getY() + y,e.getW(),e.getH());
                if(box.contains(mx,my)){
                    System.out.println("UI Element clicked");
                    if(listener != null)
                        listener.onClick(e);
                }
            }
        }
    }

    public interface ClickListener{
        public void onClick(UIElement clicked);
    }
}
