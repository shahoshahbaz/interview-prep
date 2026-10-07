package practice.javaconcepts.threading.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    private double balance = 1000.0;
    private final ReentrantLock lock = new ReentrantLock();

    public void withdraw(String person, double amount){
        System.out.println(person + " trying to withdraw $" + amount);

        if(lock.tryLock()){
            try {
                if(balance >= amount){
                    System.out.println(person + " checking balance: $" + balance);
                    balance -= amount;
                    System.out.println(person + " withdrew $" + amount
                            + " — remaining balance: $" + balance);
                }else{
                    System.out.println(person + " — insufficient funds!");
                }
            }finally {
                lock.unlock();
                System.out.println(person +" released the lock");

            }
        }else{
            System.out.println(person + " — account is busy, try again later");
        }
    }

}
