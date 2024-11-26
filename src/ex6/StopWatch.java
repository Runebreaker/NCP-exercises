package ex6;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class StopWatch {
    public static void main(String[] args) throws InterruptedException {
        // Usage Demo
        StopWatch stopWatch = new StopWatch();
        Thread.sleep(500);
        System.out.println("time Since created " + stopWatch.time());
        stopWatch.reset();
        Thread.sleep(500);
        System.out.println("Time Since reset " + stopWatch.time());
    }
    private long timeWhenCreated = System.nanoTime();
    @Override
    public String toString() {
        return NANOSECONDS.toMillis(System.nanoTime() - timeWhenCreated)
                + " ms";
    }
    public void reset() {
        timeWhenCreated = System.nanoTime();
    }
    public float time() {
        return NANOSECONDS.toMillis(System.nanoTime() - timeWhenCreated);
    }
    public float timeInNano() {
        return (System.nanoTime() - timeWhenCreated);
    }
}

