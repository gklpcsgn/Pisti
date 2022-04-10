
public class Driver_CVC {
    private static int simulateNum = 100000;


    public static void main(String[] args) {

        GameCVC randvsrand = new GameCVC();
        // randvsrand.max_k();
        // randvsrand.max_m();
        

        // int[] pistiSayisip1 = new int[simulateNum];
        // int[] pistiSayisip2 = new int[simulateNum];
        // double[] results = randvsrand.simulate_gamesR(simulateNum, pistiSayisip1, pistiSayisip2);
        // System.out.println(results[4]);
        // System.out.println(results[3]);

        // GameRVR randvsrand = new GameRVR();
        int[] pistiSayisip1 = new int[simulateNum];
        int[] pistiSayisip2 = new int[simulateNum];
        double[] results = randvsrand.simulate_games(simulateNum, pistiSayisip1, pistiSayisip2);
        System.out.println(results[4]);
        System.out.println(results[3]); 
    }
}
