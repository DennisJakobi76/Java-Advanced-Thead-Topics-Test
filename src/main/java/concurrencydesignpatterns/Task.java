package concurrencydesignpatterns;

public class Task implements Runnable{

    private final int taskId;

    public Task(int taskId){
        this.taskId = taskId;
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() + " processing task: " + taskId);

        try{
            Thread.sleep(1000L); // Simulate time taken to process the task
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + " completed task: " + taskId);
    }
}
