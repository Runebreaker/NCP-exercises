package ex2;

class DaemonableThread extends Thread {
    @Override
    public void run() {
        String output = Thread.currentThread().isDaemon() ? "Daemon" : "User";
                System.out.println("This is a " + output + " thread!");
    }
}

public class Task1 {
    public static void main(String[] args) {
        // Define Threads
        Thread t1 = new DaemonableThread();
        Thread t2 = new DaemonableThread();

        // Only make t1 a daemon!
        t1.setDaemon(true);

        // Start them
        t1.start();
        t2.start();
    }
}
