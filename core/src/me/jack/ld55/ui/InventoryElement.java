package me.jack.ld55.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.entity.rune.Rune;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class InventoryElement extends UIElement{

    BitmapFont font = new BitmapFont();
    @Override
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, int px, int py) {
        super.draw(shapeRenderer, batch, px, py);
        HashMap<Rune,Integer>  runes = RuneCollectionElement.instance.fullRunes;
        Set<Rune> owned = runes.keySet().stream().filter(x ->runes.get(x) != 0 && runes.get(x) != null).collect(Collectors.toSet());
        int i = 0;
        for(Rune r : owned){
            if(runes.get(r) == null)
                continue;
            Rune.renderMiniRuneAt(batch,shapeRenderer,getX() + px + i * 20,getY() + py,runes.get(r),r);
            i++;
        }

        font.draw(batch,"Rune Inventory",getX() + px,getY() + py + 40);
    }

    public InventoryElement(int x, int y, int w, int h) {
        super(x, y, w, h);



    }
}
