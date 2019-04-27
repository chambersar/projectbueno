package Game;

public class GameState {
    public Player curr;//current player
    public Card[] cards;//cards being given to current player
    public boolean skip;//if current player is being skipped

    public GameState(Player curr, Card[] cards, boolean skip){
        this.curr = curr;
        this.cards = cards;
        this.skip = skip;
    }
}
