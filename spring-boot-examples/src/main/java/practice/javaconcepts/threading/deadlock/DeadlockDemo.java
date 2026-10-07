package practice.javaconcepts.threading.deadlock;

public class DeadlockDemo {
    public static void main(String[] args) {
        BankAccountDeadlock accountA = new BankAccountDeadlock("Account A", 1000.0);
        BankAccountDeadlock accountB = new BankAccountDeadlock("Account B", 1000.0);

        // thread 1 — transfers from A to B
        // locks A first, then tries to lock B
        Thread t1 = new Thread( () ->
                accountA.transfer(accountB, 100.0), "Thread-1");
        // thread 2 — transfers from B to A
        // locks B first, then tries to lock A

        Thread t2 = new Thread(() ->
                accountB.transfer(accountA, 50.0), "Thread-2");

        t1.start();
        t2.start();


    }
}
