import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Stack;

public class GameCVC {
    int lastOne = 1;
    int numofOpenCards = 0;
    int computerStarted = 0;
    Card printBoard[] = new Card[52];
    Stack<Card> board = new Stack<Card>();
    int player1Score = 0;
    int computerScore = 0;

    public GameCVC() {
        this.board = new Stack<Card>();
    }

    public Card turn(Player p) {
        return p.play();
    }

    public int[] start(RandomPlayer p1, AIplayer computer, Stack<Card> deck, String starter) {
        boolean jackFound = false;
        Card[] cards = new Card[52];
        int i = 0;
        for (int value = 1; value <= 13; value++) {
            cards[i++] = new Spade(value);
            cards[i++] = new Heart(value);
            cards[i++] = new Diamond(value);
            cards[i++] = new Club(value);
        }
        // shuffle the cards
        for (int j = 0; j < cards.length; j++) {
            int random = (int) (Math.random() * cards.length);
            Card temp = cards[j];
            cards[j] = cards[random];
            cards[random] = temp;
        }
        // zero for random and 1 for computer
        String answer = starter;
        int cut = 26;
        if (answer.equals("0")) {
            computerStarted = 0;
            cut = (int) (Math.random() * 44 + 4);
            if (cards[cut].getValue() == 11) { // if the cut card is Jack then player 1 gets it.
                p1.hand[0] = cards[cut];
                printBoard[numofOpenCards++] = cards[cut - 1];
                board.push(cards[cut - 2].flip());
                board.push(cards[cut - 3].flip());
                board.push(cards[cut - 4].flip());
                board.push(cards[cut - 1]);
                jackFound = true;
            } else {
                printBoard[numofOpenCards++] = cards[cut];
                board.push(cards[cut - 1].flip());
                board.push(cards[cut - 2].flip());
                board.push(cards[cut - 3].flip());
                board.push(cards[cut]);
            }
            // we have 4 cards on the board now.
            if (jackFound) {
                for (int j = cut - 5; j >= 0; j--) {
                    deck.push(cards[j]);
                }
                for (int j = 51; j > cut; j--) {
                    deck.push(cards[j]);
                }
            } else {
                for (int j = cut - 4; j >= 0; j--) {
                    deck.push(cards[j]);
                }
                for (int j = 51; j > cut; j--) {
                    deck.push(cards[j]);
                }
            }
            // deal the cards
            if (jackFound) {
                for (int j = 1; j < 4; j++) {
                    p1.hand[j] = deck.pop();
                }
                for (int j = 0; j < 4; j++) {
                    computer.hand[j] = deck.pop();
                }
            } else {
                for (int j = 0; j < 4; j++) {
                    p1.hand[j] = deck.pop();
                }
                for (int j = 0; j < 4; j++) {
                    computer.hand[j] = deck.pop();
                }
            }

        } else {
            computerStarted = 1;
            if (cards[cut].getValue() == 11) { // if the cut card is Jack then computer gets it.
                computer.hand[0] = cards[cut];
                printBoard[numofOpenCards++] = cards[cut - 1];
                board.push(cards[cut - 2].flip());
                board.push(cards[cut - 3].flip());
                board.push(cards[cut - 4].flip());
                board.push(cards[cut - 1]);
                computer.deletePlayedCard(cards[cut - 1]);

                jackFound = true;
            } else {
                printBoard[numofOpenCards++] = cards[cut];
                board.push(cards[cut - 1].flip());
                board.push(cards[cut - 2].flip());
                board.push(cards[cut - 3].flip());
                board.push(cards[cut]);
                computer.deletePlayedCard(cards[cut]);
            }
            // we have 4 cards on the board now.
            if (jackFound) {
                for (int j = cut - 5; j >= 0; j--) {
                    deck.push(cards[j]);
                }
                for (int j = 51; j > cut; j--) {
                    deck.push(cards[j]);
                }
            } else {
                for (int j = cut - 4; j >= 0; j--) {
                    deck.push(cards[j]);
                }
                for (int j = 51; j > cut; j--) {
                    deck.push(cards[j]);
                }
            }
            // deal the cards
            if (jackFound) {
                for (int j = 1; j < 4; j++) {
                    computer.hand[j] = deck.pop();
                }
                for (int j = 0; j < 4; j++) {
                    p1.hand[j] = deck.pop();
                }
            } else {
                for (int j = 0; j < 4; j++) {
                    computer.hand[j] = deck.pop();
                }
                for (int j = 0; j < 4; j++) {
                    p1.hand[j] = deck.pop();
                }
            }
        }

        return play(p1, computer, deck, answer);
    }

