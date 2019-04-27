package Game;

public class Card {
    private CardType value;
    private Suit suit;

    Card(CardType value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    Suit getSuit(){
        return suit;
    }

    CardType getCardVal(){
        return value;
    }

    public void setSuit(Suit s){
        suit = s;
    }

    @Override
    public String toString(){
        return suit.toString() + " " + value.toString();
    }
}
