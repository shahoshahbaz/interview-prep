# Java Interfaces & Abstract Classes — Interview Cheat Sheet

---

## 1. What is an Interface?

A contract — defines **what** a class must do, not **how**.

```java
public interface Payable {
    void pay(double amount);  // no implementation — just the contract
}
```

Any class that implements `Payable` **must** provide a `pay()` method.

**Interview one-liner:**
> *"An interface is a contract — it defines what a class must do without saying how."*

---

## 2. What is an Abstract Class?

A **partially built class** — some methods are implemented (shared), some are left for subclasses to fill in (forced).

```java
public abstract class Animal {

    String name;  // shared field — all animals have a name

    // concrete method — shared behavior, same for all subclasses
    public void breathe() {
        System.out.println(name + " is breathing");
    }

    // abstract method — every animal does this differently
    // subclass MUST implement this
    public abstract void makeSound();
}
```

- You **cannot** do `new Animal()` — it is incomplete, exists only to be extended
- Subclasses inherit shared code and must fill in the abstract methods

**Interview one-liner:**
> *"An abstract class is a partially built class — it provides shared behavior and forces subclasses to implement what differs."*

---

## 3. Interface vs Abstract Class

| | Interface | Abstract Class |
|---|---|---|
| **Purpose** | Define a contract | Share common base behavior |
| **Multiple inheritance** | ✅ A class can implement many | ❌ A class can only extend one |
| **Fields** | Only `public static final` constants | ✅ Can have instance fields |
| **Constructor** | ❌ No | ✅ Yes |
| **Methods** | Abstract + default + static | Abstract + concrete |
| **Can instantiate?** | ❌ No | ❌ No |
| **When to use** | Unrelated classes need same behavior | Related classes share common code |

**Interview one-liner:**
> *"An interface defines a contract — what to do. An abstract class defines a base — what you are."*

---

## 4. The Rule of Thumb

```
Abstract class  → what you ARE        (CreditCardPayment IS A Payment)
Interface       → what you CAN DO     (CreditCardPayment CAN BE Refunded)
```

---

## 5. Multiple Inheritance

A class can only **extend one** abstract class but can **implement many** interfaces.

```java
// ❌ cannot extend two abstract classes — compile error
public class CreditCardPayment extends Payment extends AnotherClass { }

// ✅ can implement as many interfaces as needed
public class CreditCardPayment extends Payment
        implements Refundable, Loggable, Encryptable { }
```

**Simple way to remember:**
```
A person can have ONE biological mother     → abstract class (one only)
A person can hold MANY job titles           → interface (can implement many)
```

---

## 6. Payment System Example — When to Use Which

### Abstract class — shared base

```java
public abstract class Payment {

    protected String accountNumber;  // shared field

    public Payment(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    // shared behavior — same for all payments
    public void logPayment(double amount) {
        System.out.println("Payment of $" + amount +
                          " from account: " + accountNumber);
    }

    // forced — every payment processes differently
    public abstract void processPayment(double amount);

    // forced — every payment validates differently
    public abstract boolean validate();
}
```

### Interface — optional capability

```java
public interface Refundable {
    void refund(double amount);  // not all payments support refund
}
```

### Subclass — extends one, implements many

```java
// CreditCard IS A Payment AND CAN BE Refunded
public class CreditCardPayment extends Payment implements Refundable {

    public CreditCardPayment(String accountNumber) {
        super(accountNumber);
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
        logPayment(amount);  // reusing shared method from abstract class
    }

    @Override
    public boolean validate() {
        return accountNumber.length() == 16;
    }

    @Override
    public void refund(double amount) {
        System.out.println("Refunding $" + amount + " to credit card");
    }
}

// PayPal IS A Payment but does NOT support refund
public class PayPalPayment extends Payment {

    public PayPalPayment(String accountNumber) {
        super(accountNumber);
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
        logPayment(amount);
    }

    @Override
    public boolean validate() {
        return accountNumber.contains("@");  // PayPal uses email
    }
}
```

---

## 7. Default Methods in Interfaces (Java 8+)

Before Java 8 — interfaces could only have abstract methods.
Java 8 added **default methods** — a concrete method inside an interface with a fallback implementation.

```java
public interface Refundable {

    void refund(double amount);  // abstract — must implement

    // default — optional to override, fallback provided
    default void refundWithFee(double amount) {
        double fee = amount * 0.02;
        refund(amount - fee);
        System.out.println("Processing fee deducted: $" + fee);
    }
}
```

- Subclass can use `refundWithFee()` as-is or override it
- Allows adding new methods to interfaces without breaking existing implementations

**Interview one-liner:**
> *"Default methods let you add concrete behavior to an interface without forcing all implementing classes to change."*

---

## 8. Common Interview Questions

**Q: What is the difference between an interface and an abstract class?**
> An interface is a pure contract — defines what a class must do. An abstract class is a partial implementation — shares common code and forces subclasses to fill in what differs. A class can implement many interfaces but extend only one abstract class.

**Q: When would you use an abstract class over an interface?**
> When related classes share common fields or behavior. For example, `Payment` has a shared `accountNumber` field and `logPayment()` method — that belongs in an abstract class, not an interface.

**Q: When would you use an interface over an abstract class?**
> When unrelated classes need to share a capability. `Refundable` can apply to `CreditCardPayment`, a `SubscriptionService`, or a `TicketBooking` — they are unrelated but share the same capability.

**Q: Can an abstract class implement an interface?**
> Yes. The abstract class can implement some or none of the interface methods — subclasses will fill in the rest.

**Q: Can you instantiate an abstract class?**
> No. It is incomplete by design — you must extend it and implement all abstract methods first.

**Q: What are default methods in interfaces?**
> Concrete methods added to interfaces in Java 8 — they provide a fallback implementation so existing classes don't break when new methods are added to an interface.

---

*Part of Java interview prep series — java-concepts module*