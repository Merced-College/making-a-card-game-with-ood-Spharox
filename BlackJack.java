//Pablo Mendoza
//I was unnable to communicate with my group, so I finished this alone. 

import java.util.Random;
import java.util.Scanner;


public class BlackJack {
    //added card class, attributes, constructor, value mutator, get methods, and toString method
  static class Card { 
    private int value;
    private String suit;
    private String rank;

    public Card(int value, String suit, String rank){
        this.value = value;
        this.suit = suit; 
        this.rank = rank;
    }

    public int getValue(){
        return value;
    }

    public String getSuit(){
        return suit;
    }

    public String getRank(){
        return rank;
    }

    public String toString() {
        return rank + " of " + suit;
    }
  }

  private static Card[] cards = new Card[52];
  private static int currentCardIndex = 0;
  private static int suitIndex = 0;

  public static void main(String[] args) {
    boolean turn = true;
    Scanner scanner = new Scanner(System.in);
    while(turn) {
      initializeDeck();
      // moved String playerDecision = "" ; to continueOrDont()
      shuffleDeck();
      int playerTotal = 0;
      int dealerTotal = 0;
      playerTotal = dealInitialPlayerCards();

      dealerTotal = dealInitialDealerCards();

      //added another function here, in the event that the player pulls 21, its an automatic blackjack.
      if(playerTotal == 21){
        if(dealerTotal == 21){
            System.out.println("You and dealer both drew blackjack!"); //if both dealer and player have 21, its a tie
        }else{
            System.out.println("You drew a blackjack!");
        }
        turn = continueOrDont(playerTotal, dealerTotal, scanner); 
        continue; 
        // if player draws blackjack, game is over. no further input required from player
      }

      playerTotal = playerTurn(scanner, playerTotal);
      if (playerTotal > 21) {
        System.out.println("You busted! Dealer wins.");
        return;
      }

      dealerTotal = dealerTurn(dealerTotal);
      turn = continueOrDont(playerTotal, dealerTotal, scanner); 
    }
    System.out.println("Thanks for playing!");
  }

  // made this its own function for more functional code
  // essentially shifted much of the functionality of the main code into its own function so that I can make it more dynamic
  private static boolean continueOrDont(int playerTotal, int dealerTotal, Scanner scanner) {
    String playerDecision = "" ;

    determineWinner(playerTotal, dealerTotal);
    System.out.println("Would you like to play another hand?");
    playerDecision = scanner.nextLine().toLowerCase();

    while(!(playerDecision.equals("no") || (playerDecision.equals("yes")) )){
        System.out.println("Invalid action. Please type 'yes' or 'no'."); //changed line here so that it correctly asks for "yes" or "no"
        playerDecision = scanner.nextLine().toLowerCase();
      }
      if (playerDecision.equals("no")){
          return false;
      }
    return true;
  }

  private static void initializeDeck() {
    String[] SUITS = { "Hearts", "Diamonds", "Clubs","Spades" };
    String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9","10", "Jack", "Queen", "King","Ace" };
    int suitsIndex = 0, rankIndex = 0;
    for (int i = 0; i < cards.length; i++) {
      int val = 10;
      if(rankIndex < 9)
        val = Integer.parseInt(RANKS[rankIndex]);
      
      cards[i] = new Card( val, SUITS[suitIndex], RANKS[rankIndex]);
      suitIndex++;
      if (suitIndex == 4) {
        suitIndex = 0;
        rankIndex++;
      }
    }
  }
  private static void shuffleDeck() {
    Random random = new Random();
    for (int i = 0; i < cards.length; i++) {
      int index = random.nextInt(cards.length);
      Card temp = cards[i];
      cards[i] = cards[index];
      cards[index] = temp;
    }
  }
  private static int dealInitialPlayerCards() {
    Card card1 = dealCard();
    Card card2 = dealCard();

    System.out.println("Your cards: " + card1.getRank() + " of " + card1.getSuit() + " and " + card2.getRank() + " of " + card2.getSuit());
        
        
    return card1.getValue() + card2.getValue();
  }
  private static int dealInitialDealerCards() {
    Card card1 = dealCard();
    System.out.println("Dealer's card: " + card1);
    return card1.getValue();
  }
  private static int playerTurn(Scanner scanner, int playerTotal) {
    while (true) {
      System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
      String action = scanner.nextLine().toLowerCase();
      if (action.equals("hit")) {
        Card newCard = dealCard();
        playerTotal += newCard.getValue();
        System.out.println("You drew a " + newCard );
        if (playerTotal > 21) {
          System.out.println("you busted Dealer wins!" );
          playerTotal = 0;
          return playerTotal;
        }
      } else if (action.equals("stand")) {
        break;
      } else {
        System.out.println("Invalid action. Please type 'hit' or 'stand'.");
      }
    }
    return playerTotal;
  }
  // algorithm for dealer's turn
  private static int dealerTurn(int dealerTotal) {
    while (dealerTotal < 17) {
      Card newCard = dealCard();
      dealerTotal += newCard.getValue();
    }
    System.out.println("Dealer's total is " + dealerTotal);
    return dealerTotal;
  }
  // algorithm to determine the winner
  private static void determineWinner(int playerTotal, int dealerTotal) {
    if (dealerTotal > 21 || playerTotal > dealerTotal) {
      System.out.println("You win!");
    } else if (dealerTotal == playerTotal) {
      System.out.println("It's a tie!");
    } else {
      System.out.println("Dealer wins!");
      playerTotal = 0;
    }
  }
  // algroithm to deal a card
  //private static int dealCard() {
  private static Card dealCard() {
    //return DECK[currentCardIndex++] % 13;
    return cards[currentCardIndex++];
  }
  // algorithm to determine card value
  private static int cardValue(int card) {
    return card < 9 ? card + 2 : 10;
  }
}
