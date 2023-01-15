import java.util.*;
import java.io.*;

public class Blackjack{
    
    static String[] deck = new String[52];
    static int count = 52; //one deck have 52 cards. 4 suits x 13 ranks

    public static void main(String[] args){
        
        try (Scanner sc = new Scanner(System.in)) {
            
            int round = 0;
            while(round <= 0) {
                try {
                    System.out.print("How many rounds do you want to play?: ");
                    round = sc.nextInt();
                    
                    if(round < 1)
                        throw new IllegalArgumentException("That is not possible");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }catch (InputMismatchException e) {
                    System.out.println("Invalid input, please enter a number.");
                    sc.nextLine();
                }
            }
            
            double[] score = new double[round]; 
            
            readDeck(); //load the deck from the deck.txt file
            
            for(int game = 0; game < round; game++){
                
                System.out.printf("\nGame #%d\n", game+1); sleep(0.7);
                
                String[][] cards = new String[2][15];
                int p = 0, d = 1; // this is just a row index for the 2d array above since it is easier to read
                int handSum = 0, houseSum = 0, myAce = 0, dAce = 0, np = 2, nd = 2;
                //np = number of player's cards, nd = number of dealer's card. By default it is always at least 2

                ShuffleDeck();  //The cards are shuffle at the beginning of the game

                //The "dealer" dealt two cards for the player and the dealer at the beginning of the game
                for(int i = 0; i < 2; i++){  

                    cards[p][i] = giveCard();
                    if(isAce(cards[p][i]))
                        myAce++;

                    handSum += handCount(cards[p][i].charAt(1));

                    cards[d][i] = giveCard();           
                    if(isAce(cards[d][i]))
                        dAce++;

                    houseSum += handCount(cards[d][i].charAt(1));
                }
                sleep(1);

                System.out.printf("Player's cards: %s %s", cards[p][0], cards[p][1]); //The two cards dealt for the player are shown to the player
                System.out.printf("\nDealer's card: %s + ?\n", cards[d][0]); //Only the first dealer card is shown to the player while the second card is faced down.
                sleep(1.5);

                //If the total player's card is not 21 (not a blackjack) the player may decide to "hit" or "stand"
                while(handSum != 21 && Hit()){

                    cards[p][np] = giveCard();
                    if(isAce(cards[p][np]))
                        myAce++;

                    System.out.println("You are dealt " + cards[p][np]);
                    handSum += handCount(cards[p][np].charAt(1));

                    while(handSum > 21 && myAce != 0){
                        handSum = handCount(handSum);
                        myAce--;
                    }

                    np++; 
                    sleep(1);
                }

                //Dealer finally reveal his hand after the player's round ended
                System.out.printf("\nDealer's cards: %s %s\n", cards[d][0], cards[d][1]);

                //As per rule, if the dealer's hand is less than 16
                //the dealer is obligated to take another card until its hand reach at least 17
                if (dAce==2) //Check the extreme case where the dealer have been dealt two aces at the beginning of the game
                       houseSum = handCount(houseSum);
                
                if(houseSum <= 16)
                    System.out.print("Dealer take card(s): ");
                                 
                while(houseSum <= 16){
                    cards[d][nd] = giveCard();
                    if(isAce(cards[d][nd]))
                        dAce++;

                    houseSum += handCount(cards[d][nd].charAt(1));
                    System.out.print(cards[d][nd] + " ");
                    while(houseSum > 21 && dAce != 0){
                        houseSum = handCount(houseSum);
                        dAce--;
                    }

                    nd++; 
                    sleep(0.6);
                }

                sleep(1.4);
                //Show the total value for Player hand and the Dealer Hands
                System.out.print("\n\nPlayer's hand: ");
                displayHand(cards[p], np); sleep(1.4);
                System.out.printf("\nPlayer total = %d\n" ,handSum); sleep(0.8);

                System.out.print("\nDealer's hand: ");
                displayHand(cards[d], nd); sleep(1.4);
                System.out.printf("\nDealer total = %d\n\n" ,houseSum); sleep(0.8);
                //Display results and tally the score of the player
                determineWinner(handSum, houseSum, np, score, game); 

                count = 52;
                System.out.println("------------------------------");
            }

            System.out.println("\nGame Played: " + round);
            double total = tallyScore(score);
            System.out.println("Total Score: " + total); 
            showStats(score);
            
            sc.close();
        }
    }

