package exceptionhandlinginthreads;

import java.util.concurrent.*;

public class CallableExceptionExample {

    public static void main(String[] args) {

        while (true){
            ExecutorService executor = Executors.newSingleThreadExecutor();

            Callable<String> task = () -> {
                System.out.println(Thread.currentThread().getName() + ": Task started.");
                double value = Math.random();
                System.out.println("Generated numer of thread: " + Thread.currentThread().getName() + " is: " + value);
                if (value > 0.5) {
                    throw new RuntimeException("Simulated error!");
                }
                Thread.sleep(1000L); // Simulate some work

                return (Thread.currentThread().getName() + ": Task completed) successfully.");
            };

            Future<String> future = executor.submit(task);
            try {
                String result = future.get(); // This will throw an exception if the task failed
                System.out.println("Result: " + result);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + ": Caught exception from task - " + e.getMessage());
                System.out.println("Task interrupted.");
            }catch ( ExecutionException e){
                System.out.println(Thread.currentThread().getName() + ": Caught execution exception from task - " + e.getCause().getMessage());
                break;
            }finally {
                executor.shutdown();
            }
        }
    }
}
