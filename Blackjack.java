import java.util.Scanner;

public class Blackjack {
    
    static String[] deck = {"HA", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "HJ", "HQ", "HK", 
        "DA", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "DJ", "DQ", "DK", 
        "CA", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "CJ", "CQ", "CK", 
        "SA", "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9", "S10", "SJ", "SQ", "SK"};
    // Due to the limitation of the netbeans compiler we have to represent the suit as a character (this is why VS code better /s)
    // H = Heart, D = Diamonf, C = Clover/Club S = Spade
    static int count = 52; //one deck have 52 cards. 4 suits x 13 ranks
    
    public static void main(String[] args){
        
        String[] playerCard = new String[2];
        String[] dealerCard = new String[2];
        int handSum = 0, houseSum = 0, myAce = 0, dAce = 0;
        Scanner sc = new Scanner(System.in);

        ShuffleDeck();  //The cards are shuffle at the beginning of the game

        //The "dealer" dealt two cards for the player at the beginning of the game
        System.out.print("Player's card: ");
        for(int i = 0; i < 2; i++){
            
            playerCard[i] = giveCard();
            String tempCard = playerCard[i];
            if(tempCard.charAt(1) == 'A')
                myAce++;

            handSum += handCount(tempCard.charAt(1));
        }

        System.out.printf("%s %s", playerCard[0], playerCard[1]); //The two cards dealt are shown to the player

        //The "dealer" are also dealt two cards at the beginning of the game
        System.out.printf("\nDealer's card: ");
        for(int i = 0; i < 2; i++){
            
            dealerCard[i] = giveCard();
            String tempCard = dealerCard[i];
            if(tempCard.charAt(1) == 'A')
                dAce++;

            houseSum += handCount(tempCard.charAt(1));
        }

        System.out.printf("%s + ?\n", dealerCard[0]); //Only the first dealer card is shown to the player as per rule
        
        //If the total player's card is not 21 (not a blackjack) the player may decide to "hit" or "stand"
        if(handSum != 21)
            handSum = playerRound(handSum, myAce);
        
        //Dealer finally reveal his hand after the player's round ended
        System.out.printf("Dealer's card: %s %s\n", dealerCard[0], dealerCard[1]);
        
        //As per rule, if the dealer's hand is less than 16
        //the house is obligated to take another card until its hand reach at least 17
        if(houseSum <= 16){
            houseSum = dealerRound(houseSum, dAce);
        }

        //Show the total value for Player hand and the Dealer Hand 
        System.out.printf("\nPlayer hand = %d\n" ,handSum);
        System.out.printf("Dealer hand = %d\n" ,houseSum);

        //Compare the value and print out the result
        result(handSum, houseSum);

        sc.close();
    }

    public static void ShuffleDeck(){

        //Use bogo sort to shuffle the deck
        for(int j = 0; j < 52; j++){
            int randId = (int) (Math.random() * 52);

            String tempStr = deck[j];
            deck[j] = deck [randId];
            deck[randId] = tempStr;
        }
    }
    
    public static String giveCard() {
        //Deal the card from the top of the stack
        count--;
        return deck[count];
    }
    
    public static int handCount(char cVal){
        int sum = 0;
        /*
        Number card (2-10) value are taken literally from the card.
        All court card (or face card which ever you prefer to call them) are counted as 10
        Court card = Jack, Queen and King
        Ace is a wildcard, it can be either counted as 11 or 1
        but by default, Ace is always counted as 11.
        */
        switch (cVal) {
            case 'A': sum += 11; break;
            case '1':                  //charAt(1) for (Suit)10 is 1
            case 'J':
            case 'Q':
            case 'K': sum +=10; break;
            default: sum += (int) cVal - 48; //lookup ASCII if this doesn't make sense to you
                break;
        }
        
        return sum;
    }

    public static int playerRound (int currSum, int ace){

        int hit; 
        String nCard;

        Scanner sc = new Scanner(System.in);

        //Hit = take another card
        //Stand = you are satisfy with you hand and want to end your round
        do{
            System.out.printf("\nDo you want to hit\n");
            System.out.println("0. Stand    1. Hit");

            hit = sc.nextInt();

            if(hit == 1){
                
                nCard = giveCard();

                if(nCard.charAt(hit) == 'A')
                    ace++;

                currSum += handCount(nCard.charAt(1));

                System.out.println("You are handed " + nCard);
            }
        }while(hit == 1);

        //in case of the player hand go over 21 and the player happened to have Ace card
        //Player's Ace value will be converted to 1.
        while(currSum > 21 && ace != 0){
            currSum-= 10;
            ace--;
        }

        sc.close();

        return currSum;
    }

    public static int dealerRound(int currSum, int ace) {
        
        String nCard;

        Scanner sc = new Scanner(System.in);

        System.out.print("Dealer take card(s): ");

        //As stated above, while dealer's hand is under 16
        //Dealer is obligated to take another card
        while(currSum <= 16 || (currSum > 21 && ace > 0)){
            //take a card again
            
            nCard = giveCard();
            System.out.printf("%s ", nCard);
            if(nCard.charAt(1) == 'A')
                ace++;

            currSum += handCount(nCard.charAt(1));

            //deduct Ace card value (if have) to 1 to prevent the hand going over 21
            if(currSum > 21 && ace != 0){
                    currSum-= 10;
                    ace--;
                }
        }

        sc.close();

        return currSum;
    }

    public static void result(int playerHand, int dealerHand) {
        //Compared the player hand and the dealer hand and print out the result
        if(playerHand == 21 && dealerHand != 21)
            System.out.println("Blackjack");

        else if(dealerHand > 21 && playerHand > 21)
            System.out.println("You both bust.");

        else if(dealerHand > 21 && playerHand <= 21)
            System.out.println("You won, the dealer bust.");

        else if(dealerHand <= 21 && playerHand > 21)
            System.out.println("You bust");

        else if(dealerHand == playerHand)
            System.out.println("Tie...");

        else if((playerHand > dealerHand) && playerHand <= 21)
            System.out.println("You won!");

        else if((playerHand < dealerHand) && dealerHand <= 21)
            System.out.println("You lost.");

    }
}
