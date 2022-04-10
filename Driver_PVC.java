import java.util.Stack;

public class Driver_PVC {
    public static void main(String[] args) {
        Player p1 = new Player();
        Stack<Card> deck = new Stack<Card>();
        AIplayer p2 = new AIplayer();
        GamePVC game = new GamePVC();
        game.start(p1,p2,deck);
    }
}
