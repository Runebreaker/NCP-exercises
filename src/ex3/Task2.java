package ex3;

import java.util.LinkedList;
import java.util.List;

class UniqueSequenceIDGenerator {
    private int value = 0;

    // Synchronize the function, so every caller needs to wait for the previous to finish!
    public synchronized int getNext() {
        return value++;
    }
}

class UpdateSequenceID extends Thread {
    private UniqueSequenceIDGenerator uniqueSequenceIDGenerator;
    private List<Integer> uniqueIDs = new LinkedList<>();

    public UpdateSequenceID(UniqueSequenceIDGenerator uniqueSequenceIDGenerator) {
        this.uniqueSequenceIDGenerator = uniqueSequenceIDGenerator;
    }

    public List<Integer> getUniqueIDs() {
        return uniqueIDs;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            uniqueIDs.add(uniqueSequenceIDGenerator.getNext());
        }
    }
}

public class Task2 {
    public static void main(String[] args) {
        UniqueSequenceIDGenerator generator = new UniqueSequenceIDGenerator();
        UpdateSequenceID t1 = new UpdateSequenceID(generator);
        UpdateSequenceID t2 = new UpdateSequenceID(generator);

        t1.start();
        t2.start();

        try {
            Thread.sleep(2);
            t1.interrupt();
            t2.interrupt();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Size of list 1: " + t1.getUniqueIDs().size() + ". Size of list 2: " + t2.getUniqueIDs().size() + ".");

        // Check for duplicates by retaining all elements from t2 in t1!
        List<Integer> dupes = t1.getUniqueIDs();
        dupes.retainAll(t2.getUniqueIDs());

        if (!dupes.isEmpty()) System.out.println("Found duplicates: " + dupes.size() + ". First Duplicate: " + dupes.getFirst());
        else System.out.println("No dupes found!");
    }
}
