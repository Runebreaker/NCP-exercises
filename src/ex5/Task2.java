package ex5;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BlockingNumberQueue {
    // One condition for full and one for empty (Probably doable with one condition)
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final List<Integer> list = new LinkedList<>();
    private final int MAX_ITEM = 4;
    // This flag will be set and read by other threads to determine if the consumer should finish
    private boolean finished = false;
    // bounded queue (wait if full or empty)


    public void setFinished(boolean finished) {
        lock.lock();
        this.finished = finished;
        lock.unlock();
    }

    public boolean isFinished() {
        lock.lock();
        try {
            return (this.finished && list.isEmpty());
        } finally {
            lock.unlock();
        }
    }

    public int getSize() {
        lock.lock();
        try {
            return list.size();
        } finally {
            lock.unlock();
        }
    }

    public void put(Integer i) throws Exception {
        lock.lock();
        try {
            while (list.size() >= MAX_ITEM) {
                notEmpty.await();
            }
            list.add(i);
            notFull.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public Integer get() throws Exception {
        lock.lock();
        try {
            while (list.isEmpty()) {
                notFull.await();
            }
            Integer i = list.removeFirst(); // always get first in
            notEmpty.signalAll();
            return i;
        } finally {
            lock.unlock();
        }
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
            try {
                queue.put(i++);
            } catch (Exception e) {
                interrupt();
            }
        }
        queue.setFinished(true);
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
