package me.jack.ld55.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Animation {


    private static HashMap<String, Texture> animationLookup = new HashMap<>();

    private List<Texture> animation = new ArrayList<>();

    private boolean loop = true;
    private boolean isDone = false;

    public int fps = 12;


    public int rotX = -1,rotY = -1;

    public Animation(Texture parent){
        this.animation.add(parent);
    }
    //1.png,2.png,...
    public Animation(String folderPath) {
        boolean done = false;
        int i = 0;
        while (!done) {
            i++;
            if (Gdx.files.internal(folderPath + "/" + i + ".png").exists()) {
                Texture t = loadImage(folderPath + "/" + i + ".png");
                animation.add(t);
            } else {
                break;
            }
        }
        if(animation.isEmpty()){
            animation.add(new Texture(folderPath));
        }
    }


    int state = 1;
    long displayedAt = 0;

    public void draw(SpriteBatch batch, int x, int y, int rotation) {
        if (System.currentTimeMillis() - displayedAt >= 1000 / fps) {
            state++;
            displayedAt = System.currentTimeMillis();
        }
        if (state >= animation.size()) {
            if (!loop) {
                state = animation.size() - 1;
                isDone = true;
            } else {
                state = 0;
            }

        }

        Texture t = animation.get(state);
        if(t == null)
            return;
        if(rotX == -1){
            rotX = t.getWidth() / 2;
            rotY = t.getHeight() /2;
        }
        batch.draw(t, x, y,rotX ,rotY , t.getWidth(), t.getHeight(), 1, 1, rotation, 0, 0, t.getWidth(), t.getHeight(), false, false);

    }



    private Texture loadImage(String key) {
        if (Animation.animationLookup.containsKey(key)) {
            return Animation.animationLookup.get(key);
        }
        Texture t = new Texture(key);
        animationLookup.put(key, t);
        return t;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public boolean isDone() {
        return isDone;
    }

    public int getHeight() {
        return animation.get(0).getHeight();
    }

    public int getWidth(){
        return animation.get(0).getWidth();
    }
}
