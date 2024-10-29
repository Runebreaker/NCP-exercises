package ex2;

import java.io.IOException;
import java.util.Random;

class HelloWorldThread extends Thread {
    @Override
    public void interrupt() {
        super.interrupt();
        System.out.println(getName() + " was interrupted...");
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            System.out.println("Hello World!");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }
}

public class Task2 {
    public static void main(String[] args) throws InterruptedException, IOException {
        // Define and start
        Thread hw = new HelloWorldThread();
        hw.start();

        {
            // Interrupt after 50ms
            Thread.sleep(50);
            hw.interrupt();
            hw.join();  // Always join to wait for thread to finish!
            hw = new HelloWorldThread();
            hw.start();

            // Interrupt after 100ms
            Thread.sleep(100);
            hw.interrupt();
            hw.join();
            hw = new HelloWorldThread();
            hw.start();

            // Interrupt after random time
            long interruptTime = new Random().nextLong(1000) + 1;
            Thread.sleep(interruptTime);
            hw.interrupt();
            hw.join();
            hw = new HelloWorldThread();
            hw.start();

            // After key press
            System.out.println("Press a key to interrupt!");
            System.in.read();
            hw.interrupt();
            hw.join();
        }
    }
}
