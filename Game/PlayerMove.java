package Game;

import java.lang.management.PlatformLoggingMXBean;

public class PlayerMove {
    public Card card;//card player wants to play, can be null which would signal player wants to draw a card
    public Suit wildSuit;//if player is playing a wild card they need to include which suit they are changing too, else this will be null
    public boolean skip;

    public PlayerMove(Card card){
        this.card = card;
        wildSuit = null;
    }

    public PlayerMove(Card card, Suit wildSuit){
        this.card = card;
        this.wildSuit = wildSuit;
    }

    public PlayerMove(boolean skip){
        this.skip = skip;
    }
}
