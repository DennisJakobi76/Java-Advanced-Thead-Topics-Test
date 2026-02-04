package advancedlockingmechanism;

import java.util.concurrent.locks.StampedLock;

public class BankAccount {

    private double balance = 0;
    private final StampedLock lock = new StampedLock();

    public void deposit(double amount){
        long stamp = lock.writeLock();
        try{
            System.out.println(Thread.currentThread().getName() + " depositing: " + amount);
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " new balance after deposit: " + balance);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public  void withdraw(double amount) {

        long stamp = lock.writeLock();
        try{
            if(balance >= amount){
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + " withdrew: " + amount + ", new balance: " + balance);
            } else {
                System.out.println(Thread.currentThread().getName() + " insufficient funds for withdrawal of: " + amount + ", current balance: " + balance);
            }
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public double getBalanceOptimistic(){
        long stamp = lock.tryOptimisticRead();
        double currentBalance = balance;

        if(!lock.validate(stamp)){
            stamp = lock.readLock();
            try{
                currentBalance = balance;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        System.out.println(Thread.currentThread().getName() + " reading balance: " + currentBalance);
        return currentBalance;
    }

    public double getBalance(){
        long stamp = lock.readLock();
        try{
            return balance;
        } finally {
            lock.unlockRead(stamp);
        }
    }

}
