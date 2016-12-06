Chapter 10: Network
===================

 

### Group 3

-   Tran Quang Duy - <duy9968@gmail.com>

-   Vu Binh Duong - <badboy.hp96@gmail.com>

-   Pham Tien Thanh - <vicnoob.gg@gmail.com>

 

### Network concepts: 

OSI model: 7 network layers

Socket: TCP/UDP

Protocol: HTTP / FTP / SIP / SMTP / IMAP ...

 

### Permissions

Specific actions require permissions

Permission is to implement security and privacy

There are 2 types of permissions: normal and dangerous (sensitive action)

-   Normal: Internet access, set wallpaper, ...

-   Dangerous: Access to contact list, phone, SMS, camera, location,
    microphone,...

Permissions needed must be defined in manifest

 

### Embedded package

1.  Create URL from string

2.  Make request to server

    Open connection, set request method (GET/POST/…)

3.  Receive response

    Use `getResponseCode()` to check response (200,403,404,500,…)

    get input stream from connection

4.  Process response

    Different response types need different data treatment (Image, JSON/XML…)

     

### External Library

Embedded package has its shortages: lots of code, no queue, no cache

\-\> We could use Volley from Google to solve this

1.  Create request queue (one per app)

    Get instance from a shared class

2.  Request with listeners

    Two main types: image request and string request

    Use listener to process after get data from request

3.  Add request to queue

 

### Data representation

JSON/XML

JSON: structured data, lighter, simple, human-readable

Use Json parser to get data from Json

 
