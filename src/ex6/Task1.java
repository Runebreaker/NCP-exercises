package ex6;

import java.util.concurrent.Semaphore;

public class Task1 {
    public static void main(String[] args) {
        final int NUMBER_OF_THREADS = 5;
        Runnable runnable = getDummyRunnable();

        Thread[] threads = new Thread[NUMBER_OF_THREADS];
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i] = new Thread(runnable);
            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static Runnable getDummyRunnable() {
        Semaphore semaphore = new Semaphore(4);
        return () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " is waiting for a permit...");
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " has acquired a permit!");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + " is done and released its permit!");
                semaphore.release();
            }
        };
    }
}