    public static void readDeck(){

        try (BufferedReader reader = new BufferedReader(new FileReader("deck.txt"))) {
        String card;

        int i = 0;
            while ((card = reader.readLine()) != null) {
                deck[i] = card;
                i++;
            }
        }catch (IOException e){
            System.out.println("Error reading file: " + e.getMessage());
            }
    }


    public static void ShuffleDeck(){

        //Use monkey sort to shuffle the deck
        for(int j = 0; j < 52; j++){
            int randId = (int) (Math.random() * 52);

            String tempStr = deck[j];
            deck[j] = deck [randId];
            deck[randId] = tempStr;
        }
    }
    
    private static String giveCard() {
        count--;
        return deck[count];
    }

    public static void sleep(double s) {
        long ms = (long) s * 1000;
        try{
            Thread.sleep(ms);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
    }
    
    public static int handCount(char cVal){
        int val;

        switch (cVal){
            case 'A': val = 11; break; //Ace can be counted as 11 or 1 but by default, Ace is always counted as 11.
            case '1':                  //charAt(1) for (Suit)10 is 1
            case 'J':
            case 'Q': //All court card (or face card which ever you prefer to call them) are counted as 10
            case 'K': val = 10; break; //Court card = Jack, Queen and King
            default: val = (int) cVal - '0'; //lookup ASCII if this doesn't make sense to you
                break;  //Number card (2-10) value are taken literally from the card.
        }

        return val;
    }

    public static int handCount(int sum){
        //recount the cards sum by subtracting changing ace value from 11 to 1
        return sum - 10;
    }

    public static boolean Hit(){// prompt the user if they want to hit or stand during the player round

        String opt;
        Scanner sc = new Scanner(System.in);

        do{
            System.out.println("\nDo you want to hit");
            System.out.println("> Stand    > Hit");
            opt = sc.next();
            
            switch (opt.charAt(0)) {
                case 'h':
                case 'H': 
                    return true;
                case 'S':
                case 's': 
                    return false;
                default: System.out.println("Invalid option!");
                    continue;
            }

        }while(opt != "hit" || opt != "stand");
        
        sc.close();

        return false;
    }

    private static void displayHand(String[] cards, int num){ //Display their deck once the game end
        //num = number of card
        for(int i = 0; i < num; i++){
            System.out.printf("%s ", cards[i]); 
        }
    }
    
    public static void determineWinner(int playerHand, int dealerHand, int nc, double[] score, int i){
        //Compared the player hand and the dealer hand and print out the result
        if(playerHand == 21 && dealerHand != 21 && nc == 2){
            System.out.println("Blackjack!\n");
            score[i] = 1.5;
        }

        else if(dealerHand > 21 && playerHand > 21){
            System.out.println("You both bust.");
            score[i] =  0;
        }

        else if(dealerHand > 21 && playerHand <= 21){
            System.out.println("You win, the dealer bust.");
            score[i] =  1;
        }

        else if(dealerHand <= 21 && playerHand > 21){
            System.out.println("You bust");
            score[i] =  -1;
        }

        else if(dealerHand == playerHand){
            System.out.println("Game ended with a tie");
            score[i] =  0;
        }

        else if(((playerHand > dealerHand) && playerHand <= 21) || playerHand == 21 && dealerHand != 21){
            System.out.println("You win!");
            score[i] =  1;
        }

        else if((playerHand < dealerHand) && dealerHand <= 21){
            System.out.println("You lose.");
            score[i] =  -1;
        }

        sleep(0.42);
        return;
    }

    public static double tallyScore(double[] score){
        double sum = 0;
        for(int i = 0; i < score.length; i++){
            sum += score[i];
        }            
        return sum;  
    }
    
    public static void showStats(double[] score){ 

        int win = 0, blackjack = 0, tie = 0, lose = 0;

        for (int i = 0; i < score.length; i++) {
            if (score[i] == 1.5) {
                blackjack++;
                win++;
            } else if (score[i] == -1) {
                lose++;
            } else if (score[i] == 1) {
                win++;
            } else if (score[i] == 0){
                tie++;
            }
        }
        
        System.out.println("\nBlkj\tWin\tDraw\tLoss");
        System.out.printf("%d\t%d\t%d\t%d\n", blackjack, win, tie, lose);
    }

    public static boolean isAce( String card){ //Check if the player card is ace or not
        if (card.charAt(1) == 'A'){
            return true;
            }
        return false;
    }
}