    public int[] play(RandomPlayer p1, AIplayer computer, Stack<Card> deck, String starter) {
        while (!deck.empty() || !p1.outOfCards() || !computer.outOfCards()) {
            // if player2 has no cards deal 4 cards
            if (computer.outOfCards() || p1.outOfCards()) {
                for (int i = 0; i < 4; i++) {
                    computer.hand[i] = deck.pop();
                    p1.hand[i] = deck.pop();
                }
            }

            if (computerStarted == 0) {
                Card card = null;
                if (numofOpenCards == 0)
                    card = p1.play(printBoard, printBoard[numofOpenCards]);
                else
                    card = p1.play(printBoard, printBoard[numofOpenCards - 1]);
                computer.deletePlayedCard(card);
                if (card.getValue() == 11 || (board.size() != 0 && card.getValue() == board.peek().getValue())) {
                    lastOne = 2;
                    // player1 gets the cards at board
                    board.push(card);
                    printBoard[numofOpenCards++] = card;
                    int size = board.size();
                    if (board.size() == 1) {
                        p1.point += 10;
                    }
                    for (int i = 0; i < size; i++) {
                        Card c = board.pop();
                        p1.collected[p1.collectedIndex++] = c;
                    }
                    // clean board
                    numofOpenCards = 0;
                    for (int i = 0; i < printBoard.length; i++) {
                        printBoard[i] = null;
                    }
                }
                // else add card to board
                else {
                    board.push(card);
                    printBoard[numofOpenCards++] = card;
                }
                Card card1 = null;
                if (numofOpenCards == 0)
                    card1 = computer.play(printBoard, printBoard[numofOpenCards]);
                else
                    card1 = computer.play(printBoard, printBoard[numofOpenCards - 1]);
                computer.deletePlayedCard(card1);
                if (card1.getValue() == 11 || (board.size() != 0 && card1.getValue() == board.peek().getValue())) {
                    lastOne = 1;
                    board.push(card1);
                    printBoard[numofOpenCards++] = card1;
                    // player1 gets the cards at board
                    int size = board.size();

                    if (board.size() == 1) {
                        computer.point += 10;
                    }
                    for (int i = 0; i < size; i++) {
                        Card c = board.pop();
                        computer.collected[computer.collectedIndex++] = c;
                    }
                    // clean board
                    numofOpenCards = 0;
                    for (int i = 0; i < printBoard.length; i++) {
                        printBoard[i] = null;
                    }
                } else {
                    board.push(card1);
                    printBoard[numofOpenCards++] = card1;
                }
            }
            // p1 started
            else {
                Card card1 = null;
                if (numofOpenCards == 0)
                    card1 = computer.play(printBoard, printBoard[numofOpenCards]);
                else
                    card1 = computer.play(printBoard, printBoard[numofOpenCards - 1]);
                computer.deletePlayedCard(card1);
                if (card1.getValue() == 11 || (board.size() != 0 && card1.getValue() == board.peek().getValue())) {
                    lastOne = 1;
                    board.push(card1);
                    printBoard[numofOpenCards++] = card1;
                    // player1 gets the cards at board
                    int size = board.size();

                    if (board.size() == 1) {
                        computer.point += 10;
                    }
                    for (int i = 0; i < size; i++) {
                        Card c = board.pop();
                        computer.collected[computer.collectedIndex++] = c;
                    }
                    // clean board
                    numofOpenCards = 0;
                    for (int i = 0; i < printBoard.length; i++) {
                        printBoard[i] = null;
                    }
                } else {
                    board.push(card1);
                    printBoard[numofOpenCards++] = card1;
                }
                Card card = null;
                if (numofOpenCards == 0)
                    card = p1.play(printBoard, printBoard[numofOpenCards]);
                else
                    card = p1.play(printBoard, printBoard[numofOpenCards - 1]);
                computer.deletePlayedCard(card);
                if (card.getValue() == 11 || (board.size() != 0 && card.getValue() == board.peek().getValue())) {
                    lastOne = 2;
                    // player1 gets the cards at board
                    board.push(card);
                    printBoard[numofOpenCards++] = card;
                    int size = board.size();
                    if (board.size() == 1) {
                        p1.point += 10;
                    }
                    for (int i = 0; i < size; i++) {
                        Card c = board.pop();
                        p1.collected[p1.collectedIndex++] = c;
                    }
                    // clean board
                    numofOpenCards = 0;
                    for (int i = 0; i < printBoard.length; i++) {
                        printBoard[i] = null;
                    }
                }
                // else add card to board
                else {
                    board.push(card);
                    printBoard[numofOpenCards++] = card;
                }
            }
        }
        // at the end of the game last player gets board collects remaining cards
        int size = board.size();
        if (lastOne == 1) {
            for (int i = 0; i < size; i++) {
                Card card = board.pop();
                p1.collected[p1.collectedIndex++] = card;
                computer.deletePlayedCard(card);
            }
        } else {
            for (int i = 0; i < size; i++) {
                Card card = board.pop();
                computer.collected[computer.collectedIndex++] = card;
                computer.deletePlayedCard(card);

            }
        }
        // clean board
        numofOpenCards = 0;
        for (int i = 0; i < printBoard.length; i++) {
            printBoard[i] = null;
        }
        // print final result
        // calculate points
        for (Card c : p1.collected) {
            if (c == null) {
                break;
            }
            if (c.getValue() == 1) {
                // System.out.println("Player1 collected an Ace");
                p1.point += 1;
            } else if (c.getValue() == 11) {
                // System.out.println("Player1 collected a Jack");
                p1.point += 1;
            } else if (c.getValue() == 2 && c.getClass().getSimpleName().equals("Spade")) {
                {
                    // System.out.println("Player1 collected a 2 of Spades");
                    p1.point += 2;
                }
            } else if (c.getValue() == 10 && c.getClass().getSimpleName().equals("Diamond")) {
                {
                    // System.out.println("Player1 collected a 10 of Diamonds");
                    p1.point += 3;
                }
            }
        }
        for (Card c : computer.collected) {
            if (c == null) {
                break;
            }
            if (c.getValue() == 1) {
                // System.out.println("Computer collected an Ace");
                computer.point += 1;
            } else if (c.getValue() == 11) {
                // System.out.println("Computer collected a Jack");
                computer.point += 1;
            } else if (c.getValue() == 2 && c.getClass().getSimpleName().equals("Spade")) {

                // System.out.println("Computer collected a 2 of Spades");
                computer.point += 2;

            } else if (c.getValue() == 10 && c.getClass().getSimpleName().equals("Diamond")) {
                // System.out.println("Computer collected a 10 of Diamonds");
                computer.point += 3;
            }
        }
        int[] results = new int[3];
        // add 3 point who has more cards than other
        if (p1.collectedIndex > computer.collectedIndex) {
            p1.point += 3;
        } else if (computer.collectedIndex > p1.collectedIndex) {
            computer.point += 3;
        }
        // print final result
        if (computer.point > p1.point) {
            results[0] = 2;
            results[1] = p1.point;
            results[2] = computer.point;
            return results;
        } else if (p1.point > computer.point) {
            results[0] = 0;
            results[1] = p1.point;
            results[2] = computer.point;
            return results;
        }
        results[0] = 1;
        results[1] = p1.point;
        results[2] = computer.point;
        return results;
    }

