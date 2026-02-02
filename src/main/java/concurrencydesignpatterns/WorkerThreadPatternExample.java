package concurrencydesignpatterns;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WorkerThreadPatternExample {

    public static void main(String[] args) {

        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
        int numberOfWorkers = 3; // pool size

        Worker[] workers = new Worker[numberOfWorkers];

        // Start workers
        for(int i = 0; i < numberOfWorkers; i++) {
            workers[i] = new Worker(taskQueue);
            workers[i].start();
        }

        // Add tasks to the queue
        for (int i = 1; i <= 10; i++){
            taskQueue.add(new Task(i));
        }

        // Stop workers after all tasks sre processed
        try {
            // Allow some time for tasks to complete
            Thread.sleep(12000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Interrupt workers to stop them
        for(Worker worker : workers) {
            worker.interrupt(); // Stop workers
        }
    }
}
