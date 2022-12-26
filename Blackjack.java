import java.util.Scanner;

public class protoBlkJk{
    
    static String[] deck = new String[52];
    static int count = 52; //one deck have 52 cards. 4 suits x 13 ranks
    
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        double bal = 1600;

        while(bal > 0){  //infinite loop
            
            String[] playerCard = new String[14];
            String[] dealerCard = new String[14];
            int handSum = 0, houseSum = 0, myAce = 0, dAce = 0, np = 2, nd = 2; 
            //np = number of player's cards, nd = number of dealer's card. By default it is always at least 2
            
            generateDeck();
            ShuffleDeck();  //The cards are shuffle at the beginning of the game
            double bet;
            do{
                System.out.printf("\nYour wallet is $%.2f\nEnter bet: ", bal);
                bet = sc.nextDouble();
            }while(bet <= 0 || bet > bal);

            for(int i = 0; i < 2; i++){  
            
                playerCard[i] = giveCard();
                if(isAce(playerCard[i]))
                    myAce++;
                    
                handSum += handCount(playerCard[i].charAt(1));
                
                dealerCard[i] = giveCard();           
                if(isAce(dealerCard[i]))
                    dAce++;
    
                houseSum += handCount(dealerCard[i].charAt(1));
            }
    
            System.out.printf("\nPlayer's cards: %s %s", playerCard[0], playerCard[1]); //The two cards dealt for the player are shown to the player
            System.out.printf("\nDealer's card: %s + ?\n", dealerCard[0]); //Only the first dealer card is shown to the player while the second card is faced down.
            
            //If the total player's card is not 21 (not a blackjack) the player may decide to "hit" or "stand"
    
            while(handSum != 21 && Hit()){
                    
                playerCard[np] = giveCard();
                if(isAce(playerCard[np]))
                    myAce++;
                
                System.out.println("You are dealt " + playerCard[np]);
                handSum += handCount(playerCard[np].charAt(1));
    
                while(handSum > 21 && myAce != 0){
                    handSum = handCount(handSum);
                    myAce--;
                }
    
                np++;
            }
    
            //Dealer finally reveal his hand after the player's round ended
            System.out.printf("\nDealer's cards: %s %s\n", dealerCard[0], dealerCard[1]);
            
            //As per rule, if the dealer's hand is less than 16
            //the dealer is obligated to take another card until its hand reach at least 17
            if(houseSum <= 16)
                System.out.print("Dealer take card(s): ");
    
            while(houseSum <= 16){
                dealerCard[nd] = giveCard();
                if(isAce(dealerCard[nd]))
                    dAce++;
                    
                houseSum += handCount(dealerCard[nd].charAt(1));
                System.out.print(dealerCard[nd] + " ");
                while(houseSum > 21 && dAce != 0){
                    houseSum = handCount(houseSum);
                    dAce--;
                }
    
                nd++;
            }
    
            //Show the total value for Player hand and the Dealer Hands
    
            System.out.printf("\n\nPlayer's hand: ");
            displayDeck(playerCard, np);
            System.out.printf("\nPlayer total = %d\n" ,handSum);
            
            System.out.printf("\nDealer's hand: ");
            displayDeck(dealerCard, nd);
            System.out.printf("\nDealer total = %d\n\n" ,houseSum); 
            
            bal += result(handSum, houseSum, np, bet);
            
            if(bal == 0)
                break;

            System.out.println("Do you want to continue playing? (yes/no)");
            String answer = sc.next();

            if(answer.charAt(0) == 'y' || answer.charAt(0) == 'Y'){
                count = 52;
                continue;
            }

            else
                break;
        }

        if(bal > 1600)
            System.out.printf("\nYou gained $%.2f",  (bal - 1600));

        else if(bal < 1600)
            System.out.printf("\nYou lost $%.2f",  (1600 - bal));

        else
            System.out.println("\nYou dont really gain or lose anything");

        sc.close();
            
    }

    public static void generateDeck(){
        
        char suit[] = {(char) 3,(char) 4,(char) 5,(char) 6};
        String rank[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9","10", "J", "Q", "K"};

        for(int n = 0; n < 52; n++){
            String Suit = suit[n / 13] + "";
            deck[n] = Suit.toString() + rank[n % 13];
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
    
    public static String giveCard() {
        //Deal the card from the top of the stack
        count--;
        return deck[count];
    }
    
    public static int handCount(char cVal){
        int val;
        /*
        Number card (2-10) value are taken literally from the card.
        All court card (or face card which ever you prefer to call them) are counted as 10
        Court card = Jack, Queen and King
        Ace is a wildcard, it can be either counted as 11 or 1
        but by default, Ace is always counted as 11.
        */
        switch (cVal){
            case 'A': val = 11; break;
            case '1':                  //charAt(1) for (Suit)10 is 1
            case 'J':
            case 'Q':
            case 'K': val = 10; break;
            default: val = (int) cVal - '0'; //lookup ASCII if this doesn't make sense to you
                break;
        }
        
        return val;
    }

    public static int handCount(int sum){
        
        return sum - 10;
    }

    public static boolean Hit(){

        int opt;
        Scanner sc = new Scanner(System.in);
        System.out.printf("\nDo you want to hit\n");
        System.out.println("0. Stand    1. Hit");

        do{
            opt = sc.nextInt();

            switch (opt) {
                case 1: return true;
                case 0: return false;
                default:
                    continue;
            }

        }while(opt != 1 || opt != 0);
        
        sc.close();

        return false;
    }

    public static double result(int playerHand, int dealerHand, int nc, double bet) {
        //Compared the player hand and the dealer hand and print out the result
        double Return = 0;
        if(playerHand == 21 && dealerHand != 21 && nc == 2){
            System.out.println("Blackjack!\n");
            Return = 1.5 * bet;
        }

        else if(dealerHand > 21 && playerHand > 21){
            System.out.println("You both bust.");
            Return = 0 * bet;
        }

        else if(dealerHand > 21 && playerHand <= 21){
            System.out.println("You win, the dealer bust.");
            Return = 1 * bet;
        }

        else if(dealerHand <= 21 && playerHand > 21){
            System.out.println("You bust");
            Return = -1 * bet;
        }

        else if(dealerHand == playerHand){
            System.out.println("Game ended with a tie");
            Return =  0 * bet;
        }

        else if(((playerHand > dealerHand) && playerHand <= 21) || playerHand == 21 && dealerHand != 21){
            System.out.println("You win!");
            Return = 1 * bet;
        }

        else if((playerHand < dealerHand) && dealerHand <= 21){
            System.out.println("You lose.");
            Return = -1 * bet;
        }

        System.out.printf("Return: %.2f\n", Return);
        return Return;
    }


    public static boolean isAce( String card) {
        if (card.charAt(1)=='A'){
            return true;
            }
        return false;
    }

    private static void displayDeck(String[] cards, int num){

        for(int i = 0; i < num; i++){
            System.out.printf("%s ", cards[i]);
        }
    }
}
