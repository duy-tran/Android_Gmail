Chapter 6: Values
=================

 

### Group 3

-   Tran Quang Duy - <duy9968@gmail.com>

-   Vu Binh Duong - <badboy.hp96@gmail.com>

-   Pham Tien Thanh - <vicnoob.gg@gmail.com>

 

### Values

Central point of all constants

 

Suppor languages (i18n)

Default: res/values/strings.xml

````<string name=“refresh”>Refresh</string>````

 

Different variables for different screen sizes

 

Using variables to avoid hard-coded values

 

Activity is already a context -\> don’t need getContext(), use this instead

Other: getActivity() or getContext()

 

### Drawables

2 types:

-   XML drawables: vector

-   Bitmap drawables: PNG/JPEG

Bitmap is put in different res/drawable-\*dpi

240 - hdpi

320 - xhdpi

480 - xxhdpi

 

\-\> use dip (dp): Density-independent Pixels

 

9patch photo is for stretching

File name: \*.9.png

Left + top: use black pixel for stretch
