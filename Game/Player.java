package Game;

import java.util.ArrayList;
import java.util.LinkedList;

class Player {
    private ArrayList<Card> hand;
    private Card lastDrewCard;
    private String name;

    Player(String name) {
        hand = new ArrayList<>();
        this.name = name;
    }

    boolean playCard(Card playCard) {
        //returns true and removes card from hand if card is in hand, else returns false
        return hand.remove(playCard);
    }

    Card getCard(int index) {
        return hand.get(index);
    }

    void drawCard(Card drawCard) {
        hand.add(drawCard);
        lastDrewCard = drawCard;
    }

    Card getLastDrewCard() {
        return lastDrewCard;
    }

    void printCards() {
        int count = 0;
        for (Card c : hand) {
            System.out.println(count + ") " + c.toString());
            count++;
        }
    }

    int getHandLength() {
        return hand.size();
    }

    String getName() {
        return name;
    }

    boolean handContainsCard(Card card){
        return hand.contains(card);
    }
}
