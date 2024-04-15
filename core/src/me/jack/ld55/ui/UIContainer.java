package me.jack.ld55.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888;

public class UIContainer {

    public CopyOnWriteArrayList<UIElement> elements;


    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    private int w;
    public int h; //TODO: Some sort of 9patch type thing for easy UI dialogs?
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
        this.elements = new CopyOnWriteArrayList<>();
        this.background = createBackgroundFromTiles("gui/dialogs");
    }


    public Texture createBackgroundFromTiles(String path){
        Pixmap inner = new Pixmap(Gdx.files.internal(path + "/inner.png"));
        Pixmap vertical = new Pixmap(Gdx.files.internal(path + "/vertical.png"));
        Pixmap horizontal = new Pixmap(Gdx.files.internal(path + "/horizontal.png"));
        Pixmap corner =  new Pixmap(Gdx.files.internal(path + "/corner.png"));
        Pixmap finalTexture = new Pixmap(w,h,RGBA8888);
        for(int xx =0 ; xx != (w-corner.getWidth())/inner.getWidth();xx++){
            for(int yy = 0; yy != (h-corner.getHeight())/inner.getHeight();yy++) {
                finalTexture.drawPixmap(inner, xx * inner.getWidth() + corner.getWidth(), yy * inner.getHeight() + corner.getHeight());
            }
        }
        for(int i = 0; i != (h - corner.getHeight()) / vertical.getHeight();i++){
            finalTexture.drawPixmap(vertical,0, corner.getHeight() + i * vertical.getHeight());
            finalTexture.drawPixmap(vertical,w - vertical.getWidth(), corner.getHeight() + i * vertical.getHeight());
        }
        for(int i = 0; i != (w - corner.getWidth()) / vertical.getWidth();i++){
            finalTexture.drawPixmap(horizontal,corner.getWidth() + i * horizontal.getWidth(),0 );
            finalTexture.drawPixmap(horizontal,corner.getWidth() + i * horizontal.getWidth(), h - vertical.getHeight() + corner.getHeight()/2);
        }



        finalTexture.drawPixmap(corner,0,0);
        finalTexture.drawPixmap(corner,w - corner.getWidth(),0);
        finalTexture.drawPixmap(corner,0,h - corner.getHeight());
        finalTexture.drawPixmap(corner,w - corner.getWidth(),h - corner.getHeight());
        inner.dispose();
        vertical.dispose();
        horizontal.dispose();
        corner.dispose();
        return new Texture(finalTexture);
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

    int prevMX =0, prevMY;
    public void update(){
        int mx = Gdx.input.getX();
        int my = Gdx.graphics.getHeight() -  Gdx.input.getY();
        for(UIElement e : elements){
            Rectangle box = new Rectangle(e.getX() + x, e.getY() + y,e.getW(),e.getH());
            if(box.contains(mx,my)){
                if(listener != null) {
                    if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
                        listener.onClick(e);
                    if(!box.contains(prevMX,prevMY)){
                        listener.onMouseIn(e);
                    }
                }
            }else{
                if(box.contains(prevMX,prevMY)){
                    listener.onMouseOut(e);
                }
            }
        }

        prevMX = mx;
        prevMY = my;


    }

    public void removeElement(UIElement clicked) {
        elements.remove(clicked);


    }

    public interface ClickListener{
        public void onClick(UIElement clicked);
        void onMouseIn(UIElement in);

        void onMouseOut(UIElement out);
    }
}
