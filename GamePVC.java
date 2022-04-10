import java.util.Stack;

public class GamePVC implements Input {
    int lastOne = 1;
    int numofOpenCards = 0;
    int computerStarted = 0;
    Card printBoard[] = new Card[52];
    Stack<Card> board = new Stack<Card>();
    int player1Score = 0;
    int computerScore = 0;

    public GamePVC() {
        this.board = new Stack<Card>();
    }

    public Card turn(Player p) {
        return p.play();
    }

    public void start(Player p1, AIplayer computer, Stack<Card> deck) {
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
        System.out.println("Birinci oyuncu olmak istiyor musunuz? (e/h)");
        String answer = scanner.nextLine();
        int cut = 26;
        if (answer.equals("e")) {
            computerStarted = 0;
            System.out.println("Deste kaçıncı karttan kesilsin?(4-48): ");
            cut = scanner.nextInt() - 1;
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
                    computer.deletePlayedCard(computer.hand[j]);
                }
            } else {
                for (int j = 0; j < 4; j++) {
                    p1.hand[j] = deck.pop();
                }
                for (int j = 0; j < 4; j++) {
                    computer.hand[j] = deck.pop();
                    computer.deletePlayedCard(computer.hand[j]);
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
                    computer.hand[j] = deck.pop();
                    computer.deletePlayedCard(computer.hand[j]);

                }
                for (int j = 0; j < 4; j++) {
                    p1.hand[j] = deck.pop();
                }
            } else {
                for (int j = 0; j < 4; j++) {
                    computer.hand[j] = deck.pop();
                    computer.deletePlayedCard(computer.hand[j]);

                }
                for (int j = 0; j < 4; j++) {
                    p1.hand[j] = deck.pop();
                }
            }
        }

        play(p1, computer, deck);
    }

    public void play(Player p1, AIplayer computer, Stack<Card> deck) {
        while (!deck.empty() || !p1.outOfCards() || !computer.outOfCards()) {
            // if player2 has no cards deal 4 cards
            if (computer.outOfCards()) {
                for (int i = 0; i < 4; i++) {
                    computer.hand[i] = deck.pop();
                    p1.hand[i] = deck.pop();
                    computer.deletePlayedCard(computer.hand[i]);
                }
            }
            if (computerStarted == 0) {
                System.out.println("----------------------------------------\nBirinci Oyuncunun turu\n----------------------------------------");
                System.out.println("Ortadaki kartlar(En alttan üste) :");
                for (int i = 0; i < numofOpenCards; i++) {
                    System.out.print(printBoard[i] + " ");
                }
                System.out.println();
                Card card = turn(p1);
                computer.deletePlayedCard(card);
                if ((card.getValue() == 11 && board.size() != 0) || (board.size() != 0 && card.getValue() == board.peek().getValue())) {
                    lastOne = 2;
                    // player1 gets the cards at board
                    board.push(card);
                    printBoard[numofOpenCards++] = card;
                    System.out.println("----------------------------------------");
                    System.out.println("Birinci oyuncu ortadaki kartları aldı.(En üstten alta)");
                    int size = board.size();
                    if (board.size() == 2) {
                        p1.point += 10;
                    }
                    for (int i = 0; i < size; i++) {
                        Card c = board.pop();
                        if (i != size)
                            System.out.print(c + " ");
                        else
                            System.out.print(c);

                        p1.collected[p1.collectedIndex++] = c;
                    }
                    System.out.println();
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
                System.out.println("----------------------------------------\nBilgisayarın turu\n----------------------------------------");
                // System.out.println("Ortadaki kartlar(En alttan üste) :");
                // for (int i = 0; i < numofOpenCards; i++) {
                //     System.out.print(printBoard[i] + " - ");
                // }
                Card card1 = null;
                if (numofOpenCards == 0)
                    card1 = computer.play(printBoard, printBoard[numofOpenCards]);
                else
                    card1 = computer.play(printBoard, printBoard[numofOpenCards - 1]);
                computer.deletePlayedCard(card1);
                if ((card1.getValue() == 11 && board.size() != 0)|| (board.size() != 0 && card1.getValue() == board.peek().getValue())) {
                    lastOne = 1;
                    board.push(card1);
                    printBoard[numofOpenCards++] = card1;
                    // computer gets the cards at board
                    System.out.println("----------------------------------------");
                    System.out.println("Bilgisayar ortadaki kartları aldı.(En üstten alta)");
                    int size = board.size();

                    if (board.size() == 2) {
                        computer.point += 10;
                    }
                    for (int i = 0; i < size; i++) {
                        Card c = board.pop();
                        if (!c.closed) {
                            if (i != size)
                                System.out.print(c + " ");
                            else
                                System.out.print(c);
                        }
                        computer.collected[computer.collectedIndex++] = c;
                    }
                    System.out.println();
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
                System.out.println("----------------------------------------\nBilgisayarın turu\n----------------------------------------");
                // System.out.println("Ortadaki kartlar(En alttan üste) :");
                // for (int i = 0; i < numofOpenCards; i++) {
                //     System.out.print(printBoard[i] + " - ");
                // }
                Card card1 = null;
                if (numofOpenCards == 0)
                    card1 = computer.play(printBoard, printBoard[numofOpenCards]);
                else
                    card1 = computer.play(printBoard, printBoard[numofOpenCards - 1]);
                computer.deletePlayedCard(card1);
                if ((card1.getValue() == 11 && board.size() != 0) || (board.size() != 0 && card1.getValue() == board.peek().getValue())) {
                    lastOne = 1;
                    board.push(card1);
                    printBoard[numofOpenCards++] = card1;
                    // player1 gets the cards at board
                    System.out.println("----------------------------------------");
                    System.out.println("Bilgisayar ortadaki kartları aldı.(En üstten alta)");
                    int size = board.size();

                    if (board.size() == 2) {
                        computer.point += 10;
                    }
                    for (int i = 0; i < size; i++) {
                        Card c = board.pop();
                        if (!c.closed) {
                            if (i != size)
                                System.out.print(c + " ");
                            else
                                System.out.print(c);
                        }
                        computer.collected[computer.collectedIndex++] = c;
                    }
                    System.out.println();
                    // clean board
                    numofOpenCards = 0;
                    for (int i = 0; i < printBoard.length; i++) {
                        printBoard[i] = null;
                    }
                } else {
                    board.push(card1);
                    printBoard[numofOpenCards++] = card1;
                }
                System.out.println("----------------------------------------\nBirinci Oyuncunun turu\n----------------------------------------");
                System.out.println("Ortadaki kartlar(En alttan üste) :");
                for (int i = 0; i < numofOpenCards; i++) {
                    System.out.print(printBoard[i] + " ");
                }
                System.out.println();
                System.out.println();
                Card card = turn(p1);
                computer.deletePlayedCard(card);
                if ((card.getValue() == 11 && board.size() != 0) || (board.size() != 0 && card.getValue() == board.peek().getValue())) {
                    lastOne = 2;
                    // player1 gets the cards at board
                    board.push(card);
                    printBoard[numofOpenCards++] = card;
                    System.out.println("----------------------------------------");
                    System.out.println("Birinci oyuncu ortadaki kartları aldı.(En üstten alta)");
                    int size = board.size();
                    if (board.size() == 2) {
                        p1.point += 10;
                    }
                    for (int i = 0; i < size; i++) {
                        Card c = board.pop();
                        if (i != size)
                            System.out.print(c + " ");
                        else
                            System.out.print(c);

                        p1.collected[p1.collectedIndex++] = c;
                    }
                    System.out.println();
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
        } // clean board

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
        if (computer.collectedIndex > p1.collectedIndex) {
            computer.point+=3;
        }
        if (computer.collectedIndex < p1.collectedIndex) {
            p1.point+=3;
        }


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
        // print final result
        System.out.println("\n----------------------------------------\nSonuç :");
        System.out.println("Oyuncu : " + p1.point + " puan.");
        System.out.println("Bilgisayar : " + computer.point + " puan.");

    }
}
