package me.jack.ld55.ui;

import com.badlogic.gdx.graphics.Texture;

public class SidebarContainer extends UIContainer{

    private CardCarousel cardCarousel;
    public SidebarContainer(int x, int y, int w, int h) {
        super(x, y, w, h);
        this.background = new Texture("gui/sidebar.png");
        cardCarousel = new CardCarousel(0,320,175,400);
        this.addElement(cardCarousel);
    }

    public void addCard(UIElement clicked) {
        cardCarousel.addCard((CardElement) clicked);
    }

    public void removeCard(UIElement clicked) {
        cardCarousel.removeCard((CardElement) clicked);
    }

    public void setCardSelectionListener(UIContainer.ClickListener listener){
        cardCarousel.setCardClickListener(listener);
    }

    public boolean hasCardsInHand() {
        return !cardCarousel.elements.stream().noneMatch(x-> x instanceof CardElement);
    }
}
