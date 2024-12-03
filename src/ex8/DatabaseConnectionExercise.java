package ex8;

public class DatabaseConnectionExercise {
    public static void main(String[] args) {
        PlayerDatabase playerDatabase = new PlayerDatabase();
        DatabaseAccessorThread thread1 = new DatabaseAccessorThread(playerDatabase);
        DatabaseAccessorThread thread2 = new DatabaseAccessorThread(playerDatabase);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
