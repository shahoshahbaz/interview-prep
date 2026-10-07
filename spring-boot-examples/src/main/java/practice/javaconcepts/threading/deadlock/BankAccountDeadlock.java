package practice.javaconcepts.threading.deadlock;

import java.util.concurrent.locks.ReentrantLock;

public class BankAccountDeadlock {

    private double balance;
    private final String name;
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccountDeadlock(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }


    public void transfer(BankAccountDeadlock target, double amount) {
        System.out.println(Thread.currentThread().getName() +
                " trying to lock " + this.name);
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()
                    + " locked " + this.name
                    + " — now trying to lock " + target.name);

            target.lock.lock(); // lock target account
            try{
                this.balance -= amount;
                target.balance += amount;
                System.out.println("Transferred $" + amount
                        + " from " + this.name
                        + " to " + target.name);

            }finally{
                target.lock.unlock();
            }

        }finally {
            lock.unlock();
        }
    }
}
