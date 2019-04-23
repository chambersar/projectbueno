package Game;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    Deck deck;
    int startingHandSize = 4;

    ArrayList<Player> players;
    public Game(ArrayList<Player> players, int multiple){
        this.players = players;
        deck = new Deck(multiple);
    }

    public void run(){
        Scanner sc = new Scanner(System.in);
        //deal cards
        for(int i = 0; i < startingHandSize; i++){
            for(Player p : players)
                p.drawCard(deck.drawCard());
        }

//        for(Player p : players){
//            System.out.println("\nPlayer " + p.name + " cards");
//            p.printCards();
//        }

        while(true){
            for(Player p : players) {
                System.out.println("\n" + p.name + "'s turn");
                System.out.println("Top card is " + deck.getTopCard().toString() + "\n");
                System.out.println("Your cards are:");
                p.printCards();
                System.out.println("Enter the index of the card you wish to play or type draw");
                String move = sc.nextLine();

                if(move.equalsIgnoreCase("draw")){
                    p.drawCard(deck.drawCard());
                }
                else if(isNumberic(move)){
                    int cardNum = Integer.parseInt(move);
                    Card playCard = p.getCard(cardNum);
                    if(playCard.getSuit() == Suit.Wild){
                        p.playCard(playCard);
                        System.out.println("What color would you like to change to?");
                        String newColor = sc.nextLine();
                        switch (newColor){
                            case "green":
                                playCard.setSuit(Suit.Green);
                                break;
                            case "red":
                                playCard.setSuit(Suit.Red);
                                break;
                            case "blue":
                                playCard.setSuit(Suit.Blue);
                                break;
                            case "yellow":
                                playCard.setSuit(Suit.Yellow);
                                break;
                            default:
                                System.out.println("Invalid suit, setting to green");
                                playCard.setSuit(Suit.Green);
                                break;
                        }
                        deck.playCard(playCard);
                    }
                    else if(Card.checkMove(deck.getTopCard(), playCard)){
                        p.playCard(playCard);
                        deck.playCard(playCard);
                    }
                }
                else{
                    System.out.println("I don't know what you mean, so it looks like you are drawing a card");
                    p.drawCard(deck.drawCard());
                }

                if(p.getHandLength() == 0){
                    System.out.println(p.getName() + " won");
                    return;
                }
            }
        }
    }

    public boolean isNumberic(String str){
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of players");
        int playersNum = sc.nextInt();
        sc.nextLine();

        ArrayList<Player> tempPlayers = new ArrayList<Player>();
        for(int i = 1; i <= playersNum; i++){
            System.out.println("Enter player " + i + "'s name");
            tempPlayers.add(new Player(sc.nextLine()));
        }
        Game game = new Game(tempPlayers, 1);
        game.run();
    }
}
