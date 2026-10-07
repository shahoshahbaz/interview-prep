package practice.javaconcepts.threading.reentrantlock;

public class WithdrawalDemo {

    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        Thread atm1 = new Thread(()->account.withdraw("Alice", 800.0));
        Thread atm2 = new Thread(()->account.withdraw("Bob", 800.0));

        atm1.start();
        atm2.start();
    }
}
