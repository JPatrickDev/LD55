package me.jack.ld55.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Animation {


    private static HashMap<String,Texture> animationLookup = new HashMap<>();

    private List<Texture> animation = new ArrayList<>();

    int fps = 12;
    //1.png,2.png,...
    public Animation(String folderPath){
            boolean done = false;
            int i = 0;
            while(!done){
                i++;
                if(Gdx.files.internal(folderPath + "/" + i + ".png").exists()){
                    Texture t= loadImage(folderPath + "/" + i + ".png");
                    animation.add(t);
                }else{
                    break;
                }
            }
    }


    int state = 1;
    long displayedAt = 0;

    public void draw(SpriteBatch batch,int x,int y,int rotation) {
        if(System.currentTimeMillis() - displayedAt >= 1000/fps){
            state++;
            displayedAt = System.currentTimeMillis();
        }
        if(state >= animation.size()){
            state = 0;
        }
        batch.draw(animation.get(state),x,y,16,16,32,32,1,1,rotation,0,0,32,32,false,false);

    }


    public Animation(Texture spriteSheet, int tw, int th, int r, int c){

    }


    private Texture loadImage(String key){
        if(Animation.animationLookup.containsKey(key)){
            return Animation.animationLookup.get(key);
        }
        Texture t = new Texture(key);
        animationLookup.put(key,t);
        return t;
    }
}
