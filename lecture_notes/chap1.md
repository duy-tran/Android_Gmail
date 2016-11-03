Chapter 1: Introduction and Android Fundamentals
================================================

 

### Group 3

-   Tran Quang Duy - duy9968@gmail.com

-   Vu Binh Duong - badboy.hp96@gmail.com

-   Pham Tien Thanh - vicnoob.gg@gmail.com

 

### Objective: To have a clear view about Android and understand basic fundamentals of Android 

 

### Introduction

OS: a software that controls hardware, manages software, intermediate layer between hardware and application, provides services for the software to work with the hardware. 

Android: mobile OS, linux distribution, set of user-space applications and frameworks,  use Java.

Android runs on phones, tablets, watches, tv, set-top boxes, cars, glasses and so on.

Android: popular, cheap, fast, easy to use

-   Reinforce the basics: OOP, decomposition

-   Separation of UI design and functionality 

-   Asynchronous programming

-   Callback methods

 

### Android Fundamentals 

1.  Architecture

    1.  Kernel

    2.  Libraries

    Mostly in C/C++, low level

    1.  Application framework

    In Java, higher level, have user interface, location service, notification,...

    1.  Application

    Written in Java (the course mainly focus on this part)

2.  Compilation

    Use virtual machine

3.  Controllers

    Controller: Context, Application, Activity, Fragment 

    Context: central command center, access application-specific data: settings, private files, resources, assets, system services (push notification) 

    Application: A context, can be subclassed to contain global data, early initialization of libraries. 

    Global data: instance, token (server sends back after successful authentication)

    Android memory management:

    -   Garbage collector

    -   Upper limit for each application

    -   Kill activities when low memory

    -   Out-of-memory exception 

    Android Manifest: metadata, permission
