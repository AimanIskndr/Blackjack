import java.util.*;
import java.io.*;

public class deckWriter {
 
    public static void main(String[] args) {
        
        try{
            String[] deck = new String[52];
            PrintWriter write = new PrintWriter("deck.txt");
            
            for(int i = 0; i < 52; i++){    
                deck[i] = generateDeck(i);
                write.println(deck[i]);
            }
            
            write.close();
            
        }catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
       
    }

    public static String generateDeck(int n){
        
        char suit[] = {'H', 'D', 'C', 'S'};
        String rank[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9","X", "J", "Q", "K"};

        String card = suit[n/13] + rank[n%13];
        
        return card;
    }
}
