package me.jack.ld55.ui;

import com.badlogic.gdx.graphics.Texture;

public class SidebarContainer extends UIContainer{
    public SidebarContainer(int x, int y, int w, int h) {
        super(x, y, w, h);
        this.background = new Texture("gui/sidebar.png");
    }
}
