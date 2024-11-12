## Task 4.1 - Multiplayer Multithreaded Turn-based Game

Using **monitor waiting conditions**:
- Modify the [multithreaded turn-based Chess Game prototype](https://lectures.hci.informatik.uni-wuerzburg.de/ws24/ncp/lectures/04-lecture-deck.html#/thread-waiting-condition-example) described in the lecture to handle “6-players”

## Taks 4.2 - Simple Producer-Consumer Pattern
Using **monitor waiting conditions**,
- program a simple producer-consumer pattern as described in the lecture
- where one thread is producing sequential numbers (incrementing number from zero to 1000),
- and another thread is consuming this number by displaying them on the console.
- Once finished, the producer should tell the consumer to stop, before exiting the program.
- Extra Task: Do the same but using a LinkedBlockingQueue
