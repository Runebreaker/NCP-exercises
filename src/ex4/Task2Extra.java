package ex4;

import java.util.concurrent.LinkedBlockingQueue;

class SequentialNumberProducerExtra extends Thread {
    private final LinkedBlockingQueue<Integer> queue;
    private final int maxValue;

    public SequentialNumberProducerExtra(LinkedBlockingQueue<Integer> q) {
        this.queue = q;
        this.maxValue = 1000;
    }

    public SequentialNumberProducerExtra(LinkedBlockingQueue<Integer> q, int maxValue) {
        this.queue = q;
        this.maxValue = maxValue;
    }

    @Override
    public void run() {
        int i = 0;
        while (!isInterrupted() && i <= maxValue) {
            try {
                queue.put(i++);
            } catch (Exception e) {
                interrupt();
            }
        }
        // Put an unreachable value here to communicate with the other thread, now that we don't have a flag
        // on the monitor object
        try {
            queue.put(-1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class NumberConsumerExtra extends Thread {
    private final LinkedBlockingQueue<Integer> queue;

    public NumberConsumerExtra(LinkedBlockingQueue<Integer> q) {
        this.queue = q;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                var readValue = queue.take();
                if (readValue == -1) {
                    System.out.println("Queue finished. Terminating...");
                    interrupt();
                }
                else System.out.println(readValue);
            } catch (Exception e) {
                interrupt();
            }
        }
    }
}

public class Task2Extra {
    public static void main(String[] args) throws InterruptedException {
        // Due to LinkedBlockingQueue being thread-safe, we don't have to synchronize anywhere in the example!
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        Thread producer = new SequentialNumberProducerExtra(queue);
        Thread consumer = new NumberConsumerExtra(queue);
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}
