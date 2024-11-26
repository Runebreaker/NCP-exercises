## Task 6.1 - Semaphores
Using the code and explanations given in the lecture:
1. Use semaphore to create a simple program
2. that will let no more than 4 threads using a limited shared resource at the same time
3. Test your program with 5 threads, one thread should always be waiting.
> Here the limited shared resource is just a function sleeping for 1000 milliseconds (e.g. simulating database access)

## Task 6.2 - Image Generator
Using the code and explanations given in the lecture:
1. Create a simple program that computes large procedural images using FutureTask.
2. Use the code in edu.ncp.utils.ImageUtils in the lecture gitlab repo to randomly generate an RGB image and save it as .bmp file.
3. Measure the time to create the image using the edu.ncp.utils.StopWatch
4. Measure the time with different image resolutions from very small to extra large (4K images),
> Procedural images can be used for many things in a game (e.g. used as textures, height maps for terrain generation, …) If you want, you can try to modify the image random generation techniques.

## Task 6.3 - Game Engine Startup Simulation
Using the code and explanations given in the lecture:
1. use a CountDownLatch, to create a simple game engine.
2. A GameLogic thread will create and wait for three Subsystem threads (see below) to finish their initialisations before starting.
   - PhysicsSystem
   - AudioSystem
   - GraphicsSystem
> The GameLogic thread which will just wait until the Subsystem threads finish their startup function before entering the game update loop. Subsystem threads are just threads sleeping for various amounts of time to simulate a complex and diverse initialization phase. The GameLogic thread should display “engine is starting” before the other engines are intialised, and when all subsystem threads are ready, it starts updating the game every 16 ms (e.g. just displaying “tick” on console). Use edu.ncp.utils.LogUtil.Log to print status messages. After 3000 ms, the GameLogic thread is interrupted and ends the game. It gracefully shuts down the subsystems and waits for all subsystem threads to finish before exiting the program.

