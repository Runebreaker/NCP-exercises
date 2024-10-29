## Task 1 - Java daemon Thread
Create two threads, one daemon and one User Thread.
They should display their types on the console.
You program should display something like that:

```
Hi, I am User Thread
Hi, I am daemon Thread
```


## Task 2 - Java and Interrupt Pattern

Use the Interrupt-Pattern from the lecture to stop a thread

- which is printing Hello World! every 300ms
- after a given time:
  - After 50 ms
  - After 300 ms
  - After a random number of ms between 1ms and 1000ms
  - After pressing a key

## Task 3 - Java Thread Priority

Create three threads
- one with maximum priority
- one with minimum priority
- one with normal priority

They should display their priority on the console.
You program should display something like that:

```
Hi, I am a thread with a priority of 10
Hi, I am a thread with a priority of 1
Hi, I am a thread with a priority of 5
```

Hint: Check the Thread class