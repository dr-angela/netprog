// java Aufgabe1_7 -> das rennt endlos;  mit control+c stoppen

import java.util.Stack;
import java.util.Random;

class Producer extends Thread {
    private final Stack<Integer> stack;
    private final Random rand = new Random();

    public Producer(Stack<Integer> stack) {
        this.stack = stack;
    }

    public void run() {
        while (true) {
            synchronized (stack) {
                int countToProduce = rand.nextInt(5) + 1;
                for (int i = 0; i < countToProduce; i++) {
                    int num = rand.nextInt(100);
                    stack.push(num);
                    System.out.print(num + " ");
                    System.out.println("Produced: " + num);
                }
                stack.notifyAll();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted. " + e.getMessage());
            }
        }
    }
}

class Consumer extends Thread {
    private final Stack<Integer> stack;
    private final Random rand = new Random();

    public Consumer(Stack<Integer> stack) {
        this.stack = stack;
    }

    public void run() {
        while (true) {
            int sum = 0;
            synchronized (stack) {
                while (stack.isEmpty()) {
                    try {
                        stack.wait();
                    } catch (InterruptedException e) {
                        System.err.println("Thread interrupted. " + e.getMessage());
                    }
                }

                int numToConsume = rand.nextInt(stack.size()) + 1; // at least 1
                System.out.print("Consuming " + numToConsume + " items: ");

                for (int i = 0; i < numToConsume; i++) {
                    int value = stack.pop();
                    sum += value;
                    System.out.print(value + " ");
                }
                System.out.println("Sum: " + sum);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted. " + e.getMessage());
            }
        }
    }
}

public class Aufgabe1_7 {
    public static void main(String[] args) {
        Stack<Integer> sharedStack = new Stack<>();
        Producer producer1 = new Producer(sharedStack);
        Consumer consumer = new Consumer(sharedStack);

        producer1.start();
        consumer.start();
    }
}