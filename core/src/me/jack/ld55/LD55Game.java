package me.jack.ld55;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import me.jack.ld55.state.InGameState;
import me.jack.ld55.state.Screen;

import java.util.Random;

public class LD55Game extends ApplicationAdapter {

	public static Screen currentScreen = null;
	@Override
	public void create () {
		currentScreen = new InGameState();
		currentScreen.show();
	}

	@Override
	public void render () {
		//System.out.println(Gdx.graphics.getFramesPerSecond());
		ScreenUtils.clear(0, 0, 0, 1);
		currentScreen.render();
	}
	
	@Override
	public void dispose () {


	}


	public static final Random r = new Random();

	public static int rand(int max) {
		return r.nextInt(max);
	}

	public static float rand() {
		return r.nextFloat();
	}

	public static boolean randBool() {
		return r.nextBoolean();
	}

}
