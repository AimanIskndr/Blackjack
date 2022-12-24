# Blackjack
Blackjack is a popular card game that is played with a deck of 52 cards. The goal of the game is to beat the dealer by having a hand value that is 21 or as close to 21 as possible, without going over. The value of a hand is determined by the sum of the values of the individual cards. In this implementation of Blackjack, Aces are worth 1 or 11 points, face cards (King, Queen, and Jack) are worth 10 points, and all other cards are worth their face value (e.g. 2 of diamonds is worth 2 points).

## How to run the code

To run the Blackjack code, you will need to have a Java compiler installed on your computer. You can then compile and run the code by using the following commands:

`javac Blackjack.java`  //compile the code
`java Blackjack`  //run the code

## How to play the game

At the beginning of each game, the deck is shuffled. When the game starts, you will be dealt two cards and the dealer will be dealt two cards as well, with one of the dealer's cards faced down. The value of your hand and the value of the dealer's visible card will be displayed. You will then be given the option to "hit" or "stand". If you choose to "hit", you will receive another card and the value of your hand will be updated. If you choose to "stand", your turn will end and the dealer will reveal their second card and begin their turn.

The dealer is required to "hit" if the value of their hand is less than 17 and "stand" if the value of their hand is 17 or higher. The value of the dealer's hand will be updated after each card is dealt. If the value of the dealer's hand exceeds 21 and they have an Ace, the value of the Ace will be reduced from 11 to 1 to prevent the dealer from busting.

After both the player and dealer have completed their turns, the outcome of the game will be determined based on the values of their hands. If your hand is worth more than the dealer's hand or the dealer busts, you win. If your hand is worth less than the dealer's hand or you bust, you lose. If your hand is worth the same as the dealer's hand, the game is a draw.

After the game is over, you will be asked if you want to continue playing. If you choose to continue, a new game will start with a shuffled deck of cards. If you choose to stop playing, the program will end.

##### Sample Run (in VS Code version)
![Sample Run](https://media.discordapp.net/attachments/954699219485212712/1056127879806718002/image.png)
![Sample blkjk](https://media.discordapp.net/attachments/954699219485212712/1056123724379721818/image.png)
![Sample bust](https://media.discordapp.net/attachments/954699219485212712/1056124784125153350/image.png)

I hope you enjoy playing Blackjack!



