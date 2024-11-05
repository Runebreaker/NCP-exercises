package ex3;

import java.util.*;

class NumberList {
    private List<Integer> list = new Vector<>();

    public synchronized void add(Integer i) {
        if (!list.contains(i)) {
            list.add(i);
            System.out.println("[" + Thread.currentThread().getName() + "] add " + i);
        }
    }

    public void checkForRedundantNumber() {
        boolean found = false;

        // Since all numbers should be equal to their index, set a flag when this is not the case
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            if (list.get(i) < i) {
                found = true;
                break;
            }
        }

        // For safety reasons, still check with existing functionalities
        Set<Integer> set = new HashSet<>(list);
        System.out.println("Number of duplicates: " + (list.size() - set.size()));

        if (!found) System.out.println("[  OK ] No duplicates.");
        else System.out.println("[ ERR ] Duplicates found!");
    }
}

class AddNumberThread extends Thread {
    private NumberList numberList = null;

    public AddNumberThread(NumberList numberList) {
        this.numberList = numberList;
    }

    @Override
    public void run() {
        int i = 0;
        while (!isInterrupted()) {
            numberList.add(i++);
        }
    }
}

public class Task3 {
    public static void main(String[] args) throws InterruptedException {
        NumberList numberList = new NumberList();
        List<Thread> threads = new LinkedList<>();
        for(int i = 0 ; i < 10; i++){
            threads.add(new AddNumberThread(numberList));
        }

        threads.forEach(t -> t.start());

        Thread.sleep(2000);

        threads.forEach(t -> t.interrupt());
        for (Thread t : threads) {
            t.join();
        }

        numberList.checkForRedundantNumber();
    }
}
