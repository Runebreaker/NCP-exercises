## Task 0 - Java Progamming Tools Installation:
    - If you are working with your own computer:
        - Please install the recommended Java IDE and SDK on the Programming Tools page
    - If you are working in the labs
        - Please read the licence activation procedure on the Programming Tools page

## Task 1 - Hello, Java threads!

Create a Thread in Java that will just display “Hello World!” on the system console using the different methods discussed during the lecture:

    - Thread class
    - Runnable
    - Anonymous Runnables
    - Lambdas

## Task 2 - Hello, Java thread class!

Develop a program reproducing the console output below. Random values do not need to match.

Console Output:
```
>> Main Thread Starting...

Hello, World! - from Main Thread JVM name : main

3 New Threads Created :

AppID [0] JVM name: Thread-0
AppID [1] JVM name: Thread-1
AppID [2] JVM name: Thread-2

>> New Threads Starting... 

Hello, World! -  From New Thread - Application ID [0]  (JVM ID [11])
Hello, World! -  From New Thread - Application ID [1]  (JVM ID [12])
Hello, World! -  From New Thread - Application ID [2]  (JVM ID [13])
Thread AppID [0] will sleep for 420 milli-seconds (random - max 5 seconds)
Thread AppID [1] will sleep for 1697 milli-seconds (random - max 5 seconds)
Thread AppID [2] will sleep for 895 milli-seconds (random - max 5 seconds)
Thread AppID [0] Finished sleeping (actual time sleeping : 423 ms)
Thread AppID [2] Finished sleeping (actual time sleeping : 899 ms)
Thread AppID [1] Finished sleeping (actual time sleeping : 1699 ms)

<< All New Threads Finished

<< Main Thread Finished
```

## Task 3 - Hello, (un-)responsive Java GUI!

Replicate the solution explained in the lecture to fix the code in `DemoGUIUnresponsive.java` so that the GUI stays responsive after user clicks the button.
