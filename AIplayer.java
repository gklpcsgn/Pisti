public class AIplayer extends Player {
    public static int k = 4;
    public static int m = 0;

    Card[] unplayedCards = new Card[52];
    int totalUnplayedCards = 52;
        public AIplayer() {
        super();
        fillUnplayedCards();
    }

    public Card play(Card[] openCardsOnBoard,Card firstCard)
    {   
        ////////////////////////////////////////////////////////////////////////////////
        /*
        Use jack if you see a card on board and play the card with smallest possibility
        */
        ////////////////////////////////////////////////////////////////////////////////
        // int totalPoint = 0;
        // for (int j = 0; j < openCardsOnBoard.length; j++) {
        //     if (openCardsOnBoard[j]!=null && openCardsOnBoard[j].point > 0) {
        //         totalPoint += openCardsOnBoard[j].point;
        //     }
        // }
        // double min = Integer.MAX_VALUE;
        // int minIndex = 0;
        // for (int i = 0; i < 4; i++) {
        //     if (firstCard!=null && !hand[i].used && firstCard.getValue() == hand[i].getValue()) {
        //         hand[i].used = true;

        //         return hand[i];
        //     }
        //     if (firstCard!=null && !hand[i].used && hand[i].getValue() == 11) {
        //         hand[i].used = true;
        //         return hand[i];
        //     }
        //     if(!hand[i].used && calcPossibilityOfCard(hand[i].getValue()) < min)
        //     {
        //         min = calcPossibilityOfCard(hand[i].getValue());
        //         minIndex = i;
        //     }
        // }
        // hand[minIndex].used = true;
        // return hand[minIndex];
        ////////////////////////////////////////////////////////////////////////////////
        
        //----------------------------------------------------------------------------------

        ///////////////////////////////////////////////////////////////////////////////
        /*
        Use jack if point on board is higher than k and play the card with smallest possibility
        */
        ////////////////////////////////////////////////////////////////////////////////
        int totalPoint = 0;
        for (int j = 0; j < openCardsOnBoard.length; j++) {
            if (openCardsOnBoard[j]!=null && openCardsOnBoard[j].point > 0) {
                totalPoint += openCardsOnBoard[j].point;
            }
        }
        double min = Integer.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < 4; i++) {
            if (firstCard!=null && !hand[i].used && firstCard.getValue() == hand[i].getValue()) {
                hand[i].used = true;

                return hand[i];
            }
        }
        for (int i = 0; i < 4; i++) {
            if (firstCard!=null && (totalPoint > k) && !hand[i].used && hand[i].getValue() == 11) {
                hand[i].used = true;
                return hand[i];
            }
        }
        for (int i = 0; i < 4; i++) {
            if(!hand[i].used && calcPossibilityOfCard(hand[i].getValue()) < min)
            {
                min = calcPossibilityOfCard(hand[i].getValue());
                minIndex = i;
            }
        }
        
        hand[minIndex].used = true;
        return hand[minIndex];
        ////////////////////////////////////////////////////////////////////////////////

        // ---------------------------------------------------------------------------------

        ///////////////////////////////////////////////////////////////////////////////
        /*
        Use jack if point on board is higher than k or number of cards on board > m and play the card with smallest possibility
        */
        ////////////////////////////////////////////////////////////////////////////////
        // int totalPoint = 0;
        // int totalCards = 0;
        // double min = Integer.MAX_VALUE;
        // int minIndex = 0;
        // int k = 1;
        // int m = 1;

        // for (int j = 0; j < openCardsOnBoard.length; j++) {
        //     if (openCardsOnBoard[j]!=null && openCardsOnBoard[j].point > 0) {
        //         totalPoint += openCardsOnBoard[j].point;
        //         totalCards++;
        //     }
        // }

        // for (int i = 0; i < 4; i++) {
        //     if (firstCard!=null && !hand[i].used && firstCard.getValue() == hand[i].getValue()) {
        //         hand[i].used = true;

        //         return hand[i];
        //     }
        //     if (firstCard!=null && (totalPoint > k || totalCards > m) && !hand[i].used && hand[i].getValue() == 11) {
        //         hand[i].used = true;
        //         return hand[i];
        //     }
        //     if(!hand[i].used && calcPossibilityOfCard(hand[i].getValue()) < min)
        //     {
        //         min = calcPossibilityOfCard(hand[i].getValue());
        //         minIndex = i;
        //     }
        // }
        // hand[minIndex].used = true;
        // return hand[minIndex];
        ///////////////////////////////////////////////////////////////////////////////
        
        // ---------------------------------------------------------------------------------

        ///////////////////////////////////////////////////////////////////////////////
        /*
        play randomly
        */
        ////////////////////////////////////////////////////////////////////////////////
        // //play a random card
        // if (!hand[0].used) {
        //     hand[0].used = true;
        //     return hand[0];
        // }
        // else if (!hand[1].used) {
        //     hand[1].used = true;
        //     return hand[1];
        // }
        // else if (!hand[2].used) {
        //     hand[2].used = true;
        //     return hand[2];
        // }
        // else if (!hand[3].used) {
        //     hand[3].used = true;
        //     return hand[3];
        // }
        // else {
        //     return null;
        // }
        ///////////////////////////////////////////////////////////////////////////////
        
        // ---------------------------------------------------------------------------------

    public void fillUnplayedCards() {
        int index = 0;
        for (int value = 1; value <= 13; value++) {
            unplayedCards[index++] = new Spade(value);
            unplayedCards[index++] = new Heart(value);
            unplayedCards[index++] = new Diamond(value);
            unplayedCards[index++] = new Club(value);
        }
    }
    public void deletePlayedCard(Card card) {
        for (int i = 0; i < 52; i++) {
            if (unplayedCards[i]!=null && unplayedCards[i].getValue() == card.getValue() && unplayedCards[i].getClass() == card.getClass()) {
                unplayedCards[i] = null;
                totalUnplayedCards--;
            }
        }
    }
    public double calcPossibilityOfCard(int a) {
        int count = 0;
        this.totalUnplayedCards = 0;
        for (int i = 0; i < 52; i++) {
            if (unplayedCards[i] != null) {
                this.totalUnplayedCards++;
                if (unplayedCards[i].getValue() == a) {
                    count++;
                }
            }
        }
        return (double)count/(double)totalUnplayedCards;
    }
    public boolean outOfCards() {
        //check if the player has any cards left
        //if not, return true
        for (int i = 0; i < 4; i++) {
            if (!hand[i].used) {
                return false;
            }
        }
        return true;
    }
    public Card[] getHand() {
        return this.hand;
    }

    public Card[] getCollected() {
        return this.collected;
    }

    public int getPoint() {
        return this.point;
    }

    public int getTotalUnplayedCards() {
        return this.totalUnplayedCards;
    }

    public Card[] getUnplayedCards() {
        return this.unplayedCards;
    }

    public int getCollectedIndex() {
        return this.collectedIndex;
    }
    
}