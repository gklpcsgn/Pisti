import java.util.Stack;

public class GamePVP implements Input {
    int lastOne = 1;
    int numofOpenCards = 0;
    Card printBoard[] = new Card[52];
    Stack<Card> board = new Stack<Card>();
    int player1Score = 0;
    int player2Score = 0;

    public GamePVP() {
        this.board = new Stack<Card>();
    }

    public Card turn(Player p) {
        return p.play();
    }

    public void start(Player p1, Player p2, Stack<Card> deck) {
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
        System.out.println("Enter the cut number(4-48): ");
        int cut = scanner.nextInt() - 1;
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
                p2.hand[j] = deck.pop();
            }
        } else {
            for (int j = 0; j < 4; j++) {
                p1.hand[j] = deck.pop();
            }
            for (int j = 0; j < 4; j++) {
                p2.hand[j] = deck.pop();
            }
        }
        play(p1, p2, deck);
    }

    public void play(Player p1, Player p2, Stack<Card> deck) {
        while (!deck.empty() || !p1.outOfCards() || !p2.outOfCards()) {
            // if player2 has no cards deal 4 cards
            if (p2.outOfCards()) {
                for (int i = 0; i < 4; i++) {
                    p2.hand[i] = deck.pop();
                    p1.hand[i] = deck.pop();
                }
            }
            // player2 will start
            System.out.println("\n----------------------------------------\nPlayer 2's turn");
            // print board except closed cards
            System.out.println("Board ( Bottom to top ) :");
            for (int i = 0; i < numofOpenCards; i++) {
                System.out.print(printBoard[i] + " - ");
            }
            System.out.println();
            Card card = turn(p2);
            if (card.getValue() == 11 || (board.size() != 0 && card.getValue() == board.peek().getValue())) {
                lastOne = 2;
                // player2 gets the cards at board
                board.push(card);
                printBoard[numofOpenCards++] = card;
                System.out.println("Player 2 gets the cards at the board");
                int size = board.size();
                if (board.size() == 1) {
                    p2.point += 10;
                }
                for (int i = 0; i < size; i++) {
                    Card c = board.pop();
                    if (!c.closed) {
                        if (i != size)
                            System.out.println(c + " - ");
                        else
                            System.out.println(c);
                    }
                    p2.collected[p2.collectedIndex++] = c;
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
            System.out.println("\n----------------------------------------\nPlayer 1's turn");
            // print board except closed cards and player1's turn
            System.out.println("Board ( Bottom to top ) :");
            for (int i = 0; i < numofOpenCards; i++) {
                System.out.print(printBoard[i] + " - ");
            }
            System.out.println();
            Card card1 = turn(p1);
            if (card1.getValue() == 11 || (board.size() != 0 && card1.getValue() == board.peek().getValue())) {
                lastOne = 1;
                board.push(card1);
                printBoard[numofOpenCards++] = card1;
                // player1 gets the cards at board
                System.out.println("Player 1 gets the cards at the board");
                int size = board.size();

                if (board.size() == 1) {
                    p1.point += 10;
                }
                for (int i = 0; i < size; i++) {
                    Card c = board.pop();
                    if (!c.closed) {
                        if (i != size)
                            System.out.println(c + " - ");
                        else
                            System.out.println(c);
                    }
                    p1.collected[p1.collectedIndex++] = c;
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
        // at the end of the game last player gets board collects remaining cards
        int size = board.size();
        if (lastOne == 1) {
            for (int i = 0; i < size; i++) {
                p1.collected[p1.collectedIndex++] = board.pop();
            }
        } else {
            for (int i = 0; i < size; i++) {
                p2.collected[p2.collectedIndex++] = board.pop();
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
                p1.point += 1;
            } else if (c.getValue() == 11) {
                p1.point += 1;
            } else if (c.getValue() == 2 && c.getClass().getSimpleName().equals("Spade")) {
                {
                    p1.point += 2;
                }
            } else if (c.getValue() == 10 && c.getClass().getSimpleName().equals("Diamond")) {
                {
                    p1.point += 3;
                }
            }
        }
        for (Card c : p2.collected) {
            if (c == null) {
                break;
            }
            if (c.getValue() == 1) {
                p2.point += 1;
            } else if (c.getValue() == 11) {
                p2.point += 1;
            } else if (c.getValue() == 2 && c.getClass().getSimpleName().equals("Spade")) {
                {
                    p2.point += 2;
                }
            } else if (c.getValue() == 10 && c.getClass().getSimpleName().equals("Diamond")) {
                {
                    p2.point += 3;
                }
            }
        }
        // add 3 point who has more cards than other
        if (p1.collectedIndex > p2.collectedIndex) {
            p1.point += 3;
        } else if (p2.collectedIndex > p1.collectedIndex) {
            p2.point += 3;
        }
        // print final result
        System.out.println("\n----------------------------------------\nFinal result");
        System.out.println("Player 1 : " + p1.point);
        System.out.println("Player 2 : " + p2.point);

    }
}
