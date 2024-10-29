package ex2;

class PriorityThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hi, I am a thread with a priority of " + Thread.currentThread().getPriority() + "!");
    }
}

public class Task3 {
    public static void main(String[] args) {
        // Define Threads
        Thread t1 = new PriorityThread();
        Thread t2 = new PriorityThread();
        Thread t3 = new PriorityThread();

        // Set Priorities
        t1.setPriority(1);
        t2.setPriority(5);
        t3.setPriority(10);

        // Start them
        t1.start();
        t2.start();
        t3.start();

        // Join them to wait for all to finish!
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Couldn't join threads...");
        }
    }
}
