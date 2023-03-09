public class Main {
    public static void main(String[] args) {
        try {
            Simulation simulation = new Simulation();
            Thread t = new Thread(simulation);

            t.start();
            t.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
