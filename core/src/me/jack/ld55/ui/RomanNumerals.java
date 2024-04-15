package me.jack.ld55.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

public class RomanNumerals {

    public static HashMap<Integer,Texture> nums = new HashMap<Integer, Texture>();
    public static void drawNumeral(int x, int y, SpriteBatch batch,int num){
        if(num > 5 || num == 0)
            return;
        if(!nums.containsKey(new Integer(num))) {
            nums.put(num, new Texture("numerals/" + num + ".png"));
        }
        batch.draw(nums.get(num),x,y);
    }
}
