## Task 3.1 - Updating Position

Using the code below:
- First, replicate the race condition Java example UpdatedDot from lecture slides
- Then, fix it by synchronising the threads - Test your program with at least 2 threads.

```java
class Dot {
    private int x, y;
    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {return x;}
    public void setX(int x) {this.x = x;}
    public int getY() {return y;}
    public void setY(int y) {this.y = y;}
    public String toString() {return "Dot X:" + x + "  Y:" + y;}
}

class UpdateDot extends Thread {
    private Dot dot = null;
    private int x;

    public UpdateDot(Dot dot, int x) {
        this.dot = dot;
        this.x = x;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
                dot.setX(x);
                dot.setY(x);
                if (dot.getX() != dot.getY())
                    System.out.println("DIFFERENCE --> " +
                            " Thread =  " + Thread.currentThread().getName() +
                            "   X = " + dot.getX() +
                            " | Y = " + dot.getY());
        }
    }
}
```

## Task 3.2 - Generating Unique ID

Using the code below,
- First, replicate the race condition Java example unique sequence ID generator from lecture slides
- Then, fix it by synchronising the threads - Test your program with at least 2 threads.
>**Note:** you will need to implement the function to test if unique ID generated are really unique

```java
class UniqueSequenceIDGenerator {
    private int value = 0;
    public  int getNext() {
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
```

## Task 3.3 - Unique Number List

Using the code below,
- First, replicate the race condition Java example unique number list from lecture slides
- Then, fix it by synchronising the threads - Test your program with at least 10 threads.
>**Note:** you will need to implement the function to test if the all number in the list are really unique

```java
class NumberList {
    private List<Integer> list = new Vector<>();

    public void add(Integer i) {
            if (!list.contains(i)) {
                list.add(i);
                System.out.println("[" + Thread.currentThread().getName() + "] add " + i);
            }
    }

    public void checkForRedundantNumber() {
        // to implement yoursef
        if (!found) System.out.println("[OK] No duplicates.");
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
```
