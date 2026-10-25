Say It

A programming language that doesn't teach you types.

Say It is a programming language designed to make coding easier to write and understand, combining Python-like simplicity with Java/C++-inspired programming concepts.

---

✨ Features

- Simple Syntax — No semicolons required.
- Variables — Declare variables using "as".
- Expressions — Perform mathematical operations and combine values.
- Built-in Functions — Includes "show()", "showln()", "input()", "toInt()", and more.
- Nested Function Calls — Execute nested expressions and function calls.
- Conditional Statements — Supports "if" statements.
- Loops — Supports "while" and "for" loops.
- Java-Based Runtime — Built using Java.

🚀 Example

name as string = input("Enter your name: ")
show("Hello, " name "\n")

🧮 Calculator Example

A as int = toInt(input("First number: "))
B as int = toInt(input("Second number: "))

show("Addition: " (A + B) "\n")
show("Subtraction: " (A - B) "\n")
show("Multiplication: " (A * B) "\n")

🎯 Philosophy

Say It aims to make programming more approachable without sacrificing expressive power.

- Less syntax overhead.
- Readable code.
- Flexible expressions.
- Familiar programming concepts with a different approach.

🛠️ Built With

- Java — Language implementation and runtime.
- Custom Compiler and Runtime — For parsing, compiling, and executing Say It programs.

📦 Getting Started

Say It is currently under development as a Java project.

Requirements

- Java Development Kit (JDK)
- A Java IDE or text editor

Running the Project

1. Clone or download this repository.
2. Open the project in your Java development environment.
3. Locate "Main.java".
4. Run "Main.java" to start the program or execute the available tests.

Note: Say It is still evolving. The project structure, syntax, and features may change as development continues.

🤝 Contributing

Contributions are welcome under the project's license.

1. Fork this repository.
2. Create a separate branch for your changes.
3. Implement and test your improvements.
4. Submit a Pull Request for review.
5. Do not push directly to the official "main" branch.

All contributions are subject to review and acceptance.

📜 License

Say It is distributed under the Say It Custom License (SICL).

- Free to develop and use.
- Commercial use is permitted under the license terms.
- Commercial projects must link to the official Say It GitHub repository.
- Claiming ownership of the original Say It project is prohibited.

See the ""LICENSE"" (LICENSE) file for the complete terms.

👨‍💻 Author

S Siva Surya

Creator and developer of Say It.

📝 First Update Full Features:
1. show(...) -> print function
2. showln(...) -> prints everything in new line
3. add(a,b) -> add a and b -> return result number
4. sub(a,b) -> subtracts a and b -> return result number
5. if(condition+block... block) -> check condition and if true execute block , else if there is another condition repeat or else execute last block if present.
6.  while(condition block) -> it executes block till condition is true
7.  for("loop_var_name" n block) -> execute block n times storing 0-(n-1) in loop_var_name
8.  createFunction(block "name" "returntype" "argtype"+"argname"...) -> creates a function at runtime
9.  input(string) -> show the string and wait for input
10.  toInt(string) -> string to int
11.  toLonh(string) -> string to long
12.  toFloat(string) -> string to float
13.  toDouble(string) -> string to double
14.  bool() -> initialize a Boolean var
15.  bool(string) -> true if non empty
16.  bool(number) -> true if greater than 0
---

Say It — Write code your way.
