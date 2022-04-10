public class Player implements Input {
    Card[] hand;
    public static boolean first = true;
    Card[] collected;
    int collectedIndex;
    int point = 0;

    public Player() {
        this.collectedIndex = 0;
        this.hand = new Card[4];
        this.collected = new Card[52];
        this.point = 0;
    }
    public Card play() {
        System.out.println("Oynamak istediğiniz kartı seçin.(1-2-3-4): ");
        // print hand
        for (int i = 0; i < 4; i++) {
            System.out.print(i + 1 + ": ");
            if (!hand[i].used) {
                System.out.println(hand[i]);
            } else
                System.out.println(" ");
        }
        // scanner bug.
        if (first) {
            scanner.nextLine();
            first = false;
        }
        int choice = 1;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Lütfen geçerli bir sayı giriniz.");
            }
            finally{
                if(choice < 1 || choice > 4 || hand[choice - 1].used) {
                    continue;
                }
                break;
            }
        }
        hand[choice - 1].used = true;
        return hand[choice - 1];
    }

    public boolean outOfCards() {
        // check if the player has any cards left
        // if not, return true
        for (int i = 0; i < 4; i++) {
            if (!hand[i].used) {
                return false;
            }
        }
        return true;
    }

}
