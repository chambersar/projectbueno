package Game;

import shared.CyclicLinkedList;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Deck deck;
    private int startingHandSize = 4;
    private boolean drawTwo = false;
    private boolean drawFour = false;
    private boolean skip = false;
    private Player curr;

    CyclicLinkedList<Player> players;

    public Game(CyclicLinkedList<Player> players, int multiple) {
        this.players = players;
        deck = new Deck(multiple);

        Player first = curr = players.current();
        for (int i = 0; i < startingHandSize; i++) {
            first.drawCard(deck.drawCard());
            while (players.next() != first)
                players.current().drawCard(deck.drawCard());
        }
        deck.playFirstCard();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        //deal cards


        while (true) {
            Player p = players.next();
            //skip will always be true if drawTwo or drawFour is true, so only checking skip for now
            if (!skip) {
                System.out.println("\n" + p.getName() + "'s turn");
                System.out.println("Top card is " + deck.getTopCard().toString() + "\n");
                System.out.println("Your cards are:");
                p.printCards();

                boolean moveValid = false;
                while (!moveValid) {
                    System.out.println("Enter the index of the card you wish to play or type draw");
                    String move = sc.nextLine();

                    if (move.equalsIgnoreCase("draw")) {
                        Card drawCard = deck.drawCard();
                        p.drawCard(drawCard);
                        System.out.println(drawCard.toString() + " drawn");
                        System.out.println("Type yes to play card draw");
                        move = sc.nextLine();
                        if (move.equalsIgnoreCase("yes")) {
                            if (Game.checkMove(deck.getTopCard(), drawCard)) {
                                p.playCard(drawCard);
                                deck.playCard(drawCard);
                            }
                        }
                        moveValid = true;
                    } else if (isNumeric(move)) {
                        int cardNum = Integer.parseInt(move);
                        Card playCard = p.getCard(cardNum);

                        if (playCard.getSuit() == Suit.Wild) {
                            p.playCard(playCard);//remove from player hand
                            deck.playCard(playCard);//add to deck
                            //create temporary card based on desired suit and add to deck using special deck.addWild() method
                            //the deck will handle removing the blank card when the next card is played.
                            Card blankWild = null;
                            while (blankWild == null) {
                                System.out.println("What color would you like to change to?");
                                String newColor = sc.nextLine();
                                switch (newColor) {
                                    case "green":
                                        blankWild = new Card(CardType.Wild, Suit.Green);
                                        break;
                                    case "red":
                                        blankWild = new Card(CardType.Wild, Suit.Red);
                                        break;
                                    case "blue":
                                        blankWild = new Card(CardType.Wild, Suit.Blue);
                                        break;
                                    case "yellow":
                                        blankWild = new Card(CardType.Wild, Suit.Yellow);
                                        break;
                                    default:
                                        System.out.println("Invalid suit, pick green, red, blue, or yellow");
                                        break;
                                }
                            }
                            deck.addWild(blankWild);

                            if (playCard.getCardVal() == CardType.WildDraw) {
                                skip = true;
                                drawFour = true;
                            }
                            moveValid = true;

                        } else if (Game.checkMove(deck.getTopCard(), playCard)) {
                            p.playCard(playCard);
                            deck.playCard(playCard);
                            switch (playCard.getCardVal()) {
                                case Reverse:
                                    players.flipDirection();
                                    break;
                                case DrawTwo:
                                    drawTwo = true;
                                    skip = true;
                                    break;
                                case Skip:
                                    skip = true;
                                    break;
                                default:
                                    break;
                            }
                            moveValid = true;
                        } else {
                            System.out.println("Move not valid, try again.");
                        }

                    } else if (move.equalsIgnoreCase("end")) {
                        moveValid = true;
                    } else {
                        System.out.println("Command not valid, try again.");
                    }

                    if (p.getHandLength() == 0) {
                        System.out.println(p.getName() + " won");
                        return;
                    }
                }
                //handle skip and stuff
            } else {
                System.out.println("\n" + p.getName() + "'s turn was skipped");
                if (drawTwo) {
                    Card temp = deck.drawCard();
                    System.out.println(p.getName() + " drew " + temp.toString());
                    p.drawCard(temp);

                    temp = deck.drawCard();
                    System.out.println(p.getName() + " drew " + temp.toString());
                    p.drawCard(temp);
                    drawTwo = false;
                } else if (drawFour) {
                    Card temp = deck.drawCard();
                    System.out.println(p.getName() + " drew " + temp.toString());
                    p.drawCard(temp);

                    temp = deck.drawCard();
                    System.out.println(p.getName() + " drew " + temp.toString());
                    p.drawCard(temp);

                    temp = deck.drawCard();
                    System.out.println(p.getName() + " drew " + temp.toString());
                    p.drawCard(temp);

                    temp = deck.drawCard();
                    System.out.println(p.getName() + " drew " + temp.toString());
                    p.drawCard(temp);
                    drawFour = false;
                }
                skip = false;
            }
        }
    }

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of players");
        int playersNum = sc.nextInt();
        sc.nextLine();
        Player p = new Player("tempRemove");
        CyclicLinkedList<Player> tempPlayers = new CyclicLinkedList<Player>(p);
        for (int i = 1; i <= playersNum; i++) {
            System.out.println("Enter player " + i + "'s name");
            tempPlayers.add(new Player(sc.nextLine()));
        }
        tempPlayers.remove(p);
        Game game = new Game(tempPlayers, 1);
        game.run();
    }

    //increment to next players turn
    //figure out if they should be skipped/what cards they will recieve
    public GameState updateGameState(){
        curr = players.next();
        Card[] cards = null;
        boolean s = skip;
        if(skip) {
            if (drawTwo) {
                cards = new Card[2];
                cards[0] = deck.drawCard();
                cards[1] = deck.drawCard();
                curr.drawCard(cards[0]);
                curr.drawCard(cards[1]);
                drawTwo = false;
            } else if (drawFour) {
                cards = new Card[4];
                cards[0] = deck.drawCard();
                cards[1] = deck.drawCard();
                cards[2] = deck.drawCard();
                cards[3] = deck.drawCard();
                curr.drawCard(cards[0]);
                curr.drawCard(cards[1]);
                curr.drawCard(cards[2]);
                curr.drawCard(cards[3]);
                drawFour = false;
            }
            skip = false;
        }
        return new GameState(curr, cards, s);
    }

    //if player move card is null, then draw card
    public GameReply playCard(PlayerMove playerMove) {
        if (curr.handContainsCard(playerMove.card)){
            if (playerMove.card == null) {
                //case for draw card
                Card temp = deck.drawCard();
                curr.drawCard(temp);
                return new GameReply(true, temp);
            } else if (Game.checkMove(deck.getTopCard(), playerMove.card)) {
                //case for successfully played card
                resolveMove(playerMove);
                return new GameReply(true);
            }
        }
        //case for card not in hand or not valid move
        return new GameReply(false);
    }

    public GameReply playDrawCard(PlayerMove playerMove){
        if(playerMove.card.equals(curr.getLastDrewCard())){
            if(Game.checkMove(deck.getTopCard(), playerMove.card)){
                resolveMove(playerMove);
                return new GameReply(true);
            }
        }
        return new GameReply(false);
    }

    private void resolveWild(Suit wildSuit){
        Card blankWild = new Card(CardType.Wild, wildSuit);
        deck.addWild(blankWild);
    }

    private void resolveMove(PlayerMove playerMove){
        deck.playCard(playerMove.card);
        curr.playCard(playerMove.card);
        //handle action cards
        switch(playerMove.card.getCardVal()){
            case Wild:
                resolveWild(playerMove.wildSuit);
                break;
            case WildDraw:
                resolveWild(playerMove.wildSuit);
                drawFour = true;
                break;
            case Reverse:
                players.flipDirection();
                break;
            case DrawTwo:
                drawTwo = true;
                skip = true;
                break;
            case Skip:
                skip = true;
                break;
            default:
                break;
        }
    }

    public static boolean checkMove(Card topCard, Card newCard){
        //if wild card then move is always valid
        //if the new cards suit or value matches the top cards then move is valid
        //else move is false
        if(newCard.getCardVal() == CardType.Wild || newCard.getCardVal() == CardType.WildDraw)
            return true;
        else if(newCard.getCardVal() == topCard.getCardVal() || newCard.getSuit() == topCard.getSuit())
            return true;
        else
            return false;
    }
}
