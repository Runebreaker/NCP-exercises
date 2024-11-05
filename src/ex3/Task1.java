package ex3;

class Dot {
    private int x, y;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String toString() {
        return "Dot X:" + x + "  Y:" + y;
    }
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
            // Put synchronized here, so every operation on 'dot' is done by acquiring the monitor beforehand!
            synchronized (dot) {
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
}

public class Task1 {
    public static void main(String[] args) {
        Dot dot = new Dot(0, 0);
        UpdateDot updater1 = new UpdateDot(dot, 1);
        UpdateDot updater2 = new UpdateDot(dot, 10);

        updater1.start();
        updater2.start();
        try {
            updater1.join();
            updater2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
