public class RandomPlayer extends Player{

        public RandomPlayer() {
        super();
    }
    public Card play(Card[] openCardsOnBoard,Card firstCard)
    {   
        for (int i = 0; i < 4; i++) {
            if (firstCard!=null && !hand[i].used && firstCard.getValue() == hand[i].getValue()) {
                hand[i].used = true;
                return hand[i];
            }
        }
        if (!hand[0].used) {
            hand[0].used = true;
            return hand[0];
        }
        else if (!hand[1].used) {
            hand[1].used = true;
            return hand[1];
        }
        else if (!hand[2].used) {
            hand[2].used = true;
            return hand[2];
        }
        else if (!hand[3].used) {
            hand[3].used = true;
            return hand[3];
        }
        else {
            return null;
        }
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
    
}
