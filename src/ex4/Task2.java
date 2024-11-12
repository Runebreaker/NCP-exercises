package ex4;

import java.util.LinkedList;
import java.util.List;

class BlockingNumberQueue {
    private final List<Integer> list = new LinkedList<>();
    private final int MAX_ITEM = 4;
    // This flag will be set and read by other threads to determine if the consumer should finish
    private boolean finished = false;
    // bounded queue (wait if full or empty)


    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return (this.finished && list.isEmpty());
    }

    public int getSize() {
        return list.size();
    }

    public void put(Integer i) throws Exception{
        while (list.size() >= MAX_ITEM) { this.wait(); }
        list.add(i);
        this.notifyAll();
    }

    public Integer get() throws Exception{
        while (list.isEmpty()) {this.wait();}
        Integer i = list.removeFirst(); // always get first in
        this.notifyAll();
        return i;
    }
}

class SequentialNumberProducer extends Thread {
    private final BlockingNumberQueue queue;
    private final int maxValue;

    public SequentialNumberProducer(BlockingNumberQueue q) {
        this.queue = q;
        this.maxValue = 1000;
    }

    public SequentialNumberProducer(BlockingNumberQueue q, int maxValue) {
        this.queue = q;
        this.maxValue = maxValue;
    }

    @Override
    public void run() {
        int i = 0;
        while (!isInterrupted() && i <= maxValue) {
            synchronized (queue) {
                try {
                    queue.put(i++);
                }
                catch (Exception e) {
                    interrupt();
                }
            }
        }
        synchronized (queue) {
            queue.setFinished(true);
        }
    }
}

class NumberConsumer extends Thread {
    private final BlockingNumberQueue queue;

    public NumberConsumer(BlockingNumberQueue q) {
        this.queue = q;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            synchronized (queue) {
                try {
                    if (queue.isFinished()) {
                        System.out.println("Queue finished. Terminating...");
                        interrupt();
                    }
                    System.out.println(queue.get());
                } catch (Exception e) {
                    interrupt();
                }
            }
        }
    }
}

public class Task2 {
    public static void main(String[] args) throws InterruptedException {
        BlockingNumberQueue queue = new BlockingNumberQueue();
        Thread producer = new SequentialNumberProducer(queue);
        Thread consumer = new NumberConsumer(queue);
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}
