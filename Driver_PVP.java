import java.util.Stack;

public class Driver_PVP {
    public static void main(String[] args) {
        Player p1 = new Player();
        Stack<Card> deck = new Stack<Card>();
        Player p2 = new Player();
        GamePVP game = new GamePVP();
        game.start(p1,p2,deck);
    }
}
