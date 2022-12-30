# Blackjack
Blackjack is a popular card game that is played with a deck of 52 cards. The goal of the game is to beat the dealer by having a hand value that is 21 or as close to 21 as possible, without going over. The value of a hand is determined by the sum of the values of the individual cards. In this implementation of Blackjack, Aces are worth 1 or 11 points, face cards (King, Queen, and Jack) are worth 10 points, and all other cards are worth their face value (e.g. 2 of diamonds is worth 2 points).

This Java code simulates a game of Blackjack. The code shuffles a deck of cards, deals two cards to the player and two cards to the dealer, and allows the player to "hit" or "stand" until they decide to stand or until they bust (go over 21). The dealer will then reveal their hand and will "hit" or "stand" according to a set of rules. The game will output the result of the round (win, lose, or draw) and the final score for each hand. The game can be played for a specified number of rounds.

## Getting Started
To run this code, you will need to have the Java Development Kit (JDK) and a Java Integrated Development Environment (IDE) installed on your machine. The code was developed and tested using Visual Studio Code, but any Java IDE should work.

### Prerequisites
JDK: You can download the latest version of the JDK [here](https://www.oracle.com/java/technologies/downloads/).

IDE: Some popular Java IDEs include [Eclipse](https://www.eclipse.org/downloads/), [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows), and [Netbeans](https://netbeans.apache.org/download/index.html).

### Running the code
1. Clone or download the code to your local machine.
2. Open the code in your chosen IDE.
3. Compile and run the code.

  * `javac Blackjack.java`

  * `java Blackjack`

4. Follow the prompts in the terminal to play the game.

## How to play the game

1. Compile and run the code in your Java IDE.
2. At the start of each round, you will be prompted to enter the number of rounds you want to play. Enter the desired number of rounds and press Enter.
3. For each round, you will be dealt two cards and the dealer will be dealt two cards, with one of the dealer's cards facedown. The value of your hand will be displayed, along with the facedown dealer card.
4. You will then be prompted to "hit" or "stand". If you choose to "hit", you will receive another card. If you choose to "stand", your turn will end. You can choose to "hit" or "stand" as many times as you wish, but be careful not to go over 21 (bust).
5. After you choose to "stand" or bust, the dealer will reveal their facedown card and will "hit" or "stand" according to the following rules:
    * If the value of the dealer's hand is less than 17, the dealer will "hit" and receive another card.
    * If the value of the dealer's hand is 17 or higher, the dealer will "stand" and their turn will end.
6. The game will then determine the winner of the round based on the following rules:
    * If you bust, you lose and the dealer wins.
    * If the dealer busts, you win.
    * If neither you nor the dealer bust, the winner is determined by the highest hand value. If your hand value is higher, you win. If the dealer's hand value is higher, the dealer wins. If both hands have the same value, the round is a draw.
7. The game will display the result of the round 
8. If there are more rounds to play, the game will start the next round. If you have played the desired number of rounds, the game will end.
9. The program will output the final overall score and the statistics of the game (win, blackjack, lose, or draw)

##### Sample Run (in [VS Code version](https://github.com/AimanIskndr/Blackjack/tree/VSCode-ver))
![Sample Run](https://media.discordapp.net/attachments/954699219485212712/1057681745060827166/Sample_run.png)
![Sample blkjk](https://media.discordapp.net/attachments/954699219485212712/1057681744683335770/Sample_run_2.png)

I hope you enjoy playing Blackjack!

#### Remarks

For this program to work, you have to also download the deck.txt file and put in the same project folder as your Blackjack.java file

<sub> Consider checking the [VSCode version](https://github.com/AimanIskndr/Blackjack/tree/VSCode-ver) instead of this version if you use Visual Studio Code </sub>