    public double[] simulate_games(double num,int[] pistiSayisip1,int[] pistiSayisip2) {
        double[] results = new double[8];
        //0 : p1 win percentage
        //1 : p2 win percentage
        //2 : draw percentage
        int random_wins = 0;
        int AI_wins = 0;
        int draw = 0;
        int p1_points = 0;
        int p2_points = 0;
        for (int i = 0; i < num; i++) {
            RandomPlayer p1 = new RandomPlayer();
            AIplayer p2 = new AIplayer();
            Stack<Card> deck = new Stack<>();
            String starter = "2";
            int[] result = start(p1, p2, deck, starter);
            if (result[0] == 0) {
                random_wins++;
            } else if (result[0] == 2) {
                AI_wins++;
            } else {
                draw++;
            }
            int p1_pistisiz = calcPoint(p1.collected);
            int p2_pistisiz = calcPoint(p2.collected);

            if (p1.point > p1_pistisiz) {
                pistiSayisip1[i] = (p1.point - p1_pistisiz)/10;
            }
            if (p2.point > p2_pistisiz) {
                pistiSayisip2[i] = (p2.point - p2_pistisiz)/10;
            }

            p1_points += result[1];
            p2_points += result[2];
            
        }
        // System.out.println("Random wins: " + random_wins);
        // System.out.println("AI wins: " + AI_wins);
        // System.out.println("Draw: " + draw);

        // // print total points
        // System.out.println("Random total points: " + p1_points);
        // System.out.println("AI total points: " + p2_points);

        //print winning percentage of AI
        System.out.println("AI winning percentage: " + (AI_wins * 100) / num + "%");
        System.out.println("Random winning percentage: " + (random_wins * 100) / num + "%");

        int randomPistiSayisi = 0;
        for (int i = 0; i < pistiSayisip1.length; i++) {
            randomPistiSayisi += pistiSayisip1[i];
        }

        int aiPistiSayisi = 0;
        for (int i = 0; i < pistiSayisip2.length; i++) {
            aiPistiSayisi += pistiSayisip2[i];
        }
        // System.out.println("AI pisti sayisi: " + aiPistiSayisi);
        // System.out.println("Random pisti sayisi: " + randomPistiSayisi);
        results[0] = (double)(random_wins * 100) / num;
        results[1] = (double)(AI_wins * 100) / num;
        results[2] = (double)(draw * 100) / num;
        results[3] = randomPistiSayisi;
        results[4] = aiPistiSayisi;
        results[5] = random_wins;
        results[6] = AI_wins;
        results[7] = draw;
        return results;
    }
    public int max_m()
    {
        double max = 0;
        double[] current;
        int max_k = 0;
        double simulateNum = 100000D;
        //use printwriter to write a file
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("winForEachM.txt", "UTF-8");
            for (int i = 1; i < 26; i++) {
                // AIplayer.m = i;
                int[] pistiSayisip1 = new int[(int)simulateNum];
                int[] pistiSayisip2 = new int[(int)simulateNum];
                current = simulate_games(simulateNum, pistiSayisip1, pistiSayisip2);
                System.out.println("AI win percentage for "+ i + " is : %" + current[1]);
                System.out.println("\n----------------------------------------------\n");
                writer.print(current[6]+","+current[4]+","+current[3]+","+current[5]+"\n");
                if (current[1] > max) {
                    max = current[1];
                    max_k = i;
                }
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        finally
        {
            writer.close();
        }
        
        
        return max_k;
    }
    public int max_k()
    {
        double max = 0;
        double[] current;
        int max_k = 0;
        double simulateNum = 100000D;
        //use printwriter to write a file
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("winForEachK.txt", "UTF-8");
            for (int i = 0; i < 12; i++) {
                AIplayer.k = i;
                int[] pistiSayisip1 = new int[(int)simulateNum];
                int[] pistiSayisip2 = new int[(int)simulateNum];
                current = simulate_games(simulateNum, pistiSayisip1, pistiSayisip2);
                System.out.println("AI win percentage for "+ i + " is : %" + current[1]);
                System.out.println("\n----------------------------------------------\n");
                writer.print(current[6]+","+current[4]+","+current[3]+","+current[5]+"\n");
                if (current[1] > max) {
                    max = current[1];
                    max_k = i;
                }
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        finally
        {
            writer.close();
        }
        
        
        return max_k;
    }
    public int calcPoint(Card[] cards)
    {
        int point = 0;
        for (Card c : cards) {
            if (c == null) {
                break;
            }
            if (c.getValue() == 1) {
                // System.out.println("Player1 collected an Ace");
                point += 1;
            } else if (c.getValue() == 11) {
                // System.out.println("Player1 collected a Jack");
                point += 1;
            } else if (c.getValue() == 2 && c.getClass().getSimpleName().equals("Spade")) {
                {
                    // System.out.println("Player1 collected a 2 of Spades");
                    point += 2;
                }
            } else if (c.getValue() == 10 && c.getClass().getSimpleName().equals("Diamond")) {
                {
                    // System.out.println("Player1 collected a 10 of Diamonds");
                    point += 3;
                }
            }
        }
        return point;
    }
    public double[] simulate_gamesR(double num,int[] pistiSayisip1,int[] pistiSayisip2) {
        double[] results = new double[8];
        //0 : p1 win percentage
        //1 : p2 win percentage
        //2 : draw percentage
        int random_wins = 0;
        int AI_wins = 0;
        int draw = 0;
        int p1_points = 0;
        int p2_points = 0;
        for (int i = 0; i < num; i++) {
            RandomPlayer p1 = new RandomPlayer();
            AIplayer p2 = new AIplayer();
            Stack<Card> deck = new Stack<>();
            String starter = "0";
            int[] result = start(p1, p2, deck, starter);
            if (result[0] == 0) {
                random_wins++;
            } else if (result[0] == 2) {
                AI_wins++;
            } else {
                draw++;
            }
            int p1_pistisiz = calcPoint(p1.collected);
            int p2_pistisiz = calcPoint(p2.collected);

            if (p1.point > p1_pistisiz) {
                pistiSayisip1[i] = (p1.point - p1_pistisiz)/10;
            }
            if (p2.point > p2_pistisiz) {
                pistiSayisip2[i] = (p2.point - p2_pistisiz)/10;
            }

            p1_points += result[1];
            p2_points += result[2];
            
        }
        // System.out.println("Random wins: " + random_wins);
        // System.out.println("AI wins: " + AI_wins);
        // System.out.println("Draw: " + draw);

        // // print total points
        // System.out.println("Random total points: " + p1_points);
        // System.out.println("AI total points: " + p2_points);

        //print winning percentage of AI
        System.out.println("AI winning percentage: " + (AI_wins * 100) / num + "%");
        System.out.println("Random winning percentage: " + (random_wins * 100) / num + "%");

        int randomPistiSayisi = 0;
        for (int i = 0; i < pistiSayisip1.length; i++) {
            randomPistiSayisi += pistiSayisip1[i];
        }

        int aiPistiSayisi = 0;
        for (int i = 0; i < pistiSayisip2.length; i++) {
            aiPistiSayisi += pistiSayisip2[i];
        }
        // System.out.println("AI pisti sayisi: " + aiPistiSayisi);
        // System.out.println("Random pisti sayisi: " + randomPistiSayisi);
        results[0] = (double)(random_wins * 100) / num;
        results[1] = (double)(AI_wins * 100) / num;
        results[2] = (double)(draw * 100) / num;
        results[3] = randomPistiSayisi;
        results[4] = aiPistiSayisi;
        results[5] = random_wins;
        results[6] = AI_wins;
        results[7] = draw;
        return results;
    }
}
