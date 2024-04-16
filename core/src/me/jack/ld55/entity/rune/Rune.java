package me.jack.ld55.entity.rune;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.ui.TextAreaElement;

import java.util.HashMap;

public enum Rune {

    RED(Color.RED, Color.ORANGE),BLUE(Color.BLUE,Color.CYAN), GREEN(Color.GREEN,Color.LIME),PURPLE(Color.PURPLE,Color.MAGENTA);

    private final Color color;
    private final Color highlight;

    private Rune(Color color, Color highlight){
        this.color = color;
        this.highlight = highlight;
    }


    static BitmapFont font = new BitmapFont();

    static HashMap<String, Texture> runeCache = new HashMap<>();
    public static void renderMiniRuneAt(SpriteBatch batch, ShapeRenderer renderer, int x,int y, int amount, Rune rune){
        Texture toDraw = null;
        if(runeCache.containsKey( "runes/minirune.png" + rune.name()) ){
            toDraw = runeCache.get("runes/minirune.png" + rune.name());
        }else {
            Pixmap pixmap = new Pixmap(Gdx.files.internal("runes/minirune.png"));
            for (int xx = 0; xx != pixmap.getWidth(); xx++) {
                for (int yy = 0; yy != pixmap.getHeight(); yy++) {
                    if (pixmap.getPixel(xx, yy) == 0xFF00DCFF) {
                        //  System.out.println("Replacing colour");
                        pixmap.drawPixel(xx, yy, Color.rgba8888(rune.color));
                    } else if (pixmap.getPixel(xx, yy) == 0x1500FFFF) {
                        //       System.out.println("Replacing highlight");
                        pixmap.drawPixel(xx, yy, Color.rgba8888(rune.highlight));
                    }

                }
            }
            toDraw = new Texture(pixmap);
            runeCache.put( "runes/minirune.png" + rune.name(),toDraw);
        }
        batch.draw(toDraw,x,y);
        if(amount > 0)
        font.draw(batch,"x" + amount,x ,y - 5);
    }

    public static void renderFullRuneAt(SpriteBatch batch, ShapeRenderer renderer, int x,int y, int amount, Rune rune,int pieces){
        Texture toDraw = null;
        if(runeCache.containsKey( "runes/genericrune_" + pieces + ".png"+ rune.name()) ){
            toDraw = runeCache.get("runes/genericrune_" + pieces + ".png" + rune.name());
        }else {
            Pixmap pixmap = new Pixmap(Gdx.files.internal("runes/genericrune_" + pieces + ".png"));
            for (int xx = 0; xx != pixmap.getWidth(); xx++) {
                for (int yy = 0; yy != pixmap.getHeight(); yy++) {
                    if (pixmap.getPixel(xx, yy) == 0xFF00DCFF) {
               //         System.out.println("Replacing colour");
                        pixmap.drawPixel(xx, yy, Color.rgba8888(rune.color));
                    } else if (pixmap.getPixel(xx, yy) == 0x1500FFFF) {
                //        System.out.println("Replacing highlight");
                        pixmap.drawPixel(xx, yy, Color.rgba8888(rune.highlight));
                    } else if (pixmap.getPixel(xx, yy) == 0x404040FF) {
                 //       System.out.println("Replacing highlight");
                        Color black = new Color(0, 0, 0, 1); // Black color
                        pixmap.drawPixel(xx, yy, Color.rgba8888(new Color(rune.highlight).lerp(black, 0.3f)));
                    }


                }
            }
            toDraw  = new Texture(pixmap);
            runeCache.put( "runes/genericrune_" + pieces + ".png"+ rune.name(),toDraw);
        }
        batch.draw(toDraw,x,y);
      //  if(amount > 1){
            font.setColor(Color.WHITE);
           font.draw(batch, "" + amount,x + toDraw.getWidth()/2 - 4,y +toDraw.getHeight()/2 +7);
       // }
    }

    public static void renderRuneShardAt(SpriteBatch batch, ShapeRenderer renderer, int x,int y, Rune rune){

        Texture toDraw;
        if(runeCache.containsKey( "runes/genericruneshard_" + 1 + ".png" + rune.name()) ){
            toDraw = runeCache.get("runes/genericruneshard_" + 1 + ".png" + rune.name());
        }else {
            Pixmap pixmap = new Pixmap(Gdx.files.internal("runes/genericruneshard_" + 1 + ".png"));
            for (int xx = 0; xx != pixmap.getWidth(); xx++) {
                for (int yy = 0; yy != pixmap.getHeight(); yy++) {
                    if (pixmap.getPixel(xx, yy) == 0xFF00DCFF) {
                 //       System.out.println("Replacing colour");
                        pixmap.drawPixel(xx, yy, Color.rgba8888(rune.color));
                    } else if (pixmap.getPixel(xx, yy) == 0x1500FFFF) {
                      //  System.out.println("Replacing highlight");
                        pixmap.drawPixel(xx, yy, Color.rgba8888(rune.highlight));
                    } else if (pixmap.getPixel(xx, yy) == 0x404040FF) {
                      //  System.out.println("Replacing highlight");
                        Color black = new Color(0, 0, 0, 1); // Black color
                        pixmap.drawPixel(xx, yy, Color.rgba8888(new Color(rune.highlight).lerp(black, 0.3f)));
                    }


                }
            }
            toDraw = new Texture(pixmap);
            runeCache.put("runes/genericruneshard_" + 1 + ".png" + rune.name(),toDraw);
        }

        batch.draw(toDraw,x,y);
    }
}
