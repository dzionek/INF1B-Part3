# Library system for INF1B 2020
My solution of the final part of coursework for OOP course at the University of Edinburgh. Our aim was to create a well-documented and tested library system CLI. During developing the app we used Test Driven Development and design patterns. I have received the highest grade in my class (421 students) with 87% (**A2**) for this submission.

For more details about the course see the [course website](http://www.drps.ed.ac.uk/19-20/dpt/cxinfr08029.htm).

## Installation & Usage
Use the built-in Java compiler to compile and then open the file in command line.
```bash
cd project
javac -d out src/*.java
java -cp out Main
```

To see the list of all commnands type "HELP". You will not initially see any books, use the "ADD" command with csv argument to add them:
```bash
> ADD books01.csv
24 new book entries added.
```
