import java.util.Scanner;

public class BlackjackVSC{
    
    static String[] deck = new String[52];
    static int count = 52; //one deck have 52 cards. 4 suits x 13 ranks
    
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        int round;
        do{
            System.out.print("How many rounds do you want to play?: ");
            round = sc.nextInt();

            if(round <= 0)
                System.out.println("That is not possible");

        }while(round <= 0);

        double[] score = new double[round];
        
        for(int game = 0; game < round; game++){
            
            System.out.printf("\nGame #%d\n", game + 1);
            
            String[][] cards = new String[2][15];
            int p = 0, d = 1; // this is just a row index for the 2d array above since it is easier to read
            int handSum = 0, houseSum = 0, myAce = 0, dAce = 0, np = 2, nd = 2;
            //np = number of player's cards, nd = number of dealer's card. By default it is always at least 2

            generateDeck();
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

            System.out.printf("Player's cards: %s %s", cards[p][0], cards[p][1]); //The two cards dealt for the player are shown to the player
            System.out.printf("\nDealer's card: %s + ?\n", cards[d][0]); //Only the first dealer card is shown to the player while the second card is faced down.
            
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
            }

            //Dealer finally reveal his hand after the player's round ended
            System.out.printf("\nDealer's cards: %s %s\n", cards[d][0], cards[d][1]);

            //As per rule, if the dealer's hand is less than 16
            //the dealer is obligated to take another card until its hand reach at least 17
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
            }
        
            //Show the total value for Player hand and the Dealer Hands
            System.out.printf("\n\nPlayer's hand: ");
            displayHand(cards[p], np);
            System.out.printf("\nPlayer total = %d\n" ,handSum);

            System.out.printf("\nDealer's hand: ");
            displayHand(cards[d], nd);
            System.out.printf("\nDealer total = %d\n\n" ,houseSum); 
            //Display results and tally the score of the player
            determineWinner(handSum, houseSum, np, score, game);

            System.out.println("------------------------------");
            
        }

        System.out.println("\nGame Played: " + round);
        double total = tallyScore(score);
        System.out.println("Total Score: " + total);
        showStats(score);
        
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
            case 'K': val = 10; break;  //card rank 10 and court cards are counted as 10
            default: val = (int) cVal - '0'; //lookup ASCII if this doesn't make sense to you
                break;
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
            score[i] = 0;
        }

        else if(dealerHand > 21 && playerHand <= 21){
            System.out.println("You win, the dealer bust.");
            score[i] = 1;
        }

        else if(dealerHand <= 21 && playerHand > 21){
            System.out.println("You bust");
            score[i] = -1;
        }

        else if(dealerHand == playerHand){
            System.out.println("Game ended with a tie");
            score[i] = 0;
        }

        else if(((playerHand > dealerHand) && playerHand <= 21) || playerHand == 21 && dealerHand != 21){
            System.out.println("You win!");
            score[i] = 1;
        }

        else if((playerHand < dealerHand) && dealerHand <= 21){
            System.out.println("You lose.");
            score[i] = -1;
        }
    }

    public static double tallyScore(double[] score){
        double sum = 0;

        for (int i = 0; i < score.length; i++){
            sum+= score[i];
        }
        return sum;
    }

    public static void showStats(double[] score){ 

        int win = 0, blkj = 0, tie = 0, lose = 0;

        for(int i = 0; i < score.length; i++){

            if(score[i] == 1.5){
                blkj++; win++;
            }
            
            else if(score[i] == 1)
                win++;
            
            else if(score[i] == -1)
                lose++;
            
            else if(score[i] == 0)
                tie++;
            
        }

        System.out.printf("Win (Blackjack)\t Draw\tLoss\n");
        System.out.printf(" %d\t(%d)\t  %d\t  %d\n", win, blkj, tie, lose);
    }

    public static boolean isAce( String card){ //Check if the player card is ace or not
        if (card.charAt(1)=='A'){
            return true;
            }
        return false;
    }

}
