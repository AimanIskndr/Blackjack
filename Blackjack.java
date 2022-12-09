import java.util.Scanner;

public class Blackjack {
    public static void main(String[] args){
        
        String playerCard;
        String dealerCard;
        int handSum = 0, houseSum = 0, myAce = 0, dAce = 0;

        Scanner sc = new Scanner(System.in);

        System.out.print("Player's card: ");
        
        for(int i = 0; i < 2; i++){
            
            playerCard = giveCard();
            
            if(playerCard.charAt(1) == 'A')
                myAce++;

            handSum += handCount(playerCard.charAt(1));

            System.out.printf("%s ", playerCard);
        }

        System.out.printf("\nDealer's card: ");
        
        for(int i = 0; i < 2; i++){
            
            dealerCard = giveCard();

            if(dealerCard.charAt(1) == 'A')
                dAce++;

            houseSum += handCount(dealerCard.charAt(1));

            System.out.printf("%s ", dealerCard);
        }
        
        if(handSum != 21)
            handSum = playerRound(handSum, myAce);
        

        if(houseSum <= 16)
            houseSum = dealerRound(houseSum, dAce);
        
        System.out.printf("\nPlayer hand = %d\n" ,handSum);
        System.out.printf("Dealer hand = %d\n" ,houseSum);

        result(handSum, houseSum);

        sc.close();
    }
    
    public static String giveCard() {
        
        String[] deck = {"HA", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "HX", "HJ", "HQ", "HK", 
        "DA", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "DX", "DJ", "DQ", "DK", 
        "CA", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CX", "CJ", "CQ", "CK", 
        "SA", "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9", "SX", "SJ", "SQ", "SK"};

        String card;

        do{
            double pick =  Math.random() * 52;
            card = deck[(int) pick];
            deck[(int) pick] = "x0";

        }while(card == "X0");
        
        return card;
    }
    
    public static int handCount(char cVal){
        int sum = 0;

        switch (cVal) {
            case 'A': sum += 11; break;
            case 'X':
            case 'J':
            case 'Q':
            case 'K': sum +=10; break;
            default: sum += (int) cVal - 48;
                break;
        }
        
        return sum;
    }

    public static int playerRound (int currSum, int ace){

        int hit;
        String nCard;

        Scanner sc = new Scanner(System.in);

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

        while(currSum > 21 && ace != 0){
            currSum-= 10;
        }

        sc.close();

        return currSum;
    }

    public static int dealerRound(int currSum, int ace) {
        
        String nCard;

        Scanner sc = new Scanner(System.in);

        System.out.print("Dealer take card(s): ");

        while(currSum <= 16 || (currSum > 21 && ace > 0)){
            //take a card again
            
            nCard = giveCard();
            System.out.printf("%s ", nCard);
            if(nCard.charAt(1) == 'A')
                ace++;

            currSum += handCount(nCard.charAt(1));

            if(currSum > 21 && ace != 0){
                    currSum-= 10;
                    ace--;
                }
        }

        sc.close();

        return currSum;
    }

    public static void result(int playerHand, int dealerHand) {

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
