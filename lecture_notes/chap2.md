Chapter 2: Android Fundamentals (continue)
==========================================

 

### Group 3

-   Tran Quang Duy - duy9968@gmail.com

-   Vu Binh Duong - badboy.hp96@gmail.com

-   Pham Tien Thanh - vicnoob.gg@gmail.com

 

### Objective: To understand how activity, fragment and view work

 

### Activity

 

Android does not have main(), all code run in activity 

Activity controls user interface

An app has various activities 

Activity has life cycle: 

-   using (resume)

-   partially visible (pause)

-   invisible (stop)

 

*Example*

onCreate(): Initialization 

onPause(): stop animation or heavy task, stop unsaved changes, release resources 

Screen orientation: create child activity

close current activity: call finish()

 

### Fragment

 

*Example: *List Fragment - Detail Fragment (Gmail, Settings app) 

For different screen (size, resolution, density, orientation)

Fragment represents a behavior or a portion of UI, building block of fundamental building blocks

Fragment is optional (example: game, calculator, camera app) 

Don’t need view unlike activity 

Has life cycle similar to Activity

Can be nested 

Activity with fragment is simplified, use FragmentManager 

 

### View

 

View is the user interface 

Can be XML or dynamic code

*Example:* TextView, ImageView, Button, ViewGroup (contain children views)
