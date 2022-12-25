import java.util.Scanner;

public class Blackjack{
    
    static String[] deck = new String[52];
    static int count = 52; //one deck have 52 cards. 4 suits x 13 ranks
    
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);

        System.out.println("How many rounds do you want to play");
        int round = sc.nextInt();
        double[][] score = new double[2][round];

        for(int game = 0; game < round; game++){ 
            
            System.out.printf("\nGame #%d\n", game + 1);

            String[] playerCard = new String[14];
            String[] dealerCard = new String[14];
            int handSum = 0, houseSum = 0, myAce = 0, dAce = 0, np = 2, nd = 2; 
            //np = number of player's cards, nd = number of dealer's card. By default it is always at least 2
            
            generateDeck();
            ShuffleDeck();  //The cards are shuffle at the beginning of the game
        
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
    
            System.out.printf("Player's cards: %s %s", playerCard[0], playerCard[1]); //The two cards dealt for the player are shown to the player
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
            
            result(handSum, houseSum, score, np, game);
            count = 52;
            
        }

        tallyScore(score, round);

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
            case 'K': val =10; break;
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

    public static void result(int playerHand, int dealerHand, double[][] score, int nc, int g) {
        //Compared the player hand and the dealer hand and print out the result
        if(playerHand == 21 && dealerHand != 21 && nc == 2){
            System.out.println("Blackjack!\n");
            score[0][g] = 2.5;
            score[1][g] = -1.5;
        }

        else if(dealerHand > 21 && playerHand > 21){
            System.out.println("You both bust.");
            score[0][g] = 1;
            score[1][g] = 0;
        }

        else if(dealerHand > 21 && playerHand <= 21){
            System.out.println("You won, the dealer bust.");
            score[0][g] = 2;
            score[1][g] = -1;
        }

        else if(dealerHand <= 21 && playerHand > 21){
            System.out.println("You bust");
            score[0][g] = -1;
            score[1][g] = 1;
        }

        else if(dealerHand == playerHand){
            System.out.println("Game ended with a tie");
            score[0][g] = 1;
            score[1][g] = 0;
        }

        else if(((playerHand > dealerHand) && playerHand <= 21) || playerHand == 21 && dealerHand != 21){
            System.out.println("You won!");
            score[0][g] = 2;
            score[1][g] = -1;
        }

        else if((playerHand < dealerHand) && dealerHand <= 21){
            System.out.println("You lost.");
            score[0][g] = -1;
            score[1][g] = 1;
        }
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

    private static void tallyScore(double[][] score, int r){
        
        int playerScore = 0;
        int dealerScore = 0;

        for(int i = 0; i < r; i++){
            playerScore += score[0][i];
            dealerScore += score[1][i];
        }

        System.out.println("\nPlayer's score: " + playerScore);
        System.out.println("Dealer's score: " + dealerScore);
    }
}
