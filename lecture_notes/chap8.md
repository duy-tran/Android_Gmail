Chapter 8: Threading
====================

 

### Group 3

-   Tran Quang Duy - <duy9968@gmail.com>

-   Vu Binh Duong - <badboy.hp96@gmail.com>

-   Pham Tien Thanh - <vicnoob.gg@gmail.com>

 

### Threading

 

Can be considered as sub-process

Thread can be created faster, better CPU utilization, separation of tasks

Context switch allocates CPU between processes

Each thread has its own registers to copy the register value for it to wake up

However there can be complication, inconsistency, more threads means more
waiting time

 

### Android Thread Model

 

Main thread: drawing widgets, dispatching user inputs

Views can’t be manipulated using worker threads

Example: only main thread can call Toaster

Use handler to send messages between threads
