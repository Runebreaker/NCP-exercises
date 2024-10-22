package ex1;

class HelloWorldThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hello World!");
    }
}

class HelloWorldRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello World!");
    }
}

public class Task1 {
    public static void main(String[] args) {
        // Solution 1: Via extended Thread class
        Thread hw1 = new HelloWorldThread();
        // Solution 2: Via Runnable
        Runnable hw2 = new HelloWorldRunnable();
        hw1.start();
        hw2.run();
        // Solution 3: Via anonymous Runnable
        new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World!");
            }
        }.run();
        // Solution 4: Via lambda?
        Runnable lambda = () -> {
            System.out.println("Hello World!");
        };
        lambda.run();
    }
}