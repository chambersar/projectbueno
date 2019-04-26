package Game;

import java.util.LinkedList;

public class Player {
    LinkedList<Card> hand;
    String name;

    public Player(String name){
        hand = new LinkedList<>();
        this.name = name;
    }

    public boolean playCard(Card playCard){
        //returns true and removes card from hand if card is in hand, else returns false
        return hand.remove(playCard);
    }

    public Card getCard(int index){
        return hand.get(index);
    }

    public void drawCard(Card drawCard){
        hand.addFirst(drawCard);
    }

    public void printCards(){
        int count = 0;
        for(Card c : hand) {
            System.out.println(count + ") " + c.toString());
            count++;
        }
    }

    public int getHandLength(){
        return hand.size();
    }

    public String getName(){
        return name;
    }
}
