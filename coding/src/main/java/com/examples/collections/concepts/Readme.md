## Static Keyword in Java

In Java, the `static` keyword is used to indicate that a particular member (variable or method) belongs to the class itself rather than to instances of the class. This means that static members are shared among all instances of a class and can be accessed without creating an instance of the class.

### Key Features of `static`

#### Static Variables (Class Variables):
- A static variable is shared among all instances of a class. It can be accessed directly using the class name.
- It's initialized only once when the class is loaded.

#### Static Methods:
- A static method can be called without creating an instance of the class.
- It can only access static variables and static methods of the class.

#### Static Blocks:
- A static block is used for static initialization of a class. It runs once when the class is loaded.

#### Static Classes:
- In Java, you can declare a nested class as static. A static nested class can access the static members of the outer class but cannot access instance variables or methods.
