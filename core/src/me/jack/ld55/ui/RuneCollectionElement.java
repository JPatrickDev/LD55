package me.jack.ld55.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.entity.rune.Rune;

import java.util.HashMap;

public class RuneCollectionElement extends UIElement{

    public HashMap<Rune,Integer> fullRunes = new HashMap<Rune, Integer>();
    public HashMap<Rune,Integer> runePieces = new HashMap<Rune, Integer>();

    public static RuneCollectionElement instance;
    public RuneCollectionElement(int x, int y, int w, int h) {
        super(x, y, w, h);
        fullRunes.put(Rune.RED,3);
        fullRunes.put(Rune.BLUE,5);
        runePieces.put(Rune.RED,0);
        runePieces.put(Rune.BLUE,0);

        fullRunes.put(Rune.GREEN,0);
        runePieces.put(Rune.GREEN,0);

        fullRunes.put(Rune.PURPLE,0);
        runePieces.put(Rune.PURPLE,0);

        instance = this;
    }

    public  void addRune(Rune type,int shardCount){
        if(shardCount == 4){
            fullRunes.put(type,fullRunes.get(type) + 1);
        }else{
            int currentCount = runePieces.get(type);
            currentCount += shardCount;
            int full = currentCount / 4;
            int remain = currentCount % 4;
            fullRunes.put(type,fullRunes.get(type) + full);
            runePieces.put(type,remain);
        }
    }

    public static boolean canAfford(CardElement cards){
        HashMap<Rune,Integer> price = cards.cost;
        boolean canAfford = true;
        for(Rune r : instance.fullRunes.keySet()){
            if(!price.containsKey(r))
                continue;
            int v = instance.fullRunes.get(r);
            int c = price.get(r);
            if(v < c)
                return false;
        }
        return canAfford;
    }
    @Override
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, int px, int py) {
        int xof = 0;
        int i = 0;
        int yof =0;
        for(Rune r : fullRunes.keySet()){
            int amount = fullRunes.get(r);
            int pieces = runePieces.get(r);
            Rune.renderFullRuneAt(batch,shapeRenderer,px + getX() + xof,py+getY() + yof,amount,r,pieces);
            xof+=82;

            i++;
            if(i % 2 == 0){
                yof-=82;
                xof = 0;
            }
        }
    }


    public static boolean buy(CardElement cards){
        if(!canAfford(cards))
            return false;
        for(Rune r : cards.cost.keySet()){
            int v = cards.cost.get(r);
            instance.fullRunes.put(r,instance.fullRunes.get(r) - v);
        }
        return true;
    }
}
