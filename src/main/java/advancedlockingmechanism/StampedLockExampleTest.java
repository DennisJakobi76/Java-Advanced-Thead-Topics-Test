package advancedlockingmechanism;

public class StampedLockExampleTest {

    public static void main(String[] args) {

        BankAccount account = new BankAccount();

        Runnable depositTask = () -> {
            for (int i = 0; i < 3; i ++){
                account.deposit(100);
                try{
                    Thread.sleep(500L);
                } catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable withdrawTask = () -> {
            for (int i = 0; i < 3; i ++){
                account.withdraw(50);
                try{
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable readTask = () -> {

            for (int i = 0; i < 3; i++){
                account.getBalanceOptimistic();
                try{
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread depositorThread = new Thread(depositTask, "Depositor");
        Thread withdawerThread = new Thread(withdrawTask, "Withdrawer");
        Thread readThread = new Thread(readTask, "Reader");

        depositorThread.start();
        withdawerThread.start();
        readThread.start();

        try {
            depositorThread.join();
            withdawerThread.join();
            readThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Final Balance: " + account.getBalance());
    }
}
